package ledger.wallet.handler;

import ledger.wallet.data.entity.WalletView;
import ledger.wallet.data.repository.WalletReadRepository;
import ledger.wallet.dto.WalletBalanceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
public class WalletBalanceQueryHandler {

    @Autowired
    private WalletReadRepository repository;

    private Executor executor = Executors.newVirtualThreadPerTaskExecutor();

    public CompletableFuture<WalletBalanceResponse> getWalletBalance(String walletId, long balanceAsOfTimestamp) {
        return getWalletBalanceHistory(walletId, balanceAsOfTimestamp)
                .thenApply(walletView -> {
                    if (walletView.isPresent()) {
                        WalletView w = walletView.get();
                        OffsetDateTime balUpdatedTime = OffsetDateTime.ofInstant(Instant.ofEpochMilli(w.getTimestamp()), ZoneId.of("UTC"));

                        return new WalletBalanceResponse(w.getWalletId(), w.getBalance(), balUpdatedTime.toString());
                    } else {
                        return new WalletBalanceResponse(walletId, null, null);
                    }
                });
    }

    public CompletableFuture<Optional<WalletView>> getWalletBalanceHistory(String walletId, long balanceAsOfTimestamp) {
        return CompletableFuture.supplyAsync(() -> repository.findWalletHistoryAsOf(walletId, balanceAsOfTimestamp), executor);
    }
}

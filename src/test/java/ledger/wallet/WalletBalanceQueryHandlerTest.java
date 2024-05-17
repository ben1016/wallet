package ledger.wallet;

import ledger.wallet.data.entity.WalletView;
import ledger.wallet.data.repository.WalletReadRepository;
import ledger.wallet.dto.WalletBalanceResponse;
import ledger.wallet.handler.WalletBalanceQueryHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class WalletBalanceQueryHandlerTest {

    @InjectMocks
    private WalletBalanceQueryHandler walletBalanceQueryHandler;

    @Mock
    private WalletReadRepository repository;

    @Test
    public void testQueryWalletBalance() throws ExecutionException, InterruptedException {
        //test data
        String walletId = "1";
        long balanceAsOfTime = Instant.now().toEpochMilli();
        BigDecimal balance = BigDecimal.TWO;
        long balanceUpdatedTime = Instant.now().minusSeconds(10).toEpochMilli();

        when(repository.findWalletHistoryAsOf(any(), anyLong())).thenReturn(Optional.of(new WalletView("123", walletId, balance, balanceUpdatedTime)));

        //test
        WalletBalanceResponse result = walletBalanceQueryHandler.getWalletBalance(walletId, balanceAsOfTime).get();

        //verify
        Assertions.assertEquals(walletId, result.getWalletId());
        Assertions.assertEquals(balance, result.getBalance());
        Assertions.assertEquals(OffsetDateTime.ofInstant(Instant.ofEpochMilli(balanceUpdatedTime), ZoneId.of("UTC")).toString(),
                result.getBalanceUpdatedTimestamp());
    }

}

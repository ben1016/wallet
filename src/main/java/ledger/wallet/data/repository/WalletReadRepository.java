package ledger.wallet.data.repository;

import ledger.wallet.data.entity.WalletView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface WalletReadRepository extends JpaRepository<WalletView, String> {

    @Query(value = "SELECT * FROM wallet_view w WHERE w.wallet_Id = :walletId AND w.timestamp <= :balanceAsOfTimestamp " +
            "ORDER BY timestamp DESC LIMIT 1",
            nativeQuery = true)
    Optional<WalletView> findWalletHistoryAsOf(String walletId, long balanceAsOfTimestamp);
}

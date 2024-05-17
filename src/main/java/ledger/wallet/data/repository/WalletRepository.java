package ledger.wallet.data.repository;

import ledger.wallet.data.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, String> {

    @Query(value = "SELECT * FROM wallet w WHERE w.wallet_Id = :walletId ORDER BY updated_time DESC LIMIT 1",
            nativeQuery = true)
    Optional<Wallet> findLatestWallet(String walletId);
}

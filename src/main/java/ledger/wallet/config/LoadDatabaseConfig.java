package ledger.wallet.config;

import ledger.wallet.data.entity.Account;
import ledger.wallet.data.entity.Wallet;
import ledger.wallet.data.repository.AccountRepository;
import ledger.wallet.data.repository.WalletRepository;
import ledger.wallet.dto.AccountStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.math.BigDecimal;

@Configuration
public class LoadDatabaseConfig {

    @Bean
    CommandLineRunner initDatabase(WalletRepository walletRepo, AccountRepository accountRepo) {
        return args -> {
            walletRepo.save(new Wallet("1", "1", BigDecimal.valueOf(200)));
            walletRepo.save(new Wallet("1", "2", BigDecimal.valueOf(200)));
            walletRepo.save(new Wallet("1", "3", BigDecimal.valueOf(200)));
            walletRepo.save(new Wallet("1", "4", BigDecimal.valueOf(200)));

            accountRepo.save(new Account("1", "1", AccountStatus.OPEN.toString()));
        };
    }
}

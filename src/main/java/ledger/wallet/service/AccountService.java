package ledger.wallet.service;

import ledger.wallet.data.entity.Account;
import java.util.concurrent.CompletableFuture;

public interface AccountService {
    CompletableFuture<Account> createAccount(String ledgerId, String status);

    CompletableFuture<Account> updateAccount(String accountId, String status);
}

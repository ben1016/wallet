package ledger.wallet.service.impl;

import ledger.wallet.data.entity.Account;
import ledger.wallet.data.repository.AccountRepository;
import ledger.wallet.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository repository;

    private Executor executor = Executors.newVirtualThreadPerTaskExecutor();

    @Override
    public CompletableFuture<Account> createAccount(String ledgerId, String status) {
        return CompletableFuture.supplyAsync(() -> repository.save(new Account(ledgerId, status)), executor);
    }

    @Override
    public CompletableFuture<Account> updateAccount(String accountId, String status) {
        return CompletableFuture.supplyAsync(() -> repository.findById(accountId)
                .map(account -> {
                    account.setStatus(status);
                    return repository.save(account);
                })
                .orElseThrow(), executor);
    }
}

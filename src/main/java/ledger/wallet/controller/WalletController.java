package ledger.wallet.controller;

import ledger.wallet.data.entity.Account;
import ledger.wallet.dto.*;
import ledger.wallet.handler.TransferAssetCommandHandler;
import ledger.wallet.handler.WalletBalanceQueryHandler;
import ledger.wallet.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.concurrent.CompletableFuture;

@RestController
public class WalletController {

    @Autowired
    private TransferAssetCommandHandler transferAssetCommandHandler;

    @Autowired
    private WalletBalanceQueryHandler walletBalanceQueryHandler;

    @Autowired
    private AccountService accountService;

    @PostMapping("/wallets/transfer")
    public CompletableFuture<ResponseEntity<TransferAssetResponse>> transferAsset(@RequestBody TransferAssetRequest request) {
        TransferAssetCommand command = new TransferAssetCommand(request.getTransfers(), Instant.now().toEpochMilli());

        return transferAssetCommandHandler.handleTransferAssetCommand(command)
                .thenApply(resp -> ResponseEntity.ok().body(new TransferAssetResponse(resp)));
    }

    @GetMapping("/wallets/balance")
    public CompletableFuture<ResponseEntity<WalletBalanceResponse>> getWalletBalance(@RequestBody WalletBalanceRequest request) {
        long balanceAsOf = OffsetDateTime.parse(request.getBalanceAsOfTimestamp()).toInstant().toEpochMilli();

        return walletBalanceQueryHandler.getWalletBalance(request.getWalletId(), balanceAsOf)
                .thenApply(walletBalanceResponse -> ResponseEntity.ok().body(walletBalanceResponse));
    }

    @PatchMapping("/account")
    public CompletableFuture<ResponseEntity<Account>> updateAccount(@RequestBody AccountUpdateRequest request) {
        return accountService.updateAccount(request.getAccountId(), request.getStatus())
                .thenApply(account -> ResponseEntity.ok().body(account));
    }

    @PostMapping("/account")
    public CompletableFuture<ResponseEntity<Account>> createAccount(@RequestBody AccountCreateRequest request) {
        return accountService.createAccount(request.getLedgerId(), request.getStatus())
                .thenApply(account -> ResponseEntity.ok().body(account));
    }

}

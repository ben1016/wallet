package ledger.wallet.handler;

import ledger.wallet.data.repository.WalletRepository;
import ledger.wallet.dto.TransferAssetCommand;
import ledger.wallet.dto.TransferAssetResponseDTO;
import ledger.wallet.dto.TransferStatus;
import ledger.wallet.event.TransferAssetCompletedEvent;
import ledger.wallet.event.WalletBalanceUpdatedEvent;
import ledger.wallet.event.producer.TransferAssetCompletedEventProducer;
import ledger.wallet.event.producer.WalletBalanceUpdatedEventProducer;
import ledger.wallet.service.TransferAssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
public class TransferAssetCommandHandler {
    @Autowired
    private TransferAssetService transferAssetService;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalletBalanceUpdatedEventProducer walletBalanceUpdatedEventProducer;

    @Autowired
    private TransferAssetCompletedEventProducer transferMoneyEventProducer;

    private Executor executor = Executors.newVirtualThreadPerTaskExecutor();

    @Transactional
    public CompletableFuture<String> handleTransferAssetCommand(TransferAssetCommand command) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                var transferAssetResponseDTOList = transferAssetService.transferAsset(command.getDetails());

                sendWalletBalanceUpdatedEvent(transferAssetResponseDTOList);

                return TransferStatus.CLEARED.toString();
            } catch (Exception e) {
                System.out.println("Exception occurred in handling TransferMoneyCommand " + e);
                return TransferStatus.FAILED.toString();
            }
        }, executor);
    }

    private void sendWalletBalanceUpdatedEvent(ArrayList<TransferAssetResponseDTO> responseDTOList) {
        responseDTOList.stream().forEach(responseDTO -> {
            TransferAssetResponseDTO.TransferredWallet fromWallet = responseDTO.getUpdatedFromWallet();
            WalletBalanceUpdatedEvent fromEvent = new WalletBalanceUpdatedEvent(fromWallet.getWalletId(), fromWallet.getBalance(), fromWallet.getUpdatedTime());
            walletBalanceUpdatedEventProducer.sendWalletBalanceUpdatedEvent(fromEvent);

            TransferAssetResponseDTO.TransferredWallet toWallet = responseDTO.getUpdatedToWallet();
            WalletBalanceUpdatedEvent toEvent = new WalletBalanceUpdatedEvent(toWallet.getWalletId(), toWallet.getBalance(), toWallet.getUpdatedTime());
            walletBalanceUpdatedEventProducer.sendWalletBalanceUpdatedEvent(toEvent);

            TransferAssetCompletedEvent transferAssetCompletedEvent = new TransferAssetCompletedEvent(TransferStatus.CLEARED.toString(),
                    responseDTO.getUpdatedFromWallet(), responseDTO.getUpdatedToWallet());

            transferMoneyEventProducer.sendTransferMoneyCompletedEvent(transferAssetCompletedEvent);
        });
    }

}

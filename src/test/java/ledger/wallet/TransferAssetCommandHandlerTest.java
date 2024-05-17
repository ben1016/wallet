/*
package ledger.wallet;

import ledger.wallet.data.entity.Wallet;
import ledger.wallet.data.repository.WalletRepository;
import ledger.wallet.dto.TransferAssetCommand;
import ledger.wallet.dto.TransferAssetResponseDTO;
import ledger.wallet.dto.TransferRequestDetails;
import ledger.wallet.dto.TransferStatus;
import ledger.wallet.event.producer.TransferAssetCompletedEventProducer;
import ledger.wallet.event.producer.WalletBalanceUpdatedEventProducer;
import ledger.wallet.handler.TransferAssetCommandHandler;
import ledger.wallet.service.TransferAssetService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class TransferAssetCommandHandlerTest {
    @InjectMocks
    private TransferAssetCommandHandler transferAssetCommandHandler;

    @Mock
    private TransferAssetService transferAssetService;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private WalletBalanceUpdatedEventProducer walletBalanceUpdatedEventProducer;

    @Mock
    private TransferAssetCompletedEventProducer transferMoneyEventProducer;

    @Test
    public void asset_transferred_after_handling_command() throws ExecutionException, InterruptedException {
        //given
        String testFromWalletId = "1";
        String testToWalletId = "2";

        Wallet testFromWallet = new Wallet("1", BigDecimal.TEN);
        Wallet testToWallet = new Wallet("2", BigDecimal.TEN);

        when(transferAssetService.transferAsset(any(), any(), any()))
                .thenReturn(new TransferAssetResponseDTO(new TransferAssetResponseDTO.TransferredWallet(testFromWalletId, BigDecimal.valueOf(9), Instant.now().toEpochMilli()),
                        new TransferAssetResponseDTO.TransferredWallet(testFromWalletId, BigDecimal.valueOf(11), Instant.now().toEpochMilli()), BigDecimal.ONE));
        when(walletRepository.findLatestWallet(testFromWalletId))
                .thenReturn(Optional.of(testFromWallet));
        when(walletRepository.findLatestWallet(testToWalletId))
                .thenReturn(Optional.of(testToWallet));

        TransferRequestDetails[] requestDetailsList = {new TransferRequestDetails(testFromWalletId, testToWalletId, BigDecimal.ONE)};
        TransferAssetCommand command = new TransferAssetCommand(requestDetailsList, Instant.now().toEpochMilli());

        //test
        CompletableFuture<String> result = transferAssetCommandHandler.handleTransferAssetCommand(command);

        //verify
        Assertions.assertEquals(TransferStatus.CLEARED.toString(), result.get());
    }
}
*/

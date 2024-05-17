package ledger.wallet;

import ledger.wallet.data.entity.Wallet;
import ledger.wallet.data.repository.WalletRepository;
import ledger.wallet.dto.TransferRequestDetails;
import ledger.wallet.service.ProjectionService;
import ledger.wallet.service.impl.TransferAssetServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class TransferAssetServiceTest {
    @InjectMocks
    private TransferAssetServiceImpl transferAssetService;

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private ProjectionService projectionService;

    @Test
    public void testTransferAsset() {
        //test data
        String fromWalletId = "1";
        String toWalletId = "2";

        Arrays.asList(fromWalletId, toWalletId).forEach(walletId ->
            when(walletRepository.findLatestWallet(walletId))
                    .thenReturn(Optional.of(new Wallet("1", walletId, BigDecimal.TEN)))
        );

        when(walletRepository.save(any())).thenAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                return invocation.getArguments()[0];
            }
        });

        var requestDetails = Arrays.asList(new TransferRequestDetails(fromWalletId, toWalletId, BigDecimal.ONE));

        //test
        var responseDTO = transferAssetService.transferAsset(requestDetails);

        //verify
        Assertions.assertEquals(BigDecimal.valueOf(9), responseDTO.get(0).getUpdatedFromWallet().getBalance());
        Assertions.assertEquals(BigDecimal.valueOf(11), responseDTO.get(0).getUpdatedToWallet().getBalance());
    }

    @Test
    public void transferMultipleAssets() {
        //test data
        Arrays.asList("1", "2", "3", "4").forEach(walletId ->
                when(walletRepository.findLatestWallet(walletId))
                        .thenReturn(Optional.of(new Wallet("1", walletId, BigDecimal.TEN)))
                );

        when(walletRepository.save(any())).thenAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) {
                return invocation.getArguments()[0];
            }
        });

        var requestDetails = Arrays.asList(new TransferRequestDetails("1", "2", BigDecimal.ONE),
                new TransferRequestDetails("3", "4", BigDecimal.TWO));

        //test
        var responseDTO = transferAssetService.transferAsset(requestDetails);

        //verify
        Assertions.assertEquals(BigDecimal.valueOf(9), responseDTO.get(0).getUpdatedFromWallet().getBalance());
        Assertions.assertEquals(BigDecimal.valueOf(11), responseDTO.get(0).getUpdatedToWallet().getBalance());

        Assertions.assertEquals(BigDecimal.valueOf(8), responseDTO.get(1).getUpdatedFromWallet().getBalance());
        Assertions.assertEquals(BigDecimal.valueOf(12), responseDTO.get(1).getUpdatedToWallet().getBalance());
    }


    @Test
    public void exception_thrown_when_wallet_not_found() {
        //test data
        String fromWalletId = "1";
        String toWalletId = "2";
        var requestDetails = Arrays.asList(new TransferRequestDetails(fromWalletId, toWalletId, BigDecimal.ONE));

        //test
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            transferAssetService.transferAsset(requestDetails);
        });

        //verify
        assertTrue(exception.getMessage().contains("No value present"));
    }
}

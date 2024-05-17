package ledger.wallet.service.impl;

import ledger.wallet.data.entity.Wallet;
import ledger.wallet.data.repository.WalletRepository;
import ledger.wallet.dto.TransferAssetResponseDTO;
import ledger.wallet.dto.TransferRequestDetails;
import ledger.wallet.service.ProjectionService;
import ledger.wallet.service.TransferAssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransferAssetServiceImpl implements TransferAssetService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private ProjectionService projectionService;

    @Override
    @Transactional
    public ArrayList<TransferAssetResponseDTO> transferAsset(final List<TransferRequestDetails> transferRequestDetails) {
        ArrayList<TransferAssetResponseDTO> transferAssetResponseDTOList = new ArrayList<>();
        for (TransferRequestDetails request : transferRequestDetails) {
            final String fromWalletId = request.getFromWalletId();
            final String toWalletId = request.getToWalletId();

            transferAssetResponseDTOList.add(transferAsset(fromWalletId, toWalletId, request.getAmount()));
        }

        return transferAssetResponseDTOList;
    }

    private TransferAssetResponseDTO transferAsset(final String fromWalletId, final String toWalletId, final BigDecimal amount) {
        final Wallet fromWallet = walletRepository.findLatestWallet(fromWalletId)
                .map(wallet -> walletRepository.save(new Wallet(wallet.getAccountId(), fromWalletId, wallet.getBalance().subtract(amount))))
                .orElseThrow();
        projectionService.updateProjection(fromWallet);

        final Wallet toWallet = walletRepository.findLatestWallet(toWalletId)
                .map(wallet -> walletRepository.save(new Wallet(wallet.getAccountId(), toWalletId, wallet.getBalance().add(amount))))
                .orElseThrow();
        projectionService.updateProjection(toWallet);

        return new TransferAssetResponseDTO(new TransferAssetResponseDTO.TransferredWallet(fromWallet.getWalletId(), fromWallet.getBalance(), fromWallet.getUpdatedTime()),
                new TransferAssetResponseDTO.TransferredWallet(toWallet.getWalletId(), toWallet.getBalance(), toWallet.getUpdatedTime()), amount);
    }
}

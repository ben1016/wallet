package ledger.wallet.service;

import ledger.wallet.dto.TransferAssetResponseDTO;
import ledger.wallet.dto.TransferRequestDetails;
import java.util.ArrayList;
import java.util.List;

public interface TransferAssetService {
    ArrayList<TransferAssetResponseDTO> transferAsset(final List<TransferRequestDetails> transferRequestDetails);
}

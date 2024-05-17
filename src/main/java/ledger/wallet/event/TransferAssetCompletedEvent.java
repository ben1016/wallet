package ledger.wallet.event;

import ledger.wallet.dto.TransferAssetResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransferAssetCompletedEvent {
    private String status;
    private TransferAssetResponseDTO.TransferredWallet transferredFromWallet;
    private TransferAssetResponseDTO.TransferredWallet transferredToWallet;
}

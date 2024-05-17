package ledger.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class TransferAssetCommand {
    private List<TransferRequestDetails> details;

    long requestedDateTime;
}

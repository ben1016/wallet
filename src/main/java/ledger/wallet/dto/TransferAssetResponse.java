package ledger.wallet.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransferAssetResponse {

    @JsonProperty("status")
    private String status;
}

package ledger.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class TransferRequestDetails {
    private String fromWalletId;
    private String toWalletId;
    private BigDecimal amount;
}

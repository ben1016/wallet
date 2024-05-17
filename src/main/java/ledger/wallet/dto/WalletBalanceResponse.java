package ledger.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class WalletBalanceResponse {
    private String walletId;
    private BigDecimal balance;
    private String balanceUpdatedTimestamp;
}

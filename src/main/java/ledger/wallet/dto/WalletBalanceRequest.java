package ledger.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WalletBalanceRequest {
    private String walletId;
    private String balanceAsOfTimestamp;
}

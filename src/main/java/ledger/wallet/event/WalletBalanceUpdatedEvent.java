package ledger.wallet.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class WalletBalanceUpdatedEvent {

    private String walletId;
    private BigDecimal balance;
    private long balanceUpdatedTimestamp;
}

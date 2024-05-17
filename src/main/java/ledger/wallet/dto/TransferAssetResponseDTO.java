package ledger.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class TransferAssetResponseDTO {
    private final TransferredWallet updatedFromWallet;
    private final TransferredWallet updatedToWallet;
    private final BigDecimal amount;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class TransferredWallet {
        String walletId;
        BigDecimal balance;
        long updatedTime;
    }
}

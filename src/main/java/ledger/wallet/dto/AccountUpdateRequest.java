package ledger.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountUpdateRequest {
    private String accountId;
    private String status;
}

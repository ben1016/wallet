package ledger.wallet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountCreateRequest {
    private String ledgerId;
    private String status;
}

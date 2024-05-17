package ledger.wallet.data.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    private @Id @GeneratedValue(strategy = GenerationType.UUID) String accountId;
    private String ledgerId;
    private String status;
    private long createdTime = Instant.now().toEpochMilli();
    private long updatedTime = Instant.now().toEpochMilli();

    public Account(String accountId, String ledgerId, String status) {
        this.accountId = accountId;
        this.ledgerId = ledgerId;
        this.status = status;
    }

    public Account(String ledgerId, String status) {
        this.ledgerId = ledgerId;
        this.status = status;
    }

    @PreUpdate
    public void preUpdate() {
        updatedTime = Instant.now().toEpochMilli();
    }
}

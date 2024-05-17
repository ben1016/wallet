package ledger.wallet.data.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {
    private @Id @GeneratedValue(strategy = GenerationType.UUID) String transactionId;
    private String accountId;
    private String walletId;
    private BigDecimal balance;
    private long createdTime = Instant.now().toEpochMilli();
    private long updatedTime = Instant.now().toEpochMilli();

    public Wallet(String accountId, String walletId, BigDecimal balance) {
        this.accountId = accountId;
        this.walletId = walletId;
        this.balance= balance;
    }

    public Wallet(String accountId, String walletId, BigDecimal balance, long updatedTime) {
        this.accountId = accountId;
        this.walletId = walletId;
        this.balance= balance;
        this.updatedTime = updatedTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wallet wallet = (Wallet) o;
        return Objects.equals(transactionId, wallet.transactionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId);
    }

    @PreUpdate
    public void preUpdate() {
        updatedTime = Instant.now().toEpochMilli();
    }
}

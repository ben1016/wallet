package ledger.wallet.event.producer;

import ledger.wallet.event.WalletBalanceUpdatedEvent;
import org.springframework.stereotype.Service;

@Service
public class WalletBalanceUpdatedEventProducer {

    public void sendWalletBalanceUpdatedEvent(WalletBalanceUpdatedEvent event) {
        System.out.println("emitting the WalletBalanceUpdatedEvent: " + event);
    }
}

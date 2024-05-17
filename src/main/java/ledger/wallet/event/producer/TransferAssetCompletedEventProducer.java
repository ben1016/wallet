package ledger.wallet.event.producer;

import ledger.wallet.event.TransferAssetCompletedEvent;
import org.springframework.stereotype.Service;

@Service
public class TransferAssetCompletedEventProducer {

    public void sendTransferMoneyCompletedEvent(TransferAssetCompletedEvent transferAssetCompletedEvent) {
        System.out.println("emitting the TransferAssetCompletedEvent: " + transferAssetCompletedEvent);
    }
}

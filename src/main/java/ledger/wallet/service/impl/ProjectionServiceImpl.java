package ledger.wallet.service.impl;

import ledger.wallet.data.entity.Wallet;
import ledger.wallet.data.entity.WalletView;
import ledger.wallet.data.repository.WalletReadRepository;
import ledger.wallet.service.ProjectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectionServiceImpl implements ProjectionService {

    @Autowired
    private WalletReadRepository walletReadRepository;

    @Override
    public void updateProjection(Wallet wallet) {
        WalletView walletView = new WalletView(wallet.getTransactionId(), wallet.getWalletId(),
                wallet.getBalance(), wallet.getUpdatedTime());
        walletReadRepository.save(walletView);
    }
}

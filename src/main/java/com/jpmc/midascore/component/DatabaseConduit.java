package com.jpmc.midascore.component;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRecordRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Component
@Service
public class DatabaseConduit {
    private final UserRepository userRepository;
    private final TransactionRecordRepository transactionRecordRepository;
    private final IncentiveComponent incentiveComponent;

    @Autowired
    public DatabaseConduit(UserRepository userRepository, TransactionRecordRepository transactionRecordRepository, IncentiveComponent incentiveComponent) {
        this.userRepository = userRepository;
        this.transactionRecordRepository = transactionRecordRepository;
        this.incentiveComponent = incentiveComponent;
    }
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConduit.class);

    public void save(UserRecord userRecord) {
        userRepository.save(userRecord);
    }

    public void TransactionValidation(Transaction transaction) {
        UserRecord sender = userRepository.findById(transaction.getSenderId());
        UserRecord recipient = userRepository.findById(transaction.getRecipientId());

        if (sender != null && recipient != null) {
            if (sender.getBalance() >= transaction.getAmount()) {
                float incentiveAmount = incentiveComponent.getIncentive(transaction);
                sender.setBalance(sender.getBalance() - transaction.getAmount());
                recipient.setBalance(recipient.getBalance() + transaction.getAmount());
                save(sender);
                save(recipient);
            }
        }
    }

}

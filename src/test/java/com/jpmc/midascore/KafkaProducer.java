package com.jpmc.midascore;

import com.jpmc.midascore.foundation.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class KafkaProducer {
    private final String topic;
    private final KafkaTemplate<String, Transaction> kafkaTemplate;

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    public KafkaProducer(@Value("${general.kafka-topic}") String topic, KafkaTemplate<String, Transaction> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String transactionLine) {
        logger.info("Transaction line: " + transactionLine);
        String nextTransaction;
        while (transactionLine != null) {
            int separatorIndex = transactionLine.indexOf("\n");
            if (separatorIndex != -1) {
                nextTransaction = transactionLine.substring(0, separatorIndex).trim();
                transactionLine = transactionLine.substring(separatorIndex).trim();
            } else {
                nextTransaction = transactionLine.trim();
                transactionLine = null;
            }
            if (nextTransaction.isEmpty()) continue;
            logger.info("nextTransaction: " + nextTransaction);
            String[] transactionData = nextTransaction.split(", ");
            // transactionData[0] = Arrays.toString(transactionData[0].split(", "));
            transactionData[1] = transactionData[1].replaceAll("[^0-9]", "");
            transactionData[2] = transactionData[2].replaceAll("[^0-9.]", "");
            kafkaTemplate.send(topic, new Transaction(Long.parseLong(transactionData[0]), Long.parseLong(transactionData[1]), Float.parseFloat(transactionData[2])));
        }
    }
}
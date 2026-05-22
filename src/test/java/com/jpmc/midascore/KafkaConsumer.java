package com.jpmc.midascore;

import com.jpmc.midascore.foundation.Transaction;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
//    @Bean
//    public RecordMessageConverter converter() {
//        return new JsonMessageConverter();
//    }

    @KafkaListener(topics = "${general.kafka-topic}", groupId = "midas")
    public void listen(Transaction in) {
        System.out.println("Received: " + in.getAmount());
    }
}
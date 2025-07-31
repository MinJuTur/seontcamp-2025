package com.example.demo.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = "user-joined", groupId = "demo-group")
    public void handleUserJoined(ConsumerRecord<String, String> record) {
        String message = record.value();
        System.out.println("kafaka 메지지 수신됨: " + message);
    }
}

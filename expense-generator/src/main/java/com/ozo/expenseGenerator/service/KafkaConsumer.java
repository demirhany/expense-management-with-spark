package com.ozo.expenseGenerator.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "${kafka.topic}", groupId = "expense-generator")
    public void listen(String message) {
        System.out.println("Message received from kafka: " + message);
    }

    /*
    * "/opt/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic expense-topic --from-beginning"
    * While sending message to topic expense-topic, the message is received by the consumer on kafka-console-consumer.sh
    * If the command above is executed on kafka bash in docker container(kafka bash can be opened with "docker exec -it broker bash"), the message is received by the consumer will be listed on the terminal.
    * */
}

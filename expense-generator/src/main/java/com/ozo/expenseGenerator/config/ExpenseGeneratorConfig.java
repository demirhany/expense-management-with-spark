package com.ozo.expenseGenerator.config;

import com.ozo.expenseGenerator.model.Expense;
import com.ozo.expenseGenerator.service.ExpenseService;
import com.ozo.expenseGenerator.util.ExpenseConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.UUID;

@Configuration
@EnableScheduling
public class ExpenseGeneratorConfig {
    private final ExpenseService expenseService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.topic}")
    private String topic;

    public ExpenseGeneratorConfig(ExpenseService expenseService, KafkaTemplate<String, String> kafkaTemplate) {
        this.expenseService = expenseService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedRate = 1000)
    public void run() {
        Expense expense = expenseService.generateRandomExpense();
        UUID uuid = UUID.randomUUID();
        kafkaTemplate.send(topic + expense.getUserId(), ExpenseConverter.toJson(uuid.toString(), expense));
        System.out.println("Message sent to kafka: " + expense);
    }
}

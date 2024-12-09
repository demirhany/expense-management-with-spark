package com.ozo.expenseGenerator.config;

import com.ozo.expenseGenerator.model.Expense;
import com.ozo.expenseGenerator.service.ExpenseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

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
        kafkaTemplate.send(topic, expense.toString());
        System.out.println("Message sent to kafka: " + expense);
    }
}

package com.ozo.expenseGenerator.config;

import com.ozo.expenseGenerator.service.EmployeeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ExpenseGeneratorConfig {
    private final EmployeeService employeeService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.topic}")
    private String topic;

    public ExpenseGeneratorConfig(EmployeeService employeeService, KafkaTemplate<String, String> kafkaTemplate) {
        this.employeeService = employeeService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedRate = 1000)
    public void run() {
        Long empno = employeeService.getARandomEmpno();
        String data = empno.toString() + " " + employeeService.getEmployeeNameByEmpno(empno);
        kafkaTemplate.send(topic, data);
        System.out.println("Message sent to kafka: " + data);
//        System.out.print(empno);
//        System.out.println(" " + employeeService.getEmployeeNameByEmpno(empno));
    }
}

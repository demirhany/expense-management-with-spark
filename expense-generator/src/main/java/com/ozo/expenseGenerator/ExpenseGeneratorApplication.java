package com.ozo.expenseGenerator;

import com.ozo.expenseGenerator.service.EmployeeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class ExpenseGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpenseGeneratorApplication.class, args);
    }

}

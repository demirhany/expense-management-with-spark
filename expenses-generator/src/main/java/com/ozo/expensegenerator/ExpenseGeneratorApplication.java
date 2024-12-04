package com.ozo.expensegenerator;

import com.ozo.expensegenerator.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor
public class ExpenseGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseGeneratorApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(EmployeeService employeeService) {
		return args -> {
			employeeService.getAllUserNames().forEach(
					System.out::println
			);
		};
	}
}

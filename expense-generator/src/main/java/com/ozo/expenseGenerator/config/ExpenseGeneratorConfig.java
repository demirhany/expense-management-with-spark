package com.ozo.expenseGenerator.config;

import com.ozo.expenseGenerator.service.EmployeeService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ExpenseGeneratorConfig {
    private final EmployeeService employeeService;

    public ExpenseGeneratorConfig(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Scheduled(fixedRate = 1000)
    public void run() {
        Long empno = employeeService.getARandomEmpno();
        System.out.print(empno);
        System.out.println(" " + employeeService.getEmployeeNameByEmpno(empno));
    }
}

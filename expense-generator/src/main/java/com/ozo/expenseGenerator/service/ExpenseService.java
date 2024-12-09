package com.ozo.expenseGenerator.service;

import com.ozo.expenseGenerator.constant.ExpenseDescriptions;
import com.ozo.expenseGenerator.constant.ExpenseTypes;
import com.ozo.expenseGenerator.model.Expense;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final EmployeeService employeeService;

    public Expense generateRandomExpense() {
        // employee number
        Long employeeNumber = employeeService.getARandomEmpno();

        // date time
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        String dateTime = localDateTime.format(formatter);

        // description
        int randomDescriptionIndex = (int) (Math.random() * ExpenseDescriptions.values().length);
        String description = ExpenseDescriptions.values()[randomDescriptionIndex].toString();

        // type
        int randomTypeIndex = (int) (Math.random() * ExpenseTypes.values().length);
        String type = ExpenseTypes.values()[randomTypeIndex].toString();

        // count
        int count = (int) (Math.random() * 10) + 1;

        // payment
        float payment = (float) (Math.random() * 100) + 1;
        payment = Math.round(payment * 100) / 100f;

        return Expense.builder()
                .userId(employeeNumber)
                .dateTime(dateTime)
                .description(description)
                .type(type)
                .count(count)
                .payment(payment)
                .build();
    }
}

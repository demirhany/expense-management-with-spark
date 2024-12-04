package com.ozo.expenseGenerator.service;

import com.ozo.expenseGenerator.model.Employee;
import com.ozo.expenseGenerator.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllUsers() {
        return employeeRepository.findAll();
    }

    public List<String> getAllEmployeesNames() {
        return employeeRepository.findAll().stream().map(Employee::getEname).toList();
    }

    public String getEmployeeNameByEmpno(Long empno) {
        return employeeRepository.findEmployeeByEmpno(empno).getEname();
    }

    public Long getARandomEmpno() {
        final List<Long> empnos =  employeeRepository.findAllEmpno();

        final Random random = new Random();
        final int randomIndex = random.nextInt(0, empnos.size());

        return empnos.get(randomIndex);
    }
}

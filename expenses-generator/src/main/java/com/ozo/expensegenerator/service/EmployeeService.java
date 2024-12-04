package com.ozo.expensegenerator.service;

import com.ozo.expensegenerator.model.Employee;
import com.ozo.expensegenerator.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<String> getAllUserNames() {
        return employeeRepository.findAll().stream().map(Employee::getEname).toList();
    }


}

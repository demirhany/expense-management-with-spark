package com.ozo.bigdatahadoopspring.config;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.ozo.bigdatahadoopspring.entity.Department;
import com.ozo.bigdatahadoopspring.entity.Employee;
import com.ozo.bigdatahadoopspring.repository.DepartmentRepository;
import com.ozo.bigdatahadoopspring.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DatabaseSeeder {
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Value(value = "classpath:emp.csv")
    private Resource empResource;

    @Value(value = "classpath:dept.csv")
    private Resource deptResource;

    @Bean
    CommandLineRunner databaseSeederBean() {
        return args -> {
            List<String[]> rawEmployees = readResource(empResource.getURL().getPath());
            List<String[]> rawDepartments = readResource(deptResource.getURL().getPath());

            for (String[] rawDepartment : rawDepartments) {
                Long deptno = Long.parseLong(rawDepartment[0]);
                if (departmentRepository.existsById(deptno)) {
                    continue;
                }

                Department department = Department.builder()
                        .deptno(deptno)
                        .dname(rawDepartment[1])
                        .loc(rawDepartment[2])
                        .build();
                departmentRepository.save(department);
            }

            for (String[] rawEmployee : rawEmployees) {
                Long empno = Long.valueOf(rawEmployee[0]);
                if (employeeRepository.existsById(empno)) {
                    continue;
                }

                Long deptno = Long.valueOf(rawEmployee[7]);
                Department department = departmentRepository.getReferenceById(deptno);

                String rawMgr = rawEmployee[3];
                Long mgr = rawMgr.isEmpty() ? null : Long.valueOf(rawMgr);

                String rawComm = rawEmployee[6];
                Long comm = rawComm.isEmpty() ? null : Long.valueOf(rawComm);

                Employee employee = Employee.builder()
                        .empno(empno)
                        .ename(rawEmployee[1])
                        .job(rawEmployee[2])
                        .mgr(mgr)
                        .hiredate(rawEmployee[4])
                        .sal(Long.valueOf(rawEmployee[5]))
                        .comm(comm)
                        .isDeleted(false)
                        .deptno(department)
                        .img(rawEmployee[8])
                        .build();
                employeeRepository.save(employee);
            }
        };
    }

    private List<String[]> readResource(String fileName) {
        try {
            FileReader fileReader = new FileReader(fileName);
            try (CSVReader csvReader = new CSVReaderBuilder(fileReader).withSkipLines(1).build()) {
                return csvReader.readAll();
            } catch (IOException | CsvException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

package com.example.big_data_aws.controller;

import com.example.big_data_aws.dto.EmployeeCreateReq;
import com.example.big_data_aws.dto.EmployeeDto;
import com.example.big_data_aws.dto.EmployeeUpdateReq;
import com.example.big_data_aws.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {
    private final MainService mainService;

    @GetMapping("/employee/{empno}")
    public ResponseEntity<EmployeeDto> getEmployeeByEmpno(@PathVariable("empno") Long empno) {
        return ResponseEntity.ok(mainService.getEmployeeByEmpno(empno));
    }

    @PutMapping("/employee/{empno}")
    public ResponseEntity<EmployeeDto> updateEmployeeByEmpno(@PathVariable("empno") Long empno, @RequestBody EmployeeUpdateReq employeeUpdateReq) {
        return ResponseEntity.ok(mainService.updateEmployeeByEmpno(empno, employeeUpdateReq));
    }

    @DeleteMapping("/employee/{empno}")
    public ResponseEntity<String> deleteEmployeeByEmpno(@PathVariable("empno") Long empno) {
        log.info("Deleting employee with empno: {}", empno);
        mainService.deleteEmployeeByEmpno(empno);
        return ResponseEntity.ok("Employee with empno: " + empno + " deleted successfully");
    }

    @PostMapping("/employee")
    public ResponseEntity<String> createEmployee(@RequestBody EmployeeCreateReq employeeCreateReq) {
        mainService.createEmployee(employeeCreateReq);
        return ResponseEntity.ok("Employee created successfully with empno: " + employeeCreateReq.getEmpno());
    }
}

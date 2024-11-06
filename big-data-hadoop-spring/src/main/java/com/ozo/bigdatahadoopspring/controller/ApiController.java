package com.ozo.bigdatahadoopspring.controller;

import com.ozo.bigdatahadoopspring.dto.EmployeeCreateReq;
import com.ozo.bigdatahadoopspring.dto.EmployeeDto;
import com.ozo.bigdatahadoopspring.dto.EmployeeUpdateReq;
import com.ozo.bigdatahadoopspring.service.MainService;
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

    @PostMapping("/employee")
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeCreateReq employeeCreateReq) {
        log.info("employee create: {}", employeeCreateReq);
        EmployeeDto result = mainService.createEmployee(employeeCreateReq);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/employee/{empno}")
    public ResponseEntity<EmployeeDto> updateEmployeeByEmpno(@PathVariable("empno") Long empno, @RequestBody EmployeeUpdateReq employeeUpdateReq) {
        return ResponseEntity.ok(mainService.updateEmployeeByEmpno(empno, employeeUpdateReq));
    }

    @DeleteMapping("/employee/{empno}")
    public ResponseEntity<String> deleteEmployeeByEmpno(@PathVariable("empno") Long empno) {
        mainService.deleteEmployeeByEmpno(empno);

        return ResponseEntity.ok("Employee with empno: " + empno + " deleted successfully");
    }
}

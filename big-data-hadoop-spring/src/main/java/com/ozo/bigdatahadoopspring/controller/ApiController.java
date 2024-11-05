package com.ozo.bigdatahadoopspring.controller;

import com.ozo.bigdatahadoopspring.dto.EmployeeDto;
import com.ozo.bigdatahadoopspring.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<EmployeeDto> updateEmployeeByEmpno(@PathVariable("empno") Long empno, @RequestBody EmployeeDto employeeDto) {
        return ResponseEntity.ok(mainService.updateEmployeeByEmpno(empno, employeeDto));
    }

    @DeleteMapping("/employee/{empno}")
    public ResponseEntity<String> deleteEmployeeByEmpno(@PathVariable("empno") Long empno) {
        mainService.deleteEmployeeByEmpno(empno);
        return ResponseEntity.ok("Employee with empno: " + empno + " deleted successfully");
    }
}

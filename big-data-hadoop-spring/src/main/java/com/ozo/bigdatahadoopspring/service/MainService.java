package com.ozo.bigdatahadoopspring.service;

import com.ozo.bigdatahadoopspring.dto.EmployeeDto;
import com.ozo.bigdatahadoopspring.dto.EmployeeWithBase64PhotoDto;
import com.ozo.bigdatahadoopspring.dto.EmployeeWithUrlPhotoDto;
import com.ozo.bigdatahadoopspring.entity.Employee;
import com.ozo.bigdatahadoopspring.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MainService {
    private final EmployeeRepository employeeRepository;
    private final PhotoService photoService;

    public List<EmployeeWithBase64PhotoDto> getAllEmployeesWithBase64Photos() {
        List<Employee> fetchedEmployees = employeeRepository.findAllAndIsDeletedFalse();
        List<EmployeeWithBase64PhotoDto> employeeWithPhotoDtoList = new ArrayList<>();
        for (Employee employee : fetchedEmployees) {
            EmployeeWithBase64PhotoDto employeeWithPhotoDto = _getEmployeeWithBase64PhotoDto(employee);
            employeeWithPhotoDtoList.add(employeeWithPhotoDto);
        }
        return employeeWithPhotoDtoList;
    }

    public List<EmployeeWithUrlPhotoDto> getAllEmployeesWithUrlPhotos() {
        List<Employee> fetchedEmployees = employeeRepository.findAll();
        List<EmployeeWithUrlPhotoDto> employeeWithPhotoDtoList = new ArrayList<>();
        for (Employee employee : fetchedEmployees) {
            EmployeeWithUrlPhotoDto employeeWithPhotoDto = _getEmployeeWithUrlPhotoDto(employee);
            employeeWithPhotoDtoList.add(employeeWithPhotoDto);
        }
        return employeeWithPhotoDtoList;
    }

    private EmployeeWithBase64PhotoDto _getEmployeeWithBase64PhotoDto(Employee employee) {
        EmployeeWithBase64PhotoDto employeeWithPhotoDto = new EmployeeWithBase64PhotoDto(employee);
        try {
            String photoBase64 = photoService.getPhotoAsBase64(employee.getImg());
            employeeWithPhotoDto.setBase64Photo(photoBase64);
        } catch (Exception e) {
            log.error("Failed to get photo for employee with empno: {}", employee.getEmpno(), e);
        }
        return employeeWithPhotoDto;
    }

    private EmployeeWithUrlPhotoDto _getEmployeeWithUrlPhotoDto(Employee employee) {
        EmployeeWithUrlPhotoDto employeeWithPhotoDto = new EmployeeWithUrlPhotoDto(employee);
        try {
            String photoUrl = "/images/" + employee.getImg();
            employeeWithPhotoDto.setPhotoUrl(photoUrl);
        } catch (Exception e) {
            log.error("Failed to get photo for employee with empno: {}", employee.getEmpno(), e);
        }
        return employeeWithPhotoDto;
    }

    public EmployeeDto getEmployeeByEmpno(Long empno) {
        return new EmployeeDto(employeeRepository.findById(empno).orElseThrow(
                () -> new RuntimeException("Employee with empno " + empno + " not found")
        ));
    }

    public void deleteEmployeeByEmpno(Long empno) {
        Employee employee = employeeRepository.findById(empno).orElseThrow(
                () -> new RuntimeException("Employee with empno " + empno + " not found")
        );
        employee.setIsDeleted(true);
        employeeRepository.save(employee);
    }

    public EmployeeDto updateEmployeeByEmpno(Long empno, EmployeeDto employeeDto) {
        Employee employee = employeeRepository.findById(empno).orElseThrow(
                () -> new RuntimeException("Employee with empno " + empno + " not found")
        );
        employee.setEname(employeeDto.getEname());
        employee.setJob(employeeDto.getJob());
        employee.setMgr(employeeDto.getMgr());
        employee.setHiredate(employeeDto.getHiredate());
        employee.setSal(employeeDto.getSal());
        employee.setComm(employeeDto.getComm());
        employeeRepository.save(employee);
        return new EmployeeDto(employee);
    }
}

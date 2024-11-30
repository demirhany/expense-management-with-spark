package com.example.big_data_aws.service;

import com.example.big_data_aws.dto.EmployeeCreateReq;
import com.example.big_data_aws.dto.EmployeeDto;
import com.example.big_data_aws.dto.EmployeeUpdateReq;
import com.example.big_data_aws.dto.EmployeeWithUrlPhotoDto;
import com.example.big_data_aws.entity.Department;
import com.example.big_data_aws.entity.Employee;
import com.example.big_data_aws.interfaces.BucketService;
import com.example.big_data_aws.repository.DepartmentRepository;
import com.example.big_data_aws.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MainService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final BucketService bucketService;

    public List<EmployeeWithUrlPhotoDto> getAllEmployeesWithUrlPhotos() {
        List<Employee> fetchedEmployees = employeeRepository.findAllAndIsDeletedFalse();
        List<EmployeeWithUrlPhotoDto> employeeWithPhotoDtoList = new ArrayList<>();
        for (Employee employee : fetchedEmployees) {
            EmployeeWithUrlPhotoDto employeeWithPhotoDto = _getEmployeeWithUrlPhotoDto(employee);
            employeeWithPhotoDtoList.add(employeeWithPhotoDto);
        }
        return employeeWithPhotoDtoList;
    }

    private EmployeeWithUrlPhotoDto _getEmployeeWithUrlPhotoDto(Employee employee) {
        EmployeeWithUrlPhotoDto employeeWithPhotoDto = new EmployeeWithUrlPhotoDto(employee);
        try {
            String photoUrl = bucketService.getImagePresignedUrl("big-data-project-ahmetcaggn", "images/", employee.getImg()).toString();
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
        log.info("Employee exists with empno: {} and deleting", empno);
        employee.setIsDeleted(true);
        employeeRepository.save(employee);
    }

    public EmployeeDto updateEmployeeByEmpno(Long empno, EmployeeUpdateReq employeeUpdateReq) {
        Employee employee = employeeRepository.findById(empno).orElseThrow(
                () -> new RuntimeException("Employee with empno " + empno + " not found")
        );
        employee.setEname(employeeUpdateReq.getEname());
        employee.setJob(employeeUpdateReq.getJob());
        employee.setMgr(employeeUpdateReq.getMgr());
        employee.setHiredate(employeeUpdateReq.getHiredate());
        employee.setSal(employeeUpdateReq.getSal());
        employee.setComm(employeeUpdateReq.getComm());

        if (employeeUpdateReq.getBase64Image() == null || employeeUpdateReq.getBase64Image().isEmpty()) {
            if(employee.getImg() == null || employee.getImg().isEmpty()) {
                employee.setImg("placeholder.png");
            }
        } else {
            try {
                String imageUUID = UUID.randomUUID().toString();
//                bucketService.putObjectIntoBucket("big-data-project-ahmetcaggn", "images/", employeeCreateReq.getBase64Image());
                bucketService.uploadBase64Image("big-data-project-ahmetcaggn", "images/"+imageUUID, employeeUpdateReq.getBase64Image());

                employee.setImg(imageUUID);
            } catch (Exception e) {
                employee.setImg("placeholder.png");
            }
        }
        employeeRepository.save(employee);
        return new EmployeeDto(employee);
    }

    public void createEmployee(EmployeeCreateReq employeeCreateReq) {
        Department department = departmentRepository.findByName(employeeCreateReq.getDept()).orElseThrow(
                () -> new RuntimeException("Department with name " + employeeCreateReq.getDept() + " not found")
        );
        if (employeeRepository.existsById(employeeCreateReq.getEmpno())) {
            throw new RuntimeException("Employee with empno " + employeeCreateReq.getEmpno() + " already exists");
        }


        Employee employee = new Employee();
        if (employeeCreateReq.getBase64Image() == null || employeeCreateReq.getBase64Image().isEmpty()) {
            employee.setImg("placeholder.png");
            log.info("Base64 image is empty for employee with empno: {}", employeeCreateReq.getEmpno());
        } else {
            try {
                String imageUUID = UUID.randomUUID().toString();
//                bucketService.putObjectIntoBucket("big-data-project-ahmetcaggn", "images/", employeeCreateReq.getBase64Image());
                bucketService.uploadBase64Image("big-data-project-ahmetcaggn", "images/"+imageUUID, employeeCreateReq.getBase64Image());

                employee.setImg(imageUUID);
            } catch (Exception e) {
                employee.setImg("placeholder.png");
                log.error("Failed to upload image for employee with empno: {}", employeeCreateReq.getEmpno(), e);
            }
        }
        employee.setEmpno(employeeCreateReq.getEmpno());
        employee.setEname(employeeCreateReq.getEname());
        employee.setJob(employeeCreateReq.getJob());
        employee.setMgr(employeeCreateReq.getMgr());
        employee.setHiredate(employeeCreateReq.getHiredate());
        employee.setSal(employeeCreateReq.getSal());
        employee.setComm(employeeCreateReq.getComm());
        employee.setDeptno(department);
        employee.setIsDeleted(false);
        employeeRepository.save(employee);
    }
}

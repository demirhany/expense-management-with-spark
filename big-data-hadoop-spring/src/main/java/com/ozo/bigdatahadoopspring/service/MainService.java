package com.ozo.bigdatahadoopspring.service;

import com.ozo.bigdatahadoopspring.dto.EmployeeWithPhotoDto;
import com.ozo.bigdatahadoopspring.entity.Employee;
import com.ozo.bigdatahadoopspring.repository.DepartmentRepository;
import com.ozo.bigdatahadoopspring.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MainService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public List<EmployeeWithPhotoDto> getAllEmployeesWithPhotos() {
        List<Employee> fetchedEmployees = employeeRepository.findAll();
        List<EmployeeWithPhotoDto> employeeWithPhotoDtoList = new ArrayList<>();
        for (Employee employee : fetchedEmployees) {
            EmployeeWithPhotoDto employeeWithPhotoDto = new EmployeeWithPhotoDto(employee);
//            byte [] photo = null;
////            try {
////                photo = hadoopService.getPhotoFromHadoop(employee.getImg());
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
//            String photoBase64 = (photo != null) ? Base64.getEncoder().encodeToString(photo) : null;
//            employeeWithPhotoDto.setBase64Photo(photoBase64);
            log.info("employeeWithPhotoDto: " + employeeWithPhotoDto.getEname());
            employeeWithPhotoDtoList.add(employeeWithPhotoDto);
        }
        return employeeWithPhotoDtoList;
    }
}

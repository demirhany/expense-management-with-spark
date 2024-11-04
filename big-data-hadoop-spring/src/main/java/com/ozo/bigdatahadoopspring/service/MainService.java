package com.ozo.bigdatahadoopspring.service;

import com.ozo.bigdatahadoopspring.dto.EmployeeWithPhotoDto;
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

    public List<EmployeeWithPhotoDto> getAllEmployeesWithPhotos() {
        List<Employee> fetchedEmployees = employeeRepository.findAll();
        List<EmployeeWithPhotoDto> employeeWithPhotoDtoList = new ArrayList<>();
        for (Employee employee : fetchedEmployees) {
            EmployeeWithPhotoDto employeeWithPhotoDto = new EmployeeWithPhotoDto(employee);
            try{
                String photoBase64 = photoService.getPhoto(employee.getImg());
                employeeWithPhotoDto.setBase64Photo(photoBase64);
            } catch (Exception e) {
                log.error("Failed to get photo for employee with empno: " + employee.getEmpno(), e);
            }
            employeeWithPhotoDtoList.add(employeeWithPhotoDto);
        }
        return employeeWithPhotoDtoList;
    }
}

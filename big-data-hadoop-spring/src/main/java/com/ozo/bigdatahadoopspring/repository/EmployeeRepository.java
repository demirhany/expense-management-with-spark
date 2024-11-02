package com.ozo.bigdatahadoopspring.repository;

import com.ozo.bigdatahadoopspring.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}

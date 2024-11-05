package com.ozo.bigdatahadoopspring.repository;

import com.ozo.bigdatahadoopspring.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT e FROM Employee e WHERE e.isDeleted = false ORDER BY e.empno ASC")
    List<Employee> findAllAndIsDeletedFalse();
}

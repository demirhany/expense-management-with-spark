package com.ozo.expenseGenerator.repository;

import com.ozo.expenseGenerator.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT e.empno FROM Employee e")
    List<Long> findAllEmpno();

    Employee findEmployeeByEmpno(Long empno);
}

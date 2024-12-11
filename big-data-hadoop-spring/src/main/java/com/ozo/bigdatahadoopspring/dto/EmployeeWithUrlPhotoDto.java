package com.ozo.bigdatahadoopspring.dto;

import com.ozo.bigdatahadoopspring.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeWithUrlPhotoDto {
    private Long empno;
    private String ename;
    private String job;
    private Long mgr;
    private String hiredate;
    private Long sal;
    private Long comm;
    private String dept;
    private Float totalExpense;
    private String photoUrl;

    public EmployeeWithUrlPhotoDto(Employee employee) {
        this.empno = employee.getEmpno();
        this.ename = employee.getEname();
        this.job = employee.getJob();
        this.mgr = employee.getMgr();
        this.hiredate = employee.getHiredate();
        this.sal = employee.getSal();
        this.comm = employee.getComm();
        this.dept = employee.getDeptno().getDname();
    }
    public EmployeeWithUrlPhotoDto(Employee employee, Float totalExpense) {
        this.empno = employee.getEmpno();
        this.ename = employee.getEname();
        this.job = employee.getJob();
        this.mgr = employee.getMgr();
        this.hiredate = employee.getHiredate();
        this.sal = employee.getSal();
        this.comm = employee.getComm();
        this.dept = employee.getDeptno().getDname();
        this.totalExpense = totalExpense;
    }
}

package com.example.big_data_aws.dto;

import com.example.big_data_aws.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeWithBase64PhotoDto {
    private Long empno;
    private String ename;
    private String job;
    private Long mgr;
    private String hiredate;
    private Long sal;
    private Long comm;
    private String dept;
    private String Base64Photo;

    public EmployeeWithBase64PhotoDto(Employee employee) {
        this.empno = employee.getEmpno();
        this.ename = employee.getEname();
        this.job = employee.getJob();
        this.mgr = employee.getMgr();
        this.hiredate = employee.getHiredate();
        this.sal = employee.getSal();
        this.comm = employee.getComm();
        this.dept = employee.getDeptno().getDname();
    }
}

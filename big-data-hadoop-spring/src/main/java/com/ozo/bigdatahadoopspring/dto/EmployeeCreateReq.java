package com.ozo.bigdatahadoopspring.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeCreateReq {
    private Long empno;
    private String ename;
    private String job;
    private Long mgr;
    private String hiredate;
    private Long sal;
    private Long comm;
    private Long dept;
    private String imageBase64;
}

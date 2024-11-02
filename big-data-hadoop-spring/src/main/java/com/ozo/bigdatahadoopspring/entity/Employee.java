package com.ozo.bigdatahadoopspring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "emp", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {
    @Id
    Long empno;

    String ename;
    String job;
    Long mgr;
    Date hiredate;
    Long sal;
    Long comm;

    @ManyToOne
    @JoinColumn(name = "deptno")
    Department deptno;

    String img;
}

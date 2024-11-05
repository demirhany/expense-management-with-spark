package com.ozo.bigdatahadoopspring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    String hiredate;

    Long sal;

    Long comm;

    @Column(nullable = false, columnDefinition = "boolean default false")
    Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "deptno")
    Department deptno;

    String img;
}

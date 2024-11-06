package com.ozo.bigdatahadoopspring.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "emp", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
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

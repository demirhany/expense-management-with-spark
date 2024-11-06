package com.ozo.bigdatahadoopspring.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "dept", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Department {
    @Id
    Long deptno;

    String dname;

    String loc;
}

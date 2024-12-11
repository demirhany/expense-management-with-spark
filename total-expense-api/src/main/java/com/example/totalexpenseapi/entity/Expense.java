package com.example.totalexpenseapi.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Builder
@ToString
@AllArgsConstructor
@Setter
public class Expense implements Serializable {
    private String id;
    private Long userid;
    private String datetime;
    private String description;
    private String type;
    private Integer count;
    private Float payment;
}
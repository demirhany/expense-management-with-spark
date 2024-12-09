package com.ozo.expenseGenerator.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@JsonSerialize
@ToString
public class Expense {
    private Long userId;
    private String dateTime;
    private String description;
    private String type;
    private Integer count;
    private Float payment;
}

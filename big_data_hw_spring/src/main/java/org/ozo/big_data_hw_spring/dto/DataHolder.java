package org.ozo.big_data_hw_spring.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@Getter
public class DataHolder {
    private double value;
}

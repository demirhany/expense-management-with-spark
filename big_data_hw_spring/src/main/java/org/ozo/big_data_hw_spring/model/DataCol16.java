package org.ozo.big_data_hw_spring.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class DataCol16 {
    List<String> data;

    @Override
    public String toString() {
        return "DataCol16{" +
                "data=" + data.toString() +
                '}';
    }
}
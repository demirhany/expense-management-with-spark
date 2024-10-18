package org.ozo.big_data_hw_spring.controller;

import lombok.AllArgsConstructor;
import org.ozo.big_data_hw_spring.model.DataCol16;
import org.ozo.big_data_hw_spring.model.DataHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.function.Function;

@Controller
@RequestMapping("/plot")
@AllArgsConstructor
public class PlotController {
    private final Function<DataHolder, String> plotFunction;
    private final DataCol16 dataset;

    private static int counter = 0;

    @GetMapping(produces = "image/svg+xml")
    public ResponseEntity<String> plot() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Refresh", "1");

        // get the value from dataset
        final double x = Double.parseDouble(dataset.getData().get(counter));
        counter = (counter + 1) % dataset.getData().size();

        // generate the svg
        String svg;
        synchronized (plotFunction) {
            svg = plotFunction.apply(DataHolder.builder().value(x).build());
        }

        return new ResponseEntity<>(svg, responseHeaders, HttpStatus.OK);
    }
}

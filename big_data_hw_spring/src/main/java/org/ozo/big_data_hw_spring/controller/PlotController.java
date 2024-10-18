package org.ozo.big_data_hw_spring.controller;

import lombok.AllArgsConstructor;
import org.ozo.big_data_hw_spring.dto.DataHolder;
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

    // TODO: remove static value (currently this is from example project)
    public static double x = 0;

    // TODO: update the function (currently this is from example project)
    @GetMapping(produces = "image/svg+xml")
    public ResponseEntity<String> plot() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.RETRY_AFTER, "1");

        // generate the svg
        String svg;
        synchronized (plotFunction) {
            svg = plotFunction.apply(DataHolder.builder().value(10 * Math.sin(x)).build());
        }

        // update static value
        x += 0.1;
        x %= 2 * Math.PI;

        return new ResponseEntity<>(svg, headers, HttpStatus.OK);
    }
}

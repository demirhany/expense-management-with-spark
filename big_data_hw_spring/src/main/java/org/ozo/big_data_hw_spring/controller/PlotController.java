package org.ozo.big_data_hw_spring.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.ozo.big_data_hw_spring.dto.DataHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.function.Function;

@Controller
@RequestMapping("/plot")
@AllArgsConstructor
public class PlotController {
    private final Function<DataHolder, String> plotFunction;

    // TODO: update the function (currently this is from example project)
    @GetMapping(produces = "image/svg+xml")
    public ResponseEntity<String> plot(@CookieValue(value = "x", defaultValue = "0") final String xString,
                                       HttpServletResponse response) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Refresh", "1");

        double x = Double.parseDouble(xString);
        x += 0.1;
        x %= 2 * Math.PI;
        Cookie cookie = new Cookie("x", String.valueOf(x));
        response.addCookie(cookie);

        // generate the svg
        String svg;
        synchronized (plotFunction) {
            svg = plotFunction.apply(DataHolder.builder().value(10 * Math.sin(x)).build());
        }

        return new ResponseEntity<>(svg, responseHeaders, HttpStatus.OK);
    }
}

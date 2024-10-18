package org.ozo.big_data_hw_spring.conifguration;

import lombok.extern.slf4j.Slf4j;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.TypeLiteral;
import org.ozo.big_data_hw_spring.dto.DataHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.function.Function;

@Slf4j
@Configuration
public class AppConfiguration {
    @Value(value = "classpath:plot.R")
    private Resource plotRResource;

    @Bean
    protected Function<DataHolder, String> plotFunction(Context context) throws IOException {
        log.info("... plotFunction is loading...");

        Source source = Source.newBuilder("R", plotRResource.getURL()).build();
        final Function<DataHolder, String> result = context.eval(source).as(new TypeLiteral<>() {
        });

        log.info("plotFunction is loaded");

        return result;
    }

    @Bean
    protected Context graalVMContext() {
        return Context.newBuilder("R").allowAllAccess(true).build();
    }
}

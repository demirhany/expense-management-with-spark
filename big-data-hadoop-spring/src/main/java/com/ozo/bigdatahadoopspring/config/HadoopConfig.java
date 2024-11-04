package com.ozo.bigdatahadoopspring.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.fs.FileSystem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class HadoopConfig {
    @Value("${hadoop.url}")
    private String hadoopUrl;

    @Bean
    public FileSystem fileSystem() throws Exception {
        org.apache.hadoop.conf.Configuration configuration = new org.apache.hadoop.conf.Configuration();
        configuration.set("fs.defaultFS", hadoopUrl);
        return FileSystem.get(configuration);
    }
}

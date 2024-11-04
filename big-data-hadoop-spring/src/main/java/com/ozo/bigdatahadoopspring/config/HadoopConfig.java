package com.ozo.bigdatahadoopspring.config;

import org.apache.hadoop.fs.FileSystem;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

@Configuration
public class HadoopConfig {
    @Bean
    public FileSystem fileSystem() throws Exception {
        org.apache.hadoop.conf.Configuration configuration = new org.apache.hadoop.conf.Configuration();
        configuration.set("fs.defaultFS", "hdfs://localhost:9000");
        return FileSystem.get(configuration);
    }
}

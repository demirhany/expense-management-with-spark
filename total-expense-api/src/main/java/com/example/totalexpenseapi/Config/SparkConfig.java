package com.example.totalexpenseapi.Config;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SparkConfig {
    @Bean
    public SparkContext sparkContext() {
        SparkConf conf = new SparkConf()
                .setAppName("SparkTotalExpenseCassandra")
                .setMaster("local[2]");

        return new SparkContext(conf);
    }
}

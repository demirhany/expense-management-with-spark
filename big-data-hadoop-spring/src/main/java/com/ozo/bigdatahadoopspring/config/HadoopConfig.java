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

    @Value("${hadoop.datanode.hostname}")
    private String hadoopDatanodeHostname;

    @Bean
    public FileSystem fileSystem() throws Exception {
        log.info("Hadoop is configuring with url: {}, datanode host: {}", hadoopUrl, hadoopDatanodeHostname);

        org.apache.hadoop.conf.Configuration configuration = new org.apache.hadoop.conf.Configuration();
        configuration.set("fs.defaultFS", hadoopUrl);

        // This is necessary to avoid the connection refused exception when reaching data node
//        configuration.set("dfs.client.use.datanode.hostname", "true");
        configuration.set("dfs.datanode.hostname", hadoopDatanodeHostname);

        FileSystem result = FileSystem.get(configuration);

        log.info("Hadoop is configured with url: {}, datanode host: {}", hadoopUrl, hadoopDatanodeHostname);

        return result;
    }
}

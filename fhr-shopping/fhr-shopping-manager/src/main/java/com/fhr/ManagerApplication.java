package com.fhr;

import com.fhr.log.annotation.EnableLogAspect;
import com.fhr.properties.MinioProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author FHR
 * @Create ${DATE} ${TIME}
 * @Version 1.0
 */
@SpringBootApplication
@EnableConfigurationProperties(value = {MinioProperties.class})
@EnableScheduling
@EnableLogAspect //让Spring能扫描到common-log模块包
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class,args);
    }
}
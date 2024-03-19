package com.nageoffer.shortlink;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author Nuyoah
 * @Date 2024/3/19
 * @Description 聚合服务启动类
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(value = {
        "com.nageoffer.shortlink.project.dao.mapper",
        "com.nageoffer.shortlink.admin.dao.mapper"
})
public class AggregationServiceApplication {
  public static void main(String[] args) {
    SpringApplication.run(AggregationServiceApplication.class,args);
  }
}

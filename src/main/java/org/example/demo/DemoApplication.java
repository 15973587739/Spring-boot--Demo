package org.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 */
// 标记这是Spring Boot启动类，自动扫描当前包（org.example.demo）及其子包
@SpringBootApplication
// 额外扫描Mapper接口（如果Mapper不在启动类子包下，需要手动指定）
@MapperScan("org.example.demo.mapper")
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
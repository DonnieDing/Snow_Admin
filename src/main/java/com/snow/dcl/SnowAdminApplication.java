package com.snow.dcl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * (功能描述)
 * Snow_Admin启动类
 * @Author:Dcl_Snow
 * @Create: 2021/8/24 13:33
 * @version: 1.0.0
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
public class SnowAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(SnowAdminApplication.class, args);
    }

}

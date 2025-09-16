package com.szatc.manage;

import cn.stylefeng.roses.core.config.WebAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 
 *@Title WebstackGunsApplication.java
 *@description SpringBoot方式启动类
 *@time 2019年12月22日 下午8:47:10
 *@author Nikati
 *@version 1.0
*
 */
@EnableScheduling
@SpringBootApplication(exclude = WebAutoConfiguration.class)
public class WebstackApplication {

    private final static Logger logger = LoggerFactory.getLogger(WebstackApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(WebstackApplication.class, args);
        logger.info("Application is success!");
    }
}

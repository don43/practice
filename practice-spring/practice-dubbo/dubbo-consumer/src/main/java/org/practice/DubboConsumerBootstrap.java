package org.practice;

import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;

@EnableAutoConfiguration
public class DubboConsumerBootstrap {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Reference
    private GreetingService greetingService;

    public static void main(String[] args) {
        SpringApplication.run(DubboConsumerBootstrap.class);
    }

    @Bean
    public ApplicationRunner runner() {
        return args -> {
            while (true) {
                Thread.sleep(2000);
                try {
                    logger.info(greetingService.sayHello("don"));
                } catch (Exception ex) {
                    logger.error("rpc invoke error:", ex);
                }
            }
        };
    }
}
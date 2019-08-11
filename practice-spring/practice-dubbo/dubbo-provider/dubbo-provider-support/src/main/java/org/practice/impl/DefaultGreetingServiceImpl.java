package org.practice.impl;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;
import org.practice.GreetingService;

@Service
public class DefaultGreetingServiceImpl implements GreetingService {

    /**
     * The default value of ${dubbo.application.name} is ${spring.application.name}
     */
    @Value("${dubbo.application.name}")
    private String serviceName;

    public String sayHello(String name) {
        return String.format("[%s]: Hello, %s", serviceName, name);
    }
}
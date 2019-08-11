package org.practice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static org.practice.PkgConst.ROUTING_KEY;

@Component
@RabbitListener(queues = ROUTING_KEY)
@Slf4j
public class Receiver {

    @RabbitHandler
    public void process(String hello) {
        log.info("########## Receiver : " + hello);
    }

}
package org.practice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import static org.practice.PkgConst.ROUTING_KEY;

@Component
@Slf4j
public class Sender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        String context = "hello ";
        log.info("########## Sender : " + context);
        this.rabbitTemplate.convertAndSend(ROUTING_KEY, context);
    }
}
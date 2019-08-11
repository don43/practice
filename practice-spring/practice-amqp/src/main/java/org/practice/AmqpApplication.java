package org.practice;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static org.practice.PkgConst.ROUTING_KEY;

@SpringBootApplication
public class AmqpApplication implements CommandLineRunner {

    static final String topicExchangeName = "spring-boot-exchange";

    @Autowired
    private Sender sender;

    @Bean
    Queue queue() {
        return new Queue(ROUTING_KEY, false);
    }

    /*@Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }*/

    @Override
    public void run(String... args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            sender.send();
            Thread.sleep(5000);
        }
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AmqpApplication.class, args);
    }
}

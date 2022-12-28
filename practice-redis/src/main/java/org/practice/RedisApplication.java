package org.practice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;


@SpringBootApplication
public class RedisApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(RedisApplication.class);

    @Autowired
    private StringRedisTemplate template;

    @Override
    public void run(String... args) {
        ValueOperations<String, String> ops = this.template.opsForValue();
        String key = "spring.boot.redis.test";
        if (!this.template.hasKey(key)) {
            ops.set(key, "foo");
        }
        log.info("Found key " + key + ", value=" + ops.get(key));
    }

    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args).close();
    }
}

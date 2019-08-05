package org.practice.redis;

import org.redisson.Redisson;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author don
 */
public class RedisApplication {
    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        RedissonClient redisson = Redisson.create(config);
        RSet<String> set = redisson.getSet("anySet");
        System.out.println(set.add("sss"));
    }
}

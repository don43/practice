package org.practice.rs;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

/**
 * @author don
 */
public class RsApplication {

    public static void main(String[] args) throws InterruptedException {
        Config config = new Config();
        config.setLockWatchdogTimeout(5 * 1000);
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        RedissonClient redisson = Redisson.create(config);

        RLock lock = redisson.getLock("anyLock");
        try {
            System.out.println("lock start ");
            lock.lock(10, TimeUnit.SECONDS);
            System.out.println("lock success ");
            Thread.sleep(15 * 1000);
            System.out.println("打印就表示有执行");
        } finally {
            try {
                lock.unlock();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("unlock success ");
        }
        redisson.shutdown();
    }
}

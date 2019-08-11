package org.practice;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.RetryNTimes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootConfiguration
public class CuratorApplication implements CommandLineRunner {

    private static final int num = 3, maxRetries = 0, sleepMsBetweenRetries = 0;

    private static final String connect = "192.168.255.131:2181";

    private static final String PATH = "/examples/locks";

    private static final ExecutorService service = Executors.newFixedThreadPool(num);

    private static final CountDownLatch latch = new CountDownLatch(num);

    private static final Logger log = LoggerFactory.getLogger(CuratorApplication.class);


    @Override
    public void run(String... args) {
        Runnable run = () -> {
            String threadName = Thread.currentThread().getName();
            log.info("#####curator will start#####" + threadName);
            CuratorFramework client = CuratorFrameworkFactory.newClient(connect, new RetryNTimes(maxRetries, sleepMsBetweenRetries));

            client.start();
            log.info("######curator is started#####" + threadName);
            InterProcessMutex lock = new InterProcessMutex(client, PATH);
            log.info("######curator mutex is instance #####" + threadName);
            try {
                log.info("######curator lock wait######" + threadName);
                boolean acquired = lock.acquire(15, TimeUnit.SECONDS);
                log.info("######curator lock acquired######" + threadName + ":" + acquired);
                Thread.sleep(5000);
            } catch (Exception e) {
                log.error("######curator lock acquired error######", e);
            } finally {
                try {
                    lock.release();
                    log.info("######curator lock released######" + threadName);
                } catch (Exception e) {
                    log.error("######curator lock release error######", e);
                }
                latch.countDown();
            }
        };
        for (int i = 0; i < num; i++) {
            service.submit(run);
        }

        log.info("等待子线程执行完毕...");
        try {
            latch.await();
        } catch (InterruptedException e) {
            log.error("latch await error", e);
        }
        log.info("子线程已经执行完毕.");
    }

    public static void main(String[] args) {
        SpringApplication.run(CuratorApplication.class, args);
    }
}

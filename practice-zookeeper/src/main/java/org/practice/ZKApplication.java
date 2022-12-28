package org.practice;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringBootConfiguration;

import java.net.InetAddress;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CountDownLatch;

import static org.apache.zookeeper.CreateMode.PERSISTENT;
import static org.apache.zookeeper.Watcher.Event.KeeperState.SyncConnected;
import static org.apache.zookeeper.ZooDefs.Ids.OPEN_ACL_UNSAFE;

@SpringBootConfiguration
public class ZKApplication implements CommandLineRunner {

    private static final int num = 1, maxRetries = 0, sleepMsBetweenRetries = 0;

    private static final String connect = "192.168.255.130:2181";

    private static final String PATH = "/examples/locks";

    private static final Logger log = LoggerFactory.getLogger(ZKApplication.class);


    @Override
    public void run(String... args) {
       /* // 创建一个目录节点
        zk.create("/path01", "data01".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        // 创建一个子目录节点
        zk.create("/path01/path01", "data01/data01".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(new String(zk.getData("/path01", false, null)));
        // 取出子目录节点列表
        System.out.println(zk.getChildren("/path01", true));
        // 创建另外一个子目录节点
        zk.create("/path01/path02", "data01/data02".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(zk.getChildren("/path01", true));
        // 修改子目录节点数据
        zk.setData("/path01/path01", "data01/data01-01".getBytes(), -1);
        byte[] datas = zk.getData("/path01/path01", true, null);
        String str = new String(datas, "utf-8");
        System.out.println(str);
        // 删除整个子目录 -1代表version版本号，-1是删除所有版本
        zk.delete("/path01/path01", -1);
        zk.delete("/path01/path02", -1);
        zk.delete("/path01", -1);
        System.out.println(str);
        Thread.sleep(15000);
        zk.close();
        System.out.println("OK");*/
    }

    public static void main(String[] args) throws Exception {
        //  SpringApplication.run(ZKApplication.class, args);

        CountDownLatch latch = new CountDownLatch(1);
        Watcher w = e -> {
            log.debug(e.toString());
            if (e.getState() == SyncConnected) {
                latch.countDown();
            }
        };
        log.debug("#####zookeeper will start#####");
        ZooKeeper zk = new ZooKeeper("192.168.255.131:2181", 30000, w, false);
        latch.await();
        log.debug("######zookeeper is started#####");

        log.debug("######创建一个目录节点######");
        zk.create("/path01", "data01".getBytes(), OPEN_ACL_UNSAFE, PERSISTENT);

        log.debug("######创建一个子目录节点######");
        zk.create("/path01/path01", "data01/data01".getBytes(), OPEN_ACL_UNSAFE, PERSISTENT);

        log.debug("######取出子目录节点列表######" + zk.getChildren("/path01", true));
        // 删除整个子目录 -1代表version版本号，-1是删除所有版本
        zk.delete("/path01/path01", -1);
        zk.delete("/path01", -1);
        Thread.sleep(5000);
        zk.close();
        log.debug("OK!");
    }

    public static void _main(String[] args) throws Exception {
        System.out.println(InetAddress.getLocalHost().getHostAddress());
        System.out.println(DateTimeFormatter.ISO_DATE_TIME.format(ZonedDateTime.now()));

        System.out.println(InetAddress.getLocalHost().getCanonicalHostName());

        System.out.println(DateTimeFormatter.ISO_DATE_TIME.format(ZonedDateTime.now()));

        System.out.println(InetAddress.getLocalHost().getCanonicalHostName());

        System.out.println(DateTimeFormatter.ISO_DATE_TIME.format(ZonedDateTime.now()));
    }
}

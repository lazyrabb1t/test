package xyz.lazyrabbit.zookeeper.lock;

import lombok.SneakyThrows;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ZookeeperLock {

    ZooKeeper zooKeeper = null;
    private String connectString = "127.0.0.1:2181";
    private String rootPath = "/locks";
    private String lockPath = "/seq";
    // 当前创建的节点路径
    private String currentPath = null;
    // 要监听的上一个节点路径
    private String prePath = null;
    CountDownLatch preNodeCountDownLatch = null;

    public ZookeeperLock() throws IOException, InterruptedException, KeeperException {
        // 建立连接
        // 使用countDownLatch确保连接成功再进行其他操作
        CountDownLatch countDownLatch = new CountDownLatch(1);
        preNodeCountDownLatch = new CountDownLatch(1);
        zooKeeper = new ZooKeeper(connectString, 2000, watchedEvent -> {
            System.out.println("zookeeper watcher path:" + watchedEvent.getPath()
                    + " state:" + watchedEvent.getState().name()
                    + " type:" + watchedEvent.getType().name());
            // 监听连接状态
            if (countDownLatch.getCount() > 0l && watchedEvent.getState() == Watcher.Event.KeeperState.SyncConnected) {
                System.out.println("连接zookeeper服务成功！");
                countDownLatch.countDown();
            }
            // 监听上一个节点删除
            if (preNodeCountDownLatch.getCount() > 0l && watchedEvent.getPath().equals(prePath) && watchedEvent.getType() == Watcher.Event.EventType.NodeDeleted) {
                System.out.println("上一个节点被删除！");
                preNodeCountDownLatch.countDown();
            }
        });
        countDownLatch.await();

        // 创建根节点
        Stat stat = zooKeeper.exists(rootPath, false);
        if (stat == null) {
            try {
                zooKeeper.create(rootPath, "this is a distribute lock;".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            } catch (KeeperException exception) {
                // 可能这个过程中已经被创建出来了
                stat = zooKeeper.exists(rootPath, false);
                if (stat == null) {
                    throw exception;
                }
            }
        }
    }

    public void lock() {
        try {
            // 创建节点
            currentPath = zooKeeper.create(rootPath + lockPath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            // 获取所有子节点
            List<String> children = zooKeeper.getChildren(rootPath, false);
            if (children.size() == 1) {// 只有一个节点则代表获取到锁
                return;
            } else {
                //
                Collections.sort(children);
                String currentNodeName = currentPath.substring(rootPath.length() + 1);
                int index = children.indexOf(currentNodeName);
                if (index < 0) {
                    // 节点未获取到，可能被人为干掉了，此时当前实例就不能获得锁了
                    throw new NullPointerException("获取锁失败！");
                } else if (index == 0) {
                    // 当前节点排序最小，直接获得锁
                    return;
                } else {
                    // 监听上一个节点的变化
                    prePath = rootPath + "/" + children.get(index - 1);
                    System.out.println("上一个节点是" + prePath);
                    try {
                        zooKeeper.getData(prePath, true, null);
                    } catch (KeeperException exception) {
                        // 上一个节点可能被释放了，所以获取异常时需要判断节点存不存在了，不存在则直接获取锁
                        Stat stat = zooKeeper.exists(prePath, false);
                        if (stat == null) {
                            return;
                        } else {
                            throw exception;
                        }
                    }
                    preNodeCountDownLatch.await();
                    return;
                }
            }
        } catch (InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
    }

    public void unlock() throws KeeperException, InterruptedException {
        // 删除节点
        zooKeeper.delete(currentPath, -1);
    }
}

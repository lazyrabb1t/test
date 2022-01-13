package xyz.lazyrabbit.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import javax.sound.midi.SoundbankResource;
import java.io.IOException;
import java.util.List;

public class ZookeeperDemo {
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 2000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

            }
        });
        String nodePath = "/rabb_java_api";
        // 创建方法
        String create = zooKeeper.create(nodePath, "test_data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(create);
        // 获取子节点方法
        List<String> children = zooKeeper.getChildren("/", true);
        System.out.println(children);
        // 判断节点是否存在方法
        Stat exists = zooKeeper.exists(nodePath, false);
        System.out.println(exists);
        // 获取节点数据方法
        byte[] data = zooKeeper.getData(nodePath, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

            }
        }, null);
        System.out.println(new String(data));
        // 修改节点数据
        zooKeeper.setData(nodePath, "test_data1".getBytes(), zooKeeper.exists(nodePath, false).getVersion());
        // 删除节点
        zooKeeper.delete(nodePath, zooKeeper.exists(nodePath, false).getVersion());
    }

}

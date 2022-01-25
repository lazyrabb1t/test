package xyz.lazyrabbit.zookeeper.server;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class DistributeServer {
    ZooKeeper zooKeeper = null;
    private String connectString = "127.0.0.1:2181";
    private String serverRootPath = "/servers";
    private String serverName = "/test-server";
    private String serverUrl = "127.0.0.1:8080";

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        DistributeServer distributeServer = new DistributeServer();
        // 获取连接
        distributeServer.getConnect();
        // 注册服务
        distributeServer.register();
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }

    /**
     * 注册服务
     *
     * @throws KeeperException
     * @throws InterruptedException
     */
    private void register() throws KeeperException, InterruptedException {
        // 利用临时的顺序节点存储服务信息，服务断开后会自动删除服务
        zooKeeper.create(serverRootPath + serverName, serverUrl.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
    }

    private void getConnect() throws IOException {
        zooKeeper = new ZooKeeper(connectString, 2000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

            }
        });

    }
}

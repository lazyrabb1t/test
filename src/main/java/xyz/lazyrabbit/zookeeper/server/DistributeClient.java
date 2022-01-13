package xyz.lazyrabbit.zookeeper.server;

import lombok.SneakyThrows;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.bouncycastle.util.Strings;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class DistributeClient {
    ZooKeeper zooKeeper = null;
    private String connectString = "127.0.0.1:2181";
    private String serverRootPath = "/servers";
    private Map<String, Set<String>> serverUrlsMap = null;

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        DistributeClient distributeClient = new DistributeClient();
        // 获取连接
        distributeClient.getConnect();
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }

    /**
     * 获取所有服务
     *
     * @throws KeeperException
     * @throws InterruptedException
     */
    private void listServers() throws KeeperException, InterruptedException {
        System.out.println("---------监听所有服务开始---------");
        serverUrlsMap = new HashMap<>();
        List<String> children = zooKeeper.getChildren(serverRootPath, true);
        for (String node : children) {
            String serverName = node.substring(0, node.length() - 10);
            byte[] data = zooKeeper.getData(serverRootPath + "/" + node, false, null);
            Set<String> urls = serverUrlsMap.get(serverName);
            if (urls == null) {
                urls = new HashSet<>();
            }
            urls.add(Strings.fromByteArray(data));
            serverUrlsMap.put(serverName, urls);
        }
        System.out.println("所有服务：" + serverUrlsMap.toString());

        System.out.println("---------监听所有服务结束---------");
    }

    /**
     * 获取连接
     *
     * @throws IOException
     */
    private void getConnect() throws IOException {
        // 通过watch监听服务
        zooKeeper = new ZooKeeper(connectString, 2000, new Watcher() {
            @SneakyThrows
            @Override
            public void process(WatchedEvent watchedEvent) {
                listServers();
            }
        });
    }
}

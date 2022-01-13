package xyz.lazyrabbit.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.bouncycastle.util.Strings;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ZookeeperWatcherDemo {
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        String nodePath = "/rabb_java_api";
        Watcher defaultWatcher = watchedEvent -> {
            System.out.println("defaultWatcher start");
            System.out.println("state:" + watchedEvent.getState().name());
            System.out.println("type:" + watchedEvent.getType().name());
            System.out.println("path:" + watchedEvent.getPath());
            System.out.println("defaultWatcher end");
        };
        ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 2000, defaultWatcher);
        Watcher childrenWatcher = watchedEvent -> {
            System.out.println("childrenWatcher start");
            System.out.println("state:" + watchedEvent.getState().name());
            System.out.println("type:" + watchedEvent.getType().name());
            System.out.println("path:" + watchedEvent.getPath());
            System.out.println("childrenWatcher end");
        };
        Watcher dataWatcher = watchedEvent -> {
            System.out.println("dataWatcher start");
            System.out.println("state:" + watchedEvent.getState().name());
            System.out.println("type:" + watchedEvent.getType().name());
            System.out.println("path:" + watchedEvent.getPath());
            if (watchedEvent.getType() == Watcher.Event.EventType.NodeDataChanged) {
                try {
                    byte[] data = zooKeeper.getData(nodePath, false, null);
                    System.out.println("data change to " + Strings.fromByteArray(data));
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("dataWatcher end");
        };
        zooKeeper.getChildren(nodePath, childrenWatcher);
        zooKeeper.getData(nodePath, dataWatcher, null);
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }

}

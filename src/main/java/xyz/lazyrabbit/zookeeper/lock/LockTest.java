package xyz.lazyrabbit.zookeeper.lock;

import lombok.SneakyThrows;
import lombok.Synchronized;
import org.apache.zookeeper.KeeperException;

import javax.sound.midi.SoundbankResource;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class LockTest {

    public static void main(String[] args) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        // zookeeper同一个Ip默认限制最大连接为60，要测更复杂的情况需要修改配置
        NumTest numTest = new NumTest();
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(new SumRunnable(numTest, countDownLatch));
            thread.start();
        }
        countDownLatch.await();
        System.out.println(numTest.getNum());

    }

    static class NumTest {
        private Integer num;

        public NumTest() {
            this.num = 0;
        }

        public void add() throws Exception {
            ZookeeperLock zookeeperLock = new ZookeeperLock();
            zookeeperLock.lock();
//            synchronized (this){
            this.num++;
//            }

            zookeeperLock.unlock();
        }

        public Integer getNum() {
            return num;
        }

        public void setNum(Integer num) {
            this.num = num;
        }
    }

    static class SumRunnable implements Runnable {

        NumTest numTest;
        CountDownLatch countDownLatch;

        public SumRunnable(NumTest numTest, CountDownLatch countDownLatch) {
            this.numTest = numTest;
            this.countDownLatch = countDownLatch;
        }

        @SneakyThrows
        @Override
        public void run() {
            for (int i = 0; i < 20; i++) {
                numTest.add();
            }
            System.out.println("执行完成 " + Thread.currentThread().getName());
            countDownLatch.countDown();
        }
    }
}

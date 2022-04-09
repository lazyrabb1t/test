package xyz.lazyrabbit.juc.producer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class ProducerAndConsumerSync {
    static class Container {
        LinkedList<Integer> list = new LinkedList<Integer>();
        int capacity = 10;

        public void put(int value) {
            while (true) {
                try {
                    //sleep不能放在同步代码块里面，因为sleep不会释放锁，
                    // 当前线程会一直占有produce线程，直到达到容量，调用wait()方法主动释放锁
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (this) {
                    //当容器满的时候，producer处于等待状态
                    while (list.size() == capacity) {
                        System.out.println("container is full,waiting ....");
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //没有满，则继续produce
                    System.out.println("producer--" + Thread.currentThread().getName() + "--put:" + value);
                    list.add(value++);
                    //唤醒其他所有处于wait()的线程，包括消费者和生产者
                    notifyAll();
                }
            }
        }

        public Integer take() {
            Integer val = 0;
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (this) {
                    //如果容器中没有数据，consumer处于等待状态
                    while (list.size() == 0) {
                        System.out.println("container is empty,waiting ...");
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //如果有数据，继续consume
                    val = list.removeFirst();
                    System.out.println("consumer--" + Thread.currentThread().getName() + "--take:" + val);
//唤醒其他所有处于wait()的线程，包括消费者和生产者
                    //notify必须放在同步代码块里面
                    notifyAll();
                }
            }
        }
    }

    static class Producer implements Runnable {
        private Container container;

        public Producer(Container container) {
            this.container = container;
        }

        @Override
        public void run() {
            container.put(new Random().nextInt(100));
        }
    }

    static class Consumer implements Runnable {
        private Container container;

        public Consumer(Container container) {
            this.container = container;
        }

        @Override
        public void run() {
            Integer val = container.take();
        }
    }

    public static void main(String[] args) {
        Container container = new Container();
        Thread producer1 = new Thread(new Producer(container));
        Thread producer2 = new Thread(new Producer(container));
        Thread producer3 = new Thread(new Producer(container));
        Thread producer4 = new Thread(new Producer(container));
        producer1.start();
        producer2.start();
        producer3.start();
        producer4.start();
        Thread consumer1 = new Thread(new Consumer(container));
        Thread consumer2 = new Thread(new Consumer(container));
        Thread consumer3 = new Thread(new Consumer(container));
        Thread consumer4 = new Thread(new Consumer(container));
        Thread consumer5 = new Thread(new Consumer(container));
        Thread consumer6 = new Thread(new Consumer(container));
        consumer1.start();
        consumer2.start();
        consumer3.start();
        consumer4.start();
        consumer5.start();
        consumer6.start();
    }
}
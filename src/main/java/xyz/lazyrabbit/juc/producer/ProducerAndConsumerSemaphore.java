package xyz.lazyrabbit.juc.producer;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class ProducerAndConsumerSemaphore {
    static class Container {
        Semaphore fullCount = new Semaphore(0);
        Semaphore emptyCount = new Semaphore(10);
        Semaphore isUse = new Semaphore(1);

        List list = new LinkedList<Integer>();


        public void put(Integer val) {

            try {
                emptyCount.acquire();
                isUse.acquire();

                list.add(val);
                System.out.println("producer--" + Thread.currentThread().getName() + "--put:" + val + "===size:" + list.size());

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                isUse.release();
                fullCount.release();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


        public Integer get() {
            Integer val1 = 0;
            try {
                fullCount.acquire();
                isUse.acquire();

                val1 = (Integer) list.remove(0);
                System.out.println("consumer--" + Thread.currentThread().getName() + "--take:" + val1 + "===size:" + list.size());

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                isUse.release();
                emptyCount.release();
            }

            return val1;

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
            Integer val = container.get();
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
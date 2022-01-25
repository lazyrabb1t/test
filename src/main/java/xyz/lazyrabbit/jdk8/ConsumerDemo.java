package xyz.lazyrabbit.jdk8;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author guoxw
 * @Description TODO
 * @createTime 2022年01月25日 14:51:00
 */
public class ConsumerDemo {
    public static void main(String[] args) {
        // 用来消费一个对象，接受一个参数，没有返回值，集合类的foreach方法的参数就是一个Consumer对象
        Consumer<String> consumer = System.out::println;
        consumer.accept("123");
        // andThen方法用来组合另一个消费者，可以对一个参数进行多次消费
        consumer.andThen(s -> System.out.println(s.length())).accept("haha");
        // jdk还定义了以下Consumer
        // BiConsumer	传入两个任意类型参数，无返回值
        // DoubleConsumer	传入一个 double 参数，无返回值
        // IntConsumer	传入一个 int 参数，无返回值
        // LongConsumer	传入一个 long 参数，无返回值
        // ObjDoubleConsumer	传入一个任意类型参数，一个 double 参数，无返回值
        // ObjIntConsumer	传入一个任意类型参数，一个 int 参数，无返回值
        // ObjLongConsumer	传入一个任意类型参数，一个 long 参数，无返回值
        BiConsumer<String, Integer> biConsumer = (str, length) -> {
            System.out.println(str.substring(0, length));
        };
        biConsumer.accept("hello world!", 10);
    }
}

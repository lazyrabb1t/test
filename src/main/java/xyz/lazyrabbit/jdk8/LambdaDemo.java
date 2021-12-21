package xyz.lazyrabbit.jdk8;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class LambdaDemo {
    public static void main(String[] args) {
        List list = new ArrayList<>();
        list.forEach(System.out::println);
        Consumer<String> consumer = String::new;
        consumer.accept("123");
        System.out.println(consumer.toString());
    }
}

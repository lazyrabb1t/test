package xyz.lazyrabbit.jdk8;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author guoxw
 * @Description TODO
 * @createTime 2022��01��25�� 14:51:00
 */
public class ConsumerDemo {
    public static void main(String[] args) {
        // ��������һ�����󣬽���һ��������û�з���ֵ���������foreach�����Ĳ�������һ��Consumer����
        Consumer<String> consumer = System.out::println;
        consumer.accept("123");
        // andThen�������������һ�������ߣ����Զ�һ���������ж������
        consumer.andThen(s -> System.out.println(s.length())).accept("haha");
        // jdk������������Consumer
        // BiConsumer	���������������Ͳ������޷���ֵ
        // DoubleConsumer	����һ�� double �������޷���ֵ
        // IntConsumer	����һ�� int �������޷���ֵ
        // LongConsumer	����һ�� long �������޷���ֵ
        // ObjDoubleConsumer	����һ���������Ͳ�����һ�� double �������޷���ֵ
        // ObjIntConsumer	����һ���������Ͳ�����һ�� int �������޷���ֵ
        // ObjLongConsumer	����һ���������Ͳ�����һ�� long �������޷���ֵ
        BiConsumer<String, Integer> biConsumer = (str, length) -> {
            System.out.println(str.substring(0, length));
        };
        biConsumer.accept("hello world!", 10);
    }
}

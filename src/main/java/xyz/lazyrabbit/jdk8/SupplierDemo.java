package xyz.lazyrabbit.jdk8;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

/**
 * @author guoxw
 * @Description TODO
 * @createTime 2022年01月25日 13:48:00
 */
public class SupplierDemo {
    public static void main(String[] args) {
        // 当做创建对象的生产者，提供了一个get方法来获取对象
        Supplier localDateTimeSupplier = LocalDateTime::now;
        System.out.println(localDateTimeSupplier.get());
        Supplier<Integer> randomSupplier = () -> new Random().nextInt(10);
        System.out.println(randomSupplier.get());

        // 默认提供了指定类型的Supplier，如BooleanSupplier、DoubleSupplier、IntSupplier以及LongSupplier
        IntSupplier intSupplier = () -> new Random().nextInt(10);
        System.out.println(intSupplier.getAsInt());
        BooleanSupplier booleanSupplier = () -> new Random().nextInt(10) > 5;
        System.out.println(booleanSupplier.getAsBoolean());

    }
}

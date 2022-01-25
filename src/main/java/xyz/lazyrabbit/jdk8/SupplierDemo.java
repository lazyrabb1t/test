package xyz.lazyrabbit.jdk8;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

/**
 * @author guoxw
 * @Description TODO
 * @createTime 2022��01��25�� 13:48:00
 */
public class SupplierDemo {
    public static void main(String[] args) {
        // ������������������ߣ��ṩ��һ��get��������ȡ����
        Supplier localDateTimeSupplier = LocalDateTime::now;
        System.out.println(localDateTimeSupplier.get());
        Supplier<Integer> randomSupplier = () -> new Random().nextInt(10);
        System.out.println(randomSupplier.get());

        // Ĭ���ṩ��ָ�����͵�Supplier����BooleanSupplier��DoubleSupplier��IntSupplier�Լ�LongSupplier
        IntSupplier intSupplier = () -> new Random().nextInt(10);
        System.out.println(intSupplier.getAsInt());
        BooleanSupplier booleanSupplier = () -> new Random().nextInt(10) > 5;
        System.out.println(booleanSupplier.getAsBoolean());

    }
}

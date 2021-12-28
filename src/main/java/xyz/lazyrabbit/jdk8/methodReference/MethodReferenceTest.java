package xyz.lazyrabbit.jdk8.methodReference;

import java.time.LocalDateTime;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author guoxw
 * @Description TODO
 * @createTime 2021年12月28日 11:21:00
 */
public class MethodReferenceTest {

    public static void main(String[] args) {
        test1();
        test2();
        test3();
        test4();
        test5();
    }

    /**
     * 对象名::引用成员方法
     */
    static void test1() {
        LocalDateTime localDateTime = LocalDateTime.now();
        Supplier<String> supplier1 = () -> localDateTime.toString();
        System.out.println(supplier1.get());
        Supplier<String> supplier2 = localDateTime::toString;
        System.out.println(supplier2.get());
    }

    /**
     * 类名::引用静态方法
     */
    static void test2() {
        Supplier<LocalDateTime> supplier1 = () -> LocalDateTime.now();
        System.out.println(supplier1.get());
        Supplier<LocalDateTime> supplier2 = LocalDateTime::now;
        System.out.println(supplier2.get());
    }

    /**
     * 类名::引用实例方法
     */
    static void test3() {
        Function<String, Integer> function = (s) -> s.length();
        System.out.println(function.apply("1"));
        Function<String, Integer> function2 = String::length;
        System.out.println(function.apply("1"));
    }

    /**
     * 类名::new  引用类构造器
     */
    static void test4() {
        Function<String, String> function1 = (s) -> new String(s);
        System.out.println(function1.apply("321"));
        Function<String, String> function2 = String::new;
        System.out.println(function2.apply("321"));
    }

    /**
     * 数组::new  引用数组构造器
     */
    static void test5() {
        Function<Integer, String[]> function1 = (len) -> new String[len];
        System.out.println(function1.apply(2).length);
        Function<Integer, String[]> function2 = String[]::new;
        System.out.println(function2.apply(2).length);
    }
}


package xyz.lazyrabbit.jdk8;

import java.util.function.DoublePredicate;
import java.util.function.Predicate;

/**
 * @author guoxw
 * @Description TODO
 * @createTime 2022年01月25日 14:51:00
 */
public class PredicateDemo {
    public static void main(String[] args) {
        // Predicate 断言，Stream 中的 filter() 方法是通过接收一个 Predicate 函数接口实现的
        Predicate<String> predicate = s -> s.contains("a");
        // 可以通过test方法判断其是否符合条件，
        System.out.println(predicate.test("abc"));
        // negate方法返回一个相反的对象
        System.out.println(predicate.negate().test("abc"));
        // 提供了and以及or方法，可以多个断言进行且、或判断
        System.out.println(predicate.and(s -> s.contains("c")).test("ac"));
        System.out.println(predicate.or(s -> s.contains("d")).test("d"));

        // jdk也提供了DoublePredicate、BiPredicate、IntPredicate、LongPredicate
        DoublePredicate doublePredicate = d -> d > 0d;
        System.out.println(doublePredicate.test(-2d));
    }
}

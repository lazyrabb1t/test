package xyz.lazyrabbit.jdk8;

import java.util.Comparator;
import java.util.function.*;

/**
 * @author guoxw
 * @Description TODO
 * @createTime 2022年01月25日 11:05:00
 */
public class FunctionDemo {
    public static void main(String[] args) {
        // 计算机翻译
        // Represents a function that accepts one argument and produces a result.
        // 表示函数，它接受一个参数并产生一个结果。

        // 获取字符串长度
        Function<String, Integer> lengthFunction = s -> s.length();
        // 获取数字的平方
        Function<Integer, Double> squareFunction = num -> Math.pow(num.doubleValue(), 2d);

        // identity 返回自身
        System.out.println(Function.identity().apply("hhh"));

        // apply 执行定义的函数
        System.out.println(lengthFunction.apply("hello"));
        System.out.println(squareFunction.apply(100));

        // andThen 可以指定一个函数，使用当前函数的输出作为输入
        System.out.println(lengthFunction.andThen(squareFunction).apply("rabb"));
        // compose 可以指定一个函数，该函数的输出将作为当前函数的输入，与andThen相反
        System.out.println(squareFunction.compose(lengthFunction).apply("apply"));

        // IntFunction 接受一个int参数，返回指定类型的结果，类似的还有 DoubleFunction LongFunction
        IntFunction<String> intFunction = value -> Integer.toString(value);
        // IntFunction 接受一个指定类型参数，返回int类型的结果，类似的还有 ToDoubleFunction ToLongFunction
        ToIntFunction<String> toIntFunction = value -> value.length();

        // BiFunction 接受两个指定类型参数，产生一个指定类型结果，和Function类似，他也有IntBiFunction、ToIntBiFunction这些方法
        BiFunction<String, String, Integer> lengthBiFunction = (s1, s2) -> s1.length() + s2.length();

        // UnaryOperator Function的子类，区别在于UnaryOperator的参数和返回值类型是相同的，同时拓展了一个identity方法，直接返回参数
        UnaryOperator<String> unaryOperator = s -> s + "_abc";
        System.out.println(unaryOperator.apply("123"));
        System.out.println(UnaryOperator.identity().apply("123"));

        // BinaryOperator BiFunction的子类，BinaryOperator的参数和返回值类型也是相同的，同时拓展了两个方法，minBy以及maxBy,分别返回一个较小值以及一个较大值
        BinaryOperator<String> binaryOperator = (s1, s2) -> s1 + s2;
        System.out.println(binaryOperator.apply("hello", " world"));
        System.out.println(BinaryOperator.maxBy(Comparator.comparingInt(String::length)).apply("abc", "de"));
    }
}

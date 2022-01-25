package xyz.lazyrabbit.jdk8;

import java.util.Comparator;
import java.util.function.*;

/**
 * @author guoxw
 * @Description TODO
 * @createTime 2022��01��25�� 11:05:00
 */
public class FunctionDemo {
    public static void main(String[] args) {
        // ���������
        // Represents a function that accepts one argument and produces a result.
        // ��ʾ������������һ������������һ�������

        // ��ȡ�ַ�������
        Function<String, Integer> lengthFunction = s -> s.length();
        // ��ȡ���ֵ�ƽ��
        Function<Integer, Double> squareFunction = num -> Math.pow(num.doubleValue(), 2d);

        // identity ��������
        System.out.println(Function.identity().apply("hhh"));

        // apply ִ�ж���ĺ���
        System.out.println(lengthFunction.apply("hello"));
        System.out.println(squareFunction.apply(100));

        // andThen ����ָ��һ��������ʹ�õ�ǰ�����������Ϊ����
        System.out.println(lengthFunction.andThen(squareFunction).apply("rabb"));
        // compose ����ָ��һ���������ú������������Ϊ��ǰ���������룬��andThen�෴
        System.out.println(squareFunction.compose(lengthFunction).apply("apply"));

        // IntFunction ����һ��int����������ָ�����͵Ľ�������ƵĻ��� DoubleFunction LongFunction
        IntFunction<String> intFunction = value -> Integer.toString(value);
        // IntFunction ����һ��ָ�����Ͳ���������int���͵Ľ�������ƵĻ��� ToDoubleFunction ToLongFunction
        ToIntFunction<String> toIntFunction = value -> value.length();

        // BiFunction ��������ָ�����Ͳ���������һ��ָ�����ͽ������Function���ƣ���Ҳ��IntBiFunction��ToIntBiFunction��Щ����
        BiFunction<String, String, Integer> lengthBiFunction = (s1, s2) -> s1.length() + s2.length();

        // UnaryOperator Function�����࣬��������UnaryOperator�Ĳ����ͷ���ֵ��������ͬ�ģ�ͬʱ��չ��һ��identity������ֱ�ӷ��ز���
        UnaryOperator<String> unaryOperator = s -> s + "_abc";
        System.out.println(unaryOperator.apply("123"));
        System.out.println(UnaryOperator.identity().apply("123"));

        // BinaryOperator BiFunction�����࣬BinaryOperator�Ĳ����ͷ���ֵ����Ҳ����ͬ�ģ�ͬʱ��չ������������minBy�Լ�maxBy,�ֱ𷵻�һ����Сֵ�Լ�һ���ϴ�ֵ
        BinaryOperator<String> binaryOperator = (s1, s2) -> s1 + s2;
        System.out.println(binaryOperator.apply("hello", " world"));
        System.out.println(BinaryOperator.maxBy(Comparator.comparingInt(String::length)).apply("abc", "de"));
    }
}

package xyz.lazyrabbit.jdk8;

import java.util.function.DoublePredicate;
import java.util.function.Predicate;

/**
 * @author guoxw
 * @Description TODO
 * @createTime 2022��01��25�� 14:51:00
 */
public class PredicateDemo {
    public static void main(String[] args) {
        // Predicate ���ԣ�Stream �е� filter() ������ͨ������һ�� Predicate �����ӿ�ʵ�ֵ�
        Predicate<String> predicate = s -> s.contains("a");
        // ����ͨ��test�����ж����Ƿ����������
        System.out.println(predicate.test("abc"));
        // negate��������һ���෴�Ķ���
        System.out.println(predicate.negate().test("abc"));
        // �ṩ��and�Լ�or���������Զ�����Խ����ҡ����ж�
        System.out.println(predicate.and(s -> s.contains("c")).test("ac"));
        System.out.println(predicate.or(s -> s.contains("d")).test("d"));

        // jdkҲ�ṩ��DoublePredicate��BiPredicate��IntPredicate��LongPredicate
        DoublePredicate doublePredicate = d -> d > 0d;
        System.out.println(doublePredicate.test(-2d));
    }
}

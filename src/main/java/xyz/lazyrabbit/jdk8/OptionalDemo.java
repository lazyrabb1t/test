package xyz.lazyrabbit.jdk8;

import java.util.Optional;

/**
 * @author guoxw
 * @Description TODO
 * @createTime 2022��01��25�� 10:23:00
 */
public class OptionalDemo {
    public static void main(String[] args) {

        // Optional ���췽ʽ1 - of �����ֵ����Ϊ null
        Optional<String> helloOption = Optional.of("hello");
        // Optional ���췽ʽ2 - empty һ���� optional
        Optional<String> emptyOptional = Optional.empty();
        // Optional ���췽ʽ3 - ofNullable ֧�ִ��� null ֵ�� optional
        Optional<String> nullOptional = Optional.ofNullable(null);

        Optional<String> stringOptional = Optional.of("hello");
        // �ж��Ƿ�Ϊnull
        System.out.println(stringOptional.isPresent());

        // ��ȡֵ
        // 1��get ֱ�ӻ�ȡֵ��Ϊ��ʱ�׳�NoSuchElementException�쳣
        System.out.println(stringOptional.get());
        // 2��orElse Ϊ��ʱ��ȡ��Ĭ��ֵ
        stringOptional.orElse("default");
        // 3��orElseGet Ϊ��ʱ��ȡ��Ĭ��ֵ����orElse��ȣ�Supplier����ֻ����Optional���󲻺�ֵʱ��ִ�е���
        stringOptional.orElseGet(() -> "default");
        // 4��orElseThrow Ϊ��ʱ�׳��Զ����쳣
        stringOptional.orElseThrow(() -> new NullPointerException("ֵΪ��"));


        // ifPresent ����ֵʱ���÷�����������ʱdo nothing
        stringOptional.ifPresent(System.out::println);
        // filter ����ֵ
        stringOptional.filter(str -> str.length() > 3).ifPresent(System.out::println);
        // map ת��ֵ������ֵ�뵱ǰֵ������ͬ
        stringOptional.map(str -> str + "123");
        // flatMap ת��ֵ������ֵ�����봫��ֵ��ͬ���ҷ���ֵ��Ҫ�ֶ���װΪOptional����
        stringOptional.flatMap(str -> Optional.of(str.length())).ifPresent(System.out::println);

    }
}

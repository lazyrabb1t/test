package xyz.lazyrabbit.jdk8;

import java.util.Optional;

/**
 * @author guoxw
 * @Description TODO
 * @createTime 2022年01月25日 10:23:00
 */
public class OptionalDemo {
    public static void main(String[] args) {

        // Optional 构造方式1 - of 传入的值不能为 null
        Optional<String> helloOption = Optional.of("hello");
        // Optional 构造方式2 - empty 一个空 optional
        Optional<String> emptyOptional = Optional.empty();
        // Optional 构造方式3 - ofNullable 支持传入 null 值的 optional
        Optional<String> nullOptional = Optional.ofNullable(null);

        Optional<String> stringOptional = Optional.of("hello");
        // 判断是否不为null
        System.out.println(stringOptional.isPresent());

        // 获取值
        // 1、get 直接获取值，为空时抛出NoSuchElementException异常
        System.out.println(stringOptional.get());
        // 2、orElse 为空时获取到默认值
        stringOptional.orElse("default");
        // 3、orElseGet 为空时获取到默认值，与orElse相比，Supplier方法只有在Optional对象不含值时才执行调用
        stringOptional.orElseGet(() -> "default");
        // 4、orElseThrow 为空时抛出自定义异常
        stringOptional.orElseThrow(() -> new NullPointerException("值为空"));


        // ifPresent 存在值时调用方法，不存在时do nothing
        stringOptional.ifPresent(System.out::println);
        // filter 过滤值
        stringOptional.filter(str -> str.length() > 3).ifPresent(System.out::println);
        // map 转换值，返回值与当前值类型相同
        stringOptional.map(str -> str + "123");
        // flatMap 转换值，返回值可以与传入值不同，且返回值需要手动包装为Optional类型
        stringOptional.flatMap(str -> Optional.of(str.length())).ifPresent(System.out::println);

    }
}

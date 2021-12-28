package xyz.lazyrabbit.jdk8.function;

/**
 * @author guoxw
 * @Description TODO
 * @createTime 2021年12月25日 11:16:00
 */
@FunctionalInterface
public interface FunctionInterfaceDemo {
    String test();

//    String test2();

    default String test3() {
        return "test3";
    }

    static String test4() {
        return "test4";
    }
}

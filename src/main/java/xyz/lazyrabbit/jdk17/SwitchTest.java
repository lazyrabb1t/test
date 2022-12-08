package xyz.lazyrabbit.jdk17;

public class SwitchTest {
    /**
     * 支持lambda表达式
     *
     * @param a
     */
    public static void test1(String a) {
        switch (a) {
            case "a" -> System.out.println("i guess it is a ~");
            default -> System.out.println("default");
        }
    }

    /**
     * 支持yield返回值
     *
     * @param a
     */
    public static void test2(String a) {
        String result = switch (a) {
            case "a" -> {
                yield "i guess it is a ~";
            }
            // 这里也可以直接省略yield
            default -> "default";
        };
        System.out.println("test2:" + result);
    }

    enum Type {
        TYPE_A, TYPE_B, TYPE_C;
    }

    /**
     * 使用枚举测试
     * 如果已覆盖所有可能取值，则不再需要default分支
     *
     * @param type
     * @return
     */
    public static String test3(Type type) {
        return switch (type) {
            case TYPE_A -> "TYPE_A";
            case TYPE_B -> "TYPE_B";
            case TYPE_C -> "TYPE_C";
        };
    }

    sealed interface ITest permits TestA, TestB {
    }

    static final class TestA implements ITest {
    }

    static final class TestB implements ITest {
    }

    /**
     * 使用密封类测试
     * 如果已覆盖所有可能取值，则不再需要default分支
     *
     * @param test
     * @return
     */
    public static String test4(ITest test) {
        return switch (test) {
            case TestA testA -> "TestA";
            case TestB testB -> "TestB";
        };
    }

    /**
     * null测试
     *
     * @param s
     * @return
     */
    public static String test5(String s) {
        return switch (s) {
            case null -> "null";
            case "a" -> "a";
            default -> "default";
        };
    }

    /**
     * 支持连写case
     *
     * @param s
     * @return
     */
    public static String test6(String s) {
        return switch (s) {
            case "a", "b", "c" -> "abc";
            case "d" -> "d";
            default -> "default";
        };
    }

    public static void main(String[] args) {
        test1("a");
        test1("b");
        test2("a");
        test2("b");
        System.out.println("test3:" + test3(Type.TYPE_A));
        System.out.println("test4:" + test4(new TestA()));
        System.out.println("test5:" + test5(null));
        System.out.println("test6:" + test6("a"));
    }
}

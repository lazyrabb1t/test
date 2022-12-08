package xyz.lazyrabbit.jdk17;

public class InstanceTest {
    public static void main(String[] args) {
        test(1);
        test("1");
        test(1L);

        System.out.println(test2(1));
        System.out.println(test2("1"));
        System.out.println(test2(1L));
        System.out.println(test2(1f));
    }

    public static void test(Object object) {
        if (object instanceof String str) {
            System.out.println("String:" + str);
        } else if (object instanceof Integer integer) {
            System.out.println("Integer:" + integer);
        } else {
            System.out.println("unknown");
        }
    }

    public static String test2(Object o) {
        return switch (o) {
            case Integer i -> String.format("int %d", i);
            case Long l -> String.format("long %d", l);
            case Double d -> String.format("double %f", d);
            case String s -> String.format("String %s", s);
            default -> "unknown";
        };
    }
}

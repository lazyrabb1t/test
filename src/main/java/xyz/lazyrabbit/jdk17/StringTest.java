package xyz.lazyrabbit.jdk17;

public class StringTest {

    public static void main(String[] args) {
        String str = """
                asdasdqwdqwdqw
                  123123
                asdasd
                """;
        System.out.println(str);
        String str2 = """
                   asdasdqwdqwdqw
                     123123
                   asdasd
                """;
        System.out.println(str2);
    }
}

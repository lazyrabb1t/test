package xyz.lazyrabbit.jdk17;

public class InterfaceTest {
    interface ITest {
        default void test() {
            System.out.println(test2());
        }

        private String test2() {
            return "test2";
        }
    }
}

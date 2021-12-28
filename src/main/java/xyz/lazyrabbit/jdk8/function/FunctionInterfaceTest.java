package xyz.lazyrabbit.jdk8.function;

/**
 * @author guoxw
 * @Description TODO
 * @createTime 2021年12月25日 11:19:00
 */
public class FunctionInterfaceTest {

    public static void main(String[] args) {

        // 创建匿名函数
        FunctionInterfaceDemo functionInterfaceDemo1 = new FunctionInterfaceDemo() {
            @Override
            public String test() {
                return "hello ";
            }
        };
        System.out.println(functionInterfaceDemo1.test());

        // 使用lambda替代上面的写法，效果一样
        FunctionInterfaceDemo functionInterfaceDemo2 = () -> "hello";
        System.out.println(functionInterfaceDemo2.test());
    }
}

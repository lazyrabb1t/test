package xyz.lazyrabbit.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Test {


    public static void main(String[] args) {
        Person person = (Person) Proxy.newProxyInstance(Person.class.getClassLoader(), new Class[]{Person.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println(method.getName());
                return null;
            }
        });
        person.speak();

        Person actor = new Actor();
        Person proxyActor = (Person) Proxy.newProxyInstance(Person.class.getClassLoader(), new Class[]{Person.class}, new DynamicProxyHandler(actor));
        proxyActor.speak();
    }


}

interface Person {
    void speak();
}

class Actor implements Person {
    @Override
    public void speak() {
        System.out.println("i am a actor");
    }
}

class DynamicProxyHandler implements InvocationHandler {

    private Object object;

    public DynamicProxyHandler(Object object) {
        super();
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("befroe");
        Object result = method.invoke(object, args);
        System.out.println("after");
        return result;
    }
}

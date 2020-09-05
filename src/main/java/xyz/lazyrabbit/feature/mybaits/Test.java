package xyz.lazyrabbit.feature.mybaits;

import xyz.lazyrabbit.feature.mybaits.annotation.Select;
import xyz.lazyrabbit.feature.mybaits.test.mapper.UserMapper;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Test {
    public static void main(String[] args) {
        UserMapper userMapper = (UserMapper) Proxy.newProxyInstance(UserMapper.class.getClassLoader(), new Class[]{UserMapper.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if(method.isAnnotationPresent(Select.class)){
                    Select select = method.getAnnotation(Select.class);
                    System.out.println(select.value());
                }
                return null;
            }
        });
        userMapper.save(1,"jack ma");
    }
}

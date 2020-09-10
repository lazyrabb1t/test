package xyz.lazyrabbit.feature.mybaits;

import xyz.lazyrabbit.feature.mybaits.annotation.Insert;
import xyz.lazyrabbit.feature.mybaits.annotation.Select;
import xyz.lazyrabbit.feature.mybaits.test.entity.User;
import xyz.lazyrabbit.feature.mybaits.test.mapper.UserMapper;
import xyz.lazyrabbit.util.JDBCUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        UserMapper userMapper = (UserMapper) Proxy.newProxyInstance(UserMapper.class.getClassLoader(), new Class[]{UserMapper.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if(method.isAnnotationPresent(Select.class)){
                    Select select = method.getAnnotation(Select.class);
                    System.out.println(select.value());
                    return JDBCUtils.executeDQL(User.class, select.value());
                }else if(method.isAnnotationPresent(Insert.class)){
                    Insert insert = method.getAnnotation(Insert.class);
                    System.out.println(insert.value());
                    System.out.println(Arrays.toString(args));
                    JDBCUtils.executeDML(insert.value(),args);
                }
                return null;
            }
        });
        userMapper.save(999,"jack ma");
        List<User> list = userMapper.findAll();
        System.out.println(Arrays.toString(list.toArray()));
    }
}

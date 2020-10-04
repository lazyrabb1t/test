package xyz.lazyrabbit.generics;


import java.util.ArrayList;
import java.util.List;

public class GenericsTest<T1, T2> {

    public void go(List<? extends String> t) {

    }

    public void go2(List<? super String> t) {

    }


    public <T extends String, Number> void go(T t) {
        List<T1> list = new ArrayList<>();
        List<?> list2 = new ArrayList<>();
    }
}

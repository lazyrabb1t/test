package xyz.lazyrabbit.jdk8.stream;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author guoxw
 * @Description TODO
 * @createTime 2022年01月07日 16:25:00
 */
public class StreamDemo {

    public static void main(String[] args) throws FileNotFoundException {
        source();
        intermediate();
        terminal();
    }

    /**
     * 创建
     * @throws FileNotFoundException
     */
    public static void source() throws FileNotFoundException {
        // 使用Collection
        List<String> list = Arrays.asList("Messi", "Neymar", "Suárez");
        Stream<String> collectionStream = list.stream();
        Stream<String> collectionParallelStream = list.parallelStream();
        // 使用Arrays
        String[] array = new String[]{"Benzema", "CR7", "Bale"};
        Stream<String> arrayStream = Arrays.stream(array);
        // 使用Stream
        Stream<String> ofStream = Stream.of("Salah", "Mane", "Firmino");
        Stream<UUID> generateStream = Stream.generate(UUID::randomUUID);
        Stream<Integer> integerStream = Stream.iterate(1, integer -> integer + 3);
        IntStream rangeStream = IntStream.range(1, 10);
        // 使用BufferedReader
        BufferedReader bufferedReader = new BufferedReader(new FileReader("README.md"));
        // 使用Pattern
        Pattern pattern = Pattern.compile("A");
        Stream<String> patternStream = pattern.splitAsStream("ABCDE");
    }

    /**
     * 中间操作
     */
    public static void intermediate() {
        List<String> list1 = Arrays.asList("Messi", "Neymar", "Suárez");
        List<String> list2 = Arrays.asList("Benzema", "CR7", "Bale");
        List<String> list3 = Arrays.asList("Salah", "Mane", "Firmino", "Mane");
        // 使用filter过滤
        System.out.println(list1.stream().filter(s -> s.length() > 4).count());
        // 使用map映射
        System.out.println(list1.stream().map(s -> s.length()).collect(Collectors.toList()));
        // 使用flatMap合并
        System.out.println(Stream.of(list1, list2, list3).flatMap(list -> list.stream()).collect(Collectors.toList()));
        // 使用peek方法，主要用于调试，以便在元素流过管道中的某个点时查看它们，输出集合所有元素
        list1.stream().peek(s -> System.out.println(s)).count();
        // 使用distinct去重
        System.out.println(list3.stream().distinct().collect(Collectors.toList()));
        // 使用sort排序
        System.out.println(list3.stream().sorted(Comparator.comparing(s -> s.length())).collect(Collectors.toList()));
        // 使用limit获取指定数量元素
        System.out.println(list1.stream().limit(2).collect(Collectors.toList()));
        // 使用skip跳过指定数量元素
        System.out.println(list1.stream().skip(2).collect(Collectors.toList()));
    }

    /**
     * 结果处理
     */
    public static void terminal() {
        List<String> list1 = Arrays.asList("Messi", "Neymar", "Suárez");
        // forEach遍历
        list1.stream().forEach(System.out::println);
        // forEachOrdered遍历，相较于forEach，该方法可以保证在并行流中消费的顺序
        list1.parallelStream().forEachOrdered(System.out::println);
        // 使用toArray转化为数组
        System.out.println(list1.stream().toArray());
        // 使用collect收集转换数据
        // 转化为map
        System.out.println(list1.stream().collect(Collectors.toMap(Function.identity(), s -> s.length())));
        // 转化为逗号分隔的字符串
        String str = list1.stream().collect(Collectors.joining(","));
        System.out.println(str);
        // 分组聚合
        System.out.println(list1.stream().collect(Collectors.groupingBy(s -> s.length())));
        // 按条件分为两组
        System.out.println(list1.stream().collect(Collectors.partitioningBy(s -> s.length() > 4)));
        // 使用reduce，找到length最小的元素
        System.out.println(list1.stream().reduce((s1, s2) -> s1.length() > s2.length() ? s2 : s1));
        // 使用max找到最大元素，min类似
        System.out.println(list1.stream().max(Comparator.comparing(Function.identity())));
        // 使用count计数
        System.out.println(list1.stream().count());
        // 使用findFirst获取第一个元素
        System.out.println(list1.stream().findFirst());
        // 使用findAny获取任一元素
        System.out.println(list1.stream().filter(s -> s.length() > 1).findAny());
        // 使用anyMatch，有一个符合条件就返回true，allMatch、noneMatch类似
        System.out.println(list1.stream().anyMatch(s -> s.length() > 5));
    }
}

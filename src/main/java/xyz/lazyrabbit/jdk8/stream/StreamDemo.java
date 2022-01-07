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
 * @createTime 2022��01��07�� 16:25:00
 */
public class StreamDemo {

    public static void main(String[] args) throws FileNotFoundException {
        source();
        intermediate();
        terminal();
    }

    /**
     * ����
     * @throws FileNotFoundException
     */
    public static void source() throws FileNotFoundException {
        // ʹ��Collection
        List<String> list = Arrays.asList("Messi", "Neymar", "Su��rez");
        Stream<String> collectionStream = list.stream();
        Stream<String> collectionParallelStream = list.parallelStream();
        // ʹ��Arrays
        String[] array = new String[]{"Benzema", "CR7", "Bale"};
        Stream<String> arrayStream = Arrays.stream(array);
        // ʹ��Stream
        Stream<String> ofStream = Stream.of("Salah", "Mane", "Firmino");
        Stream<UUID> generateStream = Stream.generate(UUID::randomUUID);
        Stream<Integer> integerStream = Stream.iterate(1, integer -> integer + 3);
        IntStream rangeStream = IntStream.range(1, 10);
        // ʹ��BufferedReader
        BufferedReader bufferedReader = new BufferedReader(new FileReader("README.md"));
        // ʹ��Pattern
        Pattern pattern = Pattern.compile("A");
        Stream<String> patternStream = pattern.splitAsStream("ABCDE");
    }

    /**
     * �м����
     */
    public static void intermediate() {
        List<String> list1 = Arrays.asList("Messi", "Neymar", "Su��rez");
        List<String> list2 = Arrays.asList("Benzema", "CR7", "Bale");
        List<String> list3 = Arrays.asList("Salah", "Mane", "Firmino", "Mane");
        // ʹ��filter����
        System.out.println(list1.stream().filter(s -> s.length() > 4).count());
        // ʹ��mapӳ��
        System.out.println(list1.stream().map(s -> s.length()).collect(Collectors.toList()));
        // ʹ��flatMap�ϲ�
        System.out.println(Stream.of(list1, list2, list3).flatMap(list -> list.stream()).collect(Collectors.toList()));
        // ʹ��peek��������Ҫ���ڵ��ԣ��Ա���Ԫ�������ܵ��е�ĳ����ʱ�鿴���ǣ������������Ԫ��
        list1.stream().peek(s -> System.out.println(s)).count();
        // ʹ��distinctȥ��
        System.out.println(list3.stream().distinct().collect(Collectors.toList()));
        // ʹ��sort����
        System.out.println(list3.stream().sorted(Comparator.comparing(s -> s.length())).collect(Collectors.toList()));
        // ʹ��limit��ȡָ������Ԫ��
        System.out.println(list1.stream().limit(2).collect(Collectors.toList()));
        // ʹ��skip����ָ������Ԫ��
        System.out.println(list1.stream().skip(2).collect(Collectors.toList()));
    }

    /**
     * �������
     */
    public static void terminal() {
        List<String> list1 = Arrays.asList("Messi", "Neymar", "Su��rez");
        // forEach����
        list1.stream().forEach(System.out::println);
        // forEachOrdered�����������forEach���÷������Ա�֤�ڲ����������ѵ�˳��
        list1.parallelStream().forEachOrdered(System.out::println);
        // ʹ��toArrayת��Ϊ����
        System.out.println(list1.stream().toArray());
        // ʹ��collect�ռ�ת������
        // ת��Ϊmap
        System.out.println(list1.stream().collect(Collectors.toMap(Function.identity(), s -> s.length())));
        // ת��Ϊ���ŷָ����ַ���
        String str = list1.stream().collect(Collectors.joining(","));
        System.out.println(str);
        // ����ۺ�
        System.out.println(list1.stream().collect(Collectors.groupingBy(s -> s.length())));
        // ��������Ϊ����
        System.out.println(list1.stream().collect(Collectors.partitioningBy(s -> s.length() > 4)));
        // ʹ��reduce���ҵ�length��С��Ԫ��
        System.out.println(list1.stream().reduce((s1, s2) -> s1.length() > s2.length() ? s2 : s1));
        // ʹ��max�ҵ����Ԫ�أ�min����
        System.out.println(list1.stream().max(Comparator.comparing(Function.identity())));
        // ʹ��count����
        System.out.println(list1.stream().count());
        // ʹ��findFirst��ȡ��һ��Ԫ��
        System.out.println(list1.stream().findFirst());
        // ʹ��findAny��ȡ��һԪ��
        System.out.println(list1.stream().filter(s -> s.length() > 1).findAny());
        // ʹ��anyMatch����һ�����������ͷ���true��allMatch��noneMatch����
        System.out.println(list1.stream().anyMatch(s -> s.length() > 5));
    }
}

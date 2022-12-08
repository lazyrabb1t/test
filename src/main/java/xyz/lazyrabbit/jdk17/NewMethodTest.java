package xyz.lazyrabbit.jdk17;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class NewMethodTest {
    public static void main(String[] args) throws IOException {
        List<Integer> integers = List.of(1, 2, 3, 4, 5, 6, 7, 1);
        System.out.printf("" + integers.stream().takeWhile(integer -> integer < 4).count());
        System.out.printf("" + Stream.ofNullable(null).count());
        String s = "123abd";
        System.out.println(s.isBlank());
        System.out.println(s.isEmpty());
        Files.writeString(
                Path.of("./", "tmp.txt"), // 路径
                "hello, jdk11 files api", // 内容
                StandardCharsets.UTF_8); // 编码
        String txt = Files.readString(
                Paths.get("./tmp.txt"), // 路径
                StandardCharsets.UTF_8); // 编码
        System.out.println(txt);
    }
}

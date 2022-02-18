package xyz.lazyrabbit.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author guoxw
 * @Description TODO
 * @createTime 2022年02月18日 15:08:00
 */
public class CommandUtils {

//    private static Logger log = LogManager.getLogger(CommandUtils.class);

    public static void exec(String... command) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder().command(command);
        Process process = processBuilder.start();
        try (BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
             BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
        }
        process.waitFor();
        final int i = process.exitValue();
        System.out.println(i);
    }
}

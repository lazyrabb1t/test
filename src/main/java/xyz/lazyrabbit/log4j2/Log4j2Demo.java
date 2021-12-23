package xyz.lazyrabbit.log4j2;

import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.lazyrabbit.log4j2.test1.A;
import xyz.lazyrabbit.log4j2.test2.B;

import java.util.concurrent.TimeUnit;

/**
 * @author guoxw
 * @Description TODO
 * @createTime 2021年12月22日 16:10:00
 */
public class Log4j2Demo {

    static Logger logger = LogManager.getLogger(Log4j2Demo.class);

    @SneakyThrows
    public static void main(String[] args) {
        int i = 0;
        while (true) {
            logger.trace("log trace @{}", i);
            logger.debug("log debug @{}", i);
            logger.info("log info @{}", i);
            logger.warn("log warn @{}", i);
            logger.error("log error @{}", i);
            logger.fatal("log fatal @{}", i);
            A a = new A();
            B b = new B();
            i++;
            TimeUnit.SECONDS.sleep(2);
        }

    }
}

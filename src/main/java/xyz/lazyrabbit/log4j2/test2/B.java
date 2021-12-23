package xyz.lazyrabbit.log4j2.test2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.lazyrabbit.log4j2.test1.A;

/**
 * @author guoxw
 * @Description TODO
 * @createTime 2021年12月21日 20:45:00
 */
public class B {

    Logger logger = LogManager.getLogger(B.class);

    public B() {
        String name = this.getClass().getName();
        logger.trace("log trace @{}", name);
        logger.debug("log debug @{}", name);
        logger.info("log info @{}", name);
        logger.warn("log warn @{}", name);
        logger.error("log error @{}", name);
        logger.fatal("log fatal @{}", name);
    }

    public static void main(String[] args) {
        new B();
    }
}

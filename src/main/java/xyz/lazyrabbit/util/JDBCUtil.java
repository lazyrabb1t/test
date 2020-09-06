package xyz.lazyrabbit.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JDBCUtil {
    private static String driverName = "";
    private static String url = "";
    private static String uname = "";
    private static String pwd = "";

    static {

        try {
            // Properties<String,String> extends HashTable<Object,Object>

            // 1.创建Properties对象
            Properties pro = new Properties();

            // 2.加载properties文件
            pro.load(JDBCUtil.class.getClassLoader().getResourceAsStream(
                    "db.properties"));

            // 3.读取properties文件中的数据
            driverName = pro.getProperty("driverName");
            url = pro.getProperty("url");
            uname = pro.getProperty("username");
            pwd = pro.getProperty("password");

            // 加载驱动
            Class.forName(driverName);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接
     *
     * @return
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, uname, pwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 获取Statement对象
     *
     * @param conn
     * @return
     */
    public static Statement getStatement(Connection conn) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stmt;
    }

    /**
     * 获取预编译的命令发送
     *
     * @param conn
     * @param sql
     * @return
     */
    public static PreparedStatement getPreparedStatement(Connection conn,
                                                         String sql) {
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pstmt;
    }

    /**
     * 释放资源
     *
     * @param rs
     * @param stmt
     * @param conn
     */
    public static void closeAll(ResultSet rs, Statement stmt, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 封装DML操作工具方法
     *
     * @param sql
     * @param params
     * @return
     */
    public static boolean executeDML(String sql, Object... params) {

        // 声明连接
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {

            // 获取连接
            conn = getConnection();
            // 取消事务的自动提交
            conn.setAutoCommit(false);

            // 获取命令发送器
            pstmt = getPreparedStatement(conn, sql);
            // 给SQL参数赋值
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }

            // 发送SQL并执行SQL
            int count = pstmt.executeUpdate();

            // 判断
            if (count == 0) {
                // 手动回滚事务
                conn.rollback();
                return false;
            } else {
                // 手动提交事务
                conn.commit();
                return true;
            }

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();

        } finally {// 释放资源
            closeAll(null, pstmt, conn);
        }

        return false;

    }

    /**
     * 封装查询操作的工具方法
     *
     * @param cls
     * @param sql
     * @param params
     * @return
     */
    public static <T> List<T> executeDQL(Class<T> cls, String sql,
                                         Object... params) {

        // 创建返回值对象
        List<T> list = new ArrayList<T>();

        // 声明连接
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            // 获取连接
            conn = getConnection();

            // 获取命令发送器
            pstmt = getPreparedStatement(conn, sql);
            // 给sql参数赋值
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }

            // 发送SQL并执行SQL
            rs = pstmt.executeQuery();

            // 获取结果集的元数据信息（列数，字段名称，总列数）
            ResultSetMetaData metadata = rs.getMetaData();

            // 遍历结果集
            while (rs.next()) {
                // 创建T对象
                T obj = cls.newInstance();

                // 获取结果集中的数据并且赋值给T对象
                for (int i = 1; i <= metadata.getColumnCount(); i++) {
                    BeanUtils.setProperty(obj, metadata.getColumnLabel(i)
                            .toLowerCase(), rs.getObject(i));

                }

                ConvertUtils.register(new DateConverter(null),
                        java.util.Date.class);

                // 将每条记录的对象存放至list集合中
                list.add(obj);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } finally {// 关闭资源
            closeAll(rs, pstmt, conn);
        }

        return list;
    }

}

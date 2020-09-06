package xyz.lazyrabbit.jdbc;

import java.sql.*;

public class Test {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/test?serverTimezone=GMT";

    static final String USERNAME = "root";
    static final String PASSWORD = "123456";

    public static void main(String[] args) {

        String sql = "SELECT id, username FROM t_user";
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);) {
            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                int id = rs.getInt("id");

                String username = rs.getString("username");

                //Display values
                System.out.print("ID: " + id);
                System.out.print(", username: " + username);
                System.out.print("\r\n");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

package org.example;

import java.sql.*;

public class Connect {
    public static void connect() {
        String rootPath = System.getProperty("user.dir");
        Connection conn = null;
        try {

            String url = "jdbc:sqlite:" + rootPath + "/db_ecommerce.db";
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private Connection connection() {

        String rootPath = System.getProperty("user.dir");
        String url = "jdbc:sqlite:" + rootPath + "/db_ecommerce.db";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void selectAll() throws SQLException{
        String sql = "SELECT * FROM users";

        try (Connection conn = connection();
             Statement statement  = conn.createStatement();
             ResultSet rs    = statement.executeQuery(sql)){

            while (rs.next()) {
                System.out.println(rs.getInt("id") +  "\t" +
                        rs.getString("last_name") + "\t" +
                        rs.getString("type"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

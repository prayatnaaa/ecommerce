package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;

public class Query {
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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public JSONArray selectAll() throws SQLException {
        JSONArray jsonArray = new JSONArray();
        String sql = "SELECT * FROM users";

        try (Connection conn = connection();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(sql);
        ) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String lastName = rs.getString("last_name");
                String type = rs.getString("type");

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", id);
                jsonObject.put("last_name", lastName);
                jsonObject.put("type", type);

                // Add the user JSON object to the array
                jsonArray.put(jsonObject);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return jsonArray;
    }

    public JSONArray selectUser(int userId) throws SQLException {
        JSONArray jsonArray = new JSONArray();
        String sql = "SELECT * FROM users WHERE id=" + userId;

        try (Connection conn = connection();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(sql);) {


            while (rs.next()) {
                int id = rs.getInt("id");
                String lastName = rs.getString("last_name");
                String type = rs.getString("type");

                // Create a JSON object for the user
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", id);
                jsonObject.put("last_name", lastName);
                jsonObject.put("type", type);

                // Add the user JSON object to the array
                jsonArray.put(jsonObject);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return jsonArray;
    }

    public void deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE id=" + userId;
        try (Connection conn = connection();
             Statement statement = conn.createStatement();) {
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Data User Id" + userId + "has been removed!");
    }
}


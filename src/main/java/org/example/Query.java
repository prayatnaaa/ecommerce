package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
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
        System.out.println("Data User Id " + userId + " has been removed!");
    }

    public String postMethod(JSONObject reqBodyJSON){
        String first_name = reqBodyJSON.optString("first_name");
        String last_name = reqBodyJSON.optString("last_name");
        String email = reqBodyJSON.optString("email");
        String phone_number = reqBodyJSON.optString("phone_number");
        String type = reqBodyJSON.optString("type");
        PreparedStatement statement = null;
        int rowsAffected = 0;

        String query = "INSERT INTO users (first_name, last_name, email, phone_number, type) VALUES(?,?,?,?,?)";
        try{
            statement = this.connection().prepareStatement(query);
            statement.setString(1, first_name);
            statement.setString(2, last_name);
            statement.setString(3, email);
            statement.setString(4, phone_number);
            statement.setString(5, type);
            rowsAffected = statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return rowsAffected + " row has been inserted!";
    }

    public String putMethod(String path, JSONObject reqBodyJSON){
        String[] pathParts = path.split("/");
        int userId = Integer.parseInt(pathParts[pathParts.length - 1]);
        String first_name = reqBodyJSON.optString("first_name");
        String last_name = reqBodyJSON.optString("last_name");
        String email = reqBodyJSON.optString("email");
        String phone_number = reqBodyJSON.optString("phone_number");
        String type = reqBodyJSON.optString("type");
        PreparedStatement statement = null;
        int rowsAffected = 0;

        String query = "UPDATE users SET first_name = ?, last_name = ?, email = ?, phone_number = ?, type = ? WHERE id=" +userId;
        try {
            statement = this.connection().prepareStatement(query);
            statement.setString(1, first_name);
            statement.setString(2, last_name);
            statement.setString(3, email);
            statement.setString(4, phone_number);
            statement.setString(5, type);
            rowsAffected = statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return rowsAffected + " row has been updated!";
    }
}


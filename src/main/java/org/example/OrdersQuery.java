package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;

public class OrdersQuery {

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
        String sql = "SELECT * FROM orders";

        try (Connection conn = connection();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(sql);
        ) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int buyer = rs.getInt("buyer");
                int note = rs.getInt("note");
                int total = rs.getInt("total");
                int discount = rs.getInt("discount");
                int is_paid = rs.getInt("stock");

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", id);
                jsonObject.put("buyer", buyer);
                jsonObject.put("note", note);
                jsonObject.put("total", total);
                jsonObject.put("discount", discount);
                jsonObject.put("is_paid", is_paid);

                jsonArray.put(jsonObject);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return jsonArray;
    }

    public JSONArray selectOrder (int productId) throws SQLException {
        JSONArray jsonArray = new JSONArray();
        String sql = "SELECT * FROM orders WHERE id=" + productId;

        try (Connection conn = connection();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(sql);) {


            while (rs.next()) {
                int id = rs.getInt("id");
                int buyer = rs.getInt("buyer");
                int note = rs.getInt("note");
                int total = rs.getInt("total");
                int discount = rs.getInt("discount");
                int is_paid = rs.getInt("stock");

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", id);
                jsonObject.put("buyer", buyer);
                jsonObject.put("note", note);
                jsonObject.put("total", total);
                jsonObject.put("discount", discount);
                jsonObject.put("is_paid", is_paid);

                jsonArray.put(jsonObject);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return jsonArray;
    }

    public void deleteOrder(int ordersId) {
        String sql = "DELETE FROM orders WHERE id=" + ordersId;
        try (Connection conn = connection();
             Statement statement = conn.createStatement();) {
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Data Products Id " + ordersId + " has been removed!");
    }


    public String postMethod(JSONObject reqBodyJSON){
        int buyer = reqBodyJSON.optInt("buyer");
        int note = reqBodyJSON.optInt("note");
        int total = reqBodyJSON.optInt("total");
        int discount = reqBodyJSON.optInt("discount");
        int is_paid = reqBodyJSON.optInt("is_paid");
        PreparedStatement statement = null;
        int rowsAffected = 0;

        String query = "INSERT INTO orders (buyer, note, total, discount, is_paid) VALUES(?,?,?,?,?)";
        try{
            statement = this.connection().prepareStatement(query);
            statement.setInt(1, buyer);
            statement.setInt(2, note);
            statement.setInt(3, total);
            statement.setInt(4, discount);
            statement.setInt(5, is_paid);
            rowsAffected = statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return rowsAffected + " row has been inserted!";
    }

    public String putMethod(String path, JSONObject reqBodyJSON){
        String[] pathParts = path.split("/");
        int orderId = Integer.parseInt(pathParts[pathParts.length - 1]);
        int buyer = reqBodyJSON.optInt("buyer");
        int note = reqBodyJSON.optInt("note");
        int total = reqBodyJSON.optInt("total");
        int discount = reqBodyJSON.optInt("discount");
        int is_paid = reqBodyJSON.optInt("is_paid");
        PreparedStatement statement = null;
        int rowsAffected = 0;

        String query = "UPDATE orders SET buyer = ?, note = ?, total = ?, discount = ?, is_paid = ? WHERE id=" + orderId;
        try {
            statement = this.connection().prepareStatement(query);
            statement.setInt(1, buyer);
            statement.setInt(2, note);
            statement.setInt(3, total);
            statement.setInt(4, discount);
            statement.setInt(5, is_paid);
            rowsAffected = statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return rowsAffected + " row has been updated!";
    }
}

package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;

public class ordersDetail {

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
                int order = rs.getInt("order");
                int product = rs.getInt("product");
                int quantity = rs.getInt("quantity");
                int price = rs.getInt("price");

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("order", order);
                jsonObject.put("product", product);
                jsonObject.put("quantity", quantity);
                jsonObject.put("price", price);

                jsonArray.put(jsonObject);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return jsonArray;
    }

    public JSONArray selectOrdersDetail (int orderDetail) throws SQLException {
        JSONArray jsonArray = new JSONArray();
        String sql = "SELECT * FROM orders WHERE id=" + orderDetail;

        try (Connection conn = connection();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(sql);) {


            while (rs.next()) {
                int order = rs.getInt("order");
                int product = rs.getInt("product");
                int quantity = rs.getInt("quantity");
                int price = rs.getInt("price");

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("order", order);
                jsonObject.put("product", product);
                jsonObject.put("quantity", quantity);
                jsonObject.put("price", price);

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
        System.out.println("Data Order's Detail Id " + ordersId + " has been removed!");
    }


    public String postMethod(JSONObject reqBodyJSON){
        int order = reqBodyJSON.optInt("order");
        int product = reqBodyJSON.optInt("product");
        int quantity = reqBodyJSON.optInt("quantity");
        int price = reqBodyJSON.optInt("price");
        PreparedStatement statement = null;
        int rowsAffected = 0;

        String query = "INSERT INTO orders (order, product, quantity, price) VALUES(?,?,?,?)";
        try{
            statement = this.connection().prepareStatement(query);
            statement.setInt(1, order);
            statement.setInt(2, product);
            statement.setInt(3, quantity);
            statement.setInt(4, price);
            rowsAffected = statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return rowsAffected + " row has been inserted!";
    }

    public String putMethod(String path, JSONObject reqBodyJSON){
        String[] pathParts = path.split("/");
        int orderId = Integer.parseInt(pathParts[pathParts.length - 1]);
        int order = reqBodyJSON.optInt("order");
        int product = reqBodyJSON.optInt("product");
        int quantity = reqBodyJSON.optInt("quantity");
        int price = reqBodyJSON.optInt("price");
        PreparedStatement statement = null;
        int rowsAffected = 0;

        String query = "UPDATE orders SET order = ?, product = ?, quantity = ?, price = ? WHERE order=" + orderId;
        try {
            statement = this.connection().prepareStatement(query);
            statement.setInt(1, order);
            statement.setInt(2, product);
            statement.setInt(3, quantity);
            statement.setInt(4, price);
            rowsAffected = statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return rowsAffected + " row has been updated!";
    }
}
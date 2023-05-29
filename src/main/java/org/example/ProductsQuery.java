package org.example;
import org.json.JSONArray;
import org.json.JSONObject;
import java.sql.*;

public class ProductsQuery {

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
        String sql = "SELECT * FROM products";

        try (Connection conn = connection();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(sql);
        ) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String seller = rs.getString("seller");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String price = rs.getString("price");
                String stock = rs.getString("stock");

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", id);
                jsonObject.put("seller", seller);
                jsonObject.put("title", title);
                jsonObject.put("description", description);
                jsonObject.put("price", price);
                jsonObject.put("stock", stock);

                jsonArray.put(jsonObject);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return jsonArray;
    }

    public JSONArray selectProduct (int productId) throws SQLException {
        JSONArray jsonArray = new JSONArray();
        String sql = "SELECT * FROM products WHERE id=" + productId;

        try (Connection conn = connection();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(sql);) {


            while (rs.next()) {
                int id = rs.getInt("id");
                String seller = rs.getString("seller");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String price = rs.getString("price");
                String stock = rs.getString("stock");

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", id);
                jsonObject.put("seller", seller);
                jsonObject.put("title", title);
                jsonObject.put("description", description);
                jsonObject.put("price", price);
                jsonObject.put("stock", stock);

                jsonArray.put(jsonObject);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return jsonArray;
    }

    public void deleteProduct(int userId) {
        String sql = "DELETE FROM products WHERE id=" + userId;
        try (Connection conn = connection();
             Statement statement = conn.createStatement();) {
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Data Products Id " + userId + " has been removed!");
    }


    public String postMethod(JSONObject reqBodyJSON){
        String seller = reqBodyJSON.optString("seller");
        String title = reqBodyJSON.optString("title");
        String description = reqBodyJSON.optString("description");
        String price = reqBodyJSON.optString("price");
        String stock = reqBodyJSON.optString("stock");
        PreparedStatement statement = null;
        int rowsAffected = 0;

        String query = "INSERT INTO products (seller, title, description, price, stock) VALUES(?,?,?,?,?)";
        try{
            statement = this.connection().prepareStatement(query);
            statement.setString(1, seller);
            statement.setString(2, title);
            statement.setString(3, description);
            statement.setString(4, price);
            statement.setString(5, stock);
            rowsAffected = statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return rowsAffected + " row has been inserted!";
    }

    public String putMethod(String path, JSONObject reqBodyJSON){
        String[] pathParts = path.split("/");
        int userId = Integer.parseInt(pathParts[pathParts.length - 1]);
        String first_name = reqBodyJSON.optString("seller");
        String last_name = reqBodyJSON.optString("title");
        String email = reqBodyJSON.optString("description");
        String phone_number = reqBodyJSON.optString("price");
        String type = reqBodyJSON.optString("stock");
        PreparedStatement statement = null;
        int rowsAffected = 0;

        String query = "UPDATE products SET seller = ?, title = ?, description = ?, price = ?, stock = ? WHERE id=" +userId;
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


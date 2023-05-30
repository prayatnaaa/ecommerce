package org.example;
import org.json.JSONArray;
import org.json.JSONObject;
import java.sql.*;

public class UsersQuery {

    String queryField;
    int queryValue;
    String queryCondition;
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


    private static Connection connection() {

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
                String first_name = rs.getString("first_name");
                String email = rs.getString("email");
                String last_name = rs.getString("last_name");
                String phone_number = rs.getString("phone_number");
                String type = rs.getString("type");

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", id);
                jsonObject.put("first_name", first_name);
                jsonObject.put("last_name", last_name);
                jsonObject.put("email", email);
                jsonObject.put("phone_number", phone_number);
                jsonObject.put("type", type);

                jsonArray.put(jsonObject);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return jsonArray;
    }

    public JSONArray selectUser(int userId) throws SQLException {
        JSONArray jsonArray = new JSONArray();
        String query = "SELECT * FROM users WHERE id=" + userId;

        try (Connection conn = connection();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(query);) {


            while (rs.next()) {
                int id = rs.getInt("id");
                String first_name = rs.getString("first_name");
                String last_name = rs.getString("last_name");
                String email = rs.getString("email");
                String phone_number = rs.getString("phone_number");
                String type = rs.getString("type");

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", id);
                jsonObject.put("first_name", first_name);
                jsonObject.put("last_name", last_name);
                jsonObject.put("email", email);
                jsonObject.put("phone_number", phone_number);
                jsonObject.put("type", type);

                jsonArray.put(jsonObject);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return jsonArray;
    }

    public void deleteUser(int userId) {
        String query = "DELETE FROM users WHERE id=" + userId;
        try (Connection conn = connection();
             Statement statement = conn.createStatement();) {
            statement.executeUpdate(query);

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

    public static String getUsersProducts(String[] path){
        String response = "";
        if(path.length == 2){
            response = usersProducts(0);
        }else if(path.length == 3){
            response = usersProducts(Integer.parseInt(path[2]));
        }else if(path.length == 4){
            response = usersProducts(Integer.parseInt(path[2]));
        }
        return response;
    }


    public static String usersProducts(int userId){

        JSONArray jsonArray = new JSONArray();
        String query = "SELECT * FROM products WHERE seller=" + userId;

        try (Connection conn = connection();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(query);) {

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
        return jsonArray.toString();
    }

    public static String usersOrders(int userId){

        JSONArray jsonArray = new JSONArray();
        String query = "SELECT * FROM orders WHERE buyer=" + userId;

        try (Connection conn = connection();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(query);) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String buyer = rs.getString("buyer");
                int note = rs.getInt("note");
                int total = rs.getInt("total");
                int discount = rs.getInt("discount");
                int is_paid = rs.getInt("is_paid");

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
        return jsonArray.toString();
    }

    public static String getUsersOrders(String[] path){
        String response = "";
        if(path.length == 2){
            response = usersOrders(0);
        }else if(path.length == 3){
            response = usersOrders(Integer.parseInt(path[2]));
        }else if(path.length == 4){
            response = usersOrders(Integer.parseInt(path[2]));
        }
        return response;
    }

    public String userField(String field, String cond, int val){

        JSONArray jsonArray = new JSONArray();
        String query = "SELECT * FROM users WHERE " + field + cond + "'" + val + "'";

        try (Connection conn = connection();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String first_name = rs.getString("first_name");
                String email = rs.getString("email");
                String last_name = rs.getString("last_name");
                String phone_number = rs.getString("phone_number");
                String type = rs.getString("type");

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", id);
                jsonObject.put("first_name", first_name);
                jsonObject.put("last_name", last_name);
                jsonObject.put("email", email);
                jsonObject.put("phone_number", phone_number);
                jsonObject.put("type", type);

                jsonArray.put(jsonObject);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return jsonArray.toString();
    }

    public String userType(String userType){

        JSONArray jsonArray = new JSONArray();
        String query = "SELECT * FROM users WHERE type =" + userType;

        try (Connection conn = connection();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(query);) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String first_name = rs.getString("first_name");
                String email = rs.getString("email");
                String last_name = rs.getString("last_name");
                String phone_number = rs.getString("phone_number");
                String type = rs.getString("type");

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", id);
                jsonObject.put("first_name", first_name);
                jsonObject.put("last_name", last_name);
                jsonObject.put("email", email);
                jsonObject.put("phone_number", phone_number);
                jsonObject.put("type", type);

                jsonArray.put(jsonObject);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return jsonArray.toString();
    }
}
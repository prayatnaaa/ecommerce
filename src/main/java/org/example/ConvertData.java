package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ConvertData {

    public static ResultSet RetrieveData() throws Exception {

        String rootPath = System.getProperty("user.dir");
        //Getting the connection
        String url = "jdbc:sqlite:" + rootPath + "/db_ecommerce.db";
        Connection con = DriverManager.getConnection(url);
        System.out.println("Connection established......");
        //Creating the Statement
        Statement statement = con.createStatement();
        //Retrieving the records
        ResultSet rs = statement.executeQuery("Select * from users");
        return rs;
    }

    public JSONArray DataToJSON() throws Exception {

        String desktopPath = System.getProperty("user.home") + "/Desktop";
        String filePath = desktopPath + "/KULIAH SEMESTER 2/ecommerce/db_ecommerce.json";
        //Creating a JSONObject object
        JSONObject jsonObject = new JSONObject();
        //Creating a json array
        JSONArray array = new JSONArray();
        ResultSet rs = RetrieveData();
        //Inserting ResutlSet data into the json object
        while(rs.next()) {
            JSONObject record = new JSONObject();
            //Inserting key-value pairs into the json object
            record.put("id", rs.getInt("id"));
            record.put("first_name", rs.getString("first_name"));
            record.put("last_name", rs.getString("last_name"));
            record.put("type", rs.getString("type"));
            array.add(record);
        }
        jsonObject.put("Members_data", array);
        try {
            FileWriter file = new FileWriter(filePath);
            file.write(jsonObject.toJSONString());
            file.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("JSON file created......");
        return array;
    }
}


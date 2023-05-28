package org.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class serverConnection {

private static final int p=8068;

    public static void startServer() throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress("localhost", p), 0);

        httpServer.createContext("/", new Handler());
        httpServer.setExecutor(Executors.newSingleThreadExecutor());
        httpServer.start();
    }
        private static class Handler implements HttpHandler {

            @Override
            public void handle(HttpExchange exchange) throws IOException {
                Query connect = new Query();

                String ResponseToSendBack = "";
                OutputStream outputStream = exchange.getResponseBody();
                String method = exchange.getRequestMethod();
                String path = exchange.getRequestURI().getPath();


                if ("GET".equals(method) && "/users".equals(path)) {
                    outputStream = exchange.getResponseBody();
                    try {
                        ResponseToSendBack = connect.selectAll().toString();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                } else if ("GET".equals(method) && path.matches("/user/\\d+")) {
                    String[] pathParts = path.split("/");
                    int userId = Integer.parseInt(pathParts[pathParts.length - 1]);

                    outputStream = exchange.getResponseBody();
                    try {
                        ResponseToSendBack = connect.selectUser(userId).toString();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                } else if("DELETE".equals(method) && path.matches("/user/\\d+")){
                    String[] pathParts = path.split("/");
                    int userId = Integer.parseInt(pathParts[pathParts.length - 1]);

                    outputStream = exchange.getResponseBody();
                    try {
                        connect.deleteUser(userId);
                        String response = "User with ID " + userId + " has been deleted.";
                        exchange.sendResponseHeaders(200, response.length());
                        outputStream.write(response.getBytes());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else if("POST".equals(method) && path.matches("/user")){
                    JSONObject requestBodyJSON = parseRequestBody(exchange.getRequestBody());
                    ResponseToSendBack = connect.postMethod(requestBodyJSON);
                } else if ("PUT".equals(method) && path.matches("/user/\\d+")){
                    JSONObject requestBodyJSON = parseRequestBody(exchange.getRequestBody());
                    ResponseToSendBack = connect.putMethod(path, requestBodyJSON);

                }
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, ResponseToSendBack.length());
                outputStream.write(ResponseToSendBack.getBytes());
                outputStream.flush();
                outputStream.close();
            }

        }

        private static JSONObject parseRequestBody(InputStream reqBody) throws IOException{

            byte[] requestBodyBytes = reqBody.readAllBytes();
            String reqBodyString = new String(requestBodyBytes);
            return new JSONObject(reqBodyString);
        }
    }

package org.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
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

                String ResponseToSendBack = null;
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

                } else if ("GET".equals(method) && path.matches("/users/\\d+")) {
                    String[] pathParts = path.split("/");
                    int userId = Integer.parseInt(pathParts[pathParts.length - 1]);

                    outputStream = exchange.getResponseBody();
                    try {
                        ResponseToSendBack = connect.selectUser(userId).toString();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                } else if("DELETE".equals(method) && path.matches("/users/\\d+")){
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
                }
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, ResponseToSendBack.length());
                outputStream.write(ResponseToSendBack.getBytes());
                outputStream.flush();
                outputStream.close();
            }
        }
    }

package org.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws IOException {
//        HttpServer httpServer = HttpServer.create(new InetSocketAddress("localhost", 4040), 0);
//
//        httpServer.createContext("/melbourne", new MelbourneHandler());
//        httpServer.setExecutor(Executors.newSingleThreadExecutor());
//        httpServer.start();

 Connect.connect();


    }

//    public static class MelbourneHandler implements HttpHandler {
//
//        @Override
//        public void handle(HttpExchange exchange) throws IOException{
//
//            if("GET".equals(exchange.getRequestMethod())){
//
//                OutputStream outputStream = exchange.getResponseBody();
//                String ResponseToSendBack = "Hello from Melbourne";
//                exchange.sendResponseHeaders(200, ResponseToSendBack.length());
//
//                outputStream.write(ResponseToSendBack.getBytes());
//                outputStream.flush();
//                outputStream.close();
//            }
//        }
//    }
}
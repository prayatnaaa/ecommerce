package org.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.json.JSONArray;
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

        String type;
        String field;
        String cond;
        int val;

        public void queryCheck(String query){

            String[] querys = query.split("&");
            for ( String i : querys) {
                if(i.contains("type")){
                    this.type = i.substring(i.lastIndexOf("=") + 1);
                }else if (i.contains("field")){
                    this.field = i.substring(i.lastIndexOf("=")+1);

                } else if (i.contains("val")) {
                    this.val = Integer.parseInt(i.substring(i.lastIndexOf("=")+1));
                } else if (i.contains("cond")) {
                    String cond = i.substring(i.lastIndexOf("=")+1);
                    if (cond.equals("larger")){
                        this.cond = ">";
                    } else if (cond.equals("smaller")){
                        this.cond = "<";
                    }else if (cond.equals("smallerEqual")){
                        this.cond = "<=";
                    }else if (cond.equals("largerEqual")){
                        this.cond = ">=";
                    }
                }
            }

        }

        @Override
            public void handle(HttpExchange exchange) throws IOException {
                UsersQuery connect = new UsersQuery();
                ProductsQuery connect2 = new ProductsQuery();
                OrdersQuery connect3 = new OrdersQuery();
                ordersDetail connect4 = new ordersDetail();

                String ResponseToSendBack = "";
                OutputStream outputStream = exchange.getResponseBody();
                String method = exchange.getRequestMethod();
                String path = exchange.getRequestURI().getPath();
                String[] pathRoot = exchange.getRequestURI().getPath().split("/");
                String query = exchange.getRequestURI().getQuery();


                if(query != null){
                     if(query.matches("field")  && "GET".equals(method)){
                        outputStream = exchange.getResponseBody();
                        try {
                            queryCheck(query);
                            ResponseToSendBack = connect.userField(this.field, this.cond, this.val).toString();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }else if(query.contains("type")  && "GET".equals(method)) {
                         outputStream = exchange.getResponseBody();
                         try {
                             queryCheck(query);
                             System.out.println(this.type);
                             ResponseToSendBack = connect.userType(this.type).toString();
                         } catch (Exception e) {
                             throw new RuntimeException(e);
                         }
                    }
                }else if ("GET".equals(method) && "/users".equals(path)) {
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
                }else if ("GET".equals(method) && "/ordersDetails".equals(path)) {
                    outputStream = exchange.getResponseBody();
                    try {
                        ResponseToSendBack = connect4.selectAll().toString();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else if ("GET".equals(method) && path.matches("/ordersDetails/\\d+")) {
                    String[] pathParts = path.split("/");
                    int odId = Integer.parseInt(pathParts[pathParts.length - 1]);
                    outputStream = exchange.getResponseBody();
                    try {
                        ResponseToSendBack = connect4.selectOrdersDetail(odId).toString();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

                else if ("GET".equals(method) && "/orders".equals(path)) {
                    outputStream = exchange.getResponseBody();
                    try {
                        ResponseToSendBack = connect3.selectAll().toString();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else if ("GET".equals(method) && path.matches("/order/\\d+")) {
                    String[] pathParts = path.split("/");
                    int orderId = Integer.parseInt(pathParts[pathParts.length - 1]);
                    outputStream = exchange.getResponseBody();
                    try {
                        ResponseToSendBack = connect3.selectOrder(orderId).toString();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

                else if("GET".equals(method)
                        && pathRoot[1].matches("user")
                        && pathRoot[3].matches("product")){
                    ResponseToSendBack = UsersQuery.getUsersProducts(pathRoot);
                } else if("GET".equals(method)
                        && pathRoot[1].matches("user")
                        && pathRoot[3].matches("order")){
                    ResponseToSendBack = UsersQuery.getUsersOrders(pathRoot);
                }

                else if("DELETE".equals(method) && path.matches("/user/\\d+")){
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
                } else if("DELETE".equals(method) && path.matches("/product/\\d+")){
                    String[] pathParts = path.split("/");
                    int productId = Integer.parseInt(pathParts[pathParts.length - 1]);
                    outputStream = exchange.getResponseBody();
                    try {
                        connect2.deleteProduct(productId);
                        String response = "Product with ID " + productId + " has been deleted.";
                        exchange.sendResponseHeaders(200, response.length());
                        outputStream.write(response.getBytes());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else if("DELETE".equals(method) && path.matches("/order/\\d+")){
                    String[] pathParts = path.split("/");
                    int orderId = Integer.parseInt(pathParts[pathParts.length - 1]);
                    outputStream = exchange.getResponseBody();
                    try {
                        connect3.deleteOrder(orderId);
                        String response = "Product with ID " + orderId + " has been deleted.";
                        exchange.sendResponseHeaders(200, response.length());
                        outputStream.write(response.getBytes());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else if("DELETE".equals(method) && path.matches("/ordersDetails/\\d+")){
                    String[] pathParts = path.split("/");
                    int odId = Integer.parseInt(pathParts[pathParts.length - 1]);
                    outputStream = exchange.getResponseBody();
                    try {
                        connect.deleteUser(odId);
                        String response = "Orders Details with ID " + odId + " has been deleted.";
                        exchange.sendResponseHeaders(200, response.length());
                        outputStream.write(response.getBytes());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

                else if("POST".equals(method) && path.matches("/user")){
                    JSONObject requestBodyJSON = parseRequestBody(exchange.getRequestBody());
                    ResponseToSendBack = connect.postMethod(requestBodyJSON);
                }else if("POST".equals(method) && path.matches("/product")){
                    JSONObject requestBodyJSON = parseRequestBody(exchange.getRequestBody());
                    ResponseToSendBack = connect2.postMethod(requestBodyJSON);
                }else if("POST".equals(method) && path.matches("/order")){
                    JSONObject requestBodyJSON = parseRequestBody(exchange.getRequestBody());
                    ResponseToSendBack = connect3.postMethod(requestBodyJSON);
                }else if("POST".equals(method) && path.matches("/ordersDetails")){
                    JSONObject requestBodyJSON = parseRequestBody(exchange.getRequestBody());
                    ResponseToSendBack = connect4.postMethod(requestBodyJSON);
                }

                else if ("PUT".equals(method) && path.matches("/user/\\d+")){
                    JSONObject requestBodyJSON = parseRequestBody(exchange.getRequestBody());
                    ResponseToSendBack = connect.putMethod(path, requestBodyJSON);
                }else if ("PUT".equals(method) && path.matches("/product/\\d+")){
                    JSONObject requestBodyJSON = parseRequestBody(exchange.getRequestBody());
                    ResponseToSendBack = connect2.putMethod(path, requestBodyJSON);
                }else if ("PUT".equals(method) && path.matches("/order/\\d+")){
                    JSONObject requestBodyJSON = parseRequestBody(exchange.getRequestBody());
                    ResponseToSendBack = connect3.putMethod(path, requestBodyJSON);
                }else if ("PUT".equals(method) && path.matches("/orderDetails/\\d+")){
                    JSONObject requestBodyJSON = parseRequestBody(exchange.getRequestBody());
                    ResponseToSendBack = connect4.putMethod(path, requestBodyJSON);
                }

                else if ("GET".equals(method) && path.matches("/product/\\d+")){
                    String[] pathParts = exchange.getRequestURI().getPath().split("/");
                    int productsId = Integer.parseInt(pathParts[pathParts.length - 1]);
                    outputStream = exchange.getResponseBody();
                    try {
                        ResponseToSendBack = connect2.selectProduct(productsId).toString();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }else if ("GET".equals(method) && "/products".equals(path)) {
                    outputStream = exchange.getResponseBody();
                    try {
                        ResponseToSendBack = connect2.selectAll().toString();
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

        private static JSONObject parseRequestBody(InputStream reqBody) throws IOException{

            byte[] requestBodyBytes = reqBody.readAllBytes();
            String reqBodyString = new String(requestBodyBytes);
            return new JSONObject(reqBodyString);
        }
    }

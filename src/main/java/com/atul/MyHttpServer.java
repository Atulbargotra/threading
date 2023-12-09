package com.atul;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MyHttpServer {
    public static void main(String[] args) throws IOException {
        String text = new String(Files.readAllBytes(Paths.get("resources/a.txt")));
        startServer();
    }

    public static void startServer() throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(8000), 0);
        httpServer.createContext("/search", new WordCountHandler());
        Executor executor = Executors.newFixedThreadPool(1);
        httpServer.setExecutor(executor);
        httpServer.start();
    }

    private static class WordCountHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String query = exchange.getRequestURI().getQuery();
            String s = new String("Hello");
            String [] keyValue = query.split("=");
            long count = 100L;
            byte[] bytes = Long.toString(count).getBytes();
            exchange.sendResponseHeaders(
                    200, bytes.length);
            OutputStream responseBody = exchange.getResponseBody();
            responseBody.write(bytes);


            List<Integer> list = Arrays.asList(5, 6, 7);
            list.stream().map(num->CompletableFuture.supplyAsync(()->getNumber(num))).map(CompletableFuture->CompletableFuture.thenApply(
                    n->n*n)).map(CompletableFuture::join).forEach(System.out::println);
        }
    }

    private static int getNumber(int a){
        return a*a;
    }




}

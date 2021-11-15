package org.davistiba;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author davis t.
 * Simple Java server, using built-in libraries only.
 * Listens on localhost, port 9999
 */
public class App {

    static HttpServer server;

    static final ExecutorService executor = Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws IOException {

        server = HttpServer.create(new InetSocketAddress(9999), 0);

        server.createContext("/", new doHandle());
        server.createContext("/quotes", new MagicQuoteHandler());
        server.setExecutor(executor);
        System.out.println("Listening on port " + server.getAddress());
        server.start();

    }

    /**
     * handles the Request. Returns an HTML page
     * regardless of request METHOD
     */
    public static class doHandle implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            InputStream in = exchange.getRequestBody();
            OutputStream out = exchange.getResponseBody();
            Headers headers = exchange.getResponseHeaders();


            try (BufferedInputStream br = new BufferedInputStream(in)) {

                //read client message
                while (br.available() != 0) {
                    System.out.print((char) br.read());
                }

                System.out.println(); //skip line

                //send back HTML page
                String sb = new String(Files.readAllBytes(Paths.get("mama.html")), StandardCharsets.UTF_8);

                headers.set("Content-Type", "text/html");
                exchange.sendResponseHeaders(200, sb.length());
                out.write(sb.getBytes(StandardCharsets.UTF_8));
                out.close();
            }
        }
    }
}

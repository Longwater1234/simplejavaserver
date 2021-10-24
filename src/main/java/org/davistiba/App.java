package org.davistiba;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.json.JSONObject;

import java.io.*;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author davis t.
 * Simple Java server, using built-in libraries.
 * Listens on localhost, port 9999
 */
public class App {

    static HttpServer server;

    static ExecutorService executor = Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws IOException {

        server = HttpServer.create(new InetSocketAddress(9999), 0);

        server.createContext("/", new doHandle());
        server.setExecutor(executor);
        System.out.println("Listening on port " + server.getAddress());
        server.start();

    }

    /**
     * handles the Request. Returns 200 OK JSON response
     * regardless oF request METHOD
     */
    public static class doHandle implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {

            InputStream in = exchange.getRequestBody();
            OutputStream out = exchange.getResponseBody();
            Headers headers = exchange.getResponseHeaders();

            try (BufferedInputStream br = new BufferedInputStream(in)) {

                //read the request body
                while (br.read() != -1) {
                    //FIXME: always skips first character
                    System.out.println(new String(br.readAllBytes()));
                }

                //send back response
                final JSONObject baba = new JSONObject();
                baba.put("message", "hello");
                baba.put("datetime", LocalDateTime.now());

                BufferedOutputStream bout = new BufferedOutputStream(out);

                headers.set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, baba.toString().length());
                bout.write(baba.toString().getBytes());
                bout.close();
            }
        }
    }
}

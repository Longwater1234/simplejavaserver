package org.davistiba;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author davis t.
 * Simple Java server, using built-in libraries.
 * Listens on localhost, port 9999
 */
public class App {

    static HttpServer server;

    static ExecutorService executor = Executors.newFixedThreadPool(4);

    public static void main(String[] args) throws IOException {

        server = HttpServer.create(new InetSocketAddress(9999), 0);

        server.createContext("/", new doHandle());
        server.setExecutor(executor);
        System.out.println("Listening on port " + server.getAddress());
        server.start();

    }

    /**
     * handles the Request. Returns 200 OK response
     * regardless oF request METHOD
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

                System.out.println(); //skip a line.

                //return simple HTML page
                final String baba = String.format("<h1>hello, the local time is %s</h1> %n",
                                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                     
                BufferedOutputStream bout = new BufferedOutputStream(out);

                headers.set("Content-Type", "text/html");
                exchange.sendResponseHeaders(200, baba.length());
                bout.write(baba.getBytes());
                bout.close();
            }
        }
    }
}

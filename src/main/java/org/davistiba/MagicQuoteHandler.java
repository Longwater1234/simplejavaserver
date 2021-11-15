package org.davistiba;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class MagicQuoteHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        OutputStream out = exchange.getResponseBody();
        Headers headers = exchange.getResponseHeaders();

        BufferedOutputStream bout = new BufferedOutputStream(out);

        String quote = MagicQuotes.getQuote();

        headers.set("Content-Type", "text/html");
        exchange.sendResponseHeaders(200, quote.length());
        bout.write(quote.getBytes(StandardCharsets.UTF_8));
        bout.close();
    }
}

package com.example.cocktails;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class StaticHttpHandler implements HttpHandler {

    private final String resourcePath;

    public StaticHttpHandler(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestPath = exchange.getRequestURI().getPath().substring(1);  // Remove leading '/'
        String filePath = resourcePath + "/" + (requestPath.isEmpty() ? "index.html" : requestPath);

        try (InputStream resourceStream = getClass().getClassLoader().getResourceAsStream(filePath)) {
            if (resourceStream == null) {
                // Resource not found
                sendResponse(exchange, HttpURLConnection.HTTP_NOT_FOUND, "text/html", "<html><body><h1>Not Found</h1></body></html>".getBytes());
            } else {
                // Resource found, send it as response
                String contentType = getContentType(requestPath);
                byte[] resourceBytes = resourceStream.readAllBytes();
                sendResponse(exchange, HttpURLConnection.HTTP_OK, contentType, resourceBytes);
            }
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String contentType, byte[] responseBytes) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "text/html");
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }

    private String getContentType(String filePath) {
        if (filePath.endsWith(".html")) {
            return "text/html";
        } else if (filePath.endsWith(".js")) {
            return "application/javascript";
        } else {
            // Default to plain text if the file type is not recognized
            return "text/plain";
        }
    }

}

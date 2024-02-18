package com.example.cocktails.controller;

import com.sun.net.httpserver.*;
import org.json.simple.*;

import java.io.*;
import java.net.*;
import java.util.*;

public abstract class AbstractController implements HttpHandler {

    @Override
    public final void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        try {
            switch (method) {
                case "PATCH":
                    handlePatchRequest(exchange);
                    sendResponse(exchange, HttpURLConnection.HTTP_NO_CONTENT, null);
                    break;
                case "GET":
                    Object result = handleGetRequest(exchange);
                    if (result != null) {
                        sendResponse(exchange, HttpURLConnection.HTTP_OK, result);
                        break;
                    }
                default:
                    sendResponse(exchange, HttpURLConnection.HTTP_NOT_FOUND, Map.of("error", "Not Found"));
            }
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, HttpURLConnection.HTTP_INTERNAL_ERROR, Map.of("error", "Internal Server Error"));
        }
    }

    protected Object handleGetRequest(HttpExchange exchange) throws IOException {
        return null;
    }

    protected void handlePatchRequest(HttpExchange exchange) throws IOException {
        // empty
    }

    private void sendResponse(HttpExchange exchange, int statusCode, Object responseBody) throws IOException {
        if (responseBody == null) {
            exchange.sendResponseHeaders(statusCode, -1);
        } else {
            byte[] responseBytes = Jsoner.serialize(responseBody).getBytes();

            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(statusCode, responseBytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(responseBytes);
            }
        }
    }

}

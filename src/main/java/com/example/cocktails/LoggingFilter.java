package com.example.cocktails;

import com.sun.net.httpserver.*;

import java.io.*;

public class LoggingFilter extends Filter {

    @Override
    public void doFilter(HttpExchange exchange, Chain chain) throws IOException {
        try {
            chain.doFilter(exchange);
        } finally {
            System.out.printf("%s %25s -> %s Content-Type: %s\n",
                    exchange.getRequestMethod(),
                    exchange.getRequestURI().getPath(),
                    exchange.getResponseCode(),
                    exchange.getResponseHeaders().getFirst("Content-Type")
            );
        }
    }

    @Override
    public String description() {
        return "Logging Filter";
    }

}

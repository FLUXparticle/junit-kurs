package com.example.cocktails;

import com.example.cocktails.controller.*;
import com.google.inject.*;
import com.google.inject.name.*;
import com.sun.net.httpserver.*;
import org.apache.commons.dbcp2.*;

import javax.sql.*;
import java.io.*;
import java.net.*;

public class CocktailApp {

    public static Injector getReleaseInjector() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:sqlite::resource:cocktails.db");

        return Guice.createInjector(
                new JdbiModule(),
                binder -> binder
                        .bind(DataSource.class)
                        .annotatedWith(Names.named("data"))
                        .toInstance(dataSource)
        );
    }

    public static void main(String[] args) throws IOException {
        Injector injector = getReleaseInjector();

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        LoggingFilter loggingFilter = new LoggingFilter();

        server.createContext("/", new StaticHttpHandler("static"))
                .getFilters().add(loggingFilter);

        server.createContext("/api/", injector.getInstance(CocktailController.class))
                .getFilters().add(loggingFilter);

        server.createContext("/api/user/", injector.getInstance(UserController.class))
                .getFilters().add(loggingFilter);

        server.start();
        System.out.println("Server started...");
    }

}

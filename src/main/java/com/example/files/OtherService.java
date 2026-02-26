package com.example.files;

import java.nio.file.*;

public class OtherService {

    private final LoggerService loggerService;

    private final FileService fileService;

    public OtherService(LoggerService loggerService, FileService fileService) {
        this.loggerService = loggerService;
        this.fileService = fileService;
    }

    public void doWork(String filename) {
        Path path = Paths.get(filename);
        if (!Files.exists(path)) {
            return;
        }

        if (!Files.isReadable(path)) {
            return;
        }


    }

}

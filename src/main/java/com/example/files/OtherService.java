package com.example.files;

public class OtherService {

    private final LoggerService loggerService;

    private final FileService fileService;

    public OtherService(LoggerService loggerService, FileService fileService) {
        this.loggerService = loggerService;
        this.fileService = fileService;
    }

    public void doWork() {
        // TODO
    }

}

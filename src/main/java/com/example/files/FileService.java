package com.example.files;

import java.nio.file.*;

public class FileService {

    public boolean exists(Path path) {
        return Files.exists(path);
    }

    public boolean isReadable(Path path) {
        return Files.isReadable(path);
    }

}

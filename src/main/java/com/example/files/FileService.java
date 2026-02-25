package com.example.files;

import java.io.*;
import java.nio.file.*;

public class FileService {

    public boolean exists(Path path) {
        return Files.exists(path);
    }

    public boolean isReadable(Path path) {
        return Files.isReadable(path);
    }

    public String read(Path path) {
        if (!exists(path)) {
            throw new IllegalStateException("File does not exist: " + path);
        }
        if (!isReadable(path)) {
            throw new IllegalStateException("File is not readable: " + path);
        }

        try {
            return Files.readString(path);
        } catch (IOException e) {
            throw new IllegalStateException("Could not read file: " + path, e);
        }
    }

}

package com.example.s3file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Util {
    public static byte[] converterPDFByteArray(String diretorio) throws IOException {
        Path path = Paths.get(diretorio);
        return Files.readAllBytes(path);
    }
}

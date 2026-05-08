package com.appservice.effectiverestapi.service;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {

    private static final Logger log = LoggerFactory.getLogger(FileService.class);

    @Value("${file.upload-dir}")
    private String uploadDir;

    private Path root;

    @PostConstruct
    public void init() throws IOException {
        root = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectory(root);
    }

    public String uploadFile(MultipartFile file) throws IOException {
        log.info("Entered in uploadFile method");
        if(file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        //clean filename
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        log.info("Filename: {}", filename);

        //prevent path traversal attack
        if(filename.contains("..")) {
            throw new RuntimeException("Invalid file name");
        }

        //Optional: validate type
        if(!filename.endsWith(".txt") && !filename.endsWith(".pdf")) {
            throw new RuntimeException("Only TXT and PDF allowed");
        }

        log.info("Root data: {}", root.getFileName());
        Path target = root.resolve(filename);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        return filename;
    }

    public Resource downloadFile(String filename) throws MalformedURLException {
        log.info("Entered in downloadFile");
        Path filePath = root.resolve(filename).normalize();

        if(!Files.exists(filePath)) {
            throw new RuntimeException("File not found");
        }

        return new UrlResource(filePath.toUri());
    }
}

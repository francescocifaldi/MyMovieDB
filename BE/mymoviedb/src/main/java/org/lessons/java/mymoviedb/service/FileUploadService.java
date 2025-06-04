package org.lessons.java.mymoviedb.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {

    public String saveImage(MultipartFile imageFile, String titleOrName, String subDirectory) throws IOException {
        String extension = Optional.ofNullable(imageFile.getOriginalFilename())
                .filter(f -> f.contains("."))
                .map(f -> f.substring(imageFile.getOriginalFilename().lastIndexOf(".")))
                .orElse("");

        String fileName = titleOrName + extension;

        Path uploadDir = Paths.get("be/mymoviedb/src/main/resources/static/uploads/" + subDirectory);

        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        Path filePath = uploadDir.resolve(fileName);
        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }
}

package backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    public String saveFile(MultipartFile file, String productName) throws IOException {
        String cleanProductName = productName.trim().replaceAll("[^a-zA-Z0-9\\-]", "_");
        String fileName = cleanProductName + ".jpg";
        Path fileStorage = Paths.get(uploadDir).toAbsolutePath().normalize();

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("No se recibió ningún archivo o está vacío");
        }

        if (!Files.exists(fileStorage)) {
            Files.createDirectories(fileStorage);
        }

        Path targetLocation = fileStorage.resolve(fileName);
        file.transferTo(targetLocation.toFile());

        return fileName;
    }
}
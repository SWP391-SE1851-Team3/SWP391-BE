package com.team_3.School_Medical_Management_System.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.upload-dir:uploads/medicine-images}")
    private String uploadDir;

    /**
     * Chuyển đổi file ảnh thành Base64 string
     * @param file MultipartFile từ request
     * @return Base64 string của file ảnh
     */
    public String convertToBase64(MultipartFile file) throws IOException {
        byte[] fileBytes = file.getBytes();
        return Base64.getEncoder().encodeToString(fileBytes);
    }

    /**
     * Chuyển đổi Base64 string thành byte array
     * @param base64String Base64 string của ảnh
     * @return byte array của ảnh
     */
    public byte[] convertFromBase64(String base64String) {
        return Base64.getDecoder().decode(base64String);
    }

    /**
     * Lưu file ảnh vào thư mục trên server
     * @param file MultipartFile từ request
     * @return đường dẫn file đã lưu
     */
    public String storeFile(MultipartFile file) throws IOException {
        // Tạo thư mục nếu chưa tồn tại
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Tạo tên file unique để tránh trùng lặp
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path targetLocation = uploadPath.resolve(fileName);

        // Copy file vào thư mục đích
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }

    /**
     * Đọc file ảnh thành byte array để lưu vào database
     * @param file MultipartFile từ request
     * @return byte array của file
     */
    public byte[] getFileBytes(MultipartFile file) throws IOException {
        return file.getBytes();
    }

    /**
     * Kiểm tra file có phải là ảnh không
     * @param file MultipartFile cần kiểm tra
     * @return true nếu là file ảnh
     */
    public boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

    /**
     * Kiểm tra file có phải là PNG không
     * @param file MultipartFile cần kiểm tra
     * @return true nếu là file PNG
     */
    public boolean isPngFile(MultipartFile file) {
        String contentType = file.getContentType();
        return "image/png".equals(contentType);
    }

    /**
     * Xóa file từ thư mục
     * @param fileName tên file cần xóa
     */
    public void deleteFile(String fileName) throws IOException {
        Path filePath = Paths.get(uploadDir).resolve(fileName);
        Files.deleteIfExists(filePath);
    }

    /**
     * Lấy đường dẫn đầy đủ của file
     * @param fileName tên file
     * @return đường dẫn đầy đủ
     */
    public Path getFilePath(String fileName) {
        return Paths.get(uploadDir).resolve(fileName);
    }

    /**
     * Kiểm tra xem string có phải là Base64 không
     * @param str string cần kiểm tra
     * @return true nếu là Base64 valid
     */
    public boolean isBase64(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Base64.getDecoder().decode(str);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}

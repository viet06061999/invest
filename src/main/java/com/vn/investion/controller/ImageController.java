package com.vn.investion.controller;

import com.vn.investion.dto.Response;
import com.vn.investion.exception.BusinessException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/v1")
@SecurityRequirement(name = "Bearer Authentication")
public class ImageController {


    @Value("${app.upload.dir}") // Đường dẫn tới thư mục uploads trong project
    private String uploadDir;

    @PostMapping(path = "/image/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation()
    public Response<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // Lưu trữ tệp tin ảnh
            String fileName = file.getOriginalFilename();
            String fileExtension = FilenameUtils.getExtension(fileName);
            fileName = System.currentTimeMillis() + "." + fileExtension;
            Path dirPath = Paths.get(uploadDir);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }
            FileOutputStream fileOutputStream = new FileOutputStream(uploadDir + fileName);
            fileOutputStream.write(file.getBytes());
            fileOutputStream.close();

            return Response.ofSucceeded("/api/v1/image/download/" + fileName);
        } catch (IOException e) {
            throw new BusinessException(5000, e.getMessage(), 500);
        }
    }

    @GetMapping("/image/download/{filename}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable("filename") String filename) {
        try {
            // Đường dẫn tới thư mục lưu trữ ảnh
//            Path file = Paths.get(uploadDir).resolve(filename).normalize();
//            Resource resource = new UrlResource(file.toUri());
//            if (resource.exists()) {
//                HttpHeaders headers = new HttpHeaders();
//                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
//
//                return ResponseEntity.ok()
//                        .headers(headers)
//                        .body(resource);
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//            }
            // Đọc file ảnh từ thư mục hoặc cơ sở dữ liệu
            // Ví dụ:
            Path imagePath = Paths.get(uploadDir).resolve(filename).normalize();
            byte[] imageBytes = Files.readAllBytes(imagePath);
            String fileExtension = FilenameUtils.getExtension(filename);

            MediaType mediaType = getMediaTypeForImage(fileExtension);

            // Tạo header response cho loại ảnh
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(mediaType);

            // Trả về phản hồi HTTP 200 và dữ liệu ảnh
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private MediaType getMediaTypeForImage(String fileExtension) {
        switch (fileExtension.toLowerCase()) {
            case "jpeg":
            case "jpg":
                return MediaType.IMAGE_JPEG;
            case "png":
                return MediaType.IMAGE_PNG;
            case "gif":
                return MediaType.IMAGE_GIF;
            case "pdf":
                return MediaType.APPLICATION_PDF;
            // Các loại ảnh khác nếu cần
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}

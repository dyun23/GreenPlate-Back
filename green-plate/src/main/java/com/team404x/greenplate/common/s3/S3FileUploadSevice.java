package com.team404x.greenplate.common.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3FileUploadSevice {

    @Value("db20240625")
    private String bucketName;
    @Value("ap-northeast-2")
    private String staticRegion;
    private final AmazonS3 amazonS3;

    public String upload(String domain, Long id, MultipartFile file) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        String uploadPath = makeFolder();
        try {
            String fileName = domain + "/" + id + "/" + uploadPath + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();
            amazonS3.putObject(bucketName, fileName, file.getInputStream(), metadata);
            return "https://" + bucketName + ".s3." + staticRegion + ".amazonaws.com/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String makeFolder() {
        String folderPath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        File uploadPathFolder = new File(folderPath);
        if (!uploadPathFolder.exists()) {
            if (uploadPathFolder.mkdirs()) {
                System.out.println("Directory was created successfully.");
            } else {
                System.err.println("Failed to create directory.");
            }
        }
        return folderPath;
    }
}

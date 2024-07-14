package com.fhr.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author FHR
 * @Create 2024/4/25 21:20
 * @Version 1.0
 */
public interface FileUploadService {
    String upload(MultipartFile file);
}

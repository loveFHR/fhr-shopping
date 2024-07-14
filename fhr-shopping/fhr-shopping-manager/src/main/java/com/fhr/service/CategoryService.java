package com.fhr.service;

import com.fhr.model.entity.product.Category;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author FHR
 * @Create 2024/7/13 21:29
 * @Version 1.0
 */
public interface CategoryService {
    List<Category> findByParentId(Long parentId);

    void exportData(HttpServletResponse response);

    void importData(MultipartFile file);
}

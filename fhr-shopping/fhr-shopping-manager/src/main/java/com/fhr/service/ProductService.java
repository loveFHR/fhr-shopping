package com.fhr.service;

import com.fhr.model.dto.product.ProductDto;
import com.fhr.model.entity.product.Product;
import com.github.pagehelper.PageInfo;

/**
 * @Author FHR
 * @Create 2024/7/14 0:30
 * @Version 1.0
 */
public interface ProductService {
    PageInfo<Product> findByPage(Integer page, Integer limit, ProductDto productDto);

    void save(Product product);

    Product getById(Long id);

    void updateById(Product product);

    void deleteById(Long id);

    void updateAuditStatus(Long id, Integer auditStatus);

    void updateStatus(Long id, Integer status);
}

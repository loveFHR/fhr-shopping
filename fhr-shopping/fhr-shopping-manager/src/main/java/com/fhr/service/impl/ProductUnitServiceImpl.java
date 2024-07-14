package com.fhr.service.impl;

import com.fhr.mapper.ProductUnitMapper;
import com.fhr.model.entity.base.ProductUnit;
import com.fhr.service.ProductUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author FHR
 * @Create 2024/7/14 0:48
 * @Version 1.0
 */
@Service
public class ProductUnitServiceImpl implements ProductUnitService {

    @Autowired
    private ProductUnitMapper productUnitMapper ;
    @Override
    public List<ProductUnit> findAll() {
        return productUnitMapper.findAll() ;
    }
}

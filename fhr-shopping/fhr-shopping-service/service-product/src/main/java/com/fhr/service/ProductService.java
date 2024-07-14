package com.fhr.service;

import com.fhr.model.dto.h5.ProductSkuDto;
import com.fhr.model.dto.product.SkuSaleDto;
import com.fhr.model.entity.product.ProductSku;
import com.fhr.model.vo.h5.ProductItemVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Author FHR
 * @Create 2024/4/22 20:33
 * @Version 1.0
 */
public interface ProductService {
    List<ProductSku> findProductSkuBySale();

    PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto);

    ProductItemVo item(Long skuId);

    ProductSku getBySkuId(Long skuId);

    Boolean updateSkuSaleNum(List<SkuSaleDto> skuSaleDtoList);
}

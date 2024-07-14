package com.fhr.controller;

import com.fhr.model.entity.product.Category;
import com.fhr.model.entity.product.ProductSku;
import com.fhr.model.vo.common.Result;
import com.fhr.model.vo.common.ResultCodeEnum;
import com.fhr.model.vo.h5.IndexVo;
import com.fhr.service.CategoryService;
import com.fhr.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author FHR
 * @Create 2024/4/22 20:28
 * @Version 1.0
 */
@RestController
@Tag(name = "首页接口管理")
@RequestMapping(value="/api/product/index")
public class IndexController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Operation(summary = "获取首页数据")
    @GetMapping
    public Result<IndexVo> findData(){
        //所有一级分类
        List<Category> categoryList = categoryService.findOneCategory();
        //根据销量排序，获取前10条记录
        List<ProductSku> productSkuList = productService.findProductSkuBySale();
        IndexVo indexVo = new IndexVo() ;
        indexVo.setCategoryList(categoryList);
        indexVo.setProductSkuList(productSkuList);
        return Result.build(indexVo , ResultCodeEnum.SUCCESS);
    }
}

package com.fhr.controller;

import com.fhr.model.entity.product.Brand;
import com.fhr.model.vo.common.Result;
import com.fhr.model.vo.common.ResultCodeEnum;
import com.fhr.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "品牌管理")
@RestController
@RequestMapping(value="/api/product/brand")
@SuppressWarnings({"unchecked", "rawtypes"})
public class BrandController {
   
   @Autowired
   private BrandService brandService;
   
   @Operation(summary = "获取全部品牌")
   @GetMapping("findAll")
   public Result<List<Brand>> findAll() {
      List<Brand> list = brandService.findAll();
      return Result.build(list, ResultCodeEnum.SUCCESS);
   }

}
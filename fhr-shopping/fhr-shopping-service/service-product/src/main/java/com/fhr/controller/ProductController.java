package com.fhr.controller;

import com.fhr.model.dto.h5.ProductSkuDto;
import com.fhr.model.dto.product.SkuSaleDto;
import com.fhr.model.entity.product.ProductSku;
import com.fhr.model.vo.common.Result;
import com.fhr.model.vo.common.ResultCodeEnum;
import com.fhr.model.vo.h5.ProductItemVo;
import com.fhr.service.ProductService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "商品列表管理")
@RestController
@RequestMapping(value="/api/product")
@SuppressWarnings({"unchecked", "rawtypes"})
public class ProductController {
	
	@Autowired
	private ProductService productService;

	@Operation(summary = "分页查询")
	@GetMapping(value = "/{page}/{limit}")
	public Result<PageInfo<ProductSku>> findByPage(@Parameter(name = "page", description = "当前页码", required = true) @PathVariable Integer page,
												   @Parameter(name = "limit", description = "每页记录数", required = true) @PathVariable Integer limit,
												   @Parameter(name = "productSkuDto", description = "搜索条件对象", required = false) ProductSkuDto productSkuDto) {
		PageInfo<ProductSku> pageInfo = productService.findByPage(page, limit, productSkuDto);
		return Result.build(pageInfo , ResultCodeEnum.SUCCESS) ;
	}
	@Operation(summary = "商品详情")
	@GetMapping("item/{skuId}")
	public Result<ProductItemVo> item(@Parameter(name = "skuId", description = "商品skuId", required = true) @PathVariable Long skuId) {
		ProductItemVo productItemVo = productService.item(skuId);
		return Result.build(productItemVo , ResultCodeEnum.SUCCESS);
	}

	/**
	 * 远程调用:根据skuId返回sku信息
	 * @param skuId
	 * @return
	 */
	@GetMapping("/getBySkuId/{skuId}")
	@Operation(summary = "远程调用:根据skuId返回sku信息")
	public ProductSku getBySkuId(@PathVariable Long skuId){
		return productService.getBySkuId(skuId);
	}

	@Operation(summary = "远程调用:更新商品sku销量")
	@PostMapping("updateSkuSaleNum")
	public Boolean updateSkuSaleNum(@RequestBody List<SkuSaleDto> skuSaleDtoList) {
		return productService.updateSkuSaleNum(skuSaleDtoList);
	}
}
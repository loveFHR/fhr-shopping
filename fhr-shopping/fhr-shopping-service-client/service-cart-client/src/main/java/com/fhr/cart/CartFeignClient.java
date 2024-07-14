package com.fhr.cart;

import com.fhr.model.entity.h5.CartInfo;
import com.fhr.model.vo.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @Author FHR
 * @Create 2024/4/23 22:33
 * @Version 1.0
 */
@FeignClient(value = "service-cart")
public interface CartFeignClient {
    @GetMapping(value = "/api/order/cart/auth/getAllCkecked")
    public List<CartInfo> getAllCkecked();

    @GetMapping(value = "/api/order/cart/auth/deleteChecked")
    public  Result deleteChecked() ;
}

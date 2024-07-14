package com.fhr.service;

import com.fhr.model.entity.h5.CartInfo;

import java.util.List;

/**
 * @Author FHR
 * @Create 2024/4/23 20:31
 * @Version 1.0
 */
public interface CartService {
    void addToCart(Long skuId, Integer skuNum);

    List<CartInfo> getCartList();

    void deleteCart(Long skuId);

    void checkCart(Long skuId, Integer isChecked);

    void allCheckCart(Integer isChecked);

    void clearCart();

    List<CartInfo> getAllChecked();

    void deleteChecked();
}

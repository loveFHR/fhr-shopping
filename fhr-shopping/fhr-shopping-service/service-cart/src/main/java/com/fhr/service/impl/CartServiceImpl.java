package com.fhr.service.impl;

import com.alibaba.fastjson.JSON;
import com.fhr.model.entity.h5.CartInfo;
import com.fhr.model.entity.product.ProductSku;
import com.fhr.product.ProductFeignClient;
import com.fhr.service.CartService;
import com.fhr.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author FHR
 * @Create 2024/4/23 20:32
 * @Version 1.0
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private ProductFeignClient productFeignClient;

    private static final String USER_CART = "user:cart:";

    /**
     * 添加购物车
     * @param skuId
     * @param skuNum
     */
    @Override
    public void addToCart(Long skuId, Integer skuNum) {
        //登录状态 得到id (ThreadLocal)
        Long userId = AuthContextUtil.getUserInfo().getId();
        //从redis里获取购物车信息
        //hash类型 key: userId  field: skuId  value:sku信息
        Object cartInfoObj = redisTemplate.opsForHash().get(USER_CART + userId, String.valueOf(skuId));
        CartInfo cartInfo = null;
        if (cartInfoObj!=null) {
            //购物车商品已存在，则只需要商品数量+1
            cartInfo = JSON.parseObject(cartInfoObj.toString(), CartInfo.class);
            cartInfo.setSkuNum(cartInfo.getSkuNum() + skuNum);
            cartInfo.setIsChecked(1); //设置购物车商品为选中状态
            cartInfo.setUpdateTime(new Date());
        }else {
            //要加入的商品在购物车中不存在,则需要添加到redis中
             cartInfo = new CartInfo();
            ProductSku productSku = productFeignClient.getBySkuId(skuId);
            cartInfo.setCartPrice(productSku.getSalePrice());
            cartInfo.setSkuNum(skuNum);
            cartInfo.setSkuId(skuId);
            cartInfo.setUserId(userId);
            cartInfo.setImgUrl(productSku.getThumbImg());
            cartInfo.setSkuName(productSku.getSkuName());
            cartInfo.setIsChecked(1); //设置购物车商品为选中状态
            cartInfo.setCreateTime(new Date());
            cartInfo.setUpdateTime(new Date());
        }
        redisTemplate.opsForHash().put(USER_CART + userId,
                String.valueOf(skuId),
                JSON.toJSONString(cartInfo));
    }

    /**
     * 查询购物车
     * @return
     */
    @Override
    public List<CartInfo> getCartList() {
        // 获取当前登录的用户信息
        Long userId = AuthContextUtil.getUserInfo().getId();

        // 获取数据
        List<Object> cartInfoList = redisTemplate.opsForHash().values(USER_CART+userId);

        //List<Object>  ---> List<CartInfo>
        if (!CollectionUtils.isEmpty(cartInfoList)) {
            List<CartInfo> infoList = cartInfoList.stream()
                    .map(cartInfoJSON -> JSON.parseObject(cartInfoJSON.toString(), CartInfo.class))
                    .sorted((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()))
                    .collect(Collectors.toList());
            return infoList ;
        }

        return new ArrayList<>() ;
    }

    /**
     * 删除购物车商品
     * @param skuId
     */
    @Override
    public void deleteCart(Long skuId) {
        // 获取当前登录的用户数据
        Long userId = AuthContextUtil.getUserInfo().getId();

        //删除缓存对象
        redisTemplate.opsForHash().delete(USER_CART+userId  ,String.valueOf(skuId)) ;
    }

    /**
     * 更新购物车商品选中状态
     * @param skuId
     * @param isChecked
     */
    @Override
    public void checkCart(Long skuId, Integer isChecked) {
        // 获取当前登录的用户数据
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = USER_CART+userId;

        Boolean hasKey = redisTemplate.opsForHash().hasKey(cartKey, String.valueOf(skuId));
        if(hasKey) {
            String cartInfoJSON = redisTemplate.opsForHash().get(cartKey, String.valueOf(skuId)).toString();
            CartInfo cartInfo = JSON.parseObject(cartInfoJSON, CartInfo.class);
            cartInfo.setIsChecked(isChecked);
            redisTemplate.opsForHash().put(cartKey , String.valueOf(skuId) , JSON.toJSONString(cartInfo));
        }
    }

    /**
     * 更新购物车商品全部选中状态
     * @param isChecked
     */
    @Override
    public void allCheckCart(Integer isChecked) {
        // 获取当前登录的用户数据
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = USER_CART+userId;

        // 获取所有的购物项数据
        List<Object> objectList = redisTemplate.opsForHash().values(cartKey);
        if(!CollectionUtils.isEmpty(objectList)) {
            objectList.stream().map(cartInfoJSON -> {
                CartInfo cartInfo = JSON.parseObject(cartInfoJSON.toString(), CartInfo.class);
                cartInfo.setIsChecked(isChecked);
                return cartInfo ;
            }).forEach(cartInfo -> redisTemplate.opsForHash().put(cartKey , String.valueOf(cartInfo.getSkuId()) , JSON.toJSONString(cartInfo)));

        }
    }

    /**
     * 清空购物车
     */
    @Override
    public void clearCart() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = USER_CART+userId;
        redisTemplate.delete(cartKey);
    }

    @Override
    public List<CartInfo> getAllChecked() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = USER_CART+userId;
        //根据key获取购物车中所有的值
        List<Object> objectList = redisTemplate.opsForHash().values(cartKey);

        //List<Object>  ---> List<CartInfo>
        if (!CollectionUtils.isEmpty(objectList)) {
            List<CartInfo> infoList = objectList.stream()
                    .map(cartInfoJSON -> JSON.parseObject(cartInfoJSON.toString(), CartInfo.class))
                    .filter(cartInfo -> cartInfo.getIsChecked() == 1)
                    .sorted((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()))
                    .collect(Collectors.toList());
            return infoList ;
        }
        return new ArrayList<>();
    }

    @Override
    public void deleteChecked() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = USER_CART+userId;
        List<Object> objectList = redisTemplate.opsForHash().values(cartKey);       // 删除选中的购物项数据
        if(!CollectionUtils.isEmpty(objectList)) {
            objectList.stream().map(cartInfoJSON -> JSON.parseObject(cartInfoJSON.toString(), CartInfo.class))
                    .filter(cartInfo -> cartInfo.getIsChecked() == 1)
                    .forEach(cartInfo -> redisTemplate.opsForHash().delete(cartKey , String.valueOf(cartInfo.getSkuId())));
        }
    }
}

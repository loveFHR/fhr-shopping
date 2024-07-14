package com.fhr.user;

import com.fhr.model.entity.user.UserAddress;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author FHR
 * @Create 2024/4/24 13:40
 * @Version 1.0
 */
@FeignClient(value = "service-user")
public interface UserFeignClient {
    @GetMapping("/api/user/userAddress/getUserAddress/{id}")
    public  UserAddress getUserAddress(@PathVariable("id") Long id) ;
}

package com.fhr.controller;

import com.fhr.model.entity.user.UserAddress;
import com.fhr.model.vo.common.Result;
import com.fhr.model.vo.common.ResultCodeEnum;
import com.fhr.service.UserAddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "用户地址接口")
@RestController
@RequestMapping(value="/api/user/userAddress")
@SuppressWarnings({"unchecked", "rawtypes"})
public class UserAddressController {
   
   @Autowired
   private UserAddressService userAddressService;

   @Operation(summary = "获取用户地址列表")
   @GetMapping("auth/findUserAddressList")
   public Result<List<UserAddress>> findUserAddressList() {
      List<UserAddress> list = userAddressService.findUserAddressList();
      return Result.build(list , ResultCodeEnum.SUCCESS) ;
   }
   @Operation(summary = "获取地址信息")
   @GetMapping("getUserAddress/{id}")
   public UserAddress getUserAddress(@PathVariable Long id) {
      return userAddressService.getById(id);
   }
}
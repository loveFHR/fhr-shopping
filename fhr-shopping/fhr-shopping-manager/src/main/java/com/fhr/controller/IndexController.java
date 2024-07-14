package com.fhr.controller;


import com.fhr.model.dto.system.LoginDto;
import com.fhr.model.entity.system.SysUser;
import com.fhr.model.vo.common.Result;
import com.fhr.model.vo.common.ResultCodeEnum;
import com.fhr.model.vo.system.LoginVo;
import com.fhr.model.vo.system.SysMenuVo;
import com.fhr.model.vo.system.ValidateCodeVo;
import com.fhr.service.SysMenuService;
import com.fhr.service.SysUserService;
import com.fhr.service.ValidateCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "用户接口")
@RestController
@RequestMapping(value = "/admin/system/index")
public class IndexController {

    @Autowired
    private SysUserService sysUserService ;
    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private ValidateCodeService validateCodeService;

    @Operation(summary = "登录接口")
    @PostMapping(value = "/login")
    public Result<LoginVo> login(@RequestBody LoginDto loginDto) {
        LoginVo loginVo = sysUserService.login(loginDto) ;
        return Result.build(loginVo , ResultCodeEnum.SUCCESS) ;
    }

    @GetMapping(value = "/generateValidateCode")
    @Operation(summary = "生成图片验证码")
    public Result<ValidateCodeVo> generateValidateCode() {
        ValidateCodeVo validateCodeVo = validateCodeService.generateValidateCode();
        return Result.build(validateCodeVo , ResultCodeEnum.SUCCESS) ;
    }
    @GetMapping("/getUserInfo")
    @Operation(summary = "获取用户信息")
    public Result getUserInfo(@RequestHeader(name = "token") String token){
        SysUser sysUser = sysUserService.getUserInfo(token);
        return Result.build(sysUser,ResultCodeEnum.SUCCESS);
    }
    @GetMapping("/logout")
    @Operation(summary = "退出")
    public Result logout(@RequestHeader("token") String token){
        sysUserService.logout(token);
        return Result.build("",ResultCodeEnum.SUCCESS);
    }

    @GetMapping("/menus")
    @Operation(summary = "查询用户可以操作的菜单")
    public Result menus() {
        List<SysMenuVo> sysMenuVoList =  sysMenuService.findUserMenuList() ;
        return Result.build(sysMenuVoList , ResultCodeEnum.SUCCESS) ;
    }
}
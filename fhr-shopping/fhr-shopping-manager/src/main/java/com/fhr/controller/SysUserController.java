package com.fhr.controller;

import com.fhr.log.annotation.Log;
import com.fhr.log.enums.BusinessType;
import com.fhr.model.dto.system.AssignRoleDto;
import com.fhr.model.dto.system.SysUserDto;
import com.fhr.model.entity.system.SysRole;
import com.fhr.model.entity.system.SysUser;
import com.fhr.model.vo.common.Result;
import com.fhr.model.vo.common.ResultCodeEnum;
import com.fhr.service.SysUserService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author FHR
 * @Create 2024/4/25 19:17
 * @Version 1.0
 */
@Tag(name = "用户管理")
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 用户条件分页查询
     * @param sysUserDto
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Operation(summary = "用户条件分页查询")
    @GetMapping(value = "/findByPage/{pageNum}/{pageSize}")
    public Result<PageInfo<SysRole>> findByPage(SysUserDto sysUserDto ,
                                                @PathVariable(value = "pageNum") Integer pageNum ,
                                                @PathVariable(value = "pageSize") Integer pageSize) {
        PageInfo<SysUser> pageInfo = sysUserService.findByPage(sysUserDto , pageNum , pageSize) ;
        return Result.build(pageInfo , ResultCodeEnum.SUCCESS) ;
    }

    @Operation(summary = "用户添加")
    @PostMapping("/saveSysUser")
    @Log(title = "添加用户",businessType = BusinessType.INSERT)
    public Result saveSysUser(@RequestBody SysUser sysUser){
        sysUserService.saveSysUser(sysUser);
        return Result.build("",ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "用户修改")
    @PutMapping("/updateSysUser")
    @Log(title = "修改用户",businessType = BusinessType.UPDATE)
    public Result updateSysUser(@RequestBody SysUser sysUser){
        sysUserService.updateSysUser(sysUser);
        return Result.build("",ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "用户删除")
    @DeleteMapping(value = "/deleteById/{userId}")
    @Log(title = "删除用户",businessType = BusinessType.DELETE)
    public Result deleteById(@PathVariable(value = "userId") Long userId) {
        sysUserService.deleteById(userId) ;
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }
    @PostMapping("/doAssign")
    @Operation(summary = "为用户分配角色")
    public Result doAssign (@RequestBody AssignRoleDto assignRoleDto){
        sysUserService.doAssign(assignRoleDto);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
}

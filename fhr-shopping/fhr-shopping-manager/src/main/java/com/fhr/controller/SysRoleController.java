package com.fhr.controller;

import com.fhr.log.annotation.Log;
import com.fhr.log.enums.BusinessType;
import com.fhr.model.dto.system.SysRoleDto;
import com.fhr.model.entity.system.SysRole;
import com.fhr.model.vo.common.Result;
import com.fhr.model.vo.common.ResultCodeEnum;
import com.fhr.service.SysRoleService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author FHR
 * @Create 2024/4/22 15:41
 * @Version 1.0
 */
@RestController
@RequestMapping("/admin/system/sysRole")
@Tag(name = "角色接口")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @PostMapping("/findByPage/{current}/{limit}")
    @Operation(summary = "角色列表")
    public Result findByPage(@PathVariable Integer current
            , @PathVariable Integer limit
            , @RequestBody SysRoleDto sysRoleDto){
        PageInfo<SysRole> pageInfo =  sysRoleService.findByPage(current,limit,sysRoleDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/saveSysRole")
    @Operation(summary = "添加角色")
    @Log(title = "添加角色",businessType = BusinessType.INSERT)
    public Result saveSysRole(@RequestBody SysRole sysRole){
        sysRoleService.saveSysRole(sysRole);
        return Result.build("",ResultCodeEnum.SUCCESS);
    }
    @PutMapping("/updateSysRole")
    @Operation(summary = "修改角色")
    @Log(title = "修改角色",businessType = BusinessType.UPDATE)
    public Result updateSysRole(@RequestBody SysRole sysRole){
        sysRoleService.updateSysRole(sysRole);
        return Result.build("",ResultCodeEnum.SUCCESS);
    }

    @DeleteMapping(value = "/deleteById/{roleId}")
    @Operation(summary = "删除角色")
    @Log(title = "删除角色",businessType = BusinessType.DELETE)
    public Result deleteById(@PathVariable(value = "roleId") Long roleId) {
        sysRoleService.deleteById(roleId) ;
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    @GetMapping(value = "/findAllRoles/{userId}")
    @Operation(summary = "查询所有角色")
    public Result<Map<String , Object>> findAllRoles(@PathVariable("userId") Long userId) {
        Map<String, Object> resultMap = sysRoleService.findAllRoles(userId);
        return Result.build(resultMap , ResultCodeEnum.SUCCESS)  ;
    }
}

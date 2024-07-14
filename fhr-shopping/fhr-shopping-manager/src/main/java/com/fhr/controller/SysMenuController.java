package com.fhr.controller;

import com.fhr.log.annotation.Log;
import com.fhr.log.enums.BusinessType;
import com.fhr.model.entity.system.SysMenu;
import com.fhr.model.vo.common.Result;
import com.fhr.model.vo.common.ResultCodeEnum;
import com.fhr.service.SysMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author FHR
 * @Create 2024/4/27 14:28
 * @Version 1.0
 */
@Tag(name = "菜单管理接口")
@RestController
@RequestMapping(value="/admin/system/sysMenu")
public class SysMenuController {
   
   @Autowired
   private SysMenuService sysMenuService;

   @GetMapping("/findNodes")
   @Operation(summary = "菜单列表")
   public Result<List<SysMenu>> findNodes() {
      List<SysMenu> list = sysMenuService.findNodes();
      return Result.build(list , ResultCodeEnum.SUCCESS) ;
   }

   @PostMapping("/save")
   @Operation(summary = "添加菜单")
   @Log(title = "添加菜单",businessType = BusinessType.INSERT)
   public Result save(@RequestBody SysMenu sysMenu) {
      sysMenuService.save(sysMenu);
      return Result.build(null , ResultCodeEnum.SUCCESS) ;
   }
   @PutMapping("/updateById")
   @Operation(summary = "修改菜单")
   @Log(title = "修改菜单",businessType = BusinessType.UPDATE)
   public Result updateById(@RequestBody SysMenu sysMenu) {
      sysMenuService.updateById(sysMenu);
      return Result.build(null , ResultCodeEnum.SUCCESS) ;
   }
   @DeleteMapping("/removeById/{id}")
   @Operation(summary = "删除菜单")
   @Log(title = "删除菜单",businessType = BusinessType.DELETE)
   public Result removeById(@PathVariable Long id) {
      sysMenuService.removeById(id);
      return Result.build(null , ResultCodeEnum.SUCCESS) ;
   }
}
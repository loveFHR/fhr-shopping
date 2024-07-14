package com.fhr.service.impl;

import com.fhr.mapper.SysRoleMenuMapper;
import com.fhr.model.dto.system.AssginMenuDto;
import com.fhr.model.entity.system.SysMenu;
import com.fhr.service.SysMenuService;
import com.fhr.service.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {

    @Autowired
    private SysMenuService sysMenuService;
    
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    /**
     * 查询角色分配过菜单id列表
     * @param roleId
     * @return
     */
    @Override
    public Map<String, Object> findSysRoleMenuByRoleId(Long roleId) {
    
        // 查询所有的菜单数据
        List<SysMenu> sysMenuList = sysMenuService.findNodes() ;
    
        // 查询当前角色的菜单数据
        List<Long> roleMenuIds = sysRoleMenuMapper.findSysRoleMenuByRoleId(roleId) ;
    
        // 将数据存储到Map中进行返回
        Map<String , Object> result = new HashMap<>() ;
        result.put("sysMenuList" , sysMenuList) ;
        result.put("roleMenuIds" , roleMenuIds) ;
    
        // 返回
        return result;
    }

    @Override
    public void doAssign(AssginMenuDto assginMenuDto) {
        // 根据角色的id删除其所对应的菜单数据
        sysRoleMenuMapper.deleteByRoleId(assginMenuDto.getRoleId());

        // 获取菜单的id
        List<Map<String, Number>> menuInfo = assginMenuDto.getMenuIdList();
        if(menuInfo != null && menuInfo.size() > 0) { //角色分配了菜单
            sysRoleMenuMapper.doAssign(assginMenuDto) ;
        }

    }
}
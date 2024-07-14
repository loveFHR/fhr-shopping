package com.fhr.service;

import com.fhr.model.entity.system.SysMenu;
import com.fhr.model.vo.system.SysMenuVo;

import java.util.List;

/**
 * @Author FHR
 * @Create 2024/7/13 19:51
 * @Version 1.0
 */

public interface SysMenuService {
    List<SysMenu> findNodes();

    void save(SysMenu sysMenu);

    void updateById(SysMenu sysMenu);

    void removeById(Long id);

    List<SysMenuVo> findUserMenuList();
}

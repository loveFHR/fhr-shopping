package com.fhr.mapper;

import com.fhr.model.dto.system.AssginMenuDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMenuMapper {
    public abstract List<Long> findSysRoleMenuByRoleId(Long roleId);

    void deleteByRoleId(Long roleId);

    void doAssign(AssginMenuDto assginMenuDto);

    /**
     * update sys_role_menu srm set srm.is_half = 1 where menu_id = #{menuId}
     * @param id
     */
    void updateSysRoleMenuIsHalf(Long id);
}
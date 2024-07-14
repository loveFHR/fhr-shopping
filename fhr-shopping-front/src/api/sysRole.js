import request from '@/utils/request'

// 分页查询角色数据
export const GetSysRoleListByPage = (pageNum , pageSize , queryDto) => {
    return request({
        url: '/admin/system/sysRole/findByPage/' + pageNum + "/" + pageSize,
        method: 'post',
        data: queryDto
    })
}
// 添加角色请求方法
export const SaveSysRole = (sysRole) => {
    return request({
        url: '/admin/system/sysRole/saveSysRole',
        method: 'post',
        data:sysRole
    })
}
// 修改角色请求方法
export const UpdateSysRole = (sysRole) => {
    return request({
        url: '/admin/system/sysRole/updateSysRole',
        method: 'put',
        data:sysRole
    })
}
// 删除角色请求方法
export const DeleteSysRole = (roleId) => {
    return request({
        url: `/admin/system/sysRole/deleteById/${roleId}`,
        method: 'delete',
    })
}

// 查询所有的角色数据
export const GetAllRoleList = (userId) => {
    return request({
        url: `/admin/system/sysRole/findAllRoles/${userId}`,
        method: 'get'
    })
}

export const GetSysRoleMenuIds = (roleId) => {
    return request({
        url: "/admin/system/sysRoleMenu/findSysRoleMenuByRoleId/"+ roleId,
        method: 'get'
    })
}

// 根据角色分配菜单请求方法
export const DoAssignMenuIdToSysRole = (assignMenuDto) => {
    return request({
        url: "/admin/system/sysRoleMenu/doAssign",
        method: 'post',
        data: assignMenuDto
    })
}
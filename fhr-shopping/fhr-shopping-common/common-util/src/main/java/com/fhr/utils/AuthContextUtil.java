package com.fhr.utils;

import com.fhr.model.entity.system.SysUser;
import com.fhr.model.entity.user.UserInfo;

/**
 * ThreadLocal存放登录用户数据
 *
 * @Author FHR
 * @Create 2024/4/21 22:46
 * @Version 1.0
 */
public class AuthContextUtil {
    //创建ThreadLocal对象
    private static final ThreadLocal<SysUser> tl = new ThreadLocal<>();

    private static final ThreadLocal<UserInfo> userInfoThreadLocal = new ThreadLocal<>() ;
    //添加数据
    public static void set(SysUser sysUser){
        tl.set(sysUser);
    }
    //获取数据
    public static SysUser get(){
        return tl.get();
    }
    //删除数据
    public static void remove(){
        tl.remove();
    }

    // 定义存储数据的静态方法
    public static void setUserInfo(UserInfo userInfo) {
        userInfoThreadLocal.set(userInfo);
    }

    // 定义获取数据的方法
    public static UserInfo getUserInfo() {
        return userInfoThreadLocal.get() ;
    }

    // 删除数据的方法
    public static void removeUserInfo() {
        userInfoThreadLocal.remove();
    }
}

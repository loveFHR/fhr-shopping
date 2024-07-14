package com.fhr.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.fhr.exception.FHRException;
import com.fhr.mapper.SysRoleUserMapper;
import com.fhr.mapper.SysUserMapper;
import com.fhr.model.dto.system.AssignRoleDto;
import com.fhr.model.dto.system.LoginDto;
import com.fhr.model.dto.system.SysUserDto;
import com.fhr.model.entity.system.SysUser;
import com.fhr.model.vo.common.ResultCodeEnum;
import com.fhr.model.vo.system.LoginVo;
import com.fhr.service.SysUserService;
import com.fhr.utils.AuthContextUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper ;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    @Autowired
    private RedisTemplate<String , String> redisTemplate ;

    /**
     * 登录
     * @param loginDto
     * @return
     */
    @Override
    public LoginVo login(LoginDto loginDto) {
        //校验验证码
        String captcha = loginDto.getCaptcha();
        String codeKey = loginDto.getCodeKey();
        String redisCode = redisTemplate.opsForValue().get("user:validate:"+codeKey);
        if (StrUtil.isEmpty(redisCode) || !StrUtil.equalsIgnoreCase(redisCode,captcha)) {
            throw new FHRException(ResultCodeEnum.VALIDATECODE_ERROR);
        }
        //删除redis里面的验证码
        redisTemplate.delete("user:validate:"+codeKey);

        // 根据用户名查询用户
        SysUser sysUser = sysUserMapper.selectByUserName(loginDto.getUserName());
        if(sysUser == null) {
            //throw new RuntimeException("用户名或者密码错误") ;
            throw new FHRException(ResultCodeEnum.LOGIN_ERROR);
        }

        // 验证密码是否正确
        String inputPassword = loginDto.getPassword();
        String md5InputPassword = DigestUtils.md5DigestAsHex(inputPassword.getBytes());
        if(!md5InputPassword.equals(sysUser.getPassword())) {
            //throw new RuntimeException("用户名或者密码错误") ;
            throw new FHRException(ResultCodeEnum.LOGIN_ERROR);
        }

        // 生成令牌，保存数据到Redis中
        String token = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set("user:login:" + token , JSON.toJSONString(sysUser) , 30 , TimeUnit.MINUTES);

        // 构建响应结果对象
        LoginVo loginVo = new LoginVo() ;
        loginVo.setToken(token);
        loginVo.setRefresh_token("");

        // 返回
        return loginVo;
    }

    /**
     * 获取用户信息
     * @param token
     * @return
     */
    @Override
    public SysUser getUserInfo(String token) {
        /*String userJson = redisTemplate.opsForValue().get("user:login:" + token);
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        return sysUser;*/
        return AuthContextUtil.get();
    }

    /**
     * 退出
     * @param token
     */
    @Override
    public void logout(String token) {
        redisTemplate.delete("user:login:" + token);
    }

    /**
     * 用户条件分页查询
     * @param sysUserDto
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<SysUser> findByPage(SysUserDto sysUserDto, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<SysUser> list = sysUserMapper.findByPage(sysUserDto);
        PageInfo<SysUser> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 用户添加
     * @param sysUser
     */
    @Override
    public void saveSysUser(SysUser sysUser) {
        //用户名不重复
        SysUser sysUserDB = sysUserMapper.selectByUserName(sysUser.getUserName());
        if (sysUserDB != null) {
            throw new FHRException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }
        //密码加密
        sysUser.setPassword(DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes()));
        sysUser.setStatus(1); //1代表可用
        sysUserMapper.save(sysUser);
    }

    /**
     * 修改用户
     * @param sysUser
     */
    @Override
    public void updateSysUser(SysUser sysUser) {
        sysUserMapper.update(sysUser);
    }

    @Override
    public void deleteById(Long userId) {
        sysUserMapper.delete(userId);
    }

    /**
     * 为用户分配角色
     * @param assignRoleDto
     */
    @Override
    public void doAssign(AssignRoleDto assignRoleDto) {
        //根据用户id删除用户之前分配角色的数据
        sysRoleUserMapper.deleteByUserId(assignRoleDto.getUserId());
        //重新分配角色数据
        List<Long> roleIdList = assignRoleDto.getRoleIdList();
        //遍历角色id
        for (Long roleId : roleIdList) {
            sysRoleUserMapper.doAssign(roleId,assignRoleDto.getUserId());
        }
    }
}
package com.fhr.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.fhr.exception.FHRException;
import com.fhr.mapper.UserInfoMapper;
import com.fhr.model.dto.h5.UserLoginDto;
import com.fhr.model.dto.h5.UserRegisterDto;
import com.fhr.model.entity.user.UserInfo;
import com.fhr.model.vo.common.ResultCodeEnum;
import com.fhr.model.vo.h5.UserInfoVo;
import com.fhr.service.UserInfoService;
import com.fhr.utils.AuthContextUtil;
import com.fhr.utils.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author FHR
 * @Create 2024/4/23 16:18
 * @Version 1.0
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    private static final String PHONE_CODE = "phone:code:";
    private static final String USER_LOGIN = "user:login:";

    /**
     * 注册
     * @param userRegisterDto
     */
    @Override
    public void register(UserRegisterDto userRegisterDto) {
        //校验验证码
        String code = userRegisterDto.getCode();
        String username = userRegisterDto.getUsername();
        String redisCode = redisTemplate.opsForValue().get(PHONE_CODE + username);
        if (!StrUtil.equals(redisCode,code)) {
            throw new FHRException(ResultCodeEnum.VALIDATECODE_ERROR);
        }
        //校验用户名不能重复
        UserInfo userInfo = userInfoMapper.SelectByUserName(username);
        if (userInfo != null ){
            //说明有用户，不能注册
            throw new FHRException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }
        //插入数据库
/*        userInfo = UserInfo.builder()
                .username(username)
                .phone(username)
                .nickName(userRegisterDto.getNickName())
                .password(DigestUtils.md5DigestAsHex(userRegisterDto.getPassword().getBytes()))//加密
                .status(1)
                .avatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132")
                .build();*/
        userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setNickName(userRegisterDto.getNickName());
        userInfo.setPassword(DigestUtils.md5DigestAsHex(userRegisterDto.getPassword().getBytes()));
        userInfo.setPhone(username);
        userInfo.setStatus(1);
        userInfo.setSex(0);
        userInfo.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        userInfoMapper.save(userInfo);
        //redis删除验证码
        redisTemplate.delete(PHONE_CODE + username);
    }

    /**
     * 登录
     * @param userLoginDto
     * @return
     */
    @Override
    public String login(UserLoginDto userLoginDto) {
        UserInfo userInfo = userInfoMapper.SelectByUserName(userLoginDto.getUsername());
        if (!DigestUtils.md5DigestAsHex(userLoginDto.getPassword().getBytes()).equals(userInfo.getPassword())) {
            throw  new FHRException(ResultCodeEnum.LOGIN_ERROR);
        }
        //检验用户是否禁用
        if (userInfo.getStatus() == 0) {
            throw new FHRException(ResultCodeEnum.ACCOUNT_STOP);
        }
        //生成token
        Map map = new HashMap();
        map.put("userInfo",userInfo);
        String token = JwtUtil.createJWT("!&^&*%%(&",7200000,map);//2*1000*60*60两小时
        redisTemplate.opsForValue().set(USER_LOGIN + token,JSON.toJSONString(userInfo),2, TimeUnit.HOURS);
        return token;
    }

    /**
     * 获取用户信息
     * @param token
     * @return
     */
    @Override
    public UserInfoVo getCurrentUserInfo(String token) {
/*        String userInfoJSON = redisTemplate.opsForValue().get(USER_LOGIN + token);
        if (StrUtil.isEmpty(userInfoJSON)) {
            throw new FHRException(ResultCodeEnum.LOGIN_AUTH);
        }
        UserInfo userInfo = JSON.parseObject(userInfoJSON , UserInfo.class) ;*/
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(userInfo, userInfoVo);
        return userInfoVo ;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete(USER_LOGIN + token);
    }
}

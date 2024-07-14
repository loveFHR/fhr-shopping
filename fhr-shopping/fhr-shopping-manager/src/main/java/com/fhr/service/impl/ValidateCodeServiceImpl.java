package com.fhr.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.core.lang.UUID;
import com.fhr.model.vo.system.ValidateCodeVo;
import com.fhr.service.ValidateCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author FHR
 * @Create 2024/4/21 21:39
 * @Version 1.0
 */
@Service
@Slf4j
public class ValidateCodeServiceImpl implements ValidateCodeService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public ValidateCodeVo generateValidateCode() {
        //hutool生成图片验证码
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(150, 48, 4, 2);
        String codeValue = circleCaptcha.getCode();
        log.info("验证码是:{}",codeValue);
        String imageBase64 = circleCaptcha.getImageBase64();
        //把验证码存储到redis里面,设置redis的key:uuid ,redis的value:验证码的值
        //设置过期时间
        String key = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set("user:validate:"+key,codeValue,5, TimeUnit.MINUTES);
        //返回
        ValidateCodeVo validateCodeVo = ValidateCodeVo.builder()
                .codeKey(key)
                .codeValue("data:image/png;base64," + imageBase64)
                .build();
        return validateCodeVo;
    }
}

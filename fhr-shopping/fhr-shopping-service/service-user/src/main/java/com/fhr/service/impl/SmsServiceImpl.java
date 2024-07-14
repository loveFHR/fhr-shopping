package com.fhr.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.fhr.exception.FHRException;
import com.fhr.model.vo.common.ResultCodeEnum;
import com.fhr.service.SmsService;
import com.fhr.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author FHR
 * @Create 2024/4/23 13:15
 * @Version 1.0
 */
@Service
public class SmsServiceImpl implements SmsService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    private static final String PHONE_CODE = "phone:code:";

    /**
     * 发送短信验证码
     * @param phone
     */
    @Override
    public void sendValidateCode(String phone) {
        /**
         * 因为短信付费，所以redis直接写死123456
         * TODO 正式记得把下面这段删掉
         */
        String fixedCode = redisTemplate.opsForValue().get(PHONE_CODE + phone);
        if (!StrUtil.isEmpty(fixedCode)) {
            return;
        }
        // -------------------------------------
        //生成验证码
        String code = RandomUtil.randomNumbers(4);
        //验证码放入redis，过期时间
        redisTemplate.opsForValue().set(PHONE_CODE+phone,code,5, TimeUnit.MINUTES);
        //向手机号发送短信
        sendMessage(phone,code);
    }

    private void sendMessage(String phone, String code) {
        String host = "https://dfsns.market.alicloudapi.com";
        String path = "/data/send_sms";
        String method = "POST";
        String appcode = ""; // TODO 自己的AppID
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("content", "code:"+code);
        bodys.put("template_id", "CST_ptdie100");
        bodys.put("phone_number", phone);

        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new FHRException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }
}

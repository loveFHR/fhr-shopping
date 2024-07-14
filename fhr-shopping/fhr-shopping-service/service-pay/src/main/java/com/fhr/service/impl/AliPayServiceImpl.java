package com.fhr.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.fhr.exception.FHRException;
import com.fhr.model.entity.pay.PaymentInfo;
import com.fhr.model.vo.common.ResultCodeEnum;
import com.fhr.service.AliPayService;
import com.fhr.service.PaymentInfoService;
import com.fhr.utils.AlipayProperties;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * @Author FHR
 * @Create 2024/4/24 17:01
 * @Version 1.0
 */
@Service
@Slf4j
public class AliPayServiceImpl implements AliPayService {
    @Autowired
    private PaymentInfoService paymentInfoService;

    @Autowired
    private AlipayProperties alipayProperties;

    @Autowired
    private AlipayClient  alipayClient;
    /**
     * 支付宝下单
     * @param orderNo
     * @return
     */
    @Override
    @SneakyThrows
    public String submitAlipay(String orderNo) {
        //保存支付记录
        PaymentInfo paymentInfo = paymentInfoService.savePaymentInfo(orderNo);
        //调用支付宝接口服务
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        // 同步回调地址
        request.setReturnUrl(alipayProperties.getReturnPaymentUrl());

        // 异步回调地址
        request.setNotifyUrl(alipayProperties.getNotifyPaymentUrl());

        // 准备请求参数 ，声明一个map 集合
        HashMap<String, Object> map = new HashMap<>();
        map.put("out_trade_no",paymentInfo.getOrderNo());
        map.put("product_code","QUICK_WAP_WAY");
        //map.put("total_amount",paymentInfo.getAmount());  //实际用到的代码
        map.put("total_amount",new BigDecimal("0.01")); //测试只支付0.01元
        map.put("subject",paymentInfo.getContent());
        request.setBizContent(JSON.toJSONString(map));

        //发送请求
        AlipayTradeWapPayResponse response = null;
        try {
            log.info("开始发送支付请求");
            response = alipayClient.pageExecute(request);
            if(response.isSuccess()){
                log.info("返回信息:{}",response.getBody());
                return response.getBody();
            } else {
                throw new FHRException(ResultCodeEnum.DATA_ERROR);
            }
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }
}

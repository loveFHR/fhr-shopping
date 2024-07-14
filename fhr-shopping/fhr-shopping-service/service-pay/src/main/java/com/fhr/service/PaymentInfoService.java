package com.fhr.service;

import com.fhr.model.entity.pay.PaymentInfo;

import java.util.Map;

/**
 * @Author FHR
 * @Create 2024/4/24 16:20
 * @Version 1.0
 */
public interface PaymentInfoService {
    PaymentInfo savePaymentInfo(String orderNo);

    void updatePaymentStatus(Map<String, String> paramMap);
}

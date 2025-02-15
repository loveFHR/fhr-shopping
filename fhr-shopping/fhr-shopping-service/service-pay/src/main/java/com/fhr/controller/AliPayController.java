package com.fhr.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.fhr.model.vo.common.Result;
import com.fhr.model.vo.common.ResultCodeEnum;
import com.fhr.service.AliPayService;
import com.fhr.service.PaymentInfoService;
import com.fhr.utils.AlipayProperties;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author FHR
 * @Create 2024/4/24 16:16
 * @Version 1.0
 */
@Controller
@RequestMapping("/api/order/alipay")
public class AliPayController {
    @Autowired
    private AliPayService alipayService;
    @Autowired
    private AlipayProperties alipayProperties;

    @Autowired
    private PaymentInfoService paymentInfoService;

    @Operation(summary="支付宝下单")
    @GetMapping("submitAlipay/{orderNo}")
    @ResponseBody
    public Result<String> submitAlipay(@Parameter(name = "orderNo", description = "订单号", required = true) @PathVariable(value = "orderNo") String orderNo) {
        String form = alipayService.submitAlipay(orderNo);
        return Result.build(form, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary="支付宝异步回调")
    @RequestMapping("callback/notify")
    @ResponseBody
    public String alipayNotify(@RequestParam Map<String, String> paramMap, HttpServletRequest request) {
        //签名校验
        boolean signVerified = false; //调用SDK验证签名
        try {
            signVerified = AlipaySignature.rsaCheckV1(paramMap, alipayProperties.getAlipayPublicKey(), AlipayProperties.charset, AlipayProperties.sign_type);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        // 交易状态
        String trade_status = paramMap.get("trade_status");

        if (signVerified) { //校验通过

            // TODO 验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure
            if ("TRADE_SUCCESS".equals(trade_status) || "TRADE_FINISHED".equals(trade_status)) {
                // 正常的支付成功，我们应该更新交易记录状态
                paymentInfoService.updatePaymentStatus(paramMap);
                return "success";
            }

        } else {
            // TODO 验签失败则记录异常日志，并在response中返回failure.
            return "failure";
        }

        return "failure";
    }
}

package com.galen.amazon.controller;

import com.galen.amazon.pojo.GalenResponse;
import com.galen.amazon.service.NotifyService;
import com.galen.amazon.utils.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sang on 2017/12/29.
 */
@Api(value = "通知", tags = "发邮件，短信等操作")
@RestController
@RequestMapping("notify")
public class NotifyController {

    @Autowired
    private NotifyService notifyService;

    @ApiOperation(value = "发送短信信息")
    @PostMapping("send/phone")
    public GalenResponse sendPhone(String phone, String msg) {
        if (null == phone) {
            return ResponseUtils.build(401, "请传入phone");
        }
        return ResponseUtils.SUCCESS(msg);
    }

    @ApiOperation(value = "发送邮件信息")
    @PostMapping("send/mail")
    public GalenResponse sendMail(String email, String msg, String goodsUrl) {
        if (null == email) {
            return ResponseUtils.build(401, "请传入email");
        }
        System.out.println("发送邮件" + msg);
        return notifyService.sendMailCode(email, msg, goodsUrl);
    }
}

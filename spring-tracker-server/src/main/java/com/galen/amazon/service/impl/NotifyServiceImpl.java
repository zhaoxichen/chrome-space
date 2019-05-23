package com.galen.amazon.service.impl;

import com.galen.amazon.component.SendMailComponent;
import com.galen.amazon.pojo.GalenResponse;
import com.galen.amazon.service.NotifyService;
import com.galen.amazon.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotifyServiceImpl implements NotifyService {

    private final String PGS_MINI_SUBJECT = "hello, PGS";
    private final String PGS_MINI_TEXT = "<html><body><h3><em> ";

    @Autowired
    private SendMailComponent sendMailComponent;

    @Override
    public GalenResponse sendMailCode(String email, String msg) {
        //发送邮件
        sendMailComponent.sendHtmlMail(email, PGS_MINI_SUBJECT,
                PGS_MINI_TEXT + msg + "</em></h3></body></html>");
        return ResponseUtils.SUCCESS("发送成功");
    }
}

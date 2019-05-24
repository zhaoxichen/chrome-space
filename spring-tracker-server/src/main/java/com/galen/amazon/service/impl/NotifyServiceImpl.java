package com.galen.amazon.service.impl;

import com.galen.amazon.component.SendMailComponent;
import com.galen.amazon.mapper.AmazonTrackerMapper;
import com.galen.amazon.model.AmazonTracker;
import com.galen.amazon.pojo.GalenResponse;
import com.galen.amazon.service.NotifyService;
import com.galen.amazon.utils.IdUtil;
import com.galen.amazon.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotifyServiceImpl implements NotifyService {

    private final String PGS_MINI_SUBJECT = "hello, amazon";

    @Autowired
    private AmazonTrackerMapper amazonTrackerMapper;
    @Autowired
    private SendMailComponent sendMailComponent;

    @Override
    public GalenResponse sendMailCode(String email, String msg, String goodsUrl) {
        AmazonTracker amazonTracker = amazonTrackerMapper.selectByGoodsUrl(goodsUrl);
        if (null != amazonTracker) {
            return ResponseUtils.SUCCESS("今天已经发送");
        }
        amazonTracker = new AmazonTracker();
        amazonTracker.setId(IdUtil.generateNumberId());
        amazonTracker.setGoodsUrl(goodsUrl);
        amazonTrackerMapper.insert(amazonTracker);
        //发送邮件
        sendMailComponent.sendHtmlMail(email, PGS_MINI_SUBJECT, msg);
        return ResponseUtils.SUCCESS("发送成功");
    }
}

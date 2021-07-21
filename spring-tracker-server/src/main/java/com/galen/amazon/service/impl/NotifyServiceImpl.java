package com.galen.amazon.service.impl;

import com.galen.amazon.component.SendMailComponent;
import com.galen.amazon.mapper.AmazonTrackerMapper;
import com.galen.amazon.entity.AmazonTracker;
import com.galen.amazon.pojo.GalenResponse;
import com.galen.amazon.service.NotifyService;
import com.galen.amazon.util.IdUtil;
import com.galen.amazon.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotifyServiceImpl implements NotifyService {

    private final String PGS_MINI_SUBJECT = "亚马逊跟卖提醒";

    @Autowired
    private AmazonTrackerMapper amazonTrackerMapper;
    @Autowired
    private SendMailComponent sendMailComponent;

    @Override
    public GalenResponse sendMailCode(String email, String msg, String goodsUrl) {
        AmazonTracker amazonTracker = amazonTrackerMapper.selectByGoodsUrl(goodsUrl);
        if (null != amazonTracker) {
            return ResponseUtil.SUCCESS("今天已经发送");
        }
        amazonTracker = new AmazonTracker();
        amazonTracker.setId(IdUtil.generateNumberId());
        amazonTracker.setGoodsUrl(goodsUrl);
        amazonTrackerMapper.insert(amazonTracker);
        //发送邮件
        sendMailComponent.sendHtmlMail(email, PGS_MINI_SUBJECT, msg + goodsUrl);
        return ResponseUtil.SUCCESS("发送成功");
    }
}

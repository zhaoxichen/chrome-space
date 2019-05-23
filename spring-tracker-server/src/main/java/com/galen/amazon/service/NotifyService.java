package com.galen.amazon.service;

import com.galen.amazon.pojo.GalenResponse;

public interface NotifyService {
    /**
     * @Author: Galen
     * @Description: 发送邮件
     * @Date: 2019/5/23-15:46
     * @Param: [email, msg]
     * @return: com.galen.amazon.pojo.GalenResponse
     **/
    GalenResponse sendMailCode(String email, String msg);
}

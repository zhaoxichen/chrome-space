package com.galen.amazon.service;

import com.galen.amazon.pojo.GalenResponse;

public interface StockService {

    GalenResponse barChartInfo(String token);

    /**
     * 添加一条记录
     *
     * @param cartHtmlBody
     * @return
     */
    GalenResponse add(String cartHtmlBody);

    /**
     * 列表
     *
     * @return
     */
    GalenResponse list();
}

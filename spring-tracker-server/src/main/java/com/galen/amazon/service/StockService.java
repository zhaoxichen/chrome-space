package com.galen.amazon.service;

import com.galen.amazon.entity.StockTracker;
import com.galen.amazon.pojo.GalenResponse;

public interface StockService {

    GalenResponse barChartInfo(String token);

    GalenResponse add(StockTracker stockTracker);
}
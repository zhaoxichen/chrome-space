package com.galen.amazon.service.impl;

import com.galen.amazon.entity.StockTracker;
import com.galen.amazon.mapper.StockTrackerMapper;
import com.galen.amazon.pojo.ChartData;
import com.galen.amazon.pojo.GalenResponse;
import com.galen.amazon.service.StockService;
import com.galen.amazon.utils.IdUtil;
import com.galen.amazon.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class StockServiceImpl implements StockService {

    private static final int WEEKDAYS = 7;

    @Autowired
    private StockTrackerMapper stockTrackerMapper;

    @Override
    public GalenResponse barChartInfo(String token) {
        ChartData chartData = new ChartData();
        //7天顺序日期
        List dataString = this.getContinuityDataString(WEEKDAYS, "yyyy-MM-dd");
        String[] columns = {"日期", "新增用户"}; // 公共x轴,1号y轴,2号y轴
        chartData.setColumns(columns);
        List<Object> rows = new LinkedList<>();
        chartData.setRows(rows);
        return ResponseUtils.SUCCESS(chartData);
    }

    @Override
    public GalenResponse add(StockTracker stockTracker) {
        stockTracker.setId(IdUtil.generateNumberId());
        int rowsAffect = stockTrackerMapper.insert(stockTracker);
        return ResponseUtils.build(rowsAffect);
    }

    /**
     * 功能描述: 获取连续num 天的日期字符串
     *
     * @param: [num]
     * @return: java.util.List
     * @auther: yancy
     * @date: 2019/3/18 21:58
     */
    private List getContinuityDataString(int num, String datePattern) {
        List<String> dateString = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
        Calendar calendar = Calendar.getInstance();
        for (int i = num - 1; i >= 0; i--) {
            calendar.setTime(new Date());
            calendar.add(Calendar.DATE, -i);
            dateString.add(sdf.format(calendar.getTime()));
        }
        return dateString;
    }
}

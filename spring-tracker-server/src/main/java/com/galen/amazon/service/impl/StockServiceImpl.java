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
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class StockServiceImpl implements StockService {

    private static final int WEEKDAYS = 7;
    /**
     * 库存数量
     */
    private static final Pattern patternStockNum = Pattern.compile("此卖家对该商品的数量限制为每位客户([1-9]\\d*|0)");
    /**
     * 商品详情url
     */
    private static final Pattern patternGoodsUrl = Pattern.compile("[a-zA-z]+://[^\\s]*psc=1");

    @Autowired
    private StockTrackerMapper stockTrackerMapper;

    @Override
    public GalenResponse barChartInfo(String token) {
        ChartData chartData = new ChartData();
        //7天顺序日期
        List dataString = this.getContinuityDataString(WEEKDAYS, "yyyy-MM-dd");
        // 公共x轴,1号y轴,2号y轴
        String[] columns = {"日期", "新增用户"};
        chartData.setColumns(columns);
        List<Object> rows = new LinkedList<>();
        chartData.setRows(rows);
        return ResponseUtils.SUCCESS(chartData);
    }

    @Override
    public GalenResponse add(String cartHtmlBody) {
        System.out.println(cartHtmlBody);
        StockTracker stockTracker = new StockTracker();
        //解析购物车的警告的P标签
        Matcher matcher = patternStockNum.matcher(cartHtmlBody);
        if (matcher.find()) {
            String stockNum = matcher.group(1);
            stockTracker.setStockNum(stockNum);
        }
        //产品链接
        matcher = patternGoodsUrl.matcher(cartHtmlBody);
        if (matcher.find()) {
            String goodsUrl = matcher.group(0);
            stockTracker.setGoodsUrl(goodsUrl);
            if (!StringUtils.isEmpty(goodsUrl)) {
                //获取asin
                int begin = goodsUrl.indexOf("dp/");
                if (-1 != begin) {
                    begin += 3;
                    int end = goodsUrl.indexOf("/ref");
                    if (-1 != end && end > begin) {
                        String goodsAsin = goodsUrl.substring(begin, end);
                        stockTracker.setGoodsAsin(goodsAsin);
                    }
                }
            }
        }
        stockTracker.setId(IdUtil.generateNumberId());
        stockTracker.initCreated();
        stockTrackerMapper.insert(stockTracker);
        return ResponseUtils.build(stockTracker);
    }

    @Override
    public GalenResponse list() {
        List<StockTracker> stockTrackerList = stockTrackerMapper.selectList(null);
        return ResponseUtils.build(stockTrackerList);
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

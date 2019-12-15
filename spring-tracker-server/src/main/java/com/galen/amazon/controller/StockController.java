package com.galen.amazon.controller;

import com.galen.amazon.pojo.GalenResponse;
import com.galen.amazon.service.StockService;
import com.galen.amazon.utils.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "库存监控", tags = "发邮件，短信等操作")
@RestController
@RequestMapping("stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @ApiOperation(value = "库存走势图")
    @GetMapping("bar-chart")
    public GalenResponse barChartInfo(String token) {
        if (StringUtils.isEmpty(token)) {
            return ResponseUtils.build(401, "请传入token");
        }
        return stockService.barChartInfo(token);
    }
}

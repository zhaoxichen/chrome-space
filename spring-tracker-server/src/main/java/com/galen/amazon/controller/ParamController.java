package com.galen.amazon.controller;

import com.galen.amazon.config.ParamConfig;
import com.galen.amazon.pojo.GalenResponse;
import com.galen.amazon.utils.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "参数持久化", tags = "参数持久化")
@RestController
@RequestMapping("config")
public class ParamController {

    @ApiOperation(value = "设置参数的值")
    @PostMapping("set-switch")
    public GalenResponse setSwitchConfig(String key, String value) {
        if (null == key) {
            return ResponseUtils.build(401, "请传入key");
        }
        Boolean boolValue;
        if ("true".equals(value)) {
            boolValue = true;
        } else {
            boolValue = false;
        }
        if ("seller_tracker_start".equals(key)) {
            ParamConfig.sellerTrackerStart = boolValue;
        } else if ("stock_tracker_start".equals(key)) {
            ParamConfig.stockTrackerStart = boolValue;
            System.out.println(key + ">>>" + ParamConfig.stockTrackerStart);
        }
        return ResponseUtils.SUCCESS("");
    }

    @ApiOperation(value = "获取参数的值")
    @GetMapping("get-switch")
    public Boolean getSwitchConfig(String key) {
        if (null == key) {
            return false;
        }
        if ("seller_tracker_start".equals(key)) {
            return ParamConfig.sellerTrackerStart;
        } else if ("stock_tracker_start".equals(key)) {
            return ParamConfig.stockTrackerStart;
        }
        return false;
    }
}

package com.galen.amazon.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "库存监控", tags = "发邮件，短信等操作")
@RestController
@RequestMapping("stock")
public class StockController {
}

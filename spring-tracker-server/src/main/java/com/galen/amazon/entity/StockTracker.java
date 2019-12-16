package com.galen.amazon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: chenzx05
 * @Date: 2019/12/16-10:08
 * @TODO: 库存记录
 **/
@TableName("stock_tracker")
@Data
public class StockTracker extends Base {

    @TableId
    private Long id;

    private String goodsName;

}

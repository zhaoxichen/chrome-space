package com.galen.amazon.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName("stock_tracker")
@Data
public class StockTracker implements Serializable {

    @TableId
    private Long id;

    private String goodsUrl;

}

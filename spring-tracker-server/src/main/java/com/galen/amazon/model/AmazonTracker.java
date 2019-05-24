package com.galen.amazon.model;

import java.io.Serializable;

/**
 * @Author: Galen
 * @Date: 2019/5/23-16:35
 * @Description: 跟卖
 **/

public class AmazonTracker implements Serializable {

    private Long id;

    private String goodsUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoodsUrl() {
        return goodsUrl;
    }

    public void setGoodsUrl(String goodsUrl) {
        this.goodsUrl = goodsUrl;
    }
}

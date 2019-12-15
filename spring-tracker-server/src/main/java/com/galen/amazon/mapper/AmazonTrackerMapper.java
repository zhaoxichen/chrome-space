package com.galen.amazon.mapper;

import com.galen.amazon.entity.AmazonTracker;
import org.springframework.stereotype.Repository;

@Repository
public interface AmazonTrackerMapper {

    int insert(AmazonTracker record);

    AmazonTracker selectByGoodsUrl(String goodsUrl);
}

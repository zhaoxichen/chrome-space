package com.galen.amazon.mapper;

import com.galen.amazon.model.AmazonTracker;
import org.springframework.stereotype.Repository;

@Repository
public interface AmazonTrackerMapper {

    int insert(AmazonTracker record);

    AmazonTracker selectByGoodsUrl(String goodsUrl);
}

package com.galen.amazon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.galen.amazon.entity.AmazonTracker;
import com.galen.amazon.entity.StockTracker;
import org.springframework.stereotype.Repository;

@Repository
public interface StockTrackerMapper extends BaseMapper<StockTracker> {

}

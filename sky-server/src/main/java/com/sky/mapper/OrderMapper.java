package com.sky.mapper;


import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.Map;

@Mapper
public interface OrderMapper {
    public void insert(Orders orders);

    Double sumByMap(Map map);
}

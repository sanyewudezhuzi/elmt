package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailMapper {

    /**
     * 保存明细表
     *
     * @param orderDetails
     */
    void saveList(List<OrderDetail> orderDetails);

}

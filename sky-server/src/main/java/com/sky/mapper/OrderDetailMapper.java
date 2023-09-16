package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderDetailMapper {

    /**
     * 保存明细表
     *
     * @param orderDetails
     */
    void saveList(List<OrderDetail> orderDetails);

    /**
     * 根据订单id获取明细表
     *
     * @param id
     * @return
     */
    @Select("select id, name, image, order_id, dish_id, setmeal_id, dish_flavor, number, amount " +
            "from order_detail " +
            "where order_id = #{id}")
    List<OrderDetail> getByOrderId(Long id);

}

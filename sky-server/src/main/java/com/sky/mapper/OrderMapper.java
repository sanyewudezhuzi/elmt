package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersConfirmDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.dto.OrdersRejectionDTO;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderMapper {

    /**
     * 保存订单
     *
     * @param orders
     */
    void save(Orders orders);

    /**
     * 根据订单号查询订单
     *
     * @param orderNumber
     */
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 修改订单信息
     *
     * @param orders
     */
    void update(Orders orders);

    /**
     * 查询历史订单
     *
     * @param ordersPageQueryDTO
     * @return
     */
    Page historyOrders(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 根据id查询订单
     *
     * @param orderId
     * @return
     */
    Orders getOrderById(Long orderId);

    /**
     * 按状态获取个数
     *
     * @param status
     * @return
     */
    Integer getCountGroupByStatus(Integer status);

    /**
     * 更改订单状态
     *
     * @param ordersConfirmDTO
     */
    @Update("update order set status = #{status} where id = #{orderId}")
    void updateStatus(OrdersConfirmDTO ordersConfirmDTO);

    /**
     * 拒单
     *
     * @param ordersRejectionDTO
     */
    void rejection(OrdersRejectionDTO ordersRejectionDTO);

    /**
     * 根据状态和下单时间查询订单
     *
     * @param status
     * @param ldt
     * @return
     */
    List<Orders> getByStatusAndOrdertimeLT(Integer status, LocalDateTime ldt);

    /**
     * 根据id查询订单
     *
     * @param id
     */
    @Select("select id , number , status , user_id , address_book_id , order_time , checkout_time , pay_method , pay_status , amount , remark , phone , address , consignee , estimated_delivery_time , delivery_status , pack_amount , tableware_number , tableware_status " +
            "from orders " +
            "where id = #{id}")
    Orders getById(Long id);

}

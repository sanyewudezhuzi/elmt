package com.sky.service.impl;

import com.sky.constant.StatusConstant;
import com.sky.entity.Orders;
import com.sky.mapper.DishMapper;
import com.sky.mapper.ReportMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.service.WorkSpaceService;
import com.sky.vo.BusinessDataVO;
import com.sky.vo.DishOverViewVO;
import com.sky.vo.OrderOverViewVO;
import com.sky.vo.SetmealOverViewVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class WorkSpaceServiceImpl implements WorkSpaceService {

    @Autowired
    private ReportMapper reportMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 根据时间段统计营业数据
     *
     * @param begin
     * @param end
     * @return
     */
    @Override
    public BusinessDataVO businessData(LocalDateTime begin, LocalDateTime end) {
        Map map = new HashMap();
        map.put("begin", begin);
        map.put("end", end);

        // 营业额：当日已完成订单的总金额
        Double turnover = reportMapper.sumByMap(map);

        // 有效订单：当日已完成订单的数量
        map.put("status", Orders.COMPLETED);
        Integer validOrderCount = reportMapper.getOrderCount(map);

        // 订单完成率：有效订单数 / 总订单数
        map.put("status", null);
        Double orderCompletionRate = validOrderCount == null || validOrderCount == 0 ? 0.0 : reportMapper.getOrderCount(map).doubleValue() / validOrderCount;

        // 平均客单价：营业额 / 有效订单数
        Double unitPrice = validOrderCount == null || validOrderCount == 0 ? 0.0 : turnover.doubleValue() / validOrderCount;

        // 新增用户：当日新增用户的数量
        Integer newUsers = reportMapper.getUserCount(map);

        return BusinessDataVO
                .builder()
                .turnover(turnover)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(orderCompletionRate)
                .unitPrice(unitPrice)
                .newUsers(newUsers)
                .build();
    }

    /**
     * 查询订单管理数据
     *
     * @return
     */
    @Override
    public OrderOverViewVO getOrderOverView() {
        Map map = new HashMap();
        map.put("begin", LocalDateTime.now().with(LocalTime.MIN));

        // 待接单数量
        map.put("status", Orders.TO_BE_CONFIRMED);
        Integer waitingOrders = reportMapper.getOrderCount(map);

        // 待派送数量
        map.put("status", Orders.CONFIRMED);
        Integer deliveredOrders = reportMapper.getOrderCount(map);

        // 已完成数量
        map.put("status", Orders.COMPLETED);
        Integer completedOrders = reportMapper.getOrderCount(map);

        // 已取消数量
        map.put("status", Orders.CANCELLED);
        Integer cancelledOrders = reportMapper.getOrderCount(map);

        // 全部订单
        map.put("status", null);
        Integer allOrders = reportMapper.getOrderCount(map);

        return OrderOverViewVO.builder()
                .waitingOrders(waitingOrders)
                .deliveredOrders(deliveredOrders)
                .completedOrders(completedOrders)
                .cancelledOrders(cancelledOrders)
                .allOrders(allOrders)
                .build();
    }

    /**
     * 查询菜品总览
     *
     * @return
     */
    @Override
    public DishOverViewVO getDishOverView() {
        Map map = new HashMap();

        // 已启售数量
        map.put("status", StatusConstant.ENABLE);
        Integer sold = dishMapper.countByMap(map);

        // 已停售数量
        map.put("status", StatusConstant.DISABLE);
        Integer discontinued = dishMapper.countByMap(map);

        return DishOverViewVO.builder()
                .sold(sold)
                .discontinued(discontinued)
                .build();
    }

    /**
     * 查询套餐总览
     *
     * @return
     */
    @Override
    public SetmealOverViewVO getSetmealOverView() {
        Map map = new HashMap();

        // 已启售数量
        map.put("status", StatusConstant.ENABLE);
        Integer sold = setmealMapper.countByMap(map);

        // 已停售数量
        map.put("status", StatusConstant.DISABLE);
        Integer discontinued = setmealMapper.countByMap(map);

        return SetmealOverViewVO.builder()
                .sold(sold)
                .discontinued(discontinued)
                .build();
    }

}

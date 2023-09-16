package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 处理支付超时订单
     */
    @Scheduled(cron = "0 0/1 * * * ? ") // 每一分钟执行一次
    public void processTimeoutOrder() {
        log.info("处理支付超时订单：{}", LocalDateTime.now());
        LocalDateTime ldt = LocalDateTime.now().plusMinutes(-15);
        List<Orders> list = orderMapper.getByStatusAndOrdertimeLT(Orders.PENDING_PAYMENT, ldt);
        if (list != null && list.size() > 0) {
            list.forEach((i) -> {
                i.setStatus(Orders.CANCELLED);
                i.setCancelReason("支付超时，自动取消");
                i.setCancelTime(LocalDateTime.now());
                orderMapper.update(i);
            });
        }
    }

    /**
     * 处理“派送中”状态的订单
     */
    @Scheduled(cron = "0 0 1 1/1 * ? ") // 每天一点执行一次
    public void processDeliveryOrder() {
        log.info("处理派送中订单：{}", LocalDateTime.now());
        LocalDateTime ldt = LocalDateTime.now().plusMinutes(-60);
        List<Orders> list = orderMapper.getByStatusAndOrdertimeLT(Orders.DELIVERY_IN_PROGRESS, ldt);
        if (list != null && list.size() > 0) {
            list.forEach((i) -> {
                i.setStatus(Orders.COMPLETED);
                orderMapper.update(i);
            });
        }
    }

}

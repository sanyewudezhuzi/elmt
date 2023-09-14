package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    /**
     * 添加购物车
     *
     * @param shoppingCartDTO
     */
    void save(ShoppingCartDTO shoppingCartDTO);

    /**
     * 查看购物车
     *
     * @param userId
     * @return
     */
    List<ShoppingCart> list(Long userId);

    /**
     * 情况购物车
     *
     * @param userId
     */
    void clean(Long userId);

    /**
     * 删除购物车中的一个商品
     *
     * @param shoppingCartDTO
     */
    void sub(ShoppingCartDTO shoppingCartDTO);

}

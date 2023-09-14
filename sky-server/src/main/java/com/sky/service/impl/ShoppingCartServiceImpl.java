package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import com.sky.vo.DishVO;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 添加购物车
     *
     * @param shoppingCartDTO
     */
    @Override
    public void save(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());
        // 判断是否已经存在
        List<ShoppingCart> shoppingCarts = shoppingCartMapper.getShoppingCart(shoppingCart);
        if (shoppingCarts != null && shoppingCarts.size() > 0) {
            // 存在则商品数量加一
            shoppingCarts.get(0).setNumber(shoppingCarts.get(0).getNumber() + 1);
            shoppingCartMapper.update(shoppingCarts.get(0));
            return;
        }
        // 不存在则新增商品
        Long dishId = shoppingCartDTO.getDishId();
        if (dishId != null) {
            // 菜品
            DishVO dishVO = dishMapper.getDishVOById(dishId);
            shoppingCart.setName(dishVO.getName());
            shoppingCart.setImage(dishVO.getImage());
            shoppingCart.setAmount(dishVO.getPrice());
        } else {
            // 套餐
            SetmealVO setmealVO = setmealMapper.getSetmealById(shoppingCartDTO.getSetmealId());
            shoppingCart.setName(setmealVO.getName());
            shoppingCart.setImage(setmealVO.getImage());
            shoppingCart.setAmount(setmealVO.getPrice());
        }
        shoppingCart.setNumber(1);
        shoppingCart.setCreateTime(LocalDateTime.now());
        shoppingCartMapper.save(shoppingCart);
    }

    /**
     * 查看购物车
     *
     * @param userId
     * @return
     */
    @Override
    public List<ShoppingCart> list(Long userId) {
        return shoppingCartMapper.list(userId);
    }

    /**
     * 清空购物车
     *
     * @param userId
     */
    @Override
    public void clean(Long userId) {
        shoppingCartMapper.clean(userId);
    }

    /**
     * 删除购物车中的一个商品
     *
     * @param shoppingCartDTO
     */
    @Override
    public void sub(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        List<ShoppingCart> cart = shoppingCartMapper.getShoppingCart(shoppingCart);
        if (cart.get(0).getNumber() > 1) {
            cart.get(0).setNumber(cart.get(0).getNumber() - 1);
            shoppingCartMapper.update(cart.get(0));
            return;
        }
        shoppingCartMapper.sub(shoppingCartDTO);
    }

}

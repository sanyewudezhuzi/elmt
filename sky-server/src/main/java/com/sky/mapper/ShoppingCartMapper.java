package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    /**
     * 获取购物车
     *
     * @param shoppingCart
     * @return
     */
    List<ShoppingCart> getShoppingCart(ShoppingCart shoppingCart);

    /**
     * 修改购物车
     *
     * @param shoppingCart
     */
    @Update("update shopping_cart set number = #{number} where id = #{id}")
    void update(ShoppingCart shoppingCart);

    /**
     * 保存购物车
     *
     * @param shoppingCart
     */
    @Insert("insert into shopping_cart (name, user_id, dish_id, setmeal_id, dish_flavor, number, amount, image, create_time) " +
            "values (#{name},#{userId},#{dishId},#{setmealId},#{dishFlavor},#{number},#{amount},#{image},#{createTime})")
    void save(ShoppingCart shoppingCart);

    /**
     * 查看购物车
     *
     * @param userId
     * @return
     */
    @Select("select id, name, user_id, dish_id, setmeal_id, dish_flavor, number, amount, image, create_time " +
            "from shopping_cart " +
            "where user_id = #{userId}")
    List<ShoppingCart> list(Long userId);

    /**
     * 清空购物车
     *
     * @param userId
     */
    @Delete("delete from shopping_cart " +
            "where user_id = #{userId}")
    void clean(Long userId);

    /**
     * 删除购物车中的一个商品
     *
     * @param shoppingCartDTO
     */
    void sub(ShoppingCartDTO shoppingCartDTO);

    /**
     * 批量保存购物车
     *
     * @param shoppingCarts
     */
    void saveList(List<ShoppingCart> shoppingCarts);

}

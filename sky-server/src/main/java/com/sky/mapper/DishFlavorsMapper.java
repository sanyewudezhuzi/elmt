package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorsMapper {

    /**
     * 新增菜品的口味
     *
     * @param flavors
     */
    void save(List<DishFlavor> flavors);

    /**
     * 根据菜品ids删除对应口味
     *
     * @param dishIds
     */
    void deleteByDishIds(List<Long> dishIds);

    /**
     * 根据菜品id获取对应口味
     *
     * @param dishId
     * @return
     */
    List<DishFlavor> getDishFlavorByDishId(Long dishId);

    /**
     * 根据菜品id删除口味
     *
     * @param dishId
     */
    void deleteByDishId(Long dishId);

}

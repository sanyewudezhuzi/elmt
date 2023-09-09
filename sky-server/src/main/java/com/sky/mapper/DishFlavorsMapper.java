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
    void deleteByIds(List<Long> dishIds);

}

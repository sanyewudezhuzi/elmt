package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    /**
     * 根据ids查询产生关联关系的菜品的个数
     *
     * @param ids
     * @return
     */
    Integer getCountOfBeRelated(List<Long> ids);
    
}

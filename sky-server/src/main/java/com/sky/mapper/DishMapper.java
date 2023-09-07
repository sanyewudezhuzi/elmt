package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品个数
     *
     * @param id
     * @return
     */
    @Select("select count(*) from dish where category_id = #{id}")
    Integer getDishCountByCategoryId(Long id);

}

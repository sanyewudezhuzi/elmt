package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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

    /**
     * 新增菜品
     *
     * @param dish
     */
    @AutoFill(OperationType.INSERT)
    void save(Dish dish);

    /**
     * 菜品分页查询
     *
     * @param dishPageQueryDTO
     * @return
     */
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 根据ids获取起售中菜品的个数
     *
     * @param ids
     * @return
     */
    Integer getCountOfEnableByIds(List<Long> ids);
    
    /**
     * 根据ids删除菜品
     *
     * @param ids
     */
    void deleteByIds(List<Long> ids);

}

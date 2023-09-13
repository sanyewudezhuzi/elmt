package com.sky.mapper;

import com.sky.entity.SetmealDish;
import com.sky.vo.DishItemVO;
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

    /**
     * 保存套餐中的菜品
     *
     * @param list
     */
    void save(List<SetmealDish> list);

    /**
     * 根据套餐ids删除对应的关联菜品
     *
     * @param ids
     */
    void deleteBySetmealIds(List<Long> ids);

    /**
     * 根据套餐id查询套餐中的菜品
     *
     * @param setmealId
     * @return
     */
    List<SetmealDish> getSetmealDishBySetmealId(Long setmealId);
    
    /**
     * 根据套餐id查询菜品列表
     *
     * @param id
     * @return
     */
    List<DishItemVO> getDishsById(Long id);

}

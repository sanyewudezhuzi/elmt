package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {

    /**
     * 新增菜品
     *
     * @param dishDTO
     */
    void save(DishDTO dishDTO);

    /**
     * 菜品分页查询
     *
     * @param dishPageQueryDTO
     * @return
     */
    PageResult page(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 批量删除菜品
     *
     * @param ids
     */
    void delete(List<Long> ids);

    /**
     * 根据id查询菜品
     *
     * @param id
     * @return
     */
    DishVO getDishVOById(Long id);

    /**
     * 修改菜品
     *
     * @param dishVO
     */
    void update(DishVO dishVO);

    /**
     * 根据分类id查询菜品，不包括口味
     *
     * @param categoryId
     * @return
     */
    List<Dish> list(Long categoryId);

    /**
     * 起售停售菜品
     *
     * @param status
     * @param id
     */
    void pickOrBan(Integer status, Long id);

    /**
     * 根据分类id查询菜品，包括口味
     *
     * @param categoryId
     * @return
     */
    List<DishVO> listPlus(Long categoryId);

}

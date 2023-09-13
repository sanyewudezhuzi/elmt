package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;

import java.util.List;

public interface SetmealService {

    /**
     * 新增套餐
     *
     * @param setmealDTO
     */
    void save(SetmealDTO setmealDTO);

    /**
     * 套餐分页查询
     *
     * @param setmealPageQueryDTO
     * @return
     */
    PageResult page(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 批量删除套餐
     *
     * @param ids
     */
    void deleteByIds(List<Long> ids);

    /**
     * 根据id查询套餐
     *
     * @param id
     * @return
     */
    SetmealVO getSetmealById(Long id);

    /**
     * 修改菜品
     *
     * @param setmealDTO
     */
    void update(SetmealDTO setmealDTO);

    /**
     * 起售停售商品
     *
     * @param status
     * @param id
     */
    void pickOrBan(Integer status, Long id);

    /**
     * 根据套餐id查询菜品列表
     *
     * @param id
     * @return
     */
    List<DishItemVO> getDishListById(Long id);

    /**
     * 根据分类id查询套餐
     *
     * @param categoryId
     * @return
     */
    List<Setmeal> list(Long categoryId);

}

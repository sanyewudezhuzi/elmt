package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorsMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorsMapper dishFlavorsMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * 新增菜品
     *
     * @param dishDTO
     */
    @Override
    @Transactional
    public void save(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.save(dish);
        Long id = dish.getId();
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            flavors = flavors.stream().map((i) -> {
                i.setDishId(id);
                return i;
            }).collect(Collectors.toList());
            dishFlavorsMapper.save(flavors);
        }
    }

    /**
     * 菜品分页查询
     *
     * @param dishPageQueryDTO
     * @return
     */
    @Override
    public PageResult page(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 批量删除菜品
     *
     * @param ids
     */
    @Override
    @Transactional
    public void delete(List<Long> ids) {
        // 起售中的菜品不能删除
        if (dishMapper.getCountOfEnableByIds(ids) > 0) {
            throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
        }

        // 被套餐关联的菜品不能删除
        if (setmealDishMapper.getCountOfBeRelated(ids) > 0) {
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }

        // 删除菜品后，关联的口味数据也需要删除掉
        dishMapper.deleteByIds(ids);
        dishFlavorsMapper.deleteByDishIds(ids);
    }

    /**
     * 根据id查询菜品
     *
     * @param id
     * @return
     */
    @Override
    public DishVO getDishVOById(Long id) {
        DishVO dishVO = dishMapper.getDishVOById(id);
        List<DishFlavor> flavors = dishFlavorsMapper.getDishFlavorByDishId(id);
        dishVO.setFlavors(flavors);
        return dishVO;
    }

    /**
     * 修改菜品
     *
     * @param dishVO
     */
    @Override
    @Transactional
    public void update(DishVO dishVO) {
        // 修改菜品
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishVO, dish);
        dishMapper.updateById(dish);

        // 先删除口味
        dishFlavorsMapper.deleteByDishId(dish.getId());

        // 再新增口味
        List<DishFlavor> dishFlavors = dishVO.getFlavors();
        if (dishFlavors != null && dishFlavors.size() > 0) {
            dishFlavors = dishFlavors.stream().map((i) -> {
                i.setDishId(dish.getId());
                return i;
            }).collect(Collectors.toList());
            dishFlavorsMapper.save(dishFlavors);
        }
    }

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId
     * @return
     */
    @Override
    public List<Dish> list(Long categoryId) {
        return dishMapper.list(categoryId);
    }

    /**
     * 起售停售菜品
     *
     * @param status
     * @param id
     */
    @Override
    public void pickOrBan(Integer status, Long id) {
        Dish dish = new Dish();
        dish.setStatus(status);
        dish.setId(id);
        dishMapper.updateById(dish);
    }

}

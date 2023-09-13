package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐个数
     *
     * @param id
     * @return
     */
    @Select("select count(*) from setmeal where category_id = #{id}")
    Integer getSetmealCountByCategoryId(Long id);

    /**
     * 新增菜品
     *
     * @param setmeal
     */
    @AutoFill(OperationType.INSERT)
    @Options(keyProperty = "id", useGeneratedKeys = true)
    @Insert("insert into setmeal(category_id, name, price, status, description, image, create_time, update_time, create_user, update_user) " +
            "values(#{categoryId}, #{name}, #{price}, #{status}, #{description}, #{image}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    void save(Setmeal setmeal);

    /**
     * 套餐分页查询
     *
     * @param setmealPageQueryDTO
     * @return
     */
    Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 根据ids获取启用中的套餐个数
     *
     * @param ids
     * @return
     */
    Integer getCountOfEnableByIds(List<Long> ids);

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
     * 根据id修改套餐
     *
     * @param setmeal
     */
    @AutoFill(OperationType.UPDATE)
    void updateById(Setmeal setmeal);

    /**
     * 根据套餐id获取套餐中处于停售状态的菜品个数
     *
     * @param id
     */
    Integer getCountOfEnableDishById(Long id);

    /**
     * 根据分类id查询套餐
     *
     * @param categoryId
     * @return
     */
    @Select("select category_id, name, price, status, description, image, create_time, update_time, create_user, update_user " +
            "from setmeal " +
            "where category_id = #{categoryId}")
    List<Setmeal> getSetmealsByCategoryId(Long categoryId);

}

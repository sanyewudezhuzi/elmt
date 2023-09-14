package com.sky.mapper;

import com.sky.controller.user.AddressBookController;
import com.sky.entity.AddressBook;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AddressBookMapper {

    /**
     * 新增地址
     *
     * @param addressBook
     */
    @Insert("insert into address_book(user_id, consignee, sex, phone, province_code, province_name, city_code, city_name, district_code, district_name, detail, label, is_default) " +
            "values(#{userId}, #{consignee}, #{sex}, #{phone}, #{provinceCode}, #{provinceName}, #{cityCode}, #{cityName}, #{districtCode}, #{districtName}, #{detail}, #{label}, #{isDefault})")
    void save(AddressBook addressBook);

    /**
     * 查询当前登录用户的所有地址信息
     *
     * @return
     */
    @Select("select id, user_id, consignee, sex, phone, province_code, province_name, city_code, city_name, district_code, district_name, detail, label, is_default " +
            "from address_book " +
            "where user_id = #{userId}")
    List<AddressBook> list(Long userId);

    /**
     * 查询默认地址
     *
     * @param userId
     * @return
     */
    @Select("select id, user_id, consignee, sex, phone, province_code, province_name, city_code, city_name, district_code, district_name, detail, label, is_default " +
            "from address_book " +
            "where user_id = #{userId} " +
            "and is_default = 1")
    AddressBook defaultAddress(Long userId);

    /**
     * 根据id查询地址
     *
     * @param id
     * @return
     */
    @Select("select id, user_id, consignee, sex, phone, province_code, province_name, city_code, city_name, district_code, district_name, detail, label, is_default " +
            "from address_book " +
            "where id = #{id}")
    AddressBook getAddressById(Long id);

    /**
     * 根据id修改地址
     *
     * @param addressBook
     */
    @Update("update address_book " +
            "set consignee = #{consignee}, " +
            "sex = #{sex}, " +
            "phone = #{phone}, " +
            "province_code = #{provinceCode}, " +
            "province_name = #{provinceName}, " +
            "city_code = #{cityCode}, " +
            "city_name = #{cityName}, " +
            "district_code = #{districtCode}, " +
            "district_name = #{districtName}, " +
            "detail = #{detail}, " +
            "label = #{label} " +
            "where id = #{id}")
    void updateById(AddressBook addressBook);

    /**
     * 取消用户的默认地址
     *
     * @param userId
     */
    @Update("update address_book " +
            "set is_default = 0 " +
            "where user_id = #{userId}")
    void updateDefaultAddressByUserId(Long userId);

    /**
     * 修改用户的默认地址
     *
     * @param id
     */
    @Update("update address_book " +
            "set is_default = 1 " +
            "where id = #{id}")
    void updateDefaultAddressById(Long id);

    /**
     * 根据id删除地址
     *
     * @param id
     */
    @Delete("delete from address_book " +
            "where id = #{id}")
    void deleteById(Long id);

}

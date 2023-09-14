package com.sky.service;

import com.sky.controller.user.AddressBookController;
import com.sky.dto.AddressBoodDTO;
import com.sky.entity.AddressBook;

import java.util.List;

public interface AddressBookService {

    /**
     * 新增地址
     *
     * @param addressBook
     */
    void save(AddressBook addressBook);

    /**
     * 查询当前登录用户的所有地址信息
     *
     * @param userId
     * @return
     */
    List<AddressBook> list(Long userId);

    /**
     * 查询默认地址
     *
     * @param userId
     * @return
     */
    AddressBook defaultAddress(Long userId);

    /**
     * 根据id查询地址
     *
     * @param id
     * @return
     */
    AddressBook getAddressById(Long id);

    /**
     * 根据id修改地址
     *
     * @param addressBook
     */
    void updateById(AddressBook addressBook);

    /**
     * 修改默认地址
     *
     * @param addressBoodDTO
     */
    void updateDefaultAddressById(AddressBoodDTO addressBoodDTO);

    /**
     * 根据id删除地址
     *
     * @param id
     */
    void deleteById(Long id);

}

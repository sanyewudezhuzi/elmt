package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.AddressBoodDTO;
import com.sky.entity.AddressBook;
import com.sky.mapper.AddressBookMapper;
import com.sky.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {

    @Autowired
    private AddressBookMapper addressBookMapper;

    /**
     * 新增地址
     *
     * @param addressBook
     */
    @Override
    public void save(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBook.setIsDefault(0);
        addressBookMapper.save(addressBook);
    }

    /**
     * 查询当前登录用户的所有地址信息
     *
     * @param userId
     * @return
     */
    @Override
    public List<AddressBook> list(Long userId) {
        return addressBookMapper.list(userId);
    }

    /**
     * 查询默认地址
     *
     * @param userId
     * @return
     */
    @Override
    public AddressBook defaultAddress(Long userId) {
        return addressBookMapper.defaultAddress(userId);
    }

    /**
     * 根据id查询地址
     *
     * @param id
     * @return
     */
    @Override
    public AddressBook getAddressById(Long id) {
        return addressBookMapper.getAddressById(id);
    }

    /**
     * 根据id修改地址
     *
     * @param addressBook
     */
    @Override
    public void updateById(AddressBook addressBook) {
        addressBookMapper.updateById(addressBook);
    }


    /**
     * 修改默认地址
     *
     * @param addressBoodDTO
     */
    @Override
    @Transactional
    public void updateDefaultAddressById(AddressBoodDTO addressBoodDTO) {
        addressBookMapper.updateDefaultAddressByUserId(addressBoodDTO.getUserId());
        addressBookMapper.updateDefaultAddressById(addressBoodDTO.getId());
    }

    /**
     * 根据id删除地址
     *
     * @param id
     */
    @Override
    public void deleteById(Long id) {
        addressBookMapper.deleteById(id);
    }

}

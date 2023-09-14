package com.sky.controller.user;

import com.sky.context.BaseContext;
import com.sky.dto.AddressBoodDTO;
import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/addressBook")
@Slf4j
@Api(tags = "地址簿接口")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    /**
     * 新增地址
     *
     * @param addressBook
     * @return
     */
    @PostMapping
    @ApiOperation("新增地址")
    public Result save(@RequestBody AddressBook addressBook) {
        log.info("Get AddressBook: {}", addressBook);
        addressBookService.save(addressBook);
        return Result.success();
    }

    /**
     * 查询当前登录用户的所有地址信息
     *
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("查询当前登录用户的所有地址信息")
    public Result<List<AddressBook>> list() {
        List<AddressBook> list = addressBookService.list(BaseContext.getCurrentId());
        return Result.success(list);
    }

    /**
     * 查询默认地址
     *
     * @return
     */
    @GetMapping("/default")
    @ApiOperation("查询默认地址")
    public Result<AddressBook> defaultAddress() {
        AddressBook addressBook = addressBookService.defaultAddress(BaseContext.getCurrentId());
        return Result.success(addressBook);
    }

    /**
     * 根据id查询地址
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询地址")
    public Result<AddressBook> getAddressById(@PathVariable Long id) {
        log.info("Get id: {}", id);
        AddressBook addressBook = addressBookService.getAddressById(id);
        return Result.success(addressBook);
    }

    /**
     * 根据id修改地址
     *
     * @param addressBook
     * @return
     */
    @PutMapping
    @ApiOperation("根据id修改地址")
    public Result updateById(@RequestBody AddressBook addressBook) {
        log.info("Get AddressBook: {}", addressBook);
        addressBookService.updateById(addressBook);
        return Result.success();
    }

    /**
     * 修改默认地址
     *
     * @param id
     * @return
     */
    @PutMapping("/default")
    @ApiOperation("修改默认地址")
    public Result updateDefaultAddressById(@RequestBody AddressBoodDTO addressBoodDTO) {
        addressBoodDTO.setUserId(BaseContext.getCurrentId());
        log.info("Get id: {}", addressBoodDTO);
        addressBookService.updateDefaultAddressById(addressBoodDTO);
        return Result.success();
    }

    /**
     * 根据id删除地址
     *
     * @param id
     * @return
     */
    @DeleteMapping
    @ApiOperation("根据id删除地址")
    public Result deleteById(Long id) {
        log.info("Get id: {}", id);
        addressBookService.deleteById(id);
        return Result.success();
    }

}

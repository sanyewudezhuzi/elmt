package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeEditPasswordDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 添加员工
     *
     * @param emp
     * @return
     */
    void save(EmployeeDTO emp);

    /**
     * 分页查询
     *
     * @return
     */
    PageResult page(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 启用禁用员工
     *
     * @param status
     * @param id
     */
    void pickOrBan(Integer status, Long id);

    /**
     * 根据id查询员工
     *
     * @param id
     * @return
     */
    Employee getById(Long id);

    /**
     * 编辑员工信息
     *
     * @param employeeDTO
     */
    void update(EmployeeDTO employeeDTO);

    /**
     * 修改密码
     *
     * @param employeeEditPasswordDTO
     */
    void editPassword(EmployeeEditPasswordDTO employeeEditPasswordDTO);

}

package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "员工修改密码时传递的数据模型")
public class EmployeeEditPasswordDTO implements Serializable {

    @ApiModelProperty("员工ID")
    private Long empId;

    @ApiModelProperty("旧密码")
    private String oldPassword;

    @ApiModelProperty("新密码")
    private String newPassword;

}

package com.lzh.sys.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lzh.sys.annotation.Excel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

@Data
/**
 *  @author: lzh
 *  @Date: 2019/8/29 17:31
 *  @Description:
 */ 

public class User implements Serializable{
    @TableId(type = IdType.AUTO)
    private Long id;
    @Excel(name = "用户登录名")
    @NotBlank(message = "用户名不能为空")
    private String userName;
    @NotBlank(message = "名字不能为空")
    private String name;
    @NotBlank(message = "密码不能为空")
    private String password;
    @TableField(exist = false)
    @NotBlank(message = "密码不能为空")
    private String determinePassword;
    private String salt;
    private String state;
    @NotBlank(message = "电话号码不能为空")
    @Pattern(regexp = "^[1][3,4,5,7,8][0-9]{9}$",message = "无效的电话号码")
    private String mobile;
    @TableField(exist = false)
    @NotBlank(message = "验证码不能为空")
    private String code;
    @TableField(exist = false)
    private List<UserRole> roles;
    public static boolean isAdmin(Long userId)
    {
        return userId != null && 1L == userId;
    }
}

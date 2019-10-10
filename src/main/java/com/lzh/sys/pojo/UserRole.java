package com.lzh.sys.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 功能描述
 *
 * @author lzh
 * @date 2019/8/29
 * @description
 */
@Data
public class UserRole implements Serializable {
    private User user;
    private Role role;
}

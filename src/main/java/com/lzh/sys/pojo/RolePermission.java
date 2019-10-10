package com.lzh.sys.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 功能描述
 *
 * @author lzh
 * @date 2019/8/29
 * @description 角色权限关联表
 */
@Data
public class RolePermission implements Serializable {
    private Role role;
    private Permission permission;
}

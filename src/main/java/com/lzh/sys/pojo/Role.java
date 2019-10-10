package com.lzh.sys.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Role implements Serializable {
    private Long id;
    private String role;
    private String description;
    @TableField(exist = false)
    private List<RolePermission> permissions;
}

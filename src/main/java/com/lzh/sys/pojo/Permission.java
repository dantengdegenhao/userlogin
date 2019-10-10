package com.lzh.sys.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class Permission implements Serializable {
    private Long id;
    private String name;
    private String url;
    private String permissionType;
    private String permission;
    private Long parentId;
}

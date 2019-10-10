package com.lzh.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lzh.sys.pojo.RolePermission;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionMapper {
    @Select("select * from role_permission where role_id=#{id}")
    @Results({
            /*@Result(property = "role",column = "role_id",one = @One(select = "com.lzh.sys.mapper.RoleMapper.selectById",fetchType = FetchType.LAZY)),*/
            @Result(property = "permission",column = "permission_id",one = @One(select = "com.lzh.sys.mapper.PermissionMapper.selectById"))
    })
    public List<RolePermission> getByRoleId(@Param("id") Long id);
}

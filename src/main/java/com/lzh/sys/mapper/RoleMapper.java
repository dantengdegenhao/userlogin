package com.lzh.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lzh.sys.pojo.Role;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleMapper extends BaseMapper<Role>{
    @Select("select * from role where id=#{id}")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "role",property = "role"),
            @Result(column = "description",property = "description"),
            @Result(column = "id",property = "permissions",many = @Many(select = "com.lzh.sys.mapper.RolePermissionMapper.getByRoleId"))
    })
    public Role getById(@Param("id") Long id);
}

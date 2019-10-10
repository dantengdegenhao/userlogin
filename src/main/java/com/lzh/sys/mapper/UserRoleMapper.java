package com.lzh.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lzh.sys.pojo.Role;
import com.lzh.sys.pojo.UserRole;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleMapper {
    @Select("select * from user_role where user_id=#{id}")
    @Results({
            /*@Result(property = "user",column = "user_id",one = @One(select = "com.lzh.sys.mapper.UserMapper.selectById",fetchType = FetchType.LAZY)),*/
            @Result(property = "role",column = "role_id",one = @One(select = "com.lzh.sys.mapper.RoleMapper.getById"))
    })
    public List<UserRole> getByUserId(@Param("id") Long id);
}

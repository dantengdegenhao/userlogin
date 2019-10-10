package com.lzh.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lzh.sys.pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User>{
    @Select("select * from user where user_name=#{username}")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "user_name",property = "userName"),
            @Result(column = "name",property = "name"),
            @Result(column = "password",property = "password"),
            @Result(column = "salt",property = "salt"),
            @Result(column = "state",property = "state"),
            @Result(column = "id",property = "roles",many = @Many(select = "com.lzh.sys.mapper.UserRoleMapper.getByUserId"))
    })
    public User getByUserName(@Param("username") String username);
}

package com.lzh.sys.server.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzh.sys.mapper.UserMapper;
import com.lzh.sys.pojo.User;
import com.lzh.sys.server.UserServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.lzh.sys.Utils.Md5Utils.MD5Pwd;

@Service
@Slf4j
public class UserServerImpl implements UserServer {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    private static final String userPrefix="sys:user:";
    private static final String columnId="id";
    private static final String columnUserName="user_name";
    private static final String columnName="name";
    private static final String columnPassword="password";
    private static final String columnSalt="salt";
    private static final String columnState="state";
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(User user) {
        Map map = MD5Pwd(user.getUserName(), user.getPassword());
        user.setPassword((String) map.get("password"));
        user.setSalt((String) map.get("salt"));
        int insert = userMapper.insert(user);
        /*int i=1/0;*/
        return insert;
    }

    @Override
    public User getByUserName(String userName)  {
        if (redisTemplate.hasKey(userPrefix + userName)){
            User user = (User)redisTemplate.opsForValue().get(userPrefix + userName);
            return user;
        }else {
            /*QueryWrapper<User> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq(columnUserName,userName);
            User user=userMapper.selectOne(queryWrapper);*/
            User user = userMapper.getByUserName(userName);
            if (user!=null) {
                redisTemplate.opsForValue().set(userPrefix + userName, user,1, TimeUnit.DAYS);
            }
            return user;
        }
    }

    /*@Override
    public User getBymobile(String mobile) {
        if (redisTemplate.hasKey(userPrefix + mobile)){
            User user = (User)redisTemplate.opsForValue().get(userPrefix + mobile);
            return user;
        }else {
            *//*QueryWrapper<User> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq(columnUserName,userName);
            User user=userMapper.selectOne(queryWrapper);*//*
            Map<String,Object> columnMap=new HashMap<>();
            columnMap.put("mobile",mobile);
            List<User> users = userMapper.selectByMap(columnMap);
            if (users!=null) {
                redisTemplate.opsForValue().set(userPrefix + mobile, users,1, TimeUnit.DAYS);
            }
            return users;
        }
    }*/
}

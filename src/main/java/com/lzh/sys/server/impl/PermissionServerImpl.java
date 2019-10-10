package com.lzh.sys.server.impl;

import com.lzh.sys.mapper.PermissionMapper;
import com.lzh.sys.pojo.Permission;
import com.lzh.sys.server.PermissionServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class PermissionServerImpl implements PermissionServer {
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    public static final String permissionPrefix="sys:permission:";
    @Override
    /*@Transactional(rollbackFor = Exception.class)*/
    public List<Permission> getList() {
        if (redisTemplate.hasKey(permissionPrefix+"list")){
            List list = (List)redisTemplate.opsForValue().get(permissionPrefix + "list");
            return list;
        }
            List<Permission> permissions = permissionMapper.selectList(null);
            redisTemplate.opsForValue().set(permissionPrefix+"list",permissions,7, TimeUnit.DAYS);
            return permissions;
    }
}

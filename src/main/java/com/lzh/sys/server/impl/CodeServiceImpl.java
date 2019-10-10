package com.lzh.sys.server.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 功能描述
 *
 * @author lzh
 * @date 2019/9/6
 * @description 验证码服务类
 */
@Service
public class CodeServiceImpl {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    public String code(){
        if (redisTemplate.hasKey("code")){
            return (String) redisTemplate.opsForValue().get("code");
        }
        return null;
    }
}

package com.lzh.sys.server;

import com.lzh.sys.pojo.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserServer {
    public int insert(User user);
    public User getByUserName(String userName);
    /*public User getBymobile(String mobile);*/
}

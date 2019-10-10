package com.lzh.sys.shiro;

import com.lzh.sys.mapper.RolePermissionMapper;
import com.lzh.sys.mapper.UserMapper;
import com.lzh.sys.mapper.UserRoleMapper;
import com.lzh.sys.pojo.Permission;
import com.lzh.sys.pojo.RolePermission;
import com.lzh.sys.pojo.User;
import com.lzh.sys.pojo.UserRole;
import com.lzh.sys.server.PermissionServer;
import com.lzh.sys.server.UserServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 描述：
 *
 * @author caojing
 * @create 2019-01-27-13:57
 */
@Slf4j
public class CustomRealm extends AuthorizingRealm {
    @Autowired
    @Lazy
    private UserServer userServer;
    @Autowired
    @Lazy
    private PermissionServer permissionServer;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("-------执行权限授权逻辑--------");
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (User.isAdmin(user.getId())){
            for (Permission permission:
            permissionServer.getList()) {
                if (permission.getPermission()!=null) {
                    info.addStringPermission(permission.getPermission());
                }
            }
        }else {
            for (UserRole userRole :
                    user.getRoles()) {
            /*info.addRole(userRole.getRole().getRole());*/
                for (RolePermission rolePermission :
                        userRole.getRole().getPermissions()) {
                    info.addStringPermission(rolePermission.getPermission().getPermission());
                }
            }
        }
        /*Set<String> stringSet = new HashSet<>();
        stringSet.add("user:show");
        stringSet.add("user:admin");
        info.setStringPermissions(stringSet);*/
        return info;
    }

    /**
     * 这里可以注入userService,为了方便演示，我就写死了帐号了密码
     * private UserService userService;
     * <p>
     * 获取即将需要认证的信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("-------执行身份认证逻辑--------");
        /*String userName = (String) authenticationToken.getPrincipal();
        String userPwd = new String((char[]) authenticationToken.getCredentials());*/
        UsernamePasswordToken token=(UsernamePasswordToken) authenticationToken;
        String userName=token.getUsername();
        /*String userPwd=new String(token.getPassword());*/
        //根据用户名从数据库获取User包含（获取密码，以及盐（未写））
        User user= null;
        try {
            user = userServer.getByUserName(userName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (user == null) {
           return null;
        }

        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配
        return new SimpleAuthenticationInfo(user, user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()), getName());

    }
}


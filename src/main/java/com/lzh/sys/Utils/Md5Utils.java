package com.lzh.sys.Utils;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

/**
 * Md5加密方法
 * 
 * @author numberone
 */
public class Md5Utils
{
    private static final Logger log = LoggerFactory.getLogger(Md5Utils.class);

    private static byte[] md5(String s)
    {
        MessageDigest algorithm;
        try
        {
            algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(s.getBytes("UTF-8"));
            byte[] messageDigest = algorithm.digest();
            return messageDigest;
        }
        catch (Exception e)
        {
            log.error("MD5 Error...", e);
        }
        return null;
    }

    private static final String toHex(byte hash[])
    {
        if (hash == null)
        {
            return null;
        }
        StringBuffer buf = new StringBuffer(hash.length * 2);
        int i;

        for (i = 0; i < hash.length; i++)
        {
            if ((hash[i] & 0xff) < 0x10)
            {
                buf.append("0");
            }
            buf.append(Long.toString(hash[i] & 0xff, 16));
        }
        return buf.toString();
    }

    public static String hash(String s)
    {
        try
        {
            return new String(toHex(md5(s)).getBytes("UTF-8"), "UTF-8");
        }
        catch (Exception e)
        {
            log.error("not supported charset...{}", e);
            return s;
        }
    }
    //注册时加密
    public static Map MD5Pwd(String username, String pwd) {
        // 加密算法MD5
        // salt盐 SecureRandomNumberGenerator().nextBytes().toHex(); //生成随机盐值
        // 迭代次数
        Map map=new HashMap();
        String salt = new SecureRandomNumberGenerator().nextBytes().toHex(); //Shiro自带的工具类，生成盐
        salt=username+salt;
        String md5Pwd = new SimpleHash("MD5", pwd,
                salt, 2).toHex();
        map.put("salt",salt);
        map.put("password",md5Pwd);
        return map;
    }
}

package com.lzh.sys.exception.file;


import com.lzh.sys.exception.base.BaseException;

/**
 * 文件信息异常类
 * 
 * @author numberone
 */
public class FileException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args)
    {
        super("file", code, args, null);
    }

}

package com.lzh.sys.exception.file;

import com.lzh.sys.exception.file.FileException;

/**
 * 文件名称超长限制异常类
 * 
 * @author numberone
 */
public class FileNameLengthLimitExceededException extends FileException
{
    private static final long serialVersionUID = 1L;

    public FileNameLengthLimitExceededException(int defaultFileNameLength)
    {
        super("upload.filename.exceed.length", new Object[] { defaultFileNameLength });
    }
}

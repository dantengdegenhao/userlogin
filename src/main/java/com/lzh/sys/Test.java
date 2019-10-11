package com.lzh.sys;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * 功能描述
 *
 * @author lzh
 * @date 2019/10/11
 * @description 编程题目测试
 */
public class Test {
    public static void main(String[] args) {

        Scanner scanner=new Scanner(System.in);

        Set<String> stringSet=new HashSet<String>();
        while (scanner.hasNext()){
            stringSet.add(scanner.next());
        }
        System.out.println("需要的材料有："+stringSet+"共"+stringSet.size()+"种");
    }

    }



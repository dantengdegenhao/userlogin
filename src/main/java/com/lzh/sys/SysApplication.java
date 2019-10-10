package com.lzh.sys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;

@SpringBootApplication
@MapperScan(value = "com.lzh.sys.mapper",annotationClass = Repository.class)
public class SysApplication {

	public static void main(String[] args) {
		SpringApplication.run(SysApplication.class, args);
	}
	//注入事务管理器
	@Autowired
	PlatformTransactionManager transactionManager=null;

	//使用初始化方法，观察自动生成的事务管理器
	@PostConstruct
	public void viewTransactionManager(){
		System.out.println(transactionManager.getClass().getName());
	}
}

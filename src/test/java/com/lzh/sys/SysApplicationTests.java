package com.lzh.sys;

import com.lzh.sys.Utils.HttpUtils;
import com.lzh.sys.Utils.poi.ExcelUtil;
import com.lzh.sys.base.AjaxResult;
import com.lzh.sys.pojo.User;
import com.lzh.sys.server.UserServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SysApplicationTests {
	@Autowired
	UserServer userServer;

	@Test
	public void contextLoads() {
		User user=new User();
		user.setName("测试员");
		user.setPassword("123456");
		user.setUserName("test");
		List<User> users=new ArrayList<>();
		users.add(user);
		ExcelUtil<User> util = new ExcelUtil<User>(User.class);
		AjaxResult result = util.exportExcel(users, "用户数据");
		log.info(result.toString());
	}

	@Test
	public void smsTest(){
		String host = "http://dingxin.market.alicloudapi.com";
		String path = "/dx/sendSms";
		String method = "POST";
		String appcode = "4b39ae2257db4b4ebe879adceed955c9";
		Map<String, String> headers = new HashMap<String, String>();
		//最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("mobile", "18888753189");
		querys.put("param", "code:1234");
		querys.put("tpl_id", "TP1711063");
		Map<String, String> bodys = new HashMap<String, String>();


		try {
			/**
			 * 重要提示如下:
			 * HttpUtils请从
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
			 * 下载
			 *
			 * 相应的依赖请参照
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
			 */
			HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
			System.out.println(response.toString());
			//获取response的body
			//System.out.println(EntityUtils.toString(response.getEntity()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

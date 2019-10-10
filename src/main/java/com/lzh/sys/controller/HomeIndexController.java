package com.lzh.sys.controller;

import com.lzh.sys.Utils.HttpUtils;
import com.lzh.sys.base.AjaxResult;
import com.lzh.sys.pojo.User;
import com.lzh.sys.server.impl.UserServerImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Controller
@Slf4j
public class HomeIndexController {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private UserServerImpl userServer;
    @GetMapping("/login")
    public String defaultLogin() {
        return "login";
    }
    @GetMapping("/")
    public String defaultLogin2() {
        return "redirect:index";
    }

    @GetMapping("/notRole")
    @ResponseBody
    public String noRole(){return "无权限";}

    @PostMapping(value = "/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, String rememberMe,
                        @RequestParam("captcha") String captcha, Model model) {
        //校验验证码
        //session中的验证码
        String sessionCaptcha = (String) SecurityUtils.getSubject().getSession().getAttribute(CaptchaController.KEY_CAPTCHA);
        if (null == captcha || !captcha.equalsIgnoreCase(sessionCaptcha)) {
            model.addAttribute("msg","验证码错误！");
            return "login";
        }

        // 从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(username,password,Boolean.parseBoolean(rememberMe));
        // 执行认证登陆操作
        try {
            subject.login(token);
            return "redirect:index";
        } catch (UnknownAccountException uae) {
            model.addAttribute("msg","未知账户");
        } catch (IncorrectCredentialsException ice) {
            model.addAttribute("msg","密码不正确");
        } catch (LockedAccountException lae) {
            model.addAttribute("msg","账户已锁定");
        } catch (ExcessiveAttemptsException eae) {
            model.addAttribute("msg","用户名或密码错误次数过多");
        } catch (AuthenticationException ae) {
            model.addAttribute("msg","用户名或密码不正确！");
        }
        return "login";
        /*if (!subject.isAuthenticated()) {
            token.clear();
            model.addAttribute("msg","未知错误登录失败，请联系管理员");
            return "login";
        }
        model.addAttribute("msg","登录失败，请联系管理员");
        return "login";*/
    }
    @GetMapping("/index")
    public String index(Model model) {
        User user=(User) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("username",user.getUserName());
        return "index";
    }
    /**
     *  @author: lzh
     *  @Date: 2019/9/6 17:02
     *  @Description:注册页面
     */
    @GetMapping("/registered")
    public String defaultRegistered(){
        return "registered";
    }

    @PostMapping("/registered")
    public String registered(@Valid User user, Errors errors,Model model){
        /*if (
                userServer.getByUserName(user.getUserName())!=null&&user.getPassword().equals(user.getDeterminePassword())
                        &&redisTemplate.hasKey("code")
                &&(int)redisTemplate.opsForValue().get("code")==Integer.parseInt(user.getMobile())){


        }*/
        /*if (userServer.getByUserName(user.getUserName())==null){
            model.addAttribute("msg","用户名");
        }
        else{
            model.addAttribute("msg","验证码错误");
        }*/
        HashMap code = (HashMap) redisTemplate.opsForValue().get("code");
        if (userServer.getByUserName(user.getUserName())!=null){
            errors.rejectValue("userName",null,"用户名已存在");
        }else if (!user.getPassword().equals(user.getDeterminePassword())){
            errors.rejectValue("determinePassword",null,"两次密码不一致");
        }else if (!redisTemplate.hasKey("code")){
            errors.rejectValue("code",null,"验证码已过期");
        }else if ((int)code.get("code")!=Integer.parseInt(user.getCode())){
            errors.rejectValue("code",null,"验证码不正确");
        }else if (!user.getMobile().equals((String) code.get("mobile"))){
            errors.rejectValue("mobile",null,"手机号与验证码不对应");
        }
        if (errors.hasErrors()){
            List<ObjectError> allErrors = errors.getAllErrors();
            for (ObjectError oe:
                    allErrors) {
                String key=null;
                String msg=null;
                if (oe instanceof FieldError){
                    FieldError fieldError=(FieldError) oe;
                    key = fieldError.getField();
                }else{
                    key = oe.getObjectName();
                }
                msg=oe.getDefaultMessage();
                log.info(msg);
                model.addAttribute(key,msg);
                model.addAttribute("user",user);
            }
            return "registered";
        }else {
            userServer.insert(user);
            return "redirect:login";
        }
        /*Map<String,Object> errMap=new HashMap();*/

    }

    @GetMapping("/code")
    @ResponseBody
    public AjaxResult code(@RequestParam("mobile") String mobile){
        /*Pattern p = Pattern.compile("");*/
        String host = "http://dingxin.market.alicloudapi.com";
        String path = "/dx/sendSms";
        String method = "POST";
        String appcode = "4b39ae2257db4b4ebe879adceed955c9";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", mobile);
        /*if (redisTemplate.hasKey("code")){
            querys.put("param", "code:"+redisTemplate.opsForValue().get("code"));
        }else {
            int i = (int) ((Math.random() * 9 + 1) * 100000);
            querys.put("param", "code:"+ i);
            redisTemplate.opsForValue().set("code",i,5, TimeUnit.MINUTES);
        }*/
        int i = (int) ((Math.random() * 9 + 1) * 100000);
        HashMap code = new HashMap();
        code.put("code",i);
        code.put("mobile",mobile);
        querys.put("param", "code:"+i);
        querys.put("tpl_id", "TP1711063");
        redisTemplate.opsForValue().set("code",code,5, TimeUnit.MINUTES);
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
            return AjaxResult.success();
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error();
        }
    }
}

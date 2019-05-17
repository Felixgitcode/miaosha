package com.imooc.miaosha.controller;

import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.MiaoshaUserService;
import com.imooc.miaosha.service.UserService;
import com.imooc.miaosha.util.ValidatorUtil;
import com.imooc.miaosha.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author Felix
 * @date 2019/5/12 15:27
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    MiaoshaUserService userService;

    @Autowired
    RedisService redisService;

    @RequestMapping("/to_login")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(HttpServletResponse response, @Valid LoginVo loginVo){
        log.info(loginVo.toString());
        /*参数检验：太麻烦，每个方法都要校验一遍，没有意义
        通过引入spring-boot-starter-validation的jar包，将需要校验的对象LoginVo用@Valid修饰
        然后将LoginVo的属性password和mobile用注解修饰，这里校验mobile格式，需要自写一个@isMobile注解
        */
       /* String passInut = loginVo.getPassword();
        String mobile = loginVo.getMobile();
        if(StringUtils.isEmpty(passInut)){
            return Result.error(CodeMsg.PASSWORD_EMPTY);
        }
        if(StringUtils.isEmpty(mobile)){
            return Result.error(CodeMsg.MOBILE_EMPTY);
        }
        if(!ValidatorUtil.isMobile(mobile)){
            return Result.error(CodeMsg.MOBILE_ERROR);
        }*/

        //登陆
        userService.login(response,loginVo);
        return Result.success(true);
    }
}

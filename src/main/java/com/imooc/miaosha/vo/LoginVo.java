package com.imooc.miaosha.vo;

import com.imooc.miaosha.validator.isMobile;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @author Felix
 * @date 2019/5/12 15:42
 */
//登陆时，用户输入的手机和密码封装进LoginVo对象中
public class LoginVo {
    @NotNull
    @isMobile
    private String mobile;

    @NotNull
    @Length(min=32)
    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginVo{" +
                "mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

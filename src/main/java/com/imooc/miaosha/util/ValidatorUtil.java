package com.imooc.miaosha.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Felix
 * @date 2019/5/12 16:02
 */
public class ValidatorUtil {
    //正则表达式规定手机号码格式
    private static final Pattern mobile_pattern = Pattern.compile("1\\d{10}");

    public static boolean isMobile(String src){
        if(StringUtils.isEmpty(src)){
            return false;
        }
        Matcher m = mobile_pattern.matcher(src);
        return m.matches();
    }
}

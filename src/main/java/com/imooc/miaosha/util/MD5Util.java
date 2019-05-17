package com.imooc.miaosha.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author Felix
 * @date 2019/5/11 21:02
 */
public class MD5Util {
    public static String md5(String str){
        return DigestUtils.md5Hex(str);
    }

    private static final String salt = "1a2b3c4d";//盐

    //将用户输入的密码混合盐值,进行一次md5生成表单密码
    public static String inputPassToFormPass(String inputPass){
        String str = "" + salt.charAt(0)+salt.charAt(2)+inputPass+salt.charAt(5)+salt.charAt(4);
        return md5(str);
    }
    //表单密码混合盐值,再进行一次md5生成数据库里的密码
    public static String formPassToDBPass(String formPass,String slat){
        String str = "" + salt.charAt(0)+salt.charAt(2)+formPass+salt.charAt(5)+salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDbPass(String inputPass,String slatDB){
        String formPass = inputPassToFormPass(inputPass);
        String dbPass = formPassToDBPass(formPass,slatDB);
        return dbPass;
    }

    public static void main(String[] args){
        System.out.println(inputPassToFormPass("123456"));//d3b1294a61a07da9b49b6e22b2cbd7f9
		System.out.println(formPassToDBPass(inputPassToFormPass("123456"), "1a2b3c4d"));
    	System.out.println(inputPassToDbPass("123456", "1a2b3c4d"));//b7797cce01b4b131b433b6acf4add449
    }

}

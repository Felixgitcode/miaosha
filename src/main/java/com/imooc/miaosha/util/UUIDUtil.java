package com.imooc.miaosha.util;

import java.util.UUID;

/**
 * @author Felix
 * @date 2019/5/16 9:47
 */
public class UUIDUtil {
    public static String uuid(){
        //原生的UUID中间有"-"，这里把它去掉
        return UUID.randomUUID().toString().replace("-","");
    }
}

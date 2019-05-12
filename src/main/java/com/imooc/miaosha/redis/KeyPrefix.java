package com.imooc.miaosha.redis;

/**
 * @author Felix
 * @date 2019/5/10 22:13
 */
public interface KeyPrefix {
    public int expireSeconds();//过期时间

    public String getPrefix();
}

package com.imooc.miaosha.redis;

/**
 * @author Felix
 * @date 2019/5/10 22:26
 */
public class OrderKey extends BasePrefix {
    private OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}

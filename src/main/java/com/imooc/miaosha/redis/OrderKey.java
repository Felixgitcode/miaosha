package com.imooc.miaosha.redis;

/**
 * @author Felix
 * @date 2019/5/10 22:26
 */
public class OrderKey extends BasePrefix {
    private OrderKey(String prefix) {
        super(prefix);
    }

    public static OrderKey getMiaoshaOrderByUidGid = new OrderKey("moug");
}

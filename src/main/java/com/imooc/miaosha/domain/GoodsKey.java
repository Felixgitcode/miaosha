package com.imooc.miaosha.domain;

import com.imooc.miaosha.redis.BasePrefix;

/**
 * @author Felix
 * @date 2019/5/10 22:25
 */
//主要为了保证key不重复，不同模块如user和order由类名区别，同一模块里由字段区别如id、name
public class GoodsKey extends BasePrefix {
    private GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds,prefix);
    }
    //缓存60s
    public static GoodsKey getGoodsList = new GoodsKey(60, "gl");
    public static GoodsKey getGoodsDetail = new GoodsKey(60, "gd");


}

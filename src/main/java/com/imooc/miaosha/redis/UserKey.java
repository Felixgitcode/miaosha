package com.imooc.miaosha.redis;

/**
 * @author Felix
 * @date 2019/5/10 22:25
 */
//主要为了保证key不重复，不同模块如user和order由类名区别，同一模块里由字段区别如id、name
public class UserKey extends BasePrefix {
    private UserKey(String prefix) {
        super(prefix);
    }

    public static UserKey getById = new UserKey("id");
    public static UserKey getByName = new UserKey("name");

}

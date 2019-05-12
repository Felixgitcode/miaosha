package com.imooc.miaosha.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Felix
 * @date 2019/5/9 15:07
 */
@Component
//这个注解会读取application.properties文件中以“redis”为前缀的字段配置
@ConfigurationProperties(prefix = "redis")
public class RedisConfig {
    private String host;//主机
    private int port;//端口
    private int timeout;//连接超时的时间，单位为秒
    private String password;//密码
    //连接池的配置
    private int poolMaxTotal;//最大连接数
    private int poolMaxIdle;//最大空闲数
    private int poolMaxWait;//最大建立连接等待时间，单位为秒

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPoolMaxTotal() {
        return poolMaxTotal;
    }

    public void setPoolMaxTotal(int poolMaxTotal) {
        this.poolMaxTotal = poolMaxTotal;
    }

    public int getPoolMaxIdle() {
        return poolMaxIdle;
    }

    public void setPoolMaxIdle(int poolMaxIdle) {
        this.poolMaxIdle = poolMaxIdle;
    }

    public int getPoolMaxWait() {
        return poolMaxWait;
    }

    public void setPoolMaxWait(int poolMaxWait) {
        this.poolMaxWait = poolMaxWait;
    }
}

package com.nageoffer.shortlink.project.common.constant;

/**
 @Author: Nuyoah
 @Date: idate
 @Description: Redis Key 常量类
 **/
public class RedisKeyConstant {
    /**
     * 短链接跳转前缀key
     */
    public static final String GOTO_SHORT_LINK_KEY = "short-link:goto_%s";
    /**
     * 短链接跳转锁前缀
     */
    public static final String LOCK_GOTO_SHORT_LINK_KEY = "short-link:lock_goto_%s";
}

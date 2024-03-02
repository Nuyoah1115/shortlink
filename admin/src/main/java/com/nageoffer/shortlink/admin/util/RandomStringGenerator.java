package com.nageoffer.shortlink.admin.util;

import java.util.Random;

/**
 * @Author: Nuyoah
 * @Date: idate
 * @Description: 分组ID随机生成器
 **/
public final class RandomStringGenerator {
    // 生成随机数的字符集合
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 6; // 生成的随机数长度

    // 生成随机数的方法
    public static String generateRandomString() {
        StringBuilder sb = new StringBuilder(LENGTH);
        Random random = new Random();
        for (int i = 0; i < LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }
}

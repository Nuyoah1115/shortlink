package com.nageoffer.shortlink.project.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @Author: Nuyoah
 * @Date: idate
 * @Description: 有效期类型
 **/
@RequiredArgsConstructor
public enum ValidDateTypeEnum {
    /**
     * 永久有效
     */
    PERMANENT(0),
    /**
     * 自定义有效期
     */
    CUSTOM(1);
    @Getter
    private final int type;
}

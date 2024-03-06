package com.nageoffer.shortlink.project.util;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

import java.util.Date;
import java.util.Optional;

import static com.nageoffer.shortlink.project.common.constant.ShortLinkConstant.DEFAULT_CACHE_VALID_TIME;

/**
 @Author: Nuyoah
 @Date: idate
 @Description: 短链接工具类
 **/
public class LinkUtil {
    /**
     * 获取短链接缓存有效期时间
     * @param validDate 短链接的有效期时间
     * @return 缓存的有效期时间戳
     */
    public static long getLinkCacheValidDate(Date validDate){
        return Optional.ofNullable(validDate)
                .map(each -> DateUtil.between(new Date(),each, DateUnit.MS))
                .orElse(DEFAULT_CACHE_VALID_TIME);
    }
}

package com.nageoffer.shortlink.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nageoffer.shortlink.project.dao.entity.ShortLinkDO;

/**
 @Author: Nuyoah
 @Date: idate
 @Description: URL标题接口层
 **/
public interface UrlTitleService {

    /**
     * 根据URL获取标题
     * @param url 目标网站路径
     * @return 网站标题
     */
    String getTitleByUrl(String url);
}

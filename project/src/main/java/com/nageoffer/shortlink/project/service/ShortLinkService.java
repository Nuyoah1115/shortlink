package com.nageoffer.shortlink.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nageoffer.shortlink.project.dao.entity.ShortLinkDO;
import com.nageoffer.shortlink.project.dto.req.ShortLinkSaveReqDTO;
import com.nageoffer.shortlink.project.dto.resp.ShortLinkSaveRespDTO;

/**
 * @Author: Nuyoah
 * @Date: idate
 * @Description: 短链接接口层
 **/
public interface ShortLinkService extends IService<ShortLinkDO> {
    /**
     * 创建短链接
     * @param saveReqDTO 创建短链接请求参数
     * @return 返回短链接创建信息
     */
    ShortLinkSaveRespDTO createShortLink(ShortLinkSaveReqDTO saveReqDTO);

}

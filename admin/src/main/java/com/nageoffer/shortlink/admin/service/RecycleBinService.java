package com.nageoffer.shortlink.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nageoffer.shortlink.admin.common.convention.result.Result;
import com.nageoffer.shortlink.admin.remote.dto.req.RecycleBinPageReqDTO;
import com.nageoffer.shortlink.admin.remote.dto.req.ShortLinkPageReqDTO;
import com.nageoffer.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;

/**
 @Author: Nuyoah
 @Date: idate
 @Description: 短链接回收站接口层
 **/
public interface RecycleBinService {

    /**
     * 分页查询回收站短链接
     * @param pageReqDTO 分页查询回收站请求参数
     * @return 分页查询回收站返回参数
     */
    Result<Page<ShortLinkPageRespDTO>> pageRecycleBinShortLink(RecycleBinPageReqDTO pageReqDTO);
}

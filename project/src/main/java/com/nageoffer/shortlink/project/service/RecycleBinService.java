package com.nageoffer.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nageoffer.shortlink.project.dao.entity.ShortLinkDO;
import com.nageoffer.shortlink.project.dto.req.RecycleBinPageReqDTO;
import com.nageoffer.shortlink.project.dto.req.RecycleBinRecoverReqDTO;
import com.nageoffer.shortlink.project.dto.req.RecycleBinSaveReqDTO;
import com.nageoffer.shortlink.project.dto.req.ShortLinkPageReqDTO;
import com.nageoffer.shortlink.project.dto.resp.ShortLinkPageRespDTO;

/**
 @Author: Nuyoah
 @Date: idate
 @Description: 回收站管理接口层
 **/
public interface RecycleBinService extends IService<ShortLinkDO> {
    /**
     * 短链接移至回收站
     * @param saveReqDTO
     */
    void saveRecycleBin(RecycleBinSaveReqDTO saveReqDTO);

    /**
     * 分页查询短链接
     *
     * @param pageReqDTO 请求参数
     * @return 返回参数
     */
    IPage<ShortLinkPageRespDTO> pageRecycleBinShortLink(RecycleBinPageReqDTO pageReqDTO);

    /**
     * 恢复短链接
     * @param recoverReqDTO 恢复短链接请求参数
     */
    void recoverRecycleBinShortLink(RecycleBinRecoverReqDTO recoverReqDTO);
}

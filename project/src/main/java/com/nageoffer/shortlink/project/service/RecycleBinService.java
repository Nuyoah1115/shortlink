package com.nageoffer.shortlink.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nageoffer.shortlink.project.dao.entity.ShortLinkDO;
import com.nageoffer.shortlink.project.dto.req.RecycleBinSaveReqDTO;

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

}

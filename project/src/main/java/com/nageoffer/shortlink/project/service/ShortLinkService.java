package com.nageoffer.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.nageoffer.shortlink.project.dao.entity.ShortLinkDO;
import com.nageoffer.shortlink.project.dto.req.ShortLinkPageReqDTO;
import com.nageoffer.shortlink.project.dto.req.ShortLinkSaveReqDTO;
import com.nageoffer.shortlink.project.dto.resp.ShortLinkGroupCountQueryRespDTO;
import com.nageoffer.shortlink.project.dto.resp.ShortLinkPageRespDTO;
import com.nageoffer.shortlink.project.dto.resp.ShortLinkSaveRespDTO;

import java.util.List;

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

    /**
     * 分页查询短链接
     * @param pageReqDTO 请求参数
     * @return 返回参数
     */
    IPage<ShortLinkPageRespDTO> pageShortLink(ShortLinkPageReqDTO pageReqDTO);

    /**
     * 查询短链接分组内短链接数量
     * @param gids 请求参数
     * @return 返回参数
     */
    List<ShortLinkGroupCountQueryRespDTO> listGroupShortLinkCount(List<String> gids);
}

package com.nageoffer.shortlink.admin.remote;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nageoffer.shortlink.admin.common.convention.result.Result;
import com.nageoffer.shortlink.admin.remote.dto.req.ShortLinkPageReqDTO;
import com.nageoffer.shortlink.admin.remote.dto.req.ShortLinkSaveReqDTO;
import com.nageoffer.shortlink.admin.remote.dto.req.ShortLinkUpdateReqDTO;
import com.nageoffer.shortlink.admin.remote.dto.resp.ShortLinkGroupCountQueryRespDTO;
import com.nageoffer.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;
import com.nageoffer.shortlink.admin.remote.dto.resp.ShortLinkSaveRespDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Nuyoah
 * @Date: idate
 * @Description: 短链接中台远程调用服务
 **/
public interface ShortLinkRemoteService {
    /**
     * 创建短链接
     *
     * @param saveReqDTO 创建短链接请求参数
     * @return 短链接创建响应
     */
    default Result<ShortLinkSaveRespDTO> createShortLink(ShortLinkSaveReqDTO saveReqDTO) {
        String resultBodyStr = HttpUtil.post("http://127.0.0.1:8001/api/short-link/v1/create", JSON.toJSONString(saveReqDTO));
        return JSON.parseObject(resultBodyStr, new TypeReference<>() {
        });
    }

    /**
     * 分页查询短链接
     *
     * @param pageReqDTO 分页短链接请求参数
     * @return 短链接分页查询响应
     */
    default public Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO pageReqDTO) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("gid", pageReqDTO.getGid());
        requestMap.put("current", pageReqDTO.getCurrent());
        requestMap.put("size", pageReqDTO.getSize());
        String resultPage = HttpUtil.get("http://127.0.0.1:8001/api/short-link/v1/page", requestMap);
        return JSON.parseObject(resultPage, new TypeReference<>() {
        });
    }

    /**
     * 查询短链接分组内短链接数量
     *
     * @param gids 分组标识数组
     * @return 返回分组标识和数量集合
     */
    default Result<List<ShortLinkGroupCountQueryRespDTO>> listGroupShortLinkCount(List<String> gids) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("gids", gids);
        String resultPage = HttpUtil.get("http://127.0.0.1:8001/api/short-link/v1/count", requestMap);
        return JSON.parseObject(resultPage, new TypeReference<>() {
        });
    }

    /**
     * 修改短链接
     *
     * @param gid          原分组标识
     * @param updateReqDTO 修改接收参数
     */
    default void updateShortLink(String gid, ShortLinkUpdateReqDTO updateReqDTO) {
        String resultBodyStr = HttpUtil.post("http://127.0.0.1:8001/api/short-link/v1/update?gid="+gid,JSON.toJSONString(updateReqDTO));
    }
}

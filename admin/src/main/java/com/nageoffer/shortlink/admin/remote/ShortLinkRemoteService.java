package com.nageoffer.shortlink.admin.remote;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nageoffer.shortlink.admin.common.convention.result.Result;
import com.nageoffer.shortlink.admin.common.convention.result.Results;
import com.nageoffer.shortlink.admin.remote.dto.req.*;
import com.nageoffer.shortlink.admin.remote.dto.resp.ShortLinkBatchCreateRespDTO;
import com.nageoffer.shortlink.admin.remote.dto.resp.ShortLinkGroupCountQueryRespDTO;
import com.nageoffer.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;
import com.nageoffer.shortlink.admin.remote.dto.resp.ShortLinkSaveRespDTO;
import org.springframework.web.bind.annotation.RequestParam;

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
    default Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO pageReqDTO) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("gid", pageReqDTO.getGid());
        requestMap.put("orderTag", pageReqDTO.getOrderTag());
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
     * @param updateReqDTO 修改接收参数
     */
    default void updateShortLink(ShortLinkUpdateReqDTO updateReqDTO) {
        HttpUtil.post("http://127.0.0.1:8001/api/short-link/v1/update", JSON.toJSONString(updateReqDTO));
    }

    /**
     * 根据URL获取标题
     * @param url 目标网站路径
     * @return 网站标题
     */
    default Result<String> getTitleByUrl(@RequestParam("url") String url) {
        String resultStr = HttpUtil.get("http://127.0.0.1:8001/api/short-link/v1/title?url=" + url);
        return JSON.parseObject(resultStr, new TypeReference<>() {
        });
    }

    /**
     * 短链接移至回收站
     * @param saveReqDTO
     */
    default void saveRecycleBin(RecycleBinSaveReqDTO saveReqDTO) {
        HttpUtil.post("http://127.0.0.1:8001/api/short-link/v1/recycle-bin/save", JSON.toJSONString(saveReqDTO));
    }

    /**
     * 分页查询回收站短链接
     *
     * @param pageReqDTO 分页短链接请求参数
     * @return 短链接分页查询响应
     */
    default Result<IPage<ShortLinkPageRespDTO>> pageRecycleBinShortLink(RecycleBinPageReqDTO pageReqDTO) {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("gids", pageReqDTO.getGids());
        requestMap.put("current", pageReqDTO.getCurrent());
        requestMap.put("size", pageReqDTO.getSize());
        String jsonStr = HttpUtil.get("http://127.0.0.1:8001/api/short-link/v1/recycle-bin/page", requestMap);
        return JSON.parseObject(jsonStr, new TypeReference<>() {
        });
    }

    /**
     * 恢复短链接
     * @param recoverReqDTO 短链接恢复请求参数
     */
    default void recoverRecycleBinShortLink(RecycleBinRecoverReqDTO recoverReqDTO) {
        HttpUtil.post("http://127.0.0.1:8001/api/short-link/v1/recycle-bin/recover", JSON.toJSONString(recoverReqDTO));
    }

    /**
     * 移除短链接
     * @param removeReqDTO 短链接移除请求参数
     */
    default void removeRecycleBinShortLink(RecycleBinRemoveReqDTO removeReqDTO) {
        HttpUtil.post("http://127.0.0.1:8001/api/short-link/v1/recycle-bin/recover", JSON.toJSONString(removeReqDTO));
    }

    /**
     * 批量创建短链接
     * @param saveReqDTO 批量创建短链接请求参数
     * @return 批量创建短链接返回参数
     */
    default Result<ShortLinkBatchCreateRespDTO> batchCreateShortLink(ShortLinkBatchCreateReqDTO saveReqDTO) {
        String resultBodyStr = HttpUtil.post("http://127.0.0.1:8001/api/short-link/v1/create/batch", JSON.toJSONString(saveReqDTO));
        return JSON.parseObject(resultBodyStr, new TypeReference<>() {
        });
    }
}

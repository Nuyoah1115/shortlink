package com.nageoffer.shortlink.project.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author: Nuyoah
 * @Date: idate
 * @Description: 短链接参数响应实体
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShortLinkSaveRespDTO {

    /**
     * 完整短链接
     */
    private String fullShortUrl;

    /**
     * 原始链接
     */
    private String originUrl;

    /**
     * 分组标识
     */
    private String gid;

}

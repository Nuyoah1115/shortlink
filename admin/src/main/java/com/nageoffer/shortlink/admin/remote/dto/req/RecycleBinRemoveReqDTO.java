package com.nageoffer.shortlink.admin.remote.dto.req;

import lombok.Data;

/**
 @Author: Nuyoah
 @Date: idate
 @Description: 回收站移除功能
 **/
@Data
public class RecycleBinRemoveReqDTO {
    /**
     * 分组标识
     */
    private String gid;

    /**
     * 完整短链接
     */
    private String fullShortUrl;
}

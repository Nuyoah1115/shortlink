package com.nageoffer.shortlink.project.dto.req;

import lombok.Data;

/**
 @Author: Nuyoah
 @Date: idate
 @Description: 回收站保存功能
 **/
@Data
public class RecycleBinSaveReqDTO {
    /**
     * 分组标识
     */
    private String gid;

    /**
     * 完整短链接
     */
    private String fullShortUrl;
}

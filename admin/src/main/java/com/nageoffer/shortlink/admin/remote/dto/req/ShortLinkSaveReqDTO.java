package com.nageoffer.shortlink.admin.remote.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author: Nuyoah
 * @Date: idate
 * @Description: 短链接参数请求实体
 **/
@Data
public class ShortLinkSaveReqDTO {

    /**
     * 域名
     */
    private String domain;

    /**
     * 原始链接
     */
    private String originUrl;

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 创建类型 0: 接口创建 1: 控制台创建
     */
    private int createType;

    /**
     * 有效期类型 0: 永久有效 1: 自定义
     */
    private int validDateType;

    /**
     * 有效期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date validDate;

    /**
     * 描述
     */
    private String description;
}

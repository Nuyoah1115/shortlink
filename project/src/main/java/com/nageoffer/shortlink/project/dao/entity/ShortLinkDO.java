package com.nageoffer.shortlink.project.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.nageoffer.shortlink.project.common.database.BaseDO;
import lombok.Data;

import java.util.Date;

/**
 * @Author: Nuyoah
 * @Date: idate
 * @Description: 短链接实体
 **/
@Data
@TableName("t_link")
public class ShortLinkDO extends BaseDO {
    /**
     * id
     */
    private Long id;

    /**
     * 域名
     */
    private String domain;

    /**
     * 短链接
     */
    private String shortUri;

    /**
     * 完整短链接
     */
    private String fullShortUrl;

    /**
     * 原始链接
     */
    private String originUrl;

    /**
     * 点击量
     */
    private Integer clickNum;

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 启用标识 0: 启用 1: 未启用
     */
    private int enableStatus;

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
    private Date validDate;

    /**
     * 描述
     */
    private String description;

}

package com.nageoffer.shortlink.project.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.nageoffer.shortlink.project.common.database.BaseDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 @Author: Nuyoah
 @Date: idate
 @Description: 访问日志监控实体
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("t_link_access_logs")
public class LinkAccessLogsDO extends BaseDO {

    /**
     * id
     */
    private Long id;

    /**
     * 完整短链接
     */
    private String fullShortUrl;

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 用户信息
     */
    private String user;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 访问设备
     */
    private String device;

    /**
     * 访问网络
     */
    private String network;

    /**
     * 访问地区
     */
    private String locale;

    /**
     * ip
     */
    private String ip;
}

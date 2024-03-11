package com.nageoffer.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nageoffer.shortlink.project.dao.entity.LinkOSStatsDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 @Author: Nuyoah
 @Date: 2024/3/11
 @Description: 操作系统访问统计持久层
 **/
public interface LinkOSStatsMapper extends BaseMapper<LinkOSStatsDO> {

    /**
     * 记录操作系统访问监控数据
     */
    @Insert("INSERT INTO t_link_os_stats (full_short_url, gid, date, cnt, os, create_time, update_time, del_flag) " +
            "VALUES( #{linkOSStats.fullShortUrl} , #{linkOSStats.gid}, #{linkOSStats.date}, #{linkOSStats.cnt}, #{linkOSStats.os}, NOW(), NOW(), 0) " +
            "ON DUPLICATE KEY UPDATE cnt = cnt +  #{linkOSStats.cnt};")
    void shortLinkOSStats(@Param("linkOSStats") LinkOSStatsDO linkOSStats);
}

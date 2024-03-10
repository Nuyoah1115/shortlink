package com.nageoffer.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nageoffer.shortlink.project.dao.entity.LinkAccessStatsDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 @Author: Nuyoah
 @Date: idate
 @Description: 短链接监控 基础访问持久层
 **/
public interface LinkAccessStatsMapper extends BaseMapper<LinkAccessStatsDO> {
    /**
     * 记录基础访问监控数据
     */
    @Insert("INSERT INTO t_link_access_stats ( full_short_url, gid, date, pv, uv, uip, HOUR, weekday, create_time, update_time, del_flag )" +
            "VALUES( #{linkAccessState.fullShortUrl} , #{linkAccessState.gid} , #{linkAccessState.date} " +
            ", #{linkAccessState.pv} , #{linkAccessState.uv} , #{linkAccessState.uip} , #{linkAccessState.hour} " +
            ", #{linkAccessState.weekday} , NOW(), NOW(), 0 ) " +
            "ON DUPLICATE KEY UPDATE " +
            "pv = pv + #{linkAccessState.pv}," +
            "uv = uv + #{linkAccessState.uv}," +
            "uip = uip + #{linkAccessState.uip};")
    void shortLinkStats(@Param("linkAccessState") LinkAccessStatsDO linkAccessState);
}

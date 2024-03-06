package com.nageoffer.shortlink.project.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nageoffer.shortlink.project.dao.entity.ShortLinkDO;
import com.nageoffer.shortlink.project.dao.entity.ShortLinkGotoDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: Nuyoah
 * @Date: idate
 * @Description: 短链接跳转持久层
 **/
public interface ShortLinkGotoMapper extends BaseMapper<ShortLinkGotoDO> {
}

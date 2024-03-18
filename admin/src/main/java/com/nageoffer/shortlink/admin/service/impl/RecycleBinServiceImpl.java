package com.nageoffer.shortlink.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nageoffer.shortlink.admin.common.biz.user.UserContext;
import com.nageoffer.shortlink.admin.common.convention.exception.ServiceException;
import com.nageoffer.shortlink.admin.common.convention.result.Result;
import com.nageoffer.shortlink.admin.common.convention.result.Results;
import com.nageoffer.shortlink.admin.dao.entity.GroupDO;
import com.nageoffer.shortlink.admin.dao.mapper.GroupMapper;
import com.nageoffer.shortlink.admin.remote.ShortLinkActualRemoteService;
import com.nageoffer.shortlink.admin.remote.ShortLinkRemoteService;
import com.nageoffer.shortlink.admin.remote.dto.req.RecycleBinPageReqDTO;
import com.nageoffer.shortlink.admin.remote.dto.req.ShortLinkPageReqDTO;
import com.nageoffer.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;
import com.nageoffer.shortlink.admin.service.RecycleBinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.List;

/**
 @Author: Nuyoah
 @Date: idate
 @Description: 短链接回收站接口实现层
 **/
@Service
@RequiredArgsConstructor
public class RecycleBinServiceImpl implements RecycleBinService {

    private final GroupMapper groupMapper;
    private final ShortLinkActualRemoteService shortLinkActualRemoteService;

    @Override
    public Result<Page<ShortLinkPageRespDTO>> pageRecycleBinShortLink(RecycleBinPageReqDTO pageReqDTO) {
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .eq(GroupDO::getDelFlag, 0);
        List<GroupDO> groupDOList = groupMapper.selectList(queryWrapper);
        if (CollUtil.isEmpty(groupDOList)){
            throw new ServiceException("该用户暂未创建任何分组信息");
        }
        pageReqDTO.setGids(groupDOList.stream().map(GroupDO::getGid).toList());
        return shortLinkActualRemoteService.pageRecycleBinShortLink(
                pageReqDTO.getGids(),
                pageReqDTO.getCurrent(),
                pageReqDTO.getCurrent()
                );
    }
}

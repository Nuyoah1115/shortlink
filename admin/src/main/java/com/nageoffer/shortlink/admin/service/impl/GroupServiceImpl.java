package com.nageoffer.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nageoffer.shortlink.admin.common.biz.user.UserContext;
import com.nageoffer.shortlink.admin.common.convention.exception.ClientException;
import com.nageoffer.shortlink.admin.common.convention.result.Result;
import com.nageoffer.shortlink.admin.dao.entity.GroupDO;
import com.nageoffer.shortlink.admin.dao.mapper.GroupMapper;
import com.nageoffer.shortlink.admin.dto.req.GroupReqOrderDTO;
import com.nageoffer.shortlink.admin.dto.req.GroupReqUpdateDTO;
import com.nageoffer.shortlink.admin.dto.resp.GroupRespDTO;
import com.nageoffer.shortlink.admin.remote.ShortLinkActualRemoteService;
import com.nageoffer.shortlink.admin.remote.dto.resp.ShortLinkGroupCountQueryRespDTO;
import com.nageoffer.shortlink.admin.service.GroupService;
import com.nageoffer.shortlink.admin.util.RandomStringGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.nageoffer.shortlink.admin.common.constant.RedisCacheConstant.LOCK_GROUP_CACHE_KEY;

/**
 * @Author: Nuyoah
 * @Date: idate
 * @Description: 短链接分组接口实现层
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupDO> implements GroupService {

    private final ShortLinkActualRemoteService shortLinkActualRemoteService;
    private final RedissonClient redissonClient;

    @Value("${short-link.group.max-limit}")
    private Integer groupMaxLimit;

    @Override
    public void saveGroup(String groupName) {
        this.saveGroup(UserContext.getUsername(), groupName);
    }

    @Override
    public void saveGroup(String username, String groupName) {
        RLock lock = redissonClient.getLock(String.format(LOCK_GROUP_CACHE_KEY, username));
        lock.lock();
        try {
            LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                    .eq(GroupDO::getUsername, username)
                    .eq(GroupDO::getDelFlag, 0);
            List<GroupDO> groupDOList = baseMapper.selectList(queryWrapper);
            if (CollUtil.isNotEmpty(groupDOList) && groupDOList.size() == groupMaxLimit) {
                throw new ClientException(String.format("分组已超出最大分组限制: %d", groupMaxLimit));
            }
            String gid;
            do {
                gid = RandomStringGenerator.generateRandomString();
            } while (!hasGid(username, gid));
            GroupDO groupDO = GroupDO.builder()
                    .gid(gid)
                    .sortOrder(0)
                    .name(groupName)
                    .username(username)
                    .build();
            baseMapper.insert(groupDO);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public List<GroupRespDTO> listGroup() {
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getDelFlag, 0)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .orderByDesc(GroupDO::getSortOrder, GroupDO::getUpdateTime);
        List<GroupDO> groupDOList = baseMapper.selectList(queryWrapper);
        Result<List<ShortLinkGroupCountQueryRespDTO>> listResult = shortLinkActualRemoteService
                .listGroupShortLinkCount(groupDOList.stream().map(GroupDO::getGid).toList());
        List<GroupRespDTO> groupRespDTOS = BeanUtil.copyToList(groupDOList, GroupRespDTO.class);
        groupRespDTOS.forEach(each -> {
            Optional<ShortLinkGroupCountQueryRespDTO> first = listResult.getData().stream()
                    .filter(item -> Objects.equals(item.getGid(), each.getGid())).findFirst();
            first.ifPresent(item -> each.setShortLinkCount(first.get().getShortLinkCount()));
        });
        return groupRespDTOS;
    }

    @Override
    public void updateGroup(GroupReqUpdateDTO updateDTO) {
        LambdaUpdateWrapper<GroupDO> updateWrapper = Wrappers.lambdaUpdate(GroupDO.class)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .eq(GroupDO::getGid, updateDTO.getGid())
                .eq(GroupDO::getDelFlag, 0);
        GroupDO groupDO = GroupDO.builder().name(updateDTO.getName()).build();
        baseMapper.update(groupDO, updateWrapper);
    }

    @Override
    public void deleteGroup(String gid) {
        LambdaUpdateWrapper<GroupDO> updateWrapper = Wrappers.lambdaUpdate(GroupDO.class)
                .eq(GroupDO::getUsername, UserContext.getUsername())
                .eq(GroupDO::getGid, gid)
                .eq(GroupDO::getDelFlag, 0);
        GroupDO groupDO = new GroupDO();
        groupDO.setDelFlag(1);
        baseMapper.update(groupDO, updateWrapper);
    }

    /**
     * @param orderDTOS
     */
    @Override
    public void sortGroup(List<GroupReqOrderDTO> orderDTOS) {
        orderDTOS.forEach(one -> {
            GroupDO groupDO = GroupDO.builder()
                    .sortOrder(one.getSortOrder())
                    .build();
            LambdaUpdateWrapper<GroupDO> updateWrapper = Wrappers.lambdaUpdate(GroupDO.class)
                    .eq(GroupDO::getUsername, UserContext.getUsername())
                    .eq(GroupDO::getGid, one.getGid())
                    .eq(GroupDO::getDelFlag, 0);
            baseMapper.update(groupDO, updateWrapper);
        });
    }

    /**
     * gid是否重复
     *
     * @param gid 分组标识
     * @return
     */
    private boolean hasGid(String username, String gid) {
        LambdaQueryWrapper<GroupDO> queryWrapper = Wrappers.lambdaQuery(GroupDO.class)
                .eq(GroupDO::getGid, gid)
                .eq(GroupDO::getUsername, Optional.ofNullable(username).orElse(UserContext.getUsername()));
        GroupDO hasGroupFlag = baseMapper.selectOne(queryWrapper);
        return hasGroupFlag == null;
    }
}

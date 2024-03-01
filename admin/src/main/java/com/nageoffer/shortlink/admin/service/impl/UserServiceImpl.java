package com.nageoffer.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nageoffer.shortlink.admin.common.convention.exception.ClientException;
import com.nageoffer.shortlink.admin.common.enums.UserErrorCodeEnum;
import com.nageoffer.shortlink.admin.dao.entity.UserDo;
import com.nageoffer.shortlink.admin.dao.mapper.UserMapper;
import com.nageoffer.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.nageoffer.shortlink.admin.dto.resp.UserRespDto;
import com.nageoffer.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBloomFilter;
import org.springframework.stereotype.Service;

import static com.nageoffer.shortlink.admin.common.enums.UserErrorCodeEnum.USER_SAVE_FAIL;

/**
 * @Author: Nuyoah
 * @Date: idate
 * @Description: 用户接口实现层
 **/
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDo> implements UserService {

    private final RBloomFilter<String> userRegisterCachePenetrationBloomFilter;

    @Override
    public UserRespDto getUserByUsername(String username) {
        LambdaQueryWrapper<UserDo> queryWrapper = Wrappers.lambdaQuery(UserDo.class)
                .eq(UserDo::getUsername, username);
        UserDo userDo = baseMapper.selectOne(queryWrapper);
        if (userDo == null) {
            throw new ClientException("这是客户端返回的错误", UserErrorCodeEnum.USER_NULL);
        }
        UserRespDto userRespDto = BeanUtil.copyProperties(userDo, UserRespDto.class);
        return userRespDto;
    }

    @Override
    public Boolean hasUsername(String username) {
        return userRegisterCachePenetrationBloomFilter.contains(username);
    }

    @Override
    public void register(UserRegisterReqDTO registerReqDTO) {
        if (hasUsername(registerReqDTO.getUsername())) {
            throw new ClientException(UserErrorCodeEnum.USER_NAME_EXIST);
        }
        int insert = baseMapper.insert(BeanUtil.toBean(registerReqDTO, UserDo.class));
        if (insert < 1) {
            throw new ClientException(USER_SAVE_FAIL);
        }
        userRegisterCachePenetrationBloomFilter.add(registerReqDTO.getUsername());
    }
}

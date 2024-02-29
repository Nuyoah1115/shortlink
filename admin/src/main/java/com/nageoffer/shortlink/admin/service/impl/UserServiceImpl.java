package com.nageoffer.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nageoffer.shortlink.admin.common.convention.exception.ClientException;
import com.nageoffer.shortlink.admin.common.enums.UserErrorCodeEnum;
import com.nageoffer.shortlink.admin.dao.entity.UserDo;
import com.nageoffer.shortlink.admin.dao.mapper.UserMapper;
import com.nageoffer.shortlink.admin.dto.resp.UserRespDto;
import com.nageoffer.shortlink.admin.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @Author: Nuyoah
 * @Date: idate
 * @Description: 用户接口实现层
 **/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDo> implements UserService {
    @Override
    public UserRespDto getUserByUsername(String username) {
        LambdaQueryWrapper<UserDo> queryWrapper = Wrappers.lambdaQuery(UserDo.class)
                .eq(UserDo::getUsername, username);
        UserDo userDo = baseMapper.selectOne(queryWrapper);
        if (userDo==null){
            throw new ClientException("这是客户端返回的错误",UserErrorCodeEnum.USER_NULL);
        }
        UserRespDto userRespDto = BeanUtil.copyProperties(userDo, UserRespDto.class);
        return userRespDto;
    }
}

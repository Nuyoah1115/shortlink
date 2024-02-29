package com.nageoffer.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nageoffer.shortlink.admin.dao.entity.UserDo;
import com.nageoffer.shortlink.admin.dto.resp.UserRespDto;

/**
 * @Author: Nuyoah
 * @Date: idate
 * @Description:
 **/
public interface UserService extends IService<UserDo> {
    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 用户返回实体
     */
    UserRespDto getUserByUsername(String username);
}

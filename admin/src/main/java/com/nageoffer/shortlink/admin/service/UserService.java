package com.nageoffer.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nageoffer.shortlink.admin.dao.entity.UserDo;
import com.nageoffer.shortlink.admin.dto.req.UserLoginReqDTO;
import com.nageoffer.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.nageoffer.shortlink.admin.dto.req.UserUpdateReqDTO;
import com.nageoffer.shortlink.admin.dto.resp.UserLoginRespDTO;
import com.nageoffer.shortlink.admin.dto.resp.UserRespDTO;

/**
 * @Author: Nuyoah
 * @Date: idate
 * @Description: 用户接口层
 **/
public interface UserService extends IService<UserDo> {
    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户返回实体
     */
    UserRespDTO getUserByUsername(String username);

    /**
     * 查询用户名是否存在
     *
     * @param username 用户名
     * @return 存在返回true 不存在返回false
     */
    Boolean hasUsername(String username);

    /**
     * 注册用户
     * @param registerReqDTO 注册用户请求参数
     */
    void register(UserRegisterReqDTO registerReqDTO);

    /**
     * 根据用户名修改用户信息
     * @param updateReqDTO 修改用户请求参数
     */
    void update(UserUpdateReqDTO updateReqDTO);

    /**
     * 用户登录
     * @param loginReqDTO 用户登录请求参数
     * @return 用户登录返回参数
     */
    UserLoginRespDTO login(UserLoginReqDTO loginReqDTO);

    /**
     * 检查用户是否登录
     * @param username 用户名
     * @param token 用户登录token
     * @return 登录标识
     */
    Boolean checkLogin(String username,String token);

    /**
     * 退出登录
     * @param username 用户名
     * @param token 登录token
     */
    void logout(String username, String token);
}

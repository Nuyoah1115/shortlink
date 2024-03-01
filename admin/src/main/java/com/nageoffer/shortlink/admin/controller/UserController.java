package com.nageoffer.shortlink.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import com.nageoffer.shortlink.admin.common.convention.result.Result;
import com.nageoffer.shortlink.admin.common.convention.result.Results;
import com.nageoffer.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.nageoffer.shortlink.admin.dto.resp.UserActualRespDTO;
import com.nageoffer.shortlink.admin.dto.resp.UserRespDto;
import com.nageoffer.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Nuyoah
 * @Date: idate
 * @Description: 用户管理控制层
 **/
@RestController
@RequiredArgsConstructor // lombok注解用来完成构造器注入 变量要变为final
public class UserController {

    private final UserService userService;

    /**
     * 根据用户名返回用户信息(手机号作了脱敏处理)
     */
    @GetMapping("/api/short-link/v1/user/{username}")
    public Result<UserRespDto> getUserByUsername(@PathVariable("username") String username) {
        return Results.success(userService.getUserByUsername(username));
    }

    /**
     * 根据用户名返回用户信息(手机号未作脱敏处理)
     */
    @GetMapping("/api/short-link/v1/actual/user/{username}")
    public Result<UserActualRespDTO> getActualUserByUsername(@PathVariable("username") String username) {
        return Results.success(BeanUtil.toBean(userService.getUserByUsername(username), UserActualRespDTO.class));
    }

    /**
     * 查询用户名是否存在
     * @param username
     * @return
     */
    @GetMapping("/api/short-link/v1/user/has-username")
    public Result<Boolean> hasUsername(@RequestParam("username") String username){
        return Results.success(userService.hasUsername(username));
    }

    /**
     * 注册用户
     * @param registerReqDTO
     * @return
     */
    @PostMapping("/api/short-link/v1/user")
    public Result<Void> register(@RequestBody UserRegisterReqDTO registerReqDTO){
        userService.register(registerReqDTO);
        return Results.success();
    }
}

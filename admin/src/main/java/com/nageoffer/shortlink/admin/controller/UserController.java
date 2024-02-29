package com.nageoffer.shortlink.admin.controller;

import com.nageoffer.shortlink.admin.common.convention.result.Result;
import com.nageoffer.shortlink.admin.common.convention.result.Results;
import com.nageoffer.shortlink.admin.common.enums.UserErrorCodeEnum;
import com.nageoffer.shortlink.admin.dto.resp.UserRespDto;
import com.nageoffer.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
     * 根据用户名返回用户信息
     */
    @GetMapping("/api/shortlink/v1/user/{username}")
    public Result<UserRespDto> getUserByUsername(@PathVariable("username") String username) {
        return Results.success(userService.getUserByUsername(username));
    }
}

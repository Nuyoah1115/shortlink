package com.nageoffer.shortlink.admin.controller;

import com.nageoffer.shortlink.admin.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Nuyoah
 * @Date: idate
 * @Description: 短链接分组控制层
 **/
@RestController
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;
}

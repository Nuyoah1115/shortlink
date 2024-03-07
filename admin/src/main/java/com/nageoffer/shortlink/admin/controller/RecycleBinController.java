package com.nageoffer.shortlink.admin.controller;

import com.nageoffer.shortlink.admin.common.convention.result.Result;
import com.nageoffer.shortlink.admin.common.convention.result.Results;
import com.nageoffer.shortlink.admin.remote.ShortLinkRemoteService;
import com.nageoffer.shortlink.admin.remote.dto.req.RecycleBinSaveReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 @Author: Nuyoah
 @Date: idate
 @Description: 回收站控制层
 **/
@RestController
@RequiredArgsConstructor
public class RecycleBinController {
    ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService() {
    };
    @PostMapping("/api/short-link/admin/v1/recycle-bin/save")
    public Result<Void> saveRecycleBin(@RequestBody RecycleBinSaveReqDTO saveReqDTO){
        shortLinkRemoteService.saveRecycleBin(saveReqDTO);
        return Results.success();
    }
}

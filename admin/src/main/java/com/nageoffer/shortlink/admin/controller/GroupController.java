package com.nageoffer.shortlink.admin.controller;

import com.nageoffer.shortlink.admin.common.convention.result.Result;
import com.nageoffer.shortlink.admin.common.convention.result.Results;
import com.nageoffer.shortlink.admin.dto.req.GroupReqSaveDTO;
import com.nageoffer.shortlink.admin.dto.req.GroupReqUpdateDTO;
import com.nageoffer.shortlink.admin.dto.resp.GroupRespDTO;
import com.nageoffer.shortlink.admin.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Nuyoah
 * @Date: idate
 * @Description: 短链接分组控制层
 **/
@RestController
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    /**
     * 新增短链接分组
     */
    @PostMapping("/api/short-link/v1/group")
    public Result<Void> save(@RequestBody GroupReqSaveDTO reqDTO) {
        groupService.saveGroup(reqDTO.getName());
        return Results.success();
    }

    /**
     * 查询短链接分组集合
     * @return
     */
    @GetMapping("/api/short-link/v1/group")
    public Result<List<GroupRespDTO>> listGroup(){
        return Results.success(groupService.listGroup());
    }

    /**
     * 修改短链接分组名称
     * @param updateDTO
     * @return
     */
    @PostMapping("/api/short-link/v1/group/update")
    public Result<Void> updateGroup(@RequestBody GroupReqUpdateDTO updateDTO){
        groupService.updateGroup(updateDTO);
        return Results.success();
    }
}

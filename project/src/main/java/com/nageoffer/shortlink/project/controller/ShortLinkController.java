package com.nageoffer.shortlink.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nageoffer.shortlink.project.common.convention.result.Result;
import com.nageoffer.shortlink.project.common.convention.result.Results;
import com.nageoffer.shortlink.project.dto.req.ShortLinkPageReqDTO;
import com.nageoffer.shortlink.project.dto.req.ShortLinkSaveReqDTO;
import com.nageoffer.shortlink.project.dto.resp.ShortLinkPageRespDTO;
import com.nageoffer.shortlink.project.dto.resp.ShortLinkSaveRespDTO;
import com.nageoffer.shortlink.project.service.ShortLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Nuyoah
 * @Date: idate
 * @Description: 短链接控制层
 **/
@RestController
@RequiredArgsConstructor
public class ShortLinkController {
  private final ShortLinkService shortLinkService;

  /**
   * 创建短链接
   */
  @PostMapping("/api/short-link/v1/create")
  public Result<ShortLinkSaveRespDTO> createShortLink(@RequestBody ShortLinkSaveReqDTO saveReqDTO){
    return Results.success(shortLinkService.createShortLink(saveReqDTO));
  }

  /**
   * 分页查询短链接
   */
  @GetMapping("/api/short-link/v1/page")
  public Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO pageReqDTO){
    return Results.success(shortLinkService.pageShortLink(pageReqDTO));
  }

}

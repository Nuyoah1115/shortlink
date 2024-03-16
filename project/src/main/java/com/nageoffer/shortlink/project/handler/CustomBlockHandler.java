package com.nageoffer.shortlink.project.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.nageoffer.shortlink.project.common.convention.result.Result;
import com.nageoffer.shortlink.project.dto.req.ShortLinkSaveReqDTO;
import com.nageoffer.shortlink.project.dto.resp.ShortLinkSaveRespDTO;
public class CustomBlockHandler {

    public static Result<ShortLinkSaveRespDTO> createShortLinkBlockHandlerMethod(ShortLinkSaveReqDTO requestParam, BlockException exception) {
        return new Result<ShortLinkSaveRespDTO>().setCode("B100000").setMessage("当前访问网站人数过多，请稍后再试...");
    }
}

package com.nageoffer.shortlink.admin.remote.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.List;

/**
 @Author: Nuyoah
 @Date: idate
 @Description:
 **/
@Data
public class RecycleBinPageReqDTO extends Page {

    /**
     * 分组标识集合
     */
    private List<String> gids;
}

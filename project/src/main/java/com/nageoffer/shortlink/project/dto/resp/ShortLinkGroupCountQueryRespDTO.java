package com.nageoffer.shortlink.project.dto.resp;

import lombok.Data;

/**
 * @Author: Nuyoah
 * @Date: idate
 * @Description: 短链接分组查询返回实体
 **/
@Data
public class ShortLinkGroupCountQueryRespDTO {
    /**
     * 分组标识
     */
    private String gid;

    /**
     * 短链接数量
     */
    private Integer shortLinkCount;
}

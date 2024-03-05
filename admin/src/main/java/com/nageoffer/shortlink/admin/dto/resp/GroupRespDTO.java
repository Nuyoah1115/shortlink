package com.nageoffer.shortlink.admin.dto.resp;

import lombok.Data;

/**
 * @Author: Nuyoah
 * @Date: idate
 * @Description: 短链接分组返回实体
 **/
@Data
public class GroupRespDTO {
    /**
     * 分组标识
     */
    private String gid;

    /**
     * 分组名称
     */
    private String name;

    /**
     * 分组排序
     */
    private Integer sortOrder;
}

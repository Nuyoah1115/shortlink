package com.nageoffer.shortlink.admin.dto.req;

import lombok.Data;

/**
 * @Author: Nuyoah
 * @Date: idate
 * @Description: 短链接分组创建参数实体
 **/
@Data
public class GroupReqOrderDTO {
  /**
   * 分组标识
   */
  private String gid;

  /**
   * 排序
   */
  private Integer sortOrder;
}

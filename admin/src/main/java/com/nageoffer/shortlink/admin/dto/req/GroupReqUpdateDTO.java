package com.nageoffer.shortlink.admin.dto.req;

import lombok.Data;

/**
 * @Author: Nuyoah
 * @Date: idate
 * @Description: 短链接分组修改参数实体
 **/
@Data
public class GroupReqUpdateDTO {

  /**
   * 分组标识gid
   */
  private String gid;

  /**
   * 分组名
   */
  private String name;
}

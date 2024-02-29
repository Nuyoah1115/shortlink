package com.nageoffer.shortlink.admin.dto.resp;

import lombok.Data;

/**
 * @Author: Nuyoah
 * @Date: idate
 * @Description: 用户返回参数响应
 **/
@Data
public class UserRespDto {
    /**
     * id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String mail;
}

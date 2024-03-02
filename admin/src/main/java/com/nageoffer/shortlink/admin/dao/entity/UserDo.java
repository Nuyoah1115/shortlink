package com.nageoffer.shortlink.admin.dao.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.nageoffer.shortlink.admin.common.database.BaseDO;
import lombok.Data;

import java.util.Date;

/**
 * @Author: Nuyoah
 * @Date: 2024/2/29
 * @Description: 用户持久层是实体
 **/
@Data
@TableName("t_user")
public class UserDo extends BaseDO {

    /**
     * id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

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

    /**
     * 注销时间戳
     */
    private Long deletionTime;

}

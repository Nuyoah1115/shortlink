package com.nageoffer.shortlink.admin.dto.req;

import lombok.Data;

/**
 * @Author: Nuyoah
 * @Date: idate
 * @Description:
 **/
@Data
public class UserLoginReqDTO {
    private String username;
    private String password;
    private String code;
}

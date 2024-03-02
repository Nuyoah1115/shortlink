package com.nageoffer.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nageoffer.shortlink.admin.dao.entity.GroupDO;
import com.nageoffer.shortlink.admin.dto.req.GroupReqUpdateDTO;
import com.nageoffer.shortlink.admin.dto.resp.GroupRespDTO;

import java.util.List;

/**
 * @Author: Nuyoah
 * @Date: idate
 * @Description: 短链接分组接口层
 **/
public interface GroupService extends IService<GroupDO> {
    /**
     * 新增短链接分组
     * @param groupName 短链接分组名称
     */
    void saveGroup(String  groupName);

    /**
     * 查询用户短链接分组集合
     * @return 短链接分组集合
     */
    List<GroupRespDTO> listGroup();

    void updateGroup(GroupReqUpdateDTO updateDTO);
}

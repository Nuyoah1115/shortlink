package com.nageoffer.shortlink.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nageoffer.shortlink.admin.dao.entity.GroupDO;
import com.nageoffer.shortlink.admin.dto.req.GroupReqOrderDTO;
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
     * 新增短链接分组
     * @param username 用户名
     * @param groupName 短链接分组名称
     */
    void saveGroup(String username,String  groupName);

    /**
     * 查询用户短链接分组集合
     * @return 短链接分组集合
     */
    List<GroupRespDTO> listGroup();

    /**
     * 修改短链接分组
     * @param updateDTO 修改短链接分组参数
     */
    void updateGroup(GroupReqUpdateDTO updateDTO);

    /**
     * 删除短链接分组
     * @param gid 分组标识
     */
    void deleteGroup(String gid);

    /**
     * 短链接分组排序
     * @param orderDTOS 排序参数
     */
    void sortGroup(List<GroupReqOrderDTO> orderDTOS);
}

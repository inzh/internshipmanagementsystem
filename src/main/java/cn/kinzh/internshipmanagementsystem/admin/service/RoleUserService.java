package cn.kinzh.internshipmanagementsystem.admin.service;

import cn.kinzh.internshipmanagementsystem.admin.entity.MyRoleUser;

import java.util.List;

/**
 * @author kennyhao
 * @createTime 2020/7/13
 */
public interface RoleUserService {
    /**
     * 返回用户拥有的角色
     * @param userId
     * @return
     */
    List<MyRoleUser> getMyRoleUserByUserId(Integer userId);
}

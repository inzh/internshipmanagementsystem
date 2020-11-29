package cn.kinzh.internshipmanagementsystem.admin.service.impl;

import cn.kinzh.internshipmanagementsystem.admin.dao.RoleUserDao;
import cn.kinzh.internshipmanagementsystem.admin.entity.MyRoleUser;
import cn.kinzh.internshipmanagementsystem.admin.service.RoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kennyhao
 * 根据用户id 返回角色
 */
@Service
public class RoleUserServiceImpl implements RoleUserService {
    @Autowired
    private RoleUserDao roleUserDao;
    @Override
    public List<MyRoleUser> getMyRoleUserByUserId(Integer userId) {
       return roleUserDao.getMyRoleUserByUserId(userId);
    }
}

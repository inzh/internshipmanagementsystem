package cn.kinzh.internshipmanagementsystem.admin.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.kinzh.internshipmanagementsystem.admin.annotation.DataPermission;
import cn.kinzh.internshipmanagementsystem.admin.dao.RoleUserDao;
import cn.kinzh.internshipmanagementsystem.admin.dao.UserDao;
import cn.kinzh.internshipmanagementsystem.admin.dao.UserCompanyDao;
import cn.kinzh.internshipmanagementsystem.admin.entity.MyRoleUser;
import cn.kinzh.internshipmanagementsystem.admin.entity.MyUser;
import cn.kinzh.internshipmanagementsystem.admin.entity.MyUserCompany;
import cn.kinzh.internshipmanagementsystem.admin.service.UserService;
import cn.kinzh.internshipmanagementsystem.common.exceptionhandler.MyException;
import cn.kinzh.internshipmanagementsystem.common.utils.Result;
import cn.kinzh.internshipmanagementsystem.common.constants.ResultCode;
import cn.kinzh.internshipmanagementsystem.common.constants.UserConstants;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kennyhao
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleUserDao roleUserDao;

    @Autowired
    private UserCompanyDao userCompanyDao;

    @Override
    @DataPermission(deptAlias = "d", userAlias = "u")
    public Result<MyUser> getAllUsersByPage(Integer offectPosition, Integer limit, MyUser myUser) {
        Page page = PageHelper.offsetPage(offectPosition,limit);
        List<MyUser> fuzzyUserByPage = userDao.getFuzzyUserByPage(myUser);
        return Result.ok().count(page.getTotal()).data(fuzzyUserByPage).code(ResultCode.TABLE_SUCCESS);
    }

    @Override
    public MyUser getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public void checkUserAllowed(MyUser user) {
        if (!StringUtils.isEmpty(user.getUserId()) && user.isAdmin())
        {
            throw new MyException(ResultCode.ERROR,"不允许操作超级管理员用户");
        }
    }

    @Override
    public String checkPhoneUnique(MyUser myUser) {
        Integer userId = ObjectUtil.isEmpty(myUser.getUserId()) ? -1: myUser.getUserId();
        MyUser info = userDao.checkPhoneUnique(myUser.getPhone());
        if (ObjectUtil.isNotEmpty(info) && !info.getUserId().equals(userId))
        {
            return UserConstants.USER_PHONE_NOT_UNIQUE;
        }
        return UserConstants.USER_PHONE_UNIQUE;
    }

    @Override
    public String checkUserNameUnique(MyUser myUser) {
        Integer userId = ObjectUtil.isEmpty(myUser.getUserId()) ? -1: myUser.getUserId();
        MyUser info = userDao.checkUsernameUnique(myUser.getUserName());
        if (ObjectUtil.isNotEmpty(info) && !info.getUserId().equals(userId))
        {
            return UserConstants.USER_NAME_NOT_UNIQUE;
        }
        return UserConstants.USER_NAME_UNIQUE;
    }

    @Override
    public String checkEmailUnique(MyUser myUser) {
        Integer userId = ObjectUtil.isEmpty(myUser.getUserId()) ? -1: myUser.getUserId();
        MyUser info = userDao.checkEmailUnique(myUser.getUserName());
        if (ObjectUtil.isNotEmpty(info) && !info.getUserId().equals(userId))
        {
            return UserConstants.USER_EMAIL_NOT_UNIQUE;
        }
        return UserConstants.USER_EMAIL_UNIQUE;
    }

    @Override
    public Result<MyUser> updateUser(MyUser myUser, Integer roleId) {
        if (roleId!=null){
            userDao.updateUser(myUser);
            MyRoleUser myRoleUser = new MyRoleUser();
            myRoleUser.setUserId(myUser.getUserId());
            myRoleUser.setRoleId(roleId);
            // 用户角色中已存在记录则更新，否则新增
            if(roleUserDao.getRoleUserByUserId(myUser.getUserId())!=null){
                roleUserDao.updateMyRoleUser(myRoleUser);
            }else {
                roleUserDao.save(myRoleUser);
            }
            // 更新用户，则取消任何与之绑定的公司，重置为空
            userCompanyDao.deleteUserCompanyByUserId(myUser.getUserId());
            insertUserPost(myUser);
            return Result.ok().message("更新成功");
        }else {
            return Result.error().message("更新失败");
        }
    }

    @Override
    public int changeStatus(MyUser user) {
        return userDao.updateUser(user);
    }

    @Override
    public Result<MyUser> save(MyUser myUser, Integer roleId) {
        if(roleId!= null){
            userDao.save(myUser);
            MyRoleUser myRoleUser = new MyRoleUser();
            myRoleUser.setRoleId(roleId);
            myRoleUser.setUserId(myUser.getUserId().intValue());
            roleUserDao.save(myRoleUser);
            insertUserPost(myUser);
            return Result.ok().message("添加成功，初始密码123456");
        }

        return Result.error().message("添加失败");
    }

    @Override
    public int deleteUser(Integer userId) {
        checkUserAllowed(new MyUser(userId));
        roleUserDao.deleteRoleUserByUserId(userId);
        userCompanyDao.deleteUserCompanyByUserId(userId);
        return userDao.deleteUserById(userId);
    }

    @Override
    public MyUser getUserByName(String userName) {
        return userDao.getUser(userName);
    }


    /**
     * 新增用户公司信息
     *
     * @param user 用户对象
     */
    public void insertUserPost(MyUser user)
    {
        Integer[] companies = user.getCompanyIds();

        if (ArrayUtil.isNotEmpty(companies))
        {
            // 新增用户与公司管理
            List<MyUserCompany> list = new ArrayList<MyUserCompany>();
            for (Integer companyId : companies)
            {
                MyUserCompany up = new MyUserCompany();
                up.setUserId(user.getUserId());
                up.setCompanyId(companyId);
                list.add(up);
            }
            if (list.size() > 0)
            {
                userCompanyDao.batchUserCompany(list);
            }
        }
        return;
    }
}

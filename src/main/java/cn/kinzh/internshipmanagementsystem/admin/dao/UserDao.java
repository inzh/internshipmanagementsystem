package cn.kinzh.internshipmanagementsystem.admin.dao;


import cn.kinzh.internshipmanagementsystem.admin.entity.MyUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author kennyhao
 */
@Mapper
public interface UserDao {

    /**
     * 分页返回所有用户
     * @param myUser
     * @return
     */
     List<MyUser> getFuzzyUserByPage(MyUser myUser);

    //计算所有用户数量
    // @Select("select count(*) from My_user")
    // Long countAllUser();

    /**
     *
     * 通过id返回用户
     * @param userId
     * @return
     */
    @Select("select u.user_id,u.dept_id,u.user_name,u.password,u.nick_name,u.phone,u.email,u.avatar_url,u.status,u.create_time,u.update_time from my_user u where u.user_id = #{userId}")
    MyUser getUserById(Integer userId);

    /**
     * 通过手机返回唯一指定用户
     * @param phone
     * @return
     */
    MyUser checkPhoneUnique(String phone);

    /**
     * 通过用户名返回唯一指定用户
     * @param userName
     * @return
     */
    MyUser checkUsernameUnique(String userName);

    /**
     * 通过邮箱返回唯一指定用户
     * @param email
     * @return
     */

    MyUser checkEmailUnique(String email);
    /**
     * 更新用户
     * @param myUser
     * @return
     */
    int updateUser(MyUser myUser);



    /**
     * 插入用户
     * @param myUser
     * @return
     */
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    @Insert("insert into my_user(dept_id,user_name, password, nick_name, phone, email, avatar_url, status, create_time, update_time) values(#{deptId},#{userName}, #{password}, #{nickName}, #{phone}, #{email}, #{avatarUrl}, #{status}, now(), now())")
    int save(MyUser myUser);

    /**
     * 通过id删除用户
     * @param userId
     * @return
     */
    @Delete("delete from my_user where user_id = #{userId}")
    int deleteUserById(Integer userId);



    /**
     *  根据用户名查询用户
     * @param userName
     * @return
     */
    @Select("select * from my_user t where t.user_name = #{userName}")
    MyUser getUser(String userName);


}

package cn.kinzh.internshipmanagementsystem.admin.dao;

import cn.kinzh.internshipmanagementsystem.admin.entity.MyCheckin;

public interface MyCheckinDao {
    int deleteByPrimaryKey(Integer checkinId);

    int insert(MyCheckin record);

    int insertSelective(MyCheckin record);

    MyCheckin selectByPrimaryKey(Integer checkinId);

    int updateByPrimaryKeySelective(MyCheckin record);

    int updateByPrimaryKey(MyCheckin record);
}
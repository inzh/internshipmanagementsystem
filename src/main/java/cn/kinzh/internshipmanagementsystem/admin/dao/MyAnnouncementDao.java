package cn.kinzh.internshipmanagementsystem.admin.dao;

import cn.kinzh.internshipmanagementsystem.admin.entity.MyAnnouncement;

public interface MyAnnouncementDao {
    int deleteByPrimaryKey(Integer announcementId);

    int insert(MyAnnouncement record);

    int insertSelective(MyAnnouncement record);

    MyAnnouncement selectByPrimaryKey(Integer announcementId);

    int updateByPrimaryKeySelective(MyAnnouncement record);

    int updateByPrimaryKey(MyAnnouncement record);
}
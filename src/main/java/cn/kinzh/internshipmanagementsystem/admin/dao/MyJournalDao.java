package cn.kinzh.internshipmanagementsystem.admin.dao;

import cn.kinzh.internshipmanagementsystem.admin.entity.MyJournal;

public interface MyJournalDao {
    int deleteByPrimaryKey(Integer journalId);

    int insert(MyJournal record);

    int insertSelective(MyJournal record);

    MyJournal selectByPrimaryKey(Integer journalId);

    int updateByPrimaryKeySelective(MyJournal record);

    int updateByPrimaryKey(MyJournal record);
}
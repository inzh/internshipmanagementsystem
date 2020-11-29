package cn.kinzh.internshipmanagementsystem.admin.dao;

import cn.kinzh.internshipmanagementsystem.admin.entity.MyGrade;

public interface MyGradeDao {
    int deleteByPrimaryKey(Integer gradeId);

    int insert(MyGrade record);

    int insertSelective(MyGrade record);

    MyGrade selectByPrimaryKey(Integer gradeId);

    int updateByPrimaryKeySelective(MyGrade record);

    int updateByPrimaryKey(MyGrade record);
}
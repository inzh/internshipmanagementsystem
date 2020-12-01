package cn.kinzh.internshipmanagementsystem.admin.service;

import cn.kinzh.internshipmanagementsystem.admin.entity.MyJournal;
import cn.kinzh.internshipmanagementsystem.common.utils.Result;

import java.util.List;


/**
 * @author inzh
 */
public interface JournalService {

    /**
     * 分页模糊查询实习日志
     * @return
     */
    Result<MyJournal> getFuzzyJournalByPage();

    /**
     * 根据标题查询实习日志
     * @param title
     * @return
     */
    MyJournal getJournalByTitle(String title);

    /**
     * 根据id 更新实习日志
     */
    int updateJournalById(Integer id);

    /**
     * 根据id 删除实习日志
     */
    int deleteJournalById(Integer id);

    /**
     * 保存实习日志
     */
    Result<MyJournal> save();

    /**
     * 获取所有日志
     */
    Result<List<MyJournal>> getJournalAll();

}

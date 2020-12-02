package cn.kinzh.internshipmanagementsystem.admin.service.impl;

import cn.kinzh.internshipmanagementsystem.admin.dao.MyJournalDao;
import cn.kinzh.internshipmanagementsystem.admin.entity.MyJournal;
import cn.kinzh.internshipmanagementsystem.admin.entity.MyUser;
import cn.kinzh.internshipmanagementsystem.admin.service.JournalService;
import cn.kinzh.internshipmanagementsystem.admin.service.UserService;
import cn.kinzh.internshipmanagementsystem.common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author inzh
 */
public class JournalServiceImpl implements JournalService {

    @Autowired
    private MyJournalDao myJournalDao;

    @Autowired
    private UserService userService;

    @Override
    public Result<MyJournal> getFuzzyJournalByPage() {
        return null;
    }

    @Override
    public MyJournal getJournalByTitle(String title) {
        return null;
    }

    @Override
    public int updateJournalById(Integer id) {
        return 0;
    }

    @Override
    public int deleteJournalById(Integer id) {
        return 0;
    }

    @Override
    public Result<MyJournal> save(MyJournal myJournal) {
        myJournalDao.insert(myJournal);
        return Result.ok().message("新增实习日志成功！");
    }

    @Override
    public Result<List<MyJournal>> getJournalAll() {
        return null;
    }
}

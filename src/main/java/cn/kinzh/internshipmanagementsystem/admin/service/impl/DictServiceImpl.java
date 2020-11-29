package cn.kinzh.internshipmanagementsystem.admin.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.kinzh.internshipmanagementsystem.admin.dao.DictDao;
import cn.kinzh.internshipmanagementsystem.admin.dao.DictDetailDao;
import cn.kinzh.internshipmanagementsystem.admin.entity.MyDict;
import cn.kinzh.internshipmanagementsystem.admin.service.DictService;
import cn.kinzh.internshipmanagementsystem.common.exceptionhandler.MyException;
import cn.kinzh.internshipmanagementsystem.common.utils.Result;
import cn.kinzh.internshipmanagementsystem.common.constants.ResultCode;
import cn.kinzh.internshipmanagementsystem.common.constants.UserConstants;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DictServiceImpl implements DictService {
    @Autowired
    private DictDao dictDao;

    @Autowired
    private DictDetailDao dictDetailDao;
    @Override
    public Result<MyDict> getDictPage(Integer offectPosition, Integer limit, MyDict myDict) {
        Page page = PageHelper.offsetPage(offectPosition,limit);
        List<MyDict> fuzzyDictByPage = dictDao.getFuzzyDictByPage(myDict);
        return Result.ok().count(page.getTotal()).data(fuzzyDictByPage).code(ResultCode.TABLE_SUCCESS);
    }

    @Override
    public MyDict getDictByName(String dictName) {
        return dictDao.getDictByName(dictName);
    }

    @Override
    public String checkDictNameUnique(MyDict myDict) {
        MyDict info = dictDao.getDictByName(myDict.getDictName());
        if (ObjectUtil.isNotEmpty(info) && !info.getDictId().equals (myDict.getDictId())){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public int insertDict(MyDict myDict) {
        return dictDao.insertDict(myDict);
    }

    @Override
    public MyDict getDictById(Integer dictId) {
        return dictDao.getDictById(dictId);
    }

    @Override
    public int updateDict(MyDict myDict) {
        return dictDao.updateDict(myDict);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int deleteDictByIds(String ids)  throws MyException {
        Integer[] dictIds = Convert.toIntArray(ids);
        for (Integer dictId : dictIds){
            dictDetailDao.deleteDictDetailByDictId(dictId);
        }
        return dictDao.deleteDictByIds(dictIds);
    }
}

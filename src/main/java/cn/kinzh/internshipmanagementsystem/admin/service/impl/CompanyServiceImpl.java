package cn.kinzh.internshipmanagementsystem.admin.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.kinzh.internshipmanagementsystem.admin.dao.CompanyDao;
import cn.kinzh.internshipmanagementsystem.admin.dao.UserCompanyDao;
import cn.kinzh.internshipmanagementsystem.admin.dto.CompanyQueryDto;
import cn.kinzh.internshipmanagementsystem.admin.entity.MyCompany;
import cn.kinzh.internshipmanagementsystem.admin.service.CompanyService;
import cn.kinzh.internshipmanagementsystem.common.exceptionhandler.MyException;
import cn.kinzh.internshipmanagementsystem.common.utils.Result;
import cn.kinzh.internshipmanagementsystem.common.constants.ResultCode;
import cn.kinzh.internshipmanagementsystem.common.constants.UserConstants;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kennyhao
 */
@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private UserCompanyDao userCompanyDao;

    @Override
    public Result<MyCompany> getCompanyAll(Integer offectPosition, Integer limit, CompanyQueryDto companyQueryDto) {
        Page page = PageHelper.offsetPage(offectPosition,limit);
        List<MyCompany> fuzzyCompany = companyDao.getFuzzyCompany(companyQueryDto.getQueryName(), companyQueryDto.getQueryStatus());
        return Result.ok().count(page.getTotal()).data(fuzzyCompany).code(ResultCode.TABLE_SUCCESS);
    }

    @Override
    public int insertCompany(MyCompany company) {
        return companyDao.insertCompany(company);
    }

    /**
     * 校验公司名称是否唯一
     *
     * @param company 公司信息
     * @return 结果
     */
    @Override
    public String checkCompanyNameUnique(MyCompany company) {
        MyCompany info = companyDao.checkCompanyNameUnique(company.getCompanyName());
        if (ObjectUtil.isNotEmpty(info) && !info.getCompanyId().equals (company.getCompanyId())){
            return UserConstants.COMPANY_NAME_NOT_UNIQUE;
        }
        return UserConstants.COMPANY_NAME_UNIQUE;
    }

    @Override
    public MyCompany getCompanyById(Integer companyId) {
        return companyDao.getCompanyById(companyId);
    }

    @Override
    public int deleteCompanyByIds(String ids) throws MyException {
        Integer[] companyIds = Convert.toIntArray(ids);
        for (Integer companyid:companyIds){
            MyCompany company = getCompanyById(companyid);
            if (countUserCompanytById(companyid)>0){
                throw new MyException(ResultCode.ERROR,company.getCompanyName()+ "已分配,不能删除");
            }
        }
        return companyDao.deleteCompanyByIds(companyIds);
    }

    @Override
    public int countUserCompanytById(Integer companyId) {
        return userCompanyDao.countCompanyById(companyId);
    }

    @Override
    public List<MyCompany> selectCompanyAll() {
        return companyDao.selectCompanyAll();
    }

    @Override
    public List<MyCompany> selectCompaniesByUserId(Integer userId) {
        List<MyCompany> userCompanys = companyDao.selectCompaniesByUserId(userId);
        List<MyCompany>companys =companyDao.selectCompanyAll();
        for (MyCompany company : companys)
        {
            for (MyCompany userCompany : userCompanys)
            {
                if (company.getCompanyId().equals(userCompany.getCompanyId()))
                {
                    company.setFlag(true);
                    break;
                }
            }
        }
        return companys;
    }

    @Override
    public int updateCompany(MyCompany myCompany) {
        return companyDao.updateCompany(myCompany);
    }

    @Override
    public int changeStatus(MyCompany myCompany) {
        return companyDao.updateCompany(myCompany);
    }
}

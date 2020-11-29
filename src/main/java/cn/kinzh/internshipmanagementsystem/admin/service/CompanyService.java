package cn.kinzh.internshipmanagementsystem.admin.service;

import cn.kinzh.internshipmanagementsystem.admin.dto.CompanyQueryDto;
import cn.kinzh.internshipmanagementsystem.admin.entity.MyCompany;
import cn.kinzh.internshipmanagementsystem.common.exceptionhandler.MyException;
import cn.kinzh.internshipmanagementsystem.common.utils.Result;

import java.util.List;

/**
 * @author kennyhao
 */
public interface CompanyService {
    /**
     * 返回所有公司
     * @param offectPosition
     * @param limit
     * @param companyQueryDto
     * @return
     */
    Result<MyCompany> getCompanyAll(Integer offectPosition, Integer limit, CompanyQueryDto companyQueryDto);

    /**
     * 新增公司信息
     * @param company 公司信息
     * @return 结果
     */
    int insertCompany(MyCompany company);

    /**
     * 校验公司名称
     *
     * @param company 公司信息
     * @return 结果
     */
    String checkCompanyNameUnique(MyCompany company);

    /**
     * 通过id获得公司信息
     * @param companyId
     * @return
     */
    MyCompany getCompanyById(Integer companyId);

    /**
     * 批量删除公司信息
     * @param ids 需要删除的数据ID
     * @return 结果
     * @throws MyException 异常
     */
    int deleteCompanyByIds(String ids) throws MyException;

    /**
     * 通过公司ID查询公司使用数量
     *
     * @param companyId 公司ID
     * @return 结果
     */
     int countUserCompanytById(Integer companyId);

    /**
     * 查询所有公司
     *
     * @return 公司列表
     */
     List<MyCompany> selectCompanyAll();
    /**
     * 根据用户ID查询公司
     *
     * @param userId 用户ID
     * @return 公司列表
     */
     List<MyCompany> selectCompaniesByUserId(Integer userId);

     /**
      * 修改保存公司信息
      *
      * @param myJob 公司信息
      * @return 结果
      */
    int updateCompany(MyCompany mycompany);

    /**
     * 修改公司状态
     * @param myUser
     * @return
     */
    int changeStatus(MyCompany mycompany);
}

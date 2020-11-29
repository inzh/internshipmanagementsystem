package cn.kinzh.internshipmanagementsystem.admin.dao;

import cn.kinzh.internshipmanagementsystem.admin.entity.MyUserCompany;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author kennyhao
 */
@Mapper
public interface UserCompanyDao {

    /**
     * 通过公司ID查询公司使用数量
     *
     * @param companyId 公式ID
     * @return 结果
     */
    @Select("select count(1) from my_user_company where company_id=#{companyId}")
    int countCompanyById(Integer companyId);

    /**
     * 批量新增用户公司信息
     *
     * @param userCompanyList 用户角色列表
     * @return 结果
     */
    int batchUserCompany(List<MyUserCompany> userCompanyList);

    /**
     * 通过用户ID删除用户和公司关联
     *
     * @param id 用户ID
     * @return 结果
     */
    int deleteUserCompanyByUserId(Integer id);

}

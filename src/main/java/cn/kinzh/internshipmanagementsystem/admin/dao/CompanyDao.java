package cn.kinzh.internshipmanagementsystem.admin.dao;

import cn.kinzh.internshipmanagementsystem.admin.entity.MyCompany;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author kennyhao
 */
@Mapper
public interface CompanyDao {
    /**
     * 模糊查询公司
     * @param queryName 查询的名称
     * @param queryStatus 状态查询
     * @return
     */
    List<MyCompany> getFuzzyCompany(String queryName, Integer queryStatus);

    /**
     * 新增公司信息
     * @param company 公司信息
     * @return 结果
     */
    @Insert("INSERT INTO my_company(company_name,description,status,sort, create_time, update_time) values(#{companyName},#{description},#{status},#{sort}, now(), now())")
    int insertCompany(MyCompany company);


    /**
     * 校验公司名称
     * @param name 公司名称
     * @return 结果
     */
    MyCompany checkCompanyNameUnique(String name);
    /**
     * 通过id查询公司信息
     * @param companyId
     * @return
     */
    @Select("select j.company_id,j.company_name,j.description,j.status,j.sort,j.create_time,j.update_time from my_company j where j.company_id = #{companyId}")
    MyCompany getCompanyById(Integer companyId);

    /**
     * 批量删除公司信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteCompanyByIds(Integer[] ids);

    /**
     * 根据用户ID查询公司
     *
     * @param userId 用户ID
     * @return 公司列表
     */
    List<MyCompany> selectCompaniesByUserId(Integer userId);

    /**
     * 查询所有公司
     *
     * @return 公司列表
     */
    List<MyCompany> selectCompanyAll();

    /**
     * 修改公司信息
     *
     * @param myCompany 公司信息
     * @return 结果
     */
    int updateCompany(MyCompany myCompany);
}

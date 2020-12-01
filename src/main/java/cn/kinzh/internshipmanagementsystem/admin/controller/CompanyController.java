package cn.kinzh.internshipmanagementsystem.admin.controller;

import cn.kinzh.internshipmanagementsystem.admin.dto.CompanyQueryDto;
import cn.kinzh.internshipmanagementsystem.admin.entity.MyCompany;
import cn.kinzh.internshipmanagementsystem.admin.service.CompanyService;
import cn.kinzh.internshipmanagementsystem.common.exceptionhandler.MyException;
import cn.kinzh.internshipmanagementsystem.common.utils.PageTableRequest;
import cn.kinzh.internshipmanagementsystem.common.utils.Result;
import cn.kinzh.internshipmanagementsystem.common.constants.UserConstants;
import cn.kinzh.internshipmanagementsystem.log.aop.MyLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author kennyhao
 */
@Controller
@RequestMapping("/api/company")
@Api(tags = "系统：公司管理")
public class CompanyController {

    @Autowired
    private CompanyService companyService;
    @GetMapping("index")
    @ApiOperation(value = "返回公司页面")
    public String index(){
        return "system/company/company";
    }

    @GetMapping
    @ResponseBody
    @ApiOperation(value = "公司列表")
    @PreAuthorize("hasAnyAuthority('company:list')")
    @MyLog("查询公司")
    public Result getCompanyAll(PageTableRequest pageTableRequest, CompanyQueryDto companyQueryDto){
        pageTableRequest.countOffset();
        return companyService.getCompanyAll(pageTableRequest.getOffset(),pageTableRequest.getLimit(),companyQueryDto);
    }

    @GetMapping("/add")
    @ApiOperation(value = "添加公司页面")
    @PreAuthorize("hasAnyAuthority('company:add')")
    public String addCompany(Model model){
        model.addAttribute("MyCompany",new MyCompany());
        return "/system/company/company-add";
    }

    @PostMapping
    @ResponseBody
    @ApiOperation(value = "添加公司")
    @PreAuthorize("hasAnyAuthority('company:add')")
    @MyLog("添加公司")
    public Result saveCompany(@RequestBody MyCompany myCompany){
        if (UserConstants.COMPANY_NAME_NOT_UNIQUE.equals(companyService.checkCompanyNameUnique(myCompany))) {
            return Result.error().message("新增公司'" + myCompany.getCompanyName() + "'失败，公司名称已存在");
        }
        return Result.judge(companyService.insertCompany(myCompany),"添加公司");
    }

    @GetMapping(value = "/edit")
    @ApiOperation(value = "修改公司页面")
    @PreAuthorize("hasAnyAuthority('company:edit')")
    public String editRole(Model model, MyCompany company) {
        model.addAttribute("MyCompany",companyService.getCompanyById(company.getCompanyId()));
        return "system/company/company-edit";
    }
    @PutMapping
    @ResponseBody
    @ApiOperation(value = "修改公司")
    @PreAuthorize("hasAnyAuthority('company:edit')")
    @MyLog("修改公司")
    public Result updateCompany(@RequestBody MyCompany myCompany){
        if (UserConstants.COMPANY_NAME_NOT_UNIQUE.equals(companyService.checkCompanyNameUnique(myCompany))) {
            return Result.error().message("修改公司'" + myCompany.getCompanyName() + "'失败，公司名称已存在");
        }
        return Result.judge(companyService.updateCompany(myCompany),"修改公司");
    }
    /**
     * 用户状态修改
     */
    @MyLog("修改公司状态")
    @PutMapping("/changeStatus")
    @ResponseBody
    @ApiOperation(value = "修改公司状态")
    @PreAuthorize("hasAnyAuthority('company:edit')")
    public Result changeStatus(@RequestBody MyCompany myCompany)
    {
        int i = companyService.changeStatus(myCompany);
        return Result.judge(i,"修改");
    }

    @DeleteMapping
    @ResponseBody
    @ApiOperation(value = "删除公司")
    @PreAuthorize("hasAnyAuthority('company:del')")
    public Result<MyCompany> deleteRole(String ids) {
        try {
            companyService.deleteCompanyByIds(ids);
            return Result.ok().message("删除成功");
        }catch (MyException e){
            return Result.error().message(e.getMsg()).code(e.getCode());
        }
    }
}

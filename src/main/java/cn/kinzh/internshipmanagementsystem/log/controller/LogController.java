package cn.kinzh.internshipmanagementsystem.log.controller;


import cn.kinzh.internshipmanagementsystem.common.utils.PageTableRequest;
import cn.kinzh.internshipmanagementsystem.common.utils.Result;
import cn.kinzh.internshipmanagementsystem.log.aop.MyLog;
import cn.kinzh.internshipmanagementsystem.log.dto.ErrorLogDto;
import cn.kinzh.internshipmanagementsystem.log.dto.LogDto;
import cn.kinzh.internshipmanagementsystem.log.dto.LogQuery;
import cn.kinzh.internshipmanagementsystem.log.service.MyLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author kennyhao
 */
@Controller
@RequestMapping("/api")
@Api(tags = "系统：日志管理")
public class LogController {
    @Autowired
    private MyLogService logService;

    @GetMapping("/log/index")
    public String logIndex(){
        return "system/log/log";
    }

    @GetMapping("/log")
    @ResponseBody
    @ApiOperation(value = "日志列表")
    @PreAuthorize("hasAnyAuthority('log:list')")
    public Result<LogDto> logList(PageTableRequest pageTableRequest, LogQuery logQuery){
        pageTableRequest.countOffset();
        logQuery.setLogType("INFO");
        return logService.getFuzzyInfoLogByPage(pageTableRequest.getOffset(),pageTableRequest.getLimit(),logQuery);
    }

    @DeleteMapping("/log")
    @MyLog("删除所有INFO日志")
    @ResponseBody
    @ApiOperation("删除所有INFO日志")
    @PreAuthorize("hasAnyAuthority('log:del')")
    public Result<Object> delAllByInfo(){
        logService.delAllByInfo();
        return Result.ok().message("删除成功");
    }

    @GetMapping("/log/error/index")
    public String errorLogIndex(){
        return "system/log/errorLog";
    }

    @GetMapping("/error/log")
    @ResponseBody
    @ApiOperation(value = "错误日志")
    @PreAuthorize("hasAnyAuthority('errorLog:list')")
    public Result<ErrorLogDto> errorLogList(PageTableRequest pageTableRequest, LogQuery logQuery){
        pageTableRequest.countOffset();
        logQuery.setLogType("ERROR");
        return logService.getFuzzyErrorLogByPage(pageTableRequest.getOffset(),pageTableRequest.getLimit(),logQuery);
  }
    @DeleteMapping("/error/log")
    @MyLog("删除所有ERROR日志")
    @ResponseBody
    @ApiOperation("删除所有ERROR日志")
    @PreAuthorize("hasAnyAuthority('errorLog:del')")
    public Result<Object> delAllByError(){
        logService.delAllByError();
        return Result.ok().message("删除成功");
    }

}

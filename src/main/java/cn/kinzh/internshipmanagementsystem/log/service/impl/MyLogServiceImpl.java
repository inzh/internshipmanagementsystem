package cn.kinzh.internshipmanagementsystem.log.service.impl;

import cn.hutool.json.JSONObject;
import cn.kinzh.internshipmanagementsystem.common.utils.Result;
import cn.kinzh.internshipmanagementsystem.common.constants.ResultCode;
import cn.kinzh.internshipmanagementsystem.log.dao.LogDao;
import cn.kinzh.internshipmanagementsystem.log.dto.ErrorLogDto;
import cn.kinzh.internshipmanagementsystem.log.dto.LogDto;
import cn.kinzh.internshipmanagementsystem.log.dto.LogQuery;
import cn.kinzh.internshipmanagementsystem.log.entity.MyLog;
import cn.kinzh.internshipmanagementsystem.log.service.MyLogService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author kennyhao
 */
@Service
public class MyLogServiceImpl implements MyLogService {
    @Autowired
    private LogDao logDao;

    @Override
    public Result<LogDto> getFuzzyInfoLogByPage(Integer offectPosition, Integer limit, LogQuery logQuery) {
        Page page = PageHelper.offsetPage(offectPosition,limit);
        List<LogDto> fuzzyLogByPage = logDao.getFuzzyLogByPage(logQuery);
        return Result.ok().count(page.getTotal()).data(fuzzyLogByPage).code(ResultCode.TABLE_SUCCESS);
    }

    @Override
    public Result<ErrorLogDto> getFuzzyErrorLogByPage(Integer offectPosition, Integer limit, LogQuery logQuery) {
        Page page = PageHelper.offsetPage(offectPosition,limit);
        List<ErrorLogDto> fuzzyErrorLogByPage = logDao.getFuzzyErrorLogByPage(logQuery);
        return Result.ok().count(page.getTotal()).data(fuzzyErrorLogByPage).code(ResultCode.TABLE_SUCCESS);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(String userName, String browser, String ip, ProceedingJoinPoint joinPoint, MyLog log) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        cn.kinzh.internshipmanagementsystem.log.aop.MyLog myLog = method.getAnnotation(cn.kinzh.internshipmanagementsystem.log.aop.MyLog.class);
        // 方法路径
        String methodName = joinPoint.getTarget().getClass().getName()+"."+signature.getName()+"()";
        StringBuilder params = new StringBuilder("{");
        //参数值
        Object[] argValues = joinPoint.getArgs();
        //参数名称
        String[] argNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
        if(argValues != null){
            for (int i = 0; i < argValues.length; i++) {
                params.append(" ").append(argNames[i]).append(": ").append(argValues[i]);
            }
        }
        // 描述
        if (log != null) {
            log.setDescription(myLog.value());
        }
        assert log != null;
        log.setIp(ip);
        String loginPath = "login";
        if(loginPath.equals(signature.getName())){
            try {
                assert argValues != null;
                userName = new JSONObject(argValues[0]).get("userName").toString();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        log.setMethod(methodName);
        log.setUserName(userName);
        log.setParams(params.toString() + " }");
        log.setBrowser(browser);
        logDao.save(log);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delAllByError() {
        logDao.delAllByInfo("ERROR");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delAllByInfo() {
        logDao.delAllByInfo("INFO");
    }
}

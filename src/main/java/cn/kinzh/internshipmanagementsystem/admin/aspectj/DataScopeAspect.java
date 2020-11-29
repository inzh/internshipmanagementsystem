package cn.kinzh.internshipmanagementsystem.admin.aspectj;

import cn.hutool.core.util.StrUtil;
import cn.kinzh.internshipmanagementsystem.admin.annotation.DataPermission;
import cn.kinzh.internshipmanagementsystem.admin.entity.BaseEntity;
import cn.kinzh.internshipmanagementsystem.admin.entity.MyRole;
import cn.kinzh.internshipmanagementsystem.admin.service.RoleUserService;
import cn.kinzh.internshipmanagementsystem.common.utils.SecurityUtils;
import cn.kinzh.internshipmanagementsystem.security.dto.JwtUserDto;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 数据过滤处理
 * @author kennyhao
 */
@Aspect
@Component
public class DataScopeAspect {

    @Autowired
    public RoleUserService roleUserService;
    /**
     * 全部数据权限
     */
    public static final String DATA_SCOPE_ALL = "1";

    /**
     * 自定数据权限
     */
    public static final String DATA_SCOPE_CUSTOM = "2";

    /**
     * 部门数据权限
     */
    public static final String DATA_SCOPE_DEPT = "3";

    /**
     * 部门及以下数据权限
     */
    public static final String DATA_SCOPE_DEPT_AND_CHILD = "4";

    /**
     * 仅本人数据权限
     */
    public static final String DATA_SCOPE_SELF = "5";

    /**
     * 数据权限过滤关键字
     */
    public static final String DATA_SCOPE = "dataScope";

    /**
     * 配置织入点
     */
    @Pointcut("@annotation(cn.kinzh.internshipmanagementsystem.admin.annotation.DataPermission)")
    public void dataScopePointCut()
    {
    }

    @Before("dataScopePointCut()")
    public void doBefore(JoinPoint point) throws Throwable
    {
        handleDataScope(point);
    }

    protected void handleDataScope(final JoinPoint joinPoint)
    {
        // 获得注解
        DataPermission controllerDataScope = getAnnotationLog(joinPoint);
        if (controllerDataScope == null)
        {
            return;
        }
        // 获取当前的用户
        JwtUserDto currentUser = SecurityUtils.getCurrentUser();
        if (currentUser != null)
        {
            // 如果是超级管理员，则不过滤数据
            if (!currentUser.getMyUser().isAdmin())
            {
                dataScopeFilter(joinPoint, currentUser, controllerDataScope.deptAlias(),
                        controllerDataScope.userAlias());
            }
        }
    }

    /**
     * 数据范围过滤
     *
     * @param joinPoint 切点
     * @param user 用户
     * @param deptAlias 部门别名
     * @param userAlias 用户别名
     */
    public static void dataScopeFilter(JoinPoint joinPoint, JwtUserDto user, String deptAlias, String userAlias)
    {
        StringBuilder sqlString = new StringBuilder();

        for (MyRole role : user.getRoleInfo())
        {
            String dataScope = role.getDataScope();
            if (DATA_SCOPE_ALL.equals(dataScope))
            {
                sqlString = new StringBuilder();
                break;
            }
            else if (DATA_SCOPE_CUSTOM.equals(dataScope))
            {
                sqlString.append(StrUtil.format(
                        " OR {}.dept_id IN ( SELECT dept_id FROM my_role_dept WHERE role_id = {} ) ", deptAlias,
                        role.getRoleId()));
            }
            else if (DATA_SCOPE_DEPT.equals(dataScope))
            {
                sqlString.append(StrUtil.format(" OR {}.dept_id = {} ", deptAlias, user.getMyUser().getDeptId()));
            }
            else if (DATA_SCOPE_DEPT_AND_CHILD.equals(dataScope))
            {
                sqlString.append(StrUtil.format(
                        " OR {}.dept_id IN ( SELECT dept_id FROM my_dept WHERE dept_id = {} or find_in_set( {} , ancestors ) )",
                        deptAlias, user.getMyUser().getDeptId(), user.getMyUser().getDeptId()));
            }
            else if (DATA_SCOPE_SELF.equals(dataScope))
            {
                if (StrUtil.isNotBlank(userAlias))
                {
                    sqlString.append(StrUtil.format(" OR {}.user_id = {} ", userAlias, user.getMyUser().getUserId()));
                }
                else
                {
                    // 数据权限为仅本人且没有userAlias别名不查询任何数据
                    sqlString.append(" OR 1=0 ");
                }

            }
        }
        if (StrUtil.isNotBlank(sqlString.toString()))
        {
            BaseEntity baseEntity;
            for (int i = 0;i < joinPoint.getArgs().length ;i++ ){
                if (joinPoint.getArgs()[i] instanceof BaseEntity){
                    baseEntity= (BaseEntity) joinPoint.getArgs()[i];
                    baseEntity.getParams().put(DATA_SCOPE, " AND (" + sqlString.substring(4) + ")");
                }
            }

        }
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private DataPermission getAnnotationLog(JoinPoint joinPoint)
    {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null)
        {
            return method.getAnnotation(DataPermission.class);
        }
        return null;
    }
}

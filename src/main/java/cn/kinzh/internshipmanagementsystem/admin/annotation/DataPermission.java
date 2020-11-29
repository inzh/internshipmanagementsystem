package cn.kinzh.internshipmanagementsystem.admin.annotation;

import java.lang.annotation.*;

/**
 * 数据权限过滤注解
 * @author kennyhao
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataPermission {
    /**
     * 部门表的别名
     */
    public String deptAlias() default "";

    /**
     * 用户表的别名
     */
    public String userAlias() default "";
}

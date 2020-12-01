package cn.kinzh.internshipmanagementsystem.admin.entity;

import lombok.Data;

/**
 * @author kennyhao
 */
@Data
public class MyCompany extends BaseEntity {
    private static final long serialVersionUID = 8925514045582234222L;

    private Integer companyId;

    private String companyName;

    private Integer status;

    private Integer sort;

    /** 用户是否存在此公司标识 默认不存在 */
    private boolean flag = false;
}

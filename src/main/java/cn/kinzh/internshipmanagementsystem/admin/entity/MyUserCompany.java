package cn.kinzh.internshipmanagementsystem.admin.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author kennyhao
 */
@Data
public class MyUserCompany extends BaseEntity {

    private static final long serialVersionUID = 8925514045582235321L;

    private Integer userId;

    private Integer companyId;

}

package cn.kinzh.internshipmanagementsystem.admin.entity;

import lombok.Data;

/**
 * @author kennyhao
 */
@Data
public class MyDept extends BaseEntity {
    private static final long serialVersionUID = 8925514045582235633L;

    private Integer deptId;

    private Integer parentId;

    private String ancestors;

    private String deptName;

    private Integer sort;

    private Integer status;

}

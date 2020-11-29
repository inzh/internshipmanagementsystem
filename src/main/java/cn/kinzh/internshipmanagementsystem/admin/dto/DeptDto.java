package cn.kinzh.internshipmanagementsystem.admin.dto;

import cn.kinzh.internshipmanagementsystem.admin.entity.BaseEntity;
import lombok.Data;

/**
 * @author kennyhao
 */
@Data
public class DeptDto extends BaseEntity {

    private Integer id;

    private Integer parentId;

    private String checkArr = "0";

    private String title;
}

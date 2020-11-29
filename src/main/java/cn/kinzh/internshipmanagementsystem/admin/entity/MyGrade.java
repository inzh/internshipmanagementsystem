package cn.kinzh.internshipmanagementsystem.admin.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * my_grade
 * @author kennyhao
 */
@Data
public class MyGrade extends BaseEntity {
    private Integer gradeId;

    private Integer userId;

    private Integer grade;

    private static final long serialVersionUID = 1L;
}
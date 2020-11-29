package cn.kinzh.internshipmanagementsystem.admin.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author kennyhao
 */
@Data
public class CompanyQueryDto implements Serializable {
    private String queryName;

    private Integer queryStatus;
}

package cn.kinzh.internshipmanagementsystem.admin.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author kennyhao
 */
@Data
public class MenuDto implements Serializable {

    private Integer id;

    private Integer parentId;

    private String checkArr = "0";

    private String title;
}

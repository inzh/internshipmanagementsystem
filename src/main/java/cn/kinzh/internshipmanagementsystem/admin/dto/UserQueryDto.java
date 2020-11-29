package cn.kinzh.internshipmanagementsystem.admin.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author kennyhao
 */
@Data
public class UserQueryDto implements Serializable {

    private String nickName;

    private String userName;

    private Integer deptId;
}

package cn.kinzh.internshipmanagementsystem.admin.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author kennyhao
 */
@Data
public class MyRoleUser implements Serializable {

    private static final long serialVersionUID = 8545514045582235838L;

    private Integer userId;

    private Integer roleId;
}

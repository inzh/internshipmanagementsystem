package cn.kinzh.internshipmanagementsystem.admin.entity;

import lombok.Data;

/**
 * @author kennyhao
 */
@Data
public class MyMenu extends BaseEntity{

    private static final long serialVersionUID = -6525908145032868815L;

    private Integer menuId;

    private Integer parentId;

    private String menuName;

    private String icon;

    private Integer type;

    private String url;

    private String permission;

    private Integer sort;
}

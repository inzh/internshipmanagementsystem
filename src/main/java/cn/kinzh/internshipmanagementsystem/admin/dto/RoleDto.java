package cn.kinzh.internshipmanagementsystem.admin.dto;

import cn.kinzh.internshipmanagementsystem.admin.entity.MyRole;

import java.util.List;

/**
 * @author kennyhao
 */
public class RoleDto extends MyRole {
    private static final long serialVersionUID = -5784234789156935003L;

    private List<Integer> menuIds;

    private  List<Integer> deptIds;

    public List<Integer> getDeptIds() {
        return deptIds;
    }

    public void setDeptIds(List<Integer> deptIds) {
        this.deptIds = deptIds;
    }

    public List<Integer> getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(List<Integer> menuIds) {
        this.menuIds = menuIds;
    }

}

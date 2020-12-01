package cn.kinzh.internshipmanagementsystem.admin.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author kennyhao
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class MyUser extends BaseEntity{
    private static final long serialVersionUID = -6525908145032868837L;

    private Integer userId;

    private Integer deptId;

    private String userName;

    private String password;

    private String nickName;

    private String phone;

    private String email;

    private String avatarUrl;

    private Integer status;

    public interface Status {
        int LOCKED = 0;
        int VALID = 1;
    }

    private Integer roleId;
    /** 公司组 */
    private Integer[] companyIds;

    /**
     * 判断是否为admin用户
     */
    public boolean isAdmin()
    {
        return isAdmin(this.getUserId());
    }

    public static boolean isAdmin(Integer userId)
    {
        return userId != null && 1L == userId;
    }

    public MyUser(Integer userId)
    {
        this.setUserId(userId);
    }
}

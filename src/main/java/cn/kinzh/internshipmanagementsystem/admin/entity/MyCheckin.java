package cn.kinzh.internshipmanagementsystem.admin.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * my_checkin
 * @author kennyhao
 */
@Data
public class MyCheckin implements Serializable {
    private Integer checkinId;

    private Integer userId;

    private Integer count;

    private Date checkinTime;

    private String checkinlocation;

    private static final long serialVersionUID = 1L;
}
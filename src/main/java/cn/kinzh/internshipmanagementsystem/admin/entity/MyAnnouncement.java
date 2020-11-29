package cn.kinzh.internshipmanagementsystem.admin.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * my_announcement
 * @author kennyhao
 */
@Data
public class MyAnnouncement extends BaseEntity {
    private Integer announcementId;

    private String title;

    private String content;

    private String createBy;

    private String updateBy;

    private static final long serialVersionUID = 1L;
}
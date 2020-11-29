package cn.kinzh.internshipmanagementsystem.admin.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * my_journal
 * @author kennyhao
 */
@Data
public class MyJournal extends BaseEntity {
    /**
     * 日志id
     */
    private Integer journalId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 日志标题
     */
    private String title;

    /**
     * 日志内容
     */
    private String content;

    private static final long serialVersionUID = 1L;
}
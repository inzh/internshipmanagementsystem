package cn.kinzh.internshipmanagementsystem.admin.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author inzh
 */

@Controller
@RequestMapping("/api/journal")
@Api(tags = "实习日志")
public class JournalController {

    /**
     * 展示所有实习日志页面
     * @return
     */
    @GetMapping("index")
    public String index() {

        return "system/journal/journal";
    }

    /**
     * 添加日志页面
     * @return
     */
    @GetMapping("add")
    public String addJournal() {


        return "system/journal/journal-add";
    }

    /**
     * 修改实习日志页面
     */
    @GetMapping("edit")
    public String editJournal() {


        return "system/journal/journal-edit";
    }

}

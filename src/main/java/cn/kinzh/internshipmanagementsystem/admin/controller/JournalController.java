package cn.kinzh.internshipmanagementsystem.admin.controller;

import cn.kinzh.internshipmanagementsystem.admin.entity.MyJournal;
import cn.kinzh.internshipmanagementsystem.admin.entity.MyUser;
import cn.kinzh.internshipmanagementsystem.admin.service.JournalService;
import cn.kinzh.internshipmanagementsystem.admin.service.UserService;
import cn.kinzh.internshipmanagementsystem.common.utils.Result;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author inzh
 */

@Controller
@RequestMapping("/api/journal")
@Api(tags = "实习日志")
public class JournalController {

    @Autowired
    private JournalService journalService;

    @Autowired
    private UserService userService;

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

    @PostMapping
    @ResponseBody
    @ApiOperation(value = "实习日志：添加")
    @PreAuthorize("hasAnyAuthority('journal:add')")
    public Result<MyJournal> saveJournal(@RequestBody JSONObject obj) {

        /**
         * obj 传回日志内容和用户名，通过用户名来将日志和用户相对应
         */
        if(!obj.getString("username").isEmpty()) {
            MyJournal journal = new MyJournal();
            MyUser user = new MyUser();
            user = userService.getUserByName(obj.getString("username"));
            journal.setUserId(user.getUserId());
            journal.setTitle(obj.getString("title"));
            journal.setContent(obj.getString("content"));
            journalService.save(journal);
            return Result.ok().message("日志添加成功!");
        }else {
            return Result.error().message("日志添加失败!");
        }

    }

    /**
     * 修改实习日志页面
     */
    @GetMapping("edit")
    public String editJournal() {


        return "system/journal/journal-edit";
    }

}

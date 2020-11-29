package cn.kinzh.internshipmanagementsystem.admin.controller;

import cn.kinzh.internshipmanagementsystem.common.utils.Md5Util;
import cn.kinzh.internshipmanagementsystem.common.utils.RedisUtil;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import static cn.kinzh.internshipmanagementsystem.common.utils.EmailUtil.VerifyCode;

/**
 * @author inzh
 * 注册控制器
 */

@Controller
@RequestMapping("/register")
@Api(tags = "系统：注册路由")
public class RegisterController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private RedisUtil redisUtil;



    @RequestMapping("/sendemail")
    @ResponseBody
    public String sendEmail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        String code = VerifyCode(6);
        message.setFrom("1457890059@qq.com");
        message.setTo(email);
        message.setSubject("学生实习管理系统注册");
        message.setText("您的验证码为："+ code +"，有效期时间为5分钟(若不是本人操作，可忽略该条邮件)");

        try {
            javaMailSender.send(message);
            logger.info("邮件发送成功");
            String codeHash = Md5Util.crypt(code);
            redisUtil.set("codeHash", codeHash, 300);
            return "success";
        }catch (MailSendException e) {
            logger.error("目标邮箱不存在");
            return "false";
        }catch (Exception e) {
            logger.error("邮件发送异常!",e);
            return "failure";
        }

    }

}

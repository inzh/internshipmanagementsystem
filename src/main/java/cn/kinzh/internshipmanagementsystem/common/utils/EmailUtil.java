package cn.kinzh.internshipmanagementsystem.common.utils;

import javax.annotation.Resource;
import java.util.Random;

/**
 * @author inzh
 */
public class EmailUtil {

    /**
     * 生成 n 位邮箱验证码
     * @param n
     * @return
     */
    public static String VerifyCode(int n) {
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i=0; i<n; i++) {
            int ran1 = r.nextInt(10);
            sb.append(String.valueOf(ran1));
        }
        return sb.toString();
    }


}

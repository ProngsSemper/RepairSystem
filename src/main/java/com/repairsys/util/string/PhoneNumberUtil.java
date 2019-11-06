package com.repairsys.util.string;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Prongs
 * @date 2019/11/6 20:40
 * 验证手机号码是否正确的工具类
 */
public class PhoneNumberUtil {
    public static boolean isMobile(String str) {
        Pattern p;
        Matcher m;
        boolean b = false;
        // 验证手机号
        String s2="^[1](([3|5|8][\\d])|([4][5,6,7,8,9])|([6][5,6])|([7][3,4,5,6,7,8])|([9][8,9]))[\\d]{8}$";
        if(StringUtils.isNotBlank(str)){
            p = Pattern.compile(s2);
            m = p.matcher(str);
            b = m.matches();
        }
        return b;
    }
}

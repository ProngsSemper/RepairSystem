package com.repairsys.util.string;

import com.repairsys.util.md5.Md5Util;

/**
 * @Author lyr
 * @create 2019/9/24 19:13
 * <p>
 * 主要给用户登录注册用是字符串处理
 */
public final class StringUtils {

    /**
     * 该方法主要是给用户登录注册验证用的，如果用户的 uid 和 password过短，那肯定不符合实际，返回false
     *
     * @param id
     * @param password
     * @return
     */
    public static boolean login(String id, String password) {
        if (id == null || id.length() < 1) {
            return false;
        }
        /*
         *
         * 注意：现实中并没用长度为1的账号和长度为1的密码，就是不是空串，长度为1也不能过
         * */
        return password != null && password.length() >= 1;

    }

    public static boolean login(String id) {
        return id != null && id.length() > 1;
    }

    /**
     * 对原始字符串进行加密，返回新的md5加密后的字符串
     *
     * @param password 传入原始字符串
     * @return 返回加密过后的字符串
     */
    public static String getStringMd5(String password) {

        return Md5Util.getMd5(password);
    }

    public static boolean getByFormId(String formId) {
        return formId != null && formId.length() >= 1;
    }

    public static boolean getByStudentId(String stuId) {
        return stuId != null && stuId.length() >= 1;
    }

}

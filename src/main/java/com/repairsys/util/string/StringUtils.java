package com.repairsys.util.string;

import com.repairsys.util.md5.Md5Util;

/**
 * @Author lyr
 * @create 2019/9/24 19:13
 *
 * 主要给用户登录注册用是字符串处理
 */
public class StringUtils {

    /**
     *
     * 该方法主要是给用户登录注册验证用的，如果用户的 uid 和 password过短，那肯定不符合实际，返回false
     * @param uid
     * @param password
     * @return
     */
    public static final boolean login(String uid,String password)
    {
        if(uid==null||uid.length()<1)
        {
            return false;
        }
        /*
        *
        * 注意：现实中并没用长度为1的账号和长度为1的密码，就是不是空串，长度为1也不能过
        * */
        if(password==null||password.length()<1)
        {
            return false;
        }
        return true;

    }

    public static final boolean login(String uid)
    {
        if(uid==null||uid.length()<=1)
        {
            return false;
        }

        return true;
    }

    /**
     * 对原始字符串进行加密，返回新的md5加密后的字符串
     * @param password 传入原始字符串
     * @return 返回加密过后的字符串
     */
    public static final String getStringMd5(String password)
    {

        return Md5Util.getMd5(password);
    }


}

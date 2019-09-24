package com.repairsys.util.string;

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
        if(uid==null||uid.length()<=1)
        {
            return false;
        }
        /*
        *
        * 注意：现实中并没用长度为1的账号和长度为1的密码，就是不是空串，长度为1也不能过
        * */
        if(password==null||password.length()<=1)
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


}

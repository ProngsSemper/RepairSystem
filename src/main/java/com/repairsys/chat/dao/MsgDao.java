package com.repairsys.chat.dao;

import com.repairsys.chat.domain.Message;
import com.repairsys.dao.BaseDao;
import com.repairsys.util.db.JdbcUtil;

/**
 * @Author lyr
 * @create 2019/11/9 17:10
 */
public class MsgDao extends BaseDao<Message> {

    private static final MsgDao DAO = new MsgDao();
    public static MsgDao getInstance()
    {
        return DAO;
    }

    protected MsgDao() {
        super(Message.class);
    }
    private static final String SAVE_ADMIN_MSG = "insert into adminmsg (sender,receiver,msg,flag)VALUES(?,?,?,?);";
    private static final String SAVE_MSG = "insert into studentmsg (sender,receiver,msg,flag)VALUES(?,?,?,?);";
    public void saveAdminMsg(String sender,String receiver,String msg,int flag)
    {
        super.addOne(JdbcUtil.getConnection(),SAVE_ADMIN_MSG,sender,receiver,msg,flag);
    }

    public void saveStudentMsg(String sender,String receiver,String msg,int flag)
    {
        super.addOne(JdbcUtil.getConnection(),SAVE_MSG,sender,receiver,msg,flag);
    }

    public Message getMsg(String receiver)
    {
       return null;
    }
}

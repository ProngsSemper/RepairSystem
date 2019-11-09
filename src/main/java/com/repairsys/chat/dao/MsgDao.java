package com.repairsys.chat.dao;

import com.repairsys.chat.domain.Message;
import com.repairsys.dao.BaseDao;
import com.repairsys.util.db.JdbcUtil;

import java.util.List;

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

    /**
     * 输入学生（receiver） 的学号
     */
    private static final String GET_ADMIN_MSG = "select * from adminmsg where receiver= ? or receiver=\"所有人\"";

    /**
     * 输入管理员（receiver） 的学号
     */
    private static final String GET_STUDENT_MSG = "select * from studentmsg where receiver= ? or receiver=\"离线留言\"";


    /**
     * 学生获取管理员聊天回复
     * @param studentId 学生的账号
     * @return 返回聊天消息数组
     */
    public List<Message> getAdminMsg(String studentId)
    {
       return super.selectList(JdbcUtil.getConnection(),GET_ADMIN_MSG,studentId);
    }


    /**
     * 管理员获取学生留言
     * @param adminId 管理员账号
     * @return 返回聊天消息记录
     */
    public List<Message> getStudentMsg(String adminId)
    {
        return super.selectList(JdbcUtil.getConnection(),GET_STUDENT_MSG,adminId);
    }


}

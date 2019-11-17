package com.repairsys.chat.dao;

import com.repairsys.chat.domain.Message;
import com.repairsys.dao.BaseDao;
import com.repairsys.util.db.JdbcUtil;
import com.repairsys.util.easy.EasyTool;


import java.time.LocalDate;
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
    private static final String GET_ADMIN_MSG = "select * from adminmsg where receiver= ? or receiver=\"所有人\" and flag=0  LIMIT ?,?";

    /**
     * 输入管理员（receiver） 的学号
     */
    private static final String GET_STUDENT_MSG = "select * from studentmsg where receiver= ? or receiver=\"离线留言\" and flag=0  LIMIT ?,?";


    /**
     * 学生获取管理员聊天回复
     * @param studentId 学生的账号
     * @return 返回聊天消息数组
     */
    public List<Message> getAdminMsg(String studentId,int page,int size)
    {
        if(page<=0)
        {
            page=1;
        }
        int[] res = EasyTool.getLimitNumber(page,size);
       return super.selectList(JdbcUtil.getConnection(),GET_ADMIN_MSG,studentId,res[0],res[1]);
    }


    /**
     * 管理员获取学生留言
     * @param adminId 管理员账号
     * @return 返回聊天消息记录
     */
    public List<Message> getStudentMsg(String adminId,int page,int size)
    {
        if(page<=0)
        {
            page=1;
        }
        int[] res = EasyTool.getLimitNumber(page,size);

        return super.selectList(JdbcUtil.getConnection(),GET_STUDENT_MSG,adminId,res[0],res[1]);
    }

    private static final String COUNT_ADMIN_MSG="select count(*) from adminmsg where receiver= ? or receiver=\"所有人\" ";
    private static final String COUNT_STU_MSG="select count(*) from studentmsg where receiver= ? or receiver=\"离线留言\" ";
    public int countAdminMessage(String id)
    {
        return super.getCount(JdbcUtil.getConnection(),COUNT_ADMIN_MSG,id);
    }
    public int countStuMessage(String id)
    {
        return super.getCount(JdbcUtil.getConnection(),COUNT_STU_MSG,id);

    }



    private static final String GET_MESSAGE_OF_ADMIN = "select * from adminmsg where sender = ? " +
            "union select * from studentmsg where receiver=? " +
            "or receiver= \"离线留言\" order by time DESC limit ?,?";


    /**
     * 获取学生的聊天记录
     */
    private static final String GET_MESSAGE_OF_STUDENT="select * from adminmsg where receiver= ? or receiver=\"所有人\" UNION SELECT * from studentmsg where sender= ? ORDER BY time DESC LIMIT ?,?";

    //获取管理员的聊天记录
    /**
     * 获取管理员的聊天记录总数
     *  ? -> admin
     */
    private static final String COUNT_OF_ADMIN_MSG = "select t1.cnt+t2.cnt FROM " +
            " (select count(*) cnt from adminmsg where sender = ? ) t1, " +
            "(select count(*) cnt  from studentmsg where receiver=? or receiver= \"离线留言\") t2";

    private static final String COUNT_OF_STUDENT_MSG="select t1.cnt+t2.cnt FROM " +
            " (select count(*) cnt from adminmsg  where receiver= ? or receiver= \"所有人\" ) t1," +
            " (select count(*) cnt  from studentmsg where sender= ? ) t2\n";



    /**
     * @return 获取双方的聊天记录
     */
    public List<Message> getMessageOfBoth(String userName,boolean isAdmin,int page,int size)
    {
        int[] pageRes = EasyTool.getLimitNumber(page,size);
        if(isAdmin)
        {
            return super.selectList(JdbcUtil.getConnection(),GET_MESSAGE_OF_ADMIN,userName,userName,pageRes[0],pageRes[1]);
        }else{
            return super.selectList(JdbcUtil.getConnection(),GET_MESSAGE_OF_STUDENT,userName,userName,pageRes[0],pageRes[1]);
        }

    }

    private static final String UPDATE_ADMIN_ID_FLAG="update adminmsg set flag = ? where id = ?";
    private static final String UPDATE_STU_ID_FLAG="update studentmsg set flag = ? where id = ?";
    public void updateMessage(int id,int flag,String table)
    {
        //admin表
        if(table.startsWith("ad"))
        {
            System.out.println(123);
            super.updateOne(JdbcUtil.getConnection()
            ,UPDATE_ADMIN_ID_FLAG, flag,id);
        }else{
            super.updateOne(JdbcUtil.getConnection(),
                    UPDATE_STU_ID_FLAG,
                    flag,
                    id
                    );
        }
    }

    private static final String COUNT_ADMIN_INFO_UNREAD= "select count(*) from adminmsg " +
            "where ( receiver ='所有人' or receiver =? ) and flag = ?";
    private static final String COUNT_STU_INFO_UNREAD= "select count(*) from studentmsg " +
            "where (receiver ='离线留言' or receiver =? ) and flag = ?";
    public int getCountOfUnread(String user,boolean isAdmin)
    {
        int count=0;
        if(isAdmin)
        {
            count = super.getCount(JdbcUtil.getConnection(),COUNT_STU_INFO_UNREAD,user,0);
            System.out.println(COUNT_ADMIN_INFO_UNREAD);

        }else{
            //如果是学生，去查管理员的表
            count = super.getCount(JdbcUtil.getConnection(),COUNT_ADMIN_INFO_UNREAD,user,0);
            System.out.println(COUNT_ADMIN_INFO_UNREAD);
        }
        return count;


    }

    /**
     * 获取所有的聊天记录
     * @param name
     * @param isAdmin
     * @return
     */
    public int getTotalCount(String name,boolean isAdmin)
    {
        int cnt = 0;
        if(isAdmin)
        {
            cnt = super.getCount(JdbcUtil.getConnection(),COUNT_OF_ADMIN_MSG,name,name);
        }else{
            cnt = super.getCount(JdbcUtil.getConnection(),COUNT_OF_STUDENT_MSG,name,name);
        }

        return cnt;
    }



    private static final String REMOVE_ADMIN_MSG = "delete FROM adminmsg where flag <>0 and  time <= date_sub(CURDATE(),interval 30 day)";
    private static final String REMOVE_STU_MSG = "delete FROM studentmsg where flag <>0 and  time <= date_sub(CURDATE(),interval 30 day)";
    public void removeMsg()
    {

        super.deleteOne(JdbcUtil.getConnection(),REMOVE_ADMIN_MSG);
        super.deleteOne(JdbcUtil.getConnection(),REMOVE_STU_MSG);
    }










}

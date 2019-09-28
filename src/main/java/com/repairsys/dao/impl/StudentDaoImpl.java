package com.repairsys.dao.impl;

import com.repairsys.bean.entity.Student;
import com.repairsys.dao.BaseDao;
import com.repairsys.dao.StudentDao;
import com.repairsys.util.db.JdbcUtil;

import java.sql.Connection;

/**
 * @Author lyr
 * @create 2019/9/27 11:25
 */
public class StudentDaoImpl extends BaseDao<Student> implements StudentDao {
    private static final StudentDao STUDENT_DAO = new StudentDaoImpl();

    private static final String STUDENT_REGISTER = "insert into students (stuId,stuName,stuTel,stuPassword,stuMail)values(?,?,?,?,?)";
    private static final String STUDENT_LOGIN = "select * from students where stuId = ? and stuPassword = ?";
    public static StudentDao getInstance()
    {
        return STUDENT_DAO;
    }

    private StudentDaoImpl() {
        super(Student.class);
    }

    /**
     * 根据学生学号进行查询
     *
     * @param stuId 学生学号
     * @return 返回一个bean对象 (Student 类)
     */
    @Override
    public Student queryById(String stuId) {
        return super.selectOne(JdbcUtil.getConnection(),"",stuId);
    }

    /**
     * 学生登录
     *
     * @param stuId    学生学号
     * @param password 学生账号
     * @return 返回一个bean对象 (Student 类)
     */
    @Override
    public Student login(String stuId, String password) {
        Connection conn = JdbcUtil.getConnection();
        Student s =  super.selectOne(conn,STUDENT_LOGIN,stuId,password);

        return s;
    }

    /**
     * 学生是否存在
     *
     * @param stuId 学生的账号
     *
     * @deprecated 暂时没什么用
     * @return 返回一个布尔值，true表示存在，否则表示不存在
     */
    @Override
    public boolean exists(String stuId) {
        String sql = "select count(*) from students where stuId = ?";
        return super.getCount(JdbcUtil.getConnection(),sql,stuId)>0;

    }

    /**
     * 学生注册
     *
     * @param person 传入一个 bean对象，将属性写入数据库
     * @return 返回一个 布尔值，注册是否成功
     */
    @Override
    public boolean register(Student person) {
        return false;
    }

    /**
     * 工人注册时，写入数据库的一条记录
     *
     * @param stuId       工人的登录账号
     * @param stuName     工人的名字
     * @param stuTel      工人的电话号码
     * @param stuPassword 工人的账号密码
     * @param stuMail     工人的电子邮箱号码
     * @return 注册成功返回true，若出现异常注册失败返回false
     */
    @Override
    public boolean register(String stuId, String stuName, String stuTel, String stuPassword, String stuMail) {
        return !super.addOne(JdbcUtil.getConnection(), STUDENT_REGISTER, stuId, stuName, stuTel, stuPassword, stuMail);
    }






}

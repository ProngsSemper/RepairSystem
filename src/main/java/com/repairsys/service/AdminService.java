package com.repairsys.service;

import com.repairsys.bean.entity.Form;
import com.repairsys.bean.entity.Worker;
import com.repairsys.bean.vo.Result;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;

/**
 * @Author lyr, Prongs
 * @create 2019/9/24 18:07
 */
public interface AdminService {

    /**
     * 管理员登录
     *
     * @param adminId  用户账号
     * @param password 用户密码
     * @param session  用户传入的 session
     * @return 返回对应状态码
     */
    Result<Boolean> login(String adminId, String password, HttpSession session);

    /**
     * 管理员根据报修单号查询报修单
     *
     * @param formId 报修单号
     * @return 返回对应状态码
     */
    Result getIncompleteFormByFormId(String formId);

    /**
     * 根据南北苑查询管理员未处理报修单
     *
     * @param location 南/北苑
     * @param page     当前页
     * @param limit    页面大小
     * @return 返回相应状态码
     */
    Result getIncompleteFormByLocation(String location, int page, int limit);

    /**
     * 根据南北苑查询管理员已处理报修单
     *
     * @param location 南/北苑
     * @param page     当前页
     * @param limit    页面大小
     * @return 返回相应状态码
     */
    Result getCompleteFormByLocation(String location, int page, int limit);

    /**
     * 管理员根据学生学号查询报修单
     *
     * @param stuId 学生学号
     * @return 返回对应状态码
     */
    Result getByStudentId(String stuId);

    /**
     * 管理员发送维修通知
     *
     * @param stuMail 被通知的学生邮箱号
     * @param day     师傅上门的时间是本月的几号
     * @param hour    师傅上门时间是几点
     * @param wTel    师傅联系电话
     * @return 返回对应状态码
     * @throws Exception 抛出异常
     */
    Result<Boolean> senMail(String stuMail, int day, int hour, String wTel) throws Exception;

    /**
     * 模糊查询出工人
     *
     * @param name 工人的名字
     * @return 通过工人的名字模糊查询出结果集，回馈给管理员页面
     */
    Result<List<Worker>> findWorkers(String name);

    /**
     * 根据学生id进行模糊查询 实现分页功能
     *
     * @param page      查询的页面
     * @param limit     一页的记录
     * @param studentId 学生的id号
     * @return 返回分页查询的表单集合
     */
    Result<List<Form>> getFormByStudentId(int page, int limit, String studentId);

    /**
     * 根据学生id进行模糊查询未处理报修单 实现分页功能
     *
     * @param page        当前页面
     * @param limit       设置限制条数
     * @param studentName 学生姓名
     * @return 返回学生提交的所有申请状态
     */
    Result<List<Form>> getAllIncompleteFormByStudentName(String studentName, int page, int limit);

    /**
     * 根据学生id进行模糊查询已处理报修单 实现分页功能
     *
     * @param page    当前页面
     * @param limit   设置限制条数
     * @param stuName 学生姓名
     * @return 返回学生提交的所有申请状态
     */
    Result getAllCompleteFormByStudentName(String stuName, int page, int limit);

    /**
     * 根据工人名字模糊查询表单
     *
     * @param wName 工人名字
     * @param page  当前页
     * @param limit 每页显示多少数据
     * @return 返回相应状态码
     */
    Result getFormListByWorkerName(String wName, int page, int limit);

    /**
     * 管理员发布公告
     *
     * @param board       公告内容
     * @param releaseDate 发布时间
     * @return 返回相应状态码
     */
    Result releaseBoard(String board, Timestamp releaseDate);

    /**
     * 查询所有历史公告
     *
     * @return 返回相应状态码
     */
    Result getHistoryBoard();

    /**
     * 查询管理员未处理表单
     *
     * @param page  当前页
     * @param limit 每页最多显示多少条数据
     * @return 返回相应状态码
     */
    Result getIncompleteForm(int page, int limit);

    /**
     * 查询管理员已处理报修单
     *
     * @param page  当前页
     * @param limit 页面大小
     * @return 返回相应状态码
     */
    Result getCompleteForm(int page, int limit);

    /**
     * 根据报修单Id删除对应报修单
     *
     * @param formId 报修单id
     * @return 返回相应状态码
     */
    Result<Boolean> deleteOne(int formId);

    /**
     * 传入工人key和报修单id将某报修单变为已安排工人状态
     * 并记录是哪个管理员进行的操作
     *
     * @param wKey        工人 key
     * @param adminId     管理员key
     * @param appointDate 最终确定日期
     * @param appointment 最终确定点数
     * @param formId      报修单id
     * @return 返回相应状态码
     */
    Result<Boolean> arrange(int wKey, String adminId, String appointDate, int appointment, int formId);
}

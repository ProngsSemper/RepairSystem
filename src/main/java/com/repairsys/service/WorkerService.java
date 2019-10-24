package com.repairsys.service;

import com.repairsys.bean.entity.WTime;
import com.repairsys.bean.entity.Worker;
import com.repairsys.bean.vo.Result;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.List;

/**
 * @author Prongs ,lyr
 * @date 2019/9/29 15:57
 */
public interface WorkerService {
    /**
     * 工人登录
     *
     * @param wId      用户账号
     * @param password 用户密码
     * @param session  用户传入的 session
     * @return
     */
    Result<Boolean> login(String wId, String password, HttpSession session);

    /**
     * 工人根据报修单号查询报修单
     *
     * @param formId 报修单号
     * @return 返回对应状态码
     */
    Result getByFormId(String formId);

    /**
     * 工人根据学生学号查询报修单
     *
     * @param stuId 学生学号
     * @return 返回对应状态码
     */
    Result getByStudentId(String stuId);

    /**
     * 工人修改报修单状态
     * <p>
     * 定义queryCode ,0是开始状态，开始报修，1是联系状态，已经约好工人，
     * 2是工人上门维修完毕 等待学生确认修好状态，3是已经修好状态（迁移入oldform)，-1是有缺少材料等问题状态
     *
     * @param queryCode 报修单状态码
     * @param formId    报修单号
     * @return 返回对应状态码
     */
    Result updateQueryCode(int queryCode, int formId);

    /**
     * 实现推荐算法，推荐工人
     *
     * @param workerList     工人表单
     * @param workerTimeList 工人时间表
     * @return 返回排序后的集合
     */
    Result getSortedWorkerList(List<Worker> workerList, List<WTime> workerTimeList);

    /**
     * 查询工人未完成报修单
     *
     * @param wId   工人Id
     * @param page  当前页面
     * @param limit 本页最多显示多少数据
     * @return 返回相应状态码
     */
    Result getIncompleteForm(String wId, int page, int limit);

    /**
     * 查询工人已完成报修单
     *
     * @param wId   工人Id
     * @param page  当前页面
     * @param limit 本页最多显示多少数据
     * @return 返回相应状态码
     */
    Result getCompleteForm(String wId, int page, int limit);

    /**
     * 查询满足条件的工人要求
     *
     * @param date       预约的那天
     * @param hour       预约的整点小时
     * @param workerType 工人类型
     * @return 返回推荐的工人的表单集合
     * @date 2019/10/17
     */
    Result<List<Worker>> getSuitableWorkerList(Date date, int hour, String workerType);

    /**
     * 根据工人key查询他的好评率
     *
     * @param wKey 工人key
     * @return 好评率
     */
    Result getEvaluation(int wKey);

    /**
     * 工人根据自己的key查询详细（文字）评价
     *
     * @param wKey 工人key
     * @return 评价详情
     */
    Result getDetailEvaluation(int wKey);
}

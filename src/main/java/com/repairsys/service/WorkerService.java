package com.repairsys.service;

import com.repairsys.bean.entity.WTime;
import com.repairsys.bean.entity.Worker;
import com.repairsys.bean.vo.Result;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Prongs
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
     * 定义queryCode ,0是开始状态，开始报修，1是联系状态，已经约好工人，2是修好7天内状态，
     * 3是已经修好状态（迁移入oldform)，-1是缺少材料等情况 还需要再修。
     *
     * @param queryCode 报修单状态码
     * @param formId    报修单号
     * @return 返回对应状态码
     */
    Result updateQueryCode(int queryCode, int formId);


    /**
     * 实现推荐算法，推荐工人
     * @param workerList  工人表单
     * @param workerTimeList 工人时间表
     * @return 返回排序后的集合
     */
    Result getSortedWorkerList(List<Worker>workerList, List<WTime> workerTimeList);


}

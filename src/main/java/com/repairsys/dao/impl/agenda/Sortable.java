package com.repairsys.dao.impl.agenda;

import com.repairsys.bean.entity.Worker;

import java.sql.Date;
import java.util.List;

/**
 * @Author lyr
 * @create 2019/10/11 23:29
 *
 * 负责对信息进行排序，根据前端传来的时间点，推荐出适合的工人
 *
 */
public interface Sortable {

    /**
     * 根据预约的时间推荐空闲且合适的工人
     * @param hours 预约的时间
     * @return 返回推荐的工人
     */
    List<Worker> recommendByAppointment(int... hours);
    /**
     * 根据预约的时间推荐空闲且合适的工人
     * @param hour 预约的时间
     * @param wType 工人类型
     * @return 返回推荐的工人
     */
    List<Worker> recommendByAppointment(int hour, String wType);


    /**
     * @param appointDate 学生预约的时间
     * @param hour  学生预约的时间，整点
     * @param wType  工人类型
     * @return
     */
    List<Worker> recommendByAppintment(Date appointDate,int hour,String wType);



}

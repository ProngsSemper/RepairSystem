package com.repairsys.service;

import com.repairsys.bean.vo.Result;

import java.util.HashMap;

/**
 * @Author lyr
 * @create 2019/10/22 19:10
 */
public interface ExcelService {
    /**
     * 导出工人每天的工作任务 ，Excel表
     *
     * @param res 需要填写的答案，添加数据后返回
     * @return 返回操作是否成功
     * @deprecated 推荐使用 exportAll
     */
    Result<HashMap> exportTable(Result res);

    /**
     * 一个一个工人的Excel任务表打印粗来
     *
     * @param res 返回将要转化为json的 result对象
     * @return 返回result参数
     * @deprecated 推荐使用 exportAll
     */
    Result<HashMap> exportOneByOne(Result res);

    /**
     * 打印所有工人的Excel表
     *
     * @param result 返回的参数
     * @return 返回result参数
     */
    Result exportAll(Result result);

    /**
     * 导出压缩包，并把路径存储在 result对象中
     *
     * @param exportPath
     * @param target     您要导出的目标文件，这里一般是选择天数算，比如我要把 2019-10-26这一天的工人任务表统一导出一个 zip压缩包，这个 target可以选择 2019-10-26 （需要注意的是）
     *                   <p>
     *                   你今天无法导出明天的压缩包，可以导出自服务器开启的那一天，有记录的压缩包
     *                   </p>
     * @param result     最终转为json字符串，并发送给前端页面
     */
    void exportZipFile(String exportPath, String target, Result result);

    /**
     * 查找服务器过去历史中，导出的 文件和 zip
     *
     * @param result 上一层servlet中传入的结果对象
     * @param target 你要查询的目标，可以是一段字符串，只要文件夹或文件夹下的路径包含这段字符串就记录，不过本人使用的是天数来区分不同的包，所有只是传一个年月日
     */
    void findPackageByString(Result result, String target);

}

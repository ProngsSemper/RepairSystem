package com.repairsys.controller.student;

import com.alibaba.fastjson.JSONObject;
import com.repairsys.bean.vo.Result;
import com.repairsys.code.ResultEnum;
import com.repairsys.controller.BaseServlet;
import com.repairsys.dao.DaoFactory;
import com.repairsys.service.ServiceFactory;
import com.repairsys.service.impl.student.StudentServiceImpl;
import com.repairsys.util.textfilter.SensitiveWordFilter;
import com.repairsys.util.textfilter.TextFilterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * @author Prongs
 * @date 2019/10/30 13:36
 */
@WebServlet("/student/feedback")
public class CreateFeedbackServlet extends BaseServlet {
    private final StudentServiceImpl studentService = ServiceFactory.getStudentService();
    private static final Logger logger = LoggerFactory.getLogger(CreateFeedbackServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject requestBody = (JSONObject) request.getAttribute("requestBody");
        String boardMsg = requestBody.getString("msg");
        //检测是否含有敏感词，有敏感词则提示 且告知敏感词是什么便于修改 不写入数据库

        SensitiveWordFilter filter = TextFilterFactory.getInstance().getFilter(request);
        boolean b = filter.isContainSensitiveWord(boardMsg, 1);
        Set<String> set = filter.getSensitiveWord(boardMsg, 1);
        Result result = new Result<>();
        if (b) {
            result.setResult(ResultEnum.FEEDBACK_SENSITIVELY);
            result.setDesc("所含敏感词为：" + set);
            logger.debug("发布失败{}", result);
            request.setAttribute("result", result);
            super.doPost(request, response);
            return;
        }
        result = studentService.createFeedback(requestBody.getString("stuId"),
                requestBody.getString("stuName"),
                requestBody.getString("stuPhone"),
                requestBody.getString("msg")
        );
        int flag = 200;
        if (result.getCode() == flag) {
            logger.debug("发布反馈成功{}", result);
        } else {
            logger.debug("发布反馈失败{}", result);
        }
        request.setAttribute("result", result);
        super.doPost(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

package com.repairsys.controller.student;

import com.alibaba.fastjson.JSONObject;
import com.repairsys.bean.vo.Result;
import com.repairsys.code.ResultEnum;
import com.repairsys.controller.BaseServlet;
import com.repairsys.service.ServiceFactory;
import com.repairsys.service.impl.student.StudentServiceImpl;
import com.repairsys.util.textfilter.SensitiveWordFilter;
import com.repairsys.util.textfilter.TextFilterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * @author Prongs
 * @date 2019/10/23 19:53
 */
@WebServlet("/student/evaluate/detail")
public class EvaluateDetailServlet extends BaseServlet {
    private final StudentServiceImpl studentService = ServiceFactory.getStudentService();
    private static final Logger logger = LoggerFactory.getLogger(EvaluateDetailServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject requestBody = (JSONObject) request.getAttribute("requestBody");
        String msg = requestBody.getString("msg");
        //检测是否含有敏感词，有敏感词则提示 且告知敏感词是什么便于修改 不写入数据库

        SensitiveWordFilter filter = TextFilterFactory.getInstance().getFilter(request);
        boolean isBadWords = filter.isContainSensitiveWord(msg, 1);
        Set<String> set = filter.getSensitiveWord(msg, 1);
        Result result;
        if (isBadWords) {
            Result<Boolean> sensitive = new Result<>();
            sensitive.setResult(ResultEnum.EVALUATE_SENSITIVELY);
            sensitive.setDesc("所含敏感词为：" + set);
            logger.debug("检测到有敏感词！");
            request.setAttribute("result", sensitive);
            super.doPost(request, response);
            return;
        }
        //详细（文字）评价
        result = studentService.addEvaluation(requestBody.getString("msg"),
                requestBody.getInteger("wKey"));
        int flag = 201;
        if (result.getCode() == flag) {
            logger.debug("评价成功{}", result);
            String path = "/student/evaluation";
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/" + path);
            requestDispatcher.forward(request, response);
        } else {
            logger.debug("评价失败{}", result);
        }
        request.setAttribute("result", result);
        super.doPost(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

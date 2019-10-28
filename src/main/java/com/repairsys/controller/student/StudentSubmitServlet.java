package com.repairsys.controller.student;

import com.alibaba.fastjson.JSONObject;
import com.repairsys.bean.vo.Result;
import com.repairsys.code.ResultEnum;
import com.repairsys.controller.BaseServlet;
import com.repairsys.service.ServiceFactory;
import com.repairsys.util.string.StringUtils;
import com.repairsys.util.textfilter.SensitiveWordFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Set;

/**
 * @Author lyr
 * @create 2019/10/9 20:39
 * <p>
 * 学生提交报修表单
 */
@WebServlet("/student/submission/form")
public class StudentSubmitServlet extends BaseServlet {
    public static final Logger logger = LoggerFactory.getLogger(StudentSubmitServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("收到申请表单请求");
        boolean commited = false;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                boolean b = "commited".equals(cookie.getName());
                if (b) {
                    commited = true;
                    break;
                }
            }
        }
        logger.info("处理中");
        if (commited) {
            Result<Boolean> commitedRes = new Result<>();
            commitedRes.setResult(ResultEnum.SUBMITTED_REPEATLY);
            logger.debug("检测到提交过了，返回");
            request.setAttribute("result", commitedRes);

            super.doPost(request, response);
            return;
        }
        HttpSession session = request.getSession();

        //检验token是否一样，如果有重复提交的话，token是一样的，就不写入数据库了
        JSONObject requestBody = (JSONObject) request.getAttribute("requestBody");
        String message = requestBody.getString("formMsg").trim();
        SensitiveWordFilter filter = new SensitiveWordFilter();
        //检测是否含有敏感词，有敏感词则提示 且告知敏感词是什么便于修改 不写入数据库
        boolean isBadWords = filter.isContainSensitiveWord(message, 1);
        Set<String> set = filter.getSensitiveWord(message, 1);
        if (isBadWords) {
            Result<Boolean> sensitive = new Result<>();
            sensitive.setResult(ResultEnum.SUBMITTED_SENSITIVELY);
            sensitive.setDesc("所含敏感词为：" + set);
            logger.debug("检测到有敏感词！");
            request.setAttribute("result", sensitive);
            super.doPost(request, response);
            return;
        }
        String mdMessage = StringUtils.getStringMd5(message);
        String temp = (String) session.getAttribute("mdMessage");
        boolean b = temp == null || (!temp.equals(mdMessage));
        if (!b) {
            Result<Boolean> commitedRes = new Result<>();
            commitedRes.setResult(ResultEnum.SUBMITTED_REPEATLY);
            logger.debug("检测到提交过了，返回");
            request.setAttribute("result", commitedRes);
            super.doPost(request, response);
            return;
        } else {
            logger.info(temp);
            logger.info(mdMessage);
            logger.info("正在后台提交数据");
            session.setAttribute("mdMessage", mdMessage);
        }
        //经过检验，提交的不是重复记录，可以通过，写入数据库
        String photoId = requestBody.getString("photoId");
        if (photoId == null) {
            photoId = " -1 ";
        }
        Result<Boolean> res = ServiceFactory.getStudentService().applyForm(
                requestBody.getString("stuId"),
                0,
                message,
                new Timestamp(System.currentTimeMillis()),
                requestBody.getString("stuMail"),
                photoId,
                requestBody.getString("room"),
                requestBody.getString("stuName"),
                requestBody.getString("stuPhone"),
                requestBody.getString("wType"),
                requestBody.getInteger("appointment"),
                requestBody.getString("appointDate"),
                "B"
        );
        int flag = 201;
        if (res.getCode() == flag) {
            logger.debug("提交成功{}", res);
            Cookie time = new Cookie("commited", "1");
            time.setMaxAge(60);
            //一分钟内不准提交
            response.addCookie(time);
        } else {
            logger.debug("提交失败{}", res);
        }

        request.setAttribute("result", res);
        super.doPost(request, response);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

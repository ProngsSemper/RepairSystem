package com.repairsys.controller.administrator;

import com.alibaba.fastjson.JSONObject;
import com.repairsys.bean.vo.Result;
import com.repairsys.code.ResultEnum;
import com.repairsys.controller.BaseServlet;
import com.repairsys.service.ServiceFactory;
import com.repairsys.service.impl.admin.AdminServiceImpl;
import com.repairsys.util.textfilter.SensitiveWordFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Set;

/**
 * @author Prongs
 * @date 2019/10/8 22:30
 */
@WebServlet("/admin/board")
public class ReleaseBoardServlet extends BaseServlet {
    private final AdminServiceImpl adminService = ServiceFactory.getAdminService();
    private static final Logger logger = LoggerFactory.getLogger(ReleaseBoardServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject requestBody = (JSONObject) request.getAttribute("requestBody");
        String boardMsg = requestBody.getString("board");
        //检测是否含有敏感词，有敏感词则提示 且告知敏感词是什么便于修改 不写入数据库
        String path = request.getServletContext().getRealPath("/WEB-INF");
        SensitiveWordFilter filter = new SensitiveWordFilter(path);
        boolean b = filter.isContainSensitiveWord(boardMsg, 1);
        Set<String> set = filter.getSensitiveWord(boardMsg, 1);
        Result result = new Result<>();
        if (b) {
            result.setResult(ResultEnum.RELEASE_SENSITIVELY);
            result.setDesc("所含敏感词为：" + set);
            logger.debug("发布失败{}", result);
            request.setAttribute("result", result);
            super.doPost(request, response);
            return;
        }
        if ("".equals(boardMsg)) {
            result.setResult(ResultEnum.RELEASE_FAILED);
            logger.debug("发布失败{}", result);
        } else {
            result = adminService.releaseBoard(boardMsg,
                    new Timestamp(System.currentTimeMillis()));
            int flag = 200;
            if (result.getCode() == flag) {
                logger.debug("发布成功{}", result);
            } else {
                logger.debug("发布失败{}", result);
            }
        }
        request.setAttribute("result", result);
        super.doPost(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

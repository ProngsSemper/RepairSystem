package com.repairsys.controller.administrator;

import com.alibaba.fastjson.JSONObject;
import com.repairsys.bean.vo.Result;
import com.repairsys.controller.BaseServlet;
import com.repairsys.service.ServiceFactory;
import com.repairsys.service.impl.admin.AdminServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Prongs  ,lyr
 * <p>
 * 这里使用的是分页查询，因此，前端需要传递给我的参数至少要有两个，limit,page
 * 其中 page 指定当前页 ，limit指定一页有几条数据
 * 如果想做优化的话，我们第一次查询出数据库的总记录数，发给前端，前端再带过来，这样就不用再查总数了，不过这就是后话了
 * @date 2019/10/1 16:35
 */
@WebServlet("/admin/stuName")
public class GetByStudentNameServlet extends BaseServlet {
    private final AdminServiceImpl adminService = ServiceFactory.getAdminService();
    private static final Logger logger = LoggerFactory.getLogger(GetByStudentNameServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject requestBody = (JSONObject) request.getAttribute("requestBody");

        Result result = adminService.getAllFormByStudentName(requestBody.getString("stuName"),
                requestBody.getInteger("page"),
                requestBody.getInteger("limit")
        );

        int flag = 200;
        if (result.getCode() == flag) {
            logger.debug("查询成功{}", result);
        } else {
            logger.debug("查询失败{}", result);
        }
        request.setAttribute("result", result);
        super.doPost(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

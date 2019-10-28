package com.repairsys.controller.administrator;

import com.repairsys.bean.vo.Page;
import com.repairsys.controller.BaseServlet;
import com.repairsys.service.ServiceFactory;
import com.repairsys.service.impl.form.FormServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Prongs
 * @date 2019/10/2 12:51
 * <p>
 * 分页查询
 */
@WebServlet("/admin/form/page")
public class GetByPageServlet extends BaseServlet {
    private final FormServiceImpl formService = ServiceFactory.getFormService();
    private static final Logger logger = LoggerFactory.getLogger(GetByPageServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int totalCount = formService.getTotalCount();
        Integer size = Integer.valueOf(request.getParameter("limit"));

        Integer page = Integer.valueOf(request.getParameter("page"));

        int totalPage = 1;

        if (totalCount > size) {
            boolean b = totalCount % size != 0;
            totalPage = totalCount / size;
            if (b) {
                ++totalPage;
            }

        }
        Page result = (Page) formService.getPageList(page, size);
        result.setSize(size);
        result.setTotalPage(totalPage);
        logger.debug("{}", page);

        result.setTargetPage(page);
        //TODO: 还有代码没写完，暂时脑补一下
        int flag = 200;
        if (result.getCode() == flag) {
            logger.debug("查询成功{}", result);
            result.setCode(0);
            result.setTotalCount(totalCount);
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

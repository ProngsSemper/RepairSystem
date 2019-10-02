package com.repairsys.controller.student;

import com.repairsys.controller.BaseServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author lyr
 * @create 2019/9/29 14:08
 */
@WebServlet("/student/information")
public class StudentLoginServlet extends BaseServlet {
    private static Logger logger = LoggerFactory.getLogger(StudentLoginServlet.class);



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("学生登录 ");


    }
   /**
    *
    * 用户个人信息获取
    *
    * */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  {

        doPost(request,response);
    }

}

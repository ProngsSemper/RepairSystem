package com.repairsys.controller.file;


import com.repairsys.dao.impl.file.FileDaoImpl;
import com.repairsys.dao.impl.form.FormListDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import java.io.IOException;

import java.util.Collection;

import java.util.LinkedList;
import java.util.UUID;

/**
 * @Author lyr
 * @create 2019/10/20 19:52
 */
@WebServlet("/upload/img")
@MultipartConfig()
public class UploadServlet extends HttpServlet {
    private static final FileDaoImpl FILE_DAO = FileDaoImpl.getInstance();
    private static final FormListDaoImpl FORM_DAO = FormListDaoImpl.getInstance();
    private static final String[] ARR =
    {
            ".jpg",".jpeg",".png",".gif"
    };

    private static final Logger logger = LoggerFactory.getLogger(UploadServlet.class);
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String formId = (String)request.getSession().getAttribute("formId");
        /*
         *还没完成
         * TODO: 这里先用假的formId 代替
         *
         * */
        formId = "34";

        logger.debug("正在提交图片信息");
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String path = request.getServletContext().getRealPath("/upload/img");
        Collection<Part> parts = request.getParts();
        LinkedList<String> imgPathList = new LinkedList<>();

        for(Part part: parts)
        {
            //如果是文件类型才进行下一步判断
            if("file".equals(part.getName()))
            {

                //判读是否为图片后缀
                if(!part.getContentType().startsWith("image"))
                {
                    continue;
                }
                String name = part.getSubmittedFileName();
                String fileName = path+"\\"+ UUID.randomUUID().toString();
                String finalFileName = fileName+name;

                logger.debug(finalFileName);
                part.write(finalFileName);
                imgPathList.add(finalFileName);

            }


        }
        //TODO:需要注释掉

        int primaryKey = FILE_DAO.addOne(imgPathList);
        logger.debug("key: {}",primaryKey);
        boolean b = FORM_DAO.setPhotoId(primaryKey,formId);
        System.out.println(b);
        //TODO:应该可以了
        request.getRequestDispatcher("../m_sucess.jsp").forward(request,response);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}

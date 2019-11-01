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
import java.io.File;
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

    private static final Logger logger = LoggerFactory.getLogger(UploadServlet.class);
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {




        int tmp = (Integer)request.getSession().getAttribute("formId");

        String formId = tmp+"";
        logger.debug("正在提交图片信息");
        request.setCharacterEncoding("utf-8");

        String path = request.getServletContext().getRealPath("/upload/img/");
        File f = new File(path);
        if(!f.exists())
        {
            f.mkdir();
        }
        Collection<Part> parts = request.getParts();
        LinkedList<String> imgPathList = new LinkedList<>();
        int count=0;
        for(Part part: parts)
        {


            if(!part.getContentType().startsWith("image"))
            {

                logger.debug("不是图片类型");
                continue;
            }
            if(count>3)
            {
                break;
            }
            ++count;
            String name = part.getSubmittedFileName();


            String fileName = path+"\\"+ UUID.randomUUID().toString();
            String finalFileName = fileName+name;

            logger.debug(finalFileName);
            part.write(finalFileName);
            imgPathList.add(finalFileName);


        }

        if(imgPathList.isEmpty())
        {
            return;
        }
        int primaryKey = FILE_DAO.addOne(imgPathList);


        logger.debug("key: {}",primaryKey);
        logger.debug("提交成功");
        boolean b = FORM_DAO.setPhotoId(primaryKey,formId);
        logger.trace("{}",b);
        // System.out.println(b);
        //TODO:应该可以了


    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}

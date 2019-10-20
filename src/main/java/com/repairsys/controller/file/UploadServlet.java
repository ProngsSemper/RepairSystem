package com.repairsys.controller.file;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Author lyr
 * @create 2019/10/20 19:52
 */
@WebServlet("/upload/img")
public class UploadServlet extends HttpServlet {
    private String UPLOAD_PATH = "E:/demo/file/";
    Logger logger = LoggerFactory.getLogger(UploadServlet.class);
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("收到请求");
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload sfu = new ServletFileUpload(factory);
        sfu.setHeaderEncoding("utf-8");


        try {
            //解析请求
            List<FileItem> items = sfu.parseRequest(request);
            for(FileItem i:items)
            {
                boolean b = i.isFormField();
                //是普通数据
                if(b)
                {
                    String fileName = i.getString("utf-8");
                    logger.info(fileName);

                }else {

                    String name = i.getName();
                    logger.info(name);
                    InputStream is =  i.getInputStream();
                    FileUtils.copyInputStreamToFile(is,new File(UPLOAD_PATH+name));
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}

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
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Author lyr, Prongs
 * @create 2019/10/20 19:52
 */
@WebServlet("/upload/img")
public class UploadServlet extends HttpServlet {
    private Logger logger = LoggerFactory.getLogger(UploadServlet.class);
    private String uploadPath = this.getServletContext().getRealPath("upload");

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("收到请求");
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload sfu = new ServletFileUpload(factory);
        sfu.setHeaderEncoding("utf-8");
        try {
            //解析请求
            List<FileItem> items = sfu.parseRequest(request);
            for (FileItem item : items) {
                boolean isFile = item.isFormField();
                //是普通数据
                if (isFile) {
                    String fileName = item.getString("utf-8");
                    logger.info(fileName);

                } else {
                    String name = item.getName();
                    //判断文件类型
                    String ext = name.substring(name.indexOf(".") + 1);
                    if (!("png".equals(ext) || "gif".equals(ext) || "jpg".equals(ext))) {
                        logger.info("图片类型有误");
                        return;
                    }
                    logger.info(name);
                    InputStream is = item.getInputStream();
                    FileUtils.copyInputStreamToFile(is, new File(uploadPath + name));
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

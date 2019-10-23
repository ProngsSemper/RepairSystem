package com.repairsys.controller.file;


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

import java.util.UUID;

/**
 * @Author lyr
 * @create 2019/10/20 19:52
 */
@WebServlet("/upload/img")
@MultipartConfig()
public class UploadServlet extends HttpServlet {
    private static final String[] ARR =
    {
            ".jpg",".jpeg",".png",".gif"
    };

    private static final Logger logger = LoggerFactory.getLogger(UploadServlet.class);
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("正在提交图片信息");
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String path = request.getServletContext().getRealPath("/upload");
        Collection<Part> parts = request.getParts();
        // LinkedList<String> imgPath = new LinkedList<>();

        for(Part part: parts)
        {
            //如果是文件类型才进行下一步判断
            if("file".equals(part.getName()))
            {
                String name = part.getSubmittedFileName();
                boolean b = false;
                //判读是否为图片后缀
                for(String t:ARR)
                {
                    if(name.endsWith(t))
                    {
                        b = true;
                        break;
                    }
                }
                //如果不是图片后缀就下一个
                if(!b)
                {
                    continue;
                }
                String fileName = path+"\\"+ UUID.randomUUID().toString();
                String finalFileName = fileName+name;
                System.out.println(finalFileName);
            }
            // System.out.println("3"+fileName+part.getName());
        }



    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}

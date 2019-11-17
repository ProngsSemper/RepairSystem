package com.repairsys.controller.file;

import com.alibaba.fastjson.JSONObject;
import com.repairsys.bean.vo.Result;
import com.repairsys.chat.util.TaskUtil;
import com.repairsys.controller.BaseServlet;
import com.repairsys.util.string.StringUtils;
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
 * @create 2019/11/17 14:14
 */
@WebServlet("/upload/talk/img")
@MultipartConfig()
public class TalkServlet extends BaseServlet {
    private static final Logger logger = LoggerFactory.getLogger(TalkServlet.class);


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        logger.warn("正在提交图片信息");
        request.setCharacterEncoding("utf-8");

        String path = request.getServletContext().getRealPath("/upload/talk/img");
        File f = new File(path);
        if(!f.exists())
        {
            f.mkdir();
        }
        Collection<Part> parts = request.getParts();
        LinkedList<String> imgPathList = new LinkedList<>();

        for(Part part: parts)
        {
            if(part.getContentType()==null||!part.getContentType().startsWith("image"))
            {
                continue;
            }

            String name = part.getSubmittedFileName();
            String fileName = path+"\\"+ UUID.randomUUID().toString();
            String finalFileName = fileName+name;
            logger.debug(finalFileName);
            //写入服务器
            part.write(finalFileName);
            imgPathList.add(finalFileName.replaceAll("\\\\","/").replaceAll("(.*)upload", StringUtils.getBasePath(request)+"/upload"));
        }
        System.out.println(imgPathList);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg",imgPathList.getFirst());
        jsonObject.put("sender",request.getParameter("sender"));
        jsonObject.put("target",request.getParameter("target"));
        jsonObject.put("isAdmin",request.getParameter("isAdmin"));

        TaskUtil.getInstance().consume(jsonObject);


        System.out.println(request.getParameter("sender"));
        System.out.println(request.getParameter("target"));
        Result res = new Result();


        request.setAttribute("result",res);
        super.doPost(request,response);




    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}

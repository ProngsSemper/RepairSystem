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
import java.util.LinkedList;
import java.util.UUID;

/**
 * @Author lyr
 * @create 2019/10/20 19:52
 */
@WebServlet("/uploadServlet")
@MultipartConfig()
public class UploadServlet extends HttpServlet {
    private static final String[] arr =
    {
            ".jpg",".jpeg",".png",".gif"
    };

    private static final Logger logger = LoggerFactory.getLogger(UploadServlet.class);
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String path = request.getServletContext().getRealPath("/upload");
        Collection<Part> parts = request.getParts();
        LinkedList<String> imgPath = new LinkedList<>();
        for(Part part: parts)
        {

            String fileName = path+"/"+ UUID.randomUUID().toString();
            System.out.println(part.getName());
            System.out.println(fileName+part.getName());
        }



    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}

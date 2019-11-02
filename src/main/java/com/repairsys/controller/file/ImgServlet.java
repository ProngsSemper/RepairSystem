package com.repairsys.controller.file;

import com.alibaba.fastjson.JSONObject;
import com.repairsys.bean.entity.Photo;
import com.repairsys.bean.vo.Result;
import com.repairsys.controller.BaseServlet;
import com.repairsys.dao.impl.file.FileDaoImpl;
import com.repairsys.service.FileService;
import com.repairsys.service.impl.file.ImgService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author lyr
 * @create 2019/11/1 1:10
 */
@WebServlet("/path.get")
public class ImgServlet extends BaseServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String p = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";


        JSONObject jsonObject = (JSONObject) request.getAttribute("requestBody");
        String formId = jsonObject.getString("formId");

        Result<Photo> res = ImgService.getInstance().getPath(formId);
        // Photo.set
        Photo tmp = res.getData();
        if(tmp!=null)
        {
            tmp.setPath(p);
        }else{
            Photo t = new Photo();


            res.setData(t);
        }
        request.setAttribute("result",res);
        super.doPost(request,response);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}

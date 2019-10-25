package com.repairsys.controller.file;

import com.repairsys.bean.vo.Excel;
import com.repairsys.bean.vo.Result;
import com.repairsys.code.ResultEnum;
import com.repairsys.controller.BaseServlet;
import com.repairsys.service.ExcelService;
import com.repairsys.service.impl.table.ExcelServiceImpl;
import com.repairsys.util.time.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

/**
 * @Author lyr
 * @create 2019/10/25 16:51
 */
@WebServlet("/file/excel")
public class ExcelServlet extends BaseServlet {
    Logger logger = LoggerFactory.getLogger(ExcelServlet.class);
    int time = -1;
    Result result = null;


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int tmp = Calendar.getInstance().getTime().getDay();
        boolean b = this.result==null||time==-1||time!=tmp;
        if(!b)
        {
            logger.debug("重复查询，直接返回");
            request.setAttribute("result",result);
            super.doPost(request,response);
            return;
        }
        logger.debug("初始化..");

        Excel<Excel> excel = new Excel();
        String path = request.getServletContext().getRealPath("/upload/excel/").replaceAll("\\\\","/")+ TimeUtil.getCurTime()+"/";
        //设置一下路径

        excel.setDesc(path);
        logger.debug(excel.getDesc());


        ExcelServiceImpl service = ExcelServiceImpl.getInstance();
        service.exportAll(excel);

        service.exportZipFile(request.getServletContext().getRealPath("/upload/zip/").replaceAll("\\\\","/")+TimeUtil.getCurTime()+".zip",
                TimeUtil.getCurTime(),
                excel
        );


        excel.setResult(ResultEnum.QUERY_SUCCESSFULLY);
        request.setAttribute("result",excel);
        this.result = excel;
        this.time = tmp;

        //TODO:顺便打印 压缩包，并把压缩包路径也返回



        super.doPost(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}

package com.repairsys.service.impl.table;

import com.repairsys.bean.entity.ExcelTable;
import com.repairsys.bean.vo.Result;
import com.repairsys.dao.impl.table.WorkerTableImpl;
import com.repairsys.service.ExcelService;
import com.repairsys.util.easy.EasyTool;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author lyr
 * @create 2019/10/22 19:12
 */
public final class ExcelServiceImpl implements ExcelService {
    private static ExcelServiceImpl service = new ExcelServiceImpl();

    public static ExcelServiceImpl getInstance() {
        return service;
    }

    private ExcelServiceImpl() {

    }

    private static final String PATH = "E:/demo/file/";
    private static final String[] title = {
            "工人名字",
            "表单id",
            "报修信息",
            "报修地点",
            "提交时间",
            "学生名字",
            "学生学号",
            "学生电话",
            "学生邮箱",
            "预约时间",
            "预约点数"
    };


    /**
     * 导出工人每天的工作任务 ，Excel表
     *
     * @return 返回操作是否成功
     */
    @Override
    public Result<Boolean> exportTable() {
        List<ExcelTable> list = WorkerTableImpl.getInstance().getTable();
        if(list==null||list.isEmpty()||list.get(0)==null)
        {
            return new Result<Boolean>();
        }
        Result<Boolean> res = new Result<>();
        String s = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
        try(
                FileOutputStream fos = new FileOutputStream(PATH+s+"_all.xls");
                )
        {
                Workbook wb = new HSSFWorkbook();
                HSSFSheet sheet = (HSSFSheet) wb.createSheet();
                int i=0;
                HSSFRow r0 = sheet.createRow(i++);
                EasyTool.print(r0,title);

                for(ExcelTable t:list)
                {
                    EasyTool.print(sheet.createRow(i++),
                            t.getwName(),
                            t.getFormId(),
                            t.getFormMsg(),
                            t.getRoom(),
                            t.getFormDate(),
                            t.getStuName(),
                            t.getStuId(),
                            t.getStuPhone(),
                            t.getStuMail(),
                            t.getAppointDate(),
                            t.getAppointment()


                            );
                }


                wb.write(fos);
                fos.flush();


        }catch (Exception e)
        {
            e.printStackTrace();
        }


        return res;
    }

    public void exportAll()
    {
        exportTable();
    }
    public  Result<Boolean> exportOneByOne()
    {
        List<ExcelTable> list = WorkerTableImpl.getInstance().getTable();
        if(list==null||list.isEmpty()||list.get(0)==null)
        {
            return new Result<Boolean>();
        }
        Result<Boolean> res = new Result<>();
        String s = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());

        HashMap<String,LinkedList<ExcelTable>> map = new HashMap<>(list.size());
        for(ExcelTable t:list)
        {
            LinkedList<ExcelTable> curTable = map.getOrDefault(t.getwName(), new LinkedList<>());
            curTable.add(t);
            map.put(t.getwName(),curTable);
        }
        for(LinkedList<ExcelTable> table:map.values())
        {
            print(table,s);
        }
        res.setData(true);

        return res;
    }















    public boolean print(LinkedList<ExcelTable > list, String s)
    {
        if(list==null||list.isEmpty()||list.get(0)==null)
        {
            return false;
        }
        Result<Boolean> res = new Result<>();
        //
        try(
                FileOutputStream fos = new FileOutputStream(PATH+s+list.get(0).getwName()+".xls");
        )
        {
            Workbook wb = new HSSFWorkbook();
            HSSFSheet sheet = (HSSFSheet) wb.createSheet();
            int i=0;
            HSSFRow r0 = sheet.createRow(i++);
            EasyTool.print(r0,title);

            for(ExcelTable t:list)
            {
                EasyTool.print(sheet.createRow(i++),
                        t.getwName(),
                        t.getFormId(),
                        t.getFormMsg(),
                        t.getRoom(),
                        t.getFormDate(),
                        t.getStuName(),
                        t.getStuId(),
                        t.getStuPhone(),
                        t.getStuMail(),
                        t.getAppointDate(),
                        t.getAppointment()


                );
            }


            wb.write(fos);
            fos.flush();


        }catch (Exception e)
        {
            e.printStackTrace();
        }


        return true;

    }
}

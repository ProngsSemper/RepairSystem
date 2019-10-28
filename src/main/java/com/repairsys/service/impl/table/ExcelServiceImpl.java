package com.repairsys.service.impl.table;

import com.repairsys.bean.entity.ExcelTable;
import com.repairsys.bean.vo.Excel;
import com.repairsys.bean.vo.Result;
import com.repairsys.dao.impl.table.WorkerTableImpl;
import com.repairsys.service.ExcelService;
import com.repairsys.util.easy.EasyTool;
import com.repairsys.util.file.PrintUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
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

    private String path = null;
    private static final String[] TITLE = {
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
    @SuppressWarnings("unchecked")
    @Override
    public Result<HashMap> exportTable(Result res) {
        List<ExcelTable> list = WorkerTableImpl.getInstance().getTable();
        if (list == null || list.isEmpty() || list.get(0) == null) {
            return res;
        }

        String s = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
        try (
                FileOutputStream fos = new FileOutputStream(path + s + "_all_.xls");
        ) {
            Excel p = (Excel) res;
            p.getPaths().put("所有人", path + s + "_all_.xls");
            Workbook wb = new HSSFWorkbook();
            HSSFSheet sheet = (HSSFSheet) wb.createSheet();
            int i = 0;
            HSSFRow r0 = sheet.createRow(i++);
            EasyTool.print(r0, TITLE);

            for (ExcelTable t : list) {
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

        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    /**
     * @param result 首先要获取path路径打印
     * @return 然后
     */
    @Override
    public Result exportAll(Result result) {
        if (this.path == null) {
            synchronized (this) {
                if (this.path == null) {
                    //TODO: 需要注意，在 servlet里面传参
                    this.path = result.getDesc();
                    File file = new File(this.path);
                    if (!file.exists()) {
                        file.mkdir();
                    }
                }
            }
            //把路径存储在 desc描述里面，到这里取出来
        }
        //注意，下面的代码，请不要调换上下位置，不然就是一堆bug
        exportOneByOne(result);
        exportTable(result);
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Result<HashMap> exportOneByOne(Result res) {
        List<ExcelTable> list = WorkerTableImpl.getInstance().getTable();
        if (list == null || list.isEmpty() || list.get(0) == null) {
            return res;
        }
        // Result<Boolean> res = new Result<>();
        String s = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
        HashMap<String, String> paths = new HashMap<>(list.size() + 1);
        ((Excel) res).setPaths(paths);

        java.util.LinkedHashMap<String, LinkedList<ExcelTable>> map = new java.util.LinkedHashMap(list.size());
        for (ExcelTable t : list) {
            LinkedList<ExcelTable> curTable = map.getOrDefault(t.getwName(), new LinkedList<>());
            curTable.add(t);
            map.put(t.getwName(), curTable);
        }
        for (LinkedList<ExcelTable> table : map.values()) {
            print(table, s, paths);
        }

        return res;
    }

    @Override
    public void exportZipFile(String exportPath, String target, Result result) {
        // 注意，这里我是 在 servlet那里创建里 result对象，为了节省变量，把path 路径存储在了 result对象里面的 description 描述中，因此这里是配合 专门的servlet来使用的
        LinkedList<File> targetFileList = PrintUtil.dfs(result.getDesc(), target);
        PrintUtil.export(result, exportPath, targetFileList);

    }

    /**
     * 查找服务器过去历史中，导出的 文件和 zip
     *
     * @param result 上一层servlet中传入的结果对象
     * @param target 你要查询的目标，可以是一段字符串，只要文件夹或文件夹下的路径包含这段字符串就记录，不过本人使用的是天数来区分不同的包，所有只是传一个年月日
     */
    @SuppressWarnings("unchecked")
    @Override
    public void findPackageByString(Result result, String target) {

        result.setData(0);
        String p = result.getDesc();
        result.setDesc("excel");
        PrintUtil.findFile(result, target, p + "/excel/");
        result.setDesc("zip");
        PrintUtil.findFile(result, target, p + "/zip/");
    }

    private boolean print(LinkedList<ExcelTable> list, String s, HashMap<String, String> paths) {
        if (list == null || list.isEmpty() || list.get(0) == null) {
            return false;
        }
        Result<Boolean> res = new Result<>();
        //
        try (
                FileOutputStream fos = new FileOutputStream(path + s + list.get(0).getwName() + ".xls");
        ) {
            paths.put(list.get(0).getwName(), path + s + list.get(0).getwName() + ".xls");
            Workbook wb = new HSSFWorkbook();
            HSSFSheet sheet = (HSSFSheet) wb.createSheet();
            int i = 0;
            HSSFRow r0 = sheet.createRow(i++);
            EasyTool.print(r0, TITLE);

            //for 循环导出一个员工对象的一列数据
            for (ExcelTable t : list) {
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

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;

    }
}

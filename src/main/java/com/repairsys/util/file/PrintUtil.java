package com.repairsys.util.file;

import com.repairsys.bean.vo.Excel;
import com.repairsys.bean.vo.Result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Author lyr
 * @create 2019/10/25 23:39
 */
public final class PrintUtil {
    private static final Logger logger = LoggerFactory.getLogger(PrintUtil.class);

    /**
     * 深度优先搜索，遍历资源目录
     * @return 返回File 集合
     */
    public static LinkedList<File> dfs(String path,String target)
    {

        LinkedList<File> fileList = new LinkedList<>();
        File curPackage = new File(path);
        if(!curPackage.exists())
        {
            curPackage.mkdir();
        //如果文件夹不存在，就创建文件夹
        }

        File[] tmpList = curPackage.listFiles();
        //列出当前文件夹的 file 对象
        for(File f: tmpList)
        {
            if(f.isDirectory())
            {
                String p = f.getAbsolutePath();
                if(p.lastIndexOf(target)>=0)
                {
                    //减枝操作，加快算法的速度
                    //注意，这里是遍历Excel表的存储路径，因此其文件目录是按照天数来算的
                    dfs(f.getAbsolutePath(),fileList,target);
                    break;
                }
            }else{
                String p = f.getAbsolutePath();
                if(p.lastIndexOf(target)>=0)
                {

                    fileList.add(f.getAbsoluteFile());
                }
            }
        }


        return fileList;

    }

    //搜索符合目标的文件
    /**
     * 深度优先搜索，相信你有能力看的懂
     * @param path 路径
     * @param ans 要把目标 文件路径记录在 answer 集合里面
     * @param target 目标文件，可以是目标文件路径的一小段
     */
    private static void dfs(String path,LinkedList<File> ans,String target)
    {
        File[] tmpList = new File(path).listFiles();
        for(File f:tmpList)
        {
            if(f.isDirectory())
            {
                String p = f.getAbsolutePath();
                if(p.lastIndexOf(target)>=0)
                {
                    dfs(f.getAbsolutePath(),ans,target);
                }
                break;
            }else{
                String p = f.getAbsolutePath();
                if(p.lastIndexOf(target)>=0)
                {
                    //查询到相关文件
                    ans.add(f.getAbsoluteFile());
                }
            }
        }

    }

    @SuppressWarnings("unchecked")
    public static void export(Result result, String exportPath, LinkedList<File> list)
    {


        File zipFile = new File(exportPath);
        boolean b = zipFile.exists();
        if(!b)
        {
            try {
                zipFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // = null;

        try(
                FileOutputStream fos = new FileOutputStream(zipFile);
                ZipOutputStream zos = new ZipOutputStream(fos);


        ){
            for(File i:list)
            {
                ZipEntry zipEntry  = new ZipEntry(i.getName());
                zos.putNextEntry(zipEntry);
                int len;
                byte[] buffer = new byte[1024*4];
                FileInputStream fis = new FileInputStream(i);
                while (((len=fis.read(buffer))>0))
                {
                    zos.write(buffer,0,len);
                }
                fis.close();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        ((Excel) result).getPaths().put("压缩包",exportPath);


    }




}

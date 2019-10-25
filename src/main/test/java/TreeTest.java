import com.repairsys.util.easy.EasyTool;
import com.repairsys.util.file.PrintUtil;
import com.repairsys.util.time.TimeUtil;
import net.sf.json.util.JSONUtils;
import org.junit.Test;
import org.omg.CORBA.PUBLIC_MEMBER;

import javax.jnlp.ClipboardService;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Author lyr
 * @create 2019/10/25 22:16
 */

public class TreeTest {

    //深度遍历某文件夹下的文件

    public LinkedList<File> dfs(String path)
    {
        String curTime = TimeUtil.getCurTime();

        File files = new File(path);
            LinkedList<File> list = new LinkedList<>();
        if(files.exists())
        {
            File[] fileList = files.listFiles();
            for(File f:fileList)
            {
                if(f.isDirectory())
                {
                    System.out.println("文件夹: "+f.getAbsolutePath());
                    dfs(f.getAbsolutePath());
                }else {
                    System.out.println("文件: "+f.getAbsolutePath());
                    boolean b = f.getAbsolutePath().lastIndexOf(curTime)>0;
                    if(b)
                    {
                        list.add(f.getAbsoluteFile());
                        System.out.println("发现今天的文件，添加进去-------------------");
                    }
                }
            }
        }

        for(File i:list)
        {
            System.out.println("开始打印----ddd----------");
            System.out.println(i);
        }

        return list;



    }
    // @Test
    public  LinkedList<File> dfs()
    {
        String path ="F:\\算法\\我的团队项目\\p1\\target\\RepairSystem\\upload";
        return dfs(path);
    }

    @Test
    public void print3()
    {
        String path = "F:\\算法\\我的团队项目\\p1\\target\\RepairSystem\\upload\\test";
        File f = new File(path);
        System.out.println(f.exists());
        if(!f.exists())
        {
            f.mkdir();
        }
    }

    @Test
    public void export()
    {
        LinkedList<File> list = dfs();
    //    导出压缩包
        String path ="F:\\算法\\我的团队项目\\p1\\target\\RepairSystem\\upload\\zip\\test.zip";
        File zipFile = new File(path);
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
                 FileOutputStream  fos = new FileOutputStream(zipFile);
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



         } catch (FileNotFoundException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         } finally {

         }






    }

    @Test
    public void print2()
    {

        // System.out.println(TimeUtil.getCurTime());
        long begin = System.currentTimeMillis();
        dfs();
        begin = System.currentTimeMillis()-begin;
        System.out.println(begin);

    }

    @Test
    public void print1()
    {
        System.out.println("222123456".lastIndexOf("12","222123456".length()-20));
    }

    @Test
    public void testPrint()
    {
        // 打印 带有 2015-10-25日字符串的文件路径
        LinkedList<File> ans = PrintUtil.dfs("F:\\算法\\我的团队项目\\p1\\target\\RepairSystem\\upload","2019-10-25");
        System.out.println("\r\n\r\n");
        for(File i: ans)
        {
            System.out.println(i);
        }


    }

    @Test
    public void testPrint2()
    {
        String p = "\\dd\\dd\\dd".replaceAll("\\\\","/");
        System.out.println(p);
    }

    @Test
    public void printStr()
    {
        System.out.println("123".lastIndexOf("123"));
    }
}

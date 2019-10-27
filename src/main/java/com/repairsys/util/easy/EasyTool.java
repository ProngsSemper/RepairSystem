package com.repairsys.util.easy;

import com.alibaba.fastjson.JSONObject;
import com.repairsys.bean.entity.WTime;
import com.repairsys.bean.entity.Worker;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.*;

/**
 * @Author lyr
 * @create 2019/9/26 1:29
 */
public final class EasyTool {
    private static final Logger logger = LoggerFactory.getLogger(EasyTool.class);


    //分页查询公式  limit (page-1)*size,size

    /**
     * 形如 select * from table limit ?,?; 的sql语句，给写dao的程序员方便计算用的
     *
     * @param nextPage 前端点击下一页，的目标页面
     * @param size     一页要的总的记录数
     * @return 长度为2的 int数组，第一个数是limit的第一个问号，第二个是 limit的第二个问号
     */
    public static int[] getLimitNumber(int nextPage, int size) {
        if (nextPage <= 0) {
            nextPage = 1;
        }
        int[] ans = new int[2];
        ans[0] = (nextPage - 1) * size;
        ans[1] = size;
        return ans;
    }

    /**
     * 传入入会话的request对象，验证用户输入的验证码是否正确
     *
     * @param request 用户的请求
     * @return 验证码是否填写正确
     */
    public static boolean compareToCode(HttpServletRequest request) {
        logger.debug("进入比较");
        JSONObject jsonObject = (JSONObject) request.getAttribute("requestBody");
        logger.debug("获得对象");
        System.out.println(jsonObject);
        if(jsonObject==null)
        {
            return false;
        }
        String code = jsonObject.getString("vcode").toLowerCase();
        System.out.println(code);
        String ans = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        System.out.println(ans);
        logger.debug("{}", ans);
        boolean b;
        b = ans.equals(code);
        logger.debug("{}", b);
        return b;

    }

    /**
     * 估计bean中的属性，做一个简单的排序，用于实现简单的推荐算法
     * @param workerList 工人或者工人表单的集合
     * @param workerTimeList  工人或者工人时间表类
     * @deprecated
     */
    @Deprecated
    public static void resortListOfWorker(List<WTime>workerTimeList,List<Worker> workerList)
    {
        Collections.sort(workerTimeList);
        Collections.sort(workerList);
        Iterator<WTime> it = workerTimeList.iterator();
        for(Worker w:workerList)
        {
            if(it.hasNext())
            {
                WTime table = it.next();
                if(table.getwKey()==w.getwKey())
                {
                    w.setScore(table.getSum());
                }
            }else{
                break;
            }
        }

        /*
        *
        * 这里是使用了 lambda表达式，如果报错，请把程序改成 比较器形式，或者把jdk版本修改为 1.8或以上
        *
        * */
        workerList.sort((Worker i, Worker j) -> {
            return j.getScore() - i.getScore();
        });

    }







        private static int width = 90;
    // 定义图片的width
        private static int height = 20;
        // 定义图片的height
        private static int codeCount = 4;

        // 定义图片上显示验证码的个数

        private static int xx = 15;
        private static int fontHeight = 18;
        private static  int codeY = 16;
        private static char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
                'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

        /**
         * 生成一个map集合
         * code为生成的验证码
         * codePic为生成的验证码BufferedImage对象
         * @return
         */
        public static Map<String,Object> generateCodeAndPic() {
            // 定义图像buffer
            BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            Graphics gd = buffImg.getGraphics();
            // 创建一个随机数生成器类
            Random random = new Random();
            // 将图像填充为白色
            gd.setColor(Color.WHITE);
            gd.fillRect(0, 0, width, height);

            // 创建字体，字体的大小应该根据图片的高度来定。
            Font font = new Font("Fixedsys", Font.BOLD, fontHeight);
            // 设置字体。
            gd.setFont(font);

            // 画边框。
            gd.setColor(Color.BLACK);
            gd.drawRect(0, 0, width - 1, height - 1);

            // 随机产生40条干扰线，使图象中的认证码不易被其它程序探测到。
            gd.setColor(Color.BLACK);
            for (int i = 0; i < 30; i++) {
                int x = random.nextInt(width);
                int y = random.nextInt(height);
                int xl = random.nextInt(12);
                int yl = random.nextInt(12);
                gd.drawLine(x, y, x + xl, y + yl);
            }

            // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
            StringBuffer randomCode = new StringBuffer();
            int red = 0, green = 0, blue = 0;

            // 随机产生codeCount数字的验证码。
            for (int i = 0; i < codeCount; i++) {
                // 得到随机产生的验证码数字。
                String code = String.valueOf(codeSequence[random.nextInt(36)]);
                // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
                red = random.nextInt(255);
                green = random.nextInt(255);
                blue = random.nextInt(255);

                // 用随机产生的颜色将验证码绘制到图像中。
                gd.setColor(new Color(red, green, blue));
                gd.drawString(code, (i + 1) * xx, codeY);

                // 将产生的四个随机数组合在一起。
                randomCode.append(code);
            }
            Map<String,Object> map  =new HashMap<String,Object>();
            //存放验证码
            map.put("code", randomCode);
            //存放生成的验证码BufferedImage对象
            map.put("codePic", buffImg);
            return map;
        }

    /**
     * 打印Excel表的一个方法
     * @param r row,行数
     * @param str 列字符串数组
     */
        public static void print(HSSFRow r,String... str)
        {
            for(int i=0;i<str.length;++i)
            {
                HSSFCell col = r.createCell(i);
                col.setCellValue(str[i]);
            }
        }






}

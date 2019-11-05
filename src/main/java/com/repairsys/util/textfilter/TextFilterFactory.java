package com.repairsys.util.textfilter;

import javax.servlet.http.HttpServletRequest;


/**
 * @Author lyr
 * @create 2019/11/5 9:57
 *
 * 使用此类是发现，每次敏感词过滤都要 new 一次对象，还要从 web-inf 下面读取文件，并且写入字符串，构造字典树，耗费了大量的资源，性能低效
 *
 *
 */
public class TextFilterFactory {
    private SensitiveWordFilter  filter = null;
    private static final TextFilterFactory FACTORY = new TextFilterFactory();
    private TextFilterFactory()
    {

    }
    public static TextFilterFactory getInstance()
    {
        return FACTORY;
    }


    public SensitiveWordFilter getFilterForm(String path)
    {
        return null;
    }


    public SensitiveWordFilter getChatPathFilter()
    {
        //file:/D:/JavaWeb/.metadata/.me_tcat/webapps/TestBeanUtils/WEB-INF/classes/
        if(this.filter==null)
        {
            String path = Thread.currentThread().getContextClassLoader().getResource("").toString();
            path = path.replace('/', '\\').replace("file:", "").replace("classes\\", "").substring(1);



            this.filter = new SensitiveWordFilter(path);
        }

        return filter;
    }

    public  SensitiveWordFilter getFilter(HttpServletRequest request)
    {
        if(this.filter==null)
        {
            String path = request.getServletContext().getRealPath("/WEB-INF");

            this.filter =  new SensitiveWordFilter(path);
        }
        return this.filter;
    }
}

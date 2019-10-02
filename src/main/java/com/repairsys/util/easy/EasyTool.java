package com.repairsys.util.easy;

/**
 * @Author lyr
 * @create 2019/9/26 1:29
 */
public final class EasyTool {

    //分页查询公式  limit (page-1)*size,size

    /**
     *  形如 select * from table limit ?,?; 的sql语句，给写dao的程序员用的
     *
     * @param nextPage 前端点击下一页，的目标页面
     * @param size 一页要的总的记录数
     * @return  长度为2的 int数组，第一个数是limit的第一个问号，第二个是 limit的第二个问号
     */
    public static int[] getLimitNumber(int nextPage,int size)
    {
        if(nextPage<=0)
        {
            nextPage = 1;
        }
        int[] ans = new int[2];
        ans[0] = (nextPage-1)*size;
        ans[1] = size;
        return ans;
    }




}

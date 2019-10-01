package com.repairsys.util.easy;

/**
 * @Author lyr
 * @create 2019/9/26 1:29
 */
public final class EasyTool {

    //分页查询公式  limit (page-1)*size,size

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

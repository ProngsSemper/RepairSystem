package com.repairsys.service.impl.file;

import com.repairsys.bean.entity.Photo;
import com.repairsys.bean.vo.Result;
import com.repairsys.code.ResultEnum;
import com.repairsys.dao.impl.file.FileDaoImpl;

/**
 * @Author lyr
 * @create 2019/10/24 0:02
 */
public final class ImgService {
    private static final ImgService imgService = new ImgService();

    private ImgService() {
    }

    public static ImgService getInstance()
    {
        return imgService;
    }


    public Result<Photo> getPath(String formId)
    {
        Photo tmp = FileDaoImpl.getInstance().getImgPath(formId);
        Result temp = new Result();
        if(tmp==null)
        {
            temp.setResult(ResultEnum.QUERY_EMPTY);
        }else{
            temp.setData(tmp);
            temp.setResult(ResultEnum.QUERY_SUCCESSFULLY);
        }

        return temp;
    }




}

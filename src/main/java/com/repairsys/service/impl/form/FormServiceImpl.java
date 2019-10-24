package com.repairsys.service.impl.form;

import com.repairsys.bean.entity.Form;
import com.repairsys.bean.vo.Page;
import com.repairsys.bean.vo.Result;
import com.repairsys.code.ResultEnum;
import com.repairsys.dao.DaoFactory;
import com.repairsys.dao.FormDao;
import com.repairsys.service.FormService;

import java.util.List;

/**
 * @Author lyr
 * @create 2019/9/29 0:53
 */
public final class FormServiceImpl implements FormService {
    private FormDao formDao = DaoFactory.getFormDao();

    @Override
    public int getTotalCount() {
        return formDao.getTotalCount();
    }

    @Override
    public Result<List> getPageList(int targetPage, int size) {
        Result<List> result = new Page<>();
        List<Form> list = formDao.getPageList(targetPage, size);
        result.setData(list);
        return result.setResult(ResultEnum.QUERY_SUCCESSFULLY);
    }
}

package com.repairsys.service.impl.worker;

import com.repairsys.bean.entity.Worker;
import com.repairsys.bean.vo.Result;
import com.repairsys.code.ResultEnum;
import com.repairsys.dao.DaoFactory;
import com.repairsys.dao.WorkerDao;
import com.repairsys.service.WorkerService;
import com.repairsys.util.string.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;

/**
 * @author Prongs
 * @date 2019/9/29 15:59
 */
public class WorkerServiceImpl implements WorkerService {
    private static final Logger logger = LoggerFactory.getLogger(WorkerServiceImpl.class);
    private WorkerDao workerDao = DaoFactory.getWorkerDao();

    @Override
    public Result<Boolean> login(String wId, String password, HttpSession session) {

        Result<Boolean> result = new Result<>();
        if (!StringUtils.login(wId, password)) {
            return result.setResult(ResultEnum.USERNAME_PASSWORD_EMPTY);
        }
        //该方法在内部已经catch住了异常，出异常时 admin可能为空
        Worker worker = workerDao.login(wId, password);
        if (worker == null) {
            return result.setResult(ResultEnum.LOGIN_FAIL);
        }

        session.setAttribute("worker", worker);

        return result.setResult(ResultEnum.LOGIN_SUCCESS);
    }

    public WorkerServiceImpl() {
    }

}
package com.repairsys.service.impl.admin;



import com.repairsys.bean.entity.Admin;
import com.repairsys.bean.vo.Result;
import com.repairsys.code.ResultEnum;
import com.repairsys.dao.AdminDao;
import com.repairsys.dao.DaoFactory;
import com.repairsys.service.AdminService;
import com.repairsys.util.string.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.http.HttpSession;

/**
 * @Author lyr
 * @create 2019/9/24 18:08
 */
public class AdminServiceImpl implements AdminService {
    //TODO: 目前只是写了 login

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
    private AdminDao adminDao = DaoFactory.getAdminDao();



    /**
     * 管理员登录
     *
     * @param adminId  用户账号
     * @param password 用户密码
     * @return
     */
    @Override
    public Result<Boolean> login(String adminId, String password, HttpSession session) {

        Result<Boolean> result = new Result<>();
        if(!StringUtils.login(adminId,password))
        {
            return result.setResult(ResultEnum.USERNAME_PASSWORD_EMPTY);
        }
        //该方法在内部已经catch住了异常，出异常时 admin可能为空
        Admin admin = adminDao.login(adminId,password);
        if(admin == null)
        {
            return result.setResult(ResultEnum.LOGIN_FAIL);
        }

        session.setAttribute("admin",admin);



        return result.setResult(ResultEnum.LOGIN_SUCCESS);
    }

    public AdminServiceImpl() {
    }
}

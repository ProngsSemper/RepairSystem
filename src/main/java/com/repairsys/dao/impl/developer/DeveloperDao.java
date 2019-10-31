package com.repairsys.dao.impl.developer;

import com.repairsys.bean.entity.Developer;
import com.repairsys.dao.BaseDao;
import com.repairsys.util.db.JdbcUtil;
import com.repairsys.util.string.StringUtils;

import java.sql.Connection;

/**
 * 开发者账号，项目发布时删除接口
 *
 * @Author lyr
 * @create 2019/10/2 15:20
 */
public class DeveloperDao extends BaseDao<Developer> {
    private static final String LOGIN = "select * from supers where `id` =? and `password` = ?";
    private static final String REGISTER = "insert into supers (`id`,`password`) values(?,?)";


    private DeveloperDao() {
        super(Developer.class);
    }

    private static final DeveloperDao DAO = new DeveloperDao();

    public static DeveloperDao getInstance() {
        return DAO;
    }

    public Developer login(String id, String password) {
        String section = StringUtils.getStringMd5(password);
        String pwd = section + section;

        return super.selectOne(JdbcUtil.getConnection(), LOGIN, id, pwd);
    }

    public boolean register(String id, String password) {
        String section = StringUtils.getStringMd5(password);
        String pwd = section + section;
        return super.addOne(JdbcUtil.getConnection(), REGISTER, id, pwd);
    }

}

package com.repairsys.dao.impl.form;

import com.repairsys.bean.entity.Form;
import com.repairsys.util.db.JdbcUtil;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.List;

/**
 * @Author lyr
 * @create 2019/9/30 18:50
 */
public final class FormListDaoImpl extends FormDaoImpl{
    private static final String BASE_QUERY_BY_ID = "select * from form where queryCode = ? and stuId like %?";
    private static final String BASE_QUERY = "select * from form where queryCode = ? ";
    private static final FormListDaoImpl DAO = new FormListDaoImpl();
    private FormListDaoImpl()
    {
        super();
    }
    public static FormListDaoImpl getInstance()
    {
        return DAO;
    }
    public final List<Form> getListById(String stuId,int queryCode,String... formId)
    {
        if(formId.length==0)
        {
            return super.selectList(JdbcUtil.getConnection(),BASE_QUERY_BY_ID,queryCode,stuId);
        }
        StringBuffer sb = new StringBuffer(BASE_QUERY);

        for(int i=0;i<formId.length;++i)
        {
            sb.append(" and formId like%"+formId[i]);
        }


        return super.selectList(JdbcUtil.getConnection(),sb.toString(),queryCode,stuId);
    }

    public final List<Form> getList(int queryCode)
    {
        return super.selectList(JdbcUtil.getConnection(),BASE_QUERY,queryCode);
    }

    /**
     * 将数据库表中的过期的信息迁移到 oldform 中
     * @return 返回操作是否成功
     */
    @Override
    public final Boolean updateTable()
    {
        return super.updateTable();
    }



}

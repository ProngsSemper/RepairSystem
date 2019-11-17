package com.repairsys.dao.impl.form;

import com.repairsys.bean.entity.Form;
import com.repairsys.dao.AbstractPageDao;
import com.repairsys.dao.FormDao;
import com.repairsys.util.db.JdbcUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * @Author lyr
 * @create 2019/9/26 23:23
 */
public class FormDaoImpl extends AbstractPageDao<Form> implements FormDao {
    private static final Logger logger = LoggerFactory.getLogger(FormDaoImpl.class);

    /**
     * 查询表单的 id号
     */
    private static final String QUERY_BY_FORMID = "select * from form where `formId` = ?";
    private static final String QUERY_OLD_BY_FORMID = "select * from oldform where `formId` = ?";
    private static final String WORKER_QUERY_INCOMPLETE_BY_FORMID = "select * from form where `formId` = ? and wKey = ? and queryCode=1";
    private static final String ADMIN_QUERY_COMPLETE_BY_FORMID = "select * from form where `formId` = ? and queryCode <> 0";
    private static final String WORKER_QUERY_COMPLETE_BY_FORMID = "select * from form where `formId` = ? and wKey = ? and (queryCode>1 OR queryCode =-1)";
    private static final String ADMIN_QUERY_INCOMPLETE_BY_FORMID = "select * from form where `formId` = ? and queryCode=0";
    private static final String WORKER_QUERY_BY_FORMID_OLD = "select * from oldform where `formId` = ? and wKey = ? and (queryCode>1 OR queryCode =-1)";
    private static final String ADMIN_QUERY_BY_FORMID_OLD = "select * from oldform where `formId` = ? and queryCode <> 0";
    /**
     * 根据学生的 id号查询
     */
    private static final String QUERY_BY_STUDENTID = "select * from form where `stuId` like '%";
    private static final String QUERY_BY_STUDENTID_OLD = "select * from oldform where `stuId` like '%";

    /**
     * 申请维修
     */
    private static final String APPLY_FORM = "INSERT INTO FORM (stuId,queryCode,formMsg,formDate,stuMail,photoId,room,stuName,stuPhone,wType,appointment,appointDate,level)values(?,?,?,?,?,?,?,?,?,?,?,DATE_FORMAT('2019-'?,'%Y-%m-%d'),?)";
    /**
     * 申请维修
     */
    private static final String APPLY_FORM_DEFAULT = "INSERT INTO FORM (stuId,queryCode,formMsg,formDate,stuMail,photoId)values(?,?,?,?,?,?)";

    /**
     * 查询超过了30天前的记录
     */
    private static final String QUERY_MORE_THAN_DAY30 = "select * from form where queryCode>=2 and endDate<= date_sub(CURDATE(),interval 37 day)";

    /**
     * 将超过7天的废弃数据迁移到old 表
     */
    private static final String QUERY_MORE_THAN_DAY7 = "insert into oldform( select *  from form where queryCode>=2 and appointDate<= date_sub(CURDATE(),interval 7 day))";

    /**
     * 如果工人已经和学生提交的 form表单匹配上了，但是工人可能不会上我们这个网来确认，因此可能要管理员来手动确认，或者这里14天内，如果工人未确认的话，也需要更新
     * 因为学生那边是有一键再修的功能的，学生不满意，是可以再修的，而且正常的报修不会超过2个星期都没搞定，我们也有其他的方法来解决这个的问题
     */
    private static final String QUERY_MORE_THAN_DAY14 = "insert into oldform (select * from form where queryCode=1 and appointDate<= date_sub(CURDATE(),interval 14 day))";

    /**
     * 删除超过7天的垃圾数据
     */
    private static final String DELETE_FORM_DAY_OVER7 = "delete FROM form where queryCode>=2  and appointDate<= date_sub(CURDATE(),interval 7 day)";

    private static final String DELETE_FORM_DAY_OVER14 = "delete FROM form where queryCode=1  and appointDate<= date_sub(CURDATE(),interval 14 day)";
    /**
     * 设置管理员的id
     */
    private static final String SET_ADMIN_KEY = "update form set adminKey = ? where formId = ?";
    /**
     * 设置工人的id
     */
    private static final String SET_WORKER_KEY = "update form set wKey = ? where formId = ?";
    private static final String SET_FINISHED_WORK = "update form set queryCode = ? where formId = ?";

    /**
     * 设置照片的url表信息的id
     */
    private static final String SET_PHOTO_KEY = "update form set photoId = ? where formId = ?";
    /**
     * 更新工作完成时间
     */
    private static final String SET_FINISH_DAY = "update form set endDate = ?, queryCode=3 where formId = ?";
    /**
     * 管理员分配维修任务时的更新操作
     */
    private static final String UPDATE_INFORMATION = "update form set endDate = ? , queryCode = ? ,adminKey = ?  ,wKey=? where formId = ?";

    /**
     * 查询对于的状态的表单
     */
    private static final String QUERY_FORM_CODE = "select * from form where queryCode = ?";

    private static final String QUERY_FORM_BY_WKEY = "select * from form where wKey = ?";
    private static final String QUERY_OLDFORM_BY_WKEY = "select * from oldform where wKey = ?";
    private static final String BOOST_LEVEL = "update form set level=\"A\" where formId = ?";
    private static final String RESET_QUERYCODE = "update form set queryCode = 0 , adminKey=NULL , wKey=NULL where formId = ?";
    private static final String STUDENT_CONFIRM = "INSERT INTO oldform SELECT * FROM `form` WHERE formId=?";
    private static final String DELETE_STUDENT_CONFIRM = "DELETE FROM form WHERE formId=?";
    private static final String DELETE_ONE = DELETE_STUDENT_CONFIRM;
    private static final String ARRANGE = "UPDATE form SET queryCode=1,wKey=?,adminKey=?,appointDate=DATE_FORMAT(?,'%y-%m-%d'),appointment=? WHERE formId=?";

    /**
     * 学生对工人评价
     */
    private static final String GOOD = "UPDATE workers SET good = good + 1 WHERE wKey = ?";
    private static final String MID = "UPDATE workers SET mid = mid + 1 WHERE wKey = ?";
    private static final String BAD = "UPDATE workers SET bad = bad + 1 WHERE wKey = ?";
    private static final String FINISH_EVALUATE = "UPDATE oldform SET queryCode=4 WHERE formId = ?";
    /**
     * 学生申请一键再修时，重新预约时间
     */
    private static final String APPOINT_AGAIN = "UPDATE form SET appointDate=DATE_FORMAT('2019-'?,'%y-%m-%d'),appointment=? WHERE formId=?";

    String INSERT_FORM =
            "INSERT INTO FORM (stuId,queryCode,formId,formMsg,formDate,stuMail,photoId,adminKey,room)values(?,?,?,?,?,?,?,?,?)";

    private static final FormDaoImpl FORM_DAO = new FormDaoImpl();

    protected FormDaoImpl() {
        super(Form.class);
    }

    public static FormDaoImpl getInstance() {
        return FORM_DAO;
    }

    @Override
    public Form queryByFormId(int formId) {
        return super.selectOne(JdbcUtil.getConnection(), QUERY_BY_FORMID, formId);
    }

    @Override
    public List<Form> queryAllByFormId(int formId) {
        List<Form> list = super.selectList(JdbcUtil.getConnection(), QUERY_BY_FORMID, formId);
        if (list.isEmpty()) {
            return super.selectList(JdbcUtil.getConnection(), QUERY_OLD_BY_FORMID, formId);
        } else {
            return list;
        }
    }

    @Override
    public List<Form> adminQueryIncompleteFormByFormId(String formId) {
        return super.selectList(JdbcUtil.getConnection(), ADMIN_QUERY_INCOMPLETE_BY_FORMID, formId);
    }

    /**
     * 工人根据维修单号来查询未完成维修单的信息
     *
     * @param formId 维修单号
     * @param wKey   工人id
     * @return 返回表单bean对象
     */
    @Override
    public List<Form> workerQueryIncompleteFormByFormId(String formId, int wKey) {
        return super.selectList(JdbcUtil.getConnection(), WORKER_QUERY_INCOMPLETE_BY_FORMID, formId, wKey);
    }

    @Override
    public List<Form> workerQueryCompleteFormByFormId(String formId, int wKey) {
        return super.selectList(JdbcUtil.getConnection(), WORKER_QUERY_COMPLETE_BY_FORMID, formId, wKey);
    }

    @Override
    public List<Form> adminQueryCompleteFormByFormId(String formId) {
        return super.selectList(JdbcUtil.getConnection(), ADMIN_QUERY_COMPLETE_BY_FORMID, formId);
    }

    @Override
    public List<Form> workerQueryOldByFormId(String formId, int wKey) {
        return super.selectList(JdbcUtil.getConnection(), WORKER_QUERY_BY_FORMID_OLD, formId, wKey);
    }

    @Override
    public List<Form> adminQueryOldByFormId(String formId) {
        return super.selectList(JdbcUtil.getConnection(), ADMIN_QUERY_BY_FORMID_OLD, formId);
    }

    /**
     * 分页查询
     *
     * @param wKey 员工的key，
     * @param page 查询的页面
     * @param size 查询的记录数
     * @return bean表单集合
     * @deprecated 未实现
     */
    @Override
    public List<Form> queryAllFormIdByWorkerKey(int wKey, int page, int size) {
        throw new RuntimeException("没有实现此方法");
    }

    /**
     * 返回工人维修表单的总数
     *
     * @param wkey 工人的账号
     * @return 返回工人维修过的表单的记录数
     */
    @Override
    public int getCountByWorkerKey(String wkey) {
        throw new RuntimeException("没有实现此方法");
    }

    /**
     * 根据学号来查询维修单的信息
     *
     * @param stuId 学生学号
     * @return 返回表单bean对象
     */
    @Override
    public List<Form> queryByStudentId(String stuId) {
        String finalSql = QUERY_BY_STUDENTID + stuId + "%'";
        return super.selectList(JdbcUtil.getConnection(), finalSql);
    }

    /**
     * 在已过期表单中通过学生id查找历史报修单（模糊查询）
     *
     * @param stuId 学生id
     * @return oldfrom表中数据
     */
    @Override
    public List<Form> queryOldByStudentId(String stuId) {
        String finalSql = QUERY_BY_STUDENTID_OLD + stuId + "%'";
        return super.selectList(JdbcUtil.getConnection(), finalSql);
    }

    /**
     * 查询指定状态的表单，比如查询未进行维修处理的表单 和 已经维修完成的表单
     *
     * @param status 表单状态 如 0 表示待维修  1联系中  2表示7天内
     * @return 返回表单bean 集合(List)
     */
    @Override
    public List<Form> queryFormListByStatus(byte status) {
        assert status >= 0 && status <= 3;
        String sql = "select * from form where queryCode = ?";

        return super.selectList(JdbcUtil.getConnection(), sql, status);
    }

    /**
     * 用户提交维修申请，需要将表单读写入数据库
     *
     * @param args 表单的信息
     * @return 返回布尔值，如果提交成功返回true，如果运行出现异常，返回false
     * @deprecated 请不要直接使用，Object不定长参数不安全，而且该函数已经内定好sql语句了
     */
    @Deprecated
    @Override
    public Boolean apply(Object... args) {

        Connection conn = JdbcUtil.getConnection();
        return super.addOne(conn, APPLY_FORM, args);

    }

    /**
     * 学生如果提交图片，我需要获取formId ,发现接口 没定义，这里直接加了
     *
     * @param stuId 学生输入的id号
     * @return 带有 formId 的 form
     */
    public Form getNewFormId(String stuId) {
        String sql = "select formId from form where stuId = ? order by  formId DESC limit 0,1";
        return super.selectOne(JdbcUtil.getConnection(), sql, stuId);
    }

    /**
     * 用户申请表单提交
     *
     * @param stuId    学生账号
     * @param code     表单状态: 例如 0表示 false，即没有维修， 1表示维修完成后7天内
     * @param formMsg  表单内容详情
     * @param formDate 表单日期
     * @param stuMail  用户的邮箱账号
     * @param photoId  用户发送的照片在服务器的地址存储路径
     * @return 返回布尔值，如果提交成功返回true，如果运行出现异常，返回false
     */
    @Override
    public Boolean apply(String stuId, int code, String formMsg, Date formDate, String stuMail, String photoId) {
        //INSERT INTO FORM (stuId,queryCode,formMsg,formDate,stuMail,photoId)";
        return super.addOne(JdbcUtil.getConnection(), APPLY_FORM_DEFAULT, stuId, code, formMsg, formDate, stuMail, photoId);
    }

    /**
     * 用户申请表单提交
     *
     * @param stuId    学生账号
     * @param formMsg  学生表单报修详细情况
     * @param formDate 学生表单提交日期
     * @return 返回布尔值，如果提交成功返回true，如果运行出现异常，返回false
     */
    @Override
    public Boolean apply(String stuId, String formMsg, Date formDate) {

        return this.apply(stuId, 0, formMsg, formDate, "", "");
    }

    /**
     * 维修成功后表单在数据库超过7天，管理员可能会手动删除记录，或者迁移记录，用来给管理员迁移记录到新的表的功能
     *
     * @param form 表单的bean对象
     * @return 如果插入失败，返回false，否则返回true
     */
    @Override
    public Boolean apply(Form form) {
        return this.apply(form.getStuId(), form.getQueryCode(), form.getFormMsg(), form.getFormDate(), form.getStuMail(), form.getPhotoId());
    }

    //TODO:后端同学请注意，这个东西一定要读懂才去调用

    /**
     * 管理员可能要删除维修完成后，时间过久了的表单记录，根据表单的 id号进行删除
     * 删除oldform 表，将oldform 表中超过30天的数据 视为废弃数据，删除
     *
     * @param tableName 删除某一个form表的数据
     * @return 如果删除失败，或者出现异常，返回false，否则返回true
     */
    @Override
    public Boolean deleteBefore(String tableName, Date date) {
        if (tableName == null || tableName.length() <= 1) {
            tableName = "oldform";
        }

        String patchDelete = "delete from " + tableName + " where " + " queryCode >= 2 and  and date_sub(CURDATE(),interval 30 day)  >= CURDATE()";
        logger.info(patchDelete);

        boolean b = super.deleteOne(JdbcUtil.getConnection(), patchDelete);
        return b;
    }

    /**
     * 垃圾清理
     * 将form中超过 7天的数据放入 oldform表
     */
    @Override
    public Boolean moveTo() {
        boolean b = super.updateOne(JdbcUtil.getConnection(), QUERY_MORE_THAN_DAY7);

        if (b) {
            super.deleteOne(JdbcUtil.getConnection(), DELETE_FORM_DAY_OVER7);
        }

        b = super.updateOne(JdbcUtil.getConnection(), QUERY_MORE_THAN_DAY14);
        if (b) {
            return super.deleteOne(JdbcUtil.getConnection(), DELETE_FORM_DAY_OVER14);
        }
        return false;

    }

    /**
     * 将超过 7天的数据迁移到 oldform 表中
     * 更加语义化的函数，建议直接用这个
     *
     * @return
     */
    @Override
    public Boolean updateTable() {
        logger.debug("-------- update form ----------------");
        return this.moveTo();
    }

    /**
     * 对应的管理员处理的信息
     *
     * @param adminKey 设置管理员记录，哪个管理员来处理对应的申请
     * @param formId   对应的表单的主键id号码
     * @return 若操作无异常，返回true
     */
    @Override
    public Boolean setAdminKey(String adminKey, String formId) {
        return super.updateOne(JdbcUtil.getConnection(), SET_ADMIN_KEY, adminKey, formId);
    }

    /**
     * 设置对应的工人，由管理员手动分配
     *
     * @param workerKey 设置对于的工人的 信息主键
     * @param formId    设置对应的表单的主键 id号码
     * @return 若操作无异常，返回true
     */
    @Override
    public Boolean setwKey(String workerKey, String formId) {
        return super.updateOne(JdbcUtil.getConnection(), SET_WORKER_KEY, workerKey, formId);
    }

    /**
     * 设置照片的信息，学生上传照片，照片的 url地址保存在一个 photo表里面，记录photo 表信息的主键
     *
     * @param id     设置学生上传照片在服务器的地址
     * @param formId 设置对应的表单的主键 id号码
     * @return 若操作无异常，返回true
     */
    @Override
    public Boolean setPhotoId(String id, String formId) {
        return super.updateOne(JdbcUtil.getConnection(), SET_PHOTO_KEY, id, formId);
    }

    public Boolean setPhotoId(int id, String formId) {
        return super.updateOne(JdbcUtil.getConnection(), SET_PHOTO_KEY, id, formId);
    }

    /**
     * 工作完成，工人抹除任务
     *
     * @param date   维修完成的日期
     * @param formId 设置对应的表单的主键 id号码
     * @return 若操作无异常，返回true
     */
    @Override
    public Boolean setEndDate(Date date, String formId) {
        return super.updateOne(JdbcUtil.getConnection(), SET_FINISH_DAY, date, formId);
    }

    /**
     * 设置任务完成状态，学生申请： 0，管理员审核 ：1 ，工人维修完成 2
     *
     * @param code   维修单的状态码，完成进度
     * @param formId 设置对应的表单的主键 id号码
     * @return 若操作无异常，返回true
     */
    @Override
    public Boolean setQueryCode(int code, String formId) {
        return super.updateOne(JdbcUtil.getConnection(), SET_FINISHED_WORK, code, formId);
    }

    /**
     * 管理员查看了学生的申请，并为其分配维修工人和安排时间
     *
     * @param formId    设置对应的表单的主键 id号码
     * @param endDate   维修结束时间
     * @param queryCode 维修查询状态码
     * @param adminKey  管理员的key主键
     * @param wKey      工人的key主键
     * @return 如果操作无异常，返回true
     * @date 2019/10/1
     */
    @Override
    public Boolean updateForm(String formId, Date endDate, int queryCode, int adminKey, int wKey) {
        return super.updateOne(JdbcUtil.getConnection(), UPDATE_INFORMATION, endDate, queryCode, adminKey, wKey, formId);
    }

    /**
     * 分页查询的功能
     *
     * @param targetPage 目标页面
     * @param size       记录条数
     * @return 一个bean集合
     * @deprecated
     */
    @Deprecated
    @Override
    public List<Form> getPageList(int targetPage, int size) {
        throw new RuntimeException("方法已经过时");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Form> getListByWorkerId(String workerKey) {
        List<Form> list1 = super.selectList(JdbcUtil.getConnection(), QUERY_FORM_BY_WKEY, workerKey);
        List<Form> list2 = super.selectList(JdbcUtil.getConnection(), QUERY_OLDFORM_BY_WKEY, workerKey);
        list1.addAll(list2);
        return list1;
    }

    /**
     * 没有加对应的查询条件，不建议使用
     *
     * @return 返回总的记录数
     */
    @Deprecated
    @Override
    public int getTotalCount() {
        String sql = "select count(*) from form";
        return super.getCount(JdbcUtil.getConnection(), sql);
    }

    /**
     * 获取form表的对应条件的记录总数
     *
     * @param queryCode 你要查询哪一类表单？ 0：正在申请 1：已经分配维修工 2：修理完成  -1：特殊情况
     * @return 返回对应的记录的条数
     */
    @Override
    public int getTotalCount(int queryCode) {
        return super.getCount(JdbcUtil.getConnection(), QUERY_FORM_CODE, queryCode);
    }

    /**
     * 用户申请表单提交
     *
     * @param stuId    学生账号
     * @param code     表单状态
     * @param formMsg  表单内容详情
     * @param formDate 表单日期
     * @param formMail 用户的邮箱账号
     * @param photoId  用户发送的照片在服务器的地址存储路径
     * @param room     宿舍房间号，维修地址
     * @return 布尔值
     */
    @Override
    public Boolean apply(String stuId, int code, String formMsg, Date formDate, String formMail, String photoId, String room) {

        return super.updateOne(JdbcUtil.getConnection(), INSERT_FORM, stuId, code, formMsg, formDate, formMail, photoId, room);
    }

    @Override
    public Boolean boostLevel(int formId) {
        super.updateOne(JdbcUtil.getConnection(), RESET_QUERYCODE, formId);
        return super.updateOne(JdbcUtil.getConnection(), BOOST_LEVEL, formId);
    }

    @Override
    public Boolean studentConfirm(int formId) {
        if (super.selectOne(JdbcUtil.getConnection(), QUERY_BY_FORMID, formId) == null) {
            return false;
        }
        super.updateOne(JdbcUtil.getConnection(), SET_FINISH_DAY, new Date(System.currentTimeMillis()), formId);
        super.updateOne(JdbcUtil.getConnection(), STUDENT_CONFIRM, formId);
        return super.updateOne(JdbcUtil.getConnection(), DELETE_STUDENT_CONFIRM, formId);
    }

    @Override
    public Boolean delete(int formId) {
        if (super.selectOne(JdbcUtil.getConnection(), QUERY_BY_FORMID, formId) == null) {
            return false;
        }
        return super.updateOne(JdbcUtil.getConnection(), DELETE_ONE, formId);
    }

    @Override
    public Boolean arrange(int wKey, int adminKey, String appointDate, int appointment, int formId) {
        if (super.selectOne(JdbcUtil.getConnection(), QUERY_BY_FORMID, formId) == null) {
            return false;
        }
        return super.updateOne(JdbcUtil.getConnection(), ARRANGE, wKey, adminKey, appointDate
                , appointment, formId);
    }

    @Override
    public Boolean evaluate(String evaluation, int wKey, int formId) {
        if ("good".equals(evaluation)) {
            super.updateOne(JdbcUtil.getConnection(), FINISH_EVALUATE, formId);
            return super.updateOne(JdbcUtil.getConnection(), GOOD, wKey);
        } else if ("mid".equals(evaluation)) {
            super.updateOne(JdbcUtil.getConnection(), FINISH_EVALUATE, formId);
            return super.updateOne(JdbcUtil.getConnection(), MID, wKey);
        } else if ("bad".equals(evaluation)) {
            super.updateOne(JdbcUtil.getConnection(), FINISH_EVALUATE, formId);
            return super.updateOne(JdbcUtil.getConnection(), BAD, wKey);
        } else {
            return false;
        }
    }

    @Override
    public Boolean appointAgain(String appointDate, int appointment, int formId) {
        if (appointDate == null || appointment == 0 || formId == 0 || "".equals(appointDate)) {
            return false;
        }
        return super.updateOne(JdbcUtil.getConnection(), APPOINT_AGAIN, appointDate, appointment, formId);
    }
}

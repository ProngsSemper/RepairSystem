package com.repairsys.dao.impl.board;

import com.repairsys.bean.entity.Board;
import com.repairsys.dao.BaseDao;
import com.repairsys.dao.BoardDao;
import com.repairsys.util.db.JdbcUtil;

import java.util.List;

/**
 * @author Prongs
 * @date 2019/10/9 10:34
 */
public class BoardDaoImpl extends BaseDao<Board> implements BoardDao {
    // private final Connection JdbcUtil.getConnection() = JdbcUtil.getConnection();

    private static final String HISTORY_BOARD = "select boardMsg,date from board ORDER BY `key` DESC";
    private static final String BOARD = "select boardMsg,date from board where queryCode = 1";

    private static final BoardDaoImpl BOARD_DAO;

    static {
        BOARD_DAO = new BoardDaoImpl();
    }

    public static BoardDaoImpl getInstance() {
        return BOARD_DAO;
    }

    private BoardDaoImpl() {
        super(Board.class);
    }

    @Override
    public List getHistoryBoard() {
        return super.selectList(JdbcUtil.getConnection(), HISTORY_BOARD);
    }

    @Override
    public List getBoard() {
        return super.selectList(JdbcUtil.getConnection(), BOARD);
    }

    @Override
    public int getAllCountInBoard() {
        String countSql = "SELECT COUNT(*) FROM board";
        return super.getCount(JdbcUtil.getConnection(), countSql);
    }

}

package com.repairsys.dao.impl.board;

import com.repairsys.bean.entity.Board;
import com.repairsys.dao.BaseDao;
import com.repairsys.dao.BoardDao;
import com.repairsys.util.db.JdbcUtil;
import com.repairsys.util.easy.EasyTool;

import java.util.List;

/**
 * @author Prongs
 * @date 2019/10/9 10:34
 */
public class BoardDaoImpl extends BaseDao<Board> implements BoardDao {
    private static final String HISTORY_BOARD = "select boardMsg,date from board limit ?,?";

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
    public List getHistoryBoard(int page, int limit) {
        int[] ans = EasyTool.getLimitNumber(page, limit);
        return super.selectList(JdbcUtil.getConnection(), HISTORY_BOARD, ans[0], ans[1]);
    }

    @Override
    public int getAllCountInBoard() {
        String countSql = "SELECT COUNT(*) FROM board";
        return super.getCount(JdbcUtil.getConnection(), countSql);
    }

}

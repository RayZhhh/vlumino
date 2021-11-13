package pers.zr.vlumino.chinesechess.ai.heuristic;

import pers.zr.vlumino.chinesechess.ai.CChessboard;
import pers.zr.vlumino.chinesechess.ai.ChessPath;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取所有吃子路径，在静态搜索中使用
 */
public class AllEatPath extends AllPath {

    public AllEatPath(CChessboard board) {
        super(board);
    }


    @Override
    public List<ChessPath> selectPossiblePath(int colorSign) {
        List<ChessPath> chessPaths = super.selectPossiblePath(colorSign);
        List<ChessPath> ret = new ArrayList<>();
        for (ChessPath chessPath : chessPaths) {
            if (chessPath.eat != 0) {
                ret.add(chessPath);
            }
        }
        return ret;
    }
}

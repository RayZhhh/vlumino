package pers.zr.vlumino.chinesechess.ai.heuristic;


import pers.zr.vlumino.chinesechess.ai.CChessboard;
import pers.zr.vlumino.chinesechess.ai.ChessPath;

import java.util.*;

public class AllPathAndSort extends AllPath {

    public AllPathAndSort() {
        super();
    }


    public AllPathAndSort(CChessboard board) {
        super(board);
    }


    /**
     * 选择所有的路径，按照价值的变化将其排序
     *
     * @param colorSign
     * @return
     */
    @Override
    public List<ChessPath> selectPossiblePath(int colorSign) {
        List<ChessPath> chessPaths = super.selectPossiblePath(colorSign);
        for (ChessPath chessPath : chessPaths) {
            super.setPathValue(chessPath);
        }
        chessPaths.sort((o1, o2) -> colorSign > 0 ? o2.value - o1.value : o1.value - o2.value);
        return chessPaths;
    }
}

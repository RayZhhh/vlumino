package pers.zr.vlumino.chinesechess.ai.heuristic;


import pers.zr.vlumino.chinesechess.ai.CChessboard;
import pers.zr.vlumino.chinesechess.ai.ChessPath;
import pers.zr.vlumino.chinesechess.ai.GenChessPath;

import java.util.ArrayList;
import java.util.List;


public class AllPath extends PathSelector {

    public AllPath() {
        super();
    }


    public AllPath(CChessboard board) {
        super(board);
    }


    /**
     * 选择所有的路径
     *
     * @param colorSign
     * @return
     */
    @Override
    public List<ChessPath> selectPossiblePath(int colorSign) {
        List<ChessPath> ret = new ArrayList<>();
        if (colorSign > 0) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 9; j++) {
                    if (this.cChessboard.innerChessboard[i][j] > 0) {
                        List<ChessPath> chessPath = GenChessPath.getChessPathOfID(i, j, cChessboard);
                        if (chessPath != null) {
                            ret.addAll(chessPath);
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 9; j++) {
                    if (this.cChessboard.innerChessboard[i][j] < 0) {
                        List<ChessPath> chessPath = GenChessPath.getChessPathOfID(i, j, cChessboard);
                        if (chessPath != null) {
                            ret.addAll(chessPath);
                        }
                    }
                }
            }
        }
        return ret;
    }
}

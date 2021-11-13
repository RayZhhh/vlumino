package pers.zr.vlumino.chinesechess.ai.heuristic;

import pers.zr.vlumino.chinesechess.ai.CChessboard;
import pers.zr.vlumino.chinesechess.ai.ChessPath;
import pers.zr.vlumino.chinesechess.ai.algo.AlphaBeta;

import java.util.List;

public class AllPathAndHeuristicByAlphaBeta extends AllPath {

    public AllPathAndHeuristicByAlphaBeta() {
        super();
    }


    public AllPathAndHeuristicByAlphaBeta(CChessboard board) {
        super(board);
        this.heuristicDepth = 2;
    }


    /**
     * 构造函数
     * @param board 需要生成路径的棋盘
     * @param heuristicDepth 利用alpha-beta启发的深度
     */
    public AllPathAndHeuristicByAlphaBeta(CChessboard board, int heuristicDepth) {
        super(board);
        this.heuristicDepth = heuristicDepth;
    }


    /**
     * 利用alpha-beta进行启发的深度
     * 即用这个深度作为depth进行alpha-beta搜索
     */
    public int heuristicDepth;


    @Override
    public List<ChessPath> selectPossiblePath(int colorSign) {
        List<ChessPath> chessPaths = super.selectPossiblePath(colorSign);
        for (ChessPath chessPath : chessPaths) {
            chessPath.value = new AlphaBeta(cChessboard).calculateValue(chessPath, heuristicDepth);
        }
        chessPaths.sort((o1, o2) -> colorSign > 0 ? o2.value - o1.value : o1.value - o2.value);
        for (ChessPath chessPath : chessPaths) {
            System.out.println(chessPath.value + " " + chessPath);
        }
        return chessPaths;
    }
}

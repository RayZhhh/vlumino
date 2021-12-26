package pers.zr.vlumino.chinesechess.ai.heuristic;

import pers.zr.vlumino.chinesechess.ai.CChessboard;

public class AlphaBetaAndPathNumRestrict extends AllPathAndHeuristicByAlphaBeta {

    public AlphaBetaAndPathNumRestrict() {
        super();
    }


    public AlphaBetaAndPathNumRestrict(CChessboard board) {
        super(board);
        this.heuristicDepth = 2;
    }


    /**
     * 构造函数
     * @param board 需要生成路径的棋盘
     * @param heuristicDepth 利用alpha-beta启发的深度
     */
    public AlphaBetaAndPathNumRestrict(CChessboard board, int heuristicDepth) {
        super(board);
        this.heuristicDepth = heuristicDepth;
    }

}

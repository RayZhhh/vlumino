package pers.zr.vlumino.chinesechess.ai.evaluator;

import pers.zr.vlumino.chinesechess.ai.ChessPath;

public interface ChineseChessEvaluator {
    /**
     * 获得最好的走法
     *
     * @return 价值最大的走法
     */
    ChessPath getBestPath();

    /**
     * 获得最好的走法
     *
     * @param colorSign 棋子类型
     * @return 价值最大的走法
     */
    ChessPath getBestPath(int colorSign);
}

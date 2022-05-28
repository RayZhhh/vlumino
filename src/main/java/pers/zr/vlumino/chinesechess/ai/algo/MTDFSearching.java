package pers.zr.vlumino.chinesechess.ai.algo;


import pers.zr.vlumino.chinesechess.ai.ChessPath;

public interface MTDFSearching {
    /**
     * 获得路径评分
     *
     * @param path  要评估的路径
     * @param depth 评估深度
     * @return 在当前盘面下当前走法的评分
     */
    int calculateMTDFValue(ChessPath path, int depth, int initValue);


    /**
     * 获得路径评分
     *
     * @param path      要评估的路径
     * @param depth     评估深度
     * @param colorSign 棋子的颜色（评估对手/自己）
     * @return 在当前盘面下当前走法的评分
     */
    int calculateMTDFValue(ChessPath path, int depth, int initValue, int colorSign);
}

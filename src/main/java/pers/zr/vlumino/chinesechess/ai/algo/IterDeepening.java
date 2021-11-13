package pers.zr.vlumino.chinesechess.ai.algo;

import pers.zr.vlumino.chinesechess.ai.CChessboard;
import pers.zr.vlumino.chinesechess.ai.ChessPath;


public class IterDeepening extends MTDF {

    public IterDeepening(CChessboard cChessboard) {
        super(cChessboard);
    }

    /**
     * 迭代深入搜索
     *
     * @param path      当前尝试的路径
     * @param depth     当前深度
     * @param colorSign 当前尝试棋子的类型
     * @return 经过搜索后得到的价值
     */
    public int doIterDeepening(ChessPath path, int depth, int colorSign) {
        int val = 0;
        for (int i = 2; i <= depth; i = i + 1) {
            val = super.calculateValue(path, i, colorSign);
        }
        return val;
    }

    @Override
    public int calculateValue(ChessPath path, int depth) {
        return doIterDeepening(path, depth, MIN_LAYER_SIGN);
    }

    @Override
    public int calculateValue(ChessPath path, int depth, int colorSign) {
        return doIterDeepening(path, depth, colorSign);
    }
}

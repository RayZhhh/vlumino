package pers.zr.vlumino.chinesechess.ai.algo;

import pers.zr.vlumino.chinesechess.ai.CChessboard;
import pers.zr.vlumino.chinesechess.ai.ChessPath;
import pers.zr.vlumino.chinesechess.ai.ChessWeights;

public class MTDF_Opt extends OptimizedAlphaBetaWithMemory {
    /**
     * MTDF边界值的初始值
     */
    public static final int MTDF_INIT_VAL = 0;

    /**
     * 构造
     *
     * @param board 待评估的棋盘
     */
    public MTDF_Opt(CChessboard board) {
        super(board);
    }

    /**
     * 利用MTDF快速确定价值的搜索
     *
     * @param path      当前尝试的路径
     * @param beta      剪枝变量beta
     * @param depth     当前深度
     * @param colorSign 当前尝试棋子的类型
     * @return 经过搜索后得到的价值
     */
    public int MTDFSearching(ChessPath path, int beta, int depth, int colorSign) {
        int val = beta;
        int upperBound = MAX_EVALUATE_VAL;
        int lowerBound = MIN_EVALUATE_VAL;

        // ---------------------------------- 获得当前路径的价值 ----------------------------------
        int id = chessboard.innerChessboard[path.fromX][path.fromY];
        int eatVal = path.eat == 0 ? 0 : ChessWeights.getPosVal(path.eat, path.toX, path.toY);
        int after = ChessWeights.getPosVal(id, path.toX, path.toY);
        int before = ChessWeights.getPosVal(id, path.fromX, path.fromY);
        path.value = after - before - eatVal;
        int curVal = chessboard.getOnBoardVal() + path.value;
        // ---------------------------------- 获得当前路径的价值 ----------------------------------
        while (lowerBound < upperBound) {
            if (val == lowerBound) {
                beta = val + 1;
            } else {
                beta = val;
            }
            // 进行一个以 [beta - 1, beta] 为零窗口的记忆化搜索
            val = alphaBetaWithMemory_ValueOptimized(path, beta - 1, beta, depth, colorSign, curVal,
                    getNewZobrist(path, chessboard.hashCode()), getNewVerify(path, chessboard.verifyCode())
            );
            if (val < beta) {
                upperBound = val;
            } else {
                lowerBound = val;
            }
        }
        return val;
    }

    @Override
    public int calculateValue(ChessPath path, int depth) {
        return MTDFSearching(path, MTDF_INIT_VAL, depth, MIN_LAYER_SIGN);
    }

    @Override
    public int calculateValue(ChessPath path, int depth, int colorSign) {
        return MTDFSearching(path, MTDF_INIT_VAL, depth, colorSign);
    }
}

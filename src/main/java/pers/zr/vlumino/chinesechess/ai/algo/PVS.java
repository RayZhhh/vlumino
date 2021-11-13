package pers.zr.vlumino.chinesechess.ai.algo;

import pers.zr.vlumino.chinesechess.ai.CChessboard;
import pers.zr.vlumino.chinesechess.ai.ChessPath;
import pers.zr.vlumino.chinesechess.ai.heuristic.AllPathAndSort;

import java.util.List;

public class PVS extends AlphaBetaTree {

    public PVS(CChessboard cChessboard) {
        super(cChessboard);
    }

    /**
     * 主变量导向搜索PVS
     *
     * @param path      当前尝试的路径
     * @param alpha     剪枝变量alpha
     * @param beta      剪枝变量beta
     * @param depth     当前深度
     * @param colorSign 当前尝试棋子的类型
     * @return 经过搜索后得到的价值
     */
    public int pvsEvaluation(ChessPath path, int alpha, int beta, int depth, int colorSign) {
        if (depth == 1) {
            chessboard.moveChess(path);
            int val = chessboard.getOnBoardVal();
            chessboard.undoMoveChess(path);
            return val;
        }
        // 判断生死
        if (path.eat == -5) {
            return MAX_EVALUATE_VAL;
        } else if (path.eat == 5) {
            return MIN_EVALUATE_VAL;
        }
        // 落子
        chessboard.moveChess(path);
        // max
        if (colorSign == MAX_LAYER_SIGN) {
            int maxEval = MIN_EVALUATE_VAL;
            // 对手搜索我方可能的路径
            List<ChessPath> possiblePath = new AllPathAndSort(chessboard).selectPossiblePath(MIN_LAYER_SIGN);
            for (int i = 0; i < possiblePath.size(); i++) {
                // 所有路径下的第一个节点作为主变量，进行全窗口搜索
                if (i == 0) {
                    maxEval = pvsEvaluation(possiblePath.get(i), alpha, beta, depth - 1, MIN_LAYER_SIGN);
                } else {
                    // 非主变量通过缩小alpha-beta窗口实现
                    int eval = pvsEvaluation(possiblePath.get(i), alpha, alpha + 1, depth - 1, MIN_LAYER_SIGN);
                    if (eval >= alpha + 1) {
                        // 进行全窗口搜索
                        eval = pvsEvaluation(possiblePath.get(i), maxEval, beta, depth - 1, MIN_LAYER_SIGN);
                        maxEval = Math.max(maxEval, eval);
                    }
                }
                alpha = Math.max(alpha, maxEval);
                if (beta <= alpha) {
                    break;
                }
            }
            // 恢复棋盘
            chessboard.undoMoveChess(path);
            return maxEval;
        } else { // min
            int minEval = MAX_EVALUATE_VAL;
            // 搜索对手的可能路径
            List<ChessPath> possiblePath = new AllPathAndSort(chessboard).selectPossiblePath(MAX_LAYER_SIGN);
            for (int i = 0; i < possiblePath.size(); i++) {
                // 主变量
                if (i == 0) {
                    minEval = pvsEvaluation(possiblePath.get(i), alpha, beta, depth - 1, MAX_LAYER_SIGN);
                } else {
                    // 非主变量通过缩小alpha-beta窗口实现
                    int eval = pvsEvaluation(possiblePath.get(i), beta - 1, beta, depth - 1, MAX_LAYER_SIGN);
                    if (eval <= beta - 1) {
                        // 进行全窗口索索
                        eval = pvsEvaluation(possiblePath.get(i), alpha, minEval, depth - 1, MAX_LAYER_SIGN);
                    }
                    minEval = Math.min(minEval, eval);
                }
                // 更新beta
                beta = Math.min(beta, minEval);
                if (beta <= alpha) {
                    break;
                }
            }
            // 恢复棋盘
            chessboard.undoMoveChess(path);
            return minEval;
        }
    }


    @Override
    public int calculateValue(ChessPath path, int depth) {
        this.depth = depth;
        return this.pvsEvaluation(path, ALPHA_INIT_VAL, BETA_INIT_VAL, depth, MIN_LAYER_SIGN);
    }


    @Override
    public int calculateValue(ChessPath path, int depth, int colorSign) {
        this.depth = depth;
        return this.pvsEvaluation(path, ALPHA_INIT_VAL, BETA_INIT_VAL, depth, colorSign);
    }


}

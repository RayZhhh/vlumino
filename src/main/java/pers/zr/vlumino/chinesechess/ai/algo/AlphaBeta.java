package pers.zr.vlumino.chinesechess.ai.algo;

import pers.zr.vlumino.chinesechess.ai.CChessboard;
import pers.zr.vlumino.chinesechess.ai.ChessPath;
import pers.zr.vlumino.chinesechess.ai.heuristic.AllPathAndSort;

import java.util.List;

public class AlphaBeta extends AlphaBetaTree {

    public AlphaBeta(CChessboard board) {
        super(board);
    }


    /**
     * 极大极小搜索，利用alpha-beta算法进行剪枝
     *
     * @param path      当前尝试的路径
     * @param alpha     剪枝变量alpha
     * @param beta      剪枝变量beta
     * @param depth     当前深度
     * @param colorSign 当前尝试棋子的类型
     * @return 经过搜索后得到的价值
     */
    public int alphaBetaEvaluation(ChessPath path, int alpha, int beta, int depth, int colorSign) {
        // 判断生死
        if (path.eat == -5) {
            return MAX_EVALUATE_VAL;
        } else if (path.eat == 5) {
            return MIN_EVALUATE_VAL;
        }
        // 到达深度
        if (depth == 1) {
            chessboard.moveChess(path);
            int val = chessboard.getOnBoardVal();
            chessboard.undoMoveChess(path);
            return val;
        }
        // 落子
        chessboard.moveChess(path);
        // 落子后先判断是否有对将情况
        if (isGeneralFaceToFace()) {
            if (colorSign == MAX_LAYER_SIGN) {
                chessboard.undoMoveChess(path);
                return MAX_EVALUATE_VAL;
            } else {
                chessboard.undoMoveChess(path);
                return MIN_EVALUATE_VAL;
            }
        }
        // max层
        if (colorSign == MAX_LAYER_SIGN) {
            int maxEval = MIN_EVALUATE_VAL;
            // 对手搜索我方可能的路径
            List<ChessPath> possiblePath = chessboard.getAllPath(MIN_LAYER_SIGN, new AllPathAndSort(this.chessboard));
            for (ChessPath chessPath : possiblePath) {
                int eval = alphaBetaEvaluation(chessPath, alpha, beta, depth - 1, MIN_LAYER_SIGN);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            // 恢复棋盘
            chessboard.undoMoveChess(path);
            return maxEval;
        } else { // min层
            int minEval = MAX_EVALUATE_VAL;
            // 搜索对手的可能路径
            List<ChessPath> possiblePath = chessboard.getAllPath(MAX_LAYER_SIGN, new AllPathAndSort(this.chessboard));
            for (ChessPath chessPath : possiblePath) {
                int eval = alphaBetaEvaluation(chessPath, alpha, beta, depth - 1, MAX_LAYER_SIGN);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
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
        return alphaBetaEvaluation(path, ALPHA_INIT_VAL, BETA_INIT_VAL, depth, MIN_LAYER_SIGN);
    }


    @Override
    public int calculateValue(ChessPath path, int depth, int colorSign) {
        return alphaBetaEvaluation(path, ALPHA_INIT_VAL, BETA_INIT_VAL, depth, colorSign);
    }
}

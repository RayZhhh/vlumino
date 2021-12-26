package pers.zr.vlumino.chinesechess.ai.algo;


import pers.zr.vlumino.chinesechess.ai.CChessboard;
import pers.zr.vlumino.chinesechess.ai.ChessPath;
import pers.zr.vlumino.chinesechess.ai.heuristic.AllEatPathWith_MVV_LVA;
import pers.zr.vlumino.chinesechess.ai.heuristic.AllPathAndSort;

import java.util.List;

/**
 * 静态搜索
 * 用于克服"水平线效应"
 */
public abstract class Quiescence extends AlphaBetaTreeWithMemory {

    protected Quiescence(CChessboard cChessboard) {
        super(cChessboard);
    }

    /**
     * 静态搜索，用于减少水平线效应带来的影响
     *
     * @param path      当前尝试的路径
     * @param beta      剪枝变量beta
     * @param maxDepth  搜索的最大深度（必须要限制，否则可能会搜索到很大的深度）
     * @param colorSign 当前尝试棋子的类型
     * @return 经过搜索后得到的价值
     */
    public int quiescenceEvaluation(ChessPath path, int alpha, int beta, int maxDepth, int colorSign) {
        // 判断生死
        if (path.eat == 5) {
            return MIN_EVALUATE_VAL;
        }
        if (path.eat == -5) {
            return MAX_EVALUATE_VAL;
        }
        // 到达搜索深度
        if (maxDepth == 1) {
            chessboard.moveChess(path);
            int val = chessboard.getOnBoardVal();
            chessboard.undoMoveChess(path);
            return val;
        }
        // 下棋
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
        // max
        if (colorSign == MAX_LAYER_SIGN) {
            List<ChessPath> chessPaths;
            // 如果正在被将军，生成所有路径
            if (isChecked(MIN_LAYER_SIGN)) {
                chessPaths = chessboard.getAllPath(MIN_LAYER_SIGN, new AllPathAndSort(chessboard));
            } else { // 如果没有被将军，只生成吃子的路径，并使用mvv/lva启发
                chessPaths = chessboard.getAllPath(MIN_LAYER_SIGN, new AllEatPathWith_MVV_LVA(chessboard));
            }
            // 如果结果为空，返回当前棋盘的价值
            if (chessPaths.size() == 0) {
                int val = chessboard.getOnBoardVal();
                chessboard.undoMoveChess(path);
                return val;
            }
            int maxEval = MIN_EVALUATE_VAL;
            for (ChessPath chessPath : chessPaths) {
                int eval = quiescenceEvaluation(chessPath, alpha, beta, maxDepth - 1, MIN_LAYER_SIGN);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, maxEval);
                if (beta <= alpha) {
                    break;
                }
            }
            chessboard.undoMoveChess(path);
            return maxEval;
        } else {
            List<ChessPath> chessPaths;
            // 如果正在被将军，生成所有路径
            if (isChecked(MAX_LAYER_SIGN)) {
                chessPaths = chessboard.getAllPath(MAX_LAYER_SIGN, new AllPathAndSort(chessboard));
            } else { // 如果没有被将军，只生成吃子路径，并使用mvv / lva启发
                chessPaths = chessboard.getAllPath(MAX_LAYER_SIGN, new AllEatPathWith_MVV_LVA(chessboard));
            }
            // 如果结果为空，返回当前棋盘的价值
            if (chessPaths.size() == 0) {
                int val = chessboard.getOnBoardVal();
                chessboard.undoMoveChess(path);
                return val;
            }
            int minEval = MAX_EVALUATE_VAL;
            for (ChessPath chessPath : chessPaths) {
                int eval = quiescenceEvaluation(chessPath, alpha, beta, maxDepth - 1, MAX_LAYER_SIGN);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, minEval);
                if (beta <= alpha) {
                    break;
                }
            }
            chessboard.undoMoveChess(path);
            return minEval;
        }
    }
}

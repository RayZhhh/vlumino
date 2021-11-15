package pers.zr.vlumino.chinesechess.ai.algo;

import pers.zr.vlumino.chinesechess.ai.CChessboard;
import pers.zr.vlumino.chinesechess.ai.ChessPath;
import pers.zr.vlumino.chinesechess.ai.heuristic.AllPathAndSort;

import java.util.List;
import java.util.Map;

public class AlphaBetaWithQuiescenceAndMemory extends Quiescence {

    public AlphaBetaWithQuiescenceAndMemory(CChessboard cChessboard) {
        super(cChessboard);
    }

    /**
     * 记忆化的alpha-beta搜索 + 静态搜索，并进行了置换表优化
     *
     * @param path      当前尝试的路径
     * @param alpha     剪枝变量alpha
     * @param beta      剪枝变量beta
     * @param depth     当前深度
     * @param colorSign 当前尝试棋子的类型
     * @param realDepth 真实深度
     * @return 经搜索后得到的价值
     */
    public int alphaBetaWithQuiescenceAndMemory(ChessPath path, int alpha, int beta, int depth, int colorSign, int realDepth) {
        // 判断生死
        if (path.eat == -5) {
            return MAX_EVALUATE_VAL;
        } else if (path.eat == 5) {
            return MIN_EVALUATE_VAL;
        }
        // 如果到达程序限定深度，强制进行返回
        if (realDepth == 1) {
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
        // -------------------- 查询置换表 ---------------------------------------------------
        int chHash = chessboard.hashCode();
        int chVer = chessboard.verifyCode();
        Map<Integer, TableMsg> mapVerTable = colorSign == MAX_LAYER_SIGN ?
                tranTableForMax.get(chHash)
                : tranTableForMin.get(chHash);
        TableMsg tableMsg;
        // 要求能查询到当前局面，且置换表中的深度要大于当前深度时才使用置换表
        if (mapVerTable != null && (tableMsg = mapVerTable.get(chVer)) != null) {
            if (tableMsg.loDepth >= depth) {
                if (tableMsg.lowerBound >= beta) {
                    chessboard.undoMoveChess(path);
                    return tableMsg.lowerBound;
                }
                alpha = Math.max(alpha, tableMsg.lowerBound);
            }
            if (tableMsg.upDepth >= depth) {
                if (tableMsg.upperBound <= alpha) {
                    chessboard.undoMoveChess(path);
                    return tableMsg.upperBound;
                }
                beta = Math.min(beta, tableMsg.upperBound);
            }
        }
        // -------------------- 查询置换表 ---------------------------------------------------
        // 搜索可能路径
        List<ChessPath> possiblePath = colorSign == MAX_LAYER_SIGN ?
                new AllPathAndSort(chessboard).selectPossiblePath(MIN_LAYER_SIGN)
                : new AllPathAndSort(chessboard).selectPossiblePath(MAX_LAYER_SIGN);
        // max层
        if (colorSign == MAX_LAYER_SIGN) {
            // 处理叶子节点
            if (depth == 1) {
                // 如果正在被将军，向下扩展一层，使用alpha-beta搜索
                if (isChecked(MIN_LAYER_SIGN) || isChecked(possiblePath)) {
                    depth = 2;
                } else if (path.eat != 0) { // 如果正在发生吃子，进行静态搜索
                    chessboard.undoMoveChess(path);
                    // 这里要调用 MAX_LAYER_SIGN，因为静态搜索要帮助当前层完成搜索
                    return super.quiescenceEvaluation(path, alpha, beta, MAX_DEPTH_RESTRICT - this.depth + depth, MAX_LAYER_SIGN);
                } else { // 如果没有上述两种局面，评估并返回结果
                    int val = chessboard.getOnBoardVal();
                    chessboard.undoMoveChess(path);
                    return val;
                }
            }
            int al = alpha;
            int maxEval = MIN_EVALUATE_VAL;
            for (ChessPath chessPath : possiblePath) {
                int eval = alphaBetaWithQuiescenceAndMemory(chessPath, al, beta, depth - 1, MIN_LAYER_SIGN, realDepth - 1);
                maxEval = Math.max(maxEval, eval);
                al = Math.max(al, maxEval);
                if (beta <= al) {
                    break;
                }
            }
            // ---------------------------------------------------------
            // 将可能的情况保存到置换表
            if (maxEval <= alpha) {
                upUpBound(maxEval, MAX_LAYER_SIGN, depth);
            }
            if (alpha < maxEval && maxEval < beta) {
                upLoBound(maxEval, MAX_LAYER_SIGN, depth);
                upUpBound(maxEval + 1, MAX_LAYER_SIGN, depth);
            }
            if (maxEval >= beta) {
                upLoBound(maxEval, MAX_LAYER_SIGN, depth);
            }
            // ---------------------------------------------------------
            // 恢复棋盘
            chessboard.undoMoveChess(path);
            return maxEval;
        } else { // min层
            // 处理叶子节点
            if (depth == 1) {
                // 如果正在被将军，向下扩展一层，使用alpha-beta搜索
                if (isChecked(MAX_LAYER_SIGN) || isChecked(possiblePath)) {
                    depth = 2;
                } else if (path.eat != 0) { // 如果正在发生吃子，进行静态搜索
                    chessboard.undoMoveChess(path);
                    // 这里要调用 MIN_LAYER_SIGN，因为静态搜索要帮助当前层完成搜索
                    return super.quiescenceEvaluation(path, alpha, beta, MAX_DEPTH_RESTRICT - this.depth + depth, MIN_LAYER_SIGN);
                } else { // 如果没有上述两种局面，评估并返回结果
                    int val = chessboard.getOnBoardVal();
                    chessboard.undoMoveChess(path);
                    return val;
                }
            }
            int be = beta;
            int minEval = MAX_EVALUATE_VAL;
            List<ChessPath> allPath = chessboard.getAllPath(MAX_LAYER_SIGN, new AllPathAndSort(this.chessboard));
            for (ChessPath chessPath : allPath) {
                int eval = alphaBetaWithQuiescenceAndMemory(chessPath, alpha, be, depth - 1, MAX_LAYER_SIGN, realDepth - 1);
                minEval = Math.min(minEval, eval);
                be = Math.min(be, minEval);
                if (be <= alpha) {
                    break;
                }
            }
            // --------------------------------------------------------
            // 将可能的情况保存到置换表
            if (minEval <= alpha) {
                upUpBound(minEval, MIN_LAYER_SIGN, depth);
            }
            if (alpha < minEval && minEval < beta) {
                upLoBound(minEval, MIN_LAYER_SIGN, depth);
                upUpBound(minEval + 1, MIN_LAYER_SIGN, depth);
            }
            if (minEval >= beta) {
                upLoBound(minEval, MIN_LAYER_SIGN, depth);
            }
            // --------------------------------------------------------
            // 恢复棋盘
            chessboard.undoMoveChess(path);
            return minEval;
        }
    }

    @Override
    public int calculateValue(ChessPath path, int depth) {
        return this.alphaBetaWithQuiescenceAndMemory(path, ALPHA_INIT_VAL, BETA_INIT_VAL, depth, MIN_LAYER_SIGN, MAX_DEPTH_RESTRICT);
    }

    @Override
    public int calculateValue(ChessPath path, int depth, int colorSign) {
        return this.alphaBetaWithQuiescenceAndMemory(path, ALPHA_INIT_VAL, BETA_INIT_VAL, depth, colorSign, MAX_DEPTH_RESTRICT);
    }
}

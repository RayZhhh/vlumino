package pers.zr.vlumino.chinesechess.ai.algo;


import pers.zr.vlumino.chinesechess.ai.CChessboard;
import pers.zr.vlumino.chinesechess.ai.ChessPath;
import pers.zr.vlumino.chinesechess.ai.heuristic.AllPathAndSort;

import java.util.List;
import java.util.Map;

public class AlphaBetaWithMemory extends AlphaBetaTreeWithMemory {

    public AlphaBetaWithMemory(CChessboard cChessboard) {
        super(cChessboard);
    }

    /**
     * 记忆化的alpha-beta搜索
     *
     * @param path      当前尝试的路径
     * @param alpha     剪枝变量alpha
     * @param beta      剪枝变量beta
     * @param depth     当前深度
     * @param colorSign 当前尝试棋子的类型
     * @return 经搜索后得到的价值
     */
    public int alphaBetaWithMemory(ChessPath path, int alpha, int beta, int depth, int colorSign) {
        // 判断是否游戏结束
        if (path.eat == 5) {
            return MIN_EVALUATE_VAL;
        } else if (path.eat == -5) {
            return MAX_EVALUATE_VAL;
        }
        // 到达搜索深度
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
        // max
        if (colorSign == MAX_LAYER_SIGN) {
            int al = alpha;
            int maxEval = MIN_EVALUATE_VAL;
            List<ChessPath> allPath = chessboard.getAllPath(MIN_LAYER_SIGN, new AllPathAndSort(this.chessboard));
            for (ChessPath chessPath : allPath) {
                int eval = alphaBetaWithMemory(chessPath, al, beta, depth - 1, MIN_LAYER_SIGN);
                maxEval = Math.max(maxEval, eval);
                al = Math.max(al, maxEval);
                if (beta <= al) {
                    break;
                }
            }
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
            chessboard.undoMoveChess(path);
            return maxEval;
        } else { // min
            int be = beta;
            int minEval = MAX_EVALUATE_VAL;
            List<ChessPath> allPath = chessboard.getAllPath(MAX_LAYER_SIGN, new AllPathAndSort(this.chessboard));
            for (ChessPath chessPath : allPath) {
                int eval = alphaBetaWithMemory(chessPath, alpha, be, depth - 1, MAX_LAYER_SIGN);
                minEval = Math.min(minEval, eval);
                be = Math.min(be, minEval);
                if (be <= alpha) {
                    break;
                }
            }
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
            chessboard.undoMoveChess(path);
            return minEval;
        }
    }

    @Override
    public int calculateValue(ChessPath path, int depth) {
        return this.alphaBetaWithMemory(path, ALPHA_INIT_VAL, BETA_INIT_VAL, depth, MIN_LAYER_SIGN);
    }

    @Override
    public int calculateValue(ChessPath path, int depth, int colorSign) {
        return this.alphaBetaWithMemory(path, ALPHA_INIT_VAL, BETA_INIT_VAL, depth, colorSign);
    }
}

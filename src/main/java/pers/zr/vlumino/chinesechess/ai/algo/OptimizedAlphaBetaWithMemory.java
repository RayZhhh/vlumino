package pers.zr.vlumino.chinesechess.ai.algo;

import pers.zr.vlumino.chinesechess.ai.CChessboard;
import pers.zr.vlumino.chinesechess.ai.ChessPath;
import pers.zr.vlumino.chinesechess.ai.ChessWeights;
import pers.zr.vlumino.chinesechess.ai.heuristic.AllPathAndSort;

import java.util.List;
import java.util.Map;

public class OptimizedAlphaBetaWithMemory extends AlphaBetaTreeWithMemory {


    protected OptimizedAlphaBetaWithMemory(CChessboard cChessboard) {
        super(cChessboard);
    }


    /**
     * 记忆化的alpha-beta搜索，在评估中进行了优化
     *
     * @param path      当前尝试的路径
     * @param alpha     剪枝变量alpha
     * @param beta      剪枝变量beta
     * @param depth     当前深度
     * @param colorSign 当前尝试棋子的类型
     * @return 经搜索后得到的价值
     */
    public int alphaBetaWithMemory_ValueOptimized(
            ChessPath path, int alpha, int beta, int depth, int colorSign, int curValue, int chHash, int chVer) {
        // 判断是否游戏结束
        if (path.eat == 5) {
            return MIN_EVALUATE_VAL - depth;
        } else if (path.eat == -5) {
            return MAX_EVALUATE_VAL + depth;
        }
        // 到达搜索深度
        if (depth == 1) {
            return curValue;
        }
        // 落子
        chessboard.moveChess(path);
        // 落子后先判断是否有对将情况
        if (isGeneralFaceToFace()) {
            if (colorSign == MAX_LAYER_SIGN) {
                chessboard.undoMoveChess(path);
                return MAX_EVALUATE_VAL + depth;
            } else {
                chessboard.undoMoveChess(path);
                return MIN_EVALUATE_VAL - depth;
            }
        }
        // -------------------- 查询置换表 ---------------------------------------------------
        //int chHash = chessboard.hashCode();
        //int chVer = chessboard.verifyCode();
        Map<Integer, TableMsg> mapVerTable = colorSign == MAX_LAYER_SIGN ?
                tranTableForMax.get(chHash)
                : tranTableForMin.get(chHash);
        TableMsg tableMsg;
        // 要求能查询到当前局面，且置换表中的深度要大于当前深度时才使用置换表
        if (mapVerTable != null && (tableMsg = mapVerTable.get(chVer)) != null) {
            if (tableMsg.loDepth <= depth) {
                if (tableMsg.lowerBound >= beta) {
                    chessboard.undoMoveChess(path);
                    return tableMsg.lowerBound;
                }
                alpha = Math.max(alpha, tableMsg.lowerBound);
            }
            if (tableMsg.upDepth <= depth) {
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
                int eval = alphaBetaWithMemory_ValueOptimized(
                        chessPath, al, beta, depth - 1, MIN_LAYER_SIGN, curValue + chessPath.value,
                        getNewZobrist(chessPath, chHash), getNewVerify(chessPath, chVer)
                );
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
                int eval = alphaBetaWithMemory_ValueOptimized(
                        chessPath, alpha, be, depth - 1, MAX_LAYER_SIGN, curValue + chessPath.value,
                        getNewZobrist(chessPath, chHash), getNewVerify(chessPath, chVer)
                );
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


    protected int getCurValueParameter(ChessPath path) {
        int id = chessboard.innerChessboard[path.fromX][path.fromY];
        int eatVal = path.eat == 0 ? 0 : ChessWeights.getPosVal(path.eat, path.toX, path.toY);
        int after = ChessWeights.getPosVal(id, path.toX, path.toY);
        int before = ChessWeights.getPosVal(id, path.fromX, path.fromY);
        path.value = after - before - eatVal;
        return chessboard.getOnBoardVal() + path.value;
    }


    @Override
    public int calculateValue(ChessPath path, int depth) {
        int curVal = getCurValueParameter(path);
        return this.alphaBetaWithMemory_ValueOptimized(
                path, ALPHA_INIT_VAL, BETA_INIT_VAL, depth, MIN_LAYER_SIGN, curVal,
                getNewZobrist(path, chessboard.hashCode()), getNewVerify(path, chessboard.verifyCode())
        );
    }

    @Override
    public int calculateValue(ChessPath path, int depth, int colorSign) {
        int curVal = getCurValueParameter(path);
        return this.alphaBetaWithMemory_ValueOptimized(
                path, ALPHA_INIT_VAL, BETA_INIT_VAL, depth, colorSign, curVal,
                getNewZobrist(path, chessboard.hashCode()), getNewVerify(path, chessboard.verifyCode())
        );
    }
}

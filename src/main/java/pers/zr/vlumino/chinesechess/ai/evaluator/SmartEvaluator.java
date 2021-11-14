package pers.zr.vlumino.chinesechess.ai.evaluator;

import pers.zr.vlumino.chinesechess.ai.CChessboard;
import pers.zr.vlumino.chinesechess.ai.ChessPath;
import pers.zr.vlumino.chinesechess.ai.algo.*;
import pers.zr.vlumino.chinesechess.ai.gamebit.GameBitSearcher;

public class SmartEvaluator extends MultiThreadEvaluator {

    public SmartEvaluator(CChessboard board) {
        super(board, TreeType.MTDF);
    }

    public void setChessboard(CChessboard chessboard) {
        this.cChessboard = chessboard;
    }

    public SmartEvaluator(CChessboard board, int depth) {
        super(board, TreeType.MTDF);
        super.depth = depth;
        this.curDepth = depth;
    }

    /**
     * SmartEvaluator的使用者设置的深度，默认为8
     */
    private int curDepth = 8;

    /**
     * 是否使用开局库搜索
     */
    public boolean useGambit = true;

    @Override
    public ChessPath getBestPath(int colorSign) {
        // 先进行开局库搜索
        if (useGambit) {
            ChessPath pathFromGameBit = new GameBitSearcher(super.cChessboard).getPathFromGameBit();
            if (pathFromGameBit != null) {
                System.out.println("评估参数：开局库搜索");
                return pathFromGameBit;
            }
        }
        // 获得棋盘上棋子的数量
        int chessNum = this.cChessboard.chessNumOnBoard();
        if (chessNum >= 18) {
            super.depth = curDepth;
            super.treeType = TreeType.MTDF;
        } else if (chessNum >= 13) {
            super.depth = curDepth;
            super.treeType = TreeType.MTDF;
        } else if (chessNum >= 10) {
            super.depth = curDepth + 2;
            super.treeType = TreeType.MTDF;
        } else {
            super.depth = curDepth + 4;
            super.treeType = TreeType.MTDF;
        }
        return super.getBestPath(colorSign);
    }

    @Override
    public ChessPath getBestPath() {
        return this.getBestPath(AlphaBetaTree.MAX_LAYER_SIGN);
    }

}

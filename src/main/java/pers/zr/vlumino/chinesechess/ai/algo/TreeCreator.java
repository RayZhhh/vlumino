package pers.zr.vlumino.chinesechess.ai.algo;

import pers.zr.vlumino.chinesechess.ai.CChessboard;

public class TreeCreator {
    /**
     * 获得评估所选用的树类型
     *
     * @return 树搜索对象
     */
    public static AlphaBetaTree newTree(TreeType treeType, CChessboard cChessboard) {
        if (treeType == TreeType.ALPHA_BETA) {
            return new AlphaBeta(cChessboard);
        }
        if (treeType == TreeType.PVS) {
            return new PVS(cChessboard);
        }
        if (treeType == TreeType.ALPHA_BETA_WITH_MEMORY) {
            return new AlphaBetaWithMemory(cChessboard);
        }
        if (treeType == TreeType.QUIESCENCE) {
            return new AlphaBetaWithQuiescence(cChessboard);
        }
        if (treeType == TreeType.MTDF) {
            return new MTDF(cChessboard);
        }
        if (treeType == TreeType.ITER_DEEPENING) {
            return new IterDeepening(cChessboard);
        }
        if (treeType == TreeType.QUIESCENCE_WITH_MEMORY) {
            return new AlphaBetaWithQuiescenceAndMemory(cChessboard);
        }
        if (treeType == TreeType.MTDF_QUIESCENCE) {
            return new MTDF_Quiscence(cChessboard);
        }
        if (treeType == TreeType.OPT_AB_WITH_MEM) {
            return new OptimizedAlphaBetaWithMemory(cChessboard);
        }
        if (treeType == TreeType.MTDF_OPT) {
            return new MTDF_Opt(cChessboard);
        }
        return null;
    }
}

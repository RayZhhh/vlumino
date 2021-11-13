package pers.zr.vlumino.chinesechess.ai.algo;

import pers.zr.vlumino.chinesechess.ai.CChessboard;
import pers.zr.vlumino.chinesechess.ai.ChessPath;
import pers.zr.vlumino.chinesechess.ai.heuristic.AllPath;

import java.util.List;

public abstract class AlphaBetaTree implements TreeSearching {

    protected AlphaBetaTree(CChessboard cChessboard) {
        this.chessboard = new CChessboard(cChessboard);
    }

    /**
     * 剪枝变量alpha的初始值
     */
    public final static int ALPHA_INIT_VAL = -9999;

    /**
     * 剪枝变量beta的初始值
     */
    public final static int BETA_INIT_VAL = 9999;

    /**
     * 局面评分的最大值（AI获胜情况）
     */
    public final static int MAX_EVALUATE_VAL = 8888;

    /**
     * 局面评分的最小值（AI失败的情况）
     */
    public final static int MIN_EVALUATE_VAL = -8888;

    /**
     * 极小评估层
     */
    public final static int MIN_LAYER_SIGN = 1;

    /**
     * 极大评估层
     */
    public final static int MAX_LAYER_SIGN = -1;

    /**
     * 静态搜索的深度限制
     */
    public static int MAX_DEPTH_RESTRICT = 12;

    /**
     * 内置棋盘对象
     */
    protected CChessboard chessboard;

    /**
     * 搜索深度
     */
    protected int depth;

    /**
     * 获得路径评分
     *
     * @param path  要评估的路径
     * @param depth 评估深度
     * @return 在当前盘面下当前走法的评分
     */
    public abstract int calculateValue(ChessPath path, int depth);

    /**
     * 获得路径评分
     *
     * @param path      要评估的路径
     * @param depth     评估深度
     * @param colorSign 棋子的颜色（评估对手/自己）
     * @return 在当前盘面下当前走法的评分
     */
    public abstract int calculateValue(ChessPath path, int depth, int colorSign);

    /**
     * 检查当前所有路径中是否有将军的情况
     *
     * @param paths
     * @return
     */
    public boolean isChecked(List<ChessPath> paths) {
        for (ChessPath path : paths) {
            if (path.eat == 5 || path.eat == -5) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查当前棋子颜色是否有被将军的情况
     *
     * @param colorSign
     * @return
     */
    public boolean isChecked(int colorSign) {
        List<ChessPath> allPath = chessboard.getAllPath(-colorSign, new AllPath(this.chessboard));
        for (ChessPath chessPath : allPath) {
            if (chessPath.eat == colorSign * 5) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否存在对将情况
     *
     * @return
     */
    public boolean isGeneralFaceToFace() {
        int rivalX = 0;
        int rivalY = 0;
        for (int i = 0; i <= 2; i++) {
            boolean findFlag = false;
            for (int j = 3; j <= 5; j++) {
                if (chessboard.innterChessboard[i][j] == -5) {
                    rivalX = i;
                    rivalY = j;
                    findFlag = true;
                }
                if (findFlag) {
                    break;
                }
            }
        }
        boolean isFind = false;
        int selfX = 0;
        for (int i = 7; i <= 9; i++) {
            if (chessboard.innterChessboard[i][rivalY] == 5) {
                isFind = true;
                selfX = i;
                break;
            }
        }
        // 如果自己的将和对方的将不再一条线上
        if (!isFind) {
            return false;
        }
        for (int i = rivalX + 1; i < selfX; i++) {
            if (chessboard.innterChessboard[i][rivalY] != 0) {
                return false;
            }
        }
        return true;
    }
}

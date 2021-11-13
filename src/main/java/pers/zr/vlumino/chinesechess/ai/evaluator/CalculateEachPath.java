package pers.zr.vlumino.chinesechess.ai.evaluator;

import pers.zr.vlumino.chinesechess.ai.ChessPath;
import pers.zr.vlumino.chinesechess.ai.algo.AlphaBetaTree;
import pers.zr.vlumino.chinesechess.ai.algo.TreeSearching;

public class CalculateEachPath implements Runnable {

    private int depth;

    private ChessPath chessPath;

    private TreeSearching search;

    private int colorSign;

    public CalculateEachPath(ChessPath path, int depth, TreeSearching ts, int colorSign) {
        this.chessPath = path;
        this.depth = depth;
        this.search = ts;
        this.colorSign = colorSign;
    }


    @Override
    public void run() {
        long begin = System.currentTimeMillis();
        this.chessPath.value = this.search.calculateValue(this.chessPath, this.depth, this.colorSign);
        long end = System.currentTimeMillis();
        System.out.println("线程运行时间：" + (end - begin) / 1000 + "s  " + chessPath);
    }
}
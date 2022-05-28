package pers.zr.vlumino.chinesechess.ai.evaluator;

import pers.zr.vlumino.chinesechess.ai.CChessboard;
import pers.zr.vlumino.chinesechess.ai.ChessPath;
import pers.zr.vlumino.chinesechess.ai.algo.*;
import pers.zr.vlumino.chinesechess.ai.heuristic.AllPathAndSort;

import java.util.ArrayList;
import java.util.List;


public class MultiThreadEvaluator implements ChineseChessEvaluator {

    /**
     * 搜索深度
     */
    protected int depth;

    /**
     * 内置棋盘
     */
    protected CChessboard cChessboard;

    /**
     * 是否开启随机落子
     * 如果开启，当前评估结果中有相同的权值的路径进行随机选择
     */
    protected boolean random;

    /**
     * 树类型
     */
    protected TreeType treeType;

    /**
     * 构造
     *
     * @param board    待评估棋盘
     * @param treeType 所选搜索树类型
     */
    public MultiThreadEvaluator(CChessboard board, TreeType treeType) {
        this.cChessboard = new CChessboard(board);
        this.depth = 8;
        this.random = false;
        this.treeType = treeType;
    }

    /**
     * 构造
     *
     * @param board    待评估棋盘
     * @param depth    搜索深度
     * @param treeType 使用搜索树类型
     */
    public MultiThreadEvaluator(CChessboard board, int depth, TreeType treeType) {
        this.cChessboard = new CChessboard(board);
        this.depth = depth;
        this.random = false;
        this.treeType = treeType;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getDepth() {
        return depth;
    }

    public boolean isRandom() {
        return this.random;
    }

    /**
     * 开启随机
     */
    public void openRandom() {
        this.random = true;
    }

    /**
     * 关闭随机
     */
    public void closeRandom() {
        this.random = false;
    }

    @Override
    public ChessPath getBestPath() {
        return this.getBestPath(AlphaBetaTree.MAX_LAYER_SIGN);
    }

    @Override
    public ChessPath getBestPath(int color) {
        long begin = System.currentTimeMillis();
        List<Thread> threads = new ArrayList<>();
        List<ChessPath> chessPaths;
        // 所有路径
        chessPaths = new AllPathAndSort(this.cChessboard).selectPossiblePath(
                color == AlphaBetaTree.MAX_LAYER_SIGN ? AlphaBetaTree.MIN_LAYER_SIGN : AlphaBetaTree.MAX_LAYER_SIGN
        );
        // 进行多线程搜索
        try {
            for (ChessPath chessPath : chessPaths) {
                threads.add(new Thread(new CalculateEachPath(chessPath, this.depth, TreeCreator.newTree(this.treeType, this.cChessboard), color == AlphaBetaTree.MAX_LAYER_SIGN ? AlphaBetaTree.MIN_LAYER_SIGN : AlphaBetaTree.MAX_LAYER_SIGN)));
            }
            for (Thread thread : threads) {
                thread.start();
            }
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        chessPaths.sort(((o1, o2) -> {
            return color == AlphaBetaTree.MAX_LAYER_SIGN ?
                    o2.value - o1.value
                    : o1.value - o2.value;
        }));
        // 打印路径和价值
        for (ChessPath chessPath : chessPaths) {
            System.out.println(chessPath + " " + chessPath.value);
        }
        long end = System.currentTimeMillis();
        if (!random) {
            System.out.println("时间：" + (end - begin) / 1000);
            System.out.println("评估参数：depth=" + depth + " Tree Type=" + treeType);
            return chessPaths.get(0);
        }
        // 找到价值相等的，进行随机落子
        int maxVal = chessPaths.get(0).value;
        int pos = 1;
        for (; pos < chessPaths.size(); pos++) {
            if (chessPaths.get(pos).value != maxVal) {
                break;
            }
        }
        int ranPos = (int) (Math.random() * 1000) % pos;
        System.out.println("时间：" + (end - begin) / 1000);
        System.out.println("评估参数：depth=" + depth + " Tree Type=" + treeType);
        return chessPaths.get(ranPos);
    }
}




package pers.zr.vlumino.chinesechess.ai.evaluator;

import pers.zr.vlumino.chinesechess.ai.CChessboard;
import pers.zr.vlumino.chinesechess.ai.ChessPath;
import pers.zr.vlumino.chinesechess.ai.algo.IterDeepening;
import pers.zr.vlumino.chinesechess.ai.heuristic.AllPathAndSort;
import pers.zr.vlumino.chinesechess.ai.algo.AlphaBetaTree;

import java.util.List;

/**
 * 这个类仅用于测试
 */
@Deprecated
public class SingleThreadEvaluator {

    AlphaBetaTree alphaBetaTree;

    CChessboard cChessboard;

    public SingleThreadEvaluator(CChessboard board) {
        this.cChessboard = board;
        //this.treeSearching = ts;
    }

    public ChessPath getBest() {
        List<ChessPath> chessPaths = new AllPathAndSort(cChessboard).selectPossiblePath(1);
        IterDeepening id = new IterDeepening(cChessboard);
        //TreeSearchingWithMemory.loadTable();
        long begin = System.currentTimeMillis();
        for (ChessPath chessPath : chessPaths) {
            chessPath.value = id.calculateValue(chessPath, 8);
            System.out.println(chessPath);
        }
        //TreeSearchingWithMemory.saveTable();
        chessPaths.sort((o1, o2) -> o2.value - o1.value);
        for (ChessPath chessPath : chessPaths) {
            System.out.println(chessPath + " " + chessPath.value);
        }
        long end = System.currentTimeMillis();
        System.out.println("运行时间：" + (end - begin) / 1000 + "s");
        return chessPaths.get(0);
    }
}

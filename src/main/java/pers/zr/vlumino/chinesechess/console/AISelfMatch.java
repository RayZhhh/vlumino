package pers.zr.vlumino.chinesechess.console;

import pers.zr.vlumino.chinesechess.ai.CChessboard;
import pers.zr.vlumino.chinesechess.ai.ChessPath;
import pers.zr.vlumino.chinesechess.ai.algo.AlphaBetaTree;
import pers.zr.vlumino.chinesechess.ai.evaluator.MultiThreadEvaluator;
import pers.zr.vlumino.chinesechess.ai.evaluator.SmartEvaluator;
import pers.zr.vlumino.chinesechess.ai.algo.TreeType;

/**
 * AI进行对战，用于进行算法
 */
public class AISelfMatch {

    CChessboard cChessboard = new CChessboard();

    static int step = 0;

    public void startSelfMatch() {
        cChessboard.printChessboard();
        // 第一手棋我们帮他走
        cChessboard.moveChess(new ChessPath("21240"));
        cChessboard.printChessboard();
        while (true) {
            if (step++ == 200) {
                System.out.println("和棋");
                break;
            }
            // ai_后手
            ChessPath best_1 = new MultiThreadEvaluator(cChessboard, 6, TreeType.MTDF).getBestPath(AlphaBetaTree.MAX_LAYER_SIGN);
            //ChessPath best_1 = new SmartEvaluator(cChessboard).getBestPath();
            cChessboard.moveChess(best_1);
            System.out.println("后手落子：" + best_1);
            cChessboard.printChessboard();
            if (best_1.eat == -5) {
                System.out.println("后手赢");
                break;
            }
            // ai_先手
            ChessPath best_2 = new MultiThreadEvaluator(cChessboard, 8, TreeType.MTDF).getBestPath(AlphaBetaTree.MIN_LAYER_SIGN);
            cChessboard.moveChess(best_2);
            System.out.println("先手落子：" + best_2);
            cChessboard.printChessboard();
            if (best_2.eat == 5) {
                System.out.println("先手赢");
                break;
            }
        }
    }

    public static void main(String[] args) {
        new AISelfMatch().startSelfMatch();
    }
}

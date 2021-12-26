package pers.zr.vlumino.chinesechess.console;

import pers.zr.vlumino.chinesechess.ai.CChessboard;
import pers.zr.vlumino.chinesechess.ai.ChessPath;
import pers.zr.vlumino.chinesechess.ai.ChessWeights;
import pers.zr.vlumino.chinesechess.ai.algo.AlphaBetaTreeWithMemory;
import pers.zr.vlumino.chinesechess.ai.evaluator.MultiThreadEvaluator;
import pers.zr.vlumino.chinesechess.ai.algo.TreeType;

import java.util.Scanner;

/**
 * 控制台测试
 */
public class ConsoleTest {

    public static ChessPath convertPath(String p) {
        int fx = Integer.parseInt(String.valueOf(p.charAt(0)));
        int fy = Integer.parseInt(String.valueOf(p.charAt(1)));
        int tx = Integer.parseInt(String.valueOf(p.charAt(2)));
        int ty = Integer.parseInt(String.valueOf(p.charAt(3)));
        int eat = Integer.parseInt(String.valueOf(p.charAt(4)));
        return new ChessPath(fx, fy, tx, ty, eat);
    }

    public void setChess(int x, int y, int chess) {
        this.cChessboard.innterChessboard[x][y] = chess;
    }

//    public void selfPlay() {
//        int i = 0;
//        this.cChessboard.printChessboard();
//        while (i++ < 50) {
//            ChessPath best = new MultiThreadEvaluator(cChessboard, 8, TreeType.MTDF).getBestPathWithTreeSearching();
//            cChessboard.moveChess(best);
//            best = new MultiThreadEvaluator(cChessboard, 8, TreeType.MTDF).getBestPathWithTreeSearching(TreeSearching.MAX_EVALUATE_VAL);
//
//        }
//    }

    public void importVluminoProb() {
        setChess(0, 3, -5);
        setChess(1, 4, -4);
        setChess(2, 3, -6);
        setChess(2, 5, -4);
        setChess(3, 3, 2);
        setChess(3, 5, 7);
        setChess(4, 0, -7);
        setChess(5, 2, 3);
        setChess(5, 4, 6);
        setChess(6, 0, 7);
        setChess(7, 4, 3);
        setChess(7, 5, 5);
        setChess(8, 4, 4);
        setChess(9, 3, 4);
    }

    CChessboard cChessboard = new CChessboard();

    public void runCChess() {
        //TreeSearchingWithMemory.loadTable();
        //
//        List<ChessPath> paths = new ArrayList<>();
//        paths.add(convertPath("21240"));
//        paths.add(convertPath("71740"));
//        paths.add(convertPath("01220"));
//        paths.add(convertPath("97760"));
//        paths.add(convertPath("00010"));
//        paths.add(convertPath("91720"));
//        paths.add(convertPath("36460"));
//        paths.add(convertPath("90910"));
//        paths.add(convertPath("01910"));
//        paths.add(convertPath("72910"));
//        //paths.add(convertPath("32420"));
//        cChessboard.doMoves(paths);
        //cChessboard.recoverChessboard("0;0;0;-4;-5;-4;0;0;-1;0;0;0;0;0;0;0;0;0;0;0;0;0;-3;0;0;0;-3;6;0;0;0;-2;0;0;0;-7;0;0;-7;0;7;0;-7;0;0;0;0;0;0;0;0;0;1;0;7;0;-2;0;0;0;7;0;7;0;0;2;0;0;-6;2;0;0;0;0;0;0;0;0;0;0;0;0;0;3;4;5;4;3;0;0");

//        cChessboard.clearAllChessOnBoard();
//        importVluminoProb();


        /**
         *
         */
//        cChessboard.getOnBoardVal();
//        ChessPath path = convertPath("21240");
//        cChessboard.moveChess(path);
//        cChessboard.getOnBoardVal();
//        int id = cChessboard.innterChessboard[path.fromX][path.fromY];
//        int eatVal = ChessWeights.getPosVal(path.eat, path.toX, path.toY);
//        int after = ChessWeights.getPosVal(id, path.toX, path.toY);
//        int before = ChessWeights.getPosVal(id, path.fromX, path.fromY);
//        System.out.println("before after" + ());
        /**
         *
         */



        cChessboard.printChessboard();
        while (true) {
            // player
            String path = new Scanner(System.in).next();
            if ("exit".equals(path)) {
                //TreeSearchingWithMemory.saveTable();
                break;
            }
            if ("arr".equals(path)) {
                System.out.println(cChessboard.getBoardArr());
                continue;
            }
            if ("load".equals(path)) {
                System.out.println("输入序列");
                String arr = new Scanner(System.in).next();
                cChessboard.recoverChessboard(arr);
                cChessboard.printChessboard();
                continue;
            }
            cChessboard.moveChess(convertPath(path));
            cChessboard.printChessboard();
            // ai
//            AlphaBetaTreeWithMemory.clearTable();
//            AlphaBetaTreeWithMemory.loadTable();
            ChessPath best = new MultiThreadEvaluator(cChessboard, 8, TreeType.MTDF).getBestPath();
            //AlphaBetaTreeWithMemory.saveTable();
            //ChessPath best = new SingleThreadEvaluator(cChessboard).getBest();
            //ChessPath best = new MultiThreadEvaluator(cChessboard, 8, TreeType.ALPHA_BETA_WITH_MEMORY).getBestPathWithTreeSearching();
            cChessboard.moveChess(best);
            cChessboard.printChessboard();
        }

    }


    public static void main(String[] args) {
        new ConsoleTest().runCChess();
    }
}

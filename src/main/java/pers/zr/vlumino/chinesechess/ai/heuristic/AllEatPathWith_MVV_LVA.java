package pers.zr.vlumino.chinesechess.ai.heuristic;

import pers.zr.vlumino.chinesechess.ai.CChessboard;
import pers.zr.vlumino.chinesechess.ai.ChessPath;
import pers.zr.vlumino.chinesechess.ai.ChessWeights;

import java.util.List;


/**
 * 按照 MVV/LVA 启发方式排序
 * 最有价值受害者 / 最无价值攻击者
 * Most Valuable Victim / Least Valuable Attacker
 */
public class AllEatPathWith_MVV_LVA extends AllEatPath {

    public AllEatPathWith_MVV_LVA(CChessboard board) {
        super(board);
    }


    @Override
    public List<ChessPath> selectPossiblePath(int colorSign) {
        List<ChessPath> chessPaths = super.selectPossiblePath(colorSign);
        chessPaths.sort((o1, o2) -> {
            int eatVal1 = -colorSign * ChessWeights.getPosVal(o1.eat, o1.toX, o1.toY);
            int eatVal2 = -colorSign * ChessWeights.getPosVal(o2.eat, o2.toX, o2.toY);
            // 如果吃子不同，那么按照吃子价值降序排列
            if (eatVal1 != eatVal2) {
                return eatVal2 - eatVal1;
            } else { // 如果吃子相同，那么按照自身价值升序排列
                int selfId1 = cChessboard.innerChessboard[o1.fromX][o1.fromY];
                int selfVal1 = colorSign * ChessWeights.getPosVal(selfId1, o1.toX, o1.toY);
                int selfId2 = colorSign * cChessboard.innerChessboard[o2.fromX][o2.fromY];
                int selfVal2 = ChessWeights.getPosVal(selfId2, o2.toX, o2.toY);
                return selfVal1 - selfVal2;
            }
        });
        return chessPaths;
    }


    private int mvvVal(ChessPath path) {
        int eatVal = -ChessWeights.getPosVal(path.eat, path.toX, path.toY);
        int selfId = cChessboard.innerChessboard[path.fromX][path.fromY];
        int selfVal = ChessWeights.getPosVal(selfId, path.toX, path.toY);
        // 受害者 - 攻击者
        return eatVal / selfVal;
    }


    public static void main(String[] args) {
        CChessboard board = new CChessboard();
        List<ChessPath> allPath = board.getAllPath(-1, new AllEatPathWith_MVV_LVA(board));
        for (ChessPath chessPath : allPath) {
            System.out.println(chessPath);
        }

    }
}

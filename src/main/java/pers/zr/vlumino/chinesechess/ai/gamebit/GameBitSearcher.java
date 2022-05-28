package pers.zr.vlumino.chinesechess.ai.gamebit;

import pers.zr.vlumino.chinesechess.ai.CChessboard;
import pers.zr.vlumino.chinesechess.ai.ChessPath;

public class GameBitSearcher extends GameBitPathGenerator {

    public GameBitSearcher(CChessboard board) {
        this.baord = new CChessboard(board);
    }


    public CChessboard baord;


    public ChessPath getPathFromGameBit() {
        loadGameBitTable();
        GameBitTable table = findTable();
        if (table == null) {
            return null;
        }
        int len = table.paths.size();
        int pos = (int) (Math.random() * 1000) % len;
        ChessPath chessPath = new ChessPath(table.paths.get(pos) + "0");
        chessPath.eat = baord.innerChessboard[chessPath.toX][chessPath.toY];
        return chessPath;
    }


    private GameBitTable findTable() {
        GameBitTable gameBitTable = gameBitTableMap.get(baord.hashCode());
        if (gameBitTable != null && gameBitTable.verifyCode == baord.verifyCode()) {
            return gameBitTable;
        }
        return null;
    }

    public static void main(String[] args) {
        CChessboard cChessboard = new CChessboard();
        cChessboard.moveChess(new ChessPath("21240"));
        System.out.println(new GameBitSearcher(cChessboard).getPathFromGameBit());
    }
}

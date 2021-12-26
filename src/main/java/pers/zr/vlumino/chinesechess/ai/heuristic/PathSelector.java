package pers.zr.vlumino.chinesechess.ai.heuristic;

import pers.zr.vlumino.chinesechess.ai.CChessboard;
import pers.zr.vlumino.chinesechess.ai.ChessPath;
import pers.zr.vlumino.chinesechess.ai.ChessWeights;

public abstract class PathSelector implements Selector {

    protected CChessboard cChessboard;


    public PathSelector(CChessboard board) {
        this.cChessboard = board;
    }


    public PathSelector() {
        this.init();
    }


    private void init() {
        this.cChessboard = new CChessboard();
    }


    public void setcChessboard(CChessboard cChessboard) {
        this.cChessboard = cChessboard;
    }

    /**
     * 计算每个路径在棋盘上的增长分数
     * @param path
     * @return
     */
    protected int getPathValue(ChessPath path) {
//        int id = cChessboard.innterChessboard[path.fromX][path.fromY];
//        int eatVal = -ChessWeights.getPosVal(path.eat, path.toX, path.toY);
//        int after = ChessWeights.getPosVal(id, path.toX, path.toY);
//        int before = -ChessWeights.getPosVal(id, path.fromX, path.fromY);
//        return eatVal + after + before;
        // ---------- 尝试测试 -----------
        int id = cChessboard.innterChessboard[path.fromX][path.fromY];
        int eatVal = ChessWeights.getPosVal(path.eat, path.toX, path.toY);
        int after = ChessWeights.getPosVal(id, path.toX, path.toY);
        int before = ChessWeights.getPosVal(id, path.fromX, path.fromY);
        return after - before - eatVal;
        // ------------------------------
    }

    /**
     * 计算每个路径在棋盘上的增长分数，并将分数保存于path的value属性中
     * @param path
     * @return
     */
    protected void setPathValue(ChessPath path) {
        int id = cChessboard.innterChessboard[path.fromX][path.fromY];
        int eatVal = path.eat == 0 ? 0 : ChessWeights.getPosVal(path.eat, path.toX, path.toY);
        int after = ChessWeights.getPosVal(id, path.toX, path.toY);
        int before = ChessWeights.getPosVal(id, path.fromX, path.fromY);
        path.value = after - before - eatVal;
    }
}

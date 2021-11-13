package pers.zr.vlumino.chinesechess.ai;


import pers.zr.vlumino.chinesechess.ai.heuristic.Selector;
import pers.zr.vlumino.chinesechess.ai.zobrist.VerifyCode;
import pers.zr.vlumino.chinesechess.ai.zobrist.ZobristCode;

import java.util.List;

public class CChessboard {

//    public final static int AI_CHESS = 1;

//    public final static int RIVAL_CHESS = -1;

    public CChessboard() {
        this.init();
    }

    /**
     * 拷贝构造
     *
     * @param cBoard
     */
    public CChessboard(CChessboard cBoard) {
        this.innterChessboard = new int[10][9];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                this.innterChessboard[i][j] = cBoard.innterChessboard[i][j];
            }
        }
    }

    /**
     * 内置棋盘
     */
    public int[][] innterChessboard;

    public void init() {
        this.innterChessboard = new int[10][9];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                innterChessboard[i][j] = 0;
            }
        }
        // 敌方
        this.innterChessboard[0][0] = -CChess.JU;
        this.innterChessboard[0][1] = -CChess.MA;
        this.innterChessboard[0][2] = -CChess.XIANG;
        this.innterChessboard[0][3] = -CChess.SHI;
        this.innterChessboard[0][4] = -CChess.JIANG;
        this.innterChessboard[0][5] = -CChess.SHI;
        this.innterChessboard[0][6] = -CChess.XIANG;
        this.innterChessboard[0][7] = -CChess.MA;
        this.innterChessboard[0][8] = -CChess.JU;
        this.innterChessboard[2][1] = -CChess.PAO;
        this.innterChessboard[2][7] = -CChess.PAO;
        this.innterChessboard[3][0] = -CChess.ZU;
        this.innterChessboard[3][2] = -CChess.ZU;
        this.innterChessboard[3][4] = -CChess.ZU;
        this.innterChessboard[3][6] = -CChess.ZU;
        this.innterChessboard[3][8] = -CChess.ZU;
        // 我方
        this.innterChessboard[9][0] = CChess.JU;
        this.innterChessboard[9][1] = CChess.MA;
        this.innterChessboard[9][2] = CChess.XIANG;
        this.innterChessboard[9][3] = CChess.SHI;
        this.innterChessboard[9][4] = CChess.JIANG;
        this.innterChessboard[9][5] = CChess.SHI;
        this.innterChessboard[9][6] = CChess.XIANG;
        this.innterChessboard[9][7] = CChess.MA;
        this.innterChessboard[9][8] = CChess.JU;
        this.innterChessboard[7][1] = CChess.PAO;
        this.innterChessboard[7][7] = CChess.PAO;
        this.innterChessboard[6][0] = CChess.ZU;
        this.innterChessboard[6][2] = CChess.ZU;
        this.innterChessboard[6][4] = CChess.ZU;
        this.innterChessboard[6][6] = CChess.ZU;
        this.innterChessboard[6][8] = CChess.ZU;
    }

    /**
     * 打印棋盘
     */
    public synchronized void printChessboard() {
        System.out.printf("%6s", " ");
        for (int i = 0; i < 9; i++) {
            System.out.printf("%6d", i);
        }
        System.out.println();
        System.out.println("     --------------------------------------------------------");
        for (int i = 0; i < 10; i++) {
            System.out.printf("%6s", NumToCChar.toCChar(i));
            for (int j = 0; j < 9; j++) {
                if (innterChessboard[i][j] == 0) {
                    System.out.printf("%6s", "·");
                } else {
                    System.out.printf("%6d", innterChessboard[i][j]);
                }
            }
            System.out.println();
            System.out.println();
        }
        System.out.println("\n\n");
    }

    /**
     * 按照路径移动棋子
     *
     * @param path
     */
    public void moveChess(ChessPath path) {
        innterChessboard[path.toX][path.toY] = innterChessboard[path.fromX][path.fromY];
        innterChessboard[path.fromX][path.fromY] = 0;
    }

    /**
     * 按照路径移动棋子，支持一次性移动一个棋子数组
     *
     * @param path
     */
    public void doMoves(List<ChessPath> path) {
        for (ChessPath chessPath : path) {
            this.moveChess(chessPath);
        }
    }

    /**
     * 恢复刚才路径移动的棋子
     *
     * @param path
     */
    public void undoMoveChess(ChessPath path) {
        innterChessboard[path.fromX][path.fromY] = innterChessboard[path.toX][path.toY];
        innterChessboard[path.toX][path.toY] = path.eat;

    }

    /**
     * 计算当前盘面的价值总和
     *
     * @return
     */
    public int getOnBoardVal() {
        int val = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                if (innterChessboard[i][j] != 0) {
                    val += ChessWeights.getPosVal(innterChessboard[i][j], i, j);
                }
            }
        }
        return val;
    }

    /**
     * 获得所有可能选点
     *
     * @param colorSign
     * @param selector
     * @return
     */
    public List<ChessPath> getAllPath(int colorSign, Selector selector) {
        return selector.selectPossiblePath(colorSign);
    }

    @Deprecated
    public String hashString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                if (innterChessboard[i][j] != 0) {
                    sb.append(innterChessboard[i][j]);
                }
            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 10; j++) {
                if (innterChessboard[j][i] != 0) {
                    sb.append(innterChessboard[j][i]);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 生成哈希
     *
     * @return
     */
    @Override
    public int hashCode() {
        boolean find = false;
        int hash = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                if (innterChessboard[i][j] != 0) {
                    if (!find) {
                        hash = ZobristCode.getZobristOfId(innterChessboard[i][j], i, j);
                        find = true;
                    } else {
                        hash = hash ^ ZobristCode.getZobristOfId(innterChessboard[i][j], i, j);
                    }
                }
            }
        }
        return hash;
    }

    /**
     * 生成校验码
     *
     * @return
     */
    public int verifyCode() {
        boolean find = false;
        int ver = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                if (innterChessboard[i][j] != 0) {
                    if (!find) {
                        ver = VerifyCode.getVerifyOfId(innterChessboard[i][j], i, j);
                        find = true;
                    } else {
                        ver = ver ^ VerifyCode.getVerifyOfId(innterChessboard[i][j], i, j);
                    }
                }
            }
        }
        return ver;
    }

    /**
     * 根据序列恢复棋盘
     *
     * @param boardArr
     */
    public void recoverChessboard(String boardArr) {
        int pos = 0;
        String[] arr = boardArr.split(";");
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                innterChessboard[i][j] = Integer.parseInt(arr[pos++]);
            }
        }
    }

    /**
     * 获得棋盘序列
     *
     * @return
     */
    public String getBoardArr() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                sb.append(innterChessboard[i][j]);
                if (i != 9 || j != 8) {
                    sb.append(";");
                }
            }
        }
        return sb.toString();
    }

    /**
     * 获得棋盘上棋子的数量，用于在不同局面时选取更好的搜索算法
     *
     * @return
     */
    public int chessNumOnBoard() {
        int count = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                if (innterChessboard[i][j] != 0) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * 清除所有棋子
     */
    public void clearChessboard() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                innterChessboard[i][j] = 0;
            }
        }
    }
}

package pers.zr.vlumino.chinesechess.ai;

import java.util.ArrayList;
import java.util.List;

public class GenChessPath {

    /**
     * 获取车的所有走法
     *
     * @param x
     * @param y
     * @param board
     * @return
     */
    public static List<ChessPath> getPathOf_1(int x, int y, int[][] board) {
        List<ChessPath> ret = new ArrayList<>();
        int colorSign = board[x][y] > 0 ? 1 : -1;
        // 向上寻找路径
        if (x >= 1) {
            for (int i = x - 1; i >= 0; i--) {
                if (board[i][y] == 0) {
                    ret.add(new ChessPath(x, y, i, y));
                } else if (board[i][y] * colorSign > 0) {
                    break;
                } else if (board[i][y] * colorSign < 0) {
                    ret.add(new ChessPath(x, y, i, y, board[i][y]));
                    break;
                }
            }
        }
        // 向下寻找路径
        if (x <= 8) {
            for (int i = x + 1; i <= 9; i++) {
                if (board[i][y] == 0) {
                    ret.add(new ChessPath(x, y, i, y));
                } else if (board[i][y] * colorSign > 0) {
                    break;
                } else if (board[i][y] * colorSign < 0) {
                    ret.add(new ChessPath(x, y, i, y, board[i][y]));
                    break;
                }
            }
        }
        // 向左寻找路径
        if (y >= 1) {
            for (int i = y - 1; i >= 0; i--) {
                if (board[x][i] == 0) {
                    ret.add(new ChessPath(x, y, x, i));
                } else if (board[x][i] * colorSign > 0) {
                    break;
                } else if (board[x][i] * colorSign < 0) {
                    ret.add(new ChessPath(x, y, x, i, board[x][i]));
                    break;
                }
            }
        }
        // 向右寻找路径
        if (y <= 7) {
            for (int i = y + 1; i <= 8; i++) {
                if (board[x][i] == 0) {
                    ret.add(new ChessPath(x, y, x, i));
                } else if (board[x][i] * colorSign > 0) {
                    break;
                } else if (board[x][i] * colorSign < 0) {
                    ret.add(new ChessPath(x, y, x, i, board[x][i]));
                    break;
                }
            }
        }
        return ret;
    }


    /**
     * 获取马的所有走法
     *
     * @param x
     * @param y
     * @param board
     * @return
     */
    public static List<ChessPath> getPathOf_2(int x, int y, int[][] board) {
        List<ChessPath> ret = new ArrayList<>();
        int colorSign = board[x][y] > 0 ? 1 : -1;
        // 每个马当前最多有八种跳法，判断蹩马腿情况
        // 左上方
        if (x >= 2 && y >= 1 && board[x - 1][y] == 0) {
            // 没有棋子或是对方的棋子
            if (board[x - 2][y - 1] * colorSign <= 0) {
                ret.add(new ChessPath(x, y, x - 2, y - 1, board[x - 2][y - 1]));
            }
        }
        if (x >= 1 && y >= 2 && board[x][y - 1] == 0) {
            if (board[x - 1][y - 2] * colorSign <= 0) {
                ret.add(new ChessPath(x, y, x - 1, y - 2, board[x - 1][y - 2]));
            }
        }
        // 右上方
        if (x >= 2 && y <= 7 && board[x - 1][y] == 0) {
            if (board[x - 2][y + 1] * colorSign <= 0) {
                ret.add(new ChessPath(x, y, x - 2, y + 1, board[x - 2][y + 1]));
            }
        }
        if (x >= 1 && y <= 6 && board[x][y + 1] == 0) {
            if (board[x - 1][y + 2] * colorSign <= 0) {
                ret.add(new ChessPath(x, y, x - 1, y + 2, board[x - 1][y + 2]));
            }
        }
        // 左下方
        if (x <= 7 && y >= 1 && board[x + 1][y] == 0) {
            if (board[x + 2][y - 1] * colorSign <= 0) {
                ret.add(new ChessPath(x, y, x + 2, y - 1, board[x + 2][y - 1]));
            }
        }
        if (x <= 8 && y >= 2 && board[x][y - 1] == 0) {
            if (board[x + 1][y - 2] * colorSign <= 0) {
                ret.add(new ChessPath(x, y, x + 1, y - 2, board[x + 1][y - 2]));
            }
        }
        // 右下方
        if (x <= 7 && y <= 7 && board[x + 1][y] == 0) {
            if (board[x + 2][y + 1] * colorSign <= 0) {
                ret.add(new ChessPath(x, y, x + 2, y + 1, board[x + 2][y + 1]));
            }
        }
        if (x <= 8 && y <= 6 && board[x][y + 1] == 0) {
            if (board[x + 1][y + 2] * colorSign <= 0) {
                ret.add(new ChessPath(x, y, x + 1, y + 2, board[x + 1][y + 2]));
            }
        }
        return ret;
    }


    /**
     * 获取象的所有走法
     *
     * @param x
     * @param y
     * @param board
     * @return
     */
    public static List<ChessPath> getPathOf_3(int x, int y, int[][] board) {
        List<ChessPath> ret = new ArrayList<>();
        int colorSign = board[x][y] > 0 ? 1 : -1;
        if (colorSign > 0) {
            // 左上
            if ((x == 9 && y == 2) || (x == 7 && y == 4) || (x == 9 && y == 6) || (x == 7 && y == 8)) {
                if (board[x - 1][y - 1] == 0 && board[x - 2][y - 2] * colorSign <= 0) {
                    ret.add(new ChessPath(x, y, x - 2, y - 2, board[x - 2][y - 2]));
                }
            }
            // 右上
            if ((x == 7 && y == 0) || (x == 9 && y == 2) || (x == 7 && y == 4) || (x == 9 && y == 6)) {
                if (board[x - 1][y + 1] == 0 && board[x - 2][y + 2] * colorSign <= 0) {
                    ret.add(new ChessPath(x, y, x - 2, y + 2, board[x - 2][y + 2]));
                }
            }
            // 左下
            if ((x == 5 && y == 2) || (x == 7 && y == 4) || (x == 5 && y == 6) || (x == 7 && y == 8)) {
                if (board[x + 1][y - 1] == 0 && board[x + 2][y - 2] * colorSign <= 0) {
                    ret.add(new ChessPath(x, y, x + 2, y - 2, board[x + 2][y - 2]));
                }
            }
            // 右下
            if ((x == 7 && y == 0) || (x == 5 && y == 2) || (x == 7 && y == 4) || (x == 5 && y == 6)) {
                if (board[x + 1][y + 1] == 0 && board[x + 2][y + 2] * colorSign <= 0) {
                    ret.add(new ChessPath(x, y, x + 2, y + 2, board[x + 2][y + 2]));
                }
            }
        } else {
            // 左上
            if ((x == 4 && y == 2) || (x == 2 && y == 4) || (x == 2 && y == 8) || (x == 4 && y == 6)) {
                if (board[x - 1][y - 1] == 0 && board[x - 2][y - 2] * colorSign <= 0) {
                    ret.add(new ChessPath(x, y, x - 2, y - 2, board[x - 2][y - 2]));
                }
            }
            // 右上
            if ((x == 4 && y == 2) || (x == 2 && y == 0) || (x == 2 && y == 4) || (x == 4 && y == 6)) {
                if (board[x - 1][y + 1] == 0 && board[x - 2][y + 2] * colorSign <= 0) {
                    ret.add(new ChessPath(x, y, x - 2, y + 2, board[x - 2][y + 2]));
                }
            }
            // 左下
            if ((x == 0 && y == 2) || (x == 2 && y == 4) || (x == 0 && y == 6) || (x == 2 && y == 8)) {
                if (board[x + 1][y - 1] == 0 && board[x + 2][y - 2] * colorSign <= 0) {
                    ret.add(new ChessPath(x, y, x + 2, y - 2, board[x + 2][y - 2]));
                }
            }
            // 右下
            if ((x == 2 && y == 0) || (x == 0 && y == 2) || (x == 2 && y == 4) || (x == 0 && y == 6)) {
                if (board[x + 1][y + 1] == 0 && board[x + 2][y + 2] * colorSign <= 0) {
                    ret.add(new ChessPath(x, y, x + 2, y + 2, board[x + 2][y + 2]));
                }
            }
        }
        return ret;
    }


    /**
     * 获取士的所有走法
     *
     * @param x
     * @param y
     * @param board
     * @return
     */
    public static List<ChessPath> getPathOf_4(int x, int y, int[][] board) {
        List<ChessPath> ret = new ArrayList<>();
        int colorSign = board[x][y] > 0 ? 1 : -1;
        if (colorSign > 0) {
            // 左上
            if ((x == 8 && y == 4) || (x == 9 && y == 5)) {
                if (board[x - 1][y - 1] * colorSign <= 0) {
                    ret.add(new ChessPath(x, y, x - 1, y - 1, board[x - 1][y - 1]));
                }
            }
            // 右上
            if ((x == 8 && y == 4) || (x == 9 && y == 3)) {
                if (board[x - 1][y + 1] * colorSign <= 0) {
                    ret.add(new ChessPath(x, y, x - 1, y + 1, board[x - 1][y + 1]));
                }
            }
            // 左下
            if ((x == 8 && y == 4) || (x == 7 && y == 5)) {
                if (board[x + 1][y - 1] * colorSign <= 0) {
                    ret.add(new ChessPath(x, y, x + 1, y - 1, board[x + 1][y - 1]));
                }
            }
            // 右下
            if ((x == 8 && y == 4) || (x == 7 && y == 3)) {
                if (board[x + 1][y + 1] * colorSign <= 0) {
                    ret.add(new ChessPath(x, y, x + 1, y + 1, board[x + 1][y + 1]));
                }
            }
        } else {
            // 左上
            if ((x == 1 && y == 4) || (x == 2 && y == 5)) {
                if (board[x - 1][y - 1] * colorSign <= 0) {
                    ret.add(new ChessPath(x, y, x - 1, y - 1, board[x - 1][y - 1]));
                }
            }
            // 右上
            if ((x == 1 && y == 4) || (x == 2 && y == 3)) {
                if (board[x - 1][y + 1] * colorSign <= 0) {
                    ret.add(new ChessPath(x, y, x - 1, y + 1, board[x - 1][y + 1]));
                }
            }
            // 左下
            if ((x == 1 && y == 4) || (x == 0 && y == 5)) {
                if (board[x + 1][y - 1] * colorSign <= 0) {
                    ret.add(new ChessPath(x, y, x + 1, y - 1, board[x + 1][y - 1]));
                }
            }
            // 右下
            if ((x == 1 && y == 4) || (x == 0 && y == 3)) {
                if (board[x + 1][y + 1] * colorSign <= 0) {
                    ret.add(new ChessPath(x, y, x + 1, y + 1, board[x + 1][y + 1]));
                }
            }
        }
        return ret;
    }


    /**
     * 获取将的所有走法
     *
     * @param x
     * @param y
     * @param board
     */
    public static List<ChessPath> getPathOf_5(int x, int y, int[][] board) {
        List<ChessPath> ret = new ArrayList<>();
        int colorSign = board[x][y] > 0 ? 1 : -1;
        if (colorSign > 0) {
            // 上
            if (x >= 8) {
                if (board[x - 1][y] * colorSign <= 0) {
                    ret.add(new ChessPath(x, y, x - 1, y, board[x - 1][y]));
                }
            }
            // 下
            if (x <= 8) {
                if (board[x + 1][y] * colorSign <= 0) {
                    ret.add(new ChessPath(x, y, x + 1, y, board[x + 1][y]));
                }
            }
            // 左
            if (y >= 4) {
                if (board[x][y - 1] * colorSign <= 0) {
                    ret.add(new ChessPath(x, y, x, y - 1, board[x][y - 1]));
                }
            }
            // 右
            if (y <= 4) {
                if (board[x][y + 1] * colorSign <= 0) {
                    ret.add(new ChessPath(x, y, x, y + 1, board[x][y + 1]));
                }
            }
        } else {
            // 上
            if (x >= 1) {
                if (board[x - 1][y] * colorSign <= 0) {
                    ret.add(new ChessPath(x, y, x - 1, y, board[x - 1][y]));
                }
            }
            // 下
            if (x <= 1) {
                if (board[x + 1][y] * colorSign <= 0) {
                    ret.add(new ChessPath(x, y, x + 1, y, board[x + 1][y]));
                }
            }
            // 左
            if (y >= 4) {
                if (board[x][y - 1] * colorSign <= 0) {
                    ret.add(new ChessPath(x, y, x, y - 1, board[x][y - 1]));
                }
            }
            // 右
            if (y <= 4) {
                if (board[x][y + 1] * colorSign <= 0) {
                    ret.add(new ChessPath(x, y, x, y + 1, board[x][y + 1]));
                }
            }
        }
        return ret;
    }


    /**
     * 获取炮的所有走法
     *
     * @param x
     * @param y
     * @param board
     * @return
     */
    public static List<ChessPath> getPathOf_6(int x, int y, int[][] board) {
        List<ChessPath> ret = new ArrayList<>();
        int colorSign = board[x][y] > 0 ? 1 : -1;
        // 上
        if (x >= 1) {
            int i = x - 1;
            for (; i >= 0; i--) {
                if (board[i][y] == 0) {
                    ret.add(new ChessPath(x, y, i, y));
                } else {
                    break;
                }
            }
            if (i >= 1) {
                for (int j = i - 1; j >= 0; j--) {
                    if (board[j][y] != 0) {
                        // 对方棋子，可将其吃掉，退出循环
                        if (board[j][y] * colorSign < 0) {
                            ret.add(new ChessPath(x, y, j, y, board[j][y]));
                            break;
                        } else { // 己方棋子，退出循环
                            break;
                        }
                    }
                }
            }
        }
        // 下
        if (x <= 8) {
            int i = x + 1;
            for (; i <= 9; i++) {
                if (board[i][y] == 0) {
                    ret.add(new ChessPath(x, y, i, y));
                } else {
                    break;
                }
            }
            if (i <= 8) {
                for (int j = i + 1; j <= 9; j++) {
                    if (board[j][y] != 0) {
                        // 对方棋子，可将其吃掉，退出循环
                        if (board[j][y] * colorSign < 0) {
                            ret.add(new ChessPath(x, y, j, y, board[j][y]));
                            break;
                        } else { // 己方棋子，退出循环
                            break;
                        }
                    }
                }
            }
        }
        // 左
        if (y >= 1) {
            int i = y - 1;
            for (; i >= 0; i--) {
                if (board[x][i] == 0) {
                    ret.add(new ChessPath(x, y, x, i));
                } else {
                    break;
                }
            }
            if (i >= 1) {
                for (int j = i - 1; j >= 0; j--) {
                    if (board[x][j] != 0) {
                        if (board[x][j] * colorSign < 0) {
                            ret.add(new ChessPath(x, y, x, j, board[x][j]));
                            break;
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        // 右
        if (y <= 7) {
            int i = y + 1;
            for (; i <= 8; i++) {
                if (board[x][i] == 0) {
                    ret.add(new ChessPath(x, y, x, i));
                } else {
                    break;
                }
            }
            if (i <= 7) {
                for (int j = i + 1; j <= 8; j++) {
                    if (board[x][j] != 0) {
                        if (board[x][j] * colorSign < 0) {
                            ret.add(new ChessPath(x, y, x, j, board[x][j]));
                            break;
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        return ret;
    }


    /**
     * 获取卒的所有走法
     *
     * @param x
     * @param y
     * @param board
     * @return
     */
    public static List<ChessPath> getPathOf_7(int x, int y, int[][] board) {
        List<ChessPath> ret = new ArrayList<>();
        int colorSign = board[x][y] > 0 ? 1 : -1;
        if (colorSign > 0) {
            // 上
            if (x >= 1 && board[x - 1][y] * colorSign <= 0) {
                ret.add(new ChessPath(x, y, x - 1, y, board[x - 1][y]));
            }
        } else {
            // 下
            if (x <= 8 && board[x + 1][y] * colorSign <= 0) {
                ret.add(new ChessPath(x, y, x + 1, y, board[x + 1][y]));
            }
        }
        // 过河后可以向左向右
        if ((colorSign > 0 && x <= 4) || (colorSign < 0 && x >= 5)) {
            // 左
            if (y >= 1 && board[x][y - 1] * colorSign <= 0) {
                ret.add(new ChessPath(x, y, x, y - 1, board[x][y - 1]));
            }
            // 右
            if (y <= 7 && board[x][y + 1] * colorSign <= 0) {
                ret.add(new ChessPath(x, y, x, y + 1, board[x][y + 1]));
            }
        }
        return ret;
    }


    /**
     * 通过棋子id获得当前棋子的所有路径
     *
     * @param x
     * @param y
     * @param cChessboard
     * @return
     */
    public static List<ChessPath> getChessPathOfID(int x, int y, CChessboard cChessboard) {
        int id = cChessboard.innterChessboard[x][y];
        int[][] board = cChessboard.innterChessboard;
        if (id == 1 || id == -1) {
            return getPathOf_1(x, y, board);
        }
        if (id == 2 || id == -2) {
            return getPathOf_2(x, y, board);
        }
        if (id == 3 || id == -3) {
            return getPathOf_3(x, y, board);
        }
        if (id == 4 || id == -4) {
            return getPathOf_4(x, y, board);
        }
        if (id == 5 || id == -5) {
            return getPathOf_5(x, y, board);
        }
        if (id == 6 || id == -6) {
            return getPathOf_6(x, y, board);
        }
        if (id == 7 || id == -7) {
            return getPathOf_7(x, y, board);
        }
        return null;
    }


    /**
     * 判断是否存在对将情况
     *
     * @param board
     * @return
     */
    public static boolean isGeneralFaceToFace(int[][] board) {
        int rivalY = 0;
        for (int i = 3; i <= 5; i++) {
            boolean findFlag = false;
            for (int j = 0; j <= 2; j++) {
                if (board[i][j] == -5) {
                    rivalY = j;
                    findFlag = true;
                }
                if (findFlag) {
                    break;
                }
            }
        }
        boolean isFind = false;
        for (int i = 7; i <= 9; i++) {
            if (board[i][rivalY] == 5) {
                isFind = true;
                break;
            }
        }
        // r如果没有
        if (!isFind) {
            return false;
        }
        boolean getRivalGeneral = false;
        for (int i = 0; board[i][rivalY] != 5; i++) {
            if (board[i][rivalY] == -5) {
                getRivalGeneral = true;
                continue;
            }
            if (getRivalGeneral && board[i][rivalY] != 0) {
                return true;
            }
        }
        return false;
    }
}

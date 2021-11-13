package pers.zr.vlumino.chinesechess.ai;


public class ChessPath {

    /**
     * 起始点横坐标
     */
    public int fromX;

    /**
     * 起始点纵坐标
     */
    public int fromY;

    /**
     * 终止点横坐标
     */
    public int toX;

    /**
     * 终止点纵坐标
     */
    public int toY;

    /**
     * 吃掉的棋子
     */
    public int eat;

    /**
     * 路径的价值
     */
    public int value;

    /**
     * 构造
     *
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     */
    public ChessPath(int fromX, int fromY, int toX, int toY) {
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
        this.eat = 0;
    }

    /**
     * 构造
     *
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     * @param eat   吃子
     */
    public ChessPath(int fromX, int fromY, int toX, int toY, int eat) {
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
        this.eat = eat;
    }

    /**
     * 构造，将字符串解析为棋盘信息
     *
     * @param msg 第一个到最后一个字符分别代表 fromX, fromY, toX, toY, eat
     */
    public ChessPath(String msg) {
        this.fromX = Integer.parseInt(String.valueOf(msg.charAt(0)));
        this.fromY = Integer.parseInt(String.valueOf(msg.charAt(1)));
        this.toX = Integer.parseInt(String.valueOf(msg.charAt(2)));
        this.toY = Integer.parseInt(String.valueOf(msg.charAt(3)));
        this.eat = Integer.parseInt(String.valueOf(msg.charAt(4)));
    }

    @Override
    public String toString() {
        return "ChessPath{ " +
                "from(" + fromX + ", " + fromY + ")  " +
                "to(" + toX + ", " + toY + ")  " +
                "eat(" + eat + ")" + " }";
    }
}

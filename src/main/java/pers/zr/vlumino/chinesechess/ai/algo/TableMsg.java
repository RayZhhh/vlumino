package pers.zr.vlumino.chinesechess.ai.algo;

public class TableMsg {

    public TableMsg() {
        this.upperBound = AlphaBetaTree.BETA_INIT_VAL;
        this.lowerBound = AlphaBetaTree.ALPHA_INIT_VAL;
    }

    /**
     * 上界值
     */
    public int upperBound;

    /**
     * 下界值
     */
    public int lowerBound;

    /**
     * 上界值对应的搜索深度
     */
    public int upDepth;

    /**
     * 下界值对应的搜索深度
     */
    public int loDepth;

    /**
     * 将字符串转化称置换表
     * @param s 表示置换表信息的字符串
     */
    public TableMsg(String s) {
        String[] split = s.split(";");
        this.upperBound = Integer.parseInt(split[0]);
        this.lowerBound = Integer.parseInt(split[1]);
        this.upDepth = Integer.parseInt(split[2]);
        this.loDepth = Integer.parseInt(split[3]);
    }

    @Override
    public String toString() {
        return "" + upperBound + ";" + lowerBound + ";" + upDepth + ";" + loDepth;
    }
}

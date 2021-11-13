package pers.zr.vlumino.chinesechess.ai.heuristic;

import pers.zr.vlumino.chinesechess.ai.ChessPath;

import java.util.List;


public interface Selector {

    /**
     * 从当前盘面中根据某种方法筛选可能的棋路
     *
     * @param colorSign 要筛选的棋子颜色
     * @return 筛选过后的所有路径
     */
    List<ChessPath> selectPossiblePath(int colorSign);


    /**
     * 将List中的路径按照指定方法进行筛选
     *
     * @param colorSign 要筛选的棋子颜色
     * @param paths 待筛选的路径
     * @return 筛选后的所有路径
     */
//     List<ChessPath> selectPossiblePath(int colorSign, List<ChessPath> paths);
}

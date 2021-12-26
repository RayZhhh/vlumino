package pers.zr.vlumino.chinesechess.ai.algo;


import pers.zr.vlumino.chinesechess.ai.CChessboard;
import pers.zr.vlumino.chinesechess.ai.ChessPath;
import pers.zr.vlumino.chinesechess.ai.zobrist.VerifyCode;
import pers.zr.vlumino.chinesechess.ai.zobrist.ZobristCode;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public abstract class AlphaBetaTreeWithMemory extends AlphaBetaTree {

    /**
     * 构造
     *
     * @param cChessboard 待评估的棋盘
     */
    protected AlphaBetaTreeWithMemory(CChessboard cChessboard) {
        super(cChessboard);
        tranTableForMax = new ConcurrentHashMap<>();
        tranTableForMin = new ConcurrentHashMap<>();
    }

    /**
     * 通过[哈希值]和[校验值]查找到唯一的盘面
     */
    public static Map<Integer, Map<Integer, TableMsg>> tranTableForMax = new ConcurrentHashMap<>();

    /**
     * 通过[哈希值]和[校验值]查找到唯一的盘面
     */
    public static Map<Integer, Map<Integer, TableMsg>> tranTableForMin = new ConcurrentHashMap<>();

    /**
     * 存储maxTable的文件路径
     */
    public static String MAX_FILE_PATH = "src/main/java/pers/zr/vlumino/chinesechess/ai/files/max_transition.txt";

    /**
     * 存储minTable的文件路径
     */
    public static String MIN_FILE_PATH = "src/main/java/pers/zr/vlumino/chinesechess/ai/files/min_transition.txt";

    /**
     * 将Map中的置换表存到文件中
     *
     * @param maxTable 是否保存的是 maxTable
     */
    protected static void saveMap(boolean maxTable) {
        try {
            File file;
            Map<Integer, Map<Integer, TableMsg>> tranTable;
            if (maxTable) {
                file = new File(MAX_FILE_PATH);
                tranTable = tranTableForMax;
            } else {
                file = new File(MIN_FILE_PATH);
                tranTable = tranTableForMin;
            }
            OutputStream out = new FileOutputStream(file);
            for (Map.Entry<Integer, Map<Integer, TableMsg>> entry : tranTable.entrySet()) {
                // 加入hash
                String msg = entry.getKey().toString();
                // 遍历所有 [校验码-置换表] 键值对
                Map<Integer, TableMsg> value = entry.getValue();
                boolean findWorth = false;
                for (Map.Entry<Integer, TableMsg> en : value.entrySet()) {
                    if (maxTable) {
                        if (en.getValue().loDepth >= 7 || en.getValue().upDepth >= 7) {
                            msg += " " + en.getKey() + "#" + en.getValue().toString();
                            findWorth = true;
                        }
                    } else {
                        if (en.getValue().loDepth >= 6 || en.getValue().upDepth >= 6) {
                            msg += " " + en.getKey() + "#" + en.getValue().toString();
                            findWorth = true;
                        }
                    }
                    //msg += " " + en.getKey() + "#" + en.getValue().toString();
                }
                if (findWorth) {
                    out.write(msg.getBytes(StandardCharsets.UTF_8));
                    out.write("\n".getBytes(StandardCharsets.UTF_8));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存Max和Min置换表
     */
    public static void saveTable() {
        clearFile(MAX_FILE_PATH);
        clearFile(MIN_FILE_PATH);
        saveMap(true);
        saveMap(false);
    }

    /**
     * 清空文件
     *
     * @param path 文件路径
     */
    protected static void clearFile(String path) {
        try {
            FileWriter fileWriter = new FileWriter(new File(path));
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载置换表到内置Map中
     *
     * @param maxTable 是否加载 maxTable
     */
    protected static void loadMap(boolean maxTable) {
        try {
            File file;
            Map<Integer, Map<Integer, TableMsg>> tranTable;
            if (maxTable) {
                file = new File(MAX_FILE_PATH);
                tranTable = tranTableForMax;
            } else {
                file = new File(MIN_FILE_PATH);
                tranTable = tranTableForMin;
            }
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = br.readLine()) != null) {
                line = line.trim();
                String[] s = line.split(" ");
                int hsah = Integer.parseInt(s[0]);
                Map<Integer, TableMsg> map = new ConcurrentHashMap<>();
                for (int i = 1; i < s.length; i++) {
                    String[] split = s[i].split("#");
                    int ver = Integer.parseInt(split[0]);
                    map.put(ver, new TableMsg(split[1]));
                }
                tranTable.put(hsah, map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载置换表
     */
    public static void loadTable() {
        loadMap(true);
        loadMap(false);
        System.out.println("置换表加载成功");
        System.out.println("max table size: " + tranTableForMax.size());
        System.out.println("min table size: " + tranTableForMin.size());
    }

    /**
     * 清除置换表中内容
     */
    public static void clearTable() {
        tranTableForMin.clear();
        tranTableForMax.clear();
    }

    /**
     * 更新置换表下界
     *
     * @param loBound   下界值
     * @param colorSign 棋子类型
     * @param depth     下界对应的搜索深度
     */
    protected void upLoBound(int loBound, int colorSign, int depth) {
        int chHash = chessboard.hashCode();
        int chVer = chessboard.verifyCode();
        if (colorSign == MAX_LAYER_SIGN) {
            Map<Integer, TableMsg> msgMap = tranTableForMax.get(chHash);
            TableMsg msg;
            // 如果不能查到[哈希值]，说明盘面并不存在，添加当前盘面
            if (msgMap == null) {
                TableMsg tmp = new TableMsg();
                tmp.lowerBound = loBound;
                tmp.loDepth = depth;
                // 将[校验码，表]插入
                ConcurrentHashMap<Integer, TableMsg> verMap = new ConcurrentHashMap<>();
                verMap.put(chVer, tmp);
                tranTableForMax.put(chHash, verMap);
            } else if ((msg = msgMap.get(chVer)) == null) { // 能查到hash，但是查不到verify
                TableMsg tmp = new TableMsg();
                tmp.lowerBound = loBound;
                tmp.loDepth = depth;
                msgMap.put(chVer, tmp);
            } else if (depth > msg.loDepth) { // 查到相同局面，但是当前深度较大
                msg.lowerBound = loBound;
            } else if (depth == msg.loDepth) {
                msg.lowerBound = Math.max(msg.lowerBound, loBound);
            }
        } else {
            Map<Integer, TableMsg> msgMap = tranTableForMin.get(chHash);
            TableMsg msg;
            // 如果不能查到[哈希值]，说明盘面并不存在，添加当前盘面
            if (msgMap == null) {
                TableMsg tmp = new TableMsg();
                tmp.lowerBound = loBound;
                tmp.loDepth = depth;
                // 将[校验码，表]插入
                ConcurrentHashMap<Integer, TableMsg> verMap = new ConcurrentHashMap<>();
                verMap.put(chVer, tmp);
                tranTableForMin.put(chHash, verMap);
            } else if ((msg = msgMap.get(chVer)) == null) { // 能查到hash，但是查不到verify
                TableMsg tmp = new TableMsg();
                tmp.lowerBound = loBound;
                tmp.loDepth = depth;
                msgMap.put(chVer, tmp);
            } else if (depth > msg.loDepth) { // 查到相同局面，但是当前深度较大
                msg.lowerBound = loBound;
            } else if (depth == msg.loDepth) {
                msg.lowerBound = Math.max(msg.lowerBound, loBound);
            }
        }
    }

    /**
     * 更新置换表上界
     *
     * @param upBound   上界值
     * @param colorSign 棋子类型
     * @param depth     上界对应的搜索深度
     */
    protected void upUpBound(int upBound, int colorSign, int depth) {
        int chHash = chessboard.hashCode();
        int chVer = chessboard.verifyCode();
        if (colorSign == MAX_LAYER_SIGN) {
            Map<Integer, TableMsg> msgMap = tranTableForMax.get(chHash);
            TableMsg msg;
            // 如果不能查到[哈希值]，说明盘面并不存在，添加当前盘面
            if (msgMap == null) {
                TableMsg tmp = new TableMsg();
                tmp.upperBound = upBound;
                tmp.upDepth = depth;
                // 将[校验码，表]插入
                ConcurrentHashMap<Integer, TableMsg> verMap = new ConcurrentHashMap<>();
                verMap.put(chVer, tmp);
                tranTableForMax.put(chHash, verMap);
            } else if ((msg = msgMap.get(chVer)) == null) { // 能查到hash，但是查不到verify
                TableMsg tmp = new TableMsg();
                tmp.upperBound = upBound;
                tmp.upDepth = depth;
                msgMap.put(chVer, tmp);
            } else if (depth > msg.upDepth) { // 查到相同局面，但是当前深度较大
                msg.upperBound = upBound;
            } else if (depth == msg.upDepth) {
                msg.upperBound = Math.min(msg.upperBound, upBound);
            }
        } else {
            Map<Integer, TableMsg> msgMap = tranTableForMin.get(chHash);
            TableMsg msg;
            // 如果不能查到[哈希值]，说明盘面并不存在，添加当前盘面
            if (msgMap == null) {
                TableMsg tmp = new TableMsg();
                tmp.upperBound = upBound;
                tmp.upDepth = depth;
                // 将[校验码，表]插入
                ConcurrentHashMap<Integer, TableMsg> verMap = new ConcurrentHashMap<>();
                verMap.put(chVer, tmp);
                tranTableForMin.put(chHash, verMap);
            } else if ((msg = msgMap.get(chVer)) == null) { // 能查到hash，但是查不到verify
                TableMsg tmp = new TableMsg();
                tmp.upperBound = upBound;
                tmp.upDepth = depth;
                msgMap.put(chVer, tmp);
            } else if (depth > msg.upDepth) { // 查到相同局面，但是当前深度较大
                msg.upperBound = upBound;
            } else if (depth == msg.upDepth) {
                msg.upperBound = Math.max(msg.upperBound, upBound);
            }
        }
    }

    /**
     * 根据已有的Zobrist值和变化路径获取新的Zobrist值
     * @param path
     * @param prevZobrist
     * @return
     */
    public int getNewZobrist(ChessPath path, int prevZobrist) {
        int id = this.chessboard.innterChessboard[path.fromX][path.fromY];
        int fromZo = ZobristCode.getZobristOfId(id, path.fromX, path.fromY);
        int toZo = ZobristCode.getZobristOfId(id, path.toX, path.toY);
        if (path.eat == 0) {
            return prevZobrist ^ fromZo ^ toZo;
        } else {
            int eatZo = ZobristCode.getZobristOfId(path.eat, path.toX, path.toY);
            return prevZobrist ^ fromZo ^ toZo ^ eatZo;
        }
    }

    /**
     * 根据已有的校验码和路径获得新的校验码值
     * @param path
     * @param prevVerifyCode
     * @return
     */
    public int getNewVerify(ChessPath path, int prevVerifyCode) {
        int id = this.chessboard.innterChessboard[path.fromX][path.fromY];
        int fromVer = VerifyCode.getVerifyOfId(id, path.fromX, path.fromY);
        int toVer = VerifyCode.getVerifyOfId(id, path.toX, path.toY);
        if (path.eat == 0) {
            return prevVerifyCode ^ fromVer ^ toVer;
        } else {
            int eatVer = VerifyCode.getVerifyOfId(path.eat, path.toX, path.toY);
            return prevVerifyCode ^ fromVer ^ toVer ^ eatVer;
        }
    }
}

package pers.zr.vlumino.chinesechess.ai.gamebit;

import pers.zr.vlumino.chinesechess.ai.CChessboard;
import pers.zr.vlumino.chinesechess.ai.ChessPath;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameBitPathGenerator {
    public static final String FILE_PATH = "/Users/zhangrui/Developer/vlumino/src/main/java/pers/zr/vlumino/chinesechess/ai/files/gamebit_path.txt";

    public static final String DATA_FILE_PATH = "/Users/zhangrui/Developer/vlumino/src/main/java/pers/zr/vlumino/chinesechess/ai/files/gamebit_formatted.txt";

    public static Map<Integer, GameBitTable> gameBitTableMap = new ConcurrentHashMap<>();

    public static final String SEP_CHAR_1 = "#";

    //public static final String SEP_CHAR_2 = ";";

    public static void loadGameBitTable() {
        File file = new File(FILE_PATH);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(SEP_CHAR_1);
                int hash = Integer.parseInt(split[0]);
                gameBitTableMap.put(hash, new GameBitTable(split[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void saveGameBitTable() {
        try {
            File file = new File(FILE_PATH);
            FileOutputStream fos = new FileOutputStream(file);
            for (Map.Entry<Integer, GameBitTable> table : gameBitTableMap.entrySet()) {
                String msg = table.getKey() + SEP_CHAR_1 + table.getValue().toString();
                fos.write(msg.getBytes(StandardCharsets.UTF_8));
                fos.write("\n".getBytes(StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void genGameBitPath() {
        File file = new File(DATA_FILE_PATH);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = "";
            // 完成敌人先走的所有情况
            // 每次读取一行，即一个棋谱
            while ((line = br.readLine()) != null) {
                line = line.trim();
                // 筛选掉不符合长度的棋谱
                if (line.length() != 80) {
                    continue;
                }
                // 每次读取两个ChessPath
                CChessboard board = new CChessboard();
                for (int i = 0; i < line.length(); i = i + 8) {
                    ChessPath rivalPath = new ChessPath(parse2InversePathString(line.substring(i, i + 4)) + "0");
                    ChessPath selfPath = new ChessPath(parse2InversePathString(line.substring(i + 4, i + 8)) + "0");
                    board.moveChess(rivalPath);
                    updateGameBitMap(board.hashCode(), board.verifyCode(), parse2InversePathString(line.substring(i + 4, i + 8)));
                    board.moveChess(selfPath);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static String parse2InversePathString(String path) {
        int fromX = Integer.parseInt(String.valueOf(path.charAt(0)));
        int fromY = Integer.parseInt(String.valueOf(path.charAt(1)));
        int toX = Integer.parseInt(String.valueOf(path.charAt(2)));
        int toY = Integer.parseInt(String.valueOf(path.charAt(3)));
        fromX = 9 - fromX;
        fromY = 8 - fromY;
        toX = 9 - toX;
        toY = 8 - toY;
        return String.valueOf(fromX) + fromY + toX + toY;
    }


    private static void updateGameBitMap(int hashCode, int verCode, String path) {
        GameBitTable gameBitTable = gameBitTableMap.get(hashCode);
        // 如果查不到
        if (gameBitTableMap.get(hashCode) == null || gameBitTable.verifyCode != verCode) {
            // 创建新的路径表
            GameBitTable gameBitTable1 = new GameBitTable();
            gameBitTable1.verifyCode = verCode;
            gameBitTable1.paths.add(path);
            gameBitTableMap.put(hashCode, gameBitTable1);
        } else { // 查到了
            boolean update = true;
            for (String s : gameBitTable.paths) {
                if (s.equals(path)) { // 找到了相同的路径，那就不进行更新
                    update = false;
                    break;
                }
            }
            if (update) {
                gameBitTable.paths.add(path);
            }
        }
    }

    public static void main(String[] args) {

        genGameBitPath();
        saveGameBitTable();
    }

}

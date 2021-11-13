package pers.zr.vlumino.chinesechess.ai.gamebit;

import java.util.ArrayList;
import java.util.List;

public class GameBitTable {

    public GameBitTable() {
        this.paths = new ArrayList<>();
    }

    /**
     * 校验码
     */
    public int verifyCode;

    /**
     * 路径
     */
    List<String> paths;

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(verifyCode);
        for (int i = 0; i < paths.size(); i++) {
            str.append(";");
            str.append(paths.get(i));
        }
        return str.toString();
    }

    /**
     * 构造
     *
     * @param tableMsg 表示GameBitTable的对象
     */
    public GameBitTable(String tableMsg) {
        this.paths = new ArrayList<>();
        String[] msg = tableMsg.split(";");
        verifyCode = Integer.parseInt(msg[0]);
        for (int i = 1; i < msg.length; i++) {
            paths.add(msg[i]);
        }
    }
}

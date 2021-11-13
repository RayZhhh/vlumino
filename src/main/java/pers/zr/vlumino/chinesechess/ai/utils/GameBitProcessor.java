package pers.zr.vlumino.chinesechess.ai.utils;

import pers.zr.vlumino.chinesechess.ai.algo.TableMsg;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class GameBitProcessor {

    public static final String FILE_PATH = "src/main/java/pers/zr/vlumino/chinesechess/ai/files/gamebit.txt";

    public static final String OUT_FILE_PATH = "src/main/java/pers/zr/vlumino/chinesechess/ai/files/gamebit_formatted.txt";

    /**
     * 将所有横行的 gamebit 规格化
     *
     * @throws IOException
     */
    public static void formatGameBit() throws IOException {
        File file = new File(FILE_PATH);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        String[] gbLines = line.split(" ");
        OutputStream out = new FileOutputStream(new File(OUT_FILE_PATH));
        for (String gbLine : gbLines) {
            String temp = "";
            for (int i = 0; i < gbLine.length(); i = i + 2) {
                char x = gbLine.charAt(i + 1);
                char y = gbLine.charAt(i);
                temp += x;
                temp += y;
            }
            out.write(temp.getBytes(StandardCharsets.UTF_8));
            out.write("\n".getBytes(StandardCharsets.UTF_8));
        }
    }

    public static void main(String[] args) throws IOException {
        formatGameBit();
    }
}
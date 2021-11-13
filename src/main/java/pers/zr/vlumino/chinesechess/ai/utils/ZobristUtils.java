package pers.zr.vlumino.chinesechess.ai.utils;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public final class ZobristUtils {

    private static final String zFilePath = "src/main/java/pers/zr/brownstone/chinesechess/ai/files/zobrist.txt";

    private static void genZobrist() throws IOException {
        File zFile = new File(zFilePath);
        FileOutputStream stream = new FileOutputStream(zFile);
        for (int i = 0; i < 14; i++) {
            stream.write("{".getBytes(StandardCharsets.UTF_8));
            for (int j = 0; j < 10 * 9; j++) {
                int rand = (int) (Math.random() * 10000000);
                stream.write(("" + rand).getBytes(StandardCharsets.UTF_8));
                if (j != 89) {
                    stream.write(",".getBytes(StandardCharsets.UTF_8));
                }
            }
            stream.write("}".getBytes(StandardCharsets.UTF_8));
            if (i != 13) {
                stream.write(",\n".getBytes(StandardCharsets.UTF_8));
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ZobristUtils.genZobrist();
    }
}

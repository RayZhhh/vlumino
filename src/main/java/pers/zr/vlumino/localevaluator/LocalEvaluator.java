package pers.zr.vlumino.localevaluator;


import pers.zr.vlumino.chinesechess.ai.CChessboard;
import pers.zr.vlumino.chinesechess.ai.ChessPath;
import pers.zr.vlumino.chinesechess.ai.evaluator.SmartEvaluator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class LocalEvaluator {
    public static String readContentFromGet(String url) throws IOException {
        URL getUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
        connection.connect();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                connection.getInputStream()));
        //System.out.println("=============get================");
        String lines;
        //while ((lines = reader.readLine()) != null) {
        //    System.out.println(lines);
        //}
        lines = reader.readLine();
        reader.close();
        connection.disconnect();
        return lines;
    }


    public static String SendHttpPOST(String url, String data) throws Exception {
        String result = null;
        //打开连接
        //要发送的POST请求url?Key=Value&amp;Key2=Value2&amp;Key3=Value3的形式
        //URL requestUrl = new URL(url + "?" + requestParam);
        URL requestUrl = new URL(url);
        HttpURLConnection httpConn = (HttpURLConnection) requestUrl.openConnection();

        //加入数据
        httpConn.setRequestMethod("POST");
        httpConn.setDoOutput(true);
        httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream(), "UTF-8");
        writer.write(data);
        writer.flush();


        //获取输入流
        BufferedReader in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "utf-8"));
        int code = httpConn.getResponseCode();
        if (HttpURLConnection.HTTP_OK == code || HttpURLConnection.HTTP_CREATED == code) {
            String temp = in.readLine();
            /*连接成一个字符串*/
            while (temp != null) {
                if (result != null) {
                    result += temp;
                } else {
                    result = temp;
                }
                temp = in.readLine();
            }
        }
        return result;
    }


    public static void startService() throws Exception {
        while (true) {
            String msg = null;
            do {
                msg = readContentFromGet("http://www.steponecloud.com/vlumino/bridge");
            } while ("none".equals(msg));
            CChessboard cChessboard = new CChessboard();
            // 导入棋盘
            cChessboard.recoverChessboard(msg);
            System.out.println("received chessboard:");
            cChessboard.printChessboard();
            SmartEvaluator evaluator = new SmartEvaluator(cChessboard);
            ChessPath path = evaluator.getBestPath();
            System.out.println("评估结果：" + path);
            String postMsg = "path=" + path.fromX + ";" + path.fromY + ";" + path.toX + ";" + path.toY;
            System.out.println(SendHttpPOST("http://www.steponecloud.com/vlumino/vlumino-game", postMsg));
        }
    }


    public static void main(String[] args) throws Exception {
        startService();
    }
}

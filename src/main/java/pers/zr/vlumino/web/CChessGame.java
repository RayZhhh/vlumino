package pers.zr.vlumino.web;

import pers.zr.vlumino.chinesechess.ai.CChessboard;
import pers.zr.vlumino.chinesechess.ai.ChessPath;
import pers.zr.vlumino.chinesechess.ai.evaluator.MultiThreadEvaluator;
import pers.zr.vlumino.chinesechess.ai.algo.TreeType;
import pers.zr.vlumino.chinesechess.ai.evaluator.SmartEvaluator;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "game", value = "/ai-evaluator")
public class CChessGame extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String chessboardMsg = req.getParameter("chessboard");
        CChessboard cChessboard = new CChessboard();
        // 导入棋盘
        cChessboard.recoverChessboard(chessboardMsg);
        // 将正负对调（匹配前端）
        //cChessboard.reverseColor();
        System.out.println("received chessboard:");
        cChessboard.printChessboard();
        // 进行评估
        //MultiThreadEvaluator evaluator = new MultiThreadEvaluator(cChessboard, 8, TreeType.MTDF);
        SmartEvaluator evaluator = new SmartEvaluator(cChessboard);
        ChessPath path = evaluator.getBestPath();
        System.out.println("评估结果：" + path);
        String respMsg = "" + path.fromX + ";" + path.fromY + ";" + path.toX + ";" + path.toY;
        // response
        resp.setHeader("Content-Type", "text/html;charset=UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        resp.setHeader("Access-Control-Max-Age", "3600");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Token, Authorization, ybg");
        resp.getWriter().println(respMsg);
    }
}
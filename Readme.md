re# vLumino-Chinese Chess

# 微量-中国象棋

## 目前我的仓库中添加了一个优化版本的象棋AI，用c++实现，修复了这个项目中的小部分bug，优化了算法，搜索速度是该项目的三倍。目前有简单的前端。请见 [github.com/RayZhhh/vliang-chinesechess-cpp](https://github.com/RayZhhh/vliang-chinesechess-cpp)

👤作者：睿

📧邮件：rayZhh@163.com

📅日期：2021年10月

------

## 项目简介

·在这之前我实现了一个有6-7步搜索能力的五子棋AI。由于我爸是个中国象棋高手，他很想让我写一个中国象棋给他玩(最后发现开始还能有来有回，到残局就不太能下得过了)。我开始没有想到很好的评估方式，后来也查阅了大量资料，仍然觉得应该使用启发式搜索实现。在算法上实现了诸如MTD(f)
和置换表记忆化搜索的各种算法。我不是算法的发明者，只是一个实现者，相比想出这些算法的大牛们，我做的工作简直不值一提。向他们致敬！

·vLumino是一个中国象棋AI项目，运用启发式搜索和多线程技术进行搜索，并使用Java实现（虽然相比c++可能牺牲了一点点速度，但是用Java比较适合搭建服务器做网络游戏）。评估函数在前期的搜索深度为8，后期的搜索深度能达到10以上（使用MTD(
f)结合记忆化的alpha-beta方式进行搜索）。如果追求绝对的精准使用静态评估（quiescence
evaluation），搜索深度可能会在8-10，实际深度可能会在20左右（很难讲清楚这句话的意思）。这个项目的后端和算法由我编写，前端部分由于知识空缺我参考了别人的代码。

·如果您对计算机博弈不了解，可能会对我的代码感到非常费解。由于过分的注重算法上面的思考，一些数据结构上的设计可能并不是很合理。我实现的环节更多会在我的个人网站: http://www.steponecloud.com 上进行阐释。

·目前还有很多优化的工作要做，我也只能抽出部分平时时间进行修改和完善。如果您有建议或问题想跟我交流，请邮件联系我。水平不高，能力有限，希望中国象棋能给大家带了放松与欢乐。

------

## 主要算法（介绍）

**1.minimax search 极大极小搜索：**
通过模拟双方落子和对局面进行静态评估获得某种走法的价值。模拟的步数即深度。模拟落子搜索的过程就是在对一棵树进行深度优先搜索（树的叶子结点其实就是在一个深度下的所有盘面情况）。一般称这种树为“博弈树”。

**2.alpha-beta变量剪枝：**对与上面的“博弈树”，我们通过两个变量 alpha、beta作为参数，帮助我们跳过某些不必要搜索的情况。事实上alpha-beta剪枝带来的搜索效率上的提升是巨大的。

**3.PVS搜索（很难用一两句话科普清楚，尤其是在不了解alpha- beta原理的前提下）：**是alpha-beta搜索的一个变种，叫做 “主变量导向搜索”。先通过启发搜索每层的
“主变量”（说白了就是选取一个路径作为主要搜索的），再对剩余路径进行一个初始值为 **{ alpha = beta - 1，且beta = [主变量搜索值] }**
的“零窗口搜索”等操作，以加快搜索速度。然而“主变量”的选取极为重要，并且折很大程度的影响搜索时速度。我在实际测试时感到PVS的效果并不如单纯的alpha-beta，我觉得如果使用“历史表启发”的方式这种搜索的效率会大大增加（如果不知道我在说什么请无视）。

**4.置换表记忆化的搜索：**
在之前的树搜索中会面临大量的重复情况，我们通过某种哈希算法存储棋盘盘面并在适当的时机存储，取出它们。甚至我们可以把置换表中的内容存储在文件中，每次搜索时进行更新，这样AI就有了“学习能力”（在我的程序中我并没有这么做，因为置换表可能会非常大，因此IO可能会比较慢，并且我不希望在我的笔记本电脑上有过于频繁且大量的硬盘IO读写操作，这样会降低硬盘寿命）。

**5.Zobrist校验码：**棋盘哈希算法，单纯Zobrist时不能保证所有盘面情况的哈希值时唯一的，使用双校验就可以了。

**6.MTDF：**基于一篇文章：*MTDF(f) A Minimax Algorithm faster than NegaScout* 作者 *Aske Plaat*
。MTDF是一种通过零窗口搜索和记忆化搜索实现的快速找到minimax值的算法，很神奇。效果也确实很好。

**8.迭代深入：**有了记忆化搜索后，可以按深度递增的顺序从depth =
1利用MTDF搜索到你想要的深度。这样的好处是很多情况已经被存储到置换表中了，因此更深层次的搜索也能利用起前面搜索的结果。可惜的是不知道为什么，我的实现中迭代深度的效果并不是很好，会稍微比MTDF慢一点，可能我还是没有理解到这套算法的精髓。

**9.静态评估Quiescence Evaluation：**静态搜索能克服
“水平线效应”（如果搜索的最后一步是吃掉对方的子或者对方吃掉自己的子，然后搜索就结束了，那显得很不公平，因为之后可能会发生换棋。由这种吃子导致的评估结果不符合实际的情况叫做
“水平线效应”）。静态搜索可能搜索深度会很深，导致搜索棋盘无比，因此在棋局开始时不建议采用静态搜索（可能因局面评估方式而异）。在我的实现中我将静态搜索加上了记忆化，并且使用MTDF方式优化，效果还不错。

------

## 配置、启动游戏

推荐使用Idea打开本项目。你需要有一个Tomcat服务器作为本项目的服务器，使用Tomcat运行程序，并在浏览器中打开网址 http://localhost:8080/vlumino/index.html

如果遇到困难请百度搜索 “idea配置tomcat”。

------

## 不会配置？可以进行在线游戏

您可以访问网站 http://www.steponecloud.com/vlumino/vlumino-game 进行在线的象棋对战，我的电脑会轮询服务器进行计算。但由于平时要上课，我的笔记本计算不会一直开启（有些费电）。

------

##  性能

由于搜索依赖CPU性能，因此您的CPU性能会直接影响搜索速度。如果您有 Cinebench R23（一个CPU基准测试软件），那么建议CPU跑分在7000以上的机器在本地使用。否则请降低搜索深度（上面的代码框中已经说明了设置深度的方法）。

------

## 评估步骤

```java
import pers.zr.vlumino.*;

class Test {
    public static void main(String[] args) {
        // 1 
        // 新建棋盘对象
        // 默认将所有棋摆好
        CChessboard board = new CChessboard();

        // 当然可以清空棋盘，此时棋盘上就没有子了
        // board.clearChessboard();
        
        // 2 
        // 设置棋盘到你要评估的盘面
        // 注意：棋盘靠下方的棋子是AI方，上方是对手方（虽然不是必须，但是很推荐这样，可以避免一些问题）
        // 注意：棋盘的内部是一个 10 * 9 的数组，0为没有棋子，注意不要越界
        board.innerChessboard[x][y] = CChess.JU;
        board.innerChessboard[x][y] = -CChess.JU;
        // ...
        // ...


        // 3-1 
        // 新建一个多线程评估器
        // MultiThreadEvaluator(board, depth, TreeType.MTDF)
        // 参数：
        // board 待评估棋盘；
        // depth 搜索深度；
        // treeType 使用的搜索树类型；
        // TreeType.ALPHA_BETA               使用alpha-beta搜索
        // TreeType.ALPHA_BETA_WITH_MEMORY	 使用记忆化的alpha-beta搜索
        // TreeType.MTDF										 使用MTDF进行记忆化alpha-beta搜索
        // TreeType.ITER_DEEPENING					 使用迭代深入（利用MTDF）
        // TreeType.QUIESCENCE							 使用静态搜索
        // TreeType.QUIESCENCE_WITH_MEMORY   使用记忆化的静态搜索
        // TreeType.MTDF_QUISCENCE           使用MTDF进行记忆化静态搜索
        // TreeType.PVS											 使用主变量导向搜索
        // 推荐的类型：盘面比价复杂时使用 MTDF，不复杂时使用 MTDF_QUISCENCE
        int depth = 8; // 搜索深度
        TreeType type = TreeType.MTDF; // 使用MTDF搜索
        MultiThreadEvaluator evaluator = new MultiThreadEvaluator(board, depth, treeType);
        // 3-2 
        // 也可以选择新建一个智能评估器
        SmartEvaluator evaluator = new Evaluator(board);
        
        // 4-1 
        // 获得最佳路径（使用MultiThreadEvaluator）
        ChessPath path = evaluator.getBestPathWithTreeSearching();
        // 4-2 
        // 获得最佳路径（使用SmartEvaluator）
        ChessPath path = evaluator.getPath();
        
        // 5 
        // 走棋
        board.movePath(path);

        // 6 
        // 悔棋
        board.undoMovePath(path);
        
        // 7 
        // 玩家在棋盘上落子
        ChessPath path = new ChessPath("21240"); // 将位置 (2, 1) 的棋子移动到 (2, 4)，
        // 吃子 0 【表示没有吃子】 
        board.movePath(path);
    }
}

```

------

## 可能的优化

在程序设计中有很多优化的方式。

1.在存储棋盘时完全可以采用一维数组。这样移动位于chessboard[pos]棋子的时候(比如向下移动一格)仅需chessboard[pos + 9] = chessboard[pos]
。这样能大大的加快评估的时间。但是在开始编码时我并没有考虑到。

2.神经网络。神经网络可以减少每个深度搜索时的路径选择数量，能很大程度的加大搜索深度。如果我有时间有精力我会在之后的版本中更新。

3.GPU优化。很容易想到的优化的方式。

4.历史表启发。将每层最好的路径保存在置换表中，每层选择的时候率先作为主变量使用它们，进行PVS搜索，能极大的加快速度。如果我有时间有精力我会在之后的版本中更新。

------

## 当前版本存在的问题
在本地运行时无法使用开局库搜索，是因为开局库路径问题（在GameBitGenerator中可以修改—）。在下个版本中会更新。

-----

## 致谢



感谢帮我进行测试的 @XU_JICHUN

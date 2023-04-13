package Mobius;

import org.omg.CORBA.FloatSeqHelper;
import org.omg.CORBA.REBIND;
import sun.management.snmp.jvmmib.JvmMemManagerTableMeta;

import java.lang.reflect.Field;
import java.util.*;

public class Solution {

    /**
     * 1447. 最简分数
     * 给你一个整数 n ，请你返回所有 0 到 1 之间（不包括 0 和 1）满足分母小于等于  n 的 最简 分数 。分数可以以 任意 顺序返回。
     * @param n
     * @return
     */
    public List<String> simplifiedFractions(int n) {
        //分子和分母互质
        List<String> ans = new ArrayList<>();
        for (int i = 1 ; i < n ;i ++) {
            for (int j = i + 1 ; j <= n ; j ++) {
                if (gcd(i ,j) == 1) {
                    String str = i + "/" + j;
                    ans.add(str);
                }
            }
        }
        return ans;
    }

    public int gcd(int a ,int b) {
        if (b != 0) {
            return gcd(b, a % b);
        }
        return a;
    }

    /**
     * 878. 第 N 个神奇数字
     * 如果正整数可以被 A 或 B 整除，那么它是神奇的。
     *
     * 返回第 N 个神奇数字。由于答案可能非常大，返回它模 10^9 + 7 的结果。
     * @param n
     * @param a
     * @param b
     * @return
     */
    public int nthMagicalNumber(int n, int a, int b) {
        //能被 A 或 B整除的最大的第N个数字是n * min(a, b);
        //1 - N中有多少个数可以被a, b 整除， n / a + n / b - n / lcm(a,b),lcm最小公倍数
        //f(x) 代表 1 - x中有多少个数字能被 a或b整除，求f(x) = n ，当x增大时，f(x) 是线性增大的
        //使用二分查找函数中第一个等于n的位置
        long l = 1, r = (long) n * (long) Math.min(a,b), mid;
        while (l < r) {
            mid = (l + r) >> 1;
            if (f(mid, a, b) < n) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }
        return (int) (l % (1e9 + 7));
    }

    public long f(long n,long a, long b) {
        return n / a + n / b - n / lcm(a,b);
    }

    public long lcm(long a ,long b) {
        return a * b / gcd(a,b);
    }

    public long gcd(long a, long b) {
        if (b != 0) {
            return gcd(b, a % b);
        }
        return a;
    }

    /**
     * 372. 超级次方
     * 你的任务是计算 ab 对 1337 取模，a 是一个正整数，b 是一个非常大的正整数且会以数组形式给出。
     * @param a
     * @param b
     * @return
     */
    public int superPow(int a, int[] b) {
        //例如 a 的200次方 等于 a^10^2 * a^10^0 * a^10^0
        int base = a, ans = 1, mod_num = 1337;
        for (int i = b.length - 1; i >= 0; i --) {
            ans = ans * power(base, b[i], mod_num) % mod_num;
            base = power(base, 10 ,mod_num);
        }
        return ans;
    }

    public int power(int a, int b ,int c) {
        //快速幂算法，求a 的 b次方，将b 按二进制展开， 例如1010，当遇到位权是1时，ans 乘以对应的base值
        int base = a % c, ans = 1;
        while (b != 0) {
            if ((b & 1) == 1) {
                ans = ans * base % c;
            }
            base = base * base % c;
            b >>= 1;
        }
        return ans;
    }

    /**
     * 1512. 好数对的数目
     * 给你一个整数数组 nums 。
     *
     * 如果一组数字 (i,j) 满足 nums[i] == nums[j] 且 i < j ，就可以认为这是一组 好数对 。
     *
     * 返回好数对的数目。
     * @param nums
     * @return
     */
    public int numIdenticalPairs(int[] nums) {
        //判断出现多少次
        int[] count = new int[101];
        int ans = 0;
        for (int i : nums) {
            ans += count[i];
            count[i] ++;
        }
        return ans;
    }

    /**
     * 1359. 有效的快递序列数目
     * 给你 n 笔订单，每笔订单都需要快递服务。
     *
     * 请你统计所有有效的 收件/配送 序列的数目，确保第 i 个物品的配送服务 delivery(i) 总是在其收件服务 pickup(i) 之后。
     *
     * 由于答案可能很大，请返回答案对 10^9 + 7 取余的结果。
     * @param n
     * @return
     */
    public int countOrders(int n) {
        //定义n - 1的方案数是f(n - 1)，则n个快递的方案数f(n)是怎么样的
        //定义收件为Pn，收件为Dn，则n - 1 个快递有 2(n - 1)种序列对；f(n - 1)中加入n的方式有几种，
        // 设Pn 和 Dn之间没有间隔，则方案有2(n - 1) + 1种方案；
        //Pn 和 Dn隔一个放，则方案有2(n - 1)种方案
        //..........,
        //Pn 和 Dn放到头尾，则方案有1种；
        //等差数列求和，(2(n - 1) + 1 + 1) * (2(n - 1) + 1) / 2 , 化简 2n * (2n - 1) / 2 = 2n^2 - n
        //f(n) = f(n - 1) * (2n^2 - n);
        long ans = 1, mod_num = (long) (1e9 + 7);
        for (int i = 2; i <= n; i ++) {
            ans = ans * (2 * i * i - i) % mod_num;
        }
        return (int )ans;
    }

    /**
     * 60. 排列序列
     * 给出集合 [1,2,3,...,n]，其所有元素共有 n! 种排列。
     *
     * 按大小顺序列出所有排列情况，并一一标记，当 n = 3 时, 所有排列如下：
     *
     * "123"
     * "132"
     * "213"
     * "231"
     * "312"
     * "321"
     * 给定 n 和 k，返回第 k 个排列。
     * @param n
     * @param k
     * @return
     */
    public String getPermutation(int n, int k) {
        return "";
    }

    /**
     * 941. 有效的山脉数组
     * 给定一个整数数组 arr，如果它是有效的山脉数组就返回 true，否则返回 false。
     *
     * 让我们回顾一下，如果 A 满足下述条件，那么它是一个山脉数组：
     *
     * arr.length >= 3
     * 在 0 < i < arr.length - 1 条件下，存在 i 使得：
     * arr[0] < arr[1] < ... arr[i-1] < arr[i]
     * arr[i] > arr[i+1] > ... > arr[arr.length - 1]
     * @param arr
     * @return
     */
    public boolean validMountainArray(int[] arr) {
        int i = 0, j = arr.length - 1;
        while (i < j && arr[i] < arr[i + 1]) {
            i ++;
        }
        while (j > i && arr[j] < arr[j - 1]) {
            j --;
        }
        return i == j && i != arr.length - 1 && i != 0;
    }

    /**
     * 289. 生命游戏
     * 根据 百度百科 ，生命游戏，简称为生命，是英国数学家约翰·何顿·康威在 1970 年发明的细胞自动机。
     *
     * 给定一个包含 m × n 个格子的面板，每一个格子都可以看成是一个细胞。每个细胞都具有一个初始状态：1 即为活细胞（live），
     * 或 0 即为死细胞（dead）。每个细胞与其八个相邻位置（水平，垂直，对角线）的细胞都遵循以下四条生存定律：
     *
     * 如果活细胞周围八个位置的活细胞数少于两个，则该位置活细胞死亡；
     * 如果活细胞周围八个位置有两个或三个活细胞，则该位置活细胞仍然存活；
     * 如果活细胞周围八个位置有超过三个活细胞，则该位置活细胞死亡；
     * 如果死细胞周围正好有三个活细胞，则该位置死细胞复活；
     * 下一个状态是通过将上述规则同时应用于当前状态下的每个细胞所形成的，其中细胞的出生和死亡是同时发生的。给你 m x n 网格面板 board 的当前状态，返回下一个状态。
     * @param board
     */
    public void gameOfLife(int[][] board) {
        int m = board.length, n = board[0].length;
        //方向数组
        int[][] dir = {{0,1},{1,0},
                {0,-1},{-1,0},
                {1,1},{-1,-1},
                {1,-1},{-1,1}};

        //统计数组
        int[][] cnt = new int[m][n];
        //统计每个位置周围8个方向的活细胞数量
        for (int i = 0; i < m; i ++) {
            for (int j = 0; j < n; j ++) {
                //遍历周围8个位置
                for (int k = 0; k < 8; k ++) {
                    int x = dir[k][0] + i;
                    int y = dir[k][1] + j;
                    //说明下标超界
                    if (x < 0 || x >= m) {
                        continue;
                    }
                    if (y < 0 || y >= n) {
                        continue;
                    }
                    cnt[i][j] += board[x][y];
                }
            }
        }
        for (int i = 0; i < m; i ++) {
            for (int j = 0; j < n; j ++) {
                //如果活细胞周围八个位置的活细胞数少于两个，则该位置活细胞死亡；
                //     * 如果活细胞周围八个位置有两个或三个活细胞，则该位置活细胞仍然存活；
                //     * 如果活细胞周围八个位置有超过三个活细胞，则该位置活细胞死亡；
                //     * 如果死细胞周围正好有三个活细胞，则该位置死细胞复活；
                if (board[i][j] == 1) {
                    if (cnt[i][j] != 2 && cnt[i][j] != 3) {
                        board[i][j] = 0;
                    }
                } else {
                    if (cnt[i][j] == 3) {
                        board[i][j] = 1;
                    }
                }
            }
        }
    }

    /**
     * 754. 到达终点数字
     * 在一根无限长的数轴上，你站在0的位置。终点在target的位置。
     *
     * 每次你可以选择向左或向右移动。第 n 次移动（从 1 开始），可以走 n 步。
     *
     * 返回到达终点需要的最小移动次数。
     * @param target
     * @return
     */
    public int reachNumber(int target) {
        //一开始每步都往前走，走k步， 会到达 (1 + k)k/2 点，设这个点为m,
        //如果m超过target这个点，超过的长度为n; 那么我们只需要在m中减去 n/2个长度，如果减去n个长度，就会多减去一倍的长度；
        //而且只有当n是偶数，上述才成立；
        //并且 1 到 m 中取任意一个值，我们都会找到相应的数字相加等于他
        target = Math.abs(target);
        //k初始值为target开方并向下取整
        int k = (int) Math.floor(Math.sqrt(target));
        while ((1 + k) * k / 2 < target){
            k ++;
        }
        int n = (1 + k) * k / 2 - target;
        while (n % 2 != 0) {
            k ++;
            n += k;
        }
        return k;
    }

    /**
     * 132. 分割回文串 II
     * 给你一个字符串 s，请你将 s 分割成一些子串，使每个子串都是回文。
     *
     * 返回符合要求的 最少分割次数 。
     * @param s
     * @return
     */
    public int minCut(String s) {
        //dp[i] = dp[j] + 1;
        List<List<Integer>> idx = new ArrayList<>();
        for (int i = 0 ; i <= s.length() ; i ++) {
            idx.add(new ArrayList<>());
        }
        for (int i = 0; i < s.length(); i ++) {
            //处理奇数长度的回文串
            extract(s,i,i, idx);
            //处理偶数长度的回文串
            extract(s,i,i + 1, idx);
        }
        int[] dp = new int[s.length() + 1];
        dp[0] = 0;
        for (int i = 1 ; i <= s.length() ; i ++) {
            //默认最大分割次数
            dp[i] = i;
            for (int j : idx.get(i)) {
                dp[i] = Math.min(dp[i],dp[j] + 1);
            }
        }
        return dp[s.length()] - 1;
    }

    public void extract(String s,int i, int j, List<List<Integer>> idx) {
        //预处理回文数据,回文的起始下标，记录每个回文串前一个回文的下标
        while (i >= 0 && j < s.length() && s.charAt(i) == s.charAt(j)) {
            idx.get(j + 1).add(i);
            i --;
            j ++;
        }
    }

    /**
     * 1155. 掷骰子的N种方法
     * 这里有 d 个一样的骰子，每个骰子上都有 f 个面，分别标号为 1, 2, ..., f。
     *
     * 我们约定：掷骰子的得到总点数为各骰子面朝上的数字的总和。
     *
     * 如果需要掷出的总点数为 target，请你计算出有多少种不同的组合情况（所有的组合情况总共有 f^d 种），模 10^9 + 7 后返回。
     * @param n
     * @param k
     * @param target
     * @return
     */
    public int numRollsToTarget(int n, int k, int target) {
        //递推数组，定义dp[i][j]，dp[i][j] 等于 dp[i - 1][j - m]，m从1开始到 f的方法总数
        int[][] dp = new int[n + 1][target + 1];
        int mod = (int) (1e9 + 7);
        dp[0][0] = 1;
        //遍历骰子个数
        for (int i = 1 ; i <= n; i ++) {
            //遍历骰子的和，i个骰子，最小值是 i;
            for (int j = i; j <= target; j ++) {
                for (int m = 1; m <= k; m ++) {
                    if (j < m) {
                        break;
                    }
                    dp[i][j] += dp[i - 1][j - m];
                    dp[i][j] %= mod;
                }
            }
        }
        return dp[n][target];
    }

    /**
     * 1147. 段式回文
     * 段式回文 其实与 一般回文 类似，只不过是最小的单位是 一段字符 而不是 单个字母。
     *
     * 举个例子，对于一般回文 "abcba" 是回文，而 "volvo" 不是，但如果我们把 "volvo" 分为 "vo"、"l"、"vo" 三段，则可以认为 “(vo)(l)(vo)” 是段式回文（分为 3 段）。
     *
     * 给你一个字符串 text，在确保它满足段式回文的前提下，请你返回 段 的 最大数量 k。
     *
     * 如果段的最大数量为 k，那么存在满足以下条件的 a_1, a_2, ..., a_k：
     *
     * 每个 a_i 都是一个非空字符串；
     * 将这些字符串首位相连的结果 a_1 + a_2 + ... + a_k 和原始字符串 text 相同；
     * 对于所有1 <= i <= k，都有 a_i = a_{k+1 - i}。
     * @param text
     * @return
     */
    public int longestDecomposition(String text) {
        return getResult(text,0 ,text.length() - 1);
    }

    public int getResult(String text, int l , int r) {
        //将指定字符串从两头往中间匹配，匹配上后去掉以匹配的，继续匹配
        int n = r - l + 1;
        //如果长度不大于1，则返回
        if (n <= 1) {
            return n;
        }
        //循环匹配的字符串的长度，从1到n/2
        for (int i = 1, m = n / 2; i <= m; i ++) {
            boolean flag = true;
            //设立两个指针，指向左右两边，循环向前走
            for (int j = l , k = r - i + 1; k <= r ; j ++ , k ++) {
                if (text.charAt(j) == text.charAt(k)) {
                    continue;
                }
                flag = false;
                break;
            }
            if (flag) {
                return getResult(text,l + i, r - i) + 2;
            }
        }
        return 1;
    }

    /**
     * 2100. 适合打劫银行的日子
     * 你和一群强盗准备打劫银行。给你一个下标从 0 开始的整数数组 security ，其中 security[i] 是第 i 天执勤警卫的数量。日子从 0 开始编号。同时给你一个整数 time 。
     *
     * 如果第 i 天满足以下所有条件，我们称它为一个适合打劫银行的日子：
     *
     * 第 i 天前和后都分别至少有 time 天。
     * 第 i 天前连续 time 天警卫数目都是非递增的。
     * 第 i 天后连续 time 天警卫数目都是非递减的。
     * 更正式的，第 i 天是一个合适打劫银行的日子当且仅当：security[i - time] >= security[i - time + 1] >= ... >= security[i] <= ... <= security[i + time - 1] <= security[i + time].
     *
     * 请你返回一个数组，包含 所有 适合打劫银行的日子（下标从 0 开始）。返回的日子可以 任意 顺序排列。
     * @param security
     * @param time
     * @return
     */
    public List<Integer> goodDaysToRobBank(int[] security, int time) {
        int n = security.length;
        List<Integer> ans = new ArrayList<>();
        if (n == 0) {
            return ans;
        }
        if (n == 1) {
            if (time <= 1) {
                ans.add(0);
            }
            return ans;
        }
        int[] left = new int[n];
        int[] right = new int[n];
        int p = 0;
        //从左至右记录每个点连续递减的天数
        for (int i = 1; i < n ; i ++) {
            if (security[i] > security[i - 1]) {
                p = i;
            }
            left[i] = i - p;
        }
        p = n - 1;
        //从右至左记录每个点连续递减的天数
        for (int j = n - 2; j >= 0; j --) {
            if (security[j] > security[j + 1]) {
                p = j;
            }
            right[j] = p - j;
        }
        for (int i = 0; i < n ; i ++) {
            if (left[i] >= time &&  right[i]>= time) {
                ans.add(i);
            }
        }
        return ans;
    }

    /**
     * LCP 30. 魔塔游戏
     * 小扣当前位于魔塔游戏第一层，共有 N 个房间，编号为 0 ~ N-1。每个房间的补血道具/怪物对于血量影响记于数组 nums，
     * 其中正数表示道具补血数值，即血量增加对应数值；负数表示怪物造成伤害值，即血量减少对应数值；0 表示房间对血量无影响。
     *
     * 小扣初始血量为 1，且无上限。假定小扣原计划按房间编号升序访问所有房间补血/打怪，为保证血量始终为正值，
     * 小扣需对房间访问顺序进行调整，每次仅能将一个怪物房间（负数的房间）调整至访问顺序末尾。请返回小扣最少需要调整几次，
     * 才能顺利访问所有房间。若调整顺序也无法访问完全部房间，请返回 -1。
     * @param nums
     * @return
     */
    public int magicTower(int[] nums) {
        if (Arrays.stream(nums).sum() < 0) {
            return -1;
        }

        PriorityQueue<Integer> queue = new PriorityQueue<>();
        int n = nums.length;
        long HP = 1;
        int cnt = 0;
        //往后的过程中，当遇到怪物，将其加入堆中
        for (int i = 0; i < n ; i ++) {
            if (nums[i] < 0) {
                queue.offer(nums[i]);
                //如果打不过，则从堆中将一个最小值放到后面去
                if (HP + nums[i] <= 0) {
                    HP -= queue.poll();
                    cnt ++;
                }
            }
            HP += nums[i];
        }
        return cnt;
    }

    /**
     * 877. 石子游戏
     * Alice 和 Bob 用几堆石子在做游戏。一共有偶数堆石子，排成一行；每堆都有 正 整数颗石子，数目为 piles[i] 。
     *
     * 游戏以谁手中的石子最多来决出胜负。石子的 总数 是 奇数 ，所以没有平局。
     *
     * Alice 和 Bob 轮流进行，Alice 先开始 。 每回合，玩家从行的 开始 或 结束 处取走整堆石头。
     * 这种情况一直持续到没有更多的石子堆为止，此时手中 石子最多 的玩家 获胜 。
     *
     * 假设 Alice 和 Bob 都发挥出最佳水平，当 Alice 赢得比赛时返回 true ，当 Bob 赢得比赛时返回 false 。
     * @param piles
     * @return
     */
    public boolean stoneGame(int[] piles) {
        //只要是先手，就可以控制第一次和第二次相加之和为最多，以此类推，先手必赢
        return true;
    }


    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
    /**
     * 623. 在二叉树中增加一行
     * 给定一个二叉树，根节点为第1层，深度为 1。在其第 d 层追加一行值为 v 的节点。
     *
     * 添加规则：给定一个深度值 d （正整数），针对深度为 d-1 层的每一非空节点 N，为 N 创建两个值为 v 的左子树和右子树。
     *
     * 将 N 原先的左子树，连接为新节点 v 的左子树；将 N 原先的右子树，连接为新节点 v 的右子树。
     *
     * 如果 d 的值为 1，深度 d - 1 不存在，则创建一个新的根节点 v，原先的整棵树将作为 v 的左子树。
     * @param root
     * @param val
     * @param depth
     * @return
     */
    public TreeNode addOneRow(TreeNode root, int val, int depth) {
        if (root == null) {
            return null;
        }
        if (depth == 1) {
            TreeNode treeNode = new TreeNode(val);
            treeNode.left = root;
            return treeNode;
        } else if (depth - 1 == 1){
            TreeNode left = new TreeNode(val);
            TreeNode right = new TreeNode(val);
            left.left = root.left;
            right.right = root.right;
            root.left = left;
            root.right = right;
        } else {
            root.left = addOneRow(root.left, val, depth - 1);
            root.right = addOneRow(root.right, val, depth - 1);
        }

        return root;
    }

    /**
     * 934. 最短的桥
     * 在给定的二维二进制数组 A 中，存在两座岛。（岛是由四面相连的 1 形成的一个最大组。）
     *
     * 现在，我们可以将 0 变为 1，以使两座岛连接起来，变成一座岛。
     *
     * 返回必须翻转的 0 的最小数目。（可以保证答案至少是 1 。）
     *
     * 2 <= A.length == A[0].length <= 100
     * @param grid
     * @return
     */
    public int shortestBridge(int[][] grid) {
        int res = 0;
        int n = grid.length;
        for (int i = 0; i < n; i ++) {
            for (int j = 0 ; j < n ; j ++) {
                //遇到第一个1 后，
                if (grid[i][j] == 1) {
                    //将第一块地图描绘出来
                    dfs(grid,i,j);
                    //采用广搜，将两块地图连接，第一次连接就为最短距离
                    while (!bfs(grid)) {
                        res ++;
                    }
                    return res;
                }
            }
        }
        return -1;
    }

    public boolean color (int[][] grid,int i , int j) {
        int n = grid.length;
        if (i < 0 || i >= n || j < 0 || j >= n) {
            return false;
        }
        if (grid[i][j] == 1) {
            return true;
        }
        //将扩展的标记为3
        if (grid[i][j] == 0) {
            grid[i][j] = 3;
        }
        return false;
    }

    public boolean bfs (int[][] grid) {
        int n = grid.length;
        //广搜，遇到第一块陆地，则向四面扩展
        for (int i = 0 ;i < n ; i ++ ) {
            for (int j = 0 ; j < n ; j ++) {
                if (grid[i][j] == 2) {
                    if (color(grid,i - 1,j)
                        || color(grid,i ,j - 1)
                        || color(grid,i + 1,j)
                            || color(grid,i,j + 1)) {
                        return true;
                    }
                }
            }
        }
        //将已扩展的标记为2，作为岛屿的延申
        for (int i = 0 ;i < n ; i ++ ) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 3) {
                    grid[i][j] = 2;
                }
            }
        }

        return false;
    }

    public void dfs (int[][] grid, int i , int j) {
        int n = grid.length;
        if (i < 0 || i >= n || j < 0 || j >= n || grid[i][j] != 1) {
            return;
        }
        //深搜，从上下左右，将遇到的1 变为2 ，代表走过了
        grid[i][j] = 2;
        dfs(grid, i - 1,j);
        dfs(grid, i,j - 1);
        dfs(grid, i + 1,j);
        dfs(grid, i ,j + 1);
    }

    /**
     * 剑指 Offer 14- II. 剪绳子 II
     * 给你一根长度为 n 的绳子，请把绳子剪成整数长度的 m 段（m、n都是整数，n>1并且m>1），
     * 每段绳子的长度记为 k[0],k[1]...k[m - 1] 。请问 k[0]*k[1]*...*k[m - 1] 可能的最大乘积是多少？
     * 例如，当绳子的长度是8时，我们把它剪成长度分别为2、3、3的三段，此时得到的最大乘积是18。
     *
     * 答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1。
     * @param n
     * @return
     */
    public int cuttingRope(int n) {
        //按3截取，和最大
        if (n <= 3) {
            return n - 1;
        }
        long mod = 1000000007;
        long sum = 1;
        while (n > 4) {
            sum = sum * 3 % mod;
            n -= 3;
        }
        return (int) (sum * n % mod);
    }

    /**
     * 1863. 找出所有子集的异或总和再求和
     * 一个数组的 异或总和 定义为数组中所有元素按位 XOR 的结果；如果数组为 空 ，则异或总和为 0 。
     *
     * 例如，数组 [2,5,6] 的 异或总和 为 2 XOR 5 XOR 6 = 1 。
     * 给你一个数组 nums ，请你求出 nums 中每个 子集 的 异或总和 ，计算并返回这些值相加之 和 。
     *
     * 注意：在本题中，元素 相同 的不同子集应 多次 计数。
     *
     * 数组 a 是数组 b 的一个 子集 的前提条件是：从 b 删除几个（也可能不删除）元素能够得到 a 。
     * @param nums
     * @return
     */
    int result = 0;
    public int subsetXORSum(int[] nums) {
        //每个值可以异或，也可以不异或，使用深搜
        for (int i = 0; i < nums.length; i++) {
            xordfs(i + 1, nums[i] , nums);
        }
        return result;
    }

    public void xordfs (int i , int sum , int[] nums) {
        if (i == nums.length) {
            result += sum;
        } else {
            xordfs(i + 1, nums[i], nums);
            xordfs(i, nums[i] ^ sum, nums);
        }
    }

    /**
     * 1094. 拼车
     * 假设你是一位顺风车司机，车上最初有 capacity 个空座位可以用来载客。由于道路的限制，
     * 车 只能 向一个方向行驶（也就是说，不允许掉头或改变方向，你可以将其想象为一个向量）。
     *
     * 这儿有一份乘客行程计划表 trips[][]，其中 trips[i] = [num_passengers, start_location, end_location] 包含了第 i 组乘客的行程信息：
     *
     * 必须接送的乘客数量；
     * 乘客的上车地点；
     * 以及乘客的下车地点。
     * 这些给出的地点位置是从你的 初始 出发位置向前行驶到这些地点所需的距离（它们一定在你的行驶方向上）。
     *
     * 请你根据给出的行程计划表和车子的座位数，来判断你的车是否可以顺利完成接送所有乘客的任务（当且仅当你可以在所有给定的行程中接送所有乘客时，
     * 返回 true，否则请返回 false）。
     * trips.length <= 1000
     * @param trips
     * @param capacity
     * @return
     */
    public boolean carPooling(int[][] trips, int capacity) {
        int[] count = new int[1001];
        for (int[] trip : trips) {
            int i = trip[0];
            int j = trip[1];
            int k = trip[2];
            //使用差分数组的性质
            count[j] += i;
            count[k] -= i;
        }
        for (int i = 0; i < 1001; i ++) {
            capacity -= count[i];
            if (capacity < 0) {
                return false;
            }
        }
        return true;
    }


    class Node {
        public int val;
        public Node next;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _next) {
            val = _val;
            next = _next;
        }
    }
    /**
     * 剑指 Offer II 029. 排序的循环链表
     * 给定循环单调非递减列表中的一个点，写一个函数向这个列表中插入一个新元素 insertVal ，使这个列表仍然是循环升序的。
     *
     * 给定的可以是这个列表中任意一个顶点的指针，并不一定是这个列表中最小元素的指针。
     *
     * 如果有多个满足条件的插入位置，可以选择任意一个位置插入新的值，插入后整个列表仍然保持有序。
     *
     * 如果列表为空（给定的节点是 null），需要创建一个循环有序列表并返回这个节点。否则。请返回原先给定的节点。
     * @param head
     * @param insertVal
     * @return
     */
    public Node insert(Node head, int insertVal) {
        if (head == null) {
            Node node = new Node(insertVal);
            node.next = node;
            return node;
        }
        Node p = head,max = head;
        while (true) {
            int m = p.val;
            int n = p.next.val;
            //说明到达最后面一个值，下一个值又从头开始，记录max = p
            if (m > n) {
                max = p;
            }
            //说明在这两个节点中间
            if (m <= insertVal && insertVal <= n) {
                break;
            }
            //说明在最大最小值中间
            if (p.next == head) {
                p = max;
                break;
            }
            p = p.next;
        }
        if (p != null) {
            p.next = new Node(insertVal, p.next);
        }
        return head;
    }

    /**
     * 1201. 丑数 III
     * 给你四个整数：n 、a 、b 、c ，请你设计一个算法来找出第 n 个丑数。
     *
     * 丑数是可以被 a 或 b 或 c 整除的 正整数 。
     * @param n
     * @param a
     * @param b
     * @param c
     * @return
     */
    long a = 1,b = 1,c = 1,ab = 1,ac = 1,bc = 1,abc = 1;
    public int nthUglyNumber(int n, int a, int b, int c) {
        //求出数值范围，最大值丑数不超过 n * min(a,b,c)
        //设最大值为x， 则个数为（x/a） + (x/b) + (x/c) - (x/ab) - (x/ac) - (x/bc) + (x/abc)
        int min = Math.min(a,Math.min(b,c));
        long head = 1, tail = n * min, mid;
        this.a = a;
        this.b = b;
        this.c = c;
        this.ab = lcm1(a,b);
        this.ac = lcm1(a,c);
        this.bc = lcm1(b,c);
        this.abc = lcm1(lcm1(a,b),c);
        //二分查找,找的是第一个大于等于n 的位置
        while (head < tail) {
            mid = (head + tail) / 2;
            if (get(mid) < n) {
                head = mid + 1;
            } else {
                tail = mid;
            }
        }
        return (int) head;
    }

    public long gcd1(long a, long b) {
        //获取a,b最大公约数
        if (b != 0) {
            return gcd1(b, a % b);
        }
        return a;
    }

    public long lcm1(long a , long b) {
        //求最小公倍数
        return a * b/gcd1(a, b);
    }

    public long get (long x) {
        //获得最大值为x的 丑数个数
        return (x/a) + (x/b) + (x/c) - (x/ab) - (x/ac) - (x/bc) + (x/abc);
    }

    /**
     * 229. 求众数 II
     * 给定一个大小为 n 的整数数组，找出其中所有出现超过 ⌊ n/3 ⌋ 次的元素。
     * @param nums
     * @return
     */
    public List<Integer> majorityElement(int[] nums) {
        //抵消法，先求超过n/2个数的元素，准备一个空格子，依次将序列中的数据放入格子中并计数，
        //后一个元素放入时判断和格子中的元素是否相同，如果不相同就将格子中的计数减1，减到0就
        //清除，和要放入的元素抵消，最后剩下的数就是大于n/2的元素
        //求超过n/3的元素，则准备两个空格子
        //计数，采用二维数组，记录元素和元素个数
        int[][] count = new int[2][2];
        //最终在格子里面的是可能大于n/3的值
        for (int x : nums) {
            if (count[0][1] != 0 && x == count[0][0]) {
                count[0][1] ++;
            } else if (count[1][1] != 0 && x == count[1][0]) {
                count[1][1] ++;
            } else if (count[0][1] == 0) {
                count[0][0] = x;
                count[0][1] = 1;
            } else if (count[1][1] == 0) {
                count[1][0] = x;
                count[1][1] = 1;
            } else {
                count[0][1] --;
                count[1][1] --;
            }
        }
        count[0][1] = count[1][1] = 0;
        for (int x : nums) {
            if (x == count[0][0]) {
                count[0][1] ++;
            } else if (x == count[1][0]) {
                count[1][1] ++;
            }
        }
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 2; i ++) {
            if (count[i][1] > nums.length / 3) {
                list.add(count[i][0]);
            }
        }
        return list;
    }

    /**
     *
     1819. 序列中不同最大公约数的数目
     给你一个由正整数组成的数组 nums 。

     数字序列的 最大公约数 定义为序列中所有整数的共有约数中的最大整数。

     例如，序列 [4,6,16] 的最大公约数是 2 。
     数组的一个 子序列 本质是一个序列，可以通过删除数组中的某些元素（或者不删除）得到。

     例如，[2,5,10] 是 [1,2,1,2,4,1,5,10] 的一个子序列。
     计算并返回 nums 的所有 非空 子序列中 不同 最大公约数的 数目 。
     1 <= nums.length <= 10^5
     1 <= nums[i] <= 2 * 10^5
     * @param nums
     * @return
     */
    public int countDifferentSubsequenceGCDs(int[] nums) {
        //最大值为20000，开辟一个数组
        int[] g = new int[200005];
        int max = 0;
        //将nums中的值放入对应的位置，去掉重复数据，并求出最大值
        for (int x : nums) {
            max = Math.max(x,max);
            g[x] = x;
        }

        //枚举最大公约数，从1 到 max开始枚举，在原序列中如果有最大公约数等于i的序列，
        //说明找到了最大公约数为i的序列
        int cnt = 0;
        for (int i = 1; i <= max; i ++) {
            int ans = -1;
            for (int j = i; j <= max; j += i) {
                if (g[j] == 0) {
                    continue;
                }
                if (ans == -1) {
                    ans = j;
                } else {
                    ans = gcd(ans,j);
                }
            }
            if (ans == i) {
                cnt ++;
            }
        }
        return cnt;
    }

    /**
     * 2001. 可互换矩形的组数
     * 用一个下标从 0 开始的二维整数数组 rectangles 来表示 n 个矩形，其中 rectangles[i] = [widthi, heighti]
     * 表示第 i 个矩形的宽度和高度。
     *
     * 如果两个矩形 i 和 j（i < j）的宽高比相同，则认为这两个矩形 可互换 。
     * 更规范的说法是，两个矩形满足 widthi/heighti == widthj/heightj（使用实数除法而非整数除法），则认为这两个矩形 可互换 。
     *
     * 计算并返回 rectangles 中有多少对 可互换 矩形。
     * @param rectangles
     * @return
     */
    public long interchangeableRectangles(int[][] rectangles) {
        Map<Double,Integer> map = new HashMap<>();
        long ans = 0;
        for (int[] x : rectangles) {
            double n = (double) x[0] / (double) x[1];
            if (map.containsKey(n)) {
                ans += map.get(n);
                map.put(n, map.get(n) + 1);
            } else {
                map.put(n, 1);
            }
        }
        return ans;
    }

    /**
     * 926. 将字符串翻转到单调递增
     * 如果一个由 '0' 和 '1' 组成的字符串，是以一些 '0'（可能没有 '0'）后面跟着一些 '1'（也可能没有 '1'）的形式组成的，
     * 那么该字符串是单调递增的。
     *
     * 我们给出一个由字符 '0' 和 '1' 组成的字符串 S，我们可以将任何 '0' 翻转为 '1' 或者将 '1' 翻转为 '0'。
     *
     * 返回使 S 单调递增的最小翻转次数。
     * @param s
     * @return
     */
    public int minFlipsMonoIncr(String s) {
        char[] chars = s.toCharArray();
        int n0 = 0, n1 = 0, ans = 0;
        //记录有多少个0
        for (char x : chars) {
            if (x == '0') {
                n0 ++;
            }
        }
        //统计当前字符之后有多少个0，之前有多少个1
        ans = n0;
        for (char x : chars) {
            if (x == '0') {
                n0 --;
            } else {
                n1 ++;
            }
            //如果要递增，需要将n1翻转为0，0翻转为1
            ans = Math.min(ans, n0 + n1);
        }
        return ans;
    }

    /**
     * 1004. 最大连续1的个数 III
     * 给定一个由若干 0 和 1 组成的数组 A，我们最多可以将 K 个值从 0 变成 1 。
     *
     * 返回仅包含 1 的最长（连续）子数组的长度。
     *
     * 输入：A = [1,1,1,0,0,0,1,1,1,1,0], K = 2
     * 输出：6
     * 解释：
     * [1,1,1,0,0,1,1,1,1,1,1]
     * 粗体数字从 0 翻转到 1，最长的子数组长度为 6。
     * @param nums
     * @param k
     * @return
     */
    public int longestOnes(int[] nums, int k) {
        //滑动窗口，判断窗口内的0的个数是否小于k
        int l = 0, r = -1, ans = 0, n0 = 0;
        while (true) {
            if (n0 <= k) {
                r ++;
                if (r == nums.length) {
                    break;
                }
                if (nums[r] == 0) {
                    n0 ++;
                }
            } else {
                if (nums[l] == 0) {
                    n0 --;
                }
                l ++;
            }
            if (n0 <= k) {
                ans = Math.max(ans, r - l + 1);
            }
        }
        return ans;
    }

    /**
     * 914. 卡牌分组
     * 给定一副牌，每张牌上都写着一个整数。
     *
     * 此时，你需要选定一个数字 X，使我们可以将整副牌按下述规则分成 1 组或更多组：
     *
     * 每组都有 X 张牌。
     * 组内所有的牌上都写着相同的整数。
     * 仅当你可选的 X >= 2 时返回 true。
     * @param deck
     * @return
     */
    public boolean hasGroupsSizeX(int[] deck) {
        int[] cnt = new int[10000];
        //统计每张牌的数量，按条件可知，相同牌的数量一定是X的倍数
        for (int d : deck) {
            cnt[d] ++;
        }
        int x = -1;
        //求相同牌数量之间的最大公约数
        for (int d : cnt) {
            if (d == 0) {
                continue;
            }
            if (x == -1) {
                x = d;
            } else {
                x = gcd(d,x);
            }
            if (x == 1) {
                break;
            }
        }
        return x > 1;
    }

    /**
     * 457. 环形数组是否存在循环
     * 存在一个不含 0 的 环形 数组 nums ，每个 nums[i] 都表示位于下标 i 的角色应该向前或向后移动的下标个数：
     *
     * 如果 nums[i] 是正数，向前（下标递增方向）移动 |nums[i]| 步
     * 如果 nums[i] 是负数，向后（下标递减方向）移动 |nums[i]| 步
     * 因为数组是 环形 的，所以可以假设从最后一个元素向前移动一步会到达第一个元素，而第一个元素向后移动一步会到达最后一个元素。
     *
     * 数组中的 循环 由长度为 k 的下标序列 seq 标识：
     *
     * 遵循上述移动规则将导致一组重复下标序列 seq[0] -> seq[1] -> ... -> seq[k - 1] -> seq[0] -> ...
     * 所有 nums[seq[j]] 应当不是 全正 就是 全负
     * k > 1
     * 如果 nums 中存在循环，返回 true ；否则，返回 false 。
     * @param nums
     * @return
     */
    public boolean circularArrayLoop(int[] nums) {
        //将循环数组当成一个循环列表，判断是否有环使用快慢指针
        int n = nums.length;
        //遍历每一个可能循环的起始点
        for (int i = 0; i < n; i ++) {
            //如果走过，则跳过，优化
            if (Math.abs(nums[i]) > 1000) {
                continue;
            }
            int p = i, q = i;
            do {
                p = getNext(p, nums);
                q = getNext(getNext(q, nums), nums);
            } while (p != q);
            //正数个数，负数个数，循环长度
            int l = 0,r = 0, len = 0;
            do {
                if (nums[p] > 0) {
                    l ++;
                } else {
                    r ++;
                }
                p = getNext(p, nums);
                len ++;
            } while (p != q);

            if ((l == 0 || r == 0) && len > 1) {
                return true;
            }
        }
        return false;
    }

    public int getNext(int i , int[] nums) {
        //-1000 <= nums[i] <= 1000
        //优化，定义一个cycle 表示走了1000圈
        int n = nums.length;
        int cycle = 1000 * n;

        if (nums[i] < 0) {
            cycle = -cycle;
        }
        //表示使用过
        nums[i] += cycle;
        //可能存在负数，如果是负数就再加一个长度，变成正数下标
        return ((i + nums[i]) % n + n) % n;
    }

    /**
     * LCP 02. 分式化简
     * 有一个同学在学习分式。他需要将一个连分数化成最简分数，你能帮助他吗？
     * 连分数是形如上图的分式。在本题中，所有系数都是大于等于0的整数。
     * 输入的cont代表连分数的系数（cont[0]代表上图的a0，以此类推）。
     * 返回一个长度为2的数组[n, m]，使得连分数的值等于n / m，且n, m最大公约数为1。
     * 示例 1：
     *
     * 输入：cont = [3, 2, 0, 2]
     * 输出：[13, 4]
     * 解释：原连分数等价于3 + (1 / (2 + (1 / (0 + 1 / 2))))。注意[26, 8], [-13, -4]都不是正确答案。
     * 示例 2：
     *
     * 输入：cont = [0, 0, 3]
     * 输出：[3, 1]
     * 解释：如果答案是整数，令分母为1即可。
     *
     * 限制：
     *
     * cont[i] >= 0
     * 1 <= cont的长度 <= 10
     * cont最后一个元素不等于0
     * 答案的n, m的取值都能被32位int整型存下（即不超过2 ^ 31 - 1）
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/deep-dark-fraction
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * @param cont
     * @return
     */
    public int[] fraction(int[] cont) {
        //分数在最终表示为(a0 + (1 / (a1 + (1 / (a2 + (0 / 1))))))
        //最终的分子为0，分母为1，，a1 + (1 / (a2 + (0 / 1)))等价于 （a1*a2 + 1） / a2
        int m = 0,n = 1,j = cont.length;
        for (int i = j - 1; i >= 0; i --) {
            int k = m;
            m = n;
            n = k;
            n += m * cont[i];
            k = gcd(m,n);
            m /= k;
            n /= k;
        }
        int[] res = new int[2];
        res[0] = n;
        res[1] = m;
        return res;
    }

    /**
     * 1031. 两个非重叠子数组的最大和
     * 给出非负整数数组 A ，返回两个非重叠（连续）子数组中元素的最大和，子数组的长度分别为 L 和 M。
     * （这里需要澄清的是，长为 L 的子数组可以出现在长为 M 的子数组之前或之后。）
     *
     * 从形式上看，返回最大的 V，而 V = (A[i] + A[i+1] + ... + A[i+L-1]) + (A[j] + A[j+1] + ... + A[j+M-1])
     * 并满足下列条件之一：
     *
     * 0 <= i < i + L - 1 < j < j + M - 1 < A.length, 或
     * 0 <= j < j + M - 1 < i < i + L - 1 < A.length.
     * @param nums
     * @param firstLen
     * @param secondLen
     * @return
     */
    public int maxSumTwoNoOverlap(int[] nums, int firstLen, int secondLen) {
        //需要从数组两端维护在对应位置firstLen、secondLen非重叠子数组的最大和值
        int n = nums.length;
        int[] lmax = new int[n + 1], rmax = new int[n + 1];
        //维护两个长度的滑动窗口
        for (int i = n - 1,lsum = 0, rsum = 0; i >= 0 ; i --) {
            lsum += nums[i];
            rsum += nums[i];
            //说明firstlen的窗口出去了
            if (i + firstLen < n) {
                lsum -= nums[i + firstLen];
            }
            //说明secondLen的窗口出去了
            if (i + secondLen < n) {
                rsum -= nums[i + secondLen];
            }
            lmax[i] = Math.max(lsum, lmax[i + 1]);
            rmax[i] = Math.max(rsum, rmax[i + 1]);
        }
        int ans = 0;
        for (int i = 0,lsum = 0, rsum = 0; i < n; i ++) {
            lsum += nums[i];
            rsum += nums[i];

            //说明firstLen出去了
            if (i >= firstLen) {
                lsum -= nums[i - firstLen];
            }
            if (i >= secondLen) {
                rsum -= nums[i - secondLen];
            }
            ans = Math.max(ans, lsum + rmax[i + 1]);
            ans = Math.max(ans, rsum + lmax[i + 1]);
        }
        return ans;
    }

    /**
     * 502. IPO
     * 假设 力扣（LeetCode）即将开始 IPO 。为了以更高的价格将股票卖给风险投资公司，力扣 希望在 IPO 之前开展一些项目以增加其资本。 由于资源有限，它只能在 IPO 之前完成最多 k 个不同的项目。帮助 力扣 设计完成最多 k 个不同项目后得到最大总资本的方式。
     *
     * 给你 n 个项目。对于每个项目 i ，它都有一个纯利润 profits[i] ，和启动该项目需要的最小资本 capital[i] 。
     *
     * 最初，你的资本为 w 。当你完成一个项目时，你将获得纯利润，且利润将被添加到你的总资本中。
     *
     * 总而言之，从给定项目中选择 最多 k 个不同项目的列表，以 最大化最终资本 ，并输出最终可获得的最多资本。
     *
     * 答案保证在 32 位有符号整数范围内。
     * @param k
     * @param w
     * @param profits
     * @param capital
     * @return
     */
    public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        int n = profits.length;
        int[][] idx = new int[n][2];
        for (int i = 0 ; i < n ; i ++) {
            idx[i][0] = i;
            idx[i][1] = capital[i];
        }
        //按每个项目所需资金量大小 从小到大去排序，排序下标
        Arrays.sort(idx, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1] - o2[1];
            }
        });
        //使用大顶堆
        PriorityQueue<Integer>  queue = new PriorityQueue<>((o1,o2) -> o2 - o1);
        int i = 0;
        while (k > 0) {
            //如果满足资金使用量就加入队列
            while (i < n && capital[idx[i][0]] <= w) {
                queue.offer(profits[idx[i][0]]);
                i ++;
            }
            if (queue.isEmpty()) {
                break;
            }
            //计算收益
            w += queue.peek();
            queue.poll();
            k --;
        }
        return w;
    }

    /**
     * 2029. 石子游戏 IX
     * Alice 和 Bob 再次设计了一款新的石子游戏。现有一行 n 个石子，每个石子都有一个关联的数字表示它的价值。给你一个整数数组 stones ，其中 stones[i] 是第 i 个石子的价值。
     *
     * Alice 和 Bob 轮流进行自己的回合，Alice 先手。每一回合，玩家需要从 stones 中移除任一石子。
     *
     * 如果玩家移除石子后，导致 所有已移除石子 的价值 总和 可以被 3 整除，那么该玩家就 输掉游戏 。
     * 如果不满足上一条，且移除后没有任何剩余的石子，那么 Bob 将会直接获胜（即便是在 Alice 的回合）。
     * 假设两位玩家均采用 最佳 决策。如果 Alice 获胜，返回 true ；如果 Bob 获胜，返回 false 。
     * @param stones
     * @return
     */
    public boolean stoneGameIX(int[] stones) {
        //第一种情况，数组中不存在3的倍数
        //    A B A B A B A B A B ....... A B A B
        //K   1 1 2 1 2 1 2 1 2 1...K轮...2 1 2 2，这种情况下A赢，需要cnt1的数量<= cnt2的数量
        //M   2 2 1 2 1 2 1 2 1 2 ...K ...1 2 1 1，这种情况下A赢，需要cnt2的数量<= cnt1的数量
        //也就是说cnt1和cnt2的数量都大于等于1
        //第二种情况，数组中存在3的倍数
        //如果3倍数的个数是偶数，则并不影响最终结果
        //如果3倍数的个数是奇数
        //    A B A B A B A B A B ....... A B A B
        //K   1 1 2 1 2 1 2 1 2 1...K轮...2 1 0 1，
        //M   2 2 1 2 1 2 1 2 1 2 ...K ...1 2 0 2，
        //上面两种情况下A赢，需要cnt2的数量和cnt1的数量相差3
        long[] cnt = new long[3];
        for (int i : stones) {
            cnt[i % 3] ++;
        }
        return (cnt[0] % 2 == 0 && cnt[1] * cnt[2] >= 1)
                || (cnt[0] % 2 == 1 && Math.abs(cnt[1] - cnt[2]) >= 3);

    }

    /**
     * 1723. 完成所有工作的最短时间
     * 给你一个整数数组 jobs ，其中 jobs[i] 是完成第 i 项工作要花费的时间。
     *
     * 请你将这些工作分配给 k 位工人。所有工作都应该分配给工人，且每项工作只能分配给一位工人。
     * 工人的 工作时间 是完成分配给他们的所有工作花费时间的总和。请你设计一套最佳的工作分配方案，
     * 使工人的 最大工作时间 得以 最小化 。
     *
     * 返回分配方案中尽可能 最小 的 最大工作时间 。
     * @param jobs
     * @param k
     * @return
     */
    int ans;
    public int minimumTimeRequired(int[] jobs, int k) {
        //设置k个格子，将任务依次放入格子中尝试
        int[] slot = new int[k];
        ans = Integer.MAX_VALUE;
        dfs(jobs,0,slot,0);
        return ans;
    }

    /**
     *
     * @param jobs   工作分配
     * @param i      分配第几个工作
     * @param slot   工人任务数集合
     * @param max_num 当前工人工作时长的最大值
     * @param ans   结果尽可能 最小 的 最大工作时间
     */
    public void dfs(int[] jobs, int i, int[] slot, int max_num) {
        //如果工作循环完，ans等于当前工人工作时长的最大值
        if (i == jobs.length) {
            ans = max_num;
            return;
        }
        int k = slot.length;
        //循环去分配任务
        for (int j = 0 ; j < k ; j ++) {
            if (slot[j] + jobs[i] > ans) {
                continue;
            }
            slot[j] += jobs[i];
            dfs(jobs, i + 1, slot, Math.max(max_num,slot[j]));
            slot[j] -= jobs[i];
            //说明是分配的第一个任务，直接放进来就行
            if (slot[j] == 0) {
                break;
            }
        }

    }

    /**
     * 1477. 找两个和为目标值且不重叠的子数组
     * 给你一个整数数组 arr 和一个整数值 target 。
     *
     * 请你在 arr 中找 两个互不重叠的子数组 且它们的和都等于 target 。可能会有多种方案，请你返回满足要求的两个子数组长度和的 最小值 。
     *
     * 请返回满足要求的最小长度和，如果无法找到这样的两个子数组，请返回 -1 。
     * @param arr
     * @param target
     * @return
     */
    public int minSumOfLengths(int[] arr, int target) {
        //使用滑动窗口从前往后扫描，将每个等于target的区间段获取出来
        int l = 0, sum = 0 , n = arr.length;
        List<int[]> list = new ArrayList<>();
        for (int r = 0 ; r < n ; r ++) {
            sum += arr[r];
            while (sum > target && l <= r) {
                sum -= arr[l];
                l ++;
            }
            if (sum == target) {
                list.add(new int[]{l, r});
            }
        }
        //记录最小长度和，题目中数字都是正数，
        // 如果两个子数组要满足上述条件，如果 r1 < l2, l1 < l2
        int ans = -1,pre_idx = -1,pre_len = n + 1;
        for (int[] x : list) {
            // //获得之前区间的最小长度
            while (list.get(pre_idx + 1)[1] < x [0]) {
                pre_idx ++;
                pre_len = Math.min(list.get(pre_idx)[1] - list.get(pre_idx)[0] + 1, pre_len);
            }
            if (pre_idx == -1) {
                continue;
            }

            if (ans == -1 || ans > pre_len + x[1] - x[0] + 1) {
                ans = pre_len + x[1] - x[0] + 1;
            }
        }

        return ans;
    }

    /**
     * 面试题 17.13. 恢复空格
     * 哦，不！你不小心把一个长篇文章中的空格、标点都删掉了，并且大写也弄成了小写。
     * 像句子"I reset the computer. It still didn’t boot!"已经变成了"iresetthecomputeritstilldidntboot"。在处理标点符号和大小写之前，你得先把它断成词语。当然了，你有一本厚厚的词典dictionary，不过，有些词没在词典里。假设文章用sentence表示，设计一个算法，把文章断开，要求未识别的字符最少，返回未识别的字符数。
     *
     * 注意：本题相对原题稍作改动，只需返回未识别的字符数
     * @param dictionary
     * @param sentence
     * @return
     */
    public int respace(String[] dictionary, String sentence) {
        Trie trie = new Trie();
        for (String s : dictionary) {
            trie.insert(s);
        }
        //采用动态规划，
        //............................
        //          j+1      i
        //以i 结尾的未识别的字符数为 dp[i] + 1,
        //我们需要知道前面j + 1到 i的能匹配的字符串的标记位
        int n = sentence.length();
        int[] dp = new int[n + 1];
        List<Integer>[] marks = new List[n + 1];
        for (int i = 0 ; i <= n ; i ++) {
            marks[i] = new ArrayList<>();
        }
        for (int i = 0 ; i < n ; i ++) {
            trie.getMark(sentence,i,marks);
        }

        dp[0] = 0;
        for (int i = 1;i <= n; i ++) {
            dp[i] = dp[i - 1] + 1;
            for (int j : marks[i]) {
                dp[i] = Math.min(dp[i], dp[j]);
            }
        }
        return dp[n];
    }

    class TrieNode {
        boolean flag = false;
        TrieNode[] next = new TrieNode[26];
    }

    class Trie {

        //实现字典树
        TrieNode root = new TrieNode();

        public void insert(String s) {
            TrieNode p = root;
            for (int i = 0; i < s.length(); i ++) {
                int idx = s.charAt(i) - 'a';
                if (p.next[idx] == null) {
                    p.next[idx] = new TrieNode();
                }
                p = p.next[idx];
            }
            p.flag = true;
        }

        public void getMark(String s, int pos, List<Integer>[] marks) {
            TrieNode p = root;
            //标记以i位置为结尾，前面能合法匹配的字符串起始位置
            for (int i = pos; i < s.length(); i ++) {
                int idx = s.charAt(i) - 'a';
                p = p.next[idx];
                if (p == null) {
                    break;
                }
                if (p.flag) {
                    marks[i + 1].add(pos);
                }
            }
        }

    }

    /**
     * 2119. 反转两次的数字
     * 反转 一个整数意味着倒置它的所有位。
     *
     * 例如，反转 2021 得到 1202 。反转 12300 得到 321 ，不保留前导零 。
     * 给你一个整数 num ，反转 num 得到 reversed1 ，接着反转 reversed1 得到 reversed2 。如果 reversed2 等于 num ，返回 true ；否则，返回 false 。
     * @param num
     * @return
     */
    public boolean isSameAfterReversals(int num) {
        long num1 = getNum(num);
        long num2 = getNum(num1);
        return (int)num2 == num;
    }

    public long getNum(long x) {
        long num = 0;
        while (x > 0) {
            num = num * 10 + x % 10;
            x /= 10;
        }

        return num;
    }

    /**
     * 2110. 股票平滑下跌阶段的数目
     * 给你一个整数数组 prices ，表示一支股票的历史每日股价，其中 prices[i] 是这支股票第 i 天的价格。
     *
     * 一个 平滑下降的阶段 定义为：对于 连续一天或者多天 ，每日股价都比 前一日股价恰好少 1 ，这个阶段第一天的股价没有限制。
     *
     * 请你返回 平滑下降阶段 的数目。
     * @param prices
     * @return
     */
    public long getDescentPeriods(int[] prices) {
        //只需要求对于以i位置为结尾，之前有多少个平滑下降区间
        //例如prices = [3,2,1,4]
        //[3] 1
        //[3,2] 2
        //[3,2,1] 3
        //[3,2,1,4] 1
        //总数为7，对于当前位置对于前一个位置，如果能形成平滑下降，则相对于前面的区间数加1
        long fi = 0, ans = 0;
        for (int i = 0,pre = 0; i < prices.length; i ++) {
            if (prices[i] + 1 == pre) {
                fi ++;
            } else {
                fi = 1;
            }
            ans += fi;
            pre = prices[i];
        }
        return ans;
    }

    /**
     * 2140. 解决智力问题
     * 给你一个下标从 0 开始的二维整数数组 questions ，其中 questions[i] = [pointsi, brainpoweri] 。
     *
     * 这个数组表示一场考试里的一系列题目，你需要 按顺序 （也就是从问题 0 开始依次解决），
     * 、针对每个问题选择 解决 或者 跳过 操作。解决问题 i 将让你 获得  pointsi 的分数，
     * 但是你将 无法 解决接下来的 brainpoweri 个问题（即只能跳过接下来的 brainpoweri 个问题）。
     * 如果你跳过问题 i ，你可以对下一个问题决定使用哪种操作。
     *
     * 比方说，给你 questions = [[3, 2], [4, 3], [4, 4], [2, 5]] ：
     * 如果问题 0 被解决了， 那么你可以获得 3 分，但你不能解决问题 1 和 2 。
     * 如果你跳过问题 0 ，且解决问题 1 ，你将获得 4 分但是不能解决问题 2 和 3 。
     * 请你返回这场考试里你能获得的 最高 分数。
     * @param questions
     * @return
     */
    public long mostPoints(int[][] questions) {
        //动态规划，dp[i] 存在两种情况，选择和不选择
        //dp[i] = max(dp[i + 1], dp[j]); j = questions[i] + 1;
        int n = questions.length;
        long[] dp = new long[n + 1];
        for (int i = n - 1; i >= 0; i --) {
            dp[i] = Math.max(dp[i + 1], dp[Math.min(i + questions[i][1] + 1, n)] + questions[i][0]);
        }
        return dp[0];
    }

    /**
     * 2134. 最少交换次数来组合所有的 1 II
     * 交换 定义为选中一个数组中的两个 互不相同 的位置并交换二者的值。
     *
     * 环形 数组是一个数组，可以认为 第一个 元素和 最后一个 元素 相邻 。
     *
     * 给你一个 二进制环形 数组 nums ，返回在 任意位置 将数组中的所有 1 聚集在一起需要的最少交换次数。
     * @param nums
     * @return
     */
    public int minSwaps(int[] nums) {
        //定义一个窗口大小为1的数量的滑动窗口，窗口内0的数量就是需要交换的次数
        int ans = Integer.MAX_VALUE,z_cnt = 0,cnt1 = 0,n = nums.length;
        //计算1的个数
        for (int i = 0 ; i < n; i ++) {
            if (nums[i] == 1) {
                cnt1 ++;
            }
        }
        //初始化第一个窗口
        for (int i = 0;i < cnt1; i ++) {
            if (nums[i] == 0) {
                z_cnt ++;
            }
        }
        int l = 0, r = cnt1 - 1;
        for (int i = 0 ; i < n; i ++) {
            ans = Math.min(ans, z_cnt);
            if (nums[l] == 0) {
                z_cnt --;
            }
            //是一个环
            if (nums[(r + 1) % n] == 0) {
                z_cnt ++;
            }
            l ++;
            r ++;
        }
        return ans;
    }

    /**
     * 2130. 链表最大孪生和
     * 在一个大小为 n 且 n 为 偶数 的链表中，对于 0 <= i <= (n / 2) - 1 的 i ，第 i 个节点（下标从 0 开始）的孪生节点为第 (n-1-i) 个节点 。
     *
     * 比方说，n = 4 那么节点 0 是节点 3 的孪生节点，节点 1 是节点 2 的孪生节点。这是长度为 n = 4 的链表中所有的孪生节点。
     * 孪生和 定义为一个节点和它孪生节点两者值之和。
     *
     * 给你一个长度为偶数的链表的头节点 head ，请你返回链表的 最大孪生和 。
     * @param head
     * @return
     */
    public int pairSum(ListNode head) {
        //两种方案，第一种将链表的后半段翻转，拼到前半段后面，使用两个指针指向前半段的头和后半段的头，同时向后遍历
        //第二种方案，使用递归的回溯特性使程序可以从后往前访问链表
        //当前使用第二种方案实现
        ListNode p = head;
        ListNode q = head;
        while (q != null) {
            p = p.next;
            q = q.next.next;
        }
        return getMaxValue(head,p);
    }

    ListNode ln ;
    public int getMaxValue(ListNode head, ListNode p) {
        //当程序递归到最后,将ln指向头节点
        if (p == null) {
            ln = head;
            return Integer.MIN_VALUE;
        }

        int ans =getMaxValue(head, p.next);
        //回溯的过程中，p在往前遍历
        ans = Math.max(ans, ln.val + p.val);
        ln = ln.next;
        return ans;
    }

    class ListNode {
       int val;
       ListNode next;
       ListNode() {}
       ListNode(int val) { this.val = val; }
       ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    /**
     * 2096. 从二叉树一个节点到另一个节点每一步的方向
     * 给你一棵 二叉树 的根节点 root ，这棵二叉树总共有 n 个节点。每个节点的值为 1 到 n 中的一个整数，且互不相同。
     * 给你一个整数 startValue ，表示起点节点 s 的值，和另一个不同的整数 destValue ，表示终点节点 t 的值。
     *
     * 请找到从节点 s 到节点 t 的 最短路径 ，并以字符串的形式返回每一步的方向。每一步用 大写 字母 'L' ，'R' 和 'U' 分别表示一种方向：
     *
     * 'L' 表示从一个节点前往它的 左孩子 节点。
     * 'R' 表示从一个节点前往它的 右孩子 节点。
     * 'U' 表示从一个节点前往它的 父 节点。
     * 请你返回从 s 到 t 最短路径 每一步的方向。
     * @param root
     * @param startValue
     * @param destValue
     * @return
     */
    String s_str = "";
    String d_str = "";
    public String getDirections(TreeNode root, int startValue, int destValue) {
        //首先获取从root节点到startValue和destValue的路径，
        // 将两者的公共路径删除，将startValue的路径变成U，后面拼上destValue的路径
        char[] buff = new char[500000];
        getPathStr(root,0,startValue,destValue,buff);
        int n = Math.min(s_str.length(),d_str.length());
        for (int i = 0 ; i < n ; i ++) {
            if (s_str.charAt(i) == d_str.charAt(i)) {
                continue;
            }
            n = i;
            break;
        }
        s_str = getStr(s_str);
        d_str = getStr(d_str);
        return s_str.substring(n).replace('L','U').replace('R','U') + d_str.substring(n);
    }

    public String getStr(String str) {
        int n = str.length();
        boolean flag = false;
        int m = 0;
        for (int i = 0 ; i < n ; i ++) {
            if (str.charAt(i) != 'L' && str.charAt(i) != 'R') {
                flag = true;
                m = i;
                break;
            }
        }
        return flag ? str.substring(0,m - 1) : str;
    }

    public void getPathStr(TreeNode root,int dep,int startValue, int destValue,char[] buff) {
        buff[dep] = '\0';
        if (root == null) {
            return;
        }
        //
        if (root.val == startValue) {
//            s_str = String.valueOf(buff).trim();
            s_str = String.valueOf(buff);
        }
        if (root.val == destValue) {
//            d_str = String.valueOf(buff).trim();
            d_str = String.valueOf(buff);
        }
        buff[dep] = 'L';
        getPathStr(root.left, dep + 1, startValue,destValue,buff);
        buff[dep] = 'R';
        getPathStr(root.right, dep + 1, startValue,destValue,buff);
    }

    /**
     * 2136. 全部开花的最早一天
     * 你有 n 枚花的种子。每枚种子必须先种下，才能开始生长、开花。播种需要时间，种子的生长也是如此。给你两个下标从 0 开始的整数数组 plantTime 和 growTime ，每个数组的长度都是 n ：
     *
     * plantTime[i] 是 播种 第 i 枚种子所需的 完整天数 。每天，你只能为播种某一枚种子而劳作。无须 连续几天都在种同一枚种子，但是种子播种必须在你工作的天数达到 plantTime[i] 之后才算完成。
     * growTime[i] 是第 i 枚种子完全种下后生长所需的 完整天数 。在它生长的最后一天 之后 ，将会开花并且永远 绽放 。
     * 从第 0 开始，你可以按 任意 顺序播种种子。
     *
     * 返回所有种子都开花的 最早 一天是第几天。
     * @param plantTime
     * @param growTime
     * @return
     */
    public int earliestFullBloom(int[] plantTime, int[] growTime) {
        //采用微扰算法，在不知道如何排序的情况下，对少数元素进行排序，从而推导出最终结果
        //设第一颗种子，播种和开花所需的完整天数为p1和g1,第二颗种子为p2和g2, 并且g1 >= g2
        //t1 = max(p1 + g1, p1 + p2 + g2);
        //t2 = max(p2 + g2, p2 + p1 + g1);
        //在g1 >= g2的情况下，p2 + p1 + g1 >= p1 + p2 + g2，并且 p2 + p1 + g1 >= p1 + g1,所以t2 整体上 >= t1;
        int n = plantTime.length;
        int[][] idx = new int[n][2];
        //按开花时间从大到小对下标排序
        for (int i = 0;i < n;i ++) {
            idx[i][0] = i;
            idx[i][1] = growTime[i];
        }
        Arrays.sort(idx,new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o2[1] - o1[1];
            }
        });
        int ans = 0;
        //sum记录所有花种植时间的前缀和
        for (int i = 0, sum = 0; i < n ;i ++) {
            sum += plantTime[idx[i][0]];
            //结果是种植时间和 加上当前的开花时间与历史取最大值
            ans = Math.max(ans, sum + growTime[idx[i][0]]);
        }
        return ans;
    }

    /**
     * 2104. 子数组范围和
     * 给你一个整数数组 nums 。nums 中，子数组的 范围 是子数组中最大元素和最小元素的差值。
     *
     * 返回 nums 中 所有 子数组范围的 和 。
     *
     * 子数组是数组中一个连续 非空 的元素序列。
     * @param nums
     * @return
     */
    public long subArrayRanges(int[] nums) {
        //因为是子数组中最大元素和最小元素，所以采用单调队列，如果要计算最小和最大的差值，
        // 就需要维护两个单调队列
        //min 3 5 7 9 ...... i
        //max 5 6 8 10...... i
        //    3   5
        //使用两个指针，初始化分别指向 3 和 5，根据单调队列的特性，我们可知，小于3下标之前的子数组都满足
        //不小于3，并不大于5，范围和就是（5 - 3）* （3之前的范围）
        LinkedList<Integer> min_dq = new LinkedList<>();
        //单调递增队列
        LinkedList<Integer> max_dq = new LinkedList<>();
        int n = nums.length;
        long ans = 0;
        //维护以i为结尾，之前区间的最大、最小值
        for (int i = 0;i < n ;i ++) {
            while (!min_dq.isEmpty() && nums[i] <= nums[min_dq.peekLast()]) {
                min_dq.pollLast();
            }
            while (!max_dq.isEmpty() && nums[i] >= nums[max_dq.peekLast()]) {
                max_dq.pollLast();
            }
            //存储的是下标
            min_dq.addLast(i);
            max_dq.addLast(i);
            ans += getValue(min_dq,max_dq,nums);
        }
        return ans;
    }

    public long getValue(LinkedList<Integer> min_dq, LinkedList<Integer> max_dq,int[] nums) {
        int p = 0, q = 0;
        long ans = 0;
        //上一个位置
        int pre_pos = -1;
        while (p < min_dq.size()) {
            //根据单调队列的特性，需要获取当前的下标位置
            //总是获取最小的下标，小于这个下标的范围就是满足在最大，最小范围内的数字
            int pos = Math.min(min_dq.get(p),max_dq.get(q));
            ans += (pos - pre_pos) * (long)(nums[max_dq.get(q)] - nums[min_dq.get(p)]);
            //相等后，指针下标往后走
            if (min_dq.get(p) == pos) {
                p ++;
            }
            if (max_dq.get(q) == pos) {
                q ++;
            }
            pre_pos = pos;
        }
        return ans;
    }

    /**
     * 1028. 从先序遍历还原二叉树
     * 我们从二叉树的根节点 root 开始进行深度优先搜索。
     *
     * 在遍历中的每个节点处，我们输出 D 条短划线（其中 D 是该节点的深度），
     * 然后输出该节点的值。（如果节点的深度为 D，则其直接子节点的深度为 D + 1。根节点的深度为 0）。
     *
     * 如果节点只有一个子节点，那么保证该子节点为左子节点。
     *
     * 给出遍历输出 S，还原树并返回其根节点 root。
     * @param traversal
     * @return
     */
    public TreeNode recoverFromPreorder(String traversal) {
        //使用和树结构类似的栈结构，处理字符串的同时，遇到数字压入栈
        int i = 0 ,//字符位置
                k = 0, //深度
                n = traversal.length();//字符串长度
        Stack<TreeNode> stack = new Stack<>();
        while (i < n) {
            k = 0;
            int num = 0;
            //处理深度
            while (traversal.charAt(i) == '-' && i < n) {
                i ++;
                k ++;
            }
            //处理数字
            while (i < n && traversal.charAt(i) != '-') {
                num = num * 10 + traversal.charAt(i) - '0';
                i ++;
            }
            //如果节点的深度为 D，则其直接子节点的深度为 D + 1。根节点的深度为 0
            //如果k 小于 栈的大小，说明当前的值不是栈顶元素的子孩子
            while (stack.size() > k) {
                stack.pop();
            }
            TreeNode node = new TreeNode(num);
            if (!stack.isEmpty()) {
                if (stack.peek().left == null) {
                    stack.peek().left = node;
                } else {
                    stack.peek().right = node;
                }
            }
            stack.push(node);
        }
        TreeNode root = null;
        //栈中最后一个元素就是根节点
        while (!stack.isEmpty()) {
            root = stack.pop();
        }
        return root;
    }

    /**
     * 2115. 从给定原材料中找到所有可以做出的菜
     * 你有 n 道不同菜的信息。给你一个字符串数组 recipes 和一个二维字符串数组 ingredients 。
     * 第 i 道菜的名字为 recipes[i] ，如果你有它 所有 的原材料 ingredients[i] ，
     * 那么你可以 做出 这道菜。一道菜的原材料可能是 另一道 菜，也就是说 ingredients[i] 可能包含 recipes 中另一个字符串。
     *
     * 同时给你一个字符串数组 supplies ，它包含你初始时拥有的所有原材料，每一种原材料你都有无限多。
     *
     * 请你返回你可以做出的所有菜。你可以以 任意顺序 返回它们。
     *
     * 注意两道菜在它们的原材料中可能互相包含。
     * @param recipes
     * @param ingredients
     * @param supplies
     * @return
     */
    public List<String> findAllRecipes(String[] recipes, List<List<String>> ingredients, String[] supplies) {
        //存储每种菜的入度，入度为0代表这种菜可以做
        Map<String,Integer> cnt = new HashMap<>();
        //存储每种原材料被哪些菜所依赖
        Map<String,List<String>> g = new HashMap<>();
        int n = recipes.length;
        for (int i = 0; i < n ; i ++) {
            cnt.put(recipes[i], ingredients.get(i).size());
            for (String s : ingredients.get(i)) {
                List<String> list = new ArrayList<>();
                if (g.containsKey(s)) {
                    list = g.get(s);
                    list.add(recipes[i]);
                } else {
                    list.add(recipes[i]);
                }
                g.put(s,list);
            }
        }
        //循环原材料，递归去更新依赖原材料的菜品的入度
        for (String x : supplies) {
            update(x,cnt,g);
        }

        List<String> ans = new ArrayList<>();
        for (int i = 0; i < n ; i ++) {
            if (cnt.get(recipes[i]) == 0) {
                ans.add(recipes[i]);
            }
        }
        return ans;
    }

    public void update(String x,Map<String,Integer> cnt, Map<String,List<String>> g) {
        if (!g.containsKey(x)) {
            return;
        }
        //根据依赖每种原材料的菜品，去更新菜品的入度
        for (String s : g.get(x)) {
            cnt.put(s, cnt.get(s) - 1);
            //如果入度为0 就向上去递归更新
            if (cnt.get(s) == 0) {
                update(s,cnt,g);
            }
        }
    }

    /**
     * 2139. 得到目标值的最少行动次数
     * 你正在玩一个整数游戏。从整数 1 开始，期望得到整数 target 。
     *
     * 在一次行动中，你可以做下述两种操作之一：
     *
     * 递增，将当前整数的值加 1（即， x = x + 1）。
     * 加倍，使当前整数的值翻倍（即，x = 2 * x）。
     * 在整个游戏过程中，你可以使用 递增 操作 任意 次数。但是只能使用 加倍 操作 至多 maxDoubles 次。
     *
     * 给你两个整数 target 和 maxDoubles ，返回从 1 开始得到 target 需要的最少行动次数。
     * @param target
     * @param maxDoubles
     * @return
     */
    public int minMoves(int target, int maxDoubles) {
        //在有限的翻倍操作下，要以最小的次数达到target，需要初始值尽可能的大
        //将数字用二进制表示：100100111
        //如果翻倍一次，我们需要先将数字加到10010011，再乘2，变为100100110，再加一变为100100111
        //如果翻倍两次，我们需要先将数字加到1001001，再乘2，变为10010010，再+1 ，变为10010011；再乘2，变为100100110，再加一变为100100111
        //以此类推得到，如果要满足提议，最小次数等于 (target / (maxDoubles个2的次数)) - 1 + maxDoubles + 二进制后面1的个数

        //去获取target最高位的1的位置
        int cnt = 30 , one_cnt = 0;
        while (((1 << cnt) & target) == 0) {
            cnt --;
        }
        //获取翻倍的次数
        cnt = Math.min(cnt, maxDoubles);
        for (int i = 0 ; i < cnt ; i ++) {
            //获取后面1的个数
            if ((target & 1) == 1) {
                one_cnt ++;
            }
            //右移一位
            target >>= 1;
        }
        return target - 1 + cnt + one_cnt;
    }

    public int log2(int n) {
        return (int) Math.floor((Math.log(n) / Math.log(2)));
    }

    /**
     * 2121. 相同元素的间隔之和
     * 给你一个下标从 0 开始、由 n 个整数组成的数组 arr 。
     *
     * arr 中两个元素的 间隔 定义为它们下标之间的 绝对差 。更正式地，arr[i] 和 arr[j] 之间的间隔是 |i - j| 。
     *
     * 返回一个长度为 n 的数组 intervals ，其中 intervals[i] 是 arr[i] 和 arr 中每个相同元素（与 arr[i] 的值相同）的 间隔之和 。
     *
     * 注意：|x| 是 x 的绝对值。
     * @param arr
     * @return
     */
    public long[] getDistances(int[] arr) {
        // 例如 数字  3 ..... 3.....3
        //    下标   i.......k.....j
        //如果要计算k位置的间隔和
        //先求k到i的间隔和，(Vk - Vi) + (Vk - Vi+1) + (Vk - Vi+2) .....
        //               (k - i)*Vk - S_pre        S_pre为之前的前缀和
        //先求k到j的间隔和，(Vj - Vk) + (Vj-1 - Vk) + (Vj-2 - Vk) .....
        //               -(j - k)*Vk + S_next       S_next为之后的前缀和
        //所以k位置的间隔和为(k - i)*Vk - S_pre -(j - k)*Vk + S_next = (2k - i - j) * Vk - s_pre + s_next
        int n = arr.length;
        int[][] idx = new int[n][2];
        for (int i = 0 ;i < n ; i ++) {
            idx[i][0] = i;
            idx[i][1] = arr[i];
        }
        //将相同的数字排到一起，并按下标从小到大排序
        Arrays.sort(idx,new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1] - o2[1];
            }
        });

        Arrays.sort(idx,new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[1] == o2[1]) {
                    return o1[0] - o2[0];
                } else {
                    return 0;
                }
            }
        });

        long[] res = new long[n];
        for (int i = 0, j;i < n; i = j + 1) {
            //求出相同元素的下标起始结束位置
            j = i;
            while (j < n - 1 && idx[j + 1][1] == idx[i][1]) {
                j ++;
            }
            long s_pre = 0 , s_next = 0;
            for (int k = i ; k <= j ; k ++) {
                s_next += idx[k][0];
            }
            for (int k = i ; k <= j ; k ++) {
                s_next -= idx[k][0];
                res[idx[k][0]] = (long)(2 * k - i - j) * idx[k][0] - s_pre + s_next;
                s_pre += idx[k][0];
            }
        }
        return res;
    }

    /**
     * 324. 摆动排序 II
     * 给你一个整数数组 nums，将它重新排列成 nums[0] < nums[1] > nums[2] < nums[3]... 的顺序。
     *
     * 你可以假设所有输入数组都可以得到满足题目要求的结果。
     * @param nums
     */
    public void wiggleSort(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        int[] res = new int[n];
        int j = n - 1;
        for (int i = 1; i < n; i += 2, j -- ) {
            res[i] = nums[j];
        }
        for (int i = 0; i < n; i += 2, j --) {
            res[i] = nums[j];
        }
        for (int i = 0; i < n ; i ++) {
            nums[i] = res[i];
        }
    }

    /**
     * 807. 保持城市天际线
     * 给你一座由 n x n 个街区组成的城市，每个街区都包含一座立方体建筑。给你一个下标从 0 开始的 n x n 整数矩阵 grid ，其中 grid[r][c] 表示坐落于 r 行 c 列的建筑物的 高度 。
     *
     * 城市的 天际线 是从远处观察城市时，所有建筑物形成的外部轮廓。从东、南、西、北四个主要方向观测到的 天际线 可能不同。
     *
     * 我们被允许为 任意数量的建筑物 的高度增加 任意增量（不同建筑物的增量可能不同） 。 高度为 0 的建筑物的高度也可以增加。然而，增加的建筑物高度 不能影响 从任何主要方向观察城市得到的 天际线 。
     *
     * 在 不改变 从任何主要方向观测到的城市 天际线 的前提下，返回建筑物可以增加的 最大高度增量总和 。
     * @param grid
     * @return
     */
    public int maxIncreaseKeepingSkyline(int[][] grid) {
        //我们从远处看，天际线就是每个最高高度的大楼的轮廓，
        //在不改变天际线轮廓的情况下，每栋楼增加的最大高度就是不超过横向，纵向方向最高的楼房高度
        int n = grid.length;
        //记录每一列最大高度
        int[] rowMax = new int[n];
        //记录每一行最大高度
        int[] colMax = new int[n];
        for (int i = 0;i < n ; i ++) {
            for (int j = 0; j < n; j ++) {
                rowMax[i] = Math.max(rowMax[i], grid[i][j]);
                colMax[j] = Math.max(colMax[j], grid[i][j]);
            }
        }

        int ans = 0;
        for (int i = 0; i < n ; i ++) {
            for (int j = 0; j < n; j ++) {
                int height = Math.min(rowMax[i], colMax[j]) ;
                ans += height - grid[i][j];
            }
        }
        return ans;
    }

    /**
     * 605. 种花问题
     * 假设有一个很长的花坛，一部分地块种植了花，另一部分却没有。可是，花不能种植在相邻的地块上，它们会争夺水源，两者都会死去。
     *
     * 给你一个整数数组  flowerbed 表示花坛，由若干 0 和 1 组成，其中 0 表示没种植花，1 表示种植了花。另有一个数 n ，
     * 能否在不打破种植规则的情况下种入 n 朵花？能则返回 true ，不能则返回 false。
     * @param flowerbed
     * @param n
     * @return
     */
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        int zerocnt = 0;
        int m = flowerbed.length;
        //只有一个0的情况
        if (m == 1 && flowerbed[0] == 0) {
            return 1 >= n;
        }
        int cnt = 0;
        for (int i = 0; i < m ; i ++) {
            //计算0的个数，不是结尾跳过后面
            if (flowerbed[i] == 0 ) {
                zerocnt ++;
                if (i < m -1) {
                    continue;
                }
            }

            //如果是开头或者结尾
            if ((flowerbed[i] == 1 && zerocnt == i) || (flowerbed[i] == 0 && i == m - 1) ) {
                if (zerocnt >= 2) {
                    cnt += zerocnt / 2;
                    //如果是结尾，并且全是0，而且0的个数是奇数
                    if (flowerbed[i] == 0 && i == m - 1 && zerocnt == i + 1 && zerocnt % 2 == 1) {
                        cnt ++;
                    }
                }

                zerocnt = 0;
            } else {
                //在两个1之间
                if (zerocnt > 2) {
                    if (zerocnt % 2 == 1) {
                        cnt += zerocnt / 2;
                    } else {
                        cnt += ((zerocnt / 2) - 1);
                    }
                }
                zerocnt = 0;
            }
        }
        return  cnt >= n;
    }

    public boolean canPlaceFlowers1(int[] flowerbed, int n) {
        int m = flowerbed.length;
        for (int i = 0; i < m ;) {
            if (n == 0) {
                break;
            }
            if (flowerbed[i] == 1) {
                i += 2;
            } else if (i == m - 1 || flowerbed[i + 1] == 0) {
                n --;
                i += 2;
            } else {
                i += 3;
            }
        }
        return n == 0;
    }

    /**
     * 1291. 顺次数
     * 我们定义「顺次数」为：每一位上的数字都比前一位上的数字大 1 的整数。
     *
     * 请你返回由 [low, high] 范围内所有顺次数组成的 有序 列表（从小到大排序）。
     * @param low
     * @param high
     * @return
     */
    public List<Integer> sequentialDigits(int low, int high) {
        List<Integer> res = new ArrayList<>();
        for (int i = 1 ; i <= 9 ; i ++) {
            int num = i;
            for (int j = i + 1; j <= 9; j ++) {
                num = num * 10 + j;
                if (num >= low && num <= high) {
                    res.add(num);
                }
            }
        }
        Collections.sort(res);
        return res;
    }

    /**
     * 2114. 句子中的最多单词数
     * 一个 句子 由一些 单词 以及它们之间的单个空格组成，句子的开头和结尾不会有多余空格。
     *
     * 给你一个字符串数组 sentences ，其中 sentences[i] 表示单个 句子 。
     *
     * 请你返回单个句子里 单词的最多数目 。
     * @param sentences
     * @return
     */
    public int mostWordsFound(String[] sentences) {
        int ans = 0 ;
        for (String str : sentences) {
            ans = Math.max(str.split(" ").length, ans);
        }
        return ans;
    }

    /**
     * 2138. 将字符串拆分为若干长度为 k 的组
     * 字符串 s 可以按下述步骤划分为若干长度为 k 的组：
     *
     * 第一组由字符串中的前 k 个字符组成，第二组由接下来的 k 个字符串组成，依此类推。每个字符都能够成为 某一个 组的一部分。
     * 对于最后一组，如果字符串剩下的字符 不足 k 个，需使用字符 fill 来补全这一组字符。
     * 注意，在去除最后一个组的填充字符 fill（如果存在的话）并按顺序连接所有的组后，所得到的字符串应该是 s 。
     *
     * 给你一个字符串 s ，以及每组的长度 k 和一个用于填充的字符 fill ，按上述步骤处理之后，返回一个字符串数组，该数组表示 s 分组后 每个组的组成情况 。
     * @param s
     * @param k
     * @param fill
     * @return
     */
    public String[] divideString(String s, int k, char fill) {
        int len = s.length();
        int n = len % k == 0 ? len / k : len / k + 1;
        String[] ans = new String[n];
        for (int i = 0 ; i < n ; i ++) {
            String str = s.substring(i * k);
            if (str.length() < k) {
                for (int j = 0; j < k - str.length(); j ++) {
                    str += fill;
                }
            } else {
                str = str.substring(0,k);
            }
            ans[i] = str;
        }
        return ans;
    }

    /**
     * 1921. 消灭怪物的最大数量
     * 你正在玩一款电子游戏，在游戏中你需要保护城市免受怪物侵袭。给你一个 下标从 0 开始 且长度为 n 的整数数组 dist ，其中 dist[i] 是第 i 个怪物与城市的 初始距离（单位：米）。
     *
     * 怪物以 恒定 的速度走向城市。给你一个长度为 n 的整数数组 speed 表示每个怪物的速度，其中 speed[i] 是第 i 个怪物的速度（单位：米/分）。
     *
     * 怪物从 第 0 分钟 时开始移动。你有一把武器，并可以 选择 在每一分钟的开始时使用，包括第 0 分钟。但是你无法在一分钟的中间使用武器。这种武器威力惊人，一次可以消灭任一还活着的怪物。
     *
     * 一旦任一怪物到达城市，你就输掉了这场游戏。如果某个怪物 恰 在某一分钟开始时到达城市，这会被视为 输掉 游戏，在你可以使用武器之前，游戏就会结束。
     *
     * 返回在你输掉游戏前可以消灭的怪物的 最大 数量。如果你可以在所有怪物到达城市前将它们全部消灭，返回  n 。
     * @param dist
     * @param speed
     * @return
     */
    public int eliminateMaximum(int[] dist, int[] speed) {
        PriorityQueue<Integer> queue = new PriorityQueue();
        int n = dist.length;
        //按能走几步从小到大排序
        for (int i = 0 ; i < n; i ++) {
            queue.offer((dist[i] + speed[i] - 1) / speed[i]);
        }
        int ans = 0;
        while (!queue.isEmpty()) {
            int step = queue.poll();
            if (step <= ans) {
                break;
            }
            ans ++;
        }
        return ans;
    }

    /**
     * 1904. 你完成的完整对局数
     * 一款新的在线电子游戏在近期发布，在该电子游戏中，以 刻钟 为周期规划若干时长为 15 分钟 的游戏对局。这意味着，在 HH:00、HH:15、HH:30 和 HH:45 ，将会开始一个新的对局，其中 HH 用一个从 00 到 23 的整数表示。游戏中使用 24 小时制的时钟 ，所以一天中最早的时间是 00:00 ，最晚的时间是 23:59 。
     *
     * 给你两个字符串 startTime 和 finishTime ，均符合 "HH:MM" 格式，分别表示你 进入 和 退出 游戏的确切时间，请计算在整个游戏会话期间，你完成的 完整对局的对局数 。
     *
     * 例如，如果 startTime = "05:20" 且 finishTime = "05:59" ，这意味着你仅仅完成从 05:30 到 05:45 这一个完整对局。而你没有完成从 05:15 到 05:30 的完整对局，因为你是在对局开始后进入的游戏；同时，你也没有完成从 05:45 到 06:00 的完整对局，因为你是在对局结束前退出的游戏。
     * 如果 finishTime 早于 startTime ，这表示你玩了个通宵（也就是从 startTime 到午夜，再从午夜到 finishTime）。
     *
     * 假设你是从 startTime 进入游戏，并在 finishTime 退出游戏，请计算并返回你完成的 完整对局的对局数 。
     * @param loginTime
     * @param logoutTime
     * @return
     */
    public int numberOfRounds(String loginTime, String logoutTime) {
        int smi = getMinutes(loginTime);
        int emi = getMinutes(logoutTime);
        if (emi < smi) {
            return numberOfRounds(loginTime,"24:00") + numberOfRounds("00:00",logoutTime);
        }
        //时间延后
        smi = (smi % 15 == 0 ? smi : (smi - smi % 15) + 15);
        return (emi - smi) / 15;
    }

    public int getMinutes(String time) {
        String[] strs = time.split(":");
        int hours = Integer.parseInt(strs[0]);
        int minute = Integer.parseInt(strs[1]);
        return hours * 60 + minute;
    }

    /**
     * 1910. 删除一个字符串中所有出现的给定子字符串
     * 给你两个字符串 s 和 part ，请你对 s 反复执行以下操作直到 所有 子字符串 part 都被删除：
     *
     * 找到 s 中 最左边 的子字符串 part ，并将它从 s 中删除。
     * 请你返回从 s 中删除所有 part 子字符串以后得到的剩余字符串。
     *
     * 一个 子字符串 是一个字符串中连续的字符序列。
     * @param s
     * @param part
     * @return
     */
    public String removeOccurrences(String s, String part) {
        int idx = s.indexOf(part);
        int n = part.length();
        while (idx >= 0) {
            s = s.substring(0, idx) + s.substring(idx + n);
            idx = s.indexOf(part);
        }
        return s;
    }

    /**
     * 剑指 Offer II 035. 最小时间差
     * 给定一个 24 小时制（小时:分钟 "HH:MM"）的时间列表，找出列表中任意两个时间的最小时间差并以分钟数表示。
     * @param timePoints
     * @return
     */
    public int findMinDifference(List<String> timePoints) {
        Collections.sort(timePoints);
        int n = timePoints.size();
        int ans = getMinutes(timePoints.get(0)) + 24 * 60 - getMinutes(timePoints.get(n - 1));
        for (int i = 1; i < n ;i ++) {
            ans = Math.min(getMinutes(timePoints.get(i)) - getMinutes(timePoints.get(i - 1)), ans);
        }
        return ans;
    }

    /**
     * 剑指 Offer II 036. 后缀表达式
     * 根据 逆波兰表示法，求该后缀表达式的计算结果。
     *
     * 有效的算符包括 +、-、*、/ 。每个运算对象可以是整数，也可以是另一个逆波兰表达式。
     *
     * 说明：
     *
     * 整数除法只保留整数部分。
     * 给定逆波兰表达式总是有效的。换句话说，表达式总会得出有效数值且不存在除数为 0 的情况。
     * @param tokens
     * @return
     */
    public int evalRPN(String[] tokens) {
        //中缀表达式、后缀表达式，缀表示运算符的位置在哪
        Stack<Integer> stack = new Stack();
        for (String s : tokens) {
            if (s.length() > 1 || (s.charAt(0) >= '0' && s.charAt(0) <= '9')) {
                stack.push(Integer.valueOf(s));
            } else {
                int a = stack.pop();
                int b = stack.pop();
                if ("+".equals(s)) {
                    stack.push(b + a);
                } else if ("-".equals(s)) {
                    stack.push(b - a);
                } else if ("*".equals(s)) {
                    stack.push(b * a);
                } else if ("/".equals(s)) {
                    stack.push(b / a);
                }
            }
        }
        return stack.pop();
    }

    /**
     * 剑指 Offer II 033. 变位词组
     * 给定一个字符串数组 strs ，将 变位词 组合在一起。 可以按任意顺序返回结果列表。
     *
     * 注意：若两个字符串中每个字符出现的次数都相同，则称它们互为变位词。
     * @param strs
     * @return
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> ans = new ArrayList<>();
        //记录映射关系，记录字符串第一次出现的位置
        HashMap<String,Integer> map = new HashMap<>();
        for (String s : strs) {
            String s2 = str_sort(s);
            if (map.containsKey(s2)) {
                ans.get(map.get(s2)).add(s);
            } else {
                List<String> tmp = new ArrayList<>();
                tmp.add(s);
                ans.add(tmp);
                map.put(str_sort(s), ans.size() - 1);
            }
        }
        return ans;
    }

    public String str_sort(String s) {
        //字符串按字典序重新排序
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        return String.valueOf(chars);
    }

    /**
     * 剑指 Offer II 034. 外星语言是否排序
     * 某种外星语也使用英文小写字母，但可能顺序 order 不同。字母表的顺序（order）是一些小写字母的排列。
     *
     * 给定一组用外星语书写的单词 words，以及其字母表的顺序 order，只有当给定的单词在这种外星语中按字典序排列时，返回 true；否则，返回 false。
     * @param words
     * @param order
     * @return
     */
    HashMap<Integer,Integer> ordermap = new HashMap<>();
    public boolean isAlienSorted(String[] words, String order) {
        //建立外星语和地球语的对应关系
        for (int i = 0 ;i < 26 ; i ++) {
            ordermap.put(order.charAt(i) + 0, 'a' + i);
        }

        for (int i = 1 ; i < words.length; i ++) {
            if (!compare(words[i],words[i - 1])) {
                return false;
            }
        }
        return true;
    }

    public boolean compare(String a, String b) {
        //按外星语顺序比较
        int n = Math.min(a.length(),b.length());
        for (int i = 0 ; i < n; i ++) {
            char ac = a.charAt(i);
            char bc = b.charAt(i);
            if (ordermap.get(ac + 0) < ordermap.get(bc + 0)) {
                return false;
            }
            if (ordermap.get(ac + 0) > ordermap.get(bc + 0)) {
                return true;
            }
        }
        return a.length() >= b.length();
    }

    /**
     * 剑指 Offer II 012. 左右两边子数组的和相等
     * 给你一个整数数组 nums ，请计算数组的 中心下标 。
     *
     * 数组 中心下标 是数组的一个下标，其左侧所有元素相加的和等于右侧所有元素相加的和。
     *
     * 如果中心下标位于数组最左端，那么左侧数之和视为 0 ，因为在下标的左侧不存在元素。这一点对于中心下标位于数组最右端同样适用。
     *
     * 如果数组有多个中心下标，应该返回 最靠近左边 的那一个。如果数组不存在中心下标，返回 -1 。
     * @param nums
     * @return
     */
    public int pivotIndex(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return 0;
        }
        int[] sum = new int[n];
        sum[0] = nums[0];
        for (int i = 1 ; i < n; i ++) {
            sum[i] = nums[i] + sum[i - 1];
        }
        for (int i = 0 ; i < n; i ++) {
            if (sum[i] - nums[i] == sum[n - 1] - sum[i]) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 剑指 Offer II 010. 和为 k 的子数组
     * 给定一个整数数组和一个整数 k ，请找到该数组中和为 k 的连续子数组的个数。
     * @param nums
     * @param k
     * @return
     */
    public int subarraySum(int[] nums, int k) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return nums[0] == k ? 1 : 0;
        }
        int[] sum = new int[n + 1];
        for (int i = 1; i < n; i ++) {
            sum[i] = nums[i - 1] + sum[i - 1];
        }
        int ans = 0;
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i <= n; i ++) {
            //记录当前前缀和和k之间的差值在map中出现过没，
            // 如果出现过说明出现的次数就是和等于k的多少个区间
            if (map.containsKey(sum[i] - k)) {
                ans += map.get(sum[i] - k);
            }
            map.put(sum[i],map.getOrDefault(sum[i],0) + 1);
        }
        return ans;
    }

    /**
     * 剑指 Offer 56 - I. 数组中数字出现的次数
     * 一个整型数组 nums 里除两个数字之外，其他数字都出现了两次。请写程序找出这两个只出现一次的数字。要求时间复杂度是O(n)，空间复杂度是O(1)。
     *
     * @param nums
     * @return
     */
    public int[] singleNumbers(int[] nums) {
        int t = 0;
        //异或每个值，相同值异或值为0
        for (int x : nums) {
            t ^= x;
        }
        //找到二进制位为1 的一个ind
        int ind = 0;
        while ((t & (1 << ind)) == 0) {
            ind ++;
        }
        // 如果要找两个不同的数字，在t某个为1的二进制位，
        // 这两个数在此的二进制位肯定一个为0，一个为1
        // 根据ind这一位二进制值的不同分组异或
        int a = 0, b = 0;
        for (int x : nums) {
            if ((x & (1 << ind)) == 0) {
                a ^= x;
            } else {
                b ^= x;
            }
        }
        return new int[]{a, b};

    }

    /**
     * 剑指 Offer II 011. 0 和 1 个数相同的子数组
     * 给定一个二进制数组 nums , 找到含有相同数量的 0 和 1 的最长连续子数组，并返回该子数组的长度。
     * @param nums
     * @return
     */
    public int findMaxLength(int[] nums) {
        int n = nums.length;
        if (n <= 1) {
            return 0;
        }
        //求前缀和，把0当成-1
        nums[0] = nums[0] == 0 ? -1 : 1;
        for (int i = 1; i < n; i ++) {
            nums[i] = (nums[i] == 0 ? -1 : 1) + nums[i - 1];
        }
        int ans = 0;
        //建立前缀和及其第一次出现的下标
        HashMap<Integer,Integer> map = new HashMap<>();
        for (int i = 0 ;i < n; i ++) {
            if (nums[i] == 0) {
                ans = Math.max(ans,i + 1);
            } else {
                if (map.containsKey(nums[i])) {
                    ans = Math.max(ans, i - map.get(nums[i]));
                } else {
                    map.put(nums[i], i );
                }
            }
        }
        return ans;
    }

    /**
     * 剑指 Offer 56 - II. 数组中数字出现的次数 II
     * 在一个数组 nums 中除一个数字只出现一次之外，其他数字都出现了三次。请找出那个只出现一次的数字。
     */
    public int singleNumber(int[] nums) {
        //将所有数字拆开，统计不同位置二进制1出现的次数
        int[] bit = new int[32];
        for (int x : nums) {
            for (int i = 0 ; x != 0; i ++) {
                if ((x & 1) == 1 ) {
                    bit[i] ++;
                }
                x >>= 1;
            }
        }
        //对所有二进制位模3，如果模3后为1，说明是答案的一部分
        int ans = 0;
        for (int i = 0 ;i < 32 ; i ++) {
            bit[i] %= 3;
            if (bit[i] == 1) {
                ans += (1 << i);
            }
        }
        return ans;
    }

    /**
     * 剑指 Offer II 003. 前 n 个数字二进制中 1 的个数
     * 给定一个非负整数 n ，请计算 0 到 n 之间的每个数字的二进制表示中 1 的个数，并输出一个数组。
     * @param n
     * @return
     */
    public int[] countBits(int n) {
        //动态规划
        //0 --> 0
        //1 --> 1
        //2 --> 10
        //3 --> 11
        //4 --> 100
        //5 --> 101
        //6 --> 110
        //如果是偶数，是通过 n / 2 左移一位形成的，说明1的个数同 n/2的个数一样
        //如果是奇数，是通过(n - 1)的个数加1形成的
        int[] dp = new int[n + 1];
        for (int i = 0 ; i <= n; i ++ ) {
            if ((i & 1) == 0) {
                //偶数
                dp[i] = dp[i / 2];
            } else {
                dp[i] = dp[i - 1] + 1;
            }
        }
        return dp;
    }

    /**
     * 剑指 Offer II 005. 单词长度的最大乘积
     * 给定一个字符串数组 words，请计算当两个字符串 words[i] 和 words[j] 不包含相同字符时，
     * 它们长度的乘积的最大值。假设字符串中只包含英语的小写字母。如果没有不包含相同字符的一对字符串，返回 0。
     * @param words
     * @return
     */
    public int maxProduct(String[] words) {
        //按位保存状态，将对应的字符串变为对应的数字
        //将a对应二进制001，b-->010,c--> 100， .....以此类推，z为2的25次方
        //例如： abcd 按每个字母出现后对应位记为1，最后为1111，值为15，
        //bc 为110， bbc 为110；
        int n = words.length;
        int[] bit = new int[n];
        for (int i = 0;i < n ;i ++) {
            char[] chars = words[i].toCharArray();
            for (char c : chars) {
                bit[i] |= (1 << (c - 'a'));
            }
        }

        int ans = 0;
        for (int i = 0;i < n; i ++) {
            for (int j = i + 1; j < n; j ++) {
                if ((bit[i] & bit[j]) == 0) {
                    ans = Math.max(ans, words[i].length() * words[j].length());
                }
            }
        }
        return ans;
    }

    /**
     * 剑指 Offer 61. 扑克牌中的顺子
     * 从若干副扑克牌中随机抽 5 张牌，判断是不是一个顺子，即这5张牌是不是连续的。2～10为数字本身，
     * A为1，J为11，Q为12，K为13，而大、小王为 0 ，可以看成任意数字。A 不能视为 14。
     * @param nums
     * @return
     */
    public boolean isStraight(int[] nums) {
        //将数组排序
        int n = nums.length;
        Arrays.sort(nums);
        //获取大小王的数量，并判断除大小王以外有没有重复的牌
        int cnt = 0, min = 0, max = nums[n - 1];
        boolean flag = true;
        for (int i = 0 ; i < 5 ; i ++) {
            int x = nums[i];
            if (x == 0) {
                cnt ++;
            }
            if (x != 0 ) {
                if (flag) {
                    min = x;
                    flag = false;
                } else {
                    if (nums[i] == nums[i - 1]) {
                        return false;
                    }
                }
            }
        }

        return (max - min) < 5;
    }

    /**
     * 剑指 Offer II 008. 和大于等于 target 的最短子数组
     * 给定一个含有 n 个正整数的数组和一个正整数 target 。
     *
     * 找出该数组中满足其和 ≥ target 的长度最小的 连续子数组 [numsl, numsl+1, ..., numsr-1, numsr] ，
     * 并返回其长度。如果不存在符合条件的子数组，返回 0 。
     *
     * @param target
     * @param nums
     * @return
     */
    public int minSubArrayLen(int target, int[] nums) {
        int n = nums.length;
        int l = 0;
        int ans = Integer.MAX_VALUE, sum = 0;
        //采用滑动窗口法，for循环中的i代替右边界
        for (int i = 0 ;i < n ; i ++) {
            sum += nums[i];
            //如果当前和值减去左边界的值仍然大于target，左指针向后走，求得最小长度
            while (sum - nums[l] >= target && l < i) {
                sum -= nums[l];
                l ++;
            }
            if (sum >= target) {
                ans = Math.min(ans, i - l + 1);
            }
        }
        return ans == Integer.MAX_VALUE ? 0 : ans;
    }

    /**
     * 剑指 Offer II 009. 乘积小于 K 的子数组
     * 给定一个正整数数组 nums和整数 k ，请找出该数组内乘积小于 k 的连续的子数组的个数。
     * @param nums
     * @param k
     * @return
     */
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        if (k == 0) {
            return 0;
        }
        int n = nums.length;
        int l = 0;
        int ans = 0, sub = 1;
        //采用滑动窗口法，for循环中的i代替右边界
        for (int i = 0; i < n; i ++) {
            sub *= nums[i];
            while (sub >= k && l < i) {
                sub /= nums[l];
                l ++;
            }
            if (sub < k) {
                ans += (i - l + 1);
            }
        }
        return ans;
    }

    /**
     * 剑指 Offer II 014. 字符串中的变位词
     * 给定两个字符串 s1 和 s2，写一个函数来判断 s2 是否包含 s1 的某个变位词。
     *
     * 换句话说，第一个字符串的排列之一是第二个字符串的 子串 。
     * @param s1
     * @param s2
     * @return
     */
    public boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) {
            return false;
        }
        //滑动窗口，窗口大小为s1的大小
        int l = 0 , r = s1.length() - 1, cnt = 0;
        int[] n1 = new int[26];//s1中的字符数量
        int[] n2 = new int[26];//s2滑动窗口中字符的数量
        //初始化n1,n2字符数量
        for (int i = 0; i < s1.length() ; i ++) {
            n1[s1.charAt(i) - 'a'] ++;
            n2[s2.charAt(i) - 'a'] ++;
        }
        //判断不同字符的个数
        for (int i = 0; i < 26 ;  i ++) {
            if (n1[i] != n2[i]) {
                cnt ++;
            }
        }

        //滑动窗口
        for (int i = s1.length(); i < s2.length(); i ++) {
            if (cnt == 0) {
                break;
            }
            //放入一个新字符，原来两个对应字符数相等，说明加入后会导致不同数多一个
            if (n2[s2.charAt(i) - 'a'] == n1[s2.charAt(i) - 'a']) {
                cnt ++;
            }
            n2[s2.charAt(i) - 'a'] ++;
            //加入后相等，说明不同数少一个
            if (n2[s2.charAt(i) - 'a'] == n1[s2.charAt(i) - 'a']) {
                cnt --;
            }
            //窗口左边移出一个，原来两个对应字符数相等，说明移出后会导致不同数多一个
            if (n1[s2.charAt(i - s1.length()) - 'a'] == n2[s2.charAt(i - s1.length()) - 'a']) {
                cnt ++;
            }
            n2[s2.charAt(i - s1.length()) - 'a'] --;
            //移出后相等，说明不同数少一个
            if (n1[s2.charAt(i - s1.length()) - 'a'] == n2[s2.charAt(i - s1.length()) - 'a']) {
                cnt --;
            }
        }
        return cnt == 0 ? true : false;
    }

    /**
     * 剑指 Offer II 007. 数组中和为 0 的三个数
     * 给定一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a ，b ，c ，
     * 使得 a + b + c = 0 ？请找出所有和为 0 且 不重复 的三元组。
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        int l,r ;
        List<List<Integer>> list = new LinkedList<>();
        //排序后，如果num[i] > 0,后面必须有一个为负数，排序后不存在，所以跳出循环
        for (int i = 0; i < n && nums[i] <= 0; i ++) {
            //去重，前后两个值相同，找到的结果也一样
            if (i != 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int x = nums[i];
            int tmp = 0 - x;
            l = i + 1;
            r = n - 1;
            //双指针法，两个指针从两头逼近，找到答案
            while (l < r) {
                if (nums[l] + nums[r] == tmp) {
                    List<Integer>  tlist = new ArrayList<>();
                    tlist.add(nums[i]);
                    tlist.add(nums[l]);
                    tlist.add(nums[r]);
                    list.add(tlist);
                    //如果相同
                    while (l < r && nums[l] == nums[l + 1] ) {
                        l ++;
                    }
                    l ++;
                } else if (nums[l] + nums[r] > tmp) {
                    r --;
                } else {
                    l ++;
                }
            }
        }
        return list;
    }

    /**
     * 剑指 Offer II 018. 有效的回文
     * 给定一个字符串 s ，验证 s 是否是 回文串 ，只考虑字母和数字字符，可以忽略字母的大小写。
     *
     * 本题中，将空字符串定义为有效的 回文串 。
     * @param s
     * @return
     */
    public boolean isPalindrome(String s) {
        int n = s.length();
        if (s.length() == 0) {
            return true;
        }
        s = s.toLowerCase();
        int l = 0 , r = n - 1;
        while (l < r) {
            while (l < r && !checkChar(s.charAt(l))) {
                l ++;
            }
            while (r > l && !checkChar(s.charAt(r))) {
                r --;
            }

            if (s.charAt(l) != s.charAt(r)) {
                return false;
            }
            l ++;
            r --;
            continue;
        }
        return true;
    }

    public boolean checkChar(char c) {
        if (c >= '0' && c <= '9' || c >= 'a' && c <= 'z') {
            return true;
        }
        return false;
    }

    /**
     * 剑指 Offer II 019. 最多删除一个字符得到回文
     * 给定一个非空字符串 s，请判断如果 最多 从字符串中删除一个字符能否得到一个回文字符串。
     * @param s
     * @return
     */
    public boolean validPalindrome(String s) {
        int l = 0 , r = s.length() - 1;
        while (l < r) {
            if (s.charAt(l) != s.charAt(r)) {
                return validPalind(s,l + 1, r) || validPalind(s,l , r - 1);
            } else {
                l ++;
                r --;
            }
        }
        return true;
    }

    public boolean validPalind(String s, int l , int r) {
        while (l < r) {
            if (s.charAt(l) != s.charAt(r)) {
                return false;
            }
            l ++;
            r --;
        }
        return true;
    }

    /**
     * 剑指 Offer II 020. 回文子字符串的个数
     * 给定一个字符串 s ，请计算这个字符串中有多少个回文子字符串。
     *
     * 具有不同开始位置或结束位置的子串，即使是由相同的字符组成，也会被视作不同的子串。
     * @param s
     * @return
     */
    public int countSubstrings(String s) {
        //回文串，要么是奇数的，要么是偶数的
        int ans = 0, n = s.length();
        for (int i = 0 ; i < n ; i ++) {
            //按奇数，当前字符作为中轴，尝试向两边扩散
            int l = i - 1, r = i + 1;
            while (l >= 0 && r < n) {
                if (s.charAt(l) == s.charAt(r)) {
                    l -- ;
                    r ++ ;
                } else {
                    break;
                }
            }
            //区间回文的段数
            ans += (r - l) / 2;
            //按偶数，当前字符作为初始左边界，下一个字符作为初始有边界
            l = i;
            r = i + 1;
            while (l >= 0 && r < n) {
                if (s.charAt(l) == s.charAt(r)) {
                    l -- ;
                    r ++ ;
                } else {
                    break;
                }
            }
            ans += (r - l) / 2;
        }
        return ans;
    }

    /**
     * 967. 连续差相同的数字
     * 返回所有长度为 n 且满足其每两个连续位上的数字之间的差的绝对值为 k 的 非负整数 。
     *
     * 请注意，除了 数字 0 本身之外，答案中的每个数字都 不能 有前导零。例如，01 有一个前导零，所以是无效的；但 0 是有效的。
     *
     * 你可以按 任何顺序 返回答案。
     * @param n
     * @param k
     * @return
     */
    public int[] numsSameConsecDiff(int n, int k) {
        //例如长度3，相差2              4
        //                         2     6
        //                       0   4  4  8
        //本质是二叉树，结果为420，424，464，468
        //左子树减去差值，右子树加上差值
        List<Integer> list = new ArrayList<>();
        //第一位从1到9，不能以0开头
        for (int i = 1 ;i <= 9 ;i ++) {
            //递归函数
            getNums(i, n - 1, k, list);
        }

        int[] ans = new int[list.size()];
        for (int i = 0 ;i < list.size(); i ++) {
            ans[i] = list.get(i);
        }
        return ans;
    }

    /**
     *
     * @param now 当前数字
     * @param dep 还剩几层
     * @param k   差值
     * @param list 结果数组
     */
    public void getNums(int now ,int dep, int k, List<Integer> list) {
        if (dep == 0) {
            list.add(now);
            return;
        }
        //根据当前值最后一位，减去差值，遍历左子树
        int t = now % 10 - k;
        if (t >= 0) {
            getNums(now * 10 + t, dep - 1, k, list);
        }
        //根据当前值最后一位，加上差值，遍历右子树
        t = now % 10 + k;
        //差值为0，不用进行二次递归，会重复
        if (k != 0 && t < 10) {
            getNums(now * 10 + t, dep - 1, k ,list);
        }
    }

    /**
     * 79. 单词搜索
     * 给定一个 m x n 二维字符网格 board 和一个字符串单词 word 。如果 word 存在于网格中，返回 true ；否则，返回 false 。
     *
     * 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。
     * 同一个单元格内的字母不允许被重复使用。
     *
     * 1 <= m, n <= 6
     * @param board
     * @param word
     * @return
     */
    int m , n;
    //方向数组，前后左右
    int[] pathx = new int[]{1,-1,0,0};
    int[] pathy = new int[]{0,0,1,-1};
    //标记数组，记录走过的路径
    int[][] mark = new int[10][10];
    public boolean exist(char[][] board, String word) {
        //深度搜索
        //遍历每个点作为起点
        //不断的递归去找下一个字符，能找到就继续往前找，找不到回溯换方向找
        m = board.length;
        n = board[0].length;
        for (int i = 0; i < m ;i ++) {
            for (int j = 0 ;j < n; j ++) {
                if (board[i][j] == word.charAt(0)) {
                    mark[i][j] = 1;
                    if (findWord(board, word, 1, i , j)) {
                        return true;
                    }
                    mark[i][j] = 0;
                }
            }
        }
        return false;
    }

    /**
     *
     * @param board 字符地图
     * @param word 目标字符串
     * @param now 当前要找的字符
     * @param x  当前字符的坐标
     * @param y 当前字符的坐标
     * @return
     */
    public boolean findWord(char[][] board, String word, int now ,int x , int y) {
        if (now == word.length()) {
            return true;
        }
        //四个方向去找
        for (int i = 0 ;i < 4; i ++) {
            int px = x + pathx[i];
            int py = y + pathy[i];
            //边界判断
            if (px < 0 || py < 0 || px == m || py == n || mark[px][py] == 1) {
                continue;
            }
            //找到后，继续往后递归
            if (board[px][py] == word.charAt(now)) {
                //标记路径
                mark[px][py] = 1;
                if (findWord(board,word,now + 1, px,py)) {
                    return true;
                }
                //回溯取消标记
                mark[px][py] = 0;
            }
        }
        return false;
    }

    /**
     * 841. 钥匙和房间
     * 有 n 个房间，房间按从 0 到 n - 1 编号。最初，除 0 号房间外的其余所有房间都被锁住。
     * 你的目标是进入所有的房间。然而，你不能在没有获得钥匙的时候进入锁住的房间。
     *
     * 当你进入一个房间，你可能会在里面找到一套不同的钥匙，每把钥匙上都有对应的房间号，
     * 即表示钥匙可以打开的房间。你可以拿上所有钥匙去解锁其他房间。
     *
     * 给你一个数组 rooms 其中 rooms[i] 是你进入 i 号房间可以获得的钥匙集合。如果能进入 所有 房间返回 true，否则返回 false。
     *
     * 2 <= n <= 1000
     * @param rooms
     * @return
     */
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        //将问题看做一套连通图，本质上是用邻接表存的
        int n = rooms.size(), cnt = 1;
        int[] mark = new int[1005];//标记数组
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(0);
        mark[0] = 1;//初始化0号房间已经访问过
        while (!queue.isEmpty()) {
            int p = queue.poll();
            //获得其他房间的钥匙，并放入队列
            for (int i = 0; i < rooms.get(p).size(); i ++) {
                //拿到钥匙挨个去访问对应的房间，来过的房间不重复计数
                int e = rooms.get(p).get(i);
                if (mark[e] == 0) {
                    mark[e] = 1;
                    cnt ++;
                    queue.offer(e);
                }
            }
        }
        return cnt == n;
    }

    /**
     * 994. 腐烂的橘子
     * 在给定的 m x n 网格 grid 中，每个单元格可以有以下三个值之一：
     *
     * 值 0 代表空单元格；
     * 值 1 代表新鲜橘子；
     * 值 2 代表腐烂的橘子。
     * 每分钟，腐烂的橘子 周围 4 个方向上相邻 的新鲜橘子都会腐烂。
     *
     * 返回 直到单元格中没有新鲜橘子为止所必须经过的最小分钟数。如果不可能，返回 -1 。
     * @param grid
     * @return
     */
    public int orangesRotting(int[][] grid) {
        int m = grid.length,n = grid[0].length;
        int[] pathx = new int[]{0,0,1,-1};
        int[] pathy = new int[]{1,-1,0,0};
        int ans = 0,
                cnt = 0;//新鲜橘子个数
        Queue<Orage> queue = new ArrayDeque<>();
        for (int i = 0; i < m ; i ++) {
            for (int j = 0; j < n ; j ++) {
                if (grid[i][j] == 1) {
                    cnt ++;
                } else if (grid[i][j] == 2) {
                    queue.offer(new Orage(i,j,0));
                }
            }
        }
        //广度搜索
        while (!queue.isEmpty()) {
            Orage orage = queue.poll();
            //队列中越往后面放的橘子，扩展的步数就是最终的
            ans = orage.step;
            for (int i = 0; i < 4; i ++) {
                int px = orage.x + pathx[i];
                int py = orage.y + pathy[i];
                if (px < 0 || py < 0 || px >= m || py >= n || grid[px][py] != 1) {
                    continue;
                }
                grid[px][py] = 2;
                cnt --;
                queue.offer(new Orage(px,py,orage.step + 1));
            }
        }
        return cnt == 0 ? ans : -1;
    }

    class Orage {
        int x,y,step;
        public Orage(int x,int y,int step) {
            this.x = x;
            this.y = y;
            this.step = step;
        }
    }

    /**
     * 417. 太平洋大西洋水流问题
     * 有一个 m × n 的长方形岛屿，与 太平洋 和 大西洋 相邻。 “太平洋” 处于大陆的左边界和上边界，而 “大西洋” 处于大陆的右边界和下边界。
     *
     * 这个岛被分割成一个个方格网格。给定一个 m x n 的整数矩阵 heights ， heights[r][c] 表示坐标 (r, c) 上单元格 高于海平面的高度 。
     *
     * 岛上雨水较多，如果相邻小区的高度 小于或等于 当前小区的高度，雨水可以直接向北、南、东、西流向相邻小区。水可以从海洋附近的任何细胞流入海洋。
     *
     * 返回 网格坐标 result 的 2D列表 ，其中 result[i] = [ri, ci] 表示雨水可以从单元格 (ri, ci) 流向 太平洋和大西洋 。
     * @param heights
     * @return
     */
    //int m , n;
    //方向数组，前后左右
    //int[] pathx = new int[]{1,-1,0,0};
    //int[] pathy = new int[]{0,0,1,-1};
    //标记数组，记录走过的路径
    //int[][] mark = new int[10][10];
    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        //方法一，已每个点作为起点，遍历每个点，看是否能到达两个大洋，每个点都会被搜索m * n 次，效率较慢
        //方法二，从两大洋开始方位每个点，看当前点是否被两大洋访问到，每个点只会被访问两次，效率高
        m = heights.length;
        n = heights[0].length;
        mark = new int[201][201];
        List<List<Integer>> ans = new ArrayList<>();

        for (int i = 0 ;i < m; i ++) {
            pacific(heights,i,0,1,ans);//第一列，//从太平洋访问
            pacific(heights,i, n - 1,2,ans); //最后一列，从大西洋访问
        }

        for (int i = 0 ;i < n ;i ++) {
            pacific(heights,0,i,1 ,ans);//第一行，//从太平洋访问
            pacific(heights,m - 1,i,2,ans);//最后一行，从大西洋访问
        }
        return ans;
    }

    /**
     *
     * @param heights 地图
     * @param x 当前坐标
     * @param y 当前坐标
     * @param val 标记访问的是太平洋（1）还是大西洋（2），
     * @param ans 结果数组
     */
    public void pacific(int[][] heights,int x, int y , int val, List<List<Integer>> ans) {
        //采用标记数组，01，10 是 1和2的二进制表示，如果两个洋都来过，跳过这个点
        if ((mark[x][y] & val) != 0) {
            return;
        }
        mark[x][y] |= val;
        //两个都标记过，或的值就是3
        if (mark[x][y] == 3) {
            List<Integer> list = new ArrayList<>();
            list.add(x);
            list.add(y);
            ans.add(list);
        }

        for (int i = 0;i < 4;i ++) {
            int px = x + pathx[i];
            int py = y + pathy[i];
            if (px < 0 || py < 0 || px >= m || py >= n) {
                continue;
            }
            //当从大洋搜索过来，是从小到大
            if (heights[px][py] >= heights[x][y]) {
                pacific(heights,px,py,val,ans);
            }
        }
    }

    /**
     * 529. 扫雷游戏
     * 让我们一起来玩扫雷游戏！
     *
     * 给你一个大小为 m x n 二维字符矩阵 board ，表示扫雷游戏的盘面，其中：
     *
     * 'M' 代表一个 未挖出的 地雷，
     * 'E' 代表一个 未挖出的 空方块，
     * 'B' 代表没有相邻（上，下，左，右，和所有4个对角线）地雷的 已挖出的 空白方块，
     * 数字（'1' 到 '8'）表示有多少地雷与这块 已挖出的 方块相邻，
     * 'X' 则表示一个 已挖出的 地雷。
     * 给你一个整数数组 click ，其中 click = [clickr, clickc] 表示在所有 未挖出的 方块（'M' 或者 'E'）中的下一个点击位置（clickr 是行下标，clickc 是列下标）。
     *
     * 根据以下规则，返回相应位置被点击后对应的盘面：
     *
     * 如果一个地雷（'M'）被挖出，游戏就结束了- 把它改为 'X' 。
     * 如果一个 没有相邻地雷 的空方块（'E'）被挖出，修改它为（'B'），并且所有和其相邻的 未挖出 方块都应该被递归地揭露。
     * 如果一个 至少与一个地雷相邻 的空方块（'E'）被挖出，修改它为数字（'1' 到 '8' ），表示相邻地雷的数量。
     * 如果在此次点击中，若无更多方块可被揭露，则返回盘面。
     *
     * @return
     */
    class BoardNode {
        int x;
        int y;
        public BoardNode (int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public char[][] updateBoard(char[][] board, int[] click) {
        //搜索题，广搜、深搜都可以
        pathx = new int[]{0,0,1,-1,1,1,-1,-1};
        pathy = new int[]{1,-1,0,0,1,-1,1,-1};
        //炸了
        if (board[click[0]][click[1]] == 'M') {
            board[click[0]][click[1]] = 'X';
            return board;
        }
        m = board.length;
        n = board[0].length;
        //搜索队列
        Queue<BoardNode> queue = new ArrayDeque<>();
        queue.offer(new BoardNode(click[0],click[1]));
        board[click[0]][click[1]] = 'B';
        while (!queue.isEmpty()) {
            BoardNode boardNode = queue.poll();
            //获得点周围雷的数量
            int cnt = getBoard(board,boardNode);
            if (cnt != 0) {
                board[boardNode.x][boardNode.y] = Character.forDigit(cnt,10);
                continue;
            }
            for (int i = 0; i < 8 ; i ++) {
                int px = pathx[i] + boardNode.x;
                int py = pathy[i] + boardNode.y;
                if (px < 0 || py < 0 || px >= m || py >= n || board[px][py] != 'E') {
                    continue;
                }
                board[px][py] = 'B';
                queue.offer(new BoardNode(px,py));
            }
        }
        return board;
    }

    public int getBoard(char[][] board, BoardNode boardNode) {
        pathx = new int[]{0,0,1,-1,1,1,-1,-1};
        pathy = new int[]{1,-1,0,0,1,-1,1,-1};
        int ans = 0;
        for (int i = 0;i < 8 ;i ++) {
            int px = boardNode.x + pathx[i];
            int py = boardNode.y + pathy[i];
            if (px < 0 || py < 0 || px >= m || py >= n) {
                continue;
            }
            if (board[px][py] == 'M') {
                ans ++;
            }
        }
        return ans;
    }

    /**
     * 127. 单词接龙
     * 字典 wordList 中从单词 beginWord 和 endWord 的 转换序列 是一个按下述规格形成的序列 beginWord -> s1 -> s2 -> ... -> sk：
     *
     * 每一对相邻的单词只差一个字母。
     *  对于 1 <= i <= k 时，每个 si 都在 wordList 中。注意， beginWord 不需要在 wordList 中。
     * sk == endWord
     * 给你两个单词 beginWord 和 endWord 和一个字典 wordList ，返回 从 beginWord 到 endWord 的 最短转换序列 中的 单词数目 。
     * 如果不存在这样的转换序列，返回 0 。
     * @param beginWord
     * @param endWord
     * @param wordList
     * @return
     */
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        //广搜
        Set<String> set = new HashSet<>();//便于搜索字符串
        for (int i = 0 ;i < wordList.size();i ++) {
            set.add(wordList.get(i));
        }
        //题目要求
        if (!set.contains(endWord)) {
            return 0;
        }
        Queue<wordNode> queue = new ArrayDeque<>();
        queue.offer(new wordNode(beginWord,1));
        while (!queue.isEmpty()) {
            wordNode wordNode = queue.poll();
            //说明找到了
            if (wordNode.str.equals(endWord)) {
                return wordNode.step;
            }

            //将对应字符串的每个字符替换一遍，如果在列表中就放入队列继续往下搜索
            for (int i = 0 ;i < wordNode.str.length(); i ++) {
                char[] tmp = wordNode.str.toCharArray();
                for (char j = 'a' ;j <= 'z' ;j ++) {
                    tmp[i] = j;
                    String tstr = String.valueOf(tmp);
                    if (set.contains(tstr)) {
                        set.remove(tstr);
                        queue.offer(new wordNode(tstr,wordNode.step + 1));
                    }
                }
            }
        }
        return 0;
    }

    class wordNode {
        String str;
        int step;
        public wordNode(String s, int x) {
            str = s;
            step = x;
        }
    }

    /**
     * 1631. 最小体力消耗路径
     * 你准备参加一场远足活动。给你一个二维 rows x columns 的地图 heights ，
     * 其中 heights[row][col] 表示格子 (row, col) 的高度。一开始你在最左上角的格子 (0, 0) ，
     * 且你希望去最右下角的格子 (rows-1, columns-1) （注意下标从 0 开始编号）。
     * 你每次可以往 上，下，左，右 四个方向之一移动，你想要找到耗费 体力 最小的一条路径。
     *
     * 一条路径耗费的 体力值 是路径上相邻格子之间 高度差绝对值 的 最大值 决定的。
     *
     * 请你返回从左上角走到右下角的最小 体力消耗值 。
     *
     * rows == heights.length
     * columns == heights[i].length
     * 1 <= rows, columns <= 100
     * 1 <= heights[i][j] <= 10^6
     * @param heights
     * @return
     */
    public int minimumEffortPath(int[][] heights) {
        //方法一，暴力枚举每一条路径，选出符合题意的那一条
        //方法二，二分答案，由于1 <= heights[i][j] <= 10^6，
        //所以最小差值为0，最大差值为10^6 - 1，在满足题意的情况下，
        //最小体力消耗为0到10^6 - 1之间的一个值，从小到大排序
        //符合0000011111模型，找到最小的那个1.
        m = heights.length;
        n = heights[0].length;
        pathx = new int[]{0,0,1,-1};
        pathy = new int[]{1,-1,0,0};
        int l = 0,r = 1000000;
        //二分
        while (l != r) {
            int mid = (l + r) / 2;
            if (checkEffortPath(heights,mid)) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }

    class PathNode {
        int x;
        int y;
        public PathNode(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    public boolean checkEffortPath(int[][] heights,int mid) {
        Queue<PathNode> queue = new ArrayDeque<>();
        queue.offer(new PathNode(m - 1,n - 1));
        int[][] mark = new int[102][102];
        while (!queue.isEmpty()) {
            PathNode tmp = queue.poll();
            if (tmp.x == 0 && tmp.y == 0) {
                return true;
            }
            for (int i = 0; i < 4; i ++) {
                int px = tmp.x + pathx[i];
                int py = tmp.y + pathy[i];
                if (px < 0 || py < 0 || px >= m || py >= n || mark[px][py] == 1) {
                    continue;
                }
                if (Math.abs(heights[tmp.x][tmp.y] - heights[px][py]) <= mid) {
                    mark[px][py] = 1;
                    queue.offer(new PathNode(px,py));
                }
            }
        }
        return false;
    }

    /**
     * 864. 获取所有钥匙的最短路径
     * 给定一个二维网格 grid ，其中：
     *
     * '.' 代表一个空房间
     * '#' 代表一堵
     * '@' 是起点
     * 小写字母代表钥匙
     * 大写字母代表锁
     * 我们从起点开始出发，一次移动是指向四个基本方向之一行走一个单位空间。我们不能在网格外面行走，也无法穿过一堵墙。如果途经一个钥匙，我们就把它捡起来。除非我们手里有对应的钥匙，否则无法通过锁。
     *
     * 假设 k 为 钥匙/锁 的个数，且满足 1 <= k <= 6，字母表中的前 k 个字母在网格中都有自己对应的一个小写和一个大写字母。换言之，每个锁有唯一对应的钥匙，每个钥匙也有唯一对应的锁。另外，代表钥匙和锁的字母互为大小写并按字母顺序排列。
     *
     * 返回获取所有钥匙所需要的移动的最少次数。如果无法获取所有钥匙，返回 -1 。
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 30
     * grid[i][j] 只含有 '.', '#', '@', 'a'-'f' 以及 'A'-'F'
     * 钥匙的数目范围是 [1, 6] 
     * 每个钥匙都对应一个 不同 的字母
     * 每个钥匙正好打开一个对应的锁
     *
     * @param grid
     * @return
     */
    public int shortestPathAllKeys(String[] grid) {
        int m = grid.length, n = grid[0].length();
        //标记数组，用于标记对应点的状态是不是来过，按状态去重
        //第三维代表钥匙的状态走过没有,也就是带着几把钥匙是否走过，走过就跳过
        int[][][] mark = new int[31][31][256];
        //二进制数组，表示6把钥匙
        int[] b2 = new int[]{1,2,4,8,16,32,64,128,256};

        int cnt = 0;
        int[] pathx = new int[]{0,0,1,-1};
        int[] pathy = new int[]{1,-1,0,0};
        Queue<KeyNode> queue = new ArrayDeque<>();
        for (int i = 0 ;i < m ;i ++) {
            for (int j = 0 ;j < n; j ++) {
                //初始化起点
                if (grid[i].charAt(j)=='@') {
                    //将起点变成正常可以走的点
                    char[] chars = grid[i].toCharArray();
                    chars[j] = '.';
                    grid[i] = String.valueOf(chars);
                    mark[i][j][0] = 1;
                    queue.offer(new KeyNode(i,j,0,0));
                } else if (grid[i].charAt(j) >= 'a' && grid[i].charAt(j) <= 'z') {
                    cnt ++;
                }
            }
        }

        while (!queue.isEmpty()) {
            KeyNode tmp = queue.poll();
            //有一把钥匙000001， 1
            //有2把钥匙000011， 1 + 2 = 3
            //有3把钥匙000111， 1 + 2 + 4 = 7
            //如何判断找到所有钥匙， 2^n - 1
            if (tmp.status == b2[cnt] - 1) {
                return tmp.step;
            }
            for (int i = 0 ;i < 4 ; i ++) {
                int px = tmp.x + pathx[i];
                int py = tmp.y + pathy[i];
                if (px < 0 || py < 0 || px == m || py == n || mark[px][py][tmp.status] == 1) {
                    continue;
                }
                //正常的点
                if (grid[px].charAt(py) == '.') {
                    mark[px][py][tmp.status] = 1;
                    queue.offer(new KeyNode(px,py,tmp.step + 1, tmp.status));
                } else if (grid[px].charAt(py) >= 'a' && grid[px].charAt(py) <= 'z') {
                    //走到钥匙那里
                    //获得钥匙前的状态标为 1
                    mark[px][py][tmp.status] = 1;
                    //采用或运算，不管之前这把钥匙是不是被捡起来过，都不会重复标记
                    mark[px][py][tmp.status | b2[grid[px].charAt(py) - 'a']] = 1;
                    queue.offer(new KeyNode(px,py,tmp.step + 1, (tmp.status | b2[grid[px].charAt(py) - 'a'])));
                } else if (grid[px].charAt(py) >= 'A' && grid[px].charAt(py) <= 'Z') {
                    //走到锁那里，得有对应的钥匙才能走到那
                    if ((tmp.status & b2[grid[px].charAt(py) - 'A']) == 0) {
                        continue;
                    }
                    mark[px][py][tmp.status] = 1;
                    queue.offer(new KeyNode(px,py,tmp.step + 1, tmp.status));
                }
            }
        }
        return -1;
    }

    class KeyNode {
        int x ;
        int y ;
        int step;//当前步数
        //当前钥匙状态，用于标记捡了几把钥匙
        //总共6把钥匙，采用1，2，4，8.....的二进制位标记（按位标记）
        int status;
        public KeyNode(int x , int y , int step, int status) {
            this.x = x;
            this.y = y;
            this.step = step;
            this.status = status;
        }
    }

    /**
     * 2216. 美化数组的最少删除数
     * 给你一个下标从 0 开始的整数数组 nums ，如果满足下述条件，则认为数组 nums 是一个 美丽数组 ：
     *
     * nums.length 为偶数
     * 对所有满足 i % 2 == 0 的下标 i ，nums[i] != nums[i + 1] 均成立
     * 注意，空数组同样认为是美丽数组。
     *
     * 你可以从 nums 中删除任意数量的元素。当你删除一个元素时，被删除元素右侧的所有元素将会向左移动一个单位以填补空缺，而左侧的元素将会保持 不变 。
     *
     * 返回使 nums 变为美丽数组所需删除的 最少 元素数目。
     * @param nums
     * @return
     */
    public int minDeletion(int[] nums) {
        //凑对，从0开始往后凑对，遇到不相同的直接凑对，能凑出最多的对数
        int cnt = 0,n = nums.length, a = nums[0];
        for (int i = 1 ;i < n ;i ++) {
            if (nums[i] == a) {
                continue;
            }
            cnt ++;
            if (i + 1 == n) {
                break;
            }
            a = nums[i + 1];
        }
        return n - cnt * 2;
    }

    /**
     * 1562. 查找大小为 M 的最新分组
     * 给你一个数组 arr ，该数组表示一个从 1 到 n 的数字排列。有一个长度为 n 的二进制字符串，该字符串上的所有位最初都设置为 0 。
     *
     * 在从 1 到 n 的每个步骤 i 中（假设二进制字符串和 arr 都是从 1 开始索引的情况下），二进制字符串上位于位置 arr[i] 的位将会设为 1 。
     *
     * 给你一个整数 m ，请你找出二进制字符串上存在长度为 m 的一组 1 的最后步骤。一组 1 是一个连续的、由 1 组成的子串，且左右两边不再有可以延伸的 1 。
     *
     * 返回存在长度 恰好 为 m 的 一组 1  的最后步骤。如果不存在这样的步骤，请返回 -1 。
     * @param arr
     * @param m
     * @return
     */
    public int findLatestStep(int[] arr, int m) {
        return 0;
    }

    /**
     * 1574. 删除最短的子数组使剩余数组有序
     * 给你一个整数数组 arr ，请你删除一个子数组（可以为空），使得 arr 中剩下的元素是 非递减 的。
     *
     * 一个子数组指的是原数组中连续的一个子序列。
     *
     * 请你返回满足题目要求的最短子数组的长度。
     * @param arr
     * @return
     */
    public int findLengthOfShortestSubarray(int[] arr) {
        //从两边向中间查找单调的位置，将中间位置删除
        int n = arr.length,p = n - 1, ans = Integer.MAX_VALUE;
        while (p > 0 && arr[p] >= arr[p - 1]) {
            p --;
        }
        if (p == 0) {
            return 0;
        }
        //确定前面的位置
        ans = p;
        for (int i = 0;i < n;i ++) {
            if (i > 0 && arr[i] < arr[i - 1]) {
                break;
            }
            while ( (p < n && arr[p] < arr[i])) {
                p ++;
            }
            ans = Math.min(ans ,p - i - 1);
        }
        return ans;
    }

    /**
     * 2226. 每个小孩最多能分到多少糖果
     * 给你一个 下标从 0 开始 的整数数组 candies 。数组中的每个元素表示大小为 candies[i] 的一堆糖果。
     * 你可以将每堆糖果分成任意数量的 子堆 ，但 无法 再将两堆合并到一起。
     *
     * 另给你一个整数 k 。你需要将这些糖果分配给 k 个小孩，使每个小孩分到 相同 数量的糖果。
     * 每个小孩可以拿走 至多一堆 糖果，有些糖果可能会不被分配。
     *
     * 返回每个小孩可以拿走的 最大糖果数目 。
     * @param candies
     * @param k
     * @return
     */
    public int maximumCandies(int[] candies, long k) {
        //堆数增加，每堆的数目减少；堆数减少，每堆数目增加；
        //堆数和数目呈线性关系；用堆数去推数目比较困难，
        //反之，用数目去推堆数比较简单；可以采用二分
        int l = 0, r = Arrays.stream(candies).max().getAsInt() + 1;
        while (l < r) {
            int mid = (l + r) / 2;
            if (mid == 0) {
                return 0;
            }
            if (checkCandies(candies,mid) >= k) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }

        return l - 1;
    }

    public long checkCandies(int[] candies,int n) {
        long ans = 0;
        for (int x : candies) {
            ans += x / n;
        }
        return ans;
    }

    /**
     * 1583. 统计不开心的朋友
     * 给你一份 n 位朋友的亲近程度列表，其中 n 总是 偶数 。
     *
     * 对每位朋友 i，preferences[i] 包含一份 按亲近程度从高到低排列 的朋友列表。换句话说，
     * 排在列表前面的朋友与 i 的亲近程度比排在列表后面的朋友更高。每个列表中的朋友均以 0 到 n-1 之间的整数表示。
     *
     * 所有的朋友被分成几对，配对情况以列表 pairs 给出，其中 pairs[i] = [xi, yi] 表示 xi 与 yi 配对，且 yi 与 xi 配对。
     *
     * 但是，这样的配对情况可能会使其中部分朋友感到不开心。在 x 与 y 配对且 u 与 v 配对的情况下，如果同时满足下述两个条件，x 就会不开心：
     *
     * x 与 u 的亲近程度胜过 x 与 y，且
     * u 与 x 的亲近程度胜过 u 与 v
     * 返回 不开心的朋友的数目 。
     * @param n
     * @param preferences
     * @param pairs
     * @return
     */
    public int unhappyFriends(int n, int[][] preferences, int[][] pairs) {

        //按题目要求处理出清晰的数据，彼此相爱的人不在一起
        int[][] g = new int[n][n];
        int[] like = new int[n];

        //处理关系排位，第i个人喜欢的人按喜欢程度排位
        for (int i = 0;i < n ; i ++) {
            for (int j = 0; j < n - 1; j ++) {
                g[i][preferences[i][j]] = j;
            }
        }

        //和第i个人在一起的人是第i个人第几喜欢的人
        for (int[] x : pairs) {
            like[x[0]] = g[x[0]][x[1]];
            like[x[1]] = g[x[1]][x[0]];
        }
        int ans = 0;
        for (int i = 0 ; i < n; i ++) {
            for (int j = 0 , J = like[i]; j < J ; j ++) {
                //数字越小越喜欢，
                //第i个人喜欢的人t喜欢第i个人的程度大于和第t个人配对的人的喜欢程度，
                //说明第i个人喜欢和他配对的人
                //说明第i个人是开心的
                int t = preferences[i][j];
                if (g[t][i] >= like[t]) {
                    continue;
                }
                ans ++;
                break;
            }
        }
        return ans;
    }

    /**
     * 1585. 检查字符串是否可以通过排序子字符串得到另一个字符串
     * 给你两个字符串 s 和 t ，请你通过若干次以下操作将字符串 s 转化成字符串 t ：
     *
     * 选择 s 中一个 非空 子字符串并将它包含的字符就地 升序 排序。
     * 比方说，对下划线所示的子字符串进行操作可以由 "14234" 得到 "12344" 。
     *
     * 如果可以将字符串 s 变成 t ，返回 true 。否则，返回 false 。
     *
     * 一个 子字符串 定义为一个字符串中连续的若干字符。
     * @param s
     * @param t
     * @return
     */
    public boolean isTransformable(String s, String t) {
        //如何可以使s变成t，反向求解，如何使s变不成t,
        //就是t中对应位置的数字，在s中第一次出现的位置之前有比他还小的数字，就变不成t
        Deque<Integer>[] pos = new Deque[10];
        for (int i = 0 ;i < 10; i ++) {
            pos[i] = new ArrayDeque<>();
        }
        int n = s.length();
        for (int i = 0; i < n; i ++) {
            pos[s.charAt(i) - '0'].offerLast(i);
        }
        for (int i = 0 ; i < n ;i ++) {
            //这个数字在s中没出现过
            int num = t.charAt(i) - '0';
            if (pos[num].isEmpty()) {
                return false;
            }
            int p = pos[num].peekFirst();
            //扫描比t当前位置数字更小的数字是否在p之前出现过
            for (int j = 0; j < num; j ++) {
                if (!pos[j].isEmpty() && pos[j].peekFirst().intValue() < p) {
                    return false;
                }
            }
            pos[num].pollFirst();
        }
        return true;
    }

    /**
     * 2218. 从栈中取出 K 个硬币的最大面值和
     * 一张桌子上总共有 n 个硬币 栈 。每个栈有 正整数 个带面值的硬币。
     *
     * 每一次操作中，你可以从任意一个栈的 顶部 取出 1 个硬币，从栈中移除它，并放入你的钱包里。
     *
     * 给你一个列表 piles ，其中 piles[i] 是一个整数数组，分别表示第 i 个栈里 从顶到底 的硬币面值。
     * 同时给你一个正整数 k ，请你返回在 恰好 进行 k 次操作的前提下，你钱包里硬币面值之和 最大为多少 。
     * @param piles
     * @param k
     * @return
     */
    public int maxValueOfCoins(List<List<Integer>> piles, int k) {
        //如果要求第n组获取k次的最大值，相当于求容量为k的背包能获取的最大价值，
        //假设dp[i][j]为当前的能获取的最大价值，按分组去获取最大的值，
        //每组获取的个数是个动态变化的过程，当i - 1组已经获取j - x个，第i组获取x个
        //在dp[i][j]和dp[i - 1][j - x]中获取一个最大值
        int n = piles.size();
        int[][] dp = new int[n + 1][k + 1];
        for (int i = 1;i <= n ;i ++) {
            for (int j = 1; j <= k; j ++) {
                dp[i][j] = dp[i - 1][j];
                int x = 0,y = 0;
                for (int t : piles.get(i - 1)) {
                    x ++;
                    y += t;
                    if (x > j) {
                        break;
                    }
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - x] + y);
                }
            }
        }
        return dp[n][k];
    }

    public static void main(String[] args) {
        System.out.println("重地".equals("1"));//1179395
        System.out.println("通话".hashCode());//

        String abchh = "114";
        Field[] fields = abchh.getClass().getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field);
        }
    }

}

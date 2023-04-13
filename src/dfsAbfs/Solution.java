package dfsAbfs;

import sun.applet.Main;

import java.util.*;
import java.util.stream.Collectors;

public class Solution {
    /**
     * 130. 被围绕的区域
     * 给你一个 m x n 的矩阵 board ，由若干字符 'X' 和 'O' ，找到所有被 'X' 围绕的区域，并将这些区域里所有的 'O' 用 'X' 填充。
     *被围绕的区间不会存在于边界上，换句话说，任何边界上的'O'都不会被填充为'X'。
     * 任何不在边界上，或不与边界上的'O'相连的'O'最终都会被填充为'X'。如果两个元素在水平或垂直方向相邻，则称它们是“相连”的
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/surrounded-regions
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    public void solve(char[][] board) {
        int m = board.length;
        int n = board[0].length;
        for (int i = 0 ;i < m; i ++) {
            for (int j = 0; j < n ; j ++) {
                //边界
                if (i == 0 || j == 0 || i == m - 1 || j == n - 1) {
                    //如果边界，当为O时，将O变为#
                    if (board[i][j] == 'O') {
                        dfs(board, i , j);
                    }
                }
            }
        }
        for (int i = 0 ;i < m; i ++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                }
                if (board[i][j] == '#') {
                    board[i][j] = 'O';
                }
            }
        }
    }

    public void dfs(char[][] board, int i , int j) {
        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length
        || board[i][j] == 'X' || board[i][j] == '#') {
            return;
        }
        board[i][j] = '#';
        dfs(board, i - 1, j);
        dfs(board, i + 1, j);
        dfs(board, i, j - 1);
        dfs(board, i, j + 1);
    }

    /**
     * 494. 目标和
     * 给你一个整数数组 nums 和一个整数 target 。
     *
     * 向数组中的每个整数前添加 '+' 或 '-' ，然后串联起所有整数，可以构造一个 表达式 ：
     *
     * 例如，nums = [2, 1] ，可以在 2 之前添加 '+' ，在 1 之前添加 '-' ，然后串联起来得到表达式 "+2-1" 。
     * 返回可以通过上述方法构造的、运算结果等于 target 的不同 表达式 的数目。
     *
     * 示例 1：
     *
     * 输入：nums = [1,1,1,1,1], target = 3
     * 输出：5
     * 解释：一共有 5 种方法让最终目标和为 3 。
     * -1 + 1 + 1 + 1 + 1 = 3
     * +1 - 1 + 1 + 1 + 1 = 3
     * +1 + 1 - 1 + 1 + 1 = 3
     * +1 + 1 + 1 - 1 + 1 = 3
     * +1 + 1 + 1 + 1 - 1 = 3
     * @param nums
     * @param target
     * @return
     */
    public int findTargetSumWays(int[] nums, int target) {
        //每个数字都有+、-两个操作，采用递归的方式去回溯，看是不是满足target
        return dfs(nums, 0, target);
    }

    public int dfs(int[] nums, int i, int target) {
        //反向操作，当target等于0时就可以满足
        if (i == nums.length) {
            return target == 0 ? 1 : 0;
        }
        return dfs(nums,i + 1, target - nums[i]) + dfs(nums, i + 1 , target + nums[i]);
    }

    /**
     * 473. 火柴拼正方形
     * 还记得童话《卖火柴的小女孩》吗？现在，你知道小女孩有多少根火柴，请找出一种能使用所有火柴拼成一个正方形的方法。不能折断火柴，可以把火柴连接起来，并且每根火柴都要用到。
     *
     * 输入为小女孩拥有火柴的数目，每根火柴用其长度表示。输出即为是否能用所有的火柴拼成正方形。
     *
     * 示例 1:
     *
     * 输入: [1,1,2,2,2]
     * 输出: true
     *
     * 解释: 能拼成一个边长为2的正方形，每边两根火柴。
     * @param matchsticks
     * @return
     */
    public boolean makesquare(int[] matchsticks) {
        if (matchsticks.length < 4 || matchsticks == null) {
            return false;
        }
        //如果边长乘以4不等于和，则不构成正方形
        int sum = Arrays.stream(matchsticks).sum();
        int sidelen = sum / 4;
        if (sidelen * 4 != sum) {
            return false;
        }

        //转换为list并按从大到小排序
        List<Integer> nums = Arrays.stream(matchsticks).boxed().collect(Collectors.toList());
        Collections.sort(nums,Collections.reverseOrder());
        return dfs(0,nums,sidelen,new int[4]);
    }

    public boolean dfs(int idx,List<Integer> nums,int sidelen, int[] sums){
        if (idx == nums.size()){
            return sums[0] == sums[1] && sums[1] == sums[2]
                    && sums[2] == sums[3];
        }
        for (int i = 0; i < 4; i ++) {
            if (sums[i] + nums.get(idx) <= sidelen) {
                sums[i] += nums.get(idx);
                if (dfs(idx + 1,nums,sidelen,sums)) {
                    return true;
                } else {
                    sums[i] -= nums.get(idx);
                }
            }

        }
        return false;
    }

    /**
     * 39. 组合总和
     * 给定一个无重复元素的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
     *
     * candidates 中的数字可以无限制重复被选取。
     *
     * 说明：
     *
     * 所有数字（包括 target）都是正整数。
     * 解集不能包含重复的组合。
     * 示例 1：
     *
     * 输入：candidates = [2,3,6,7], target = 7,
     * 所求解集为：
     * [
     *   [7],
     *   [2,2,3]
     * ]
     * @param candidates
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> res = new ArrayList<>();
        Deque<Integer> queue = new LinkedList<>();
        dfs(candidates,0,candidates.length,target,queue,res);
        return res;
    }

    public void dfs(int[] candidates,int begin, int len, int target, Deque<Integer> path,List<List<Integer>> res ) {

        if (target == 0) {
            res.add(new ArrayList<>(path));
            return;
        }
        for (int i = begin;i < len; i ++) {
            if (target - candidates[i] < 0) {
                break;
            }
            path.addLast(candidates[i]);
            dfs(candidates,i,len, target - candidates[i], path,res);
            path.removeLast();
        }
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
     * 993. 二叉树的堂兄弟节点
     * 在二叉树中，根节点位于深度 0 处，每个深度为 k 的节点的子节点位于深度 k+1 处。
     *
     * 如果二叉树的两个节点深度相同，但 父节点不同 ，则它们是一对堂兄弟节点。
     *
     * 我们给出了具有唯一值的二叉树的根节点 root ，以及树中两个不同节点的值 x 和 y 。
     *
     * 只有与值 x 和 y 对应的节点是堂兄弟节点时，才返回 true 。否则，返回 false。
     * @param root
     * @param x
     * @param y
     * @return
     */
    public boolean isCousins(TreeNode root, int x, int y) {
        int[] xi = dfs(root,null,x,0);
        int[] yi = dfs(root,null,y,0);
        return xi[0] != yi[0] && xi[1] == yi[1];
    }

    public int[] dfs(TreeNode root,TreeNode parent, int x,int k) {
        //使用深度搜索
        if (root == null) {
            return new int[]{-1,-1};
        }
        if (root.val == x) {
            return new int[]{parent == null ? 1 : parent.val,k};
        }
        int[] l = dfs(root.left, root, x, k + 1);
        if (l[0] != -1) {
            return l;
        }
        return dfs(root.right, root, x, k + 1);
    }

    public boolean isCousins1(TreeNode root, int x, int y) {
        int[] xi = bfs(root,x);
        int[] yi = bfs(root,y);
        return xi[0] != yi[0] && xi[1] == yi[1];
    }

    public int[] bfs(TreeNode root, int x) {
        //使用广度搜索
        //Object[]{当前节点，⽗节点，当前层数}
        Queue<Object[]> queue = new LinkedList<>();
        queue.offer(new Object[]{root,null,0});
        while (!queue.isEmpty()) {
           int size = queue.size();
           while (size -- > 0) {
               Object[] poll = queue.poll();
               TreeNode cur = (TreeNode) poll[0];
               TreeNode par = (TreeNode) poll[1];
               int dep = (int) poll[2];
               if (cur.val == x) {
                   return new int[]{par == null ? 1:par.val,dep};
               }
               if (cur.left != null) {
                   queue.offer(new Object[]{cur.left,cur,dep + 1});
               }
               if (cur.right != null) {
                   queue.offer(new Object[]{cur.right,cur,dep + 1});
               }
           }
        }
        return new int[]{-1,-1};
    }

    /**
     * 542. 01 矩阵
     * 给定一个由 0 和 1 组成的矩阵，找出每个元素到最近的 0 的距离。
     *
     * 两个相邻元素间的距离为 1
     * @param mat
     * @return
     */
    public int[][] updateMatrix(int[][] mat) {
        Queue<int[]> queue = new LinkedList<>();
        int m = mat.length,n = mat[0].length;
        //将0入队，1改为-1，表示没有走到
        for (int i = 0; i < m; i ++) {
            for (int j = 0; j < n; j ++) {
                if (mat[i][j] == 0) {
                    queue.offer(new int[]{i,j});
                } else {
                    mat[i][j] = -1;
                }
            }
        }
        //方向数组，前后左右
        int[] pathx = new int[]{1,-1,0,0};
        int[] pathy = new int[]{0,0,1,-1};
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            for (int i = 0; i < 4 ;i ++) {
                int newx = cur[0] + pathx[i];
                int newy = cur[1] + pathy[i];
                if (newx >=0 && newx < m && newy >= 0 && newy < n && mat[newx][newy] == -1) {
                    queue.offer(new int[] {newx,newy});
                    mat[newx][newy] = mat[cur[0]][cur[1]] + 1;
                }
            }

        }
        return mat;
    }

    /**
     * 1091. 二进制矩阵中的最短路径
     * 给你一个 n x n 的二进制矩阵 grid 中，返回矩阵中最短 畅通路径 的长度。如果不存在这样的路径，返回 -1 。
     *
     * 二进制矩阵中的 畅通路径 是一条从 左上角 单元格（即，(0, 0)）到 右下角 单元格（即，(n - 1, n - 1)）的路径，该路径同时满足下述要求：
     *
     * 路径途经的所有单元格都的值都是 0 。
     * 路径中所有相邻的单元格应当在 8 个方向之一 上连通（即，相邻两单元之间彼此不同且共享一条边或者一个角）。
     * 畅通路径的长度 是该路径途经的单元格总数。
     * @param grid
     * @return
     */
    public int shortestPathBinaryMatrix(int[][] grid) {
        int m = grid.length,n = grid[0].length;
        if (grid[0][0] != 0 || grid[m - 1][n - 1] != 0) {
            return -1;
        }
        if (m == 1 && n == 1) {
            return 1;
        }
        //方向数组，前后左右
        int[] pathx = new int[]{1,-1,0,0,1,-1,1,-1};
        int[] pathy = new int[]{0,0,1,-1,-1,1,1,-1};
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[] {0,0});
        grid[0][0] = 1;
        while (!queue.isEmpty()) {
            int cur[] = queue.poll();
            int x = cur[0],y = cur[1];
            for (int i = 0; i < 8; i ++) {
                int newx = x + pathx[i];
                int newy = y + pathy[i];
                if (newx >= 0 && newx < m && newy >= 0 && newy < n
                        && grid[newx][newy] == 0) {
                    grid[newx][newy] = grid[x][y] + 1;
                    queue.offer(new int[]{newx,newy});
                }
                if (newx == m - 1 && newy == n - 1) {
                    return grid[newx][newy];
                }
            }
        }
        return -1;
    }

    /**
     * 752. 打开转盘锁
     * 你有一个带有四个圆形拨轮的转盘锁。每个拨轮都有10个数字： '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' 。
     * 每个拨轮可以自由旋转：例如把 '9' 变为 '0'，'0' 变为 '9' 。每次旋转都只能旋转一个拨轮的一位数字。
     *
     * 锁的初始数字为 '0000' ，一个代表四个拨轮的数字的字符串。
     *
     * 列表 deadends 包含了一组死亡数字，一旦拨轮的数字和列表里的任何一个元素相同，这个锁将会被永久锁定，无法再被旋转。
     *
     * 字符串 target 代表可以解锁的数字，你需要给出解锁需要的最小旋转次数，如果无论如何不能解锁，返回 -1 。
     * @param deadends
     * @param target
     * @return
     */
    public int openLock(String[] deadends, String target) {
        List<String> ddd = Arrays.asList(deadends);
        Queue<String> queue = new LinkedList<>();
        queue.offer("0000");
        if (ddd.indexOf("0000") >= 0) {
            return -1;
        }
        HashSet<String> set = new HashSet<>();
        set.add("0000");
        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int m = 0; m < size; m ++) {
                String cur = queue.poll();
                if (ddd.indexOf(cur) >= 0) {
                    continue;
                }
                if (cur.equals(target)) {
                    return step;
                }

                for (int i = 0; i < 4 ; i ++) {
                    String downstr = cur;
                    String upstr = cur;
                    downstr = downLock(downstr,i);
                    if (!set.contains(downstr)) {
                        queue.offer(downstr);
                        set.add(downstr);
                    }
                    upstr = upLock(upstr,i);
                    if (!set.contains(upstr)) {
                        queue.offer(upstr);
                        set.add(upstr);
                    }
                }
            }
            step ++;

        }
        return -1;
    }

    public String downLock(String str , int k) {
        char[] chars = str.toCharArray();
        char c = chars[k];
        if (c == '9') {
            chars[k] = '0';
        } else {
            chars[k] = (char) (c + 1);
        }
        return new String(chars);
    }

    public String upLock(String str , int k) {
        char[] chars = str.toCharArray();
        char c = chars[k];
        if (c == '0') {
            chars[k] = '9';
        } else {
            chars[k] = (char) (c - 1);
        }
        return new String(chars);
    }

    /**
     * 双向bfs
     * @param deadends
     * @param target
     * @return
     */
    public int openLock1(String[] deadends, String target) {
        List<String> ddd = Arrays.asList(deadends);
        if (ddd.indexOf("0000") >= 0) {
            return -1;
        }
        HashSet<String> q1 = new HashSet<>();
        q1.add("0000");
        HashSet<String> q2 = new HashSet<>();
        q2.add(target);
        HashSet<String> set = new HashSet<>();
        int step = 0;
        while (!q1.isEmpty() && !q2.isEmpty()) {
            HashSet<String> tmp = new HashSet<>();
            for (String cur : q1) {
                if (ddd.indexOf(cur) >= 0) {
                    continue;
                }
                if (q2.contains(cur)) {
                    return step;
                }
                set.add(cur);
                for (int i = 0; i < 4; i ++) {
                    String down = downLock(cur, i);
                    if (!set.contains(down)) {
                        tmp.add(down);
                    }
                    String up = upLock(cur, i);
                    if (!set.contains(up)) {
                        tmp.add(up);
                    }
                }
            }
            q1 = q2;
            q2 = tmp;
            step ++;
        }
        return -1;
    }

    /**
     * 剑指 Offer 13. 机器人的运动范围
     * 地上有一个m行n列的方格，从坐标 [0,0] 到坐标 [m-1,n-1] 。一个机器人从坐标 [0, 0] 的格子开始移动，
     * 它每次可以向左、右、上、下移动一格（不能移动到方格外），也不能进入行坐标和列坐标的数位之和大于k的格子。
     * 例如，当k为18时，机器人能够进入方格 [35, 37] ，因为3+5+3+7=18。但它不能进入方格 [35, 38]，因为3+5+3+8=19。
     * 请问该机器人能够到达多少个格子？
     * @param m
     * @param n
     * @param k
     * @return
     */
    public int movingCount(int m, int n, int k) {
        if (k == 0) {
            return 1;
        }
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0,0});

        boolean[][] vis = new boolean[m][n];
        vis[0][0] = true;
        int[][] path = new int[][]{{0,1},{1,0}};
        int res = 1;
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int x = cur[0];
            int y = cur[1];
            for (int i = 0; i < 2; i ++) {
                int newx = x + path[i][0];
                int newy = y + path[i][1];
                int[] newp = new int[]{newx,newy};
                if (newx >= 0 && newx < m && newy >= 0&& newy < n
                        && !vis[newx][newy] && getNum(newx) + getNum(newy) <= k) {
                    queue.offer(newp);
                    vis[newx][newy] = true;
                    res ++;
                }
            }
        }
        return res;
    }

    public int getNum(int num) {
        int res = 0;
        while (num != 0) {
            res += (num%10);
            num /= 10;
        }
        return res;
    }

    /**
     * 使用set会产生重复，所以结果会多
     * @param m
     * @param n
     * @param k
     * @return
     */
    public int movingCount1(int m, int n, int k) {
        if (k == 0) {
            return 1;
        }
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0,0});

        Set<int[]> set = new HashSet<>();
        set.add(new int[]{0,0});
        int[][] path = new int[][]{{0,1},{1,0}};
        int res = 1;
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int x = cur[0];
            int y = cur[1];
            for (int i = 0; i < 2; i ++) {
                int newx = x + path[i][0];
                int newy = y + path[i][1];
                int[] newp = new int[]{newx,newy};
                if (newx >= 0 && newx < m && newy >= 0&& newy < n
                        && !set.contains(newp) && getNum(newx) + getNum(newy) <= k) {
                    queue.offer(newp);
                    set.add(newp);
                    res ++;
                    System.out.println(newx + ";" + newy);
                }
            }
        }
        return res;
    }

    class Employee {
        public int id;
        public int importance;
        public List<Integer> subordinates;
    };

    /**
     * 690. 员工的重要性
     * 给定一个保存员工信息的数据结构，它包含了员工 唯一的 id ，重要度 和 直系下属的 id 。
     *
     * 比如，员工 1 是员工 2 的领导，员工 2 是员工 3 的领导。他们相应的重要度为 15 , 10 , 5 。
     * 那么员工 1 的数据结构是 [1, 15, [2]] ，员工 2的 数据结构是 [2, 10, [3]] ，员工 3 的数据结构是 [3, 5, []] 。
     * 注意虽然员工 3 也是员工 1 的一个下属，但是由于 并不是直系 下属，因此没有体现在员工 1 的数据结构中。
     *
     * 现在输入一个公司的所有员工信息，以及单个员工 id ，返回这个员工和他所有下属的重要度之和。
     * @param employees
     * @param id
     * @return
     */
    Map<Integer ,Employee> emap = new HashMap<>();
    public int getImportance(List<Employee> employees, int id) {
        for (int i = 0; i < employees.size(); i ++) {
            emap.put(employees.get(i).id, employees.get(i));
        }
        return getVal(id);
    }

    public int getVal(int id) {
        Employee cur = emap.get(id);
        int val = cur.importance;
        List<Integer> subordinates = cur.subordinates;
        for (int sub : subordinates) {
            val += getVal(sub);
        }
        return val;
    }

    /**
     * 17. 电话号码的字母组合
     * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按 任意顺序 返回。
     *
     * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母
     * @param digits
     * @return
     */
    String[] phonenums =  {" ", "*", "abc", "def", "ghi", "jkl", "mno", "pqrs",
            "tuv", "wxyz"};
    List<String> nums = new ArrayList<>();
    public List<String> letterCombinations(String digits) {
        if (digits == null || digits.equals("")) {
            return nums;
        }
        getPhoneNums(digits,new StringBuilder(),0);
        return nums;
    }

    public void getPhoneNums(String digits, StringBuilder sb, int idx) {
        if (idx == digits.length() - 1) {
            nums.add(sb.toString());
            return;
        }
        char c = digits.charAt(idx);
        String str = phonenums[c];
        for (int i = 0; i < str.length(); i ++) {
            getPhoneNums(digits, sb.append(str.charAt(i)), idx + 1);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    /**
     * 279. 完全平方数
     * 给定正整数 n，找到若干个完全平方数（比如 1, 4, 9, 16, ...）使得它们的和等于 n。你需要让组成和的完全平方数的个数最少。
     *
     * 给你一个整数 n ，返回和为 n 的完全平方数的 最少数量 。
     *
     * 完全平方数 是一个整数，其值等于另一个整数的平方；换句话说，其值等于一个整数自乘的积。例如，1、4、9 和 16 都是完全平方数，
     * 而 3 和 11 不是。
     * @param n
     * @return
     */
    public int numSquares(int n) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        Set<Integer> set = new HashSet<>();
        set.add(0);
        int level = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            level ++ ;
            for (int i = 0;i < size; i ++) {
                int cur = queue.poll();
                for (int j = 0; j < n; j ++) {
                    int sum = cur + j * j;
                    if (sum == n) {
                        return level;
                    }
                    if (sum > n) {
                        break;
                    }
                    if (!set.contains(sum)) {
                        queue.offer(sum);
                        set.add(sum);
                    }
                }
            }
        }
        return level;
    }

    /**
     * 111. 二叉树的最小深度
     * 给定一个二叉树，找出其最小深度。
     *
     * 最小深度是从根节点到最近叶子节点的最短路径上的节点数量。
     *
     * 说明：叶子节点是指没有子节点的节点。
     * @param root
     * @return
     */

    public int minDepth(TreeNode root) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) return 1;
        int mindep = Integer.MAX_VALUE;
        if (root.left != null) {
            mindep = Math.min(mindep,minDepth(root.left));
        }
        if (root.right != null) {
            mindep = Math.min(mindep,minDepth(root.right));
        }
        return mindep + 1;
    }

    /**
     * 1306. 跳跃游戏 III
     * 这里有一个非负整数数组 arr，你最开始位于该数组的起始下标 start 处。当你位于下标 i 处时，
     * 你可以跳到 i + arr[i] 或者 i - arr[i]。
     *
     * 请你判断自己是否能够跳到对应元素值为 0 的 任一 下标处。
     *
     * 注意，不管是什么情况下，你都无法跳到数组之外。
     * dfs
     * @param arr
     * @param start
     * @return
     */
    public boolean canReach(int[] arr, int start) {
        boolean[] vis = new boolean[arr.length];
        return canReach(arr,start,vis);
    }
    public boolean canReach(int[] arr, int start,boolean[] vis) {
        if (start < 0 || start >= arr.length || vis[start]) {
            return false;
        }
        if (arr[start] == 0) {
            return true;
        }
        vis[start] = true;
        return canReach(arr, start + arr[start], vis) || canReach(arr, start - arr[start],vis);
    }

    /**
     * bfs
     * @param arr
     * @param start
     * @return
     */
    public boolean canReach1(int[] arr, int start) {
        boolean[] vis = new boolean[arr.length];
        int m = arr.length;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            if (arr[cur] == 0) {
                return true;
            }
            int pre = cur + arr[cur];
            if (pre >= 0 && pre < m && !vis[pre]) {
                queue.offer(pre);
                vis[pre] = true;
            }
            int next = cur - arr[cur];
            if (next >= 0 && next < m && !vis[next]) {
                queue.offer(next);
                vis[next] = true;
            }
        }
        return false;
    }

    /**
     * 剑指 Offer 11. 旋转数组的最小数字
     * 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。输入一个递增排序的数组的一个旋转，
     * 输出旋转数组的最小元素。例如，数组 [3,4,5,1,2] 为 [1,2,3,4,5] 的一个旋转，该数组的最小值为1。
     *
     * 示例 1：
     *
     * 输入：[3,4,5,1,2]
     * 输出：1
     * @param numbers
     * @return
     */
    public int minArray(int[] numbers) {
        int i = 0,j = numbers.length - 1,mid ;
        while (i < j) {
            mid = (i + j) / 2;
            if (numbers[mid] < numbers[j]) {
                j = mid;
            }
            if (numbers[mid] > numbers[j]) {
                i = mid + 1;
            }
            if (numbers[mid] == numbers[j]) {
                j --;
            }
        }
        return numbers[i];
    }

    /**
     * 658. 找到 K 个最接近的元素
     * 给定一个排序好的数组 arr ，两个整数 k 和 x ，从数组中找到最靠近 x（两数之差最小）的 k 个数。返回的结果必须要是按升序排好的。
     *
     * 整数 a 比整数 b 更接近 x 需要满足：
     *
     * |a - x| < |b - x| 或者
     * |a - x| == |b - x| 且 a < b
     *
     * 示例 1：
     *
     * 输入：arr = [1,2,3,4,5], k = 4, x = 3
     * 输出：[1,2,3,4]
     * @param arr
     * @param k
     * @param x
     * @return
     */
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        int i = 0,j = arr.length - 1,l,r;
        int removenum = arr.length - k;
        while (removenum > 0) {
            l = Math.abs(arr[i] - x);
            r = Math.abs(arr[j] - x);
            if (l >= r) {
                i ++;
            } else {
                j --;
            }
            removenum --;
        }
        List<Integer> res = new ArrayList<>();
        for (int m = i; m <= j; m ++) {
            res.add(arr[m]);
        }
        return res;
    }

    /**
     * 575. 分糖果
     * 给定一个偶数长度的数组，其中不同的数字代表着不同种类的糖果，每一个数字代表一个糖果。
     * 你需要把这些糖果平均分给一个弟弟和一个妹妹。返回妹妹可以获得的最大糖果的种类数。
     * @param candyType
     * @return
     */
    public int distributeCandies(int[] candyType) {
        int count = candyType.length;//糖果数量
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < count; i ++) {
            set.add(candyType[i]);
        }
        int type = set.size();//糖果总类
        if (type > count / 2) {//如果总类大于数量的二分之一，则最大可分数量的二分之一
            return count / 2;
        } else {
            return type;
        }
    }

    /**
     * 1487. 保证文件名唯一
     * 给你一个长度为 n 的字符串数组 names 。你将会在文件系统中创建 n 个文件夹：在第 i 分钟，新建名为 names[i] 的文件夹。
     *
     * 由于两个文件 不能 共享相同的文件名，因此如果新建文件夹使用的文件名已经被占用，
     * 系统会以 (k) 的形式为新文件夹的文件名添加后缀，其中 k 是能保证文件名唯一的 最小正整数 。
     *
     * 返回长度为 n 的字符串数组，其中 ans[i] 是创建第 i 个文件夹时系统分配给该文件夹的实际名称。
     * @param names
     * @return
     */
    public String[] getFolderNames(String[] names) {
        String[] res = new String[names.length];
        Map<String,Integer> map = new HashMap<>();
        for (int i = 0; i < names.length; i ++) {
            String name = names[i];
            if (map.containsKey(name)) {
                int count = map.get(name);
                while (map.containsKey(name + "(" + count + ")")) {
                    count ++;
                }
                map.put(name + "(" + count + ")", 1);
                map.put(name, map.get(name) + 1);
                res[i] = name + "(" + count + ")";
            } else {
                res[i] = name;
                map.put(name,1);
            }
        }
        return res;
    }


    /**
     * 310. 最小高度树
     * 树是一个无向图，其中任何两个顶点只通过一条路径连接。 换句话说，一个任何没有简单环路的连通图都是一棵树。
     *
     * 给你一棵包含 n 个节点的树，标记为 0 到 n - 1 。给定数字 n 和一个有 n - 1 条无向边的 edges 列表（每一个边都是一对标签），
     * 其中 edges[i] = [ai, bi] 表示树中节点 ai 和 bi 之间存在一条无向边。
     *
     * 可选择树中任何一个节点作为根。当选择节点 x 作为根节点时，设结果树的高度为 h 。在所有可能的树中，具有最小高度的树（即，min(h)）被称为 最小高度树 。
     *
     * 请你找到所有的 最小高度树 并按 任意顺序 返回它们的根节点标签列表。
     *
     * 树的 高度 是指根节点和叶子节点之间最长向下路径上边的数量。
     * @param n
     * @param edges
     * @return
     */
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        return null;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        String[] str = new String[]{"0201","0101","0102","1212","2002"};
//        solution.openLock(str, "0202");
//        solution.movingCount1(3,2,17);

        Set<int[]> set = new HashSet<>();
        set.add(new int[]{0,0});
        set.add(new int[]{0,0});
        set.add(new int[]{0,0});
        System.out.println(set.size());
    }
}

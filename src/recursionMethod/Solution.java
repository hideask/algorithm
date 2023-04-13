package recursionMethod;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.DoublePredicate;
import java.util.logging.XMLFormatter;

public class Solution {

    /**
     *70. 爬楼梯
     * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
     *
     * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
     *
     * 注意：给定 n 是一个正整数。
     */
    public int climbStairs(int n) {
        //f(n) = f(n - 1) + f(n -2)
        int[] fn = new int[n + 1];
        fn[0] = 1;
        fn[1] = 1;
        for (int i = 2; i <= n; i ++) {
            fn[i] = fn[i - 1] + fn[i - 2];
        }
        return fn[n];
    }

    /**
     * 746. 使用最小花费爬楼梯
     * 数组的每个下标作为一个阶梯，第 i 个阶梯对应着一个非负数的体力花费值 cost[i]（下标从 0 开始）。
     *
     * 每当你爬上一个阶梯你都要花费对应的体力值，一旦支付了相应的体力值，你就可以选择向上爬一个阶梯或者爬两个阶梯。
     *
     * 请你找出达到楼层顶部的最低花费。在开始时，你可以选择从下标为 0 或 1 的元素作为初始阶梯。
     * @param cost
     * @return
     */
    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        cost = Arrays.copyOf(cost, n + 1);
        cost[n] = 0;
        int[] dp = new int[n + 1];
        dp[0] = cost[0];
        dp[1] = cost[1];
        //决策过程
        for (int i = 2; i <= n ; i ++) {
            dp[i] = Math.min(dp[i - 1], dp[i - 2]) + cost[i];
        }
        return dp[n];
    }

    /**
     * 120. 三角形最小路径和
     * 给定一个三角形 triangle ，找出自顶向下的最小路径和。
     *
     * 每一步只能移动到下一行中相邻的结点上。
     * 相邻的结点 在这里指的是 下标 与 上一层结点下标 相同或者等于 上一层结点下标 + 1 的两个结点。
     * 也就是说，如果正位于当前行的下标 i ，那么下一步可以移动到下一行的下标 i 或 i + 1 。
     * @param triangle
     * @return
     */
    public int minimumTotal(List<List<Integer>> triangle) {
//        //我从哪里来
//        int n = triangle.size();
//        //初始化两行数据
//        int[][] dp = new int[2][n];
//
//        //滚动数组
//        for (int i = 0 ; i < n; i ++) {
//            dp[(n - 1) % 2][i] = triangle.get(n - 1).get(i);
//        }
//
//        for (int i = n - 2; i >= 0 ; i --) {
//            int ind = i % 2;
//            int next_ind = ind == 0 ? 1 : 0;
//            for (int j = 0; j <= i ; j ++) {
//                dp[ind][j] = Math.min(dp[next_ind][j],dp[next_ind][j + 1]) + triangle.get(i).get(j);
//            }
//        }
//        return dp[0][0];

        //我到哪里去
//        int n = triangle.size();
//        int[][] dp = new int[n][n];
//        for (int i = 0 ; i < n ; i ++) {
//            for (int j = 0; j <= i; j++) {
//                dp[i][j] = Integer.MAX_VALUE;
//            }
//        }
//        dp[0][0] = triangle.get(0).get(0);
//        for (int i = 0 ; i < n - 1; i ++) {
//            for (int j = 0; j <= i ; j ++) {
//                dp[i + 1][j] = Math.min(dp[i + 1][j], dp[i][j] + triangle.get(i + 1).get(j));
//                dp[i + 1][j + 1] = Math.min(dp[i + 1][j + 1], dp[i][j] + triangle.get(i + 1).get(j + 1));
//            }
//        }
//
//        int ans = Integer.MAX_VALUE;
//        for (int x : dp[n - 1]) {
//            ans = Math.min(x, ans);
//        }
//        return ans;

        //我到哪里去,二维数组
        int n = triangle.size();
        int[][] dp = new int[2][n];
        for (int i = 0 ; i < n ; i ++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        dp[0][0] = triangle.get(0).get(0);
        for (int i = 0 ; i < n - 1; i ++) {
            int idx = i % 2;
            int new_idx = 1 - idx;
            for (int j = 0; j <= i ; j ++) {
                dp[new_idx][j] = Math.min(dp[new_idx][j], dp[idx][j] + triangle.get(i + 1).get(j));
                dp[new_idx][j + 1] = Math.min(dp[new_idx][j + 1], dp[idx][j] + triangle.get(i + 1).get(j + 1));
            }
            Arrays.fill(dp[idx],Integer.MAX_VALUE);
        }

        int ans = Integer.MAX_VALUE;
        for (int x : dp[(n - 1) % 2]) {
            ans = Math.min(x, ans);
        }
        return ans;
    }

    /**
     * 119. 杨辉三角 II
     * 给定一个非负索引 rowIndex，返回「杨辉三角」的第 rowIndex 行。
     *
     * 在「杨辉三角」中，每个数是它左上方和右上方的数的和。
     * @param rowIndex
     * @return
     */
    public List<Integer> getRow(int rowIndex) {
        List<Integer> res = new ArrayList<>();
        if (rowIndex == 0) {
            res.add(1);
            return res;
        }

        int[][] dp = new int[2][rowIndex + 1];
        dp[0][0] = 1;
        dp[1][0] = 1;
        dp[1][1] = 1;
        for (int i = 2 ; i <= rowIndex ; i ++) {
            int idx = i % 2;
            int next_ind = idx == 0 ? 1 : 0;
            int j = 0;
            for (; j < i; j ++) {
                if (j == 0) {
                    dp[idx][j] = 1;
                } else {
                    dp[idx][j] = dp[next_ind][j] + dp[next_ind][j - 1];
                }
            }
            dp[idx][j] = 1;
        }

        for (int num : dp[rowIndex % 2] ) {
            res.add(num);
        }
        return res;
    }

    /**
     * 53. 最大子序和
     * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {
        int n = nums.length;
        for (int i = 1; i < n ; i ++) {
            nums[i] = nums[i - 1] + nums[i];
        }
        int ans = Integer.MIN_VALUE,pre = 0;
        for (int x : nums) {
            ans = Math.max(x - pre, ans);
            pre = Math.min(x, pre);
        }
        return ans;
    }

    /**
     * 122. 买卖股票的最佳时机 II
     * 给定一个数组 prices ，其中 prices[i] 是一支给定股票第 i 天的价格。
     *
     * 设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
     *
     * 注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        int ans = 0;
        int n = prices.length;
        for (int i = 1; i < n; i ++) {
            if (prices[i] - prices[i - 1] > 0) {
                ans += (prices[i] - prices[i - 1]);
            }
        }
        return ans;
    }

    /**
     * 198. 打家劫舍
     * 你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，
     * 影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
     *
     * 给定一个代表每个房屋存放金额的非负整数数组，计算你 不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。
     * @param nums
     * @return
     */
    public int rob(int[] nums) {
        //dp[n] 0 = max(dp[n - 1] 0 ，dp[n - 1] 1);//最后一家不偷能获得的最大收益
        //dp[n] 1 = dp[n-1] 0 + nums[n];//偷最后一家
        int n = nums.length;
        int[][] dp = new int[2][2];

        dp[0][0] = 0;
        dp[0][1] = nums[0];
        for (int i = 1; i < n ; i ++) {
            int idx = i % 2;
            int pre_idx = idx == 0 ? 1 : 0;
            dp[idx][0] = Math.max(dp[pre_idx][0], dp[pre_idx][1]);
            dp[idx][1] = dp[pre_idx][0] + nums[i];
        }
        int idx = (n - 1) % 2;
        return Math.max(dp[idx][0],dp[idx][1]);

    }

    public int rob1(int[] nums) {
        //dp[n] 0 = max(dp[n - 1] 0 ，dp[n - 1] 1);//最后一家不偷能获得的最大收益
        //dp[n] 1 = dp[n-1] 0 + nums[n];//偷最后一家
        //非滚动数组
        int n = nums.length;
        int[][] dp = new int[n][2];

        dp[0][0] = 0;
        dp[0][1] = nums[0];
        for (int i = 1; i < n ; i ++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1]);
            dp[i][1] = dp[i - 1][0] + nums[i];
        }
        return Math.max(dp[n - 1][0],dp[n - 1][1]);
    }

    /**
     * 152. 乘积最大子数组
     * 给你一个整数数组 nums ，请你找出数组中乘积最大的连续子数组（该子数组中至少包含一个数字），并返回该子数组所对应的乘积。
     * @param nums
     * @return
     */
    public int maxProduct(int[] nums) {
        //dp[n] = max(dp[n - 1] * val[n], val[n]);
        //会有负数
        int ans = Integer.MIN_VALUE,max = 1, min = 1;
        for (int num : nums) {
            if (num < 0) {
                int tmp = max;
                max = min;
                min = tmp;
            }
            max = Math.max(max * num, num);
            min = Math.min(min * num, num);
            ans = Math.max(ans, max);
        }
        return ans;
    }

    /**
     * 322. 零钱兑换
     * 给你一个整数数组 coins ，表示不同面额的硬币；以及一个整数 amount ，表示总金额。
     *
     * 计算并返回可以凑成总金额所需的 最少的硬币个数 。如果没有任何一种硬币组合能组成总金额，返回 -1 。
     *
     * 你可以认为每种硬币的数量是无限的。
     * @param coins
     * @param amount
     * @return
     */
    public int coinChange(int[] coins, int amount) {
        //dp[n] = min(dp[n - x]) + 1 ,求凑成n - x当前面额所需的最小值
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, -1);//默认凑不出来的为-1
        dp[0] = 0;
        for (int i = 1; i <= amount; i ++) {
            for (int x : coins) {
                if (x > i) {//x大于当前需要凑的面额，跳过
                    continue;
                }
                if (dp[i - x] == -1) {
                    continue;
                }
                //要求的是最小数量，对于当前面额，需要dp[i]的值小于dp[i - x]
                if (dp[i] == - 1 || dp[i] > dp[i - x] + 1) {
                    dp[i] = dp[i - x] + 1;
                }
            }
        }
        return dp[amount];
    }

    public int coinChange1(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, -1);
        dp[0] = 0;
        for (int coin : coins) {
            for (int i = coin ; i <= amount; i ++) {
                if (dp[i - coin] == -1) {
                    continue;
                }
                if (dp[i] == -1 || dp[i] > dp[i - coin] + 1) {
                    dp[i] = dp[i - coin] + 1;
                }
            }
        }
        return dp[amount];
    }

    /**
     * 518. 零钱兑换 II
     * 给你一个整数数组 coins 表示不同面额的硬币，另给一个整数 amount 表示总金额。
     *
     * 请你计算并返回可以凑成总金额的硬币组合数。如果任何硬币组合都无法凑出总金额，返回 0 。
     *
     * 假设每一种面额的硬币有无限个。
     *
     * 题目数据保证结果符合 32 位带符号整数。
     * @param amount
     * @param coins
     * @return
     */
    public int change(int amount, int[] coins) {
        //dp[i][j] = dp[i - 1][j] + dp[i][j - x]
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        for (int coin : coins) {
            for (int i = coin; i <= amount; i ++) {
                dp[i] += dp[i - coin];
            }
        }
        return dp[amount];
    }

    /**
     * 300. 最长递增子序列
     * 给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。
     *
     * 子序列是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的子序列。
     * @param nums
     * @return
     */
    public int lengthOfLIS(int[] nums) {
        //dp[n] = max[dp[j] + 1] & j < n
        int n = nums.length;
        int[] dp = new int[n];
        int ans = 0;
        for (int i = 0; i < n; i ++) {
            dp[i] = 1;
            for (int j = 0 ; j < i ; j ++) {
                if (nums[j] >= nums[i]) {
                    continue;
                }
                dp[i] = Math.max(dp[j] + 1,dp[i]) ;
            }
            ans = Math.max(dp[i], ans);
        }
        return ans;
    }

    /**
     * 剑指 Offer II 095. 最长公共子序列
     * 给定两个字符串 text1 和 text2，返回这两个字符串的最长 公共子序列 的长度。如果不存在 公共子序列 ，返回 0 。
     *
     * 一个字符串的 子序列 是指这样一个新的字符串：它是由原字符串在不改变字符的相对顺序的情况下删除某些字符（也可以不删除任何字符）后组成的新字符串。
     *
     * 例如，"ace" 是 "abcde" 的子序列，但 "aec" 不是 "abcde" 的子序列。
     * 两个字符串的 公共子序列 是这两个字符串所共同拥有的子序列。
     * @param text1
     * @param text2
     * @return
     */
    public int longestCommonSubsequence(String text1, String text2) {
        int s1 = text1.length();
        int s2 = text2.length();
        int[][] dp = new int[s1 + 1][s2 + 1];

        for (int i = 1; i <= s1; i ++) {
            for (int j = 1; j <= s2; j ++) {
                dp[i][j] = Math.max(dp[i - 1][j],dp[i][j - 1]);
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + 1);
                }
            }
        }
        return dp[s1][s2];
    }

    /**
     * 给出⼀个字符串S，问对字符串S最少切⼏⼑，使得分成的每⼀部分都是⼀个回⽂
     * （注意：单⼀字符是回⽂串）
     *
     * 回文串 是正着读和反着读都一样的字符串。
     * @param s
     * @return
     */
    public int partition(String s) {
        int n = s.length();
        int[] dp = new int[n + 1];
        dp[0] = 0;
        for (int i = 1; i <= n ; i ++) {
            dp[i] = i;
            for (int j = 0; j < i ; j ++) {
                if (judge(s.substring(j , i))) {
                    dp[i] = Math.min(dp[j] + 1, dp[i]);
                }
            }
        }
        return dp[n] - 1;
    }

    public boolean judge(String s) {
        int i = 0;
        int j = s.length() - 1;
        while (i < j) {
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
            i ++;
            j --;
        }
        return true;
    }

    /**
     * 0/1背包，给⼀个能承重V的背包，和n件物品，我们⽤重量和价值的⼆元组来表⽰⼀个物品，第i
     * 件物品表⽰为（Vi，Wi），问：在背包不超重的情况下，得到物品的最⼤价值是多少？
     * 价值V，重量W
     */
    public void package01() throws FileNotFoundException {
        int MAX_N = 100;//最多100件
        int MAX_V = 10000;//最大10000的重量
        int[] v = new int[MAX_N + 5];//重量数组
        int[] w = new int[MAX_N + 5];//价格数组
        int[][] dp = new int[MAX_N + 5][MAX_V + 5];
        Scanner sc = new Scanner(new FileInputStream("D://1.txt"));
        int vn = sc.nextInt();//总重量
        int n = sc.nextInt();//有多少件物品
        for (int i = 0 ; i < n; i ++) {
            v[i] = sc.nextInt();
            w[i] = sc.nextInt();
        }

        for (int i = 1 ;i <= n; i ++) {
            for (int j = 0; j <= vn; j ++) {
                //第i 件物品不拿
                dp[i][j] = dp[i - 1][j];
                //第i 件物品拿,
                if (j >= v[i]) {
                    dp[i][j] = Math.max(dp[i][j],dp[i - 1][j - v[i]] + w[i]);
                }
            }
        }
        System.out.println(dp[n][vn]);

    }

    public void package011() throws FileNotFoundException {
        //滚动数组
        int MAX_N = 100;//最多100件
        int MAX_V = 10000;//最大10000的重量
        int[] v = new int[MAX_N + 5];//重量数组
        int[] w = new int[MAX_N + 5];//价格数组
        int[][] dp = new int[2][MAX_V + 5];
        Scanner sc = new Scanner(new FileInputStream("D://1.txt"));
        int vn = sc.nextInt();//总重量
        int n = sc.nextInt();//有多少件物品
        for (int i = 0 ; i < n; i ++) {
            v[i] = sc.nextInt();
            w[i] = sc.nextInt();
        }

        for (int i = 1 ;i <= n; i ++) {
            int idx = i % 2;
            int next_idx = 1 - idx;
            for (int j = 0; j <= vn; j ++) {

                //第i 件物品不拿
                dp[idx][j] = dp[next_idx][j];
                //第i 件物品拿,
                if (j >= v[i]) {
                    dp[idx][j] = Math.max(dp[idx][j],dp[next_idx][j - v[i]] + w[i]);
                }
            }
        }
        System.out.println(dp[n % 2][vn]);

    }

    public void package012() throws FileNotFoundException {
        //滚动数组，去掉 v, n数组
        int MAX_N = 100;//最多100件
        int MAX_V = 10000;//最大10000的重量
        int[][] dp = new int[2][MAX_V + 5];
        Scanner sc = new Scanner(new FileInputStream("D://1.txt"));
        int vn = sc.nextInt();//总重量
        int n = sc.nextInt();//有多少件物品

        int v, w;
        for (int i = 1 ;i <= n; i ++) {
            v = sc.nextInt();
            w = sc.nextInt();
            int idx = i % 2;
            int next_idx = 1 - idx;
            for (int j = 0; j <= vn; j ++) {

                //第i 件物品不拿
                dp[idx][j] = dp[next_idx][j];
                //第i 件物品拿,
                if (j >= v) {
                    dp[idx][j] = Math.max(dp[idx][j],dp[next_idx][j - v] + w);
                }
            }
        }
        System.out.println(dp[n % 2][vn]);

    }

    /**
     * 714. 买卖股票的最佳时机含手续费
     * 给定一个整数数组 prices，其中第 i 个元素代表了第 i 天的股票价格 ；整数 fee 代表了交易股票的手续费用。
     *
     * 你可以无限次地完成交易，但是你每笔交易都需要付手续费。如果你已经购买了一个股票，在卖出它之前你就不能再继续购买股票了。
     *
     * 返回获得利润的最大值。
     *
     * 注意：这里的一笔交易指买入持有并卖出股票的整个过程，每笔交易你只需要为支付一次手续费。
     * @param prices
     * @param fee
     * @return
     */
    public int maxProfit(int[] prices, int fee) {
        //利润是第 i 天持有股票和 不持有股票之间的最大值
        int n = prices.length;
        int[][] dp = new int[n][2];
        dp[0][0] = 0;//初始化第0天不持有
        dp[0][1] = -prices[0];//初始化第0天持有
        for (int i = 1; i < n; i ++) {
            //第i天不持有的收益等于 第 i - 1 天不持有 与 第 i - 1天持有并且第i 天卖掉之间收益的最大值
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i] - fee);
            //第i天持有的收益等于 第 i - 1 天持有 与 第 i - 1天不持有并且第i 天买入之间收益的最大值
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        }
        return Math.max(dp[n - 1][0],dp[n - 1][1]);
    }

    /**
     * 213. 打家劫舍 II
     * 你是一个专业的小偷，计划偷窃沿街的房屋，每间房内都藏有一定的现金。这个地方所有的房屋都 围成一圈 ，
     * 这意味着第一个房屋和最后一个房屋是紧挨着的。同时，相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警 。
     *
     * 给定一个代表每个房屋存放金额的非负整数数组，计算你 在不触动警报装置的情况下 ，今晚能够偷窃到的最高金额。
     * @param nums
     * @return
     */
    public int rob2(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return nums[0];
        }
        int[][] dp = new int[n][2];
        //不偷最后一家,所能获取的最大价值
        dp[0][0] = 0;
        dp[0][1] = nums[0];
        for (int i = 1; i < n ;i ++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1]);
            dp[i][1] = dp[i - 1][0] + nums[i];
        }
        int ans1 = dp[n - 1][0];

        //不偷第一家，所能获取的最大价值
        dp[0][0] = 0;
        dp[0][1] = 0;
        for (int i = 1; i < n; i ++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1]);
            dp[i][1] = dp[i - 1][0] + nums[i];
        }
        int ans2 = Math.max(dp[n - 1][0], dp[n - 1][1]);
        return Math.max(ans1,ans2);
    }

    /**
     * 416. 分割等和子集
     * 给你一个 只包含正整数 的 非空 数组 nums 。请你判断是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。
     * @param nums
     * @return
     */
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int i = 0; i < nums.length; i ++) {
            sum += nums[i];
        }
        //奇数取不到
        if (sum % 2 != 0) {
            return false;
        }
        //一维数组，最大长度为sum + 1;
        int[] fn = new int[sum + 1];
        fn[0] = 1;
        sum = 0;
        //从后往前扫描，获取各个sum的值
        for (int x : nums) {
            sum += x;
            for (int j = sum; j >= x; j --) {
                fn[j] |= fn[j - x];
            }
        }
        return fn[sum / 2] == 1 ? true : false;
    }

    /**
     * 474. 一和零
     * 给你一个二进制字符串数组 strs 和两个整数 m 和 n 。
     *
     * 请你找出并返回 strs 的最大子集的长度，该子集中 最多 有 m 个 0 和 n 个 1 。
     *
     * 如果 x 的所有元素也是 y 的元素，集合 x 是集合 y 的 子集 。
     * @param strs
     * @param m
     * @param n
     * @return
     */
    public int findMaxForm(String[] strs, int m, int n) {
        int[][] dp = new int[m + 1][n + 1];
        for (String str : strs) {
            int count0 = 0,count1 = 0;
            for (int i = 0; i < str.length(); i ++) {
                if (str.charAt(i) == '0') {
                    count0 ++ ;
                } else {
                    count1 ++ ;
                }
            }
            for (int i = m ; i >= count0; i --) {
                for (int j = n; j >=count1; j --) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - count0][j - count1] + 1);
                }
            }
        }
        return dp[m][n];
    }

    /**
     * 494. 目标和
     * 给你一个整数数组 nums 和一个整数 target 。
     *
     * 向数组中的每个整数前添加 '+' 或 '-' ，然后串联起所有整数，可以构造一个 表达式 ：
     *
     * 例如，nums = [2, 1] ，可以在 2 之前添加 '+' ，在 1 之前添加 '-' ，然后串联起来得到表达式 "+2-1" 。
     * 返回可以通过上述方法构造的、运算结果等于 target 的不同 表达式 的数目。
     * @param nums
     * @param target
     * @return
     */
    public int findTargetSumWays(int[] nums, int target) {
        //f[i][j] = f[i - 1][j - x] + f[i - 1][j + x]
        int sum = Arrays.stream(nums).sum();
        if (Math.abs(target) > sum) {
            return 0;
        }
        int n = nums.length;
        //定义偏移数组，用数组偏移量表示正负数组
        //使用滚动数组
        int[][] dp = new int[2][2 * sum + 1];
        int m = sum;
        sum = 0;
        //初始化初始坐标值，类似dp[0][0]
        dp[0][m] = 1;
        //我到哪里去i
        for (int i = 1; i <= n; i ++) {
            int idx = i % 2;
            int pre = 1 - idx;
            int x = nums[i - 1];
            Arrays.fill(dp[idx], 0);
            for (int j = -sum; j <= sum; j ++) {
                //从初始位置向左右扩展，m为偏移量
                dp[idx][j + m - x] += dp[pre][j + m];
                dp[idx][j + m + x] += dp[pre][j + m];
            }
            sum += x;
        }
        return dp[n % 2][m + target];
    }

    /**
     * 377. 组合总和 Ⅳ
     * 给你一个由 不同 整数组成的数组 nums ，和一个目标整数 target 。请你从 nums 中找出并返回总和为 target 的元素组合的个数。
     *
     * 题目数据保证答案符合 32 位整数范围。
     * @param nums
     * @param target
     * @return
     */
    public int combinationSum4(int[] nums, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for (int i = 1;i <= target; i ++) {
            for (int num : nums) {
                if (i < num) {
                    continue;
                }
                dp[i] += dp[i - num];
            }
        }
        return dp[target];
    }

    public Solution() {}

    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    /**
     * 382. 链表随机节点
     * 给定一个单链表，随机选择链表的一个节点，并返回相应的节点值。保证每个节点被选的概率一样。
     *
     * 进阶:
     * 如果链表十分大且长度未知，如何解决这个问题？你能否使用常数级空间复杂度实现？
     */
    /** @param head The linked list's head.
    Note that the head is guaranteed to be not null, so it contains at least one node. */
    ListNode head ;
    int n = 0;
    public Solution(ListNode head) {
        this.head = head;
        ListNode p = head,q = new ListNode();
        while (p != null) {
            q = p;
            p = p.next;
            n ++;
        }
        q.next = head;
    }

    /** Returns a random node's value. */
    public int getRandom() {
        Random random = new Random();
        int m = random.nextInt(n);
        while (m -- > 0) {
            head = head.next;
        }
        return head.val;
    }

    /**
     * 462. 最少移动次数使数组元素相等 II
     * 给定一个非空整数数组，找到使所有数组元素相等所需的最小移动数，
     * 其中每次移动可将选定的一个元素加1或减1。 您可以假设数组的长度最多为10000。
     * @param nums
     * @return
     */
    public int minMoves2(int[] nums) {
        int n = nums.length;
        if (n == 2) {
            return Math.abs(nums[0] - nums[1]);
        }
        //其实是选择中位数
        Arrays.sort(nums);
        int m = nums[n / 2];
        int ans = 0;
        for (int num : nums) {
            ans += Math.abs(num - m);
        }
        return ans;
    }

    /**
     * 77. 组合
     * 给定两个整数 n 和 k，返回范围 [1, n] 中所有可能的 k 个数的组合。
     *
     * 你可以按 任何顺序 返回答案。
     * @param n
     * @param k
     * @return
     */
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> tmp = new ArrayList<>();
        for (int i = 0;i < k; i ++) {
            tmp.add(1);
        }
        combinedfs(n,k,0, 1,tmp,ans);
        return ans;
    }

    /**
     *
     * @param n 最大可选择的数
     * @param k 最大可选择多少个数
     * @param cnt 已经选择多少个数
     * @param m 选到哪个数字
     * @param tmp 临时list
     * @param ans 结果list
     * @return
     */
    public void combinedfs(int n, int k, int cnt,int m,
                                          List<Integer> tmp, List<List<Integer>> ans) {
        if (cnt == k) {
            ans.add(new ArrayList<>(tmp));
            return ;
        }

        //如果能选的数字小于需要选的数字
        if (n - m + 1 < k - cnt) {
            return;
        }
        tmp.set(cnt, m);
        //如果选择了第m个数字
        combinedfs(n,k, cnt + 1,m + 1,tmp,ans);
        //如果没选择第m个数字
        combinedfs(n,k,cnt,m + 1, tmp,ans);
        return;
    }

    /**
     * 234. 回文链表
     * 给你一个单链表的头节点 head ，请你判断该链表是否为回文链表。如果是，返回 true ；否则，返回 false 。
     * @param head
     * @return
     */
    public boolean isPalindrome(ListNode head) {
        if (head == null) {
            return false;
        }
        ListNode p = head,q = head;
        //找到链表的中点,快慢指针
        while (p.next != null && q.next != null && q.next.next != null) {
            p = p.next;
            q = q.next.next;
        }
        //翻转链表
        p.next = reverse(p.next);

        q = p.next;
        p = head;
        while (q != null) {
            if (p.val != q.val) {
                return false;
            }
            p = p.next;
            q = q.next;
        }
        return true;
    }

    public ListNode reverse(ListNode head) {
        ListNode res = new ListNode(), p = head,q = new ListNode();
        while (p != null) {
            q = p.next;
            p.next = res.next;
            res.next = p;
            p = q;
        }
        return res.next;
    }

    /**
     * 309. 最佳买卖股票时机含冷冻期
     * 给定一个整数数组，其中第 i 个元素代表了第 i 天的股票价格 。​
     *
     * 设计一个算法计算出最大利润。在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）:
     *
     * 你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
     * 卖出股票后，你无法在第二天买入股票 (即冷冻期为 1 天)。
     * 示例:
     *
     * 输入: [1,2,3,0,2]
     * 输出: 3
     * 解释: 对应的交易状态为: [买入, 卖出, 冷冻期, 买入, 卖出]
     * @param prices
     * @return
     */
    public int maxProfit3(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[2][3];
        dp[0][0] = 0;//非冷冻不持股
        dp[0][1] = - prices[0];//持股
        dp[0][2] = 0;//冷冻期不持股
        for (int i = 1; i < n; i ++) {
            int idx = i % 2;
            int pre = 1 - idx;
            int price = prices[i];
            dp[idx][0] = Math.max(dp[pre][0], dp[pre][2]);
            dp[idx][1] = Math.max(dp[pre][1], dp[pre][0] - price);
            dp[idx][2] = dp[pre][1] + price;
        }
        int m = (n - 1) % 2;
        return Math.max(Math.max(dp[m][0],dp[m][1]), dp[m][2]);
    }

    /**
     * 1218. 最长定差子序列
     * 给你一个整数数组 arr 和一个整数 difference，请你找出并返回 arr 中最长等差子序列的长度，
     * 该子序列中相邻元素之间的差等于 difference 。
     *
     * 子序列 是指在不改变其余元素顺序的情况下，通过删除一些元素或不删除任何元素而从 arr 派生出来的序列。
     *
     * 提示：
     *
     * 1 <= arr.length <= 100000
     * -10000 <= arr[i], difference <= 10000
     * @param arr
     * @param difference
     * @return
     */
    public int longestSubsequence(int[] arr, int difference) {
        //按题意，我们定义一个20001的数组
        int[] dp = new int[20001];
        int n = arr.length;
        //因为存在负数，所以将所有的值加10000的偏移量
        for (int i = 0; i < n; i ++) {
            arr[i] += 10000;
        }

        int res = 0;

        for (int i = 0; i < n; i ++) {
            //获取当前值的上一个差值
            int x = arr[i] - difference;
            if (x < 0 || x > 20000) {
                //本身长度为1
                dp[arr[i]] = 1;
            } else {
                dp[arr[i]] = Math.max(dp[arr[i]] , dp[x] + 1);
            }
            res = Math.max(dp[arr[i]], res);
        }
        return res;
    }

    /**
     * 91. 解码方法
     * 一条包含字母 A-Z 的消息通过以下映射进行了 编码 ：
     *
     * 'A' -> 1
     * 'B' -> 2
     * ...
     * 'Z' -> 26
     * 要 解码 已编码的消息，所有数字必须基于上述映射的方法，反向映射回字母（可能有多种方法）。例如，"11106" 可以映射为：
     *
     * "AAJF" ，将消息分组为 (1 1 10 6)
     * "KJF" ，将消息分组为 (11 10 6)
     * 注意，消息不能分组为  (1 11 06) ，因为 "06" 不能映射为 "F" ，这是由于 "6" 和 "06" 在映射中并不等价。
     *
     * 给你一个只含数字的 非空 字符串 s ，请计算并返回 解码 方法的 总数 。
     *
     * 题目数据保证答案肯定是一个 32 位 的整数。
     * @param s
     * @return
     */
    public int numDecodings(String s) {
        int n = s.length();
        char[] chars = s.toCharArray();
        //如果第一位就是0，则无法解码
        if (chars[0] == '0') {
            return 0;
        }
        int[] dp = new int[n];
        //初始化第一位就为1i
        dp[0] = 1;

        for (int i = 1; i < n; i ++) {
            char c = chars[i];
            char pre = chars[i - 1];
            if (c == '0') {//如果当前位是0，则需要同前一位相连解码
                if (pre != '1' && pre != '2') {//前一位不为1 和 2 ，则无法解码
                    return 0;
                } else {//如果当前下标 为 1则组合数是 1； 下标大于1，取前两位的组合数
                    dp[i] = i == 1 ? 1 : dp[i - 2];
                }
            } else if (c >= '1' && c <= '6' && pre == '2'
                    || pre == '1') {//如果当前在 1和6之间，并且前一位等于 2，或者前一位为1
                //如果当前下标为1 ，则有2种组合；下标大于1，取前一位组合数和前两位组合数的和
                dp[i] = i == 1 ? dp[i - 1] + 1 : dp[i - 1] + dp[i - 2];
            } else {
                //以上都不满足，就取前一位的组合数
                dp[i] = dp[i - 1];
            }
        }
        return dp[n - 1];
    }

    /**
     * 343. 整数拆分
     * 给定一个正整数 n，将其拆分为至少两个正整数的和，并使这些整数的乘积最大化。 返回你可以获得的最大乘积
     * @param n
     * @return
     */
    public int integerBreak(int n) {
        int[] dp = new int[n + 1];
        dp[2] = 1;//n = 2时，最大乘积为1；
        for (int i = 3; i < n + 1; i ++) {
            //i - j 在 i / 2作为分界点的前后是镜像的
            for (int j = 1 ; j <= i / 2; j ++) {
                //如果将n分为两个正整数时，可求得的最大值
                dp[i] = Math.max(dp[i], j * (i - j));
                //如果再将（i - j）拆分，所能求得的最大值
                dp[i] = Math.max(dp[i], j * dp[(i - j)]);
                //如果再将 j 拆分，所能求得的最大值
                dp[i] = Math.max(dp[i], dp[j] * (i - j));
                ////如果再将（i - j） 和 j 拆分，所能求得的最大值
                dp[i] = Math.max(dp[i], dp[j] * dp[(i - j)]);
            }
        }
        return dp[n];
    }

    /**
     * 55. 跳跃游戏
     * 给定一个非负整数数组 nums ，你最初位于数组的 第一个下标 。
     *
     * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
     *
     * 判断你是否能够到达最后一个下标。
     * @param nums
     * @return
     */
    public boolean canJump(int[] nums) {
        int n = nums.length;
        if (n == 1) {
            return true;
        }
        //使用两个变量模拟滚动数组
        int pre = nums[0];//记录上一个起跳点最大能跳到的距离
        int cur = 0;//初始起跳点为0

        for (int i = 1; i < n; i ++) {
            //如果上一个起跳点覆盖到 i， 我们就在pre 和 nums[i] + 1中比较能达到的最大点；如果覆盖不到i, 说明当前的最大起跳点仍然
            //是pre
//            cur = pre >= i ? Math.max(pre, nums[i] + i) : pre;
            if (pre < i) {
                return false;
            }
            cur = Math.max(pre, nums[i] + i);
            pre = cur;
        }
        //返回最后一个起跳点是否覆盖 n - 1;
        return pre >= n - 1;
    }

    /**
     * 413. 等差数列划分
     * 如果一个数列 至少有三个元素 ，并且任意两个相邻元素之差相同，则称该数列为等差数列。
     *
     * 例如，[1,3,5,7,9]、[7,7,7,7] 和 [3,-1,-5,-9] 都是等差数列。
     * 给你一个整数数组 nums ，返回数组 nums 中所有为等差数组的 子数组 个数。
     *
     * 子数组 是数组中的一个连续序列。
     * @param nums
     * @return
     */
    public int numberOfArithmeticSlices(int[] nums) {
        int n = nums.length;
        //dp[n] = dp[n - 1] + 1 ,同时满足 nums[n] + nums[n - 2] = 2 * nums[n - 1];
        int[] dp = new int[n];
        for (int i = 2; i < n; i ++) {
            if (nums[i] + nums[i - 2] == 2 * nums[i - 1]) {
                dp[i] = dp[i - 1] + 1;
            }
        }
        return Arrays.stream(dp).sum();
    }

    public int numberOfArithmeticSlices1(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        //j 为前一个不构成等差数列的最近一个值
        for (int i = 2,j = 1; i < n ; i ++) {
            //满足等差数列的条件时，dp[i] = dp[i - 1] + (i - 1) - j + 1;
            if (nums[i] + nums[i - 2] == 2 * nums[i - 1]) {
                dp[i] = dp[i - 1] + i - j;
            } else {
                dp[i] = dp[i - 1];
                j = i;
            }
        }
        return dp[n];
    }

    /**
     * 139. 单词拆分
     * 给定一个非空字符串 s 和一个包含非空单词的列表 wordDict，判定 s 是否可以被空格拆分为一个或多个在字典中出现的单词。
     *
     * 说明：
     *
     * 拆分时可以重复使用字典中的单词。
     * 你可以假设字典中没有重复的单词。
     * 示例 1：
     * @param s
     * @param wordDict
     * @return
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        Set set = new HashSet(wordDict);
        int n = s.length();
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;
        for (int i = 1; i <= n; i ++) {
            for (int j = 0; j < i; j ++) {
                if (set.contains(s.substring(j, i)) && dp[j] == true) {
                    dp[i] = true;
                }
            }
        }
        return dp[n];
    }

    /**
     * 221. 最大正方形
     * 在一个由 '0' 和 '1' 组成的二维矩阵内，找到只包含 '1' 的最大正方形，并返回其面积。
     * @param matrix
     * @return
     */
    public int maximalSquare(char[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int[][] dp = new int[n][m];
        int len = 0;
        //以坐标i，j为右下角去构成正方形，
        for (int i = 0 ; i < n; i ++) {
            for (int j = 0; j < m; j ++) {
                if (matrix[i][j] == '1') {
                    //如果在上，左边界，则面积是0
                    if (i == 0 || j == 0) {
                        dp[i][j] = 1;
                    } else {
                        //比较左边和上边的边长，取最小值
                        dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]);
                        //再比较左上角的边长，取最小值，此时的边长再加上本身的边长1
                        dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - 1]) + 1;
                    }
                    len = Math.max(len ,dp[i][j]);
                }

            }
        }
        return len * len;
    }

    /**
     * 650. 只有两个键的键盘
     * 最初记事本上只有一个字符 'A' 。你每次可以对这个记事本进行两种操作：
     *
     * Copy All（复制全部）：复制这个记事本中的所有字符（不允许仅复制部分字符）。
     * Paste（粘贴）：粘贴 上一次 复制的字符。
     * 给你一个数字 n ，你需要使用最少的操作次数，在记事本上输出 恰好 n 个 'A' 。返回能够打印出 n 个 'A' 的最少操作次数。
     * @param n
     * @return
     */
    public int minSteps(int n) {
        //如果是质数，则操作次数就是他本身，因为质数的约数为1和他本身，
        //如果是合数，则dp[n] = dp[n - 1] + (i - j) /j + 1 --> dp[n - 1] + i / j,取最小操作数就是j
        //为i的最大约数
        if (n == 1) {
            return n;
        }
        int[] dp = new int[n + 1];
        dp[1] = 0;
        for (int i = 2; i <= n; i ++) {
            if (isPrime(i)) {
                dp[i] = i;
            } else {
                int j = getBigFactor(i);
                dp[i] = dp[i - j] + i / j;
            }
        }
        return dp[n];
    }

    /**
     * 获取最大约数
     * @param n
     * @return
     */
    public int getBigFactor(int n) {
        for (int i = n - 1; i >= 1; i --) {
            if (n % i == 0) {
                return i;
            }
        }
        return 1;
    }

    /**
     * 判断是否是质数
     * @param n
     * @return
     */
    public boolean isPrime(int n) {
        if (n == 2) {
            return true;
        }
        if (n % 2 == 0) {
            return false;
        }

        for (int i = 3; i <= Math.sqrt(n); i ++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 376. 摆动序列
     * 如果连续数字之间的差严格地在正数和负数之间交替，则数字序列称为 摆动序列 。第
     * ⼀个差（如果存在的话）可能是正数或负数。仅有⼀个元素或者含两个不等元素的序
     * 列也视作摆动序列。
     * 例如， [1, 7, 4, 9, 2, 5] 是⼀个 摆动序列 ，因为差值 (6, -3, 5, -7, 3) 是正负
     * 交替出现的。
     * 相反， [1, 4, 7, 2, 5] 和 [1, 7, 4, 5, 5] 不是摆动序列，第⼀个序列是因为它的
     * 前两个差值都是正数，第⼆个序列是因为它的最后⼀个差值为零。
     * ⼦序列 可以通过从原始序列中删除⼀些（也可以不删除）元素来获得，剩下的元素保
     * 持其原始顺序。
     * 给你⼀个整数数组 nums ，返回 nums 中作为 摆动序列 的 最⻓⼦序列的⻓度 。
     * @param nums
     * @return
     */
    public int wiggleMaxLength(int[] nums) {
        int n = nums.length;
        //去掉重复值
        List<Integer> list = new ArrayList();
        list.add(nums[0]);
        for (int i = 1; i < n; i ++) {
            if (nums[i] != nums[i - 1]) {
                list.add(nums[i]);
            }
        }
        n = list.size();
        if (n <= 2) {
            return n;
        }

        int[] dp = new int[n];
        dp[0] = 1;
        dp[1] = 2;
        for (int i = 2; i < n; i ++) {
            if ((list.get(i) - list.get(i - 1)) * (list.get(i - 1) - list.get(i - 2)) < 0) {
                dp[i] = dp[i - 1] + 1;
            } else {
                dp[i] = dp[i - 1];
            }
        }
        return dp[n - 1];
    }

    /**
     * 646. 最长数对链
     * 给出 n 个数对。 在每一个数对中，第一个数字总是比第二个数字小。
     *
     * 现在，我们定义一种跟随关系，当且仅当 b < c 时，数对(c, d) 才可以跟在 (a, b) 后面。我们用这种形式来构造一个数对链。
     *
     * 给定一个数对集合，找出能够形成的最长数对链的长度。你不需要用到所有的数对，你可以以任何顺序选择其中的一些数对来构造。
     *
     *
     * @param pairs
     * @return
     */
    public int findLongestChain(int[][] pairs) {
        //排序，为了使链表相对有序
        Arrays.sort(pairs, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });
        int n = pairs.length;
        int[] dp = new int[n];
        dp[0] = 1;
        for (int i = 1; i < n; i ++) {
            //和前面的相比较，找到一个最长的长度
            for (int j = i - 1; j >= 0; j --) {
                if (pairs[i][0] > pairs[j][1]) {
                    dp[i] = Math.max(dp[j] + 1, dp[i]);
                } else {
                    dp[i] = Math.max(dp[j], dp[i]);
                }
            }
        }
        return dp[n - 1];
    }

    /**
     * 1039. 多边形三角剖分的最低得分
     * 给定 N，想象一个凸 N 边多边形，其顶点按顺时针顺序依次标记为 A[0], A[i], ..., A[N-1]。
     *
     * 假设您将多边形剖分为 N-2 个三角形。对于每个三角形，该三角形的值是顶点标记的乘积，
     * 三角剖分的分数是进行三角剖分后所有 N-2 个三角形的值之和。
     *
     * 返回多边形进行三角剖分后可以得到的最低分。
     * @param values
     * @return
     */
    public int minScoreTriangulation(int[] values) {
        int n = values.length;
        int[][] dp = new int[n][n];
        //按边数遍历，最少3条边构成三角形
        for (int len = 2; len < n; len ++) {
            //顶点遍历
            for (int i = 0; i < n - len; i ++) {
                int j = i + len;//确定i,j的范围相差len
                dp[i][j] = Integer.MAX_VALUE;
                //k点构成一个三角形
                for (int k = i + 1; k < j; k ++) {
                    dp[i][j] = Math.min(values[i] * values[j] * values[k]
                                + dp[i][k] + dp[k][j],dp[i][j]);
                }
            }
        }
        return dp[0][n - 1];
    }

    // 377 382 462 77 234
    public static void main(String[] args) throws FileNotFoundException {
        Solution solution = new Solution();
        System.out.println(solution.partition("aaabaaaaaabaaaece"));
        solution.package01();
        solution.package011();
        solution.package012();
        solution.findMaxForm(new String[]{"10","0001","111001","1","0"}, 5, 3);
    }
}

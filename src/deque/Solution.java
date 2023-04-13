package deque;

import jdk.nashorn.internal.objects.annotations.Where;

import java.util.*;

public class Solution {
    /**
     * 239. 滑动窗口最大值
     * 给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
     *
     * 返回滑动窗口中的最大值。
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null && nums.length == 0) {
            return new int[]{};
        }
        Deque<Integer> deque = new LinkedList<>();
        int size = nums.length;

        int[] res = new int[size - k + 1];
        for (int i = 0; i < size; i ++) {
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }
            deque.addLast(i);
            if (i - deque.peekFirst() == k) {
                deque.pollFirst();
            }
            if (i + 1 < k) {
                continue;
            }
            res[i - k + 1] = nums[deque.peekFirst()];
        }
        return res;
    }

    /**
     * 862. 和至少为 K 的最短子数组
     * 返回 A 的最短的非空连续子数组的长度，该子数组的和至少为 K 。
     *
     * 如果没有和至少为 K 的非空子数组，返回 -1 。
     * @param nums
     * @param k
     * @return
     */
    public int shortestSubarray(int[] nums, int k) {
        int m = nums.length;
        int[] sums = new int[nums.length + 1];
        sums[0] = 0;
        for (int i = 0 ; i < m; i ++) {
            sums[i + 1] = sums[i] + nums[i];
        }
        Deque<Integer> deque = new LinkedList<>();
        deque.addLast(0);
        int cur = -1 ,ans = -1;
        for (int i = 1 ; i < sums.length; i ++) {
            while (!deque.isEmpty() && sums[i] - sums[deque.pollFirst()] >= k) {
                cur = deque.pollFirst();
            }

            if (cur != -1 && (ans == -1 || i - cur < ans)) {
                ans = i - cur;
            }
            while (!deque.isEmpty() && sums[deque.peekLast()] > sums[i]) {
                deque.pollLast();
            }
            deque.addLast(i);

        }
        return ans;
    }

    /**
     * 1438. 绝对差不超过限制的最长连续子数组
     * 给你一个整数数组 nums ，和一个表示限制的整数 limit，请你返回最长连续子数组的长度，
     * 该子数组中的任意两个元素之间的绝对差必须小于或者等于 limit 。
     *
     * 如果不存在满足条件的子数组，则返回 0 。
     * @param nums
     * @param limit
     * @return
     */
    public int longestSubarray(int[] nums, int limit) {
        return bs(nums,0,nums.length,limit);
    }

    /**
     * 二分查找一个区间段长度，其中最大值 - 最小值不超过limit，如果最大值和最小值间的差值不超过limit，则都可以
     * @return
     */
    public int bs(int[] nums, int l, int r,int limit) {
        if (l == r) {
            return l;
        }
        int mid = (l + r + 1) >> 1;
        if (check(nums,mid,limit)) {
            l = mid;
        } else {
            r = mid - 1;
        }
        return bs(nums,l,r,limit);
    }

    /**
     * 维护一个区间段最小值的堆，和一个区间段最大值的堆
     * @return
     */
    public boolean check(int[] nums,int k, int limit) {
        Deque<Integer> minq = new LinkedList<>();
        Deque<Integer> maxq = new LinkedList<>();
        for (int i = 0; i < nums.length; i ++) {
            while (!minq.isEmpty() && nums[minq.peekLast()] > nums[i]) {
                minq.pollLast();
            }
            while (!maxq.isEmpty() && nums[maxq.peekLast()] < nums[i]) {
                maxq.pollLast();
            }
            minq.addLast(i);
            maxq.addLast(i);
            if (i + 1 < k) {
                continue;
            }
            if (i - minq.peekFirst() == k) {
                minq.pollFirst();
            }
            if (i - maxq.peekFirst() == k) {
                maxq.pollFirst();
            }
            if (nums[maxq.peekFirst()] - nums[minq.peekFirst()] <= limit) {
                return true;
            }
        }
        return false;
    }

    /**45. 跳跃游戏 II
     给定一个非负整数数组，你最初位于数组的第一个位置。

     数组中的每个元素代表你在该位置可以跳跃的最大长度。

     你的目标是使用最少的跳跃次数到达数组的最后一个位置。

     假设你总是可以到达数组的最后一个位置。
     **/
    public int jump(int[] nums) {
        //使用BFS
        Queue<Integer> queue = new ArrayDeque<>();
        int m = nums.length;
        boolean[] check = new boolean[m];
        check[0] = true;//已经跳过的位置
        queue.offer(0);
        int ans = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size > 0) {
                int idx = queue.poll();
                if (idx == m - 1) {
                    return ans;
                }
                //能跳的区间，最近为idx + 1;最远是idx + nums[idx]
                for (int i = idx + 1; i <= idx + nums[idx] && i < m; i ++) {
                    if (!check[i]) {
                        queue.offer(i);
                        check[i] = true;
                    }
                }
                size --;
            }
            ans ++;
        }
        return ans;
    }

    public int jump1(int[] nums) {
        //贪心算法
        if (nums.length <= 1) {
            return 0;
        }
        int m = nums.length;
        int start = 0,end = nums[0];
        int ans = 0;
        while (end < m - 1) {
            int temp = 0;
            for (int i = start + 1; i <= end; i ++ ) {
                temp = Math.max(temp, nums[i] + i);
            }
            start = end + 1;
            end = temp;
            ans ++;
        }
        return ans;
    }

    /**
     * 93. 复原 IP 地址
     * 给定一个只包含数字的字符串，用以表示一个 IP 地址，返回所有可能从 s 获得的 有效 IP 地址 。你可以按任何顺序返回答案。
     *
     * 有效 IP 地址 正好由四个整数（每个整数位于 0 到 255 之间组成，且不能含有前导 0），整数之间用 '.' 分隔。
     *
     * 例如："0.1.2.201" 和 "192.168.1.1" 是 有效 IP 地址，但是 "0.011.255.245"、"192.168.1.312" 和 "192.168@1.1" 是 无效 IP 地址。
     *
     *
     * @param s
     * @return
     */
    public List<String> restoreIpAddresses(String s) {
        int len = s.length();
        List<String> res = new ArrayList<>();
        if (len < 4 || len > 12) {
            return res;
        }
        dfs(s,len,0,4, new LinkedList<>(), res);

        return res;
    }

    /**
     *
     * @param s 传入处理字符串
     * @param len 字符串长度
     * @param begin 开始位置
     * @param residue 剩余层数
     * @param path 遍历路径
     * @param res 结果
     */
    public void dfs(String s,int len,int begin, int residue, Deque<String> path, List<String> res) {
        if (begin == len) {
            if (residue == 0) {
                res.add(String.join(".",path));
            }
            return;
        }
        for (int i = begin; i < begin + 3; i ++) {
            if (i >= len) {
                break;
            }
            //剩余层数需要最大数字长度 小于剩余的长度，则不满足
            if ((residue - 1) * 3 < len - i - 1) {
                continue;
            }
            //如果符合ip规则，加入path，并对path回溯
            if (checkIp(s,begin,i)) {
                String cur = s.substring(begin, i + 1);
                path.offer(cur);
                dfs(s,len,i + 1, residue - 1,path,res);
                path.removeLast();
            }
        }
    }

    public boolean checkIp(String s ,int left, int right) {
        int len = right - left + 1;
        if (len > 1 && s.charAt(left) == '0') {
            return false;
        }
        int res = 0;
        while (left <= right) {
            res = res * 10 + s.charAt(left) - '0';
            left ++ ;
        }
        return res >= 0 && res <= 255;
    }

    /**
     * 46. 全排列
     * 给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。
     *
     * 示例 1：
     *
     * 输入：nums = [1,2,3]
     * 输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
     * @param nums
     * @return
     */

    public List<List<Integer>> permute(int[] nums) {
        int len = nums.length;
        List<List<Integer>> res = new ArrayList<>();
        if (len == 0) {
            return res;
        }
        Deque<Integer> path = new LinkedList<>();
        boolean[] used = new boolean[len];
        dfs(nums,len,0,path,used,res);
        return res;
    }

    /**
     * dfs树,每一层都是按数组分支
     * @param nums 排列数组
     * @param len 数组长度
     * @param dep 树深度
     * @param path 记录数据
     * @param used 是否被使用
     * @param res 结果
     */
    public void dfs (int[] nums,int len, int dep,Deque path,boolean[] used, List<List<Integer>> res) {
        //如果深度是数组长度,则获得一个排列数据
        if (dep == len) {
            res.add(new ArrayList<>(path));
            return;
        }
        //每一层都遍历所有数据,排除已经使用的
        for (int i = 0; i < len; i ++) {
            if (!used[i]) {
                path.addLast(nums[i]);
                used[i] = true;
                dfs(nums,len,dep + 1,path,used,res);
                path.removeLast();
                used[i] = false;
            }
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
     * 513. 找树左下角的值
     * 给定一个二叉树，在树的最后一行找到最左边的值。
     * @param root
     * @return
     */
    int maxk ;
    int ans ;
    public int findBottomLeftValue(TreeNode root) {
        maxk = -1;
        ans = 0;
        findBottomLeftValue(root,0);
        return ans;
    }

    public void findBottomLeftValue(TreeNode root,int k) {
        if (root == null) {
            return;
        }
        if (k > maxk) {
            maxk = k;
            ans = root.val;
        }
        findBottomLeftValue(root.left,k + 1);
        findBottomLeftValue(root.right,k + 1);
    }

    /**
     * 135. 分发糖果
     * 老师想给孩子们分发糖果，有 N 个孩子站成了一条直线，老师会根据每个孩子的表现，预先给他们评分。
     *
     * 你需要按照以下要求，帮助老师给这些孩子分发糖果：
     *
     * 每个孩子至少分配到 1 个糖果。
     * 评分更高的孩子必须比他两侧的邻位孩子获得更多的糖果。
     * 那么这样下来，老师至少需要准备多少颗糖果呢？
     * @param ratings
     * @return
     */
    public int candy(int[] ratings) {
        int len = ratings.length;
        //从左向右求出合理的糖果值
        int[] l = new int[len];
        int[] r = new int[len];
        for (int i = 0,j = 1; i < len; i ++) {
            if (i > 0 && ratings[i] > ratings[i - 1]) {
                j += 1;
            } else {
                j = 1;
            }
            l [i] = j;
        }
        //从右向左求出合理的糖果值
        for (int i = len - 1,j = 1; i >= 0; i --) {
            if (i < len - 1 && ratings[i] > ratings[i + 1]) {
                j += 1;
            } else {
                j = 1;
            }
            r [i] = j;
        }
        int ans = 0;
        //取每个位置最大的糖果值求出所需糖果
        for (int i = 0; i < len ; i ++) {
            ans = Math.max(l[i],r[i]) + ans;
        }
        return ans;
    }

    /**
     * 365. 水壶问题
     * 有两个容量分别为 x升 和 y升 的水壶以及无限多的水。请判断能否通过使用这两个水壶，从而可以得到恰好 z升 的水？
     *
     * 如果可以，最后请用以上水壶中的一或两个来盛放取得的 z升 水。
     *
     * 你允许：
     *
     * 装满任意一个水壶
     * 清空任意一个水壶
     * 从一个水壶向另外一个水壶倒水，直到装满或者倒空
     * @param jug1Capacity
     * @param jug2Capacity
     * @param targetCapacity
     * @return
     */
    public boolean canMeasureWater(int jug1Capacity, int jug2Capacity, int targetCapacity) {
        //对于每个水壶来说，只有四种情况 ：加上x升，加上y升，减去x升，减去y升
        int x = jug1Capacity,y = jug2Capacity,z = targetCapacity;
        if (x == z || y == z || z == 0) {
            return true;
        }
        if (x + y < z || x ==y && x + y != z) {
            return false;
        }
        Deque<Integer> deque = new LinkedList<>();
        Set<Integer> set = new HashSet<>();
        deque.offer(0);
        //循环4种情况，直到找到正确的水
        while (!deque.isEmpty()) {
            int tmp = deque.poll() ;
            if (tmp + x <= x + y && set.add(tmp + x)) {
                deque.offer(tmp + x);
            }
            if (tmp + y <= x + y && set.add(tmp + y)) {
                deque.offer(tmp + y);
            }
            if (tmp - x >= 0 && set.add(tmp - x)) {
                deque.offer(tmp - x);
            }
            if (tmp - y >= 0 && set.add(tmp - y)) {
                deque.offer(tmp - y);
            }
            if (set.contains(z)) {
                return true;
            }
        }
        return false;

    }

    /**
     * 1760. 袋子里最少数目的球
     * 给你一个整数数组 nums ，其中 nums[i] 表示第 i 个袋子里球的数目。同时给你一个整数 maxOperations 。
     *
     * 你可以进行如下操作至多 maxOperations 次：
     *
     * 选择任意一个袋子，并将袋子里的球分到 2 个新的袋子中，每个袋子里都有 正整数 个球。
     * 比方说，一个袋子里有 5 个球，你可以把它们分到两个新袋子里，分别有 1 个和 4 个球，或者分别有 2 个和 3 个球。
     * 你的开销是单个袋子里球数目的 最大值 ，你想要 最小化 开销。
     *
     * 请你返回进行上述操作后的最小开销。
     * @param nums
     * @param maxOperations
     * @return
     */
    public int minimumSize(int[] nums, int maxOperations) {
        //采用二分方式
        int low = 1,hight = 1000000001;
        while (low + 1 < hight) {
            int mid = low + (hight - low) / 2;
            if (checkq(nums,maxOperations,mid)) {
                hight = mid;
            } else {
                low = mid;
            }
        }

        if (checkq(nums,maxOperations,low)) {
            return low;
        }
        return hight;
    }

    public boolean checkq(int[] nums,int maxOperations,int m) {
        int res = 0;
        for (int num : nums) {
            //拆成最小开销，将一个数除以m取整，如果能整除，则次数减一
            res += num / m;
            if (num % m == 0) {
                res --;
            }
            if (res > maxOperations) {
                return false;
            }
        }
        return true;
    }

    /**
     * 43. 字符串相乘
     * 给定两个以字符串形式表示的非负整数 num1 和 num2，返回 num1 和 num2 的乘积，它们的乘积也表示为字符串形式。
     *
     * 示例 1:
     *
     * 输入: num1 = "2", num2 = "3"
     * 输出: "6"
     * @param num1
     * @param num2
     * @return
     */
    public String multiply(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) {
            return "0";
        }
        int[] a = new int[num1.length()];
        int[] b = new int[num2.length()];
        int[] c = new int[num1.length() + num2.length()];
        //数字倒排
        for (int i = 0; i < num1.length(); i++) {
            a[i] = (num1.charAt(num1.length() - i - 1) - '0');
        }
        for (int i = 0; i < num2.length(); i++) {
            b[i] = (num2.charAt(num2.length() - i - 1) - '0');
        }
        //每一位相乘后与对应位置相加
        for (int i = 0 ; i < num1.length();i ++) {
            for (int j = 0; j < num2.length(); j ++) {
                c[i + j] += a[i] * b[j];
            }
        }
        //计算每一位的值，超过10向上进位
        for (int i = 0; i < c.length; i ++) {
            if (c[i] < 10) {
                continue;
            }
            if (i + 1 == c.length) {
                break;
            }
            c[i + 1] += c[i] / 10;
            c[i] = c[i] % 10;
        }
        StringBuilder res = new StringBuilder();
        boolean flag = false;
        //倒叙拼接
        for (int i = c.length - 1; i >= 0; i --) {
            if (c[i] > 0 ) {
                flag = true;
            }
            if (flag) {
                res.append(c[i] + "");
            }
        }
        return res.toString();
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        int[] nums = new int[]{4,2,2,2,4,4,2,2};
//
//        System.out.println(solution.longestSubarray(nums,0));
//        solution.restoreIpAddresses("25525511135");
//        solution.multiply("12","12");
        solution.canMeasureWater(3,5,4);
    }

}

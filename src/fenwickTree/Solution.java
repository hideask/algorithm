package fenwickTree;

import java.awt.font.NumericShaper;
import java.util.*;

public class Solution {

    /**
     * 1109. 航班预订统计
     * 这里有 n 个航班，它们分别从 1 到 n 进行编号。
     *
     * 有一份航班预订表 bookings ，表中第 i 条预订记录 bookings[i] = [firsti, lasti, seatsi] 意味着在从 firsti 到 lasti （包含 firsti 和 lasti ）的 每个航班 上预订了 seatsi 个座位。
     *
     * 请你返回一个长度为 n 的数组 answer，里面的元素是每个航班预定的座位总数。
     * @param bookings
     * @param n
     * @return
     */
    public int[] corpFlightBookings(int[][] bookings, int n) {
        // s : 1   3   4   10  15  21   前缀和数组
        // a : 1   2   3   4   5   6    原数组
        // x : 1   1   1   1   1   1    差分数组
        // 前缀和的差分数组就是原数组，如果对原数组的一个区间段中的每个元素都加上一个值，则相当于在差分数组
        // 左边界加上一个值，和在右边界 + 1位置减去一个值，中间位置不变；加完后再对差分数组求前缀和，获得原数组加完后的数组
        // 这里使用树状数组求前缀和
        int[] a = new int[n];
        FenwickTree fenwickTree = new FenwickTree(n);
        for (int[] books : bookings) {
            fenwickTree.add(books[0], books[2]);
            fenwickTree.add(books[1] + 1, - books[2]);
        }

        for (int i = 1; i <= n ; i ++) {
            a[i - 1] = fenwickTree.query(i);
        }
        return a;
    }

    public int[] corpFlightBookings1(int[][] bookings, int n) {
        int[] ret = new int[n];
        //求差分数组的区间加值
        for (int[] books : bookings) {
            ret[books[0] - 1] += books[2];
            if (books[1] < n) {
                ret[books[1]] -= books[2];
            }

        }

        //求差分数组的前缀和
        for (int i = 1;i < n; i ++) {
            ret[i] += ret[i - 1];
        }
        return ret;
    }

    /**
     * 1310. 子数组异或查询
     * 有一个正整数数组 arr，现给你一个对应的查询数组 queries，其中 queries[i] = [Li, Ri]。
     *
     * 对于每个查询 i，请你计算从 Li 到 Ri 的 XOR 值（即 arr[Li] xor arr[Li+1] xor ... xor arr[Ri]）作为本次查询的结果。
     *
     * 并返回一个包含给定查询 queries 所有结果的数组。
     * @param arr
     * @param queries
     * @return
     */
    public int[] xorQueries(int[] arr, int[][] queries) {
        //把异或运算当成前缀和
        //异或性质： a ^ b = c , a ^ c = b , b ^ c = a
        //把异或运算当成减法
        //也可以用树状数组
        int n = arr.length;
        for (int i = 1; i < n ;i ++) {
            arr[i] ^= arr[i - 1];
        }
        int[] res = new int[queries.length];
        for (int i = 0; i < queries.length; i ++) {
            res[i] = arr[queries[i][1]] ^ (queries[i][0] > 0 ? arr[queries[i][0] - 1]: 0);
        }
        return res;
    }

    /**
     * 1409. 查询带键的排列
     * 给你一个待查数组 queries ，数组中的元素为 1 到 m 之间的正整数。 请你根据以下规则处理所有待查项 queries[i]（从 i=0 到 i=queries.length-1）：
     *
     * 一开始，排列 P=[1,2,3,...,m]。
     * 对于当前的 i ，请你找出待查项 queries[i] 在排列 P 中的位置（下标从 0 开始），然后将其从原位置移动到排列 P 的起始位置（即下标为 0 处）。注意， queries[i] 在 P 中的位置就是 queries[i] 的查询结果。
     * 请你以数组形式返回待查数组  queries 的查询结果。
     * @param queries
     * @param m
     * @return
     */
    public int[] processQueries(int[] queries, int m) {
        //计数数组实现
        //cnt数组    _ _ _ _ _ _ _ 1 2 3 4 5
        //          0 0 0 0 0 0 0 1 1 1 1 1
        //将 1 到 m 的值放到 计数数组的后面，每个值的数量计数为 1，前面预留足够的跳转空间，
        //当前值的前缀和就代表他在第几位，我们需要维护计数数组的下标信息，值与计数数组下标的对应
        int n = queries.length;
        int[] pos = new int[m + 1];//记录原始下标
        FenwickTree tree = new FenwickTree(m + n);
        //初始化计数数组，
        for (int i = 1 ; i <= m ; i ++) {
            tree.add(n + i , 1);
            pos[i] = n + i ;
        }
        int[] res = new int[n];
        for (int i = 0 ;i < n ;i ++) {
            //获取当前的坐标
            int idx = pos[queries[i]];
            res[i] = tree.query(idx - 1);
            //将当前坐标的数字减 1
            tree.add(idx, -1);
            pos[queries[i]] = n - i;
            //将数据放到前面去
            tree.add(n - i, 1);
        }
        return res;
    }

    class TreeNode {
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
     * 114. 二叉树展开为链表
     * 给你二叉树的根结点 root ，请你将它展开为一个单链表：
     *
     * 展开后的单链表应该同样使用 TreeNode ，其中 right 子指针指向链表中下一个结点，而左子指针始终为 null 。
     * 展开后的单链表应该与二叉树 先序遍历 顺序相同。
     * @param root
     */
    public void flatten(TreeNode root) {
        //先序遍历
        List<Integer> list = new ArrayList<>();
        preOrder(root,list);
        TreeNode pre = root;
        for (int i = 1; i < list.size();i ++) {
            TreeNode node = new TreeNode(list.get(i));
            pre.left = null;
            pre.right = node;
            pre = pre.right;
        }
    }


    public void preOrder(TreeNode root , List<Integer> list) {
        if (root == null) {
            return;
        }
        list.add(root.val);
        preOrder(root.left,list);
        preOrder(root.right,list);
    }

    public void flatten1(TreeNode root) {
        //先序遍历
        List<TreeNode> list = new ArrayList<>();
        preOrder1(root,list);
        for (int i = 1; i < list.size();i ++) {
            TreeNode pre = list.get(i - 1), cur = list.get(i);
            pre.left = null;
            pre.right = cur;
        }
    }


    public void preOrder1(TreeNode root , List<TreeNode> list) {
        if (root == null) {
            return;
        }
        list.add(root);
        preOrder1(root.left,list);
        preOrder1(root.right,list);
    }

    TreeNode last;
    public void flatten2(TreeNode root) {
        //空间复杂度为o(1)的
        //右左根，先遍历到最右边，last就是最右边的那个值，再遍历到右边的最左边
        //将最右边接到左边的右子树，左子树再接到根节点，以此类推直到root
        if (root == null) {
            return;
        }
        flatten2(root.right);
        flatten2(root.left);
        if (last != null) {
            root.left = null;
            root.right = last;
        }
        last = root;
    }

    /**
     * 1829. 每个查询的最大异或值
     * 给你一个 有序 数组 nums ，它由 n 个非负整数组成，同时给你一个整数 maximumBit 。你需要执行以下查询 n 次：
     *
     * 找到一个非负整数 k < 2maximumBit ，使得 nums[0] XOR nums[1] XOR ... XOR nums[nums.length-1] XOR k 的结果 最大化 。k 是第 i 个查询的答案。
     * 从当前数组 nums 删除 最后 一个元素。
     * 请你返回一个数组 answer ，其中 answer[i]是第 i 个查询的结果。
     * @param nums
     * @param maximumBit
     * @return
     */
    public int[] getMaximumXor(int[] nums, int maximumBit) {
        int n = nums.length;
        //max 为二进制位全是1的值
        int max = (1 << maximumBit) - 1;
        //异或运算中，每个数字交换位置，等式不变
        int[] ans = new int[n];
        int tmp = 0;
        for (int i = 0, j = n - 1; i < n ; i ++, j --) {
            tmp ^= nums[i];
            //异或出的值肯定比max小
            ans[j] = tmp ^ max;
        }
        return ans;
    }

    /**
     * 724. 寻找数组的中心下标
     * 给你一个整数数组 nums ，请计算数组的 中心下标 。
     *
     * 数组 中心下标 是数组的一个下标，其左侧所有元素相加的和等于右侧所有元素相加的和。
     *
     * 如果中心下标位于数组最左端，那么左侧数之和视为 0 ，因为在下标的左侧不存在元素。这一点对于中心下标位于数组最右端同样适用。
     *
     * 如果数组有多个中心下标，应该返回 最靠近左边 的那一个。如果数组不存在中心下标，返回 -1 。
     * @param nums
     */
    public int pivotIndex(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return -1;
        }
        if (n == 1) {
            return 0;
        }
        for (int i = 1 ;i < n ; i ++) {
            nums[i] += nums[i - 1];
        }
        int sum = nums[n - 1];
        if (sum - nums[0] == 0) {
            return 0;
        }
        for (int i = 1; i < n ; i ++) {
            int l = nums[i - 1];
            int r = sum - nums[i];
            if (l == r) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 1649. 通过指令创建有序数组
     * 给你一个整数数组 instructions ，你需要根据 instructions 中的元素创建一个有序数组。一开始你有一个空的数组 nums ，
     * 你需要 从左到右 遍历 instructions 中的元素，将它们依次插入 nums 数组中。每一次插入操作的 代价 是以下两者的 较小值 ：
     *
     * nums 中 严格小于  instructions[i] 的数字数目。
     * nums 中 严格大于  instructions[i] 的数字数目。
     * 比方说，如果要将 3 插入到 nums = [1,2,3,5] ，那么插入操作的 代价 为 min(2, 1) (元素 1 和  2 小于 3 ，元素 5 大于 3 ），
     * 插入后 nums 变成 [1,2,3,3,5] 。
     *
     * 请你返回将 instructions 中所有元素依次插入 nums 后的 总最小代价 。由于答案会很大，请将它对 109 + 7 取余 后返回。
     *
     * @param instructions
     * @return
     */
    public int createSortedArray(int[] instructions) {
        //每次插入的过程中，取求比他大的值个数，和比他小的值的个数，相当于一个求动态前缀和和区间和个数的过程
        //所以我们就使用树状数组
        int max = Arrays.stream(instructions).max().getAsInt();
        FenwickTree fenwickTree = new FenwickTree(max);
        long ans = 0;
        long mod = (long) (1e9 + 7);
        for (int i = 0; i < instructions.length; i ++) {
            fenwickTree.add(instructions[i] , 1);
            ans += Math.min(fenwickTree.query(instructions[i] - 1), fenwickTree.sum(max, instructions[i] + 1));
        }
        return (int) (ans % mod);
    }

    /**
     * 1838. 最高频元素的频数
     * 元素的 频数 是该元素在一个数组中出现的次数。
     *
     * 给你一个整数数组 nums 和一个整数 k 。在一步操作中，你可以选择 nums 的一个下标，并将该下标对应元素的值增加 1 。
     *
     * 执行最多 k 次操作后，返回数组中最高频元素的 最大可能频数 。
     * 示例 1：
     *
     * 输入：nums = [1,2,4], k = 5
     * 输出：3
     * 解释：对第一个元素执行 3 次递增操作，对第二个元素执 2 次递增操作，此时 nums = [4,4,4] 。
     * 4 是数组中最高频元素，频数是 3 。
     * 示例 2：
     *
     * 输入：nums = [1,4,8,13], k = 5
     * 输出：2
     * 解释：存在多种最优解决方案：
     * - 对第一个元素执行 3 次递增操作，此时 nums = [4,4,8,13] 。4 是数组中最高频元素，频数是 2 。
     * - 对第二个元素执行 4 次递增操作，此时 nums = [1,8,8,13] 。8 是数组中最高频元素，频数是 2 。
     * - 对第三个元素执行 5 次递增操作，此时 nums = [1,4,13,13] 。13 是数组中最高频元素，频数是 2 。
     * 示例 3：
     *
     * 输入：nums = [3,9,6], k = 2
     * 输出：1
     * @param nums
     * @param k
     * @return
     */
    public int maxFrequency(int[] nums, int k) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        //滑动窗口，排序是使窗口内的元素大小差异可控
        Arrays.sort(nums);
        //确保滑窗大小有两个
        long[] sum = new long[n + 1];
        sum[0] = nums[0];
        for (int i = 0; i < n ; i ++) {
            sum[i + 1] = sum[i] + nums[i];
        }
        int l = 0, r = 2;
        int ans = 0;
        while (r <= n) {
            if ((sum[r] - sum[l] + k) >= (nums[r - 1] * (r - l)) ) {
                ans = Math.max(r - l, ans);
                r ++;
            } else {
                while ((sum[r] - sum[l] + k) < (nums[r - 1] * (r - l))) {
                    l ++;
                }
            }
        }
        return ans;
    }

    /**
     * 525. 连续数组
     * 给定一个二进制数组 nums , 找到含有相同数量的 0 和 1 的最长连续子数组，并返回该子数组的长度。
     * @param nums
     * @return
     */
    public int findMaxLength(int[] nums) {
        //我们将0的值换成-1，求含有相同数量的 0 和 1 的最长连续子数组，就是求这段区间范围内
        //区间和为0的最长连续子数组
        int n = nums.length;
        int sum = 0;
        Map<Integer,Integer> map = new HashMap<>();
        //初始化0值
        map.put(0, -1);
        int res = 0;
        //在求前缀和的过程中，如果当前前缀和的值在前面出现过，说明当前位置到第一次出现的位置之间
        //的区间和的值为0，这段区间就是所求区间
        for (int i = 0; i < n; i ++ ) {
            sum += (nums[i] == 0 ? -1 : nums[i]);
            if (map.containsKey(sum)) {
                int j = map.get(sum);
                res = Math.max(res, i - j);
            } else {
                map.put(sum, i);
            }
        }
        return res;
    }

    /**
     * 670. 最大交换
     * 给定一个非负整数，你至多可以交换一次数字中的任意两位。返回你能得到的最大值。
     * @param num
     * @return
     */
    public int maximumSwap(int num) {
        char[] nums = String.valueOf(num).toCharArray();
        int n = nums.length;
        int max_ind = -1;
        for (int i = 0 ; i < n - 1 ; i ++) {
            if (nums[i + 1] <= nums[i]) {
                continue;
            }
            max_ind = i + 1;
        }
        if (max_ind == -1) {
            return num;
        }
        for (int i = max_ind ; i < n - 1; i ++) {
            if (nums[max_ind] > nums[i + 1]) {
                continue;
            }
            max_ind = i + 1;
        }

        for (int i = 0 ; i < n ;i ++) {
            if (nums[i] >= nums[max_ind]) {
                continue;
            }
            char tmp = nums[i];
            nums[i] = nums[max_ind];
            nums[max_ind] = tmp;
            break;
        }

        return Integer.parseInt(new String(nums));
    }

    /**
     * 637. 二叉树的层平均值
     * 给定一个非空二叉树, 返回一个由每层节点平均值组成的数组。
     * @param root
     * @return
     */
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> list = new ArrayList<>();
        if (root == null) {
            return list;
        }

        Deque<TreeNode> deque = new LinkedList<>();
        deque.offer(root);
        while (!deque.isEmpty()) {
            int size = deque.size();
            double sum = 0;
            for (int i = 0; i < size; i ++) {
                TreeNode tmp = deque.poll();
                sum += tmp.val;
                if (tmp.left != null) {
                    deque.offer(tmp.left);
                }
                if (tmp.right != null) {
                    deque.offer(tmp.right);
                }
            }
            list.add(sum / size);
        }
        return list;
    }

    public static void main(String[] args) {
        System.out.println(~5 ^ (~ -5));
    }

}

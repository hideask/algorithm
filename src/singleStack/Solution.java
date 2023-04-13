package singleStack;

import javax.jws.Oneway;
import java.lang.reflect.WildcardType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Solution {

    /**
     * 496. 下一个更大元素 I
     * 给你两个 没有重复元素 的数组 nums1 和 nums2 ，其中nums1 是 nums2 的子集。
     *
     * 请你找出 nums1 中每个元素在 nums2 中的下一个比其大的值。
     *
     * nums1 中数字 x 的下一个更大元素是指 x 在 nums2 中对应位置的右边的第一个比 x 大的元素。如果不存在，对应位置输出 -1 。
     *
     * 示例 1:
     *
     * 输入: nums1 = [4,1,2], nums2 = [1,3,4,2].
     * 输出: [-1,3,-1]
     * 解释:
     *     对于 num1 中的数字 4 ，你无法在第二个数组中找到下一个更大的数字，因此输出 -1 。
     *     对于 num1 中的数字 1 ，第二个数组中数字1右边的下一个较大数字是 3 。
     *     对于 num1 中的数字 2 ，第二个数组中没有下一个更大的数字，因此输出 -1
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/next-greater-element-i
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int len1 = nums1.length;
        int len2 = nums2.length;
        Map<Integer,Integer> map = new HashMap<>();
        Stack<Integer> stack = new Stack<>();//单调栈
        for (int i = 0; i < len2; i++) {
            while (!stack.isEmpty() && nums2[i] > stack.peek()) {
                map.put(stack.pop(), nums2[i]);
            }
            stack.push(nums2[i]);
        }
        int[] res = new int[len1];
        for (int i = 0;i < len1; i ++) {
            res[i] = map.getOrDefault(nums1[i], -1);
        }
        return res;
    }

    /**
     * 503. 下一个更大元素 II
     * 给定一个循环数组（最后一个元素的下一个元素是数组的第一个元素），输出每个元素的下一个更大元素。
     * 数字 x 的下一个更大的元素是按数组遍历顺序，这个数字之后的第一个比它更大的数，这意味着你应该循环地搜索它的下一个更大的数。如果不存在，则输出 -1。
     *
     * 示例 1:
     *
     * 输入: [1,2,1]
     * 输出: [2,-1,2]
     * 解释: 第一个 1 的下一个更大的数是 2；
     * 数字 2 找不到下一个更大的数；
     * 第二个 1 的下一个最大的数需要循环搜索，结果也是 2。
     * @param nums
     * @return
     */
    public int[] nextGreaterElements(int[] nums) {
        int len = nums.length;
        Stack<Integer> stack = new Stack<>();
        int[] res = new int[len];

        for (int i = 0; i < len; i ++) {
            res[i] = -1;
        }
        for (int i = 0; i < len; i ++) {
            while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) {
                res[stack.pop()] = nums[i];
            }
            stack.push(i);
        }
        //循环
        for (int i = 0; i < len; i ++) {
            while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) {
                res[stack.pop()] = nums[i];
            }
            stack.push(i);
        }
        return res;
    }

    /**
     * 739. 每日温度
     * 请根据每日 气温 列表 temperatures ，请计算在每一天需要等几天才会有更高的温度。如果气温在这之后都不会升高，请在该位置用 0 来代替。
     *
     * 示例 1:
     *
     * 输入: temperatures = [73,74,75,71,69,72,76,73]
     * 输出: [1,1,4,2,1,1,0,0]
     * @param temperatures
     * @return
     */
    public int[] dailyTemperatures(int[] temperatures) {
        int len = temperatures.length;
        int[] res = new int[len];
        Arrays.fill(res, 0);
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < len; i ++) {
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                int k = stack.pop();
                res[k] = i - k;
            }
            stack.push(i);
        }
        return res;
    }

    public int[] dailyTemperatures1(int[] temperatures) {
        int len = temperatures.length;
        int[] res = new int[len];
        //倒叙排列
        for (int i = len - 2; i >= 0; i --) {
            //跳过已经比较过的比j小的区间
            for (int j = i + 1; j < len ; j += res[j]) {
                //如果j的值大于i的值，则下标相差就是天数
                if (temperatures[j] > temperatures[i]) {
                    res[i] = j - i;
                    break;
                } else if (res[j] == 0) {//如果i的值大于j的值，并且res[j]等于0，说明后面已经没有比i大的值
                    res[i] = 0;
                    break;
                }
            }
        }
        return res;
    }

    /**
     * 84. 柱状图中最大的矩形
     * 给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。
     *
     * 求在该柱状图中，能够勾勒出来的矩形的最大面积。
     * @param heights
     * @return
     */
    public int largestRectangleArea(int[] heights) {
        int len = heights.length;
        Stack<Integer> stack = new Stack<>();
        //定义两个数组分别表示当前元素左边第一个比他小的值，和右边第一个比他小的值
        int [] l = new int[len],r = new int[len];
        //默认左右下标最值前后一个
        Arrays.fill(l, -1);
        Arrays.fill(r,len);
        for (int i = 0; i < len; i ++) {
            //使用小于等于可以这样理解，例如：2，3，3，3，3，2；虽然中间的3求前后第一个小于他的值有误差，
            //但是最后一个3前后最小值都是2，保证了答案的正确性
            while (!stack.isEmpty() && heights[i] <= heights[stack.peek()]) {
                r[stack.peek()] = i;//栈顶元素右边第一个比他小的元素为i
                stack.pop();
            }
            if (!stack.isEmpty()) {
                l[i] = stack.peek();//i左边第一个比他小的为栈顶元素
            }
            stack.push(i);
        }
        int res = 0;
        for (int i = 0;i < len; i ++) {
            res = Math.max(res,heights[i] * (r[i] - l[i] - 1));
        }
        return res;
    }

    /**
     * 1856. 子数组最小乘积的最大值
     * 一个数组的 最小乘积 定义为这个数组中 最小值 乘以 数组的 和 。
     *
     * 比方说，数组 [3,2,5] （最小值是 2）的最小乘积为 2 * (3+2+5) = 2 * 10 = 20 。
     * 给你一个正整数数组 nums ，请你返回 nums 任意 非空子数组 的最小乘积 的 最大值 。
     * 由于答案可能很大，请你返回答案对  109 + 7 取余 的结果。
     *
     * 请注意，最小乘积的最大值考虑的是取余操作 之前 的结果。题目保证最小乘积的最大值在 不取余 的情况下可以用 64 位有符号整数 保存。
     *
     * 子数组 定义为一个数组的 连续 部分。
     * @param nums
     * @return
     */
    public int maxSumMinProduct(int[] nums) {
        int len = nums.length;
        Stack<Integer> stack = new Stack<>();
        int[] l = new int[len],r = new int[len];
        Arrays.fill(l,-1);
        Arrays.fill(r,len );
        for (int i = 0; i < len; i ++) {
            while (!stack.isEmpty() && nums[i] <= nums[stack.peek()]) {
                r[stack.peek()] = i;
                stack.pop();
            }
            if (!stack.isEmpty()) {
                l[i] = stack.peek();
            }
            stack.push(i);
        }
        long[] sum = new long[len + 1];
        sum[0] = 0l;
        for (int i = 0; i < len; i ++) {
            sum[i + 1] = nums[i] + sum[i];
        }
        long res = 0l;

        for (int i = 0; i < len ; i ++) {
            res = Math.max(res , nums[i] * (sum[r[i]] - sum[l[i] + 1]) );
        }

        return (int) (res % 1000000007);
    }

    /**
     * 456. 132 模式
     * 给你一个整数数组 nums ，数组中共有 n 个整数。132 模式的子序列
     * 由三个整数 nums[i]、nums[j] 和 nums[k] 组成，并同时满足：i < j < k 和 nums[i] < nums[k] < nums[j] 。
     *
     * 如果 nums 中存在 132 模式的子序列 ，返回 true ；否则，返回 false 。
     * @param nums
     * @return
     */
    public boolean find132pattern(int[] nums) {
        int len = nums.length;
        int m = Integer.MIN_VALUE;
        //将数组从后往前找，如果要满足132模式，则当前值要大于栈顶的值，因为是单向查找，所以判断下一个当前值是不是比
        //栈顶的值小就可以了
        Stack<Integer> stack = new Stack<>();
        for (int i = len - 1; i >=0; i --) {
            if (nums[i] < m) {
                return true;
            }
            while (!stack.isEmpty() && nums[i] > stack.peek()) {
                m = Math.max(m,stack.pop());
            }
            stack.push(nums[i]);
        }
        return false;
    }

    /**
     * 907. 子数组的最小值之和
     * 给定一个整数数组 arr，找到 min(b) 的总和，其中 b 的范围为 arr 的每个（连续）子数组。
     *
     * 由于答案可能很大，因此 返回答案模 10^9 + 7 。
     * @param arr
     * @return
     */
    public int sumSubarrayMins(int[] arr) {
        //该问题可以转换为固定末尾的RMQ（x,i）的和的问题，就是求解RMQ每个关键点所贡献的和值
        int len = arr.length;
        //维护一个单调递增栈
        Stack<Integer> stack = new Stack<>();

        long[] sum = new long[len + 1];
        sum[0] = 0;
        long ans = 0;
        for (int i = 0;i < len; i ++) {
            while (!stack.isEmpty() && arr[i] <= arr[stack.peek()]) {
                stack.pop();
            }
            //当前区间的起始下标
            int idx = stack.isEmpty()? -1 : stack.peek();
            stack.push(i);

            sum[stack.size()] = sum[stack.size() - 1] + arr[i] * (i - idx);

            //由于是单调递增栈，所以和值是所有之前子数组最小值之和的总和加上当前的总和，
            //因为比他小的值同样做了贡献
            ans += sum[stack.size()];
        }
        return (int) (ans % 1000000007);
    }

    class Data {
        int val;
        int count;
        public Data (int val,int count) {
            this.val = val;
            this.count = count;
        }
    }

    public int sumSubarrayMins2(int[] arr) {
        int len = arr.length;
        Stack<Data> stack = new Stack<>();

        long res = 0;
        long tmep = 0;
        for (int i = 0;i < len; i ++) {
            int count = 1;//本身贡献一次最小值
            while (!stack.isEmpty() && arr[i] <= stack.peek().val) {
                Data data = stack.pop();
                //贡献了比他大的值的最小值次数
                count += data.count;
                tmep -= data.val * data.count;
            }
            stack.push(new Data(arr[i],count));
            tmep += arr[i] * count;
            res += tmep;
        }
        return (int) (res % 1000000007);
    }



    /**
     * 42. 接雨水
     * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
     * @param height
     * @return
     */
    public int trap(int[] height) {
        int len = height.length;
        Stack<Integer> stack = new Stack<>();
        int res = 0;
        //使用单调递减栈，一层一层计算容量
        for (int i = 0; i < len ;i ++) {
            while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                int cur = stack.pop();
                if (stack.isEmpty()){
                    continue;
                }
                int a = height[i] - height[cur];
                int b = height[stack.peek()] - height[cur];
                res += Math.min(a,b) * (i - stack.peek() - 1);
            }
            stack.push(i);
        }
        return res;
    }

    /**
     * 1673. 找出最具竞争力的子序列
     * 给你一个整数数组 nums 和一个正整数 k ，返回长度为 k 且最具 竞争力 的 nums 子序列。
     *
     * 数组的子序列是从数组中删除一些元素（可能不删除元素）得到的序列。
     *
     * 在子序列 a 和子序列 b 第一个不相同的位置上，如果 a 中的数字小于 b 中对应的数字，那么我们称子序列 a 比子序列 b（相同长度下）
     * 更具 竞争力 。 例如，[1,3,4] 比 [1,3,5] 更具竞争力，在第一个不相同的位置，也就是最后一个位置上， 4 小于 5 。
     * @param nums
     * @param k
     * @return
     */
    public int[] mostCompetitive(int[] nums, int k) {
        int len = nums.length;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0 ; i < len; i ++) {
            while (!stack.isEmpty() && nums[i] < stack.peek() && k - stack.size() < len - i) {
                stack.pop();
            }
            stack.push(nums[i]);
        }
        int[] res = new int[k];
        int size = stack.size();
        for (int j = 0; j < size; j ++) {
            if (stack.size() > k) {
                stack.pop();
            } else {
                res[stack.size() - 1] = stack.pop();
            }
        }
        return  res;
    }

    /**
     * 66. 加一
     * 给定一个由 整数 组成的 非空 数组所表示的非负整数，在该数的基础上加一。
     *
     * 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
     *
     * 你可以假设除了整数 0 之外，这个整数不会以零开头。
     * @param digits
     * @return
     */
    public int[] plusOne(int[] digits) {
        int len = digits.length;
        if (len == 1 && digits[0] == 0) {
            return new int[]{1};
        }
        int[] res = new int[len + 1];
        int jw = 0;
        for (int i = len - 1; i >= 0 ; i --) {
            if (i == len - 1) {
                jw = 1;
            }
            int num = digits[i];
            num += jw;
            if (num < 10) {
                res[i + 1] = num;
                jw = 0;
            } else {
                jw = 1;
                res[i + 1] = num % 10;
                if (i == 0) {
                    res[0] = 1;
                }
            }
        }
        int len1 = res.length;
        if (res[0] == 0) {
            int [] res1 = new int[len];
            for (int i = 1 ; i < len1 ; i ++) {
                res1[i - 1] = res[i];
            }
            return res1;
        } else {
            return res;
        }
    }

    public int[] plusOne1(int[] digits) {
        int len = digits.length;
        for (int i = len - 1; i >= 0; i --) {
            if (digits[i] != 9) {
                digits[i] ++;
                return digits;
            }
            digits[i] = 0;
        }
        digits = new int[len + 1];
        digits[0] = 1;
        return digits;
    }

    /**
     * 268. 丢失的数字
     * 给定一个包含 [0, n] 中 n 个数的数组 nums ，找出 [0, n] 这个范围内没有出现在数组中的那个数。
     * @param nums
     * @return
     */
    public int missingNumber(int[] nums) {
        int len = nums.length;
        boolean[] check = new boolean[len + 1];
        for (int i = 0; i < len ; i ++) {
            check[nums[i]] = true;
        }
        int res = 0;
        for (int i = 0 ; i < check.length; i ++) {
            if (!check[i]) {
                res = i;
                break;
            }
        }
        return res;
    }

    /**
     * 面试题 17.12. BiNode
     * 二叉树数据结构TreeNode可用来表示单向链表（其中left置空，right为下一个链表节点）。
     * 实现一个方法，把二叉搜索树转换为单向链表，要求依然符合二叉搜索树的性质，转换操作应是原址的，也就是在原始的二叉搜索树上直接修改。
     *
     * 返回转换后的单向链表的头节点。
     * @param root
     * @return
     */
    TreeNode hair = new TreeNode(-1),p = hair;
    public TreeNode convertBiNode(TreeNode root) {
        if (root == null) {
            return null;
        }
        dfsBi(root);
        return hair.right;
    }

    public void dfsBi(TreeNode root) {
        if (root == null) {
            return;
        }
        dfsBi(root.left);
        p.right = root;
        p = root;
        root.left = null;
        dfsBi(root.right);
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
     * 328. 奇偶链表
     * 给定一个单链表，把所有的奇数节点和偶数节点分别排在一起。
     * 请注意，这里的奇数节点和偶数节点指的是节点编号的奇偶性，而不是节点的值的奇偶性。
     *
     * 请尝试使用原地算法完成。你的算法的空间复杂度应为 O(1)，时间复杂度应为 O(nodes)，nodes 为节点总数
     *
     * 示例 1:
     *
     * 输入: 1->2->3->4->5->NULL
     * 输出: 1->3->5->2->4->NULL
     * @param head
     * @return
     */
    public ListNode oddEvenList(ListNode head) {
        if (head == null) {
            return null;
        }

        if (head.next == null) {
            return head;
        }

        ListNode p = null, q = null, qhead = head.next, cur = head;

        int i = 1;
        while (cur != null) {
            if (i % 2 != 0) {
                if (p == null) {
                    p = cur;
                } else {
                    p.next = cur;
                    p = p.next;
                }
            }
            if (i % 2 == 0) {
                if (q == null) {
                    q = cur;
                } else {
                    q.next = cur;
                    q = q.next;
                }
            }
            i ++;
            cur = cur.next;
        }
        q.next = null;
        p.next = qhead;
        return head;
    }

    public ListNode oddEvenList1(ListNode head) {
        if (head == null) {
            return null;
        }

        if (head.next == null) {
            return head;
        }

        ListNode p = head, q = head.next, cur = q;

        while (p.next != null && q.next != null) {
            p.next = q.next;
            p = p.next;
            q.next = p.next;
            q = q.next;
        }
        p.next = cur;
        return head;
    }

    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    /**
     * 781. 森林中的兔子
     * 森林中，每个兔子都有颜色。其中一些兔子（可能是全部）告诉你还有多少其他的兔子和自己有相同的颜色。
     * 我们将这些回答放在 answers 数组里。
     *
     * 返回森林中兔子的最少数量。
     *
     * 示例:
     * 输入: answers = [1, 1, 2]
     * 输出: 5
     * 解释:
     * 两只回答了 "1" 的兔子可能有相同的颜色，设为红色。
     * 之后回答了 "2" 的兔子不会是红色，否则他们的回答会相互矛盾。
     * 设回答了 "2" 的兔子为蓝色。
     * 此外，森林中还应有另外 2 只蓝色兔子的回答没有包含在数组中。
     * 因此森林中兔子的最少数量是 5: 3 只回答的和 2 只没有回答的。
     * @param answers
     * @return
     */
    public int numRabbits(int[] answers) {
        List<Integer> list = new ArrayList<>();
        Map<Integer,Integer> map = new HashMap<>();
        for (int num : answers) {
            map.put(num,map.getOrDefault(num,0) + 1);
        }
        int res = 0;
        Set<Integer> keys = map.keySet();
        for (int key : keys) {
            int cnt = map.get(key);
            if (key == 0) {
                res += cnt;
            } else {
                int bs = cnt / (key + 1);
                if (cnt % (key + 1) != 0) {
                    bs ++;
                }
                res += (key + 1) * bs;
            }
        }
        return res;
    }

    public int numRabbits2(int[] answers) {
        int[] rabbits = new int[1000];
        int res = 0;
        for (int answer : answers) {
            if (rabbits[answer] > 0) {
                rabbits[answer] --;
            } else {
                res += answer + 1;
                rabbits[answer] = answer;
            }
        }
        return res;
    }

    /**
     * 136. 只出现一次的数字
     * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
     *
     * 说明：
     *
     * 你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？
     * @param nums
     * @return
     */
    public int singleNumber(int[] nums) {
        int single = 0;
        for (int num : nums) {
            single ^= num;
        }
        return single;
    }

    public int singleNumber1(int[] nums) {
        int sum = 0;
        int sum1 = 0;
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            sum += num;
            if (set.add(num)) {
                sum1 += num;
            }
        }

        return sum1 * 2 - sum;
    }


    /**
     * 543. 二叉树的直径
     * 给定一棵二叉树，你需要计算它的直径长度。一棵二叉树的直径长度是任意两个结点路径长度中的最大值。这条路径可能穿过也可能不穿过根结点。
     * @param root
     * @return
     */
    int dept = 0;
    public int diameterOfBinaryTree(TreeNode root) {
        dfs(root);
        return dept;
    }

    public int dfs(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = dfs(root.left);
        int right = dfs(root.right);
        dept = Math.max(dept, left + right);
        return Math.max(left,right) + 1;
    }

    /**
     * 169. 多数元素
     * 给定一个大小为 n 的数组，找到其中的多数元素。多数元素是指在数组中出现次数 大于 ⌊ n/2 ⌋ 的元素。
     *
     * 你可以假设数组是非空的，并且给定的数组总是存在多数元素
     * 输入：[2,2,1,1,1,2,2]
     * 输出：2
     * @param nums
     * @return
     */
    public int majorityElement(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length / 2];
    }

    public int majorityElement1(int[] nums) {
        //摩尔投票法，每个元素数量相互抵消，剩余的那个就是多的数
        int cur = nums[0],count = 1;
        for (int i = 0; i < nums.length; i ++) {
            if (cur == nums[i]) {
                count ++;
            } else {
                if (-- count == 0) {
                    cur = nums[i];
                    count = 1;
                }
            }
        }
        return cur;
    }


    /**
     * 剑指 Offer 38. 字符串的排列
     * 输入一个字符串，打印出该字符串中字符的所有排列。
     *
     * 你可以以任意顺序返回这个字符串数组，但里面不能有重复元素。
     *
     * 示例:
     *
     * 输入：s = "abc"
     * 输出：["abc","acb","bac","bca","cab","cba"]
     *
     * 限制：
     *
     * 1 <= s 的长度 <= 8
     * @param s
     * @return
     */
    Set<String> set = new HashSet<>();
    boolean[] visited = new boolean[8];
    public String[] permutation(String s) {
        //实际是个N叉树
        char[] chars = s.toCharArray();
        dfs2(chars,0,"");
        String[] arrs = new String[set.size()];
        set.toArray(arrs);
        return arrs;
    }

    public void dfs2(char[] chars,int dept,String arr) {
        int n = chars.length;
        if (dept == n) {
            set.add(arr);
            return;
        }
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                visited[i] = true;
                dfs2(chars, dept + 1, arr + chars[i]);
                visited[i] = false;
            }
        }
    }

    /**
     * 260. 只出现一次的数字 III
     * 给定一个整数数组 nums，其中恰好有两个元素只出现一次，其余所有元素均出现两次。 找出只出现一次的那两个元素。你可以按 任意顺序 返回答案。
     *
     * 进阶：你的算法应该具有线性时间复杂度。你能否仅使用常数空间复杂度来实现？
     *
     * 示例 1：
     *
     * 输入：nums = [1,2,1,3,2,5]
     * 输出：[3,5]
     * 解释：[5, 3] 也是有效的答案。
     * @param nums
     * @return
     */
    public int[] singleNumber3(int[] nums) {
        return null;
    }

    public static void main(String[] args) throws ParseException {
        Solution solution = new Solution();
//        solution.plusOne(new int[]{9,9,9});

        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        solution.oddEvenList(node1);
    }
}

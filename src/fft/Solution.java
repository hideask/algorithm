package fft;


import java.util.*;

public class Solution {
    /**
     * 923. 三数之和的多种可能
     * 给定一个整数数组 arr ，以及一个整数 target 作为目标值，
     * 返回满足 i < j < k 且 arr[i] + arr[j] + arr[k] == target 的元组 i, j, k 的数量。
     *
     * 由于结果会非常大，请返回 109 + 7 的模。
     * @param arr
     * @param target
     * @return
     */
    public int threeSumMulti(int[] arr, int target) {
        //固定一个数，求两数之和，排序后方便处理相同数字
        //固定一个数后，如果得到另外的两数之和，
        // 当前结果数等于前面相同数字个数 m,后面相同数字个数n， m * n
        Arrays.sort(arr);
        int n = arr.length, mod = (int) (1e9 + 7);
        int ans = 0;
        for (int i = 0; i < n - 2; i ++) {
            ans += twoSumMulti(arr,i + 1, n - 1, target - arr[i]);
            ans %= mod;
        }
        return ans;
    }

    public int twoSumMulti(int[] arr,int l , int r, int target) {
        int ans = 0, sum = 0, mod = (int) (1e9 + 7);
        //有序数组从两端到中间逼近
        while (l < r) {
            sum = arr[l] + arr[r];
            if (sum > target) {
                r --;
            } else if (sum < target) {
                l ++;
            } else {
                //特殊情况，两端的值相等，说明中间的值都相等
                if (arr[l] == arr[r]) {
                    int n = r - l + 1;
                    ans += n * (n - 1) / 2;
                    ans %= mod;
                    break;
                }
                //计算相同数据个数
                int lcnt = 1, rcnt = 1;
                while (arr[r - 1] == arr[r]) {
                    r --;
                    rcnt ++;
                }
                while (arr[l + 1] == arr[l]) {
                    l ++;
                    lcnt ++;
                }
                ans += rcnt * lcnt;
                ans %= mod;
                l ++;
                r --;
            }
        }
        return ans;
    }

    /**
     * 1963. 使字符串平衡的最小交换次数
     * 给你一个字符串 s ，下标从 0 开始 ，且长度为偶数 n 。字符串 恰好 由 n / 2 个开括号 '[' 和 n / 2 个闭括号 ']' 组成。
     *
     * 只有能满足下述所有条件的字符串才能称为 平衡字符串 ：
     *
     * 字符串是一个空字符串，或者
     * 字符串可以记作 AB ，其中 A 和 B 都是 平衡字符串 ，或者
     * 字符串可以写成 [C] ，其中 C 是一个 平衡字符串 。
     * 你可以交换 任意 两个下标所对应的括号 任意 次数。
     *
     * 返回使 s 变成 平衡字符串 所需要的 最小 交换次数。
     * @param s
     * @return
     */
    public int minSwaps(String s) {
        int n = s.length(), ans = 0, l = 0, r = n - 1,
                lcnt = 0,//从左往右有效左括号的值
                rcnt = 0;//从右往左有效右括号的值
        //初始化
        lcnt += (s.charAt(l) == '[' ? 1 : -1);
        rcnt += (s.charAt(r) == ']' ? 1 : -1);
        while (l < r) {
            //从前往后找到第一个非法位置(右括号多了)
            while (l < r && lcnt >= 0) {
                l ++;
                lcnt += (s.charAt(l) == '[' ? 1 : -1);
            }
            //从后往前找到第一个非法位置(左括号多了)
            while (l < r && rcnt >= 0) {
                r --;
                rcnt += (s.charAt(r) == ']' ? 1 : -1);
            }
            //找到两个非法位置后，交换后继续查找
            if (l >= r) {
                break;
            }
            ans += 1;
            //在l统计范围内，少了一个右括号，多了一个左括号， 所以 + 2
            lcnt += 2;
            rcnt += 2;
        }
        return ans;
    }

    /**
     * 1984. 学生分数的最小差值
     * 给你一个 下标从 0 开始 的整数数组 nums ，其中 nums[i] 表示第 i 名学生的分数。另给你一个整数 k 。
     *
     * 从数组中选出任意 k 名学生的分数，使这 k 个分数间 最高分 和 最低分 的 差值 达到 最小化 。
     *
     * 返回可能的 最小差值 。
     * @param nums
     * @param k
     * @return
     */
    public int minimumDifference(int[] nums, int k) {
        //排序，滑动窗口k
        Arrays.sort(nums);
        int n = nums.length;
        int ans = Integer.MAX_VALUE;
        for (int i = k - 1;i < n ;i ++) {
            ans = Math.min(ans,nums[i] - nums[i - k + 1]);
        }
        return ans;
    }

    /**
     * 1981. 最小化目标值与所选元素的差
     * 给你一个大小为 m x n 的整数矩阵 mat 和一个整数 target 。
     *
     * 从矩阵的 每一行 中选择一个整数，你的目标是 最小化 所有选中元素之 和 与目标值 target 的 绝对差 。
     *
     * 返回 最小的绝对差 。
     *
     * a 和 b 两数字的 绝对差 是 a - b 的绝对值。
     * @param mat
     * @param target
     * @return
     */
    public int minimizeTheDifference(int[][] mat, int target) {
        //动态规划，或者hash
        int m = mat.length,n = mat[0].length,sum = 0;
        //当前行前一行，最接近目标值的集合
        Set<Integer>[] h = new Set[2];
        //当前行最接近目标值的集合
        h[0] = new HashSet<>();
        h[1] = new HashSet<>();
        //初始化第一个set，并初始化第一行的最大值
        for (int num : mat[0]) {
            h[0].add(num);
            sum = Math.max(num, sum);
        }
        for (int i = 1 ; i < m ; i ++) {
            int idx = i % 2;
            int pre_idx = (i - 1) % 2;
            //得到当前行和的最大值
            sum += Arrays.stream(mat[i]).max().getAsInt();
            h[idx].clear();
            //每个单元值从1开始，j的最小值就是i + 1
            for (int j = i + 1; j <= sum; j ++) {
                //如果上一行存在j - x,说明当前行能拼凑出和为j 的值
                //不用遍历每个数，防止超时
                for (int x : mat[i]) {
                    if (h[pre_idx].contains(j - x)) {
                        h[idx].add(j);
                        break;
                    }
                }
            }
        }

        int ans = Integer.MAX_VALUE;
        for (int num : h[(m - 1) % 2]) {
            ans = Math.min(ans,Math.abs(target - num));
        }
        return ans;
    }

    /**
     * 1987. 不同的好子序列数目
     * 给你一个二进制字符串 binary 。 binary 的一个 子序列 如果是 非空 的且没有 前导 0 （除非数字是 "0" 本身），
     * 那么它就是一个 好 的子序列。
     * 请你找到 binary 不同好子序列 的数目。
     * 比方说，如果 binary = "001" ，那么所有 好 子序列为 ["0", "0", "1"] ，所以 不同 的好子序列为 "0" 和 "1" 。
     * 注意，子序列 "00" ，"01" 和 "001" 不是好的，因为它们有前导 0 。
     * 请你返回 binary 中 不同好子序列 的数目。由于答案可能很大，请将它对 1e9 + 7 取余 后返回。
     *
     * 一个 子序列 指的是从原数组中删除若干个（可以一个也不删除）元素后，不改变剩余元素顺序得到的序列。
     * @param binary
     * @return
     */
    public int numberOfUniqueGoodSubsequences(String binary) {
        //根据当前位置i的值去设立递推公式，以f[i][0] , f[i][1]表示
        //如果s[i] = 0
        //f[i][0] = f[i + 1][1] + f[i + 1][0] + 1;
        //最后 + 1，代表 01xxx
        //             001xxx
        //             0001xxx，如果将第i位的0加到前面三个前面，前面的会重复，
        //             只能加到最多前导0的前面
        //f[i][1] = f[i + 1][1]
        //
        //如果s[i] = 1,上述公式反过来
        int n = binary.length(),mod = 1000000007,flag = 0;
        int[][] f = new int[n + 1][2];
        for (int i = n - 1; i >= 0; i --) {
            int j = (binary.charAt(i) - '0');
            if (j == 0) {
                flag = 1;
            }
            f[i][j] = f[i + 1][j] + f[i + 1][1 - j] + 1;
            f[i][1 - j] = f[i + 1][1 - j];
            f[i][j] %= mod;
            f[i][1 - j] %= mod;
        }
        return f[0][1] + flag;
    }

    /**
     * 1911. 最大子序列交替和
     * 一个下标从 0 开始的数组的 交替和 定义为 偶数 下标处元素之 和 减去 奇数 下标处元素之 和 。
     *
     * 比方说，数组 [4,2,5,3] 的交替和为 (4 + 5) - (2 + 3) = 4 。
     * 给你一个数组 nums ，请你返回 nums 中任意子序列的 最大交替和 （子序列的下标 重新 从 0 开始编号）。
     *
     * 一个数组的 子序列 是从原数组中删除一些元素后（也可能一个也不删除）剩余元素不改变顺序组成的数组。
     * 比方说，[2,7,4] 是 [4,2,3,7,2,1,4] 的一个子序列（加粗元素），但是 [2,4,2] 不是。
     * @param nums
     * @return
     */
    public long maxAlternatingSum(int[] nums) {
        //设定第i位为结尾，之前是减法还是加法，同时加上，减去i的值，求其中最大的值
        int n = nums.length;
        long sub_max = Integer.MIN_VALUE,
                add_max = nums[0],
                a,//加上i,能获得的最大值
                b,//减去i,能获得的最大值
                ans = nums[0];
        for (int i = 1; i < n ;i ++) {
            //加上i，跟在一个减法序列的后面，和当前值重起一个序列相比
            a = Math.max(sub_max + nums[i], (long) nums[i]);
            b = add_max - nums[i];
            ans = Math.max(ans, Math.max(a,b));
            sub_max = Math.max(sub_max, b);
            add_max = Math.max(add_max, a);
        }
        return ans;
    }

    /**
     * 剑指 Offer II 069. 山峰数组的顶部
     * 符合下列属性的数组 arr 称为 山峰数组（山脉数组） ：
     *
     * arr.length >= 3
     * 存在 i（0 < i < arr.length - 1）使得：
     * arr[0] < arr[1] < ... arr[i-1] < arr[i]
     * arr[i] > arr[i+1] > ... > arr[arr.length - 1]
     * 给定由整数组成的山峰数组 arr ，返回任何满足 arr[0] < arr[1] < ... arr[i - 1] < arr[i] > arr[i + 1] > ... > arr[arr.length - 1] 的下标 i ，即山峰顶部。
     * @param arr
     * @return
     */
    public int peakIndexInMountainArray(int[] arr) {
        //方法一
//        int n = arr.length,ans = 1;
//        for (int i = 2 ;i < n - 1 ;i ++) {
//            ans = arr[i] > arr[ans] ? i : ans;
//        }
//        return ans;
        //方法二
//        int n = arr.length, l = 1, r = n - 2;
//        while (l < r) {
//            if (arr[l] < arr[r]) {
//                l ++;
//            }
//            if (arr[r] < arr[l]) {
//                r --;
//            }
//        }
//        return l;

        //方法三，二分
        int l = 1, r = arr.length - 2, ans = 1;
        while (l < r) {
            int mid = (l + r) / 2;
            //当 mid < ans 时， 峰值在 mid 右边
            //当 mid > ans 时， 峰值在mid 左边
            if (arr[mid] > arr[mid + 1]) {
                ans = mid;
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return ans;
    }

    /**
     * 1546. 和为目标值且不重叠的非空子数组的最大数目
     * 给你一个数组 nums 和一个整数 target 。
     *
     * 请你返回 非空不重叠 子数组的最大数目，且每个子数组中数字和都为 target 。
     *
     * @param nums
     * @param target
     * @return
     */
    public int maxNonOverlapping(int[] nums, int target) {
        //如果我们找到一个区间。为了得到不重复的子数组区间和，下一个起始区间
        int n = nums.length;
        int sum = 0;
        int ans = 0;
        //贪心算法，获得到一个目标值后，将原有的set清空，重新计算
        //方法一
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < n ; i ++) {
            sum += nums[i];
            set.add(0);
            if (set.contains(sum - target) ) {
                ans ++;
                set.clear();
                sum = 0;
                continue;
            } else {
                set.add(sum);
            }
        }
        //方法二
//        int i = 0;
//        while (i < n) {
//            set.add(0);
//            sum = 0;
//            while (i < n) {
//                sum += nums[i];
//                if (set.contains(sum - target)) {
//                    ans ++
//                    break;
//                } else {
//                    set.add(sum);
//                    i++;
//                }
//            }
//            i++;
//        }
        return ans;
    }

    /**
     * 2151. 基于陈述统计最多好人数
     * 游戏中存在两种角色：
     *
     * 好人：该角色只说真话。
     * 坏人：该角色可能说真话，也可能说假话。
     * 给你一个下标从 0 开始的二维整数数组 statements ，大小为 n x n ，表示 n 个玩家对彼此角色的陈述。
     * 具体来说，statements[i][j] 可以是下述值之一：
     *
     * 0 表示 i 的陈述认为 j 是 坏人 。
     * 1 表示 i 的陈述认为 j 是 好人 。
     * 2 表示 i 没有对 j 作出陈述。
     * 另外，玩家不会对自己进行陈述。形式上，对所有 0 <= i < n ，都有 statements[i][i] = 2 。
     *
     * 根据这 n 个玩家的陈述，返回可以认为是 好人 的 最大 数目。
     * @param statements
     * @return
     */
    public int maximumGood(int[][] statements) {
        //枚举所有玩家的表述，采用二进制表示法，验证假设是否成立
        int n = statements.length;
        //枚举次数，如果 n = 3, n 从 000 枚举到 111，代表 0 --> 7
        //也就是从0 到 2^n - 1，代表每一种陈述的可能
        int m = (1 << n);
        int ans = 0;
        //对应二进制位0代表坏人，1代表好人，循环每一种陈述
        for (int i = 0; i < m ; i ++) {
            if (!check(statements,i,n)) {
                continue;
            }
//            ans = Math.max(ans, countOne(i));
            ans = Math.max(ans, Integer.bitCount(i));
        }
        return ans;
    }

    public int countOne(int x) {
        int cnt = 0;
        while (x != 0) {
            //获得对应数字二进制最后一个1，
            //例如： 1111000  & 1110111 = 1110000
            x &= (x - 1);
            cnt ++;
        }
        return cnt;
    }

    public boolean check(int[][] statements, int mark, int n) {
        //假设
        for (int i = 0; i < n;i ++) {
            //当前假设的表述是坏人，就跳过
            if ((mark & (1 << i)) == 0) {
                continue;
            }

            for (int j = 0; j < n; j ++) {
                if (statements[i][j] == 2) {
                    continue;
                }
                //i 对 j的实际表述是否和假设表述一致
                if (statements[i][j] != ((mark >> j) & 1)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 2163. 删除元素后和的最小差值
     * 给你一个下标从 0 开始的整数数组 nums ，它包含 3 * n 个元素。
     *
     * 你可以从 nums 中删除 恰好 n 个元素，剩下的 2 * n 个元素将会被分成两个 相同大小 的部分。
     *
     * 前面 n 个元素属于第一部分，它们的和记为 sumfirst 。
     * 后面 n 个元素属于第二部分，它们的和记为 sumsecond 。
     * 两部分和的 差值 记为 sumfirst - sumsecond 。
     *
     * 比方说，sumfirst = 3 且 sumsecond = 2 ，它们的差值为 1 。
     * 再比方，sumfirst = 2 且 sumsecond = 3 ，它们的差值为 -1 。
     * 请你返回删除 n 个元素之后，剩下两部分和的 差值的最小值 是多少。
     * @param nums
     * @return
     */
    public long minimumDifference(int[] nums) {
        //-------m-------
        //对于m位置作为分割点，前面如果要得到n个值，就要删掉m - n 个值
        //后面删掉 size - m - n  ,计为k
        //如果要使得前后差值最小，我们就要使得剩下的元素，前半部分尽可能的小，后半部分尽可能的大
        int m = nums.length, n = m / 3;
        long[] lsum = new long[m];
        //记录从左往右的最大值
        PriorityQueue<Integer> left = new PriorityQueue<>((o1, o2) -> o2 - o1);
        //记录冲右往左的最小值
        PriorityQueue<Integer> right = new PriorityQueue<>();
        long sum = 0;//代表扫描过程中前缀和减去删除的最大值的和值
        for (int i = 0 ;i < m ;i ++) {
            //前缀和
            sum += nums[i];
            left.offer(nums[i]);
            if (left.size() < n) {
                continue;
            }
            //如果满足n个值，删除最大的值
            if (left.size() > n) {
                sum -= left.poll();
            }
            //记录下删除后的前缀和
            lsum[i] = sum;
        }

        long ans = Long.MAX_VALUE;
        sum = 0;
        for (int i = m - 1;i >= n ;i --) {
            sum += nums[i];
            right.offer(nums[i]);
            if (right.size() < n) {
                continue;
            }
            //如果满足n个值，删除最小的值
            if (right.size() > n) {
                sum -= right.poll();
            }
            //代表第i位为分割点，左边的前缀和 减 右边的前缀和的最小值
            ans = Math.min(ans , lsum[i - 1] - sum);
        }
        return ans;
    }

    /**
     * 2187. 完成旅途的最少时间
     * 给你一个数组 time ，其中 time[i] 表示第 i 辆公交车完成 一趟旅途 所需要花费的时间。
     *
     * 每辆公交车可以 连续 完成多趟旅途，也就是说，一辆公交车当前旅途完成后，可以 立马开始 下一趟旅途。
     * 每辆公交车 独立 运行，也就是说可以同时有多辆公交车在运行且互不影响。
     *
     * 给你一个整数 totalTrips ，表示所有公交车 总共 需要完成的旅途数目。请你返回完成 至少 totalTrips 趟旅途需要花费的 最少 时间。
     * @param time
     * @param totalTrips
     * @return
     */
    public long minimumTime(int[] time, int totalTrips) {
        //对于totalTrips来说，依赖与每趟公交车的运行时间，
        //公交车运行时间越短，totalTrips越多，呈线性关系
        //从times求trips容易，从trips求time困难，
        //可以求满足trips的最少时间，时间从0到max，求第一个满足条件的时间
        //符合01模型，采用二分算法
        long l = 0,r = 1l * totalTrips * Arrays.stream(time).min().getAsInt();

        while (l < r) {
            long mid = (l + r) / 2;
            if (checkTrips(mid,time) < totalTrips) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }

        return l;
    }

    public long checkTrips(long t , int[] time) {
        long trips = 0;
        for (int i = 0; i < time.length; i ++) {
            trips += (t / time[i]);
        }
        return trips;
    }



    class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    /**
     * 2181. 合并零之间的节点
     * 给你一个链表的头节点 head ，该链表包含由 0 分隔开的一连串整数。链表的 开端 和 末尾 的节点都满足 Node.val == 0 。
     *
     * 对于每两个相邻的 0 ，请你将它们之间的所有节点合并成一个节点，其值是所有已合并节点的值之和。然后将所有 0 移除，修改后的链表不应该含有任何 0 。
     *
     *  返回修改后链表的头节点 head 。
     * @param head
     * @return
     */
    public ListNode mergeNodes(ListNode head) {
        ListNode p = head, q = head;
        int sum = 0;
        while (p != null) {
            if (p.val != 0) {
                sum += p.val;
            }
            p = p.next;
            if (p != null && p.val == 0) {
                ListNode node = new ListNode();
                node.val = sum;
                node.next = p.next;
                sum = 0;
                q.next = node;
                q = node;
            }
        }
        return head.next;

        //方法二，递归
//        if (head == null || head.next == null) {
//            return null;
//        }
//        ListNode p = head;
//        while (p.next.val != 0) {
//            p.val += p.next.val;
//            p.next = p.next.next;
//        }
//        p.next = mergeNodes(p.next);
//        return head;
    }

    /**
     * 2182. 构造限制重复的字符串
     * 给你一个字符串 s 和一个整数 repeatLimit ，用 s 中的字符构造一个新字符串 repeatLimitedString ，
     * 使任何字母 连续 出现的次数都不超过 repeatLimit 次。你不必使用 s 中的全部字符。
     *
     * 返回 字典序最大的 repeatLimitedString 。
     *
     * 如果在字符串 a 和 b 不同的第一个位置，字符串 a 中的字母在字母表中出现时间比字符串 b 对应的字母晚，
     * 则认为字符串 a 比字符串 b 字典序更大 。如果字符串中前 min(a.length, b.length) 个字符都相同，那么较长的字符串字典序更大。
     * @param s
     * @param repeatLimit
     * @return
     */
    public String repeatLimitedString(String s, int repeatLimit) {
        char[] c = s.toCharArray();
        Arrays.sort(c);

        StringBuilder tmp ;
        StringBuilder ans = new StringBuilder();
        int i = c.length - 1;

        while (i >= 0) {
            tmp = new StringBuilder();
            //从后往前，将连续的字符放入tmp中

            tmp.append(c[i]);
            i --;
            while (i >= 0 && tmp.charAt(0) == c[i]) {
                tmp.append(c[i]);
                i --;
            }
            int j = 0;
            //每隔limit个字符，将后面的字符往前挪
            while (tmp.length() - j > repeatLimit) {
                ans.append(tmp.substring(j, j + repeatLimit));
                if (i < 0) {
                    return ans.toString();
                }
                ans.append(c[i]);
                i --;
                j += repeatLimit;
            }
            //可能剩余小于limit的字符串
            if (j < tmp.length()) {
                ans.append(tmp.substring(j));
            }
        }
        return ans.toString();
    }

    /**
     * 2178. 拆分成最多数目的正偶数之和
     * 给你一个整数 finalSum 。请你将它拆分成若干个 互不相同 的正偶数之和，且拆分出来的正偶数数目 最多 。
     *
     * 比方说，给你 finalSum = 12 ，那么这些拆分是 符合要求 的（互不相同的正偶数且和为 finalSum）：
     * (2 + 10) ，(2 + 4 + 6) 和 (4 + 8) 。它们中，(2 + 4 + 6) 包含最多数目的整数。
     * 注意 finalSum 不能拆分成 (2 + 2 + 4 + 4) ，因为拆分出来的整数必须互不相同。
     * 请你返回一个整数数组，表示将整数拆分成 最多 数目的正偶数数组。如果没有办法将 finalSum 进行拆分，
     * 请你返回一个 空 数组。你可以按 任意 顺序返回这些整数。
     * @param finalSum
     * @return
     */
    public List<Long> maximumEvenSplit(long finalSum) {
        List<Long> ans = new ArrayList<>();
        if (finalSum % 2 == 1) {
            return ans;
        }
        long sum = 0;
        for (long i = 2; i <= finalSum ; i += 2) {
            if (finalSum - sum < i) {
                ans.set(ans.size() - 1, ans.get(ans.size() - 1) + i);
                return ans;
            }
            ans.add(i);
            sum += i;
        }
        return ans;
    }

    /**
     * 2170. 使数组变成交替数组的最少操作数
     * 给你一个下标从 0 开始的数组 nums ，该数组由 n 个正整数组成。
     *
     * 如果满足下述条件，则数组 nums 是一个 交替数组 ：
     *
     * nums[i - 2] == nums[i] ，其中 2 <= i <= n - 1 。
     * nums[i - 1] != nums[i] ，其中 1 <= i <= n - 1 。
     * 在一步 操作 中，你可以选择下标 i 并将 nums[i] 更改 为 任一 正整数。
     *
     * 返回使数组变成交替数组的 最少操作数 。
     * @param nums
     * @return
     */
    public int minimumOperations(int[] nums) {
        //找出奇数位、偶数位出现次数最多的两个数字
        int[][] x1 = new int[1][2];//奇数位出现次数最多的数字
        int[][] x2 = new int[1][2];//奇数位出现次数第二多的数字
        int[][] y1 = new int[1][2];//偶数位出现次数最多的数字
        int[][] y2 = new int[1][2];//偶数位出现次数第二多的数字
        getMaxNum(nums,0,x1,x2);
        getMaxNum(nums,1,y1,y2);
        int n = nums.length;
        int n0 = (n - 1) / 2 + 1;//奇数位个数
        int n1 = n - n0;//偶数位个数
        //最多出现的数字不相等
        if (x1[0][0] != y1[0][0]) {
            return (n0 - x1[0][1]) + (n1 - y1[0][1]);
        }
        //最多出现的数字相等
        return Math.min((n0 - x1[0][1]) + (n1 - y2[0][1]), (n0 - x2[0][1]) + (n1 - y1[0][1]));
    }

    public void getMaxNum(int[] nums,int p, int[][] x1, int[][] x2) {
        Map<Integer,Integer> cnt = new HashMap<>();
        int n = nums.length;
        for (int i = p; i < n; i += 2) {
            cnt.put(nums[i], cnt.getOrDefault(nums[i],0) + 1);
        }
        for (int key : cnt.keySet()) {
            if (cnt.get(key) > x1[0][1]) {
                x2[0][0] = x1[0][0];
                x2[0][1] = x1[0][1];
                x1[0][0] = key;
                x1[0][1] = cnt.get(key);
            } else if (cnt.get(key) > x2[0][1]) {
                x2[0][0] = key;
                x2[0][1] = cnt.get(key);
            }
        }
    }

    /**
     * 2165. 重排数字的最小值
     * 给你一个整数 num 。重排 num 中的各位数字，使其值 最小化 且不含 任何 前导零。
     *
     * 返回不含前导零且值最小的重排数字。
     *
     * 注意，重排各位数字后，num 的符号不会改变。
     * @param num
     * @return
     */
    public long smallestNumber(long num) {
        if (num == 0) {
            return 0;
        }
        int flag = 1;
        if (num < 0) {
            flag = -1;
            num = -num;
        }
        int[] cnt = new int[10];
        while (num != 0) {
            int m = (int) (num % 10);
            cnt[m] += 1;
            num /= 10;
        }
        long ans = 0;
        //num大于0 时，获取除0以外的最小值
        if (flag == 1) {
            for (int i = 1 ; i <= 9 ; i ++) {
                if (cnt[i] == 0) {
                    continue;
                }
                ans = i;
                cnt[i] --;
                break;
            }
        }
        //处理剩余数字，num大于0，从0开始，num小于0，从9开始往前
        //将正负数处理放到一起
        for (int i = (flag == 1 ? 0 : 9), m = 9 - i ; i * flag < m; i += flag) {
            for (int j = 0 ;j < cnt[i] ; j ++) {
                ans = ans * 10 + i;
            }
        }

        return num * flag;
    }

    /**
     * 1744. 你能在你最喜欢的那天吃到你最喜欢的糖果吗？
     * 给你一个下标从 0 开始的正整数数组 candiesCount ，其中 candiesCount[i] 表示你拥有的第 i 类糖果的数目。
     * 同时给你一个二维数组 queries ，其中 queries[i] = [favoriteTypei, favoriteDayi, dailyCapi] 。
     *
     * 你按照如下规则进行一场游戏：
     *
     * 你从第 0 天开始吃糖果。
     * 你在吃完 所有 第 i - 1 类糖果之前，不能 吃任何一颗第 i 类糖果。
     * 在吃完所有糖果之前，你必须每天 至少 吃 一颗 糖果。
     * 请你构建一个布尔型数组 answer ，用以给出 queries 中每一项的对应答案。此数组满足：
     *
     * answer.length == queries.length 。answer[i] 是 queries[i] 的答案。
     * answer[i] 为 true 的条件是：在每天吃 不超过 dailyCapi 颗糖果的前提下，
     * 你可以在第 favoriteDayi 天吃到第 favoriteTypei 类糖果；否则 answer[i] 为 false 。
     * 注意，只要满足上面 3 条规则中的第二条规则，你就可以在同一天吃不同类型的糖果。
     *
     * 请你返回得到的数组 answer 。
     * @param candiesCount
     * @param queries
     * @return
     */
    public boolean[] canEat(int[] candiesCount, int[][] queries) {
        int n = candiesCount.length;
        int m = queries.length;
        long[] sum = new long[n];
        sum[0] = candiesCount[0];
        for (int i = 1; i < n; i ++) {
            sum[i] = sum[i - 1] + candiesCount[i];
        }
        boolean[] ans = new boolean[m];
        for (int i = 0 ;i < m ; i ++) {
            int f = queries[i][0], d = queries[i][1], s = queries[i][2];
            long sumf = sum[f];//能吃到第f天的总糖果数
            long sumf1 = ((f - 1) >= 0 ? sum[f - 1] : 0);//能吃到第f天前一天的总糖果数
            //每天吃最少，由于前面的糖果不够，不够吃，
            //每天吃最多，由于前面的糖果太多，吃不到
            if (sumf < d + 1 || sumf1 / s - 1 >= d) {
                ans[i] = false;
            } else {
                ans[i] = true;
            }
        }

        return ans;
    }

    /**
     * 1745. 回文串分割 IV
     * 给你一个字符串 s ，如果可以将它分割成三个 非空 回文子字符串，那么返回 true ，否则返回 false 。
     *
     * 当一个字符串正着读和反着读是一模一样的，就称其为 回文字符串 。
     * @param s
     * @return
     */
    Set<Integer> pos0,posn;
    public boolean checkPartitioning(String s) {
        //采用两个集合去维护以0为开头的回文串起始位置，
        // 以n为结尾的回文串的起始位置
        //当再次扫描字符串时,如果是回文，则判断起始位置是否在pos0中，
        // 结束位置是否在posn中
        pos0 = new HashSet<>();
        posn = new HashSet<>();
        int n = s.length();
        for (int i = 0; i < n; i ++) {
            getPos(s,i,i);//回文串是奇数，以i为中心
            getPos(s,i,i + 1);//回文串是偶数，以i， i + 1为中心
        }
        for (int i = 0; i < n; i ++) {
            if (checkPos(s,i,i)) {
                return true;
            }
            if (checkPos(s,i,i + 1)) {
                return true;
            }
        }
        return false;
    }

    public void getPos(String s , int i ,int j) {
        int n = s.length();
        while (i >= 0 && j < n && s.charAt(i) == s.charAt(j)) {
            if (i == 0) {
                pos0.add(j);
            }
            if (j == n - 1) {
                posn.add(i);
            }
            i --;
            j ++;
        }
    }

    public boolean checkPos(String s , int i ,int j) {
        int n = s.length();
        while (i >= 0 && j < n && s.charAt(i) == s.charAt(j)) {
            if (pos0.contains(i - 1) && posn.contains(j + 1)) {
                return true;
            }
            i --;
            j ++;
        }
        return false;
    }
    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.maxNonOverlapping(new int[]{-1,-2,8,-3,8,-5,5,-4,5,4,1},5);
    }
}

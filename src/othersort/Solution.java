package othersort;

import tree.TreeNode;

import java.util.*;

/**
 * description:Solution
 * create user: songj
 * date : 2021/5/27 23:01
 */
public class Solution {
    /**
     * 207. 课程表
     * 你这个学期必须选修 numCourses 门课程，记为 0 到 numCourses - 1 。
     *
     * 在选修某些课程之前需要一些先修课程。 先修课程按数组 prerequisites 给出，
     * 其中 prerequisites[i] = [ai, bi] ，表示如果要学习课程 ai 则 必须 先学习课程  bi 。
     *
     * 例如，先修课程对 [0, 1] 表示：想要学习课程 0 ，你需要先完成课程 1 。
     * 请你判断是否可能完成所有课程的学习？如果可以，返回 true ；否则，返回 false 。
     *
     * 示例 1：
     *
     * 输入：numCourses = 2, prerequisites = [[1,0]]
     * 输出：true
     * 解释：总共有 2 门课程。学习课程 1 之前，你需要完成课程 0 。这是可能的。
     * 示例 2：
     *
     * 输入：numCourses = 2, prerequisites = [[1,0],[0,1]]
     * 输出：false
     * 解释：总共有 2 门课程。学习课程 1 之前，你需要先完成课程 0 ；并且学习课程 0 之前，你还应先完成课程 1 。这是不可能的。
     * @param numCourses
     * @param prerequisites
     * @return
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        //使用广度优先搜索
        //记录课程的前置数量
        int[] indeg = new int[numCourses];
        //记录课程间的关系
        List<List<Integer>>  g = new ArrayList<>();
        //存储当前正在学的课程
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            g.add(new ArrayList<>());
        }

        //[a,b] ,a为后续课程，b为前置课程
        for (int[] prerequisite : prerequisites) {
            //记录课程的前置课程数量
            indeg[prerequisite[0]] ++;
            //记录所有的后续课，加入到对应的前置课程集合中
            g.get(prerequisite[1]).add(prerequisite[0]);
        }

        //寻找入度为0的课程
        for (int i = 0;i < numCourses; i ++) {
            if (indeg[i] == 0) {
                queue.add(i);
            }
        }

        while (!queue.isEmpty()) {
            //将该学习的课 放入队列中
            int pre = queue.poll();
            //学习过，减一
            numCourses --;
            //找到后置课程，如果后置课程入度为0，代表需要学习，将他放到队列中
            for (Integer cur: g.get(pre)) {
                if (-- indeg[cur] == 0) {
                    queue.add(cur);
                }
            }
        }
        return numCourses == 0;
    }


    int[] indg ;//0 未读；1 正在读；2 已读
    List<List<Integer>> gf = new ArrayList<>();
    boolean flag = true;//标记是否可以走下去，没有循环
    public boolean canFinish1(int numCourses, int[][] prerequisites) {
        //使用胜读优先搜索
        for (int i = 0; i < numCourses; i ++) {
            gf.add(new ArrayList<>());
        }
        indg = new int[numCourses];
        for (int[] prerequisite : prerequisites) {
            gf.get(prerequisite[1]).add(prerequisite[0]);
        }

        for (int i = 0; i < numCourses && flag; i ++) {
            if (indg[i] == 0) {
                dfs(i);
            }
        }
        return flag;

    }

    public void dfs (int m) {
        //标记为正在读
        indg[m] = 1;
        //搜索后置课程
        for (int cur : gf.get(m)) {
            //如果入度为0，继续往下搜索
            if (indg[cur] == 0) {
                dfs(cur);
                if (!flag) {//如果遇到false代表有课程循环依赖了
                    return;
                }
            } else if (indg[cur] == 1) {
                //如果遇到后置课程是正在读，则标记为false
                flag = false;
                return;
            }
        }
        indg[m] = 2;

    }

    /**
     * 210. 课程表 II
     * 现在你总共有 n 门课需要选，记为 0 到 n-1。
     *
     * 在选修某些课程之前需要一些先修课程。 例如，想要学习课程 0 ，你需要先完成课程 1 ，
     * 我们用一个匹配来表示他们: [0,1]
     *
     * 给定课程总量以及它们的先决条件，返回你为了学完所有课程所安排的学习顺序。
     *
     * 可能会有多个正确的顺序，你只要返回一种就可以了。如果不可能完成所有课程，返回一个空数组。
     *
     * 示例 1:
     *
     * 输入: 2, [[1,0]]
     * 输出: [0,1]
     * 解释: 总共有 2 门课程。要学习课程 1，你需要先完成课程 0。因此，正确的课程顺序为 [0,1] 。
     */

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int [] indeg = new int[numCourses];
        List<List<Integer>> g = new ArrayList<>();
        for (int i = 0; i < numCourses; i ++) {
            g.add(new ArrayList<>());
        }
        Queue<Integer> queue = new LinkedList<>();
        int[] res = new int[numCourses];
        int idx = 0;
        for (int[] prerequisite : prerequisites) {
            indeg[prerequisite[0]] ++ ;
            g.get(prerequisite[1]).add(prerequisite[0]);
        }

        for (int i = 0; i < numCourses; i ++) {
            if (indeg[i] == 0) {
                queue.add(i);
            }
        }

        while (!queue.isEmpty()) {
            int pre = queue.poll();
            res [idx++] = pre;
            for (int cur : g.get(pre)) {
                if (-- indeg[cur] == 0) {
                    queue.add(cur);
                }
            }
        }
        if (idx != numCourses) {
            return new int[0];
        }
        return res;
    }

    int idx = 0;
    int[] res ;
    public int[] findOrder1(int numCourses, int[][] prerequisites) {
        idx = numCourses - 1;
        res = new int[numCourses];
        //使用胜读优先搜索
        for (int i = 0; i < numCourses; i ++) {
            gf.add(new ArrayList<>());
        }
        indg = new int[numCourses];
        for (int[] prerequisite : prerequisites) {
            gf.get(prerequisite[1]).add(prerequisite[0]);
        }

        for (int i = 0; i < numCourses && flag; i ++) {
            if (indg[i] == 0) {
                dfs1(i);
            }
        }
        return res;

    }

    public void dfs1 (int m) {
        //标记为正在读
        indg[m] = 1;
        //搜索后置课程
        for (int cur : gf.get(m)) {
            //如果入度为0，继续往下搜索
            if (indg[cur] == 0) {
                dfs1(cur);
                if (!flag) {//如果遇到false代表有课程循环依赖了
                    res = new int[0];
                    return;
                }
            } else if (indg[cur] == 1) {
                //如果遇到后置课程是正在读，则标记为false
                res = new int[0];
                flag = false;
                return;
            }
        }
        indg[m] = 2;
        res[idx--] = m;

    }

    /**
     * 1122. 数组的相对排序
     * 给你两个数组，arr1 和 arr2，
     *
     * arr2 中的元素各不相同
     * arr2 中的每个元素都出现在 arr1 中
     * 对 arr1 中的元素进行排序，使 arr1 中项的相对顺序和 arr2 中的相对顺序相同。
     * 未在 arr2 中出现过的元素需要按照升序放在 arr1 的末尾。
     *
     * 示例：
     *
     * 输入：arr1 = [2,3,1,3,2,4,6,7,9,2,19], arr2 = [2,1,4,3,9,6]
     * 输出：[2,2,2,1,4,3,3,9,6,7,19]
     * @param arr1
     * @param arr2
     * @return
     */
    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        //获取arr1最大值
        int max = Arrays.stream(arr1).max().getAsInt();
        //创建一个按最大值的数组
        int[] tmp = new int[max + 1];
        //将下标对应的值+1
        for (int i : arr1) {
            tmp[i] ++;
        }

        int ind = 0;
        //按arr2添加数据
        for (int i : arr2) {
            while (tmp[i] -- != 0) {
                arr1[ind ++] = i;
            }
        }
        //剩余的数字放到后面，已经按i 排序
        for (int i = 0 ; i <= max; i ++) {
            if (tmp[i] <= 0) {
                continue;
            }
            while (tmp[i]-- != 0) {
                arr1[ind ++] = i;
            }
        }
        return arr1;
    }

    /**
     * 164. 最大间距
     * 给定一个无序的数组，找出数组在排序之后，相邻元素之间最大的差值。
     *
     * 如果数组元素个数小于 2，则返回 0。
     *
     * 示例 1:
     *
     * 输入: [3,6,9,1]
     * 输出: 3
     * 解释: 排序后的数组是 [1,3,6,9], 其中相邻元素 (3,6) 和 (6,9) 之间都存在最大差值 3。
     * 示例 2:
     *
     * 输入: [10]
     * 输出: 0
     * 解释: 数组元素个数小于 2，因此返回 0。
     * @param nums
     * @return
     */
    public int maximumGap(int[] nums) {
        //使用基数排序
        int n = nums.length;
        if (n < 2) {
            return 0;
        }
        //获取最大值
        int max = Arrays.stream(nums).max().getAsInt();
        //排序位数
        int exp = 1;
        int[] tmp = new int[n];
        while (max >= exp) {
            //初始化10个桶，对应数字 0 至 9
            int[] cnt = new int[10];
            //初始化每个桶的大小
            for (int i = 0; i < n; i ++) {
                int m = (nums[i] / exp) % 10;
                cnt[m] ++;
            }
            //求前缀和
            for (int i = 1; i < cnt.length; i ++) {
                cnt[i] += cnt[i - 1];
            }
            //将数字按基数排序放到对应位置,数组从后往前扫描
            for (int i = n - 1; i >= 0; i--) {
                int m = (nums[i] / exp) % 10;
                tmp[--cnt[m]] = nums[i];
            }
            //将tmp数组复制到nums中
            System.arraycopy(tmp,0, nums, 0, n);
            //位数推进
            exp *= 10;
        }
        int res = 0;

        for (int i = 1; i < n ; i ++) {
            res = Math.max(nums[i] -  nums[i - 1],res);
        }
        return res;
    }

    /**
     * 274. H 指数
     * 给定一位研究者论文被引用次数的数组（被引用次数是非负整数）。编写一个方法，计算出研究者的 h 指数。
     *
     * h 指数的定义：h 代表“高引用次数”（high citations），一名科研人员的 h 指数是指他（她）的 （N 篇论文中）
     * 总共有 h 篇论文分别被引用了至少 h 次。且其余的 N - h 篇论文每篇被引用次数 不超过 h 次。
     *
     * 例如：某人的 h 指数是 20，这表示他已发表的论文中，每篇被引用了至少 20 次的论文总共有 20 篇。
     *
     *
     *
     * 示例：
     *
     * 输入：citations = [3,0,6,1,5]
     * 输出：3
     * 解释：给定数组表示研究者总共有 5 篇论文，每篇论文相应的被引用了 3, 0, 6, 1, 5 次。
     *      由于研究者有 3 篇论文每篇 至少 被引用了 3 次，其余两篇论文每篇被引用 不多于 3 次，
     *      所以她的 h 指数是 3。
     * @param citations
     * @return
     */
    public int hIndex(int[] citations) {
        //将数组按从小到大排序，从后往前找，例如第四篇论文引用次数需要是3
        Arrays.sort(citations);
        int n = citations.length;
        int i = 0;
        while (i < n && i < citations[n - 1- i]) {
            i ++;
        }
        return i;
    }

    /**
     * 56. 合并区间
     * 以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。
     * 请你合并所有重叠的区间，并返回一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间。
     *
     * 示例 1：
     *
     * 输入：intervals = [[1,3],[2,6],[8,10],[15,18]]
     * 输出：[[1,6],[8,10],[15,18]]
     * 解释：区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
     * 示例 2：
     *
     * 输入：intervals = [[1,4],[4,5]]
     * 输出：[[1,5]]
     * 解释：区间 [1,4] 和 [4,5] 可被视为重叠区间。
     * @param intervals
     * @return
     */
    public int[][] merge(int[][] intervals) {
        //数组按指定规则排序
        Arrays.sort(intervals, (a,b) -> {
            return a[0] - b[0];
        } );

        //初始化一个结果数组
        int[][] res = new int[intervals.length][2];
        //结果数组下标
        int idx = -1;
        for (int[] interval: intervals) {
            //如果是第一次或者当前结果数组末尾结果小于当前数组首位，这结果数组新增一位
            if (idx == -1 || res[idx][1] < interval[0]) {
                res[++idx] = interval;
            } else {
                //比较两个数组末尾，取大值对当前结果数组进行合并
                res[idx][1] = Math.max(res[idx][1],interval[1]);
            }
        }
        //截取数组
        return Arrays.copyOf(res, idx + 1);
    }

    /**
     * 1288. 删除被覆盖区间
     * 给你一个区间列表，请你删除列表中被其他区间所覆盖的区间。
     *
     * 只有当 c <= a 且 b <= d 时，我们才认为区间 [a,b) 被区间 [c,d) 覆盖。
     *
     * 在完成所有删除操作后，请你返回列表中剩余区间的数目。
     *
     * 示例：
     *
     * 输入：intervals = [[1,4],[3,6],[2,8]]
     * 输出：2
     * 解释：区间 [3,6] 被区间 [2,8] 覆盖，所以它被删除了。
     * @param intervals
     * @return
     */
    public int removeCoveredIntervals(int[][] intervals) {
        Arrays.sort(intervals, (a,b) -> {
            return a[0] == b[0] ? b[1] - a[1] : a[0] - b[0];
        });
        int count = 0;
        int end, pre_end = 0;
        for (int[] interval : intervals) {
            end = interval[1];
            if (end > pre_end) {
                count ++;
                pre_end = end;
            }
        }
        return count;
    }

    /**
     * 491. 递增子序列
     * 给定一个整型数组, 你的任务是找到所有该数组的递增子序列，递增子序列的长度至少是 2 。
     *
     * 示例：
     *
     * 输入：[4, 6, 7, 7]
     * 输出：[[4, 6], [4, 7], [4, 6, 7], [4, 6, 7, 7], [6, 7], [6, 7, 7], [7,7], [4,7,7]]
     * @param nums
     * @return
     */

    List<List<Integer>> relist = new ArrayList<>();
    List<Integer> path = new ArrayList<>();
    public List<List<Integer>> findSubsequences(int[] nums) {
        dfs(nums,0);
        return relist;
    }

    public void dfs(int[] nums, int idx) {
//        String r = "";
//        for (int i = 0; i < path.size(); i ++) {
//            System.out.println(r += path.get(i));
//        }
        if (path.size() > 1) {
            relist.add(new ArrayList<>(path));
        }
        //递归返回时可能存在已经添加的重复值，使用set过滤
        Set<Integer> set = new HashSet<>();
        for (int i = idx; i < nums.length; i ++) {
            //当最后一位数字比nums中的大时过滤
            if (!path.isEmpty() && path.get(path.size() - 1) > nums[i]
                || !set.add(nums[i])) {
                continue;
            }
            path.add(nums[i]);
            dfs(nums, i + 1);
            path.remove(path.size() - 1);

//            r = "re:";
//            for (int j = 0; j < path.size(); j ++) {
//                System.out.println(r += path.get(j));
//            }
        }
    }

    /**
     * 面试题 04.12. 求和路径
     * 给定一棵二叉树，其中每个节点都含有一个整数数值(该值或正或负)。设计一个算法，
     * 打印节点数值总和等于某个给定值的所有路径的数量。注意，路径不一定非得从二叉树的根节点或叶节点开始或结束，
     * 但是其方向必须向下(只能从父节点指向子节点方向)。
     *
     * 示例:
     * 给定如下二叉树，以及目标和 sum = 22，
     * @param root
     * @param sum
     * @return
     */
    public int pathSum(TreeNode root, int sum) {
        if (root == null) {
            return 0;
        }
        int a = pathSum(root.right, sum);
        int b = pathSum(root.left, sum);
        return dfs(root,sum) + a + b;
    }

    public int dfs (TreeNode root, int sum) {
        if (root == null) return 0;
        sum -= root.val;
        return (sum == 0 ? 1 : 0) + dfs(root.left,sum) + dfs(root.right, sum);
    }

    /**
     * 763. 划分字母区间
     * 字符串 S 由小写字母组成。我们要把这个字符串划分为尽可能多的片段，同一字母最多出现在一个片段中。
     * 返回一个表示每个字符串片段的长度的列表。
     * 示例：
     * 输入：S = "ababcbacadefegdehijhklij"
     * 输出：[9,7,8]
     * 解释：
     * 划分结果为 "ababcbaca", "defegde", "hijhklij"。
     * 每个字母最多出现在一个片段中。
     * 像 "ababcbacadefegde", "hijhklij" 的划分是错误的，因为划分的片段数较少。
     * @param s
     * @return
     */
    public List<Integer> partitionLabels(String s) {
        char[] chars = s.toCharArray();
        //使用map存储每个字符出现的下标，直到出现的最大下标
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0 ;i < chars.length; i ++) {
            map.put(chars[i],i);
        }

        List<Integer> list = new ArrayList();
        int start = 0 ,end = 0;
        //循环遍历字符数组，当i 等于 最大下标时为一个分段，说明之前的字符出现
        //最大的下标都在i之前
        for (int i = 0; i < chars.length; i++) {
            end = Math.max(end,map.get(chars[i]));
            if (i == end) {
                list.add(end - start + 1);
                start = end + 1;
            }
        }
        return list;
    }

    /**
     * 面试题 16.16. 部分排序
     * 给定一个整数数组，编写一个函数，找出索引m和n，只要将索引区间[m,n]的元素排好序，
     * 整个数组就是有序的。注意：n-m尽量最小，也就是说，找出符合条件的最短序列。函数返回值为[m,n]，
     * 若不存在这样的m和n（例如整个数组是有序的），请返回[-1,-1]。
     *
     * 示例：
     *
     * 输入： [1,2,4,7,10,11,7,12,6,7,16,18,19]
     * 输出： [3,9]
     * @param array
     * @return
     */
    public int[] subSort(int[] array) {
        //从左向右扫描，如果当前元素⽐它之前的最⼤的元素⼩，说明不是升序的，将当前元
        //素索引记录下来，继续遍历直到末尾。从右向左扫描，如果当前元素⽐它之后的最⼩的元素
        //⼤，说明不是降序的，将当前元素索引记录下来，继续遍历直到开始。
        int m = -1 ,n = -1;
        int len = array.length;
        int [] res = new int[2];
        if (len > 2) {
            int left = array[0];
            int right = array[len -1];
            for (int i = 0; i < len; i ++) {
                int j = len - 1 - i;
                if (array[i] < left) {
                    m = i;
                } else {
                    left = array[i];
                }

                if (array[j] > right) {
                    n = j;
                } else {
                    right = array[j];
                }

            }
        }
        res[0] = n;
        res[1] = m;
        return res;
    }

    /**
     * 687. 最长同值路径
     * 给定一个二叉树，找到最长的路径，这个路径中的每个节点具有相同值。 这条路径可以经过也可以不经过根节点。
     *
     * 注意：两个节点之间的路径长度由它们之间的边数表示。
     * @param root
     * @return
     */
    int sumpath = 0;
    public int longestUnivaluePath(TreeNode root) {
        if (root == null) {
            return 0;
        }

        dfs(root);
        return sumpath;
    }

    public int dfs(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int val = root.val;
        int a = dfs(root.left);
        int b = dfs(root.right);

        int leftPath = 0,rightPath = 0;
        if (root.left != null && val == root.left.val) {
            leftPath = a + 1;
        }
        if (root.right != null && val == root.right.val) {
            rightPath = b + 1;
        }
        sumpath = Math.max(sumpath, leftPath + rightPath);
        return Math.max(leftPath,rightPath);
    }

    /**
     * 179. 最大数
     * 给定一组非负整数 nums，重新排列每个数的顺序（每个数不可拆分）使之组成一个最大的整数。
     * 注意：输出结果可能非常大，所以你需要返回一个字符串而不是整数。
     * 示例 1：
     * 输入：nums = [10,2]
     * 输出："210"
     * 示例 2：
     *
     * 输入：nums = [3,30,34,5,9]
     * 输出："9534330"
     * @param nums
     * @return
     */
    public String largestNumber(int[] nums) {
        if (nums == null) {
            return "";
        }
        String[] strnums = new String[nums.length];
        int m = 0;
        for (int i = 0; i < nums.length; i ++) {
            if (nums[1] > 0) {
                m ++;
            }
            strnums[i] = nums[i] + "";
        }
        //如果都是0，返回0
        if (m == 0) {
            return "0";
        }
        //按相邻两个相加从大到小排序
        Arrays.sort(strnums, (a,b)-> {
            String ab = a + b, ba = b + a;
            return ba.compareTo(ab);
        });

        String res = strnums[0];
        for (int i = 1; i < strnums.length; i ++) {
            res += strnums[i];
        }

        return res;
    }

    /**
     * 347. 前 K 个高频元素
     * 给你一个整数数组 nums 和一个整数 k ，请你返回其中出现频率前 k 高的元素。你可以按 任意顺序 返回答案。
     * 示例 1:
     * 输入: nums = [1,1,1,2,2,3], k = 2
     * 输出: [1,2]
     * @param nums
     * @param k
     * @return
     */
//    public int[] topKFrequent(int[] nums, int k) {
//
//    }

    /**
     * 1647. 字符频次唯一的最小删除次数
     * 如果字符串 s 中 不存在 两个不同字符 频次 相同的情况，就称 s 是 优质字符串 。
     *
     * 给你一个字符串 s，返回使 s 成为 优质字符串 需要删除的 最小 字符数。
     *
     * 字符串中字符的 频次 是该字符在字符串中的出现次数。例如，在字符串 "aab" 中，'a' 的频次是 2，
     * 而 'b' 的频次是 1 。
     * 示例 1：
     *
     * 输入：s = "aab"
     * 输出：0
     * 解释：s 已经是优质字符串。
     * 示例 2：
     *
     * 输入：s = "aaabbbcc"
     * 输出：2
     * 解释：可以删除两个 'b' , 得到优质字符串 "aaabcc" 。
     * 另一种方式是删除一个 'b' 和一个 'c' ，得到优质字符串 "aaabbc" 。
     * @param s
     * @return
     */
    public int minDeletions(String s) {
        int len = s.length();
        if (len <= 1) {
            return 0;
        }
        char[] chars = s.toCharArray();
        Map<Character,Integer> map = new HashMap();
        for (int i = 0; i < len; i ++) {
            map.put(chars[i],map.getOrDefault(chars[i],0) + 1);
        }
        int res = 0;
        Set<Integer> set = new HashSet<>();
        Set<Character> keyset = map.keySet();

        for (char key : keyset) {
            int value = map.get(key);
            while (value > 0 && set.contains(value)) {
                value --;
                res ++;
            }
            set.add(value);
        }
        return res;
    }

    /**
     * 283. 移动零
     * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
     *
     * 示例:
     *
     * 输入: [0,1,0,3,12]
     * 输出: [1,3,12,0,0]
     * @param nums
     */
    public void moveZeroes(int[] nums) {
//        int len = nums.length;
//        if (len <= 1) {
//            return;
//        }
//        int tmp ;
//        if (len == 2) {
//            if (nums[0] == 0 && nums[1] != 0) {
//                nums[0] = nums[1];
//                nums[1] = 0;
//            }
//            return;
//        }
//        int j = len - 1;
//        for (int i = 0; i < len; i ++) {
//            if (i == j) {
//                break;
//            }
//            if (nums[i] == 0 && i < j) {
//                while ( j >= 0 && nums[j] == 0 ) {
//                    j --;
//                }
//                if (j > i) {
//                    nums[i] = nums[j];
//                    nums[j] = 0;
//                    j --;
//                }
//
//            }
//            for (int m = i + 1; m < len ; m ++) {
//                if (nums[i] > nums[m]) {
//                    if (nums[m] != 0) {
//                        tmp = nums[m];
//                        nums[m] = nums[i];
//                        nums[i] = tmp;
//                    }
//                }
//            }
//        }
        int len = nums.length, i = 0, j = 0;
        while (j < len) {
            if (nums[j] != 0) {
                nums[i ++] = nums[j];
            }
            j ++;
        }
        for (int m = i; m < len ; m ++) {
            nums[m] = 0;
        }
    }

    /**
     * 面试题 10.01. 合并排序的数组
     * 给定两个排序后的数组 A 和 B，其中 A 的末端有足够的缓冲空间容纳 B。 编写一个方法，
     * 将 B 合并入 A 并排序。
     *
     * 初始化 A 和 B 的元素数量分别为 m 和 n。
     *
     * 示例:
     *
     * 输入:
     * A = [1,2,3,0,0,0], m = 3
     * B = [2,5,6],       n = 3
     *
     * 输出: [1,2,2,3,5,6]
     * @param A
     * @param m
     * @param B
     * @param n
     */
    public void merge(int[] A, int m, int[] B, int n) {
        int pa = m - 1 ,pb = n - 1, tail = m + n - 1;
        int cur ;
        while (pa >= 0 || pb >= 0) {
            if (A[pa] > B[pb]) {
                cur = A[pa --];
            } else if (pa == -1) {
                cur = B[pb --];
            } else if (pb == -1) {
                cur = A[pa --];
            } else {
                cur = B[pb --];
            }

            A[tail --] = cur;
        }
    }

    /**
     * 561. 数组拆分 I
     * 给定长度为 2n 的整数数组 nums ，你的任务是将这些数分成 n 对, 例如 (a1, b1), (a2, b2), ..., (an, bn) ，使得从 1 到 n 的 min(ai, bi) 总和最大。
     * 返回该 最大总和 。
     * 示例 1：
     *
     * 输入：nums = [1,4,3,2]
     * 输出：4
     * 解释：所有可能的分法（忽略元素顺序）为：
     * 1. (1, 4), (2, 3) -> min(1, 4) + min(2, 3) = 1 + 2 = 3
     * 2. (1, 3), (2, 4) -> min(1, 3) + min(2, 4) = 1 + 2 = 3
     * 3. (1, 2), (3, 4) -> min(1, 2) + min(3, 4) = 1 + 3 = 4
     * 所以最大总和为 4
     * @param nums
     * @return
     */
    public int arrayPairSum(int[] nums) {
        Arrays.sort(nums);
        int res = 0 ;
        for (int i = 0; i < nums.length; i += 2) {
            res += nums[i];
        }
        return res;
    }

    /**
     * 1353. 最多可以参加的会议数目
     * 给你一个数组 events，其中 events[i] = [startDayi, endDayi] ，表示会议 i 开始于 startDayi ，
     * 结束于 endDayi 。
     *
     * 你可以在满足 startDayi <= d <= endDayi 中的任意一天 d 参加会议 i 。注意，一天只能参加一个会议。
     *
     * 请你返回你可以参加的 最大 会议数目。
     *
     * 输入：events = [[1,2],[2,3],[3,4]]
     * 输出：3
     * 解释：你可以参加所有的三个会议。
     * 安排会议的一种方案如上图。
     * 第 1 天参加第一个会议。
     * 第 2 天参加第二个会议。
     * 第 3 天参加第三个会议。
     *  提示：
     *
     * 1 <= events.length <= 10^5
     * events[i].length == 2
     * 1 <= events[i][0] <= events[i][1] <= 10^5
     *
     * @param events
     * @return
     */
    public int maxEvents(int[][] events) {
        //排序是为了下面i值可以取到正确下标
        Arrays.sort(events, (a, b)->{
            return a[0] - b[0];
        });

        PriorityQueue<Integer> queue = new PriorityQueue<>();
        int i = 0, len = events.length, res = 0;

        for (int d = 1; d <= 100000; d ++) {
            //当起始时间为同一天时，优先周期短的会议
            while (i < len && events[i][0] == d) {
                queue.offer(events[i++][1]);
            }
            //将过期的课程弹出去
            while (queue.size() > 0 && queue.peek() < d) {
                queue.poll();
            }
            //弹出优先级第一的课程，当做已参加
            if (queue.size() > 0) {
                queue.poll();
                res ++;
            }
        }

        return res;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        solution.findSubsequences(new int[]{4,6,7,7});
        int[] arrays = new int[]{2,1};
        solution.moveZeroes(arrays);
        System.out.println(arrays);
    }

}

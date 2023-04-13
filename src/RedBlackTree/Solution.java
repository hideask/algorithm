package RedBlackTree;

import com.sun.istack.internal.localization.NullLocalizable;

import java.util.*;

public class Solution {

    /**
     * 1339. 分裂二叉树的最大乘积
     * 给你一棵二叉树，它的根为 root 。请你删除 1 条边，使二叉树分裂成两棵子树，且它们子树和的乘积尽可能大。
     *
     * 由于答案可能会很大，请你将结果对 10^9 + 7 取模后再返回。
     * @param root
     * @return
     */
    long avg, ans ;
    public int maxProduct(TreeNode root) {
        avg = 0;
        ans = 0;
        int sum = countTree(root);
        avg = sum / 2;
        ans = sum;
        countTree(root);
        return (int) ((ans * (sum - ans)) % (1e9 + 7));
    }

    public int countTree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int sum = root.val + countTree(root.left) + countTree(root.right);
        if (Math.abs(sum - avg) < Math.abs(ans - avg)) {
            ans = sum;
        }
        return sum;
    }

    /**
     * 971. 翻转二叉树以匹配先序遍历
     * 给你一棵二叉树的根节点 root ，树中有 n 个节点，每个节点都有一个不同于其他节点且处于 1 到 n 之间的值。
     *
     * 另给你一个由 n 个值组成的行程序列 voyage ，表示 预期 的二叉树 先序遍历 结果。
     *
     * 通过交换节点的左右子树，可以 翻转 该二叉树中的任意节点。例，翻转节点 1 的效果如下：
     *
     *
     * 请翻转 最少 的树中节点，使二叉树的 先序遍历 与预期的遍历行程 voyage 相匹配 。
     *
     * 如果可以，则返回 翻转的 所有节点的值的列表。你可以按任何顺序返回答案。如果不能，则返回列表 [-1]。
     * @param root
     * @param voyage
     * @return
     */
    int idx;
    public List<Integer> flipMatchVoyage(TreeNode root, int[] voyage) {
        List<Integer> res = new ArrayList<>();
        idx = 0;
        flipMatchVoyage(root,voyage,res);
        return res;
    }

    public boolean flipMatchVoyage(TreeNode root, int[] voyage, List<Integer> res) {
        if (root == null) {
            return true;
        }
        if (root.val != voyage[idx]) {
            res.clear();
            res.add(-1);
            return false;
        }
        idx ++ ;
        if (idx + 1 == voyage.length) {
            return true;
        }

        if (root.left != null && root.left.val != voyage[idx]) {
            swap(root);
            res.add(root.val);
        }
        if (!flipMatchVoyage(root.left,voyage,res)) {
            return false;
        }
        if (!flipMatchVoyage(root.right,voyage,res)) {
            return false;
        }
        return true;
    }

    public void swap(TreeNode root) {
        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;
    }

    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }
    /**
     * 117. 填充每个节点的下一个右侧节点指针 II
     * 给定一个二叉树
     *
     * struct Node {
     *   int val;
     *   Node *left;
     *   Node *right;
     *   Node *next;
     * }
     * 填充它的每个 next 指针，让这个指针指向其下一个右侧节点。如果找不到下一个右侧节点，则将 next 指针设置为 NULL。
     *
     * 初始状态下，所有 next 指针都被设置为 NULL。
     *
     *
     * @param root
     * @return
     */
    public Node connect(Node root) {
        if (root == null) {
            return null;
        }
        Node head = root,pre = null,newhead = null;

        while (head != null) {
            if (head.left != null) {
                if (pre != null) {
                    pre.next = head.left;
                }
                pre = head.left;
            }

            if (newhead == null) {
                newhead = head.left;
            }

            if (head.right != null) {
                if (pre != null) {
                    pre.next = head.right;
                }
                pre = head.right;
            }

            if (newhead == null) {
                newhead = head.right;
            }

            head = head.next;
        }
        connect(newhead);
        return root;
    }

    /**
     * 剑指 Offer II 053. 二叉搜索树中的中序后继
     * 给定一棵二叉搜索树和其中的一个节点 p ，找到该节点在树中的中序后继。如果节点没有中序后继，请返回 null 。
     *
     * 节点 p 的后继是值比 p.val 大的节点中键值最小的节点，即按中序遍历的顺序节点 p 的下一个节点。
     *
     *
     * @param root
     * @param p
     * @return
     */
    TreeNode pre,next;
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        pre = null;
        next = null;
        inorderSuccessorDfs(root,p);
        return next;
    }

    public boolean inorderSuccessorDfs(TreeNode root, TreeNode p) {
        if (root == null) {
            return false;
        }
        if (inorderSuccessorDfs(root.left,p)) {
            return true;
        }
        if (pre != null && p.val == pre.val) {
            next = root;
            return true;
        }

        pre = root;
        if (inorderSuccessorDfs(root.right,p)) {
            return true;
        }
        return false;
    }

    /**
     * 47. 全排列 II
     * 给定一个可包含重复数字的序列 nums ，按任意顺序 返回所有不重复的全排列。
     * 示例 1：
     *
     * 输入：nums = [1,1,2]
     * 输出：
     * [[1,1,2],
     *  [1,2,1],
     *  [2,1,1]]
     * @param nums
     * @return
     */
    List<List<Integer>> res;
    Deque<Integer> path;
    boolean[] used;
    public List<List<Integer>> permuteUnique(int[] nums) {
        res = new ArrayList<>();
        if (nums.length == 0) {
            return res;
        }
        Arrays.sort(nums);
        path = new ArrayDeque<>();
        int length = nums.length;
        used = new boolean[length];
        dfs(nums,length,0);

        return res;
    }

    public void dfs(int[] nums,int length, int dept) {
        if (dept == length) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = 0; i < length; i ++) {
            if (used[i]) {
                continue;
            }
            if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) {
                continue;
            }
            path.addLast(nums[i]);
            used[i] = true;
            dfs(nums,length,dept + 1);
            path.removeLast();
            used[i] = false;
        }
    }

    /**
     * 78. 子集
     * 给你一个整数数组 nums ，数组中的元素 互不相同 。返回该数组所有可能的子集（幂集）。
     *
     * 解集 不能 包含重复的子集。你可以按 任意顺序 返回解集。
     * @param nums
     * @return
     */
    public List<List<Integer>> subsets(int[] nums) {
        //使用二进制存储每个数是否用过
        Map<Integer, Integer> map = new HashMap<>();
        //最多10个数，使用二进制位标记是否使用过，使用过为1
        for (int i = 0,j = 1; i < 10; i ++ , j *= 2) {
            map.put(j,i);
        }

        int length = nums.length;
        List<List<Integer>> res = new ArrayList<>();

        //
        for (int i = 0, j = (1 << length); i < j ; i ++ ) {
            List<Integer> ans = new ArrayList<>();
            int val = i;
            //val & (-val) : 找到当前数⼆进制下最后⼀个1
            //val &= (val - 1) : 去掉当前数⼆进制下最后⼀个1
            while (val != 0) {
                //根据每⼀个1 获取对应的数组索引，最后获取数组元素
                ans.add(nums[map.get(val & (-val))]);
                //删除使⽤过的1
                val &= (val - 1);
            }
            res.add(ans);
        }
        return res;
    }

    /**
     * 220. 存在重复元素 III
     * 给你一个整数数组 nums 和两个整数 k 和 t 。请你判断是否存在 两个不同下标 i 和 j，使得 abs(nums[i] - nums[j]) <= t ，同时又满足 abs(i - j) <= k 。
     *
     * 如果存在则返回 true，不存在返回 false。
     * @param nums
     * @param k
     * @param t
     * @return
     */
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        int lenth = nums.length;
        //存储num出现的次数
        TreeMap<Long,Integer> map = new TreeMap<>();
        //重新赋值，防止超限
        long[] arrs = new long[nums.length];
        for (int i = 0; i < nums.length; i ++) {
            arrs[i] = nums[i];
        }

        for (int i = 0; i < nums.length; i ++) {
            if (i > k) {
                //abs(i - j) <= k 区间中有k + 1个值
                delMap(map, arrs[i - k - 1] );
            }
            //往map中插入arrs[i] - t - 1 和 arrs[i] + t + 1,代表区间上下限，根据红黑树性质
            map.put(arrs[i] - t - 1, map.getOrDefault(arrs[i] - t - 1, 0) + 1);
            map.put(arrs[i] + t + 1, map.getOrDefault(arrs[i] + t + 1, 0) + 1);
            long key = map.higherKey(arrs[i] - t - 1);
            if (key != arrs[i] + t + 1) {
                return true;
            }
            delMap(map,arrs[i] - t - 1);
            delMap(map,arrs[i] + t + 1);
            map.put(arrs[i], map.getOrDefault(arrs[i], 0) + 1);
        }
        return false;
    }

    public void delMap(TreeMap<Long,Integer> map, long m) {
        map.put(m, map.get(m) - 1);
        if (map.get(m) == 0) {
            map.remove(m);
        }
    }

    /**
     * 41. 缺失的第一个正数
     * 给你一个未排序的整数数组 nums ，请你找出其中没有出现的最小的正整数。
     *
     * 请你实现时间复杂度为 O(n) 并且只使用常数级别额外空间的解决方案。
     * @param nums
     * @return
     */
    public int firstMissingPositive(int[] nums) {
        int length = nums.length;
        for (int i = 0; i < length; i ++) {
            while (nums[i] != i + 1) {
                if (nums[i] <= 0 || nums[i] > length) {
                    break;
                }
                int idx = nums[i] - 1;
                if (nums[i] == nums[idx]) {
                    break;
                }
                int m = nums[i];
                nums[i] = nums[idx];
                nums[idx] = m;
            }
        }

        int res = 0;
        while (res < length && nums[res] == res + 1) {
            res ++ ;
        }
        return res + 1;
    }

    /**
     * 99. 恢复二叉搜索树
     * 给你二叉搜索树的根节点 root ，该树中的两个节点被错误地交换。请在不改变其结构的情况下，恢复这棵树。
     *
     * 进阶：使用 O(n) 空间复杂度的解法很容易实现。你能想出一个只使用常数空间的解决方案吗？
     */
    TreeNode prenode;
    TreeNode node1;
    TreeNode node2;
    public void recoverTreeDfs(TreeNode root) {
        if (root == null) {
            return;
        }
        recoverTreeDfs(root.left);
        if (prenode != null && prenode.val > root.val) {
            if (node1 == null) {
                node1 = prenode;
            }
            node2 = root;
        }
        prenode = root;
        recoverTreeDfs(root.right);

    }

    public void recoverTree(TreeNode root) {
        recoverTreeDfs(root);
        if (node1 != null && node2 != null) {
            int tmp = node1.val;
            node1.val = node2.val;
            node2.val = tmp;
        }
    }

    /**
     * 653. 两数之和 IV - 输入 BST
     * 给定一个二叉搜索树 root 和一个目标结果 k，如果 BST 中存在两个元素且它们的和等于给定的目标结果，则返回 true
     * @param root
     * @param k
     * @return
     */
    Set<Integer> set = new HashSet<>();
    public boolean findTarget(TreeNode root, int k) {
        if (root == null) {
            return false;
        }

        if (findTarget(root.left,k)) {
            return true;
        }

        if (set.contains(root.val)) {
            return true;
        }
        set.add(k - root.val);


        if (findTarget(root.right,k)) {
            return true;
        }

        return false;
    }

    /**
     * 204. 计数质数
     * 统计所有小于非负整数 n 的质数的数量。
     * @param n
     * @return
     */
    public int countPrimes(int n) {
        if (n <= 1) {
            return 0;
        }
        //素数塞算法
        int[] nums = new int[n];
        Arrays.fill(nums,0);
        for (int i = 2; i * i < n; i ++) {
            if (nums[i] != 0) {
                continue;
            }
            //过滤i的倍数
            for (int j = 2 * i;j < n; j += i) {
                nums[j] = 1;
            }
        }
        int cnt = 0;
        for (int i = 2 ; i < n ; i ++) {
            if (nums[i] == 0) {
                cnt ++;
            }
        }
        return cnt;
    }

    /**
     * 504. 七进制数
     * 给定一个整数 num，将其转化为 7 进制，并以字符串形式输出
     * @param num
     * @return
     */
    public String convertToBase7(int num) {
        if (num < 7 && num > -7) {
            return String.valueOf(num);
        }
        int flag = 0;
        if (num < 0) {
            flag = 1;
        }
        num = Math.abs(num);
        int y = num % 7;
        int c = num / 7;
        String res = String.valueOf(y);

        while (c / 7 != 0 && c >= 7) {
            y = c % 7;
            c = c / 7;
            res += y;
        }
        if (c > 0) {
            res += c;
        }
        String restr = "";
        if (flag == 1) {
            restr = "-";
        }
        for (int i = res.length() - 1; i >= 0 ;i --) {
            restr += res.charAt(i);
        }

        return restr;
    }

    public String convert1ToBase7(int num) {
        if (num == 0) {
            return "0";
        }
        int flag = 0;
        if (num < 0) {
            flag = 1;
        }
        String res = "";
        num = Math.abs(num);
        while (num != 0) {
            res += num % 7;
            num /= 7;
        }
        String restr = "";
        if (flag == 1) {
            restr = "-";
        }
        for (int i = res.length() - 1; i >= 0 ;i --) {
            restr += res.charAt(i);
        }

        return restr;
    }

    /**
     * 461. 汉明距离
     * 两个整数之间的 汉明距离 指的是这两个数字 对应二进制位不同的位置 的数目。
     *
     * 给你两个整数 x 和 y，计算并返回它们之间的汉明距离。
     * @param x
     * @param y
     * @return
     */
    public int hammingDistance(int x, int y) {
        x ^= y;
        int cnt = 0;
        while (x != 0) {
            x &= (x - 1);
            cnt ++;
        }
        return cnt;
    }

    /**
     * 528. 按权重随机选择
     * 给定一个正整数数组 w ，其中 w[i] 代表下标 i 的权重（下标从 0 开始），请写一个函数 pickIndex ，
     * 它可以随机地获取下标 i，选取下标 i 的概率与 w[i] 成正比。
     *
     * 例如，对于 w = [1, 3]，挑选下标 0 的概率为 1 / (1 + 3) = 0.25 （即，25%），
     * 而选取下标 1 的概率为 3 / (1 + 3) = 0.75（即，75%）。
     *
     * 也就是说，选取下标 i 的概率为 w[i] / sum(w) 。
     * @param w
     */
    int n ;
    int[] sum ;
    public Solution(int[] w) {
        //求前缀和数组
        sum = w;
        for (int i = 1; i < w.length; i ++) {
            sum[i] = sum[i] + sum[i - 1];
        }
        //获取最大概率
        n = sum[sum.length - 1];
    }

    public int pickIndex() {
        //随机一个数
        Random random = new Random();
        int x = random.nextInt(n);
        int p = 0,q = sum.length - 1;
        //采用二分查找，在哪个概率区间，例如[0,1),[1,4),[4,9)
        while (p < q) {
            int mid = (p + q) >> 1;
            //如果相等，会进入下一个区间
            if (sum[mid] <= x) {
                p = mid + 1;
            } else {
                q = mid;
            }
        }
        return p ;
    }

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
    ListNode head;
    public Solution(ListNode head) {
        this.head = head;
    }

    /** Returns a random node's value. */
    public int getRandom() {
        int ans = head.val;
        Random random = new Random();
        ListNode node = head.next;
        for (int i = 2; node != null; i ++ ,node = node.next) {
            int d = random.nextInt(i);
            if (d < 1) {
                ans = node.val;
            }
        }
        return ans;
    }

    /**
     * 59. 螺旋矩阵 II
     * 给你一个正整数 n ，生成一个包含 1 到 n2 所有元素，且元素按顺时针顺序螺旋排列的 n x n 正方形矩阵 matrix 。
     * @param n
     * @return
     */
    public int[][] generateMatrix(int n) {
        int[][] arr = new int[n][n];
        int row = 0,col = 0;//当前坐标
        int x = n, y = n;//边界
        int num = 1;//从1开始
        int index = 0;//每行输出个数
        int count = n * n;//输出总个数
        int flag = 1;//输出方向
        while (x != 0 && y != 0) {
            index = y;
            while (index -- != 0) {
                arr[row][col] = num;
                num ++;
                col += flag;
            }
            row += flag;
            col -= flag;
            x --;
            if (x == 0) {
                break;
            }
            index = x;
            while (index -- != 0) {
                arr[row][col] = num;
                num ++;
                row += flag;
            }

            row -= flag;
            col -= flag;
            y --;
            flag *= -1;
        }
        return arr;
    }

    /**
     * 462. 最少移动次数使数组元素相等 II
     * 给定一个非空整数数组，找到使所有数组元素相等所需的最小移动数，
     * 其中每次移动可将选定的一个元素加1或减1。 您可以假设数组的长度最多为10000。
     *
     * 例如:
     * @param nums
     * @return
     */
    public int minMoves2(int[] nums) {
        return 1;
    }



    public static void main(String[] args) {
        Solution solution = new Solution(new int[]{1});
        int i = solution.pickIndex();
    }
}

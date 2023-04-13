package tree;

import leetcode.ListNode;

import java.math.BigDecimal;
import java.util.*;

/**
 * description:Solution
 * create user: songj
 * date : 2021/4/5 14:06
 */
public class Solution {
    /**
     * 144. 二叉树的前序遍历
     * 给你二叉树的根节点 root ，返回它节点值的 前序 遍历。
     * 递归方式
     *
     * @param root
     * @return
     */
    public List<Integer> preorderTraversal(TreeNode root) {

        List<Integer> res = new ArrayList();
        preorderTraversal(root, res);
        return res;
    }

    public void preorderTraversal(TreeNode root, List res) {
        if (root == null) {
            return;
        }
        //放入根节点
        res.add(root.val);
        //放入左子树
        preorderTraversal(root.left, res);
        //放入右子树
        preorderTraversal(root.right, res);
    }

    /**
     * 迭代方式
     *
     * @param root
     * @return
     */
    public List<Integer> preorderTraversal1(TreeNode root) {
        List<Integer> res = new ArrayList();
        if (root == null) {
            return res;
        }
        Deque<TreeNode> stack = new LinkedList();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            res.add(node.val);
            //入栈顺序右左，出栈顺序左右
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
        return res;
    }

    class Node {
        public int val;
        public List<Node> children;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    /**
     * 589. N 叉树的前序遍历
     * 给定一个 N 叉树，返回其节点值的 前序遍历 。
     * <p>
     * N 叉树 在输入中按层序遍历进行序列化表示，每组子节点由空值 null 分隔（请参见示例）。
     *
     * @param root
     * @return
     */
    public List<Integer> preorder(Node root) {
        List res = new ArrayList();
        preorder(root, res);
        return res;
    }

    public void preorder(Node root, List list) {
        if (root == null) {
            return;
        }
        list.add(root.val);
        for (Node node : root.children) {
            preorder(node, list);
        }
    }

    /**
     * 迭代法
     *
     * @param root
     * @return
     */
    public List<Integer> preorder1(Node root) {
        List res = new ArrayList();
        if (root == null) {
            return res;
        }
        Deque<Node> stack = new LinkedList();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            res.add(node.val);
            for (int i = node.children.size() - 1; i >= 0; i--) {
                stack.push(node.children.get(i));
            }
        }
        return res;
    }

    /**
     * 226. 翻转二叉树
     * 翻转一棵二叉树。
     */
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode right = root.right;
        root.right = root.left;
        root.left = right;
        invertTree(root.left);
        invertTree(root.right);
        return root;
    }

    /**
     * 剑指 Offer 32 - II. 从上到下打印二叉树 II
     * 从上到下按层打印二叉树，同一层的节点按从左到右的顺序打印，每一层打印到一行。
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> list = new ArrayList<>();
        getFloorRes(root, 0, list);
        return list;
    }

    public void getFloorRes(TreeNode root, int k, List<List<Integer>> list) {
        if (root == null) {
            return;
        }
        if (k == list.size()) {
            list.add(new ArrayList<>());
        }
        list.get(k).add(root.val);
        getFloorRes(root.left, k + 1, list);
        getFloorRes(root.right, k + 1, list);
    }

    /**
     * 107. 二叉树的层序遍历 II
     * 给定一个二叉树，返回其节点值自底向上的层序遍历。 （即按从叶子节点所在层到根节点所在的层，逐层从左向右遍历）
     */
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> list = new ArrayList();
        getFloorRes1(root, 0, list);
        Collections.reverse(list);
        return list;
    }

    /**
     * 递归定义：获取每层的数据
     * 递归实现：将值放入第K层
     * 边界条件：为空退出
     * @param root
     * @param k
     * @param list
     */
    public void getFloorRes1(TreeNode root, int k, List<List<Integer>> list) {
        if (root == null) {
            return;
        }
        if (k == list.size()) {
            list.add(new ArrayList<>());
        }
        list.get(k).add(root.val);
        getFloorRes1(root.left, k + 1, list);
        getFloorRes1(root.right, k + 1, list);
    }

    /**
     * 103. 二叉树的锯齿形层序遍历
     * 给定一个二叉树，返回其节点值的锯齿形层序遍历。（即先从左往右，再从右往左进行下一层遍历，
     * 以此类推，层与层之间交替进行）。
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> list = new ArrayList<>();
        getFloorRes2(root, 0, list);
        for (int i = 0; i < list.size(); i++) {
            if ((i + 1) % 2 == 0) {
                Collections.reverse(list.get(i));
            }
        }
        return list;
    }

    public void getFloorRes2(TreeNode root, int k, List<List<Integer>> list) {
        if (root == null) {
            return;
        }
        if (k == list.size()) {
            list.add(new ArrayList<>());
        }
        list.get(k).add(root.val);

        getFloorRes2(root.left, k + 1, list);
        getFloorRes2(root.right, k + 1, list);
    }

    /**
     * 110. 平衡二叉树
     * 给定一个二叉树，判断它是否是高度平衡的二叉树。
     * <p>
     * 本题中，一棵高度平衡二叉树定义为：
     * <p>
     * 一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1 。
     */
    public boolean isBalanced(TreeNode root) {
        return getHeight(root) >= 0;
    }

    /**
     * 递归定义：获取高度
     * @param root
     * @return
     */
    public int getHeight(TreeNode root) {
        //边界条件
        if (root == null) {
            return 0;
        }
        //获取左子树高度
        int l = getHeight(root.left);
        //获取右子树高度
        int r = getHeight(root.right);
        //如果左右子树高度小于0，则返回-1
        if (l < 0 || r < 0) {
            return -1;
        }
        //如果左右子树高度差大于1，则返回-1
        if (Math.abs(l - r) > 1) {
            return -1;
        }
        //返回最大值
        return Math.max(l, r) + 1;
    }

    public void test1() {
        BigDecimal res = BigDecimal.ZERO;
        Deque<Integer> stack = new LinkedList();
        Queue<Integer> queue = new LinkedList();
        for (int i = 1; i <= 10000; i++) {
            if (i % 2 == 0) {
                queue.offer(i);
            } else {
                stack.push(i);
            }
        }
        while (!stack.isEmpty()) {
            res = res.add(new BigDecimal(stack.pop()).multiply(new BigDecimal(queue.poll())));
        }
        res = res.divide(new BigDecimal(18), BigDecimal.ROUND_DOWN, 0);
        System.out.println(res.toString());
    }

    /**
     * 112. 路径总和
     * 给你二叉树的根节点 root 和一个表示目标和的整数 targetSum ，判断该树中是否存在 根节点到叶子节点 的路径，
     * 这条路径上所有节点值相加等于目标和 targetSum 。
     *
     * 叶子节点 是指没有子节点的节点。
     * @return
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        if (root.right == null && root.left == null) {
            return root.val == targetSum;
        }
        return hasPathSum(root.right, targetSum - root.val) ||
                hasPathSum(root.left,targetSum - root.val);
    }

    /**
     * 105. 从前序与中序遍历序列构造二叉树
     * 根据一棵树的前序遍历与中序遍历构造二叉树。
     *
     * 注意:
     * 你可以假设树中没有重复的元素
     * @param preorder
     * @param inorder
     * @return
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder.length == 0) {
            return null;
        }
        //获取根节点在中序遍历中的位置
        int indexroot = 0;
        while (preorder[0] != inorder[indexroot]) {
            indexroot++;
        }
        //定义前序和中序左右子树长度
        int[] preleft = new int[indexroot],
                preright = new int[inorder.length - indexroot - 1],
                inleft = new int[indexroot],
                inright = new int[inorder.length - indexroot - 1];
        //封装左子树
        for (int i = 0; i <indexroot; i++) {
            preleft[i] = preorder[i + 1];
            inleft[i] = inorder[i];
        }

        //封装右子树
        for (int i = indexroot + 1, j = 0;i < inorder.length;i ++, j++) {
            preright[j] = preorder[i];
            inright[j] = inorder[i];
        }

        TreeNode root = new TreeNode(preorder[0]);
        root.left = buildTree(preleft,inleft);
        root.right = buildTree(preright,inright);
        return root;
    }

    /**
     * 222. 完全二叉树的节点个数
     * 给你一棵 完全二叉树 的根节点 root ，求出该树的节点个数。
     *
     * 完全二叉树 的定义如下：在完全二叉树中，除了最底层节点可能没填满外，其余每层节点数都达到最大值，
     * 并且最下面一层的节点都集中在该层最左边的若干位置。若最底层为第 h 层，则该层包含 1~ 2h 个节点。
     * @param root
     * @return
     */
    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return countNodes(root.left) + countNodes(root.right) + 1;
    }

    /**
     * 剑指 Offer 54. 二叉搜索树的第k大节点
     * 给定一棵二叉搜索树，请找出其中第k大的节点。
     * @param
     */
    public int kthLargest(TreeNode root, int k) {
        int n = getCount(root.right);
        //如果右子树的节点个数大于等于k,说明第K大的节点在右边，直接求右边的节点
        if (n >= k) {
            return kthLargest(root.right,k);
        }
        //如果n+1 等于k,说明第K大的节点是根节点
        if (n + 1 == k) {
            return root.val;
        }
        //如果都不是，则在左子树
        return kthLargest(root.left,k - n -1);
    }

    /**
     * 获取树的节点个数
     * @param root
     * @return
     */
    public int getCount(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return getCount(root.right) + getCount(root.left) + 1;
    }

    int res ,K;

    /**
     * 从右遍历找到第K大的值
     * @param root
     * @param k
     * @return
     */
    public int kthLargest2(TreeNode root, int k) {
        K = k;
        getrightk(root);
        return res;
    }

    public void getrightk(TreeNode root) {
        if (root == null) {
            return;
        }
        getrightk(root.right);
        if (K == 0) {
            return;
        }
        if (--K == 0) {
            res = root.val;
        }
        getrightk(root.left);
    }


    /**
     * 剑指 Offer 26. 树的子结构
     * 输入两棵二叉树A和B，判断B是不是A的子结构。(约定空树不是任意一个树的子结构)
     *
     * B是A的子结构， 即 A中有出现和B相同的结构和节点值
     */
    public boolean isSubStructure(TreeNode A, TreeNode B) {
        if (B == null || A == null) {
            return false;
        }
        if (A.val == B.val && isMatch(A,B)) {
            return true;
        }
        return isSubStructure(A.right,B) || isSubStructure(A.left,B);
    }

    public boolean isMatch(TreeNode A, TreeNode B) {
        if (B == null) {
            return true;
        }
        if (A == null) {
            return false;
        }
        return A.val == B.val && isMatch(A.right,B.right) && isMatch(A.left,B.left);
    }

    /**
     * 662. 二叉树最大宽度
     * 给定一个二叉树，编写一个函数来获取这个树的最大宽度。树的宽度是所有层中的最大宽度。
     * 这个二叉树与满二叉树（full binary tree）结构相同，但一些节点为空。
     *
     * 每一层的宽度被定义为两个端点（该层最左和最右的非空节点，两端点间的null节点也计入长度）之间的长度。
     */
    public int widthOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int res = 0;
        //定义一个队列
        Queue<Pair> queue = new LinkedList();
        //第一层根节点入队
        queue.offer(new Pair(root,0));
        while (!queue.isEmpty()) {
            //代表队列的大小
            int count = queue.size();
            //栈顶元素，确定每层最左值最小值，为0
            int left = queue.peek().number;
            int right = 0;
            System.out.println();
            //循环放入下一层元素，当循环完毕，上一层元素全部弹出
            for (int i = 0;i <count;i++) {
                Pair tmp = queue.poll();
                //和最左值相差多少，减去left是为了防止数值超限
                right = tmp.number - left;
                if (tmp.treeNode.left != null) {
                    queue.offer(new Pair(tmp.treeNode.left, 2* right));
                }
                if (tmp.treeNode.right != null) {
                    queue.offer(new Pair(tmp.treeNode.right, 2* right + 1));
                }
            }
            res = Math.max(res,right +1);
        }
        return res;
    }

    /**
     * 定义一个类，封装node及number，number是重新赋予一个值，从0开始
     */
    class Pair {
        TreeNode treeNode;
        int number;
        public Pair (TreeNode treeNode,int number) {
            this.treeNode = treeNode;
            this.number = number;
        }
    }

    /**
     * 104. 二叉树的最大深度
     * 给定一个二叉树，找出其最大深度。
     *
     * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
     *
     * 说明: 叶子节点是指没有子节点的节点。
     *
     * 示例：
     * 给定二叉树 [3,9,20,null,null,15,7]，
     *
     *     3
     *    / \
     *   9  20
     *     /  \
     *    15   7
     * 返回它的最大深度 3
     * @param root
     * @return
     */
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int l = maxDepth(root.left);
        int r = maxDepth(root.right);
        return Math.max(l,r) + 1;
    }

    /**
     * 面试题 04.05. 合法二叉搜索树
     * 实现一个函数，检查一棵二叉树是否为二叉搜索树。
     * @param root
     * @return
     */
    TreeNode pre;
    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }
        boolean l = isValidBST(root.left);
        if (pre != null && pre.val >= root.val) {
            return false;
        }
        pre = root;
        boolean r = isValidBST(root.right);
        return l && r;
    }

    /**
     * 230. 二叉搜索树中第K小的元素
     * 给定一个二叉搜索树的根节点 root ，和一个整数 k ，
     * 请你设计一个算法查找其中第 k 个最小元素（从 1 开始计数）。
     * @param root
     * @param k
     * @return
     */
    public int kthSmallest(TreeNode root, int k) {
        List<Integer> list = new ArrayList<>();
        getList(root,list);
        return list.get(k - 1);
    }

    public void getList(TreeNode root , List<Integer> list) {
        if (root == null) {
            return;
        }
        //二叉树左边最小
        getList(root.left,list);
        //根节点
        list.add(root.val);
        //遍历右边节点
        getList(root.right,list);
    }

    /**
     * 199. 二叉树的右视图
     * 给定一棵二叉树，想象自己站在它的右侧，按照从顶部到底部的顺序，返回从右侧所能看到的节点值。
     * @param root
     * @return
     */
    public List<Integer> rightSideView(TreeNode root) {
        Deque<TreeNode> queue = new LinkedList<>();
        List<Integer> res = new ArrayList<>();
        queue.offer(root);
        if (root == null) {
            return res;
        }
        int size;
        TreeNode poll;
        while (!queue.isEmpty()) {
            size = queue.size();
            for (int i = 0;i < size ;i++) {
                poll = queue.poll();
                if (poll.left != null) {
                    queue.offer(poll.left);
                }
                if (poll.right != null) {
                    queue.offer(poll.right);
                }
                if (i == size - 1) {
                    res.add(poll.val);
                }
            }
        }
        return res;
    }

    /**
     * 100. 相同的树
     * 给你两棵二叉树的根节点 p 和 q ，编写一个函数来检验这两棵树是否相同。
     *
     * 如果两个树在结构上相同，并且节点具有相同的值，则认为它们是相同的。
     * @param p
     * @param q
     * @return
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        }
        if (p == null || q == null) {
            return false;
        }
        if (p.val != q.val) {
            return false;
        }
        return isSameTree(p.left,q.left) && isSameTree(p.right,q.right);
    }

    /**
     * 101. 对称二叉树
     * 给定一个二叉树，检查它是否是镜像对称的。
     *
     *
     *
     * 例如，二叉树 [1,2,2,3,4,4,3] 是对称的。
     * @param root
     * @return
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return isSymmetric(root.left,root.right);
    }

    public boolean isSymmetric(TreeNode root1,TreeNode root2) {
        if (root1 == null && root2 == null) {
            return true;
        }
        if (root1 == null || root2 == null) {
            return false;
        }
        if (root1.val != root2.val) {
            return false;
        }
        return isSymmetric(root1.left,root2.right) && isSymmetric(root1.right,root2.left);
    }


    /**
     * 235. 二叉搜索树的最近公共祖先
     * 给定一个二叉搜索树, 找到该树中两个指定节点的最近公共祖先。
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        //利用二叉搜索树的特性
        int val ;
        while (true) {
            val = root.val;
            //说明在左子树
            if (val > p.val && val > q.val) {
                root = root.left;
            } else if (val < p.val && val < q.val) {
                //说明在右子树
                root = root.right;
            } else {
                //说明在分叉节点或者就是节点本身
                break;
            }
        }
        return root;
    }

    /**
     * 347. 前 K 个高频元素
     * 给你一个整数数组 nums 和一个整数 k ，请你返回其中出现频率前 k 高的元素。你可以按 任意顺序 返回答案。
     * @param nums
     * @param k
     * @return
     */
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer,Integer> map = new HashMap();
        for (int i : nums) {
            map.put(i,map.getOrDefault(i,0) + 1);
        }
        PriorityQueue<Integer> queue = new PriorityQueue<>(
                new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        return map.get(o1) - map.get(o2);
                    }
                }
        );

        for (int num: map.keySet()) {
            queue.offer(num);
            if (queue.size() > k) {
                queue.poll();
            }
        }
        return queue.stream().mapToInt(i ->i).toArray();

    }

    /**
     * 451. 根据字符出现频率排序
     * 给定一个字符串，请将字符串里的字符按照出现的频率降序排列。
     *
     * 示例 1:
     *
     * 输入:
     * "tree"
     *
     * 输出:
     * "eert"
     *
     * 解释:
     * 'e'出现两次，'r'和't'都只出现一次。
     * 因此'e'必须出现在'r'和't'之前。此外，"eetr"也是一个有效的答案。
     * @param s
     * @return
     */
    public String frequencySort(String s) {
        char[] chars = s.toCharArray();
        Map<Character,Integer> map = new HashMap();
        for (char i : chars) {
            map.put(i,map.getOrDefault(i,0) + 1);
        }
        PriorityQueue<Character> queue = new PriorityQueue<>(
                new Comparator<Character>() {
                    @Override
                    public int compare(Character o1, Character o2) {
                        return map.get(o2) - map.get(o1);
                    }
                }
        );

        for (char c: map.keySet()) {
            queue.offer(c);
        }

        StringBuilder sb = new StringBuilder();
        while (!queue.isEmpty()) {
            char c = queue.poll();
            for (int i = 0;i < map.get(c);i ++) {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * 124. 二叉树中的最大路径和
     * 路径 被定义为一条从树中任意节点出发，沿父节点-子节点连接，达到任意节点的序列。同一个节点在一条路径序列中 至多出现一次 。该路径 至少包含一个 节点，且不一定经过根节点。
     *
     * 路径和 是路径中各节点值的总和。
     *
     * 给你一个二叉树的根节点 root ，返回其 最大路径和 。
     * @param root
     * @return
     */
    int maxSum = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        maxPathSum1(root);
        return maxSum;
    }

    public int maxPathSum1(TreeNode root) {
        if (root == null) {
            return 0;
        }
        //获取左节点最大路径
        int l = Math.max(maxPathSum1(root.left),0);
        //获取右节点最大路径
        int r = Math.max(maxPathSum1(root.right),0);
        //更新最大路径和
        maxSum = Math.max(maxSum,root.val + l + r);
        //返回节点的最大贡献值
        return root.val + Math.max(l,r);
    }

    /**
     * 95. 不同的二叉搜索树 II
     * 给你一个整数 n ，请你生成并返回所有由 n 个节点组成且节点值从 1 到 n 互不相同的不同 二叉搜索树 。可以按 任意顺序 返回答案。
     * @param n
     * @return
     */
    public List<TreeNode> generateTrees(int n) {
        return generateTrees(1,n);
    }

    public List<TreeNode> generateTrees(int l,int r) {
        List<TreeNode> list = new LinkedList<>();
        if (l > r) {
            list.add(null);
            return list;
        }
        for (int i = l;i <= r; i ++) {
            List<TreeNode> left = generateTrees(l, i - 1);
            List<TreeNode> right = generateTrees(i + 1, r);
            for (TreeNode lf : left) {
                for (TreeNode rt : right) {
                    list.add(new TreeNode(i,lf,rt));
                }
            }

        }
        return list;
    }

    /**
     * 1302. 层数最深叶子节点的和
     * 给你一棵二叉树的根节点 root ，请你返回 层数最深的叶子节点的和 。
     */
    int maxk = 0;
    int num = 0;
    public int deepestLeavesSum(TreeNode root) {
        deepestLeavesSum(root,1);

        return num;
    }
    public void deepestLeavesSum(TreeNode root,int k) {
        if (root == null) {
            return ;
        }
        //当前深度等于最大深度时，将值相加
        if (k == maxk) {
            num += root.val;
        }
        //当深度大于最大深度时，重新给最大深度、值和赋值
        if (k > maxk) {
            maxk = k;
            num = root.val;
        }
        //左右两边分别递归
        deepestLeavesSum(root.left, k+1);
        deepestLeavesSum(root.right, k+1);

    }

    /**
     * 面试题 04.08. 首个共同祖先
     * 设计并实现一个算法，找出二叉树中某两个节点的第一个共同祖先。
     * 不得将其他的节点存储在另外的数据结构中。注意：这不一定是二叉搜索树。
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor1(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }
        if (p == root || q == root) {//说明就是根节点
            return root;
        }
        //递归获取左边的首个共同祖先
        TreeNode left = lowestCommonAncestor1(root.left, p, q);
        //递归获取右边的首个共同祖先
        TreeNode right = lowestCommonAncestor1(root.right, p , q);
        //如果左右都有共同祖先，说明p,q分布在左右子树，则他们共同祖先为root
        if (left != null && right != null) {
            return root;
        }
        //如果左节点有，右节点没有，说明p、q都分布在左子树，则返回left
        if (left != null && right == null) {
            return left;
        }
        //最后情况是分布在右子树
        return right;
    }


    public static void main(String[] args) {
        Solution solution = new Solution();
//        solution.test1();

//        TreeNode node1 = new TreeNode(1);
//        TreeNode node2 = new TreeNode(2);
//        TreeNode node3 = new TreeNode(3);
//
//        node2.left = node1;
//        node2.right = node3;
//        solution.maxPathSum(node2);
        List list = solution.generateTrees(6);
        System.out.println(list);
    }
}

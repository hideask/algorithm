package AVL;

import javafx.animation.RotateTransition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {
    /**
     * 面试题 04.06. 后继者
     * 设计一个算法，找出二叉搜索树中指定节点的“下一个”节点（也即中序后继）。
     *
     * 如果指定节点没有对应的“下一个”节点，则返回null。
     * @param root
     * @param p
     * @return
     */
    TreeNode pre ;

    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        pre = null;
        return inorder(root,p);

    }

    public TreeNode inorder(TreeNode root, TreeNode p) {
        if (root == null) {
            return null;
        }
        TreeNode node ;
        node = inorder(root.left,p);
        if (node != null) {
            return node;
        }

        if (pre != null && p.val == pre.val) {
            return root;
        }
        pre = root;

        node = inorder(root.right,p) ;
        if (node != null) {
            return node;
        }
        return null;
    }

    /**
     * 450. 删除二叉搜索树中的节点
     * 给定一个二叉搜索树的根节点 root 和一个值 key，删除二叉搜索树中的 key 对应的节点，
     * 并保证二叉搜索树的性质不变。返回二叉搜索树（有可能被更新）的根节点的引用。
     *
     * 一般来说，删除节点可分为两个步骤：
     *
     * 首先找到需要删除的节点；
     * 如果找到了，删除它。
     * 说明： 要求算法时间复杂度为 O(h)，h 为树的高度。
     * @param root
     * @param key
     * @return
     */
    public TreeNode predecessor(TreeNode root) {
        TreeNode node = root.left;
        if (node.right != null) {
            node = node.right;
        }
        return node;
    }

    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return root;
        }
        if (root.val < key) {
            root.right = deleteNode(root.right,key) ;
        } else if (root.val > key) {
            root.left = deleteNode(root.left,key);
        } else {
            if (root.left == null || root.right == null) {
                TreeNode temp = (root.left == null ? root.right : root.left);
                return temp;
            } else {
                TreeNode temp = predecessor(root);
                root.val = temp.val;
                root.left = deleteNode(root.left,temp.val);
            }
        }
        return root;
    }

    /**
     * 1382. 将二叉搜索树变平衡
     * 给你一棵二叉搜索树，请你返回一棵 平衡后 的二叉搜索树，新生成的树应该与原来的树有着相同的节点值。
     *
     * 如果一棵二叉搜索树中，每个节点的两棵子树高度差不超过 1 ，我们就称这棵二叉搜索树是 平衡的 。
     *
     * 如果有多种构造方法，请你返回任意一种。
     * @param root
     * @return
     */
    public void getNodes(TreeNode root, List<TreeNode> list) {
        if (root == null) {
            return;
        }
        getNodes(root.left, list);
        list.add(root);
        getNodes(root.right, list);
    }

    public TreeNode buildTree(List<TreeNode> list,int l,int r) {
        if (l > r) {
            return null;
        }
        TreeNode root ;
        int mid = (l + r) / 2;
        root = list.get(mid);
        root.left = buildTree(list,0, mid - 1);
        root.right = buildTree(list,mid + 1, list.size() + 1);
        return root;
    }

    public TreeNode balanceBST(TreeNode root) {
        List<TreeNode> list = new ArrayList<>();
        getNodes(root,list);
        return buildTree(list,0,list.size());
    }

    /**
     * 108. 将有序数组转换为二叉搜索树
     * 给你一个整数数组 nums ，其中元素已经按 升序 排列，请你将其转换为一棵 高度平衡 二叉搜索树。
     *
     * 高度平衡 二叉树是一棵满足「每个节点的左右两个子树的高度差的绝对值不超过 1 」的二叉树。
     * @param nums
     * @return
     */
    public TreeNode buildTree(int[] nums,int l,int r) {
        if (l > r) {
            return null;
        }
        TreeNode root = new TreeNode();
        int mid = (l + r) / 2;
        root.val = nums[mid];
        root.left = buildTree(nums,l, mid -1);
        root.right = buildTree(nums, mid + 1, r);
        return root;
    }


    public TreeNode sortedArrayToBST(int[] nums) {
        return buildTree(nums,0, nums.length - 1);
    }

    /**
     * 98. 验证二叉搜索树
     * 给定一个二叉树，判断其是否是一个有效的二叉搜索树。
     *
     * 假设一个二叉搜索树具有如下特征：
     *
     * 节点的左子树只包含小于当前节点的数。
     * 节点的右子树只包含大于当前节点的数。
     * 所有左子树和右子树自身必须也是二叉搜索树。
     * @param root
     * @return
     */

    public boolean isValidBST(TreeNode root) {
        pre = null;
        return isValid(root);
    }

    public boolean isValid(TreeNode root) {
        if (root == null) {
            return true;
        }
        if (!isValid(root.left)) {
            return false;
        }
        if (pre != null && root.val <= pre.val) {
            return false;
        }
        pre = root;
        if (!isValid(root.right)) {
            return false;
        }
        return true;
    }

    /**
     * 501. 二叉搜索树中的众数
     * 给定一个有相同值的二叉搜索树（BST），找出 BST 中的所有众数（出现频率最高的元素）。
     *
     * 假定 BST 有如下定义：
     *
     * 结点左子树中所含结点的值小于等于当前结点的值
     * 结点右子树中所含结点的值大于等于当前结点的值
     * 左子树和右子树都是二叉搜索树
     * @param root
     * @return
     */
    TreeNode now ;
    int cnt,max;
    public int[] findMode(TreeNode root) {
        cnt = 0;
        max = 0;
        now = root;
        List<Integer> res = new ArrayList<>();
        getNums(root, res);
        int[] resn = new int[res.size()];
        for (int i = 0; i < res.size(); i ++) {
            resn[i] =  res.get(i);
        }
        return resn;
    }

    public void getNums(TreeNode root, List res) {
        if (root == null) {
            return;
        }

        getNums(root.left, res);
        if (root.val == now.val) {
            cnt++;
        } else {
            cnt = 1;
            now = root;
        }

        if (cnt > max) {
            res.clear();
            res.add(root.val);
            max = cnt;
        } else if (cnt == max) {
            res.add(root.val);
        }

        getNums(root.right, res);

    }

    /**
     * 剑指 Offer 33. 二叉搜索树的后序遍历序列
     * 输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历结果。如果是则返回 true，否则返回 false。假设输入的数组的任意两个数字都互不相同。
     * @param postorder
     * @return
     */
    int preidx;
    public boolean verifyPostorder(int[] postorder) {
        preidx = -1;//前一个元素位置
        //将序列当成一颗树
        return verifyPostorderDfs(postorder,0,postorder.length - 1);
    }

    public boolean verifyPostorderDfs(int[] postorder,int start,int tail) {
        if (start > tail) {
            return true;
        }
        //找到第一个大于末尾值的位置，就是右子树的开始位置
        int idx = start;
        while (postorder[idx] < postorder[tail]) {
            idx ++;
        }
        //判断左子树是否满足
        if (!verifyPostorderDfs(postorder,start,idx - 1)) {
            return false;
        }

        //当前元素小于等于前面一个元素
        if (preidx != -1 && postorder[preidx] >= postorder[tail]) {
            return false;
        }
        preidx = tail;

        //判断右子树是否满足
        if (!verifyPostorderDfs(postorder,idx,tail - 1)) {
            return false;
        }

        return true;
    }

    /**
     * 1008. 前序遍历构造二叉搜索树
     * 返回与给定前序遍历 preorder 相匹配的二叉搜索树（binary search tree）的根结点。
     *
     * (回想一下，二叉搜索树是二叉树的一种，其每个节点都满足以下规则，对于 node.left 的任何后代，值总 < node.val，
     * 而 node.right 的任何后代，值总 > node.val。此外，前序遍历首先显示节点 node 的值，然后遍历 node.left，接着遍历 node.right。）
     *
     * 题目保证，对于给定的测试用例，总能找到满足要求的二叉搜索树。
     * @param preorder
     * @return
     */
    public TreeNode bstFromPreorder(int[] preorder) {
        if (preorder.length == 0) {
            return null;
        }

        return bstFromPreorder(preorder,0,preorder.length - 1);
    }

    public TreeNode bstFromPreorder(int[] preorder,int start,int tail) {
        if (start > tail) {
            return null;
        }

        int idx = start + 1;
        while (idx <= tail && preorder[idx] < preorder[start]) {
            idx ++;
        }

        TreeNode root = new TreeNode(preorder[start]);
        root.left = bstFromPreorder(preorder,start + 1,idx - 1);
        root.right = bstFromPreorder(preorder, idx ,tail);
        return root;
    }

    /**
     * 面试题 04.09. 二叉搜索树序列
     * 从左向右遍历一个数组，通过不断将其中的元素插入树中可以逐步地生成一棵二叉搜索树。给定一个由不同节点组成的二叉搜索树，输出所有可能生成此树的数组。
     * @param root
     * @return
     */
    public void merge(List<Integer> l_arr, int l, List<Integer> r_arr, int r,
                      List<Integer> buff,
                      List<List<Integer>> res) {
        if (l == l_arr.size() && r == r_arr.size() ) {
            res.add(new ArrayList<>(buff));
            return;
        }

        if (l < l_arr.size()) {
            buff.add(l_arr.get(l));
            merge(l_arr, l + 1, r_arr, r, buff,res);
            buff.remove(buff.size() - 1);
        }

        if (r < r_arr.size()) {
            buff.add(r_arr.get(r));
            merge(l_arr, l, r_arr, r + 1,buff,res);
            buff.remove(buff.size() - 1);
        }

    }

    public List<List<Integer>> BSTSequences(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            res.add(new ArrayList<>());
            return res;
        }
        //递归寻找左边的值
        List<List<Integer>> l_arr = BSTSequences(root.left);
        //递归寻找右边的值
        List<List<Integer>> r_arr = BSTSequences(root.right);

        //左右两边的值进行组合，左右两边顺序可以打乱
        for (int i = 0; i < l_arr.size(); i ++) {
            for (int j = 0; j < r_arr.size(); j ++) {
                List<Integer> buff = new ArrayList<>();
                buff.add(root.val);
                merge(l_arr.get(i),0,r_arr.get(j),0,buff,res);
            }
        }

        return res;
    }
}

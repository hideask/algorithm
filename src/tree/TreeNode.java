package tree;

/**
 * Definition for a binary tree node.
 * description:TreeNode
 * create user: songj
 * date : 2021/4/5 14:05
 */
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}


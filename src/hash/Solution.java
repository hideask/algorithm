package hash;

import java.util.*;

/**
 * description:Solution
 * create user: songj
 * date : 2021/6/15 15:50
 */
public class Solution {
    /**
     * 187. 重复的DNA序列
     * 所有 DNA 都由一系列缩写为 'A'，'C'，'G' 和 'T' 的核苷酸组成，例如："ACGAATTCCG"。
     * 在研究 DNA 时，识别 DNA 中的重复序列有时会对研究非常有帮助。
     * <p>
     * 编写一个函数来找出所有目标子串，目标子串的长度为 10，且在 DNA 字符串 s 中出现次数超过一次
     *
     * @param s
     * @return
     */
    public List<String> findRepeatedDnaSequences(String s) {
        int len = s.length();
        if (len < 10) {
            return new ArrayList<>();
        }
        Map<String, Integer> map = new HashMap();
        List<String> list = new ArrayList<>();
        int l = 0, r = 10;
        while (r <= len) {
            String str = s.substring(l, r);
            map.put(str, map.getOrDefault(str, 0) + 1);
            if (map.get(str) == 2) {
                list.add(str);
            }
            l++;
            r++;
        }
        return list;
    }

    /**
     * 318. 最大单词长度乘积
     * 给定一个字符串数组 words，找到 length(word[i]) * length(word[j]) 的最大值，
     * 并且这两个单词不含有公共字母。你可以认为每个单词只包含小写字母。如果不存在这样的两个单词，返回 0。
     * <p>
     * 示例 1:
     * <p>
     * 输入: ["abcw","baz","foo","bar","xtfn","abcdef"]
     * 输出: 16
     * 解释: 这两个单词为 "abcw", "xtfn"。
     *
     * @param words
     * @return
     */
    public int maxProduct(String[] words) {
        //存储每个字符串字符位置，用二进制存储，在26位长度中，对应字符表示1，
        //没有使用0
        int[] mark = new int[words.length];
        int len = words.length;
        for (int i = 0; i < len; i++) {
            for (char c : words[i].toCharArray()) {
                //将1右移对应位数，再做或运算
                mark[i] |= 1 << (c - 'a');
            }
        }

        int ans = 0;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                //做与运算，如果不等于0说明存在相同字符
                if ((mark[i] & mark[j]) != 0) {
                    continue;
                }
                ans = Math.max(words[i].length() * words[j].length(), ans);
            }
        }

        return ans;
    }

    /**
     * 240. 搜索二维矩阵 II
     * 编写一个高效的算法来搜索 m x n 矩阵 matrix 中的一个目标值 target 。该矩阵具有以下特性：
     * <p>
     * 每行的元素从左到右升序排列。
     * 每列的元素从上到下升序排列。
     *
     * @param matrix
     * @param target
     * @return
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        int i = 0, j = matrix[0].length - 1;
        int value;
        while (i < matrix.length && j >= 0) {
            value = matrix[i][j];
            if (value == target) {
                return true;
            }
            if (value < target) {
                i++;
            }
            if (value > target) {
                j--;
            }
        }

        return false;
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    class Node {
        public int val;
        public Node prev;
        public Node next;
        public Node child;
    }

    /**
     * 863. 二叉树中所有距离为 K 的结点
     * 给定一个二叉树（具有根结点 root）， 一个目标结点 target ，和一个整数值 K 。
     * <p>
     * 返回到目标结点 target 距离为 K 的所有结点的值的列表。 答案可以以任何顺序返回。
     *
     * @param root
     * @param target
     * @param k
     * @return
     */

    HashMap parent ;
    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        //记录每个node的父节点
        parent = new HashMap();
        dfs(root,null);
        //queue记录每一层节点
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(null);
        queue.offer(target);
        //记录走过的路径
        HashSet<TreeNode> set = new HashSet<>();
        set.add(target);
        set.add(null);
        int step = 0;
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node == null) {
                //如果距离相等，则后面的为距离为K的节点
                if (step == k) {
                    List<Integer> res = new ArrayList<>();
                    for (TreeNode knode : queue) {
                        res.add(knode.val);
                    }
                    return res;
                }
                queue.offer(null);
                step ++;
            } else {
                //判断左右、父节点是否走过，没有走过则加入
                if (!set.contains(node.left)) {
                    set.add(node.left);
                    queue.offer(node.left);
                }
                if (!set.contains(node.right)) {
                    set.add(node.right);
                    queue.offer(node.right);
                }
                TreeNode par = (TreeNode) parent.get(node);
                if (!set.contains(par)) {
                    set.add(par);
                    queue.offer(par);
                }

            }

        }
        return new ArrayList<>();
    }

    public void dfs(TreeNode node, TreeNode par) {
        if (node == null) {
            return;
        }
        dfs(node.left, node);
        dfs(node.right, node);
        parent.put(node,par);
    }

    /**
     * 430. 扁平化多级双向链表
     * 多级双向链表中，除了指向下一个节点和前一个节点指针之外，它还有一个子链表指针，
     * 可能指向单独的双向链表。这些子列表也可能会有一个或多个自己的子项，依此类推，生成多级数据结构，如下面的示例所示。
     *
     * 给你位于列表第一级的头节点，请你扁平化列表，使所有结点出现在单级双链表中。
     * @param head
     * @return
     */
    public Node flatten(Node head) {
        if (head == null) {
            return head;
        }
        //定义p指向头，q指向p.next,k指向child
        Node p = head,q,k;
        while (p != null) {
            k = null;
            if (p.child != null) {
                k = flatten(p.child);
                p.child = null;
                q = p.next;
                p.next = k;
                k.prev = p;
                while (p.next != null) {
                    p = p.next;
                }
                p.next = q;
                if (q != null) {
                    q.prev = p;
                }
            }

            p = p.next;
        }
        return head;
    }

    /**
     * 979. 在二叉树中分配硬币
     * 给定一个有 N 个结点的二叉树的根结点 root，树中的每个结点上都对应有 node.val 枚硬币，并且总共有 N 枚硬币。
     *
     * 在一次移动中，我们可以选择两个相邻的结点，然后将一枚硬币从其中一个结点移动到另一个结点。
     * (移动可以是从父结点到子结点，或者从子结点移动到父结点。)。
     *
     * 返回使每个结点上只有一枚硬币所需的移动次数。
     * @param root
     * @return
     */
    int coinstep;
    public int distributeCoins(TreeNode root) {
        coinstep = 0;
        dfs(root);
        return coinstep;
    }

    public int dfs(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int l = dfs(root.left);
        int r = dfs(root.right);
        coinstep = Math.abs(l) + Math.abs(r);
        return root.val - 1 + l + r;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.findRepeatedDnaSequences("AAAAAAAAAAAAA");
    }
}

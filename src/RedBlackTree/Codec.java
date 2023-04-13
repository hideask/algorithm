package RedBlackTree;

import java.util.Stack;

/**
 * 449. 序列化和反序列化二叉搜索树
 * 序列化是将数据结构或对象转换为一系列位的过程，以便它可以存储在文件或内存缓冲区中，或通过网络连接链路传输，以便稍后在同一个或另一个计算机环境中重建。
 *
 * 设计一个算法来序列化和反序列化 二叉搜索树 。 对序列化/反序列化算法的工作方式没有限制。 您只需确保二叉搜索树可以序列化为字符串，并且可以将该字符串反序列化为最初的二叉搜索树。
 *
 * 编码的字符串应尽可能紧凑。
 */
public class Codec {
    // Encodes a tree to a single string.
    //将二叉搜索树按  root(left,right)格式序列化

    public String serialize(TreeNode root) {
        if (root == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(root.val);
        if (root.left == null && root.right == null) {
            return sb.toString();
        }
        sb.append("(");
        sb.append(serialize(root.left));
        if (root.right != null) {
            sb.append(",");
            sb.append(serialize(root.right));
        }
        sb.append(")");
        return sb.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        int idx = 0,length = data.length();
        int flag = 0;
        boolean mark = true;

        TreeNode root = null,pre = null;
        Stack<TreeNode> stack = new Stack<>();

        while (idx < length) {
            switch (flag) {
                case 0 :
                    if (data.charAt(idx) <= '9' && data.charAt(idx) >= '0') {
                        flag = 1;
                    } else if (data.charAt(idx) == '(') {
                        flag = 2;
                    } else if (data.charAt(idx) == ',') {
                        flag = 3;
                    } else if (data.charAt(idx) == ')') {
                        flag = 4;
                    }
                    break;
                case 1:
                    int val = 0;
                    while (idx < length && data.charAt(idx) <= '9' && data.charAt(idx) >= '0') {
                        val = val * 10 + (data.charAt(idx) - '0');
                        idx ++;
                    }
                    pre = new TreeNode(val);
                    if (root == null) {
                        root = pre;
                    } else {
                        if (mark) {
                            stack.peek().left = pre;
                        } else {
                            stack.peek().right = pre;
                        }
                    }
                    flag = 0;
                    break;
                case 2:
                    stack.push(pre);
                    mark = true;
                    idx ++;
                    flag = 0;
                    break;
                case 3:
                    mark = false;
                    idx ++;
                    flag = 0;
                    break;
                case 4:
                    stack.pop();
                    idx ++ ;
                    flag = 0;
                    break;
            }
        }

        return root;
    }
}

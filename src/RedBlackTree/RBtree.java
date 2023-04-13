package RedBlackTree;

public class RBtree {
    class Node {
        int val, color;
        Node left, right;
    }
    Node NIL;
    Node root;
    public RBtree() {
        NIL = new Node();
        NIL.val = -1;
        NIL.color = 1;
        NIL.left = NIL.right = NIL;
        root = NIL;
    }
    private Node getNewNode(int val) {
        Node p = new Node();
        p.val = val;
        p.color = 0;
        p.left = p.right = NIL;
        return p;
    }
    private boolean has_red_child(Node tree) {
        return tree.left.color == 0 || tree.right.color == 0;
    }
    private Node left_rotate(Node k1) {
        Node k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        return k2;
    }
    private Node right_rotate(Node k2) {
        Node k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        return k1;
    }
    public void insert(int val) {
        root = insert(root, val);
    }
    private Node insert(Node tree, int val) {
        tree = __insert(tree, val);
        tree.color = 1;
        return tree;
    }
    private Node __insert(Node tree, int val) {
        if (tree == NIL) {
            return getNewNode(val);
        }
        if (val < tree.val) {tree.left = __insert(tree.left, val);
        } else if (val > tree.val) {
            tree.right = __insert(tree.right, val);
        }
        return insert_maintain(tree);
    }
    private Node insert_maintain(Node tree) {
        if (!has_red_child(tree)) return tree;
//节点双红
        if (tree.left.color == 0 && tree.right.color == 0) {
            if (!has_red_child(tree.left) && !has_red_child(tree.right)) return tree;
            tree.color = 0;
            tree.left.color = tree.right.color = 1;
            return tree;
        }
        if (tree.left.color == 0 && !has_red_child(tree.left)) return tree;
        if (tree.right.color == 0 && !has_red_child(tree.right)) return tree;
// 左⼦树失衡
        if (tree.left.color == 0) {
            if (tree.left.right.color == 0) {
                tree.left = left_rotate(tree.left);
            }
            tree = right_rotate(tree);
        } else {
            if (tree.right.left.color == 0) {
                tree.right = right_rotate(tree.right);
            }
            tree = left_rotate(tree);
        }
        tree.color = 0;
        tree.left.color = tree.right.color = 1;
        return tree;
    }
    public void preorder() {
        preorder(root, root.val, 0);
    }
    private void preorder(Node tree, int val, int flag) {
        if (tree == NIL) return;
        if (flag == 0) {
            System.out.printf("%d is root, color is %s\n", val, tree.color == 0 ? "red" : "black");
        } else {
            System.out.printf("%d is %d's %s child, color is %s\n"
                    , tree.val, val, flag == 1 ? "right" : "left", tree.color == 0 ? "red" : "black");
        }
        preorder(tree.left, tree.val, -1);
        preorder(tree.right, tree.val, 1);
    }

    public static void main(String[] args) {
        RBtree rBtree = new RBtree();
        rBtree.insert(1);
        rBtree.insert(2);
        rBtree.insert(3);
        rBtree.insert(4);
        rBtree.insert(5);
        rBtree.insert(6);
        rBtree.insert(7);
        rBtree.insert(8);
        rBtree.insert(9);
        rBtree.preorder();
    }

}

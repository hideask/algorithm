package RedBlackTree;

/**
 * 红黑树
 */
public class RedBlackTree {
    class Node {
        int val;
        int color;//0 红色，1 黑色
        Node left;
        Node right;
        public Node() {

        }

        public Node(int val) {
            this.val = val;
            this.color = 0;
            this.left = nil;
            this.right = nil;
        }
    }

    Node nil ;//虚拟节点
    Node root;

    public RedBlackTree () {
        nil = new Node();
        nil.val = -1;
        nil.color = 1;
        nil.right = nil.left = nil;
        root = nil;
    }

    public Node getNewNode(int val) {
        return new Node(val);
    }

    public void insert(int val) {
        root = insert(root,val);
        //根节点值为黑色
        root.color = 1;
    }

    private Node insert(Node tree,int val) {
        tree = __insert(tree,val);
        
        return tree;
    }

    private Node __insert(Node tree,int val) {
        if (tree == nil) {
            return getNewNode(val);
        }
        if (tree.val == val) {
            return tree;
        }
        if (val < tree.val) {
            tree.left = insert(tree.left, val);
        }
        if (val > tree.val) {
            tree.right = insert(tree.right, val);
        }

        return insert_maintain(tree);
    }

    private boolean hasRedChild(Node tree) {
        return tree.left.color == 0 || tree.right.color == 0;
    }

    private Node left_rotate(Node tree) {
        Node newtree = tree.right;
        tree.right = newtree.left;
        newtree.left = tree;
        return newtree;
    }

    private Node right_rotate(Node tree) {
        Node newtree = tree.left;
        tree.left = newtree.right;
        newtree.right = tree;
        return newtree;
    }

    private Node preNode(Node tree) {
        Node tmp = tree.left;
        while (tmp.right != nil) {
            tmp = tmp.right;
        }
        return tmp;
    }

    private Node insert_maintain(Node tree) {
        int flag = 0;
        //左子树失衡
        if (tree.left.color == 0 && hasRedChild(tree.left)) {
            flag = 1;
        }
        //右子树失衡
        if (tree.right.color == 0 && hasRedChild(tree.right)) {
            flag = 2;
        }
        if (flag == 0) {//没有失衡
            return tree;
        }
        //第一种失衡条件
        if (tree.left.color == 0 && tree.right.color == 0) {
            tree.color = 0;
            tree.left.color = 1;
            tree.right.color = 1;
            return tree;
        }
        if (flag == 1) {
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
        //红色上浮
        tree.color = 0;
        tree.left.color = tree.right.color = 1;
        return tree;
    }

    public void remove(int key) {
        root = __remove(root,key);
        root.color = 1;
    }

    private Node __remove(Node tree,int key) {
        if (tree == nil) {
            return tree;
        }
        if (key < tree.val) {
            tree.left = __remove(tree.left, key);
        } else if (key > tree.val) {
            tree.right = __remove(tree.right,key);
        } else {
            if (tree.left == nil || tree.right == nil) {
                Node tmp = tree.left == nil ? tree.right : tree.left;
                //度0、度1黑色和红色都兼容，如果是度0 黑，则将根节点颜色加到nil节点上，nil节点变为双重黑
                tmp.color += tree.color;
                return tmp;
            } else {
                Node tmp = preNode(tree);
                tree.val = tmp.val;
                tree.left = __remove(tree.left,tmp.val);
            }
        }
        return remove_maintain(tree);
    }

    private Node remove_maintain(Node tree) {
        if (tree.left.color != 2 && tree.right.color !=2) {
            return tree;
        }
        int flag = 0;
        //兄弟节点是红色的情况，先通过旋转把兄弟节点变为黑色
        if (hasRedChild(tree)) {
            tree.color = 0;
            if (tree.left.color == 0) {
                tree = right_rotate(tree);
                flag = 1;
            } else {
                tree = left_rotate(tree);
                flag = 2;
            }
            tree.color = 1;
            if (flag == 1) {
                //右旋后，红色节点在右子树中，所以我们递归到右子树中调整
                tree.right = remove_maintain(tree.right);
            } else {
                //左旋后，红色节点在左子树中，所以我们递归到左子树中调整
                tree.left = remove_maintain(tree.left);
            }
            return tree;
        }

        //兄弟节点是黑色情况
        //情况1,兄弟节点是黑色，并且兄弟节点的子节点没有红色
        if (tree.left.color == 1 && !hasRedChild(tree.left)
            || tree.right.color == 1 && !hasRedChild(tree.right) ) {
            tree.left.color -= 1;
            tree.right.color -= 1;
            tree.color += 1;
            return tree;
        }

        if (tree.left.color == 1) {
            tree.right.color = 1;
            //确定性的黑色，LR
            if (tree.left.left.color != 0) {
                tree.left.color = 0;
                tree.left = left_rotate(tree.left);
                tree.left.color = 1;
            }
            tree.left.color = tree.color;
            tree = right_rotate(tree);
        } else {
            tree.left.color = 1;
            //确定性的黑色，RL
            if (tree.right.right.color != 0) {
                tree.right.color = 0;
                tree.right = right_rotate(tree.right);
                tree.right.color = 1;
            }
            tree.right.color = tree.color;
            tree = left_rotate(tree);
        }
        tree.left.color = tree.right.color = 1;
        return tree;

    }

    public void preorder() {
        preorder(root,root.val,0);
    }

    private void preorder(Node tree ,int val , int flag) {
        if (tree == nil) return;
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
        RedBlackTree redBlackTree = new RedBlackTree();
        redBlackTree.insert(1);
        redBlackTree.insert(2);
        redBlackTree.insert(3);
        redBlackTree.insert(4);
        redBlackTree.insert(5);
        redBlackTree.insert(6);
        redBlackTree.insert(7);
        redBlackTree.insert(8);
        redBlackTree.insert(9);
        //redBlackTree.insert(10);

        redBlackTree.remove(1);
        redBlackTree.preorder();
    }

}

package AVL;

import java.util.ServiceConfigurationError;

/**
 * 平衡二叉搜索树
 */
public class AVLTree {

    /**
     * 定义一种node节点的结构
     */
    class Node {
        int key;
        Node left ;
        Node right;
        int height;
        public Node() {

        }
        public Node(int key) {
            this.key = key;
            this.height = 1;
        }

        public Node(int key,Node left,Node right) {
            this.key = key;
            this.left = left;
            this.right = right;
            this.height = 0;
        }

    }

    private Node root;

    public AVLTree() {
        root = null;
    }

    public Node getRoot() {
        return root;
    }


    /**
     * 获取一个新节点
     */
    public Node getNewNode(int key) {
        return new Node(key);
    }

    public int getHeight() {
        return height(root);
    }

    private int height(Node node) {
        return node == null ? 0 : node.height;
    }

    private int max(int a,int b) {
        return a > b ? a : b;
    }

    private void updateHeight(Node node) {
        node.height = max(height(node.left),height(node.right)) + 1;
    }

    public boolean search(int key) {
        return search(root,key);
    }

    private boolean search(Node node, int key) {
        if (node == null) {
            return false;
        }
        if (search(node.left,key)) {
            return true;
        }
        if (node.key == key) {
            return true;
        }
        if (search(node.right,key)) {
            return true;
        }
        return false;
    }

    /**
     * 销毁一棵树
     */
//    public void clear(Node root) {
//        if (root == null) {
//            return;
//        }
//        clear(root.left);
//
//        clear(root.right);
//    }

    /**
     * 左旋
     * @param node
     */
    public Node leftRotate(Node node) {
        Node newroot = node.right;
        node.right = newroot.left;
        newroot.left = node;
        updateHeight(node);
        updateHeight(newroot);
        return newroot;
    }

    /**
     * 右旋
     * @param node
     */
    public Node rightRotate(Node node) {
        Node newroot = node.left;
        node.left = newroot.right;
        newroot.right = node;
        updateHeight(node);
        updateHeight(newroot);
        return newroot;
    }

    /**
     * 平衡左右子树
     * @param tree
     * @return
     */
    private Node maintain(Node tree) {
        int lh = height(tree.left);
        int rh = height(tree.right);
        //是平衡的
        if (Math.abs(lh - rh) < 2 ) {
            return tree;
        }
        //
        if (lh > rh) {//L
            if (height(tree.left.right) > height(tree.left.left)) {//R
                tree.left = leftRotate(tree.left);
            }
            tree = rightRotate(tree);
        } else {//R
            if (height(tree.right.left) > height(tree.right.right)) {//L
                tree.right = rightRotate(tree.right);
            }
            tree = leftRotate(tree);
        }
        return tree;
    }
    /**
     * 插入一个节点
     * @return
     */
    public void insert(int key) {
        root = insert(root,key);
    }

    private Node insert(Node node, int key) {
        //如果为空，新建一个节点
        if (node == null) {
            node = getNewNode(key);
            return node;
        }
        //如果值等于root的值，说明不用插入
        if (node.key == key) {
            return node;
        }
        //如果值小于root的值，往左子树插入
        if (key < node.key) {
            //此代码可简化插入操作的复杂逻辑判断
            node.left = insert(node.left,key);
        } else if (key > node.key) {
            node.right = insert(node.right,key);
        }
        updateHeight(node);
        return maintain(node);
    }

    /**
     * 寻找指定节点的前驱节点
     * @return
     */
    private Node preNode(Node node) {
        //去node的左子树找右子树的度为0或1的节点
        Node tmp = node.left;
        while (tmp.right != null) {
            tmp = tmp.right;
        }
        return tmp;
    }

    /**
     * 寻找指定节点的后继节点
     * @param node
     * @return
     */
    private Node bacNode(Node node) {
        //去node的右子树找左子树的度为0或1的节点
        Node tmp = node.right;
        while (tmp.left != null) {
            tmp = tmp.left;
        }
        return tmp;
    }

    /**
     * 删除一个值
     */
    public void remove(int key) {
        if (!search(key)) {
            return;
        }
        root = remove(root,key);
    }

    private Node remove(Node node,int key) {
        if (node == null) {
            return node;
        }
        if (key < node.key) {
            node.left = remove(node.left, key);
        } else if (key > node.key) {
            node.right = remove(node.right,key);
        } else {
            //度为0的节点,删除并不影响为0 和 1的判断
//            if (node.left == null && node.right == null) {
//                return null;
//            } else
            if (node.left == null || node.right == null) {//度为1的节点
                Node tmp = node.left == null ? node.right : node.left;
                return tmp;
            } else {//度为2的节点
                //获取前驱节点
                Node preNode = preNode(node);
                //将前驱节点覆盖到当前节点
                node.key = preNode.key;
                //在左子树中删除前驱节点
                node.left = remove(node.left,preNode.key);
            }
        }
        if (node != null) {
            updateHeight(node);
        }
        return maintain(node);
    }

    public void printNode(Node node) {
        if (node == null) {
            return;
        }

        printNode(node.left);
        System.out.println(node.key);
        printNode(node.right);
    }

    public static void main(String[] args) {
        AVLTree avlTree = new AVLTree();
        avlTree.insert(3);
        avlTree.insert(1);
        avlTree.insert(6);
        avlTree.insert(4);
        avlTree.insert(8);
        avlTree.insert(9);
        avlTree.insert(2);
        avlTree.insert(5);
        avlTree.insert(7);
        avlTree.insert(10);

        avlTree.remove(9);
        System.out.println("//////");
        avlTree.printNode(avlTree.getRoot());
        avlTree.remove(10);
        System.out.println("//////");
        avlTree.printNode(avlTree.getRoot());
        avlTree.remove(7);
        System.out.println("//////");
        avlTree.printNode(avlTree.getRoot());
        avlTree.remove(8);
        System.out.println("//////");
        avlTree.printNode(avlTree.getRoot());


    }
}

package AVL;

/**
 * 二叉搜索树
 */
public class BinarySearchTree {

    /**
     * 定义一种node节点的结构
     */
    class Node {
        int key;
        Node left;
        Node right;
        int height;
        public Node() {

        }
        public Node(int key) {
            this.key = key;
            this.height = 0;
        }

        public Node(int key,Node left,Node right) {
            this.key = key;
            this.left = left;
            this.right = right;
            this.height = 0;
        }

    }

    private Node root;

    public BinarySearchTree() {
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

    public boolean search(int key) {
        return search(root,key);
    }

//    private boolean search(Node node, int key) {
//        if (node == null) {
//            return false;
//        }
//        if (search(node.left,key)) {
//            return true;
//        }
//        if (node.key == key) {
//            return true;
//        }
//        if (search(node.right,key)) {
//            return true;
//        }
//        return false;
//    }

    private boolean search(Node node, int key) {
        if (node == null) {
            return false;
        }
        if (key == node.key) {
            return true;
        }

        if (key < node.key && search(node.left,key)) {
            return true;
        }

        if (key > node.key && search(node.right,key)) {
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
        node.height = max(height(node.left),height(node.right)) + 1;
        return node;
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
            //度为0的节点
            if (node.left == null && node.right == null) {
                return null;
            } else if (node.left == null || node.right == null) {//度为1的节点
                Node tmp = node.left == null ? node.right : node.left;
                return tmp;
            } else {
                //获取前驱节点
                Node preNode = preNode(node);
                node.key = preNode.key;
                node.left = remove(node.left,preNode.key);
            }
        }
        if (node != null) {
            node.height = max(height(node.right),height(node.left)) + 1;
        }
        return node;
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
        BinarySearchTree binarySearchTree = new BinarySearchTree();
        binarySearchTree.insert(3);
        binarySearchTree.insert(1);
        binarySearchTree.insert(6);
        binarySearchTree.insert(4);
        binarySearchTree.insert(8);
        binarySearchTree.insert(9);
        binarySearchTree.insert(2);
        binarySearchTree.insert(5);
        binarySearchTree.insert(7);
        binarySearchTree.insert(10);
        System.out.println(111111);
//        binarySearchTree.remove(11);
//        binarySearchTree.remove(7);
//        binarySearchTree.remove(8);
//        binarySearchTree.remove(9);
//        binarySearchTree.remove(10);
        binarySearchTree.printNode(binarySearchTree.getRoot());

        System.out.println(binarySearchTree.search(51));
        System.out.println(222222);
    }
}

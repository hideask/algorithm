package queue;

import sun.reflect.generics.tree.VoidDescriptor;

/**
 * 1670. 设计前中后队列
 * 请你设计一个队列，支持在前，中，后三个位置的 push 和 pop 操作。
 *
 * 请你完成 FrontMiddleBack 类：
 *
 * FrontMiddleBack() 初始化队列。
 * void pushFront(int val) 将 val 添加到队列的 最前面 。
 * void pushMiddle(int val) 将 val 添加到队列的 正中间 。
 * void pushBack(int val) 将 val 添加到队里的 最后面 。
 * int popFront() 将 最前面 的元素从队列中删除并返回值，如果删除之前队列为空，那么返回 -1 。
 * int popMiddle() 将 正中间 的元素从队列中删除并返回值，如果删除之前队列为空，那么返回 -1 。
 * int popBack() 将 最后面 的元素从队列中删除并返回值，如果删除之前队列为空，那么返回 -1 。
 * 请注意当有 两个 中间位置的时候，选择靠前面的位置进行操作。比方说：
 *
 * 将 6 添加到 [1, 2, 3, 4, 5] 的中间位置，结果数组为 [1, 2, 6, 3, 4, 5] 。
 * 从 [1, 2, 3, 4, 5, 6] 的中间位置弹出元素，返回 3 ，数组变为 [1, 2, 4, 5, 6]
 * description:FrontMiddleBackQueue
 * create user: songj
 * date : 2021/3/15 22:01
 */
public class FrontMiddleBackQueue {
    /**
     * 节点内，提供插入前节点，下节点，弹出前节点，下一个节点
     */
    class Node {
        Node pre,next;//前节点，下一个节点
        int val;

        public Node() {

        }
        public Node(int val) {
            this.val = val;
        }

        /**
         * 往当前节点前面增加节点
         * @param node
         */
        public void insertPre(Node node) {
            node.pre = pre;
            node.next = this;
            if (this.pre != null) {
                this.pre.next = node;
            }
            this.pre = node;
        }

        /**
         * 往当前节点后面增加节点
         * @param node
         */
        public void insertNext(Node node) {
            node.next = this.next;
            node.pre = this;
            if (this.next != null) {
                this.next.pre = node;
            }
            this.next = node;
        }

        /**
         * 删除当前节点的前节点
         */
        public void deletePre() {
            if (this.pre == null) {
                return;
            }
            Node pointer = this.pre;
            this.pre = pointer.pre;
            if (pointer.pre != null) {
                pointer.pre.next = this;
            }
        }

        /**
         * 删除当前节点的下一个节点
         */
        public void deleteNext() {
            if (this.next == null) {
                return;
            }
            Node pointer = this.next;
            this.next = pointer.next;
            if (pointer.next != null) {
                pointer.next.pre = this;
            }
        }
    }

    /**
     * 封装队列
     */
    class MyQueue{
        Node dummyHead = new Node();//虚头
        Node dummyTail = new Node();//虚尾
        int count;
        public MyQueue() {
            dummyHead.next = dummyTail;
            dummyHead.pre = null;
            dummyTail.pre = dummyHead;
            dummyTail.next = null;
            count=0;
        }

        /**
         * 在队列前面添加一个节点
         * @param value
         */
        public void pushFront(int value) {
            dummyHead.insertNext(new Node(value));
            count ++;
        }

        /**
         * 在队列后面添加一个节点
         * @param value
         */
        public void pushBack(int value) {
            dummyTail.insertPre(new Node(value));
            count ++;
        }

        /**
         * 弹出最后一个节点
         * @return
         */
        public int popBack() {
            if (isEmpty()) {
                return -1;
            }
            int val = dummyTail.pre.val;
            dummyTail.deletePre();
            count --;
            return val;
        }

        /**
         * 弹出最前面一个节点
         * @return
         */
        public int popFront() {
            if (isEmpty()) {
                return  -1;
            }
            int val = dummyHead.next.val;
            dummyHead.deleteNext();
            count -- ;
            return val;
        }

        public boolean isEmpty() {
            return dummyHead.next == dummyTail;
//            return count == 0;
        }

        public int size() {
            return count;
        }
    }

    //定义左边永远大于等于右边
    MyQueue left = new MyQueue();//左队列
    MyQueue right = new MyQueue();//右队列
    public FrontMiddleBackQueue() {

    }

    /**
     * 平衡，使左边永远大于等于右边
     */
    public void reBalance() {
        if (left.size() < right.size()) {
            left.pushBack(right.popFront());
        }
        if (left.size() == right.size() +2) {
            right.pushFront(left.popBack());
        }
    }

    public void pushFront(int val) {
        left.pushFront(val);
        reBalance();
    }

    public void pushMiddle(int val) {
        if (left.size() >right.size()) {
            right.pushFront(left.popBack());
        }
        left.pushBack(val);
    }

    public void pushBack(int val) {
        right.pushBack(val);
        reBalance();
    }

    public int popFront() {
        if (isEmpty()) {
            return -1;
        }

        int val = left.popFront();
        reBalance();
        return val;
    }

    public int popMiddle() {
        if (isEmpty()) {
            return -1;
        }
        int val = left.popBack();
        reBalance();
        return val;
    }

    public int popBack() {
        if (isEmpty()) {
            return -1;
        }
        int val;
        if (right.isEmpty()) {
            val = left.popBack();
        } else {
            val = right.popBack();
        }
        reBalance();
        return val;
    }

    public boolean isEmpty() {
        return left.size() == 0;
    }
}

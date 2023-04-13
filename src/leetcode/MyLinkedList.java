package leetcode;

/**
 * 707. 设计链表
 * 设计链表的实现。您可以选择使用单链表或双链表。单链表中的节点应该具有两个属性：val 和 next。
 * val 是当前节点的值，next 是指向下一个节点的指针/引用。如果要使用双向链表，
 * 则还需要一个属性 prev 以指示链表中的上一个节点。假设链表中的所有节点都是 0-index 的。
 * description:MyLinkedList
 * create user: songj
 * date : 2021/3/29 11:24
 */
public class MyLinkedList {
    ListNode listNode ;
    int length;

     /**Initialize your data structure here. */
    public MyLinkedList() {
//        listNode = new ListNode();
    }

    /** Get the value of the index-th node in the linked list.
     * If the index is invalid, return -1. */
    public int get(int index) {
        ListNode node = getListNode(index);
        int val;
        if (node == null) {
            val = -1;
        } else {
            val = node.val;
        }
        return val;
    }

    public ListNode getListNode(int index) {
        ListNode node = listNode;
        if (index < 0 || index > length() - 1) {
            return null;
        }
        ListNode res = new ListNode();
        int i = 0;
        while (node != null) {
            if (i == index) {
                res = node;
                break;
            }
            node = node.next;
            i++;
        }
        return res;
    }

    public ListNode getLastNode() {
        if (listNode == null) {
            return null;
        }
        ListNode node = listNode;
        while (node.next != null) {
            node = node.next;
        }
        return node;
    }

    public int length() {
        int i = 0;
        if (listNode == null) {
            return -1;
        }
        ListNode node = listNode;
        while (node != null) {
            i ++;
            node = node.next;
        }
        return i;
    }
    /** Add a node of value val before the first element of the linked list.
     * After the insertion, the new node will be the first node of the linked list. */
    public void addAtHead(int val) {
        ListNode first = new ListNode(val);
        first.next = listNode;
        listNode = first;
    }

    /** Append a node of value val to the last element of the linked list. */
    public void addAtTail(int val) {
        ListNode last = getLastNode();
        if (isEmpty(last)) {
            listNode = new ListNode(val);
        } else {
            last.next = new ListNode(val);
        }
    }

    /** Add a node of value val before the index-th node in the linked list.
     * If index equals to the length of linked list,
     * the node will be appended to the end of linked list.
     * If index is greater than the length, the node will not be inserted. */
    public void addAtIndex(int index, int val) {
        int length = length();
        if (index == 0) {
            addAtHead(val);
        } else if (index == length) {
            addAtTail(val);
        } else if (index>0 && index < length) {
            ListNode node = new ListNode(val);
            ListNode pre = getListNode(index - 1);
            ListNode next = pre.next;
            pre.next = node;
            node.next = next;
        }
    }

    /** Delete the index-th node in the linked list, if the index is valid. */
    public void deleteAtIndex(int index) {
        int length = length();
        if (index == 0) {
            listNode = listNode.next;
            return;
        }
        ListNode pre = getListNode(index - 1);
        ListNode next = pre.next;
        if (index > 0 && index < length) {
            ListNode nextnext = pre.next.next;
            pre.next = nextnext;
            next.next = null;
            return;
        }
        if (index == length) {
            pre.next = null;
        }
    }

    public boolean isEmpty(ListNode node) {
        if (node == null) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        MyLinkedList myLinkedList = new MyLinkedList();
        myLinkedList.addAtHead(1);
        myLinkedList.addAtTail(3);
        myLinkedList.addAtIndex(1,2);
        ListNode listNode = myLinkedList.listNode;

    }
}

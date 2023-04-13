package hash;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 面试题 16.25. LRU 缓存
 * 设计和构建一个“最近最少使用”缓存，该缓存会删除最近最少使用的项目。缓存应该从键映射到值(允许你插入和检索特定键对应的值)，
 * 并在初始化时指定最大容量。当缓存被填满时，它应该删除最近最少使用的项目。
 *
 * 它应该支持以下操作： 获取数据 get 和 写入数据 put 。
 *
 * 获取数据 get(key) - 如果密钥 (key) 存在于缓存中，则获取密钥的值（总是正数），否则返回 -1。
 * 写入数据 put(key, value) - 如果密钥不存在，则写入其数据值。当缓存容量达到上限时，
 * 它应该在写入新数据之前删除最近最少使用的数据值，从而为新的数据值留出空间。
 *
 * description:LRUCache
 * create user: songj
 * date : 2021/6/16 20:42
 */
public class LRUCache2 {
    //使用双向链表加HashMap方式
    int capacity ;
    Map<Integer, ListNode> map;
    ListNode head;
    ListNode tail;

    public LRUCache2(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        head = new ListNode(-1,-1);
        tail = new ListNode(-1,-1);
        head.next = tail;
        tail.pre = head;
    }

    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }
        ListNode node = map.get(key);
        node.pre.next = node.next;
        node.next.pre = node.pre;
        moveToTail(node);
        return node.val;
    }

    public void put(int key, int value) {
        if (get(key) != -1) {
            map.get(key).val = value;
            return;
        }
        ListNode node = new ListNode(key,value);
        map.put(key,node);
        moveToTail(node);
        if (map.size() > capacity) {
            map.remove(head.next.key);
            head.next = head.next.next;
            head.next.pre = head;
        }
    }

    /**
     * 移动节点到末尾
     * @param node
     */
    public void moveToTail(ListNode node) {
        node.pre = tail.pre;
        tail.pre = node;
        node.next = tail;
        node.pre.next = node;
    }

    class ListNode {
        int key;
        int val;
        ListNode pre;
        ListNode next;

        public ListNode (int key,int value) {
            this.key = key;
            this.val = value;
            pre = null;
            next = null;
        }
    }
}

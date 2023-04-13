package link;

import java.util.HashSet;

/**
 * description:CycleLink
 * create user: songj
 * date : 2021/3/8 10:21
 */
public class CycleLink {
    /**
     * 141. 环形链表
     * 给定一个链表，判断链表中是否有环。
     *
     * 如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。 为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 pos 是 -1，则在该链表中没有环。注意：pos 不作为参数进行传递，仅仅是为了标识链表的实际情况。
     *
     * 如果链表中存在环，则返回 true 。 否则，返回 false 。
     * 使用HashSet实现，利用HashSet只能保存不同元素的特性
     * @param head
     * @return
     */
    public boolean hashCycle(ListNode head) {
        HashSet<ListNode> hs = new HashSet<ListNode>();
        if (head == null) {
            return false;
        }
        while (head != null) {
            if (!hs.add(head)) {
                return true;
            }
            head = head.next;
        }
        return false;
    }

    //使用快慢指针实现，快慢指针如果相遇就是有环
    public boolean deteCycle(ListNode head) {
        if (head == null) {
            return false;
        }
        ListNode slow = head ,quick = head;
        do {
            if (quick == null || quick.next == null) {
                return false;
            }
            slow = slow.next;
            quick = quick.next.next;
        } while (slow != quick);
        return true;
    }

    /**
     * 142. 环形链表 II
     * 给定一个链表，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。
     */
    public ListNode hashCycle1(ListNode head) {
        HashSet<ListNode> hs = new HashSet<ListNode>();
        if (head == null) {
            return null;
        }
        while (head != null) {
            if (!hs.add(head)) {
                return head;
            }
            head = head.next;
        }
        return null;
    }

    //使用快慢指针实现，快慢指针如果相遇就是有环
    public ListNode deteCycle1(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode slow = head ,quick = head;
        //先找到相遇点，此时相遇点到入环点的距离等于头节点到入环点的距离
        do {
            if (quick == null || quick.next == null) {
                return null;
            }
            slow = slow.next;
            quick = quick.next.next;
        } while (slow != quick);
        //将快指针放到头节点，快慢指针每次都走一步，第二次相遇点就是入环点
        quick = head;
        while (slow != quick) {
            slow = slow.next;
            quick = quick.next;
        }
        return slow;
    }
}
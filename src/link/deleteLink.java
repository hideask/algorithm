package link;

/**
 * description:deleteLink
 * create user: songj
 * date : 2021/3/10 23:40
 */
public class deleteLink {
    /**
     * 19. 删除链表的倒数第 N 个结点
     * 给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
     *
     * 进阶：你能尝试使用一趟扫描实现吗？
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        //两遍扫描，第一遍找到length，第二遍找到length -n,则是删除前一个节点

        //一遍扫描，定义两个指针,快指针先走n步，然后快慢指针同时往后走，当fast为null时，
        //slow就指向要删除节点的前一个节点
        ListNode hair = new ListNode(0,head),fast = head,slow = hair;
        while (n > 0) {
            fast = fast.next;
            n--;
        }
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return hair.next;
    }

    /**
     * 83. 删除排序链表中的重复元素
     * 给定一个排序链表，删除所有重复的元素，使得每个元素只出现一次。
     * @param head
     * @return
     */
    public ListNode deleteDuplicates(ListNode head) {
        ListNode curr = head;
        while (curr != null && curr.next != null) {
            if (curr.val == curr.next.val) {
                curr.next = curr.next.next;
            } else {
                curr = curr.next;
            }
        }
        return head;
    }

    /**
     * 82. 删除排序链表中的重复元素 II
     * 给定一个排序链表，删除所有含有重复数字的节点，只保留原始链表中 没有重复出现 的数字。
     * @param head
     * @return
     */
    public ListNode deleteDuplicates2(ListNode head) {
        ListNode hair = new ListNode(0,head),pre = hair,curr = head;
        while (curr != null) {
            while (curr.next != null && curr.val == curr.next.val) {
                curr = curr.next;
            }
            //可能第一个节点不重复
            if (pre.next == curr) {
                pre = curr;
            } else {
                pre.next = curr.next;
            }
            curr = curr.next;
        }
        return hair.next;
    }
}

package link;

/**
 * description:SepLink
 * create user: songj
 * date : 2021/3/14 22:20
 */
public class SepLink {
    /**
     * 86. 分隔链表
     * 给你一个链表的头节点 head 和一个特定值 x ，请你对链表进行分隔，使得所有 小于 x 的节点都出现在 大于或等于 x 的节点之前。
     *
     * 你应当 保留 两个分区中每个节点的初始相对位置
     * @param head
     * @param x
     * @return
     */
    public ListNode partition(ListNode head, int x) {
        //小于x的链表节点
        ListNode small = new ListNode(0);
        ListNode smallhair = small;
        //大于x的链表节点
        ListNode large = new ListNode(0);
        ListNode largehair = large;
        while (head != null) {
            if (head.val < x) {
                //小于x的节点放到small链表
                small.next = head;
                //small指针指向下一个节点
                small = small.next;
            } else {
                //大于等于x的节点放到large链表
                large.next = head;
                large = large.next;
            }
            head = head.next;
        }
        //断开最后一个节点，否则可能会形成环
        large.next = null;
        small.next = largehair.next;
        return smallhair.next;
    }
}

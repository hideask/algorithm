package link;

/**
 * 反转列表
 * description:ReverseLink
 * create user: songj
 * date : 2021/3/9 8:50
 */
public class ReverseLink {

    /**
     * 24. 两两交换链表中的节点
     * 给定一个链表，两两交换其中相邻的节点，并返回交换后的链表。
     *
     * 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
     * @param head
     * @return
     */
    public ListNode swapPairs(ListNode head) {
        ListNode hair = new ListNode(0,head),pre=hair;
        while (pre.next != null && pre.next.next != null) {
            ListNode one = pre.next;
            ListNode two = pre.next.next;
            one.next = two.next;
            two.next = one;
            pre.next = two;
            pre = one;
        }
        return hair.next;
    }
    /**
     * 61. 旋转链表
     * 给定一个链表，旋转链表，将链表每个节点向右移动 k 个位置，其中 k 是非负数。
     * @param head
     * @param k
     * @return
     */
    public ListNode rotateRight(ListNode head, int k) {
        ListNode oldtail=head, newhead, newtail;
        if (head == null || head.next == null) {
            return head;
        }
        int length = 1;
        //找到尾结点，并计算长度
        while (oldtail.next != null) {
            oldtail = oldtail.next;
            length++;
        }

        //将尾结点和头节点连接形成一个环
        oldtail.next = head;
        //寻找新的尾结点
        newtail = head;
        for (int i = 0;i < length - k%length -1;i ++) {
            newtail = newtail.next;
        }
        //使用新的头节点，不能直接断开，不然找不到头
        newhead = newtail.next;
        //断开尾结点
        newtail.next = null;
        return newhead;
    }

    /**
     * 25. K 个一组翻转链表
     * 给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。
     *
     * k 是一个正整数，它的值小于或等于链表的长度。
     *
     * 如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
     *
     * 进阶：
     *
     * 你可以设计一个只使用常数额外空间的算法来解决此问题吗？
     * 你不能只是单纯的改变节点内部的值，而是需要实际进行节点交换。
     * @param head
     * @param k
     * @return
     */
    public ListNode reverseGroup(ListNode head,int k) {
        //定义值为0的虚拟头节点指向head，定义con指针指向hair
        ListNode hair = new ListNode(0,head),pre = hair, tail = null;
        //k个一组循环翻转
        while (head != null) {
            //将tail指针初始放到虚拟头，循环最终tail走到k个节点处得到一个k长度的链表
            tail = pre;
            for (int i = 0;i < k; i++) {
                tail = tail.next;
                if (tail == null) {
                    return hair.next;
                }
            }
            ListNode[] reverse = reverse(head,tail);
            //重新指向正确的值
            head = reverse[0];
            tail = reverse[1];
            //hair指向正确的head
            pre.next = head;
            //指向下一个虚头
            pre = tail;
            //指向下一个需要翻转的head
            head = pre.next;
        }
        return hair.next;
    }

    /**
     * 指定区间翻转列表
     * 92. 反转链表 II
     * 反转从位置 m 到 n 的链表。请使用一趟扫描完成反转。
     * @param head
     * @return
     */
    public ListNode reverseBetween(ListNode head, int left, int right) {
        //hair是值为0的头节点
        ListNode hair = new ListNode(0, head) ,con = hair;
        int n = right-left+1;
        //找到需要翻转的头节点
        while (left > 1) {
            con = con.next;
            left -- ;
        }
        //实际从0节点开始，将反转后的链表连接上
        con.next = reverse(con.next,n);
        return hair.next;
    }
    /**
     * LeetCode #206 反转链表
     * 思路1：迭代反转
     * 可以使用虚拟头节点来进行头插法
     * 思路2：递归反转（一次拆掉一个节点并递归处理剩余的子链表）
     * */
    public ListNode reverse(ListNode head) {
        /*
        设置3个指针，pre代表虚拟头节点，头节点会变化的就需要设置虚拟头节点
        curr指向需要反转的节点，next指向下一个节点
        反转当前节点的下一个指针指向
         */
        ListNode pre = null ,curr = head, next = null;
        while (curr != null) {
            next = curr.next;
            curr.next = pre;
            pre = curr;
            curr = next;
        }
        return pre;
    }

    public ListNode reverse(ListNode head,int n) {
        /*
        设置3个指针，pre代表虚拟头节点，头节点会变化的就需要设置虚拟头节点
        curr指向需要反转的节点，next指向下一个节点
        反转当前节点的下一个指针指向
         */
        ListNode pre = new ListNode() ,curr = head, next = null;
        while (n > 0) {
            next = curr.next;
            //将pre.next当做头节点，
            curr.next = pre.next;
            pre.next = curr;
            curr = next;
            n -- ;
        }
        //此时head已到末尾，将next指向当前节点，将链表连接起来
        head.next = curr;
        return pre.next;
    }

    public ListNode[] reverse(ListNode head,ListNode tail) {
        ListNode pre = tail.next,curr = head, next = null;
        while (pre != tail) {
            next = curr.next;
            curr.next = pre;
            pre = curr;
            curr = next;
        }
        return new ListNode[]{tail,head};
    }
}
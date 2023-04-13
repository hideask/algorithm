package link;

/**
 * description:ListNode
 * create user: songj
 * date : 2021/3/8 11:03
 */
public class ListNode {
    int val ;
    ListNode next;
    ListNode () { };
    ListNode (int val) {
        this.val = val;
    };
    ListNode (int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}

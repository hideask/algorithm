package link;

/**
 * description:CopyRadomLink
 * create user: songj
 * date : 2021/3/14 22:42
 */
public class CopyRadomLink {
    class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    /**
     * 138. 复制带随机指针的链表
     * 给你一个长度为 n 的链表，每个节点包含一个额外增加的随机指针 random ，
     * 该指针可以指向链表中的任何节点或空节点。
     *
     * 构造这个链表的 深拷贝。 深拷贝应该正好由 n 个 全新 节点组成，
     * 其中每个新节点的值都设为其对应的原节点的值。新节点的 next 指针和 random 指针也都应指向复制链表中的新节点，
     * 并使原链表和复制链表中的这些指针能够表示相同的链表状态。复制链表中的指针都不应指向原链表中的节点 。
     * @param head
     * @return
     */
    public Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }
        //将原有节点复制一份放到原节点后面，A->A'->B->B'
        Node pointer = head;
        while (pointer != null) {
            Node newnode = new Node(pointer.val);
            newnode.next = pointer.next;
            pointer.next = newnode;
            pointer = newnode.next;
        }
        //给复制的节点的random赋值为新的复制节点
        pointer = head;
        while (pointer != null) {
            pointer.next.random = pointer.random != null ?
                    pointer.random.next : null;
            pointer = pointer.next.next;
        }
        Node oldListNode = head;
        Node newListNode = head.next;
        Node newhead = head.next;
        //分开原始链表和复制的链表
        while (oldListNode != null) {
            oldListNode.next = oldListNode.next.next;
            newListNode.next = (newListNode.next != null) ? newListNode.next.next
                    :null;
            oldListNode = oldListNode.next;
            newListNode = newListNode.next;
        }
        return newhead;
    }
}

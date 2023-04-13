package leetcode;

import jdk.nashorn.internal.objects.annotations.Where;
import sun.dc.pr.PRError;
import sun.net.idn.Punycode;
import sun.reflect.annotation.AnnotationSupport;

import java.math.BigDecimal;
import java.util.*;

/**
 * description:Solution
 * create user: songj
 * date : 2021/3/26 18:47
 */
public class Solution {
    /**
     * 1. 两数之和
     * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 的那 两个 整数，
     * 并返回它们的数组下标。
     * <p>
     * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
     * <p>
     * 你可以按任意顺序返回答案。
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        //思路：将数字放入hash中，将目标值-当前遍历的值放到hash中找，如果有就返回，
        //时间复杂度为O(N)
        int[] res = new int[2];
        int n = nums.length;
        Map<Integer, Integer> map = new HashMap();
        for (int i = 0; i < n; i++) {
            if (map.containsKey(target - nums[i])) {
                res[0] = i;
                res[1] = map.get(target - nums[i]);
                return res;
            }
            map.put(nums[i], i);
        }
        return null;
    }

    /**
     * 2. 两数相加
     * 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
     * <p>
     * 请你将两个数相加，并以相同形式返回一个表示和的链表。
     * <p>
     * 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        BigDecimal i = new BigDecimal(getNum(l1));
        BigDecimal j = new BigDecimal(getNum(l2));
        String str = i.add(j).toString();
        if (str.isEmpty()) {
            return null;
        }
        ListNode res = new ListNode(Integer.parseInt(String.valueOf(str.charAt(str.length() - 1))));
        List<ListNode> list = new ArrayList();
        list.add(res);
        for (int m = str.length() - 2, n = 1; m >= 0; m--, n++) {
            ListNode result = new ListNode(Integer.parseInt(String.valueOf(str.charAt(m))));
            list.add(result);
            list.get(n - 1).next = result;
        }
        return res;
    }

    public String getNum(ListNode l1) {
        String num = "";
        ListNode head = new ListNode(0, l1);
        Deque<Integer> stack = new LinkedList();
        while (1 == 1) {
            if (head.next == null) {
                break;
            }
            head = head.next;
            stack.push(head.val);
        }
        while (!stack.isEmpty()) {
            num += String.valueOf(stack.pop());
        }
        return num;
    }

    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        ListNode head = null, tail = null;
        int num = 0;
        while (l1 != null || l2 != null) {
            int m1 = l1 != null ? l1.val : 0;
            int m2 = l2 != null ? l2.val : 0;
            int add = m1 + m2 + num;
            num = add / 10;
            if (head == null) {
                head = tail = new ListNode(add % 10);
            } else {
                tail.next = new ListNode(add % 10);
                tail = tail.next;
            }
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }
        if (num > 0) {
            tail.next = new ListNode(num);
        }
        return head;
    }

    /**
     * 面试题 02.02. 返回倒数第 k 个节点
     * 实现一种算法，找出单向链表中倒数第 k 个节点。返回该节点的值。
     *
     * @param head
     * @param k
     * @return
     */
    public int kthToLast(ListNode head, int k) {
        ListNode hair = new ListNode(0, head);
        ListNode pre = hair;
        for (int i = 0; i < k; i++) {
            pre = pre.next;
        }
        while (pre != null) {
            hair = hair.next;
            pre = pre.next;
        }
        return hair.val;
    }

    /**
     * 剑指 Offer 35. 复杂链表的复制
     * 请实现 copyRandomList 函数，复制一个复杂链表。在复杂链表中，
     * 每个节点除了有一个 next 指针指向下一个节点，还有一个 random 指针指向链表中的任意节点或者 null。
     *
     * @param head
     * @return
     */
    public Node copyRandomList(Node head) {
        Node pre = head;
        Node tail = null;
        if (head.next == null) {
            return head;
        }
        while (pre != null) {
            tail = pre.next;
            Node copynode = new Node(pre.val);
            copynode.next = tail;
            pre.next = copynode;
            pre = tail;
        }
        pre = head;
        while (pre != null) {
            pre.next.random = (pre.random != null ? pre.random.next : null);
            pre = pre.next.next;
        }

        pre = head;
        Node copynode = pre.next;
        Node copyhead = pre.next;
        while (pre != null) {
            pre.next = pre.next.next;
            copynode.next = copynode.next != null ? copynode.next.next : null;
            pre = pre.next;
            copynode = copynode.next;
        }
        return copyhead;

    }


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
     * 面试题 02.03. 删除中间节点
     * 实现一种算法，删除单向链表中间的某个节点（即不是第一个或最后一个节点），假定你只能访问该节点。
     * 示例：
     * <p>
     * 输入：单向链表a->b->c->d->e->f中的节点c
     * 结果：不返回任何数据，但该链表变为a->b->d->e->f
     *
     * @param node
     */
    public void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }

    /**
     * 445. 两数相加 II
     * 给你两个 非空 链表来代表两个非负整数。数字最高位位于链表开始位置。
     * 它们的每个节点只存储一位数字。将这两数相加会返回一个新的链表。
     * <p>
     * 你可以假设除了数字 0 之外，这两个数字都不会以零开头。
     * <p>
     * 进阶：
     * <p>
     * 如果输入链表不能修改该如何处理？换句话说，你不能对列表中的节点进行翻转。
     * 示例：
     * <p>
     * 输入：(7 -> 2 -> 4 -> 3) + (5 -> 6 -> 4)
     * 输出：7 -> 8 -> 0 -> 7
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers3(ListNode l1, ListNode l2) {
        //思路：放入栈中操作，对应位置数据相加得到值，如果大于10，模10将值放到下一位累加
        Deque<Integer> stack1 = new LinkedList();
        Deque<Integer> stack2 = new LinkedList();
        while (l1 != null) {
            stack1.push(l1.val);
            l1 = l1.next;
        }
        while (l2 != null) {
            stack2.push(l2.val);
            l2 = l2.next;
        }

        ListNode res = null;
        int m = 0;
        Integer m1 = null;
        Integer m2 = null;
        while (!stack1.isEmpty() || !stack2.isEmpty()) {
            m1 = stack1.peek() != null ? stack1.pop() : 0;
            m2 = stack2.peek() != null ? stack2.pop() : 0;
            System.out.println(m1 + "  " + m2);
            int i = m1 + m2 + m;
            m = i / 10;
            ListNode newnode = new ListNode(i % 10);
            newnode.next = res;
            res = newnode;
        }
        if (m > 0) {
            ListNode newnode = new ListNode(m);
            newnode.next = res;
            res = newnode;
        }
        return res;

    }

    /**
     * 143. 重排链表
     * 给定一个单链表 L：L0→L1→…→Ln-1→Ln ，
     * 将其重新排列后变为： L0→Ln→L1→Ln-1→L2→Ln-2→…
     * <p>
     * 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
     * <p>
     * 示例 1:
     * <p>
     * 给定链表 1->2->3->4, 重新排列为 1->4->2->3.
     * 示例 2:
     * <p>
     * 给定链表 1->2->3->4->5, 重新排列为 1->5->2->4->3.
     *
     * @param head
     */
    public void reorderList(ListNode head) {
        List<ListNode> list = new ArrayList();
        if (head.next == null) {
            return;
        }
        int n = 0;
        while (head != null) {
            list.add(head);
            head = head.next;
            list.get(n).next = null;
            n++;
        }

        int size = list.size();
        ListNode newhead = list.get(0);

        int i = 0, j = list.size() - 1;
        while (i < j) {
            list.get(i).next = list.get(j);
            i++;
            if (i == j) {
                break;
            }
            list.get(j).next = list.get(i);
            j--;
        }
    }

    /**
     * 给定一个链表，如果它是有环链表，实现一个算法返回环路的开头节点。
     * <p>
     * 如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。
     * 为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。
     * 如果 pos 是 -1，则在该链表中没有环。注意：pos 不作为参数进行传递，仅仅是为了标识链表的实际情况。
     *
     * @param head
     * @return
     */
    public ListNode detectCycle(ListNode head) {
        int pos = 0;
        if (head.next == null) {
            pos = -1;
            return head;
        }
        ListNode slow = head, fast = head;

        do {
            if (fast == null || fast.next == null) {
                pos = -1;
                return null;
            }
            slow = slow.next;
            fast = fast.next.next;
        } while (slow != fast);

        slow = head;

        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
            pos++;
        }
        return slow;
    }

    /**
     * 剑指 Offer 18. 删除链表的节点
     * 给定单向链表的头指针和一个要删除的节点的值，定义一个函数删除该节点。
     *
     * 返回删除后的链表的头节点。
     *
     * 注意：此题对比原题有改动
     * @param head
     * @param val
     * @return
     */
    public ListNode deleteNode(ListNode head, int val) {
        if (head == null) {
            return head;
        }
        ListNode hair = new ListNode(0);
        hair.next = head;
        ListNode newhead = hair;
        ListNode pre = head;
        if (val == head.val) {
            hair.next = pre.next;
            pre = null;
            return hair.next;
        }
        int i ;
        while (pre != null) {
            i = pre.val;
            if (i == val) {
                if (pre.next == null) {
                    newhead.next = null;
                } else {
                    newhead.next = pre.next;
                    pre.next = null;
                }
                return hair.next;
            }
            newhead = pre;
            pre = pre.next;
        }
        return hair.next;
    }

    /**
     * 725. 分隔链表
     * 给定一个头结点为 root 的链表, 编写一个函数以将链表分隔为 k 个连续的部分。
     * 每部分的长度应该尽可能的相等: 任意两部分的长度差距不能超过 1，也就是说可能有些部分为 null。
     * 这k个部分应该按照在链表中出现的顺序进行输出，并且排在前面的部分的长度应该大于或等于后面的长度。
     * 返回一个符合上述规则的链表的列表。
     * 举例： 1->2->3->4, k = 5 // 5 结果 [ [1], [2], [3], [4], null ]
     * 示例 1：
     * 输入:
     * root = [1, 2, 3], k = 5
     * 输出: [[1],[2],[3],[],[]]
     * 解释:
     * 输入输出各部分都应该是链表，而不是数组。
     * 例如, 输入的结点 root 的 val= 1, root.next.val = 2, \root.next.next.val = 3, 且 root.next.next.next = null。
     * 第一个输出 output[0] 是 output[0].val = 1, output[0].next = null。
     * 最后一个元素 output[4] 为 null, 它代表了最后一个部分为空链表。
     * 示例 2：
     *
     * 输入:
     * root = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], k = 3
     * 输出: [[1, 2, 3, 4], [5, 6, 7], [8, 9, 10]]
     * @param root
     * @param k
     * @return
     */
    public ListNode[] splitListToParts(ListNode root, int k) {
        int length = 0;
        ListNode[] res = new ListNode[k];
        if (root == null) {
            return res;
        }
        ListNode pre = root;
        while (pre != null) {
            length++;
            pre = pre.next;
        }
        int[] split = splitNum(length,k);
        pre = root;
        int num;
        for (int i = 0;i < k;i++) {
            num = split[i];
            ListNode newhead = pre;
            ListNode tail = pre;
            for (int j = 0; j < num - 1; j++) {
                tail = tail.next;
            }
            if (tail == null) {
                return res;
            }
            pre = tail.next;
            tail.next = null;
            res[i] = newhead;
        }
        return res;
    }

    /**
     * 将指定数字m分为k份，任意两部分的长度差距不能超过 1
     * 先相除后向上向下取整，已最大值与K相乘取一个数，减去m就是最小数的个数
     * @param m
     * @param k
     * @return
     */
    public int[] splitNum(int m,int k) {
        double m1 = m;
        double k1 = k;
        int resup = (int) Math.ceil(m1/k1);
        int resdown = (int) Math.floor(m1/k1);
        int big = k * resup;
        int scount = big - m;
        int bcount = (m - scount * resdown)/resup;
        int[] res = new int[bcount + scount];
        for (int i = 0; i < bcount; i ++) {
            res[i] = resup;
        }
        for (int i = 0; i < scount; i ++) {
            res[bcount+i] = resdown;
        }

        return res;
    }

    /**
     * 面试题 02.04. 分割链表
     * 编写程序以 x 为基准分割链表，使得所有小于 x 的节点排在大于或等于 x 的节点之前。
     * 如果链表中包含 x，x 只需出现在小于 x 的元素之后(如下所示)。分割元素 x 只需处于“右半部分”即可，
     * 其不需要被置于左右两部分之间。
     *
     * 示例:
     *
     * 输入: head = 3->5->8->5->10->2->1, x = 5
     * 输出: 3->1->2->10->5->5->8
     * @param head
     * @param x
     * @return
     */
    public ListNode partition(ListNode head, int x) {
        ListNode small = new ListNode(0);
        ListNode smallHead = small;
        ListNode large = new ListNode(0);
        ListNode largeHead = large;
        while (head != null) {
            if (head.val < x) {
                smallHead.next = head;
                smallHead = smallHead.next;
            } else {
                largeHead.next = head;
                largeHead = largeHead.next;
            }
            head = head.next;
        }
        smallHead.next = large.next;
        largeHead.next = null;
        return small.next;
    }

    /**
     * 779. 第K个语法符号
     * 在第一行我们写上一个 0。接下来的每一行，将前一行中的0替换为01，1替换为10。
     *
     * 给定行数 N 和序数 K，返回第 N 行中第 K个字符。（K从1开始）
     * @param N
     * @param K
     * @return
     */
    public int kthGrammar(int N, int K) {
        //时间复杂度太高
//        String num = "0,";
//        String[] nums ;
//        int length;
//        for (int i = 0 ; i < N;i++) {
//            nums = num.split(",");
//            num = "";
//            length = nums.length;
//            for (int j = 0;j < length; j++) {
//                if (nums[j] == null) {
//                    break;
//                }
//                if (nums[j].equals("0")) {
//                    nums[j] = "0,1,";
//                } else {
//                    nums[j] = "1,0,";
//                }
//                num += nums[j];
//            }
//        }
//        num = num.replace(",","");
//        return Integer.parseInt(String.valueOf(num.charAt(K -1)));
        if (N == 0 || N == 1) {
            return 0;
        }
        //i为K对应N-1行，由哪个字符转换的
        int i = K/2 + K%2;
        int num = kthGrammar(N-1,i);
        if (num == 1) {
            if (K%2==1) {
                return 1;
            } else {
                return 0;
            }
        } else {
            if (K%2==1) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    /**
     * 剑指 Offer 10- I. 斐波那契数列
     * 写一个函数，输入 n ，求斐波那契（Fibonacci）数列的第 n 项（即 F(N)）。斐波那契数列的定义如下：
     *
     * F(0) = 0,   F(1) = 1
     * F(N) = F(N - 1) + F(N - 2), 其中 N > 1.
     * 斐波那契数列由 0 和 1 开始，之后的斐波那契数就是由之前的两数相加而得出。
     *
     * 答案需要取模 1e9+7（1000000007），如计算初始结果为：1000000008，请返回 1
     * @param n
     * @return
     */
    public int fib(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int[] fn = new int[n+1];
        fn[0] = 0;
        fn[1] = 1;
        for (int i = 2; i <= n ; i++) {
            fn[i] = fn[i -1] + fn [i - 2];
            fn[i] %= 1000000007;
        }
        return fn[n];
    }

    public int fib1(int n){
        //有问题，有大量重复计算
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return fib1(n-1) + fib1(n -2);
    }

    /**
     * 3. 无重复字符的最长子串
     * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
     * 示例 1:
     * 输入: s = "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        if (s.isEmpty()) {
            return 0;
        } else if (s.length() == 1) {
            return 1;
        }
        int length = s.length();
        StringBuilder sb = new StringBuilder();
        char end ;
        int res = 0;
        int endindex;
        for (int i = 0;i < length;i++) {
            end = s.charAt(i);
            if (sb.indexOf(String.valueOf(end)) >= 0) {
                if (sb.length() > res) {
                    res = sb.length();
                }
                endindex = sb.indexOf(String.valueOf(end));
                sb.delete(0, endindex + 1);
            }
            sb.append(end);
        }
        if (sb.length() > res) {
            res = sb.length();
        }
        return res;
    }

    /**
     * 4. 寻找两个正序数组的中位数
     * 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的 中位数 。
     * 示例 1：
     * 输入：nums1 = [1,3], nums2 = [2]
     * 输出：2.00000
     * 解释：合并数组 = [1,2,3] ，中位数 2
     * 示例 2：
     * 输入：nums1 = [1,2], nums2 = [3,4]
     * 输出：2.50000
     * 解释：合并数组 = [1,2,3,4] ，中位数 (2 + 3) / 2 = 2.5
     * 示例 3：
     * 输入：nums1 = [0,0], nums2 = [0,0]
     * 输出：0.00000
     * 示例 4：
     * 输入：nums1 = [], nums2 = [1]
     * 输出：1.00000
     * 示例 5：
     * 输入：nums1 = [2], nums2 = []
     * 输出：2.00000
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        return 0;
    }

    /**
     * 5. 最长回文子串
     * 给你一个字符串 s，找到 s 中最长的回文子串
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        if (s == null) {
            return null;
        }

        int length = s.length();
        if (length == 1) {
            return s;
        }
        char end;
        char begin;
        int count = 0;
        int longcount = 0;
        StringBuilder res = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            end = s.charAt(i);
            sb.append(end);
            if (count == 0) {
                if ( i > 0 && (end == s.charAt(i - 1)
                    || end == s.charAt(i - 2 >=0 ? i -2:0)
                )) {
                    count++;
                }
            } else {
                begin = s.charAt(i - 1 - count >=0 ? i - 1 - count:0);
                if (end == begin) {
                    count++;
                } else {
                    if (count > longcount || res == null) {
                        res = sb.delete(0,sb.indexOf(String.valueOf(begin))+1)
                                .deleteCharAt(sb.length() -1);
                        sb = sb.delete(0,sb.length()-1);
                    }
                    longcount = count;
                }
            }
        }
        return res.toString();
    }

//    public static void main(String[] args) {
//        Solution solution = new Solution();
//        ListNode node1 = new ListNode(1);
//        ListNode node2 = new ListNode(2);
//        ListNode node3 = new ListNode(3);
//        ListNode node4 = new ListNode(4);
//        ListNode node5 = new ListNode(5);
//        node1.next = node2;
//        node2.next = node3;
//        node3.next = node4;
//        node4.next = node5;
//        node5.next = node3;
//        solution.detectCycle(node1);
//        ListNode[] res = solution.splitListToParts(node1,5);
//        System.out.println(res);
//        solution.longestPalindrome("babad");
//    }

    public static boolean isValid(String s) {
       // 请在此作答
        String[] chars = s.split("");
        Deque<String> ss1 = new LinkedList<>();
        Deque<String> ss2 = new LinkedList<>();
        for (int i = 0; i < chars.length; i ++) {
            ss1.push(chars[i]);
        }
        boolean flag = true;
        while (!ss1.isEmpty()) {
            if (ss2.isEmpty()) {
                ss2.push(ss1.pop());
            } else {
                if (ss1.peek().equals("(") && ss2.peek().equals(")")) {
                    ss1.pop();
                    ss2.pop();
                } else if (ss1.peek().equals("[") && ss2.peek().equals("]")) {
                    ss1.pop();
                    ss2.pop();
                } else if (ss1.peek().equals("{") && ss2.peek().equals("}")) {
                    ss1.pop();
                    ss2.pop();
                } else {
                    ss2.push(ss1.pop());
                }
            }
        }
        if (!ss1.isEmpty() || !ss2.isEmpty()) {
            flag = false;
        }
        return flag;

    }

    /**
     * 1367. 二叉树中的列表
     * 给你一棵以 root 为根的二叉树和一个 head 为第一个节点的链表。
     *
     * 如果在二叉树中，存在一条一直向下的路径，且每个点的数值恰好一一对应以 head 为首的链表中每个节点的值，那么请你返回 True ，否则返回 False 。
     *
     * 一直向下的路径的意思是：从树中某个节点开始，一直连续向下的路径。
     * @param head
     * @param root
     * @return
     */
    public boolean isSubPath(ListNode head, TreeNode root) {
        if (head == null) {
            return true;
        }
        if (root == null) {
            return false;
        }
        if (head.val == root.val && judge(head,root)) {
            return true;
        }

        return isSubPath(head,root.right) || isSubPath(head,root.left);
    }

    public boolean judge(ListNode head, TreeNode root) {
        if (head == null) {
            return true;
        }
        if (root == null) {
            return false;
        }
        if (head.val != root.val) {
            return false;
        }
        return judge(head.next,root.left) || judge(head.next,root.right);
    }

//    class Node {
//        public int val;
//        public Node left;
//        public Node right;
//
//        public Node() {}
//
//        public Node(int _val) {
//            val = _val;
//        }
//
//        public Node(int _val,Node _left,Node _right) {
//            val = _val;
//            left = _left;
//            right = _right;
//        }
//    }

    /**
     * 剑指 Offer 36. 二叉搜索树与双向链表
     * 输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的循环双向链表。要求不能创建任何新的节点，只能调整树中节点指针的指向。
     *
     *
     * @param root
     * @return
     */
//    Node pre,head;
//    public Node treeToDoublyList(Node root) {
//        if (root == null) {
//            return null;
//        }
//        treeToDoublyList(root.left);
//        linkedTree(root);
//
//        treeToDoublyList(root.right);
//        head.left = pre;
//        pre.right = head;
//        return head;
//    }
//
//    public void linkedTree(Node root) {
//        if (pre == null) {
//            head = root;
//        } else {
//            pre.right = root;
//        }
//        root.left = pre;
//        pre = root;
//    }

    /**
     * 437. 路径总和 III
     * 给定一个二叉树的根节点 root ，和一个整数 targetSum ，求该二叉树里节点值之和等于 targetSum 的 路径 的数目。
     *
     * 路径 不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的（只能从父节点到子节点）。
     * @param root
     * @param targetSum
     * @return
     */
    public int pathSum(TreeNode root, int targetSum) {
        HashMap<Integer,Integer> map = new HashMap<>();
        map.put(0,1);
        return count(root,0,targetSum,map);
    }

    public int count(TreeNode root,int sum, int targetSum, HashMap<Integer,Integer> map) {
        if (root == null) {
            return 0;
        }
        sum += root.val;
        int res = map.getOrDefault(sum - targetSum,0);
        map.put(sum, map.getOrDefault(sum,0) + 1);
        res += count(root.left,sum,targetSum,map);
        res += count(root.right,sum,targetSum,map);
        map.put(sum,map.getOrDefault(sum,0) - 1);
        return res;
    }

    /**
     * 172. 阶乘后的零
     * 给定一个整数 n，返回 n! 结果尾数中零的数量。
     *
     * 示例 1:
     *
     * 输入: 3
     * 输出: 0
     * 解释: 3! = 6, 尾数中没有零。
     * @param n
     * @return
     */
    public int trailingZeroes(int n) {
        //2 * 5 才会等于0 ，实际求有多少对2 和 5，2 实际比 5 多，实际就是有多少个5
        int m = 5,res = 0;
        while (n / m != 0) {
            res += n / m;
            m *= 5;
        }
        return res;
    }

    /**
     * 395. 至少有 K 个重复字符的最长子串
     * 给你一个字符串 s 和一个整数 k ，请你找出 s 中的最长子串， 要求该子串中的每一字符出现次数都不少于 k 。返回这一子串的长度。
     * @param s
     * @param k
     * @return
     */
    public int longestSubstring(String s, int k) {
        HashMap<Character,Integer> map = new HashMap<>();
        LinkedList<Integer> list = new LinkedList<>();
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i ++) {
            map.put(chars[i],map.getOrDefault(chars[i],0) + 1);
        }
        //确定分割点
        for (int i = 0; i < chars.length; i ++) {
            if (map.get(chars[i]) < k) {
                list.addLast(i);
            }
        }
        list.addLast(s.length());
        if (list.size() == 1) {
            return s.length();
        }
        int pre = 0,ans = 0;
        for (int i : list) {
            int len = i - pre;
            if (len >= k) {
                ans = Math.max(ans, longestSubstring(s.substring(pre,i),k));
            }
            pre = i + 1;
        }
        return ans;
    }

    /**
     * 8. 字符串转换整数 (atoi)
     * 请你来实现一个 myAtoi(string s) 函数，使其能将字符串转换成一个 32 位有符号整数（类似 C/C++ 中的 atoi 函数）。
     *
     * 函数 myAtoi(string s) 的算法如下：
     *
     * 读入字符串并丢弃无用的前导空格
     * 检查下一个字符（假设还未到字符末尾）为正还是负号，读取该字符（如果有）。 确定最终结果是负数还是正数。 如果两者都不存在，则假定结果为正。
     * 读入下一个字符，直到到达下一个非数字字符或到达输入的结尾。字符串的其余部分将被忽略。
     * 将前面步骤读入的这些数字转换为整数（即，"123" -> 123， "0032" -> 32）。如果没有读入数字，则整数为 0 。必要时更改符号（从步骤 2 开始）。
     * 如果整数数超过 32 位有符号整数范围 [−2^31,  2^31 − 1] ，需要截断这个整数，使其保持在这个范围内。具体来说，
     * 小于 −2^31 的整数应该被固定为 −2^31 ，大于 2^31 − 1 的整数应该被固定为 2^31 − 1 。
     * 返回整数作为最终结果。
     * @param s
     * @return
     */
    public int myAtoi(String s) {
        char[] chars = s.toCharArray();
        int len = chars.length;
        int idx = 0;
        for (int i = 0; i < len; i ++) {
            if (chars[i] == ' ') {
                idx ++;
            }
            if (i > idx) {
                break;
            }
        }
        if (idx == len ) {
            return 0;
        }
        char flag = '+';
        if (chars[idx] == '-') {
            flag = '-';
            idx ++;
        } else if (chars[idx] == '+') {
            idx ++;
        }
        int num = 0;
        int max = Integer.MAX_VALUE / 10, m = Integer.MAX_VALUE % 10;
        for (int i = idx ; i < len ; i ++) {
            if (chars[i] < '0' || chars[i] > '9') {
                break;
            }
            if ((num == max && (chars[i] - '0') > m) || num > max) {
                if (flag == '-') {
                    return Integer.MIN_VALUE;
                } else {
                    return Integer.MAX_VALUE;
                }
            }
            num = num * 10 + (chars[i] - '0');
        }
        return flag == '-'? - num: num;
    }

    /**
     * 402. 移掉 K 位数字
     * 给你一个以字符串表示的非负整数 num 和一个整数 k ，移除这个数中的 k 位数字，
     * 使得剩下的数字最小。请你以字符串形式返回这个最小的数字。
     * 示例 1 ：
     *
     * 输入：num = "1432219", k = 3
     * 输出："1219"
     * 解释：移除掉三个数字 4, 3, 和 2 形成一个新的最小的数字 1219 。
     * @param num
     * @param k
     * @return
     */
    public String removeKdigits(String num, int k) {
        Deque<Character> stack = new ArrayDeque<>();
        int len = num.length();
        char[] chars = num.toCharArray();
        for (int i = 0; i < len; i ++) {
            char pre = chars[i];
            while (!stack.isEmpty() && k > 0 && pre < stack.peekLast()) {
                stack.pollLast();
                k --;
            }
            stack.addLast(pre);
        }
        for (int i = 0; i < k ; i ++) {
            stack.pollLast();
        }
        StringBuilder sb = new StringBuilder();
        boolean flag = true;
        while (!stack.isEmpty()) {
            char c = stack.pollFirst();
            if (c == '0' && flag) {
                continue;
            }
            sb.append(c);
            flag = false;
        }
        return sb.length() == 0 ? "0" : sb.toString();
    }

    /**
     * 1081. 不同字符的最小子序列
     * 返回 s 字典序最小的子序列，该子序列包含 s 的所有不同字符，且只包含一次。
     *
     * 注意：该题与 316 https://leetcode.com/problems/remove-duplicate-letters/ 相同
     * 示例 1：
     *
     * 输入：s = "bcabc"
     * 输出："abc"
     * 示例 2：
     *
     * 输入：s = "cbacdcbc"
     * 输出："acdb"
     * @param s
     * @return
     */
    public String smallestSubsequence(String s) {
        int len = s.length();
        char[] chars = s.toCharArray();
        int[] counts = new int[26];

        for (int i = 0; i < len ; i ++) {
            counts[chars[i] - 'a'] ++;
        }
        Set<Character> set = new HashSet<>();
        Deque<Character> deque = new ArrayDeque<>();
        for (char c : chars) {
            if (!set.contains(c)) {
                while (!deque.isEmpty() && c < deque.peekLast() && counts[deque.peekLast() - 'a'] > 0) {
                    set.remove(deque.peekLast());
                    deque.pollLast();
                }
                set.add(c);
                deque.addLast(c);
            }
            counts[c - 'a'] --;
        }
        StringBuilder sb = new StringBuilder();
        while (!deque.isEmpty()) {
            sb.append(deque.pollFirst());
        }
        return sb.toString();
    }

    /**
     * 958. 二叉树的完全性检验
     * 给定一个二叉树，确定它是否是一个完全二叉树。
     *
     * 百度百科中对完全二叉树的定义如下：
     *
     * 若设二叉树的深度为 h，除第 h 层外，其它各层 (1～h-1) 的结点数都达到最大个数，第 h 层所有的结点都连续集中在最左边，
     * 这就是完全二叉树。（注：第 h 层可能包含 1~ 2h 个节点。）
     * @param root
     * @return
     */
    public boolean isCompleteTree(TreeNode root) {
        //BFS
        Deque<TreeNode> deque = new LinkedList<>();
        deque.addLast(root);
        boolean flag = false;
        while (!deque.isEmpty()) {
            TreeNode pre = deque.pollFirst();
            if (pre.left == null && pre.right != null) {
                return false;
            }

            if (flag && (pre.left != null || pre.right != null)) {
                return false;
            }

            if (pre.right == null) {
                flag = true;
            }

            if (pre.left != null) {
                deque.addLast(pre.left);
            }

            if (pre.right != null) {
                deque.addLast(pre.right);
            }
        }
        return true;
    }

    /**
     * 464. 我能赢吗
     * 在 "100 game" 这个游戏中，两名玩家轮流选择从 1 到 10 的任意整数，累计整数和，先
     * 使得累计整数和达到或超过 100 的玩家，即为胜者。
     * 如果我们将游戏规则改为 “玩家不能重复使⽤整数” 呢？
     * 例如，两个玩家可以轮流从公共整数池中抽取从 1 到 15 的整数（不放回），直到累计整
     * 数和 >= 100。
     * 给定⼀个整数 maxChoosableInteger （整数池中可选择的最⼤数）和另⼀个整
     * 数 desiredTotal （累计和），判断先出⼿的玩家是否能稳赢（假设两位玩家游戏时都表现
     * 最佳）？
     * 你可以假设 maxChoosableInteger 不会⼤于 20， desiredTotal 不会⼤于 300。
     * @param maxChoosableInteger
     * @param desiredTotal
     * @return
     */
    Map<Integer,Boolean> map;
    public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
        map = new HashMap<>();
        //采用二进制标记法标记已经使用过的数据
        int mark = 0, m = maxChoosableInteger , total = desiredTotal;
        if ((1 + m) * m / 2 < total) {
            return false;
        }
        return canIWinDfs(mark,m,total);
    }
    public boolean canIWinDfs(int mark ,int m, int total) {
        if (map.containsKey(mark)) {
            return map.get(mark);
        }
        for (int i = 1; i <= m; i ++) {
            if ((mark & (1 << i)) != 0) {
                continue;
            }
            //其他人输或者当前获取的数大于total
            if (i > total || !canIWinDfs((mark | (1 << i)), m , total - i)) {
                map.put(mark, true);
                return true;
            }
        }
        map.put(mark,false);
        return false;
    }

    /**
     * 190. 颠倒二进制位
     * 颠倒给定的 32 位无符号整数的二进制位。
     * 提示：
     *
     * 请注意，在某些语言（如 Java）中，没有无符号整数类型。在这种情况下，
     * 输入和输出都将被指定为有符号整数类型，并且不应影响您的实现，因为无论整数是有符号的还是无符号的，其内部的二进制表示形式都是相同的。
     * 在 Java 中，编译器使用二进制补码记法来表示有符号整数。因此，在上面的 示例 2 中，输入表示有符号整数 -3，输出表示有符号整数 -1073741825。
     * @param n
     * @return
     */
    public int reverseBits(int n) {
        int res = 0;
        //定义j、k分别指向32位前后两端,k 使用逻辑右移方式
        for (int i = 0, j = 1, k = (1 << 31); i < 32 ; i ++ , j <<= 1, k >>>= 1) {
            //如果右边对应位为1 ，则左边对应位赋值1
            if ((n & j) == j) {
                res |= k;
            }
        }
        return res;
    }

    /**
     * 1499. 满足不等式的最大值
     * 给你一个数组 points 和一个整数 k 。数组中每个元素都表示二维平面上的点的坐标，并按照横坐标 x 的值从小到大排序。
     * 也就是说 points[i] = [xi, yi] ，并且在 1 <= i < j <= points.length 的前提下， xi < xj 总成立。
     *
     * 请你找出 yi + yj + |xi - xj| 的 最大值，其中 |xi - xj| <= k 且 1 <= i < j <= points.length。
     *
     * 题目测试数据保证至少存在一对能够满足 |xi - xj| <= k 的点。
     * @param points
     * @param k
     * @return
     */
    public int findMaxValueOfEquation(int[][] points, int k) {
        //|xi - xj| <= k， 是在范围为k的滑动窗口内；
        // 公式变形，yi + xi + (yj - xj),实际找到区间范围内yj - xj最大的值，使用单调递减队列
        Deque<Integer> deque = new ArrayDeque<>();
        int res = Integer.MIN_VALUE;
        for (int i = 0 ; i < points.length; i ++) {
            //移除不在范围内的点
            while (!deque.isEmpty() && points[i][0] - points[deque.peekFirst()][0] > k) {
                deque.pollFirst();
            }
            if (!deque.isEmpty()) {
                res = Math.max(res ,
                        points[i][0] - points[deque.peekFirst()][0]
                                + points[i][1] + points[deque.peekFirst()][1]
                );
            }
            while (!deque.isEmpty() && points[i][1] - points[i][0]
                    > points[deque.peekLast()][1] - points[deque.peekLast()][0]) {
                deque.pollLast();
            }
            deque.addLast(i);
        }
        return res;
    }

    /**
     * 953. 验证外星语词典
     * 某种外星语也使用英文小写字母，但可能顺序 order 不同。字母表的顺序（order）是一些小写字母的排列。
     *
     * 给定一组用外星语书写的单词 words，以及其字母表的顺序 order，只有当给定的单词在这种外星语中按字典序排列时，返回 true；否则，返回 false。
     * @param words
     * @param order
     * @return
     */
//    public boolean isAlienSorted(String[] words, String order) {
//
//    }

    /**
     * 700. 二叉搜索树中的搜索
     * 给定二叉搜索树（BST）的根节点和一个值。 你需要在BST中找到节点值等于给定值的节点。
     * 返回以该节点为根的子树。 如果节点不存在，则返回 NULL。
     * @param root
     * @param val
     * @return
     */
    TreeNode search;
    public TreeNode searchBST(TreeNode root, int val) {
        search(root,val);
        return search;
    }

    public void search(TreeNode root, int val) {
        if (root == null) {
            return;
        }
        search(root.left,val);
        if (root.val == val) {
            search = root;
            return;
        }
        search(root.right,val);
    }

    /**
     * 965. 单值二叉树
     * 如果二叉树每个节点都具有相同的值，那么该二叉树就是单值二叉树。
     *
     * 只有给定的树是单值二叉树时，才返回 true；否则返回 false。
     * @param root
     * @return
     */
    Integer preval;
    public boolean isUnivalTree(TreeNode root) {
        if (root == null) {
            return true;
        }
        if (!isUnivalTree(root.left)) {
            return false;
        }
        if (preval != null && preval != root.val) {
            return false;
        }

        preval = root.val;
        if (!isUnivalTree(root.right)) {
            return false;
        }
        return true;
    }

    /**
     * 1110. 删点成林
     * 给出二叉树的根节点 root，树上每个节点都有一个不同的值。
     *
     * 如果节点值在 to_delete 中出现，我们就把该节点从树上删去，最后得到一个森林（一些不相交的树构成的集合）。
     *
     * 返回森林中的每棵树。你可以按任意顺序组织答案。
     * @param root
     * @param to_delete
     * @return
     */
    Set<Integer> to_del;
    List<TreeNode> tlist ;
    public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
        tlist = new ArrayList<>();
        to_del = new HashSet<>();
        if (to_delete.length == 0) {
            return tlist;
        }
        for (int i = 0; i < to_delete.length; i ++) {
            to_del.add(to_delete[i]);
        }
        if (!to_del.contains(root.val)) {
            tlist.add(root);
        }
        delNodes(root);
        return tlist;

    }

    public TreeNode delNodes(TreeNode root) {
        if (root == null) {
            return root;
        }
        root.left = delNodes(root.left);
        root.right = delNodes(root.right);
        if (to_del.contains(root.val)) {
            if (root.left != null) {
                tlist.add(root.left);
            }
            if (root.right != null) {
                tlist.add(root.right);
            }
            root = null;
        }
        return root;
    }

    /**
     * 1022. 从根到叶的二进制数之和
     * 给出一棵二叉树，其上每个结点的值都是 0 或 1 。每一条从根到叶的路径都代表一个从最高有效位开始的二进制数。
     * 例如，如果路径为 0 -> 1 -> 1 -> 0 -> 1，那么它表示二进制数 01101，也就是 13 。
     *
     * 对树上的每一片叶子，我们都要找出从根到该叶子的路径所表示的数字。
     *
     * 返回这些数字之和。题目数据保证答案是一个 32 位 整数。
     *
     *
     * @param root
     * @return
     */
    public int sumRootToLeaf(TreeNode root) {
        return sumRootToLeaf(root,0);
    }

    public int sumRootToLeaf(TreeNode root,int sum) {
        if (root == null) {
            return 0;
        }
        sum = (sum << 1) + root.val;
        if (root.left == null && root.right == null) {
            return sum;
        }
        return sumRootToLeaf(root.left,sum) + sumRootToLeaf(root.right,sum);
    }

    /**
     * 876. 链表的中间结点
     * 给定一个头结点为 head 的非空单链表，返回链表的中间结点。
     *
     * 如果有两个中间结点，则返回第二个中间结点。
     * @param head
     * @return
     */
    public ListNode middleNode(ListNode head) {
        //可以使用快慢指针，快指针速度是慢指针的一倍
        List<ListNode> list = new ArrayList<>();
        ListNode p = head;
        while (p != null) {
            list.add(p);
            p = p.next;
        }
        return list.get(list.size() / 2);
    }

    /**
     * 59. 螺旋矩阵 II
     * 给你一个正整数 n ，生成一个包含 1 到 n2 所有元素，且元素按顺时针顺序螺旋排列的 n x n 正方形矩阵 matrix 。
     * @param n
     * @return
     */
    public int[][] generateMatrix(int n) {
        int[][] res = new int[n][n];
        for (int i = 0; i < n; i ++ ) {
            for (int j = 0; j < n ;j ++) {

            }
        }
        return res;
    }

    /**
     * 1480. 一维数组的动态和
     * 给你一个数组 nums 。数组「动态和」的计算公式为：runningSum[i] = sum(nums[0]…nums[i]) 。
     *
     * 请返回 nums 的动态和。
     * @param nums
     * @return
     */
    public int[] runningSum(int[] nums) {
        int[] sum = nums;
        for (int i = 1; i < nums.length; i ++) {
            sum[i] = sum[i - 1] + nums[i];
        }
        return sum;
    }

    /**
     * 151. 翻转字符串里的单词
     * 给你一个字符串 s ，逐个翻转字符串中的所有 单词 。
     *
     * 单词 是由非空格字符组成的字符串。s 中使用至少一个空格将字符串中的 单词 分隔开。
     *
     * 请你返回一个翻转 s 中单词顺序并用单个空格相连的字符串。
     *
     * 说明：
     *
     * 输入字符串 s 可以在前面、后面或者单词间包含多余的空格。
     * 翻转后单词间应当仅用一个空格分隔。
     * 翻转后的字符串中不应包含额外的空格。
     * @param s
     * @return
     */
    public String reverseWords(String s) {
        String[] strings = s.split(" ");
        StringBuilder sb = new StringBuilder();

        for (int i = strings.length - 1; i >= 0; i --) {
            if (strings[i].length() == 0) {
                continue;
            }
            if (sb.length() == 0) {
                sb.append(strings[i]);
            } else {
                sb.append(" ").append(strings[i]);
            }
        }

        return sb.toString();
    }

    /**
     * 1367. 二叉树中的列表
     * 给你一棵以 root 为根的二叉树和一个 head 为第一个节点的链表。
     *
     * 如果在二叉树中，存在一条一直向下的路径，且每个点的数值恰好一一对应以 head 为首的链表中每个节点的值，那么请你返回 True ，否则返回 False 。
     *
     * 一直向下的路径的意思是：从树中某个节点开始，一直连续向下的路径。
     * @param head
     * @param root
     * @return
     */
//    public boolean isSubPath1(ListNode head, TreeNode root) {
//        if (head == null) {
//            return true;
//        }
//        if (root == null) {
//            return false;
//        }
//        if (head.val == root.val && judgeSubPath(head,root)) {
//            return true;
//        }
//        return isSubPath1(head,root.left) || isSubPath1(head,root.right);
//    }
//
//    public boolean judgeSubPath(ListNode head, TreeNode root) {
//        if (head == null) {
//            return true;
//        }
//        if (root == null) {
//            return false;
//        }
//        if (head.val !=  root.val ) {
//            return false;
//        }
//
//        return judgeSubPath(head.next,root.left) || judgeSubPath(head.next,root.right);
//    }

    /**
     * 669. 修剪二叉搜索树
     * 给你二叉搜索树的根节点 root ，同时给定最小边界low 和最大边界 high。通过修剪二叉搜索树，
     * 使得所有节点的值在[low, high]中。修剪树不应该改变保留在树中的元素的相对结构（即，如果没有被移除，
     * 原有的父代子代关系都应当保留）。 可以证明，存在唯一的答案。
     *
     * 所以结果应当返回修剪好的二叉搜索树的新的根节点。注意，根节点可能会根据给定的边界发生改变。
     * @param root
     * @param low
     * @param high
     * @return
     */
    public TreeNode trimBST(TreeNode root, int low, int high) {
        if (root == null) {
            return null;
        }
        if (root.val < low) {
            return trimBST(root.right,low,high);
        }
        if (root.val > high) {
            return trimBST(root.left,low,high);
        }

        root.left = trimBST(root.left,low,high);
        root.right = trimBST(root.right,low,high);
        return root;
    }


    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.smallestSubsequence("cbacdcbc");

        solution.reverseWords("  Bob    Loves  Alice   ");
    }
}

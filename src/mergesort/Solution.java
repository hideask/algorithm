package mergesort;

import tree.TreeNode;

import java.util.*;

/**
 * description:Solution
 * create user: songj
 * date : 2021/5/19 13:47
 */
public class Solution {
    /**
     * 1508. 子数组和排序后的区间和
     * 给你一个数组 nums ，它包含 n 个正整数。你需要计算所有非空连续子数组的和，
     * 并将它们按升序排序，得到一个新的包含 n * (n + 1) / 2 个数字的数组。
     *
     * 请你返回在新数组中下标为 left 到 right （下标从 1 开始）的所有数字和（包括左右端点）。
     * 由于答案可能很大，请你将它对 10^9 + 7 取模后返回。
     *
     * 示例 1：
     *
     * 输入：nums = [1,2,3,4], n = 4, left = 1, right = 5
     * 输出：13
     * 解释：所有的子数组和为 1, 3, 6, 10, 2, 5, 9, 3, 7, 4 。将它们升序排序后，
     * 我们得到新的数组 [1, 2, 3, 3, 4, 5, 6, 7, 9, 10] 。
     * 下标从 le = 1 到 ri = 5 的和为 1 + 2 + 3 + 3 + 4 = 13 。
     *
     *
     * 思路
     * 1 2 3 4
     * 1 3 6 10
     *   2 5 9
     *     3 7
     *       4
     * @return
     */
    public int rangeSum(int[] nums, int n, int left, int right) {
        //创建一个小顶堆
        PriorityQueue<int[]> queue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });
        //将原始数组丢到队列中，int[] 0存值，1存下标
        for (int i = 0; i < n; i ++) {
            queue.offer(new int[]{nums[i],i});
        }

        int sum = 0;//累计和
        int curindex = 1;//第几个区间和，相当于给出的范围
        int MOD = 1000000007;
        //当区间和下标小于最右边
        while (curindex <= right) {
            //弹出后判断
            int[] temp = queue.poll();
            //当区间和下标大于左边边界，相加
            if (curindex >= left) {
                sum = (sum + temp[0] % MOD) % MOD;
            }
            int cur_sum = temp[0];
            int cur_ind = temp[1];
            //当前下标 + 1 小于数组最大长度，将区间和放入队列，参照上面的思路图
            if (cur_ind + 1 < n) {
                queue.offer(new int[]{cur_sum + nums[cur_ind + 1],cur_ind + 1});
            }
            curindex ++ ;
        }
        return sum;
     }

    /**
     * 53. 最大子序和
     * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
     * 示例 1：
     *
     * 输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
     * 输出：6
     * 解释：连续子数组 [4,-1,2,1] 的和最大，为 6 。
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {
        //转换为前缀和之间的差值，两个前缀和的差值就是他们之间连续子数组的和
        int len = nums.length;
        //前缀和中最小值
        int min = 0;
        //前缀和
        int sum = 0;
        //最大前缀和相减的值
        int ans = nums[0];
        for (int i = 0; i < len; i++) {
            sum += nums[i];
            //获取最大的前缀和的差值，减去最小的前缀和就可以得到当前前缀和最大的差值
            ans = Math.max(ans, sum - min);
            //获取最小的前缀和
            min = Math.min(sum,min);
        }
        return ans;
    }


     class Data{
        int val;//元素值
        int index;//元素下标
        int cut;//元素左边的值

        public Data(int val, int index) {
            this.val = val;
            this.index = index;
            this.cut = 0;
        }
    }

    Data[] temp ;
    /**
     * 315. 计算右侧小于当前元素的个数
     * 给定一个整数数组 nums，按要求返回一个新数组 counts。数组 counts 有该性质： counts[i] 的值是  nums[i] 右侧小于 nums[i] 的元素的数量。
     *
     * 示例：
     *
     * 输入：nums = [5,2,6,1]
     * 输出：[2,1,1,0]
     * 解释：
     * 5 的右侧有 2 个更小的元素 (2 和 1)
     * 2 的右侧仅有 1 个更小的元素 (1)
     * 6 的右侧有 1 个更小的元素 (1)
     * 1 的右侧有 0 个更小的元素
     * @param nums
     * @return
     */
    public List<Integer> countSmaller(int[] nums) {
        int len = nums.length;
        temp = new Data[nums.length];
        //初始化temp
        Arrays.fill(temp,new Data(0,0));
        //定义一个arr数组，需要归并的数据
        Data[] arr = new Data[nums.length];
        for (int i = 0; i < len ; i ++) {
            arr[i] = new Data(nums[i], i);
        }
        //归并
        mergeSort(arr,0,len - 1);
        int[] res = new int[len];
        List<Integer> list = new ArrayList<>();
        for (int i = 0;i < len; i++) {
            res[arr[i].index] = arr[i].cut;
        }
        for (int i: res) {
            list.add(i);
        }
        return list;
    }

    public void mergeSort(Data[] arr,int l, int r) {
        if (l >= r) {
            return;
        }
        int mid = (l + r) / 2;
        mergeSort(arr,l,mid);
        mergeSort(arr,mid + 1,r);
        int k = l,p1 = l, p2 = mid + 1;
        while (p1 <= mid || p2 <= r) {
            //当p1的值大于 p2 的值，p1的右边小于数+1
            if ((p2 > r) || (p1 <= mid && arr[p1].val > arr[p2].val)) {
                arr[p1].cut += (r - p2 + 1);
                temp[k++] = arr[p1++];
            } else {
                temp[k++] = arr[p2++];
            }
        }
        for (int i = l; i <= r; i ++) {
            arr[i] = temp[i];
        }
    }

    /**
     * 剑指 Offer 51. 数组中的逆序对
     * 在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。输入一个数组，求出这个数组中的逆序对的总数。
     *
     * 示例 1:
     *
     * 输入: [7,5,6,4]
     * 输出: 5
     * @param nums
     * @return
     */
    int[] rptmp;
    public int reversePairs(int[] nums) {
        int len = nums.length;
        rptmp = new int[len];
        return reversePairs(nums,0,len - 1);
    }

    public int reversePairs(int[] nums,int l,int r) {
        if (l >= r) {
            return 0;
        }
        int mid = (l + r)/2;
        int ans = 0;
        ans += reversePairs(nums, l ,mid);
        ans += reversePairs(nums,mid + 1 ,r);
        int p1 = l,p2 = mid + 1,k = l;
        while (p1 <= mid || p2 <= r) {
            if ((p2 > r) || (p1 <= mid && nums[p1] <= nums[p2])) {
                rptmp[k++] = nums[p1++];
            } else {
                //如果num[p1] > nums[p2],则p1到mid后面的都大于p2
                ans += mid - p1 + 1;
                rptmp[k++] = nums[p2++];
            }
        }
        for (int i = l; i <= r;i ++) {
            nums[i] = rptmp[i];
        }
        return ans;
    }

    /**
     * 归并排序
     * @param nums
     */
    public void mergeSort_1(int[] nums) {
        rptmp = new int[nums.length];
        mergeSort_1(nums,0,nums.length - 1);
    }

    public void mergeSort_1(int[] nums,int l,int r) {
        if (l >= r) {
            return ;
        }
        int mid = (l + r)/2;
        mergeSort_1(nums, l ,mid);
        mergeSort_1(nums,mid + 1 ,r);
        int p1 = l,p2 = mid + 1,k = l;
        while (p1 <= mid || p2 <= r) {
            if ((p2 > r) || (p1 <= mid && nums[p1] <= nums[p2])) {
                rptmp[k++] = nums[p1++];
            } else {
                rptmp[k++] = nums[p2++];
            }
        }
        for (int i = l; i <= r;i ++) {
            nums[i] = rptmp[i];
        }
    }

    /**
     * 23. 合并K个升序链表
     * 给你一个链表数组，每个链表都已经按升序排列。
     *
     * 请你将所有链表合并到一个升序链表中，返回合并后的链表
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {
        return mergeLists(lists,0,lists.length - 1);
    }

    public ListNode mergeLists(ListNode[] lists,int l,int r) {
        if (l == r) {
            return lists[l];
        }
        if (l > r) {
            return null;
        }
        int mid = (l + r)/2;
        //拆分
        return mergeTwoList(mergeLists(lists,l,mid),mergeLists(lists,mid + 1,r));
    }

    /**
     * 归并链接排序
     * @param node1
     * @param node2
     * @return
     */
    public ListNode mergeTwoList(ListNode node1 , ListNode node2) {
        if (node1 == null || node2 == null) {
            return node1 == null ? node2 : node1;
        }
        ListNode head = new ListNode(0);
        ListNode a = node1,b = node2,tail = head;
        while (a != null || b != null) {
            if ((b == null) || (a != null && a.val < b.val)) {
                tail.next = a;
                a = a.next;
            } else {
                tail.next = b;
                b = b.next;
            }

            tail = tail.next;
        }
        return head.next;
    }

    /**
     * 148. 排序链表
     * 题⽬描述：给你链表的头结点 head ，请将其按升序排列并返回 排序后的链表 。
     * ⽰例：head = [4,2,1,3] 输出：[1,2,3,4]
     * 思路：我们先计算出链表的⻓度然后将链表不断地进⾏分区，直到不可切分，然后进⾏合并。
     * @param head
     * @return
     */
    public ListNode sortList(ListNode head) {
        int n = 0;
        ListNode p = head;
        while (p != null) {
            p = p.next;
            n ++;
        }
        return sortList(head,n);
    }

    public ListNode sortList(ListNode head ,int n) {
        if (n <= 1) {
            return head;
        }
        //拆分链表
        int l_cd = n / 2;//左边链表大小
        int r_cd = n - l_cd;//右边链表大小
        ListNode l = head;
        ListNode p = l;
        //找到右边起始链表
        for (int i = 1;i < l_cd; i ++) {
            p = p.next;
        }
        ListNode r = p.next;
        p.next = null;//将右边链表从原链表断开
        l = sortList(l, l_cd);
        r = sortList(r, r_cd);
        //归并排序
        ListNode tmp = new ListNode(0);
        p = tmp;
        while (l != null || r != null) {
            if ((r == null) || (l != null && l.val < r.val)) {
                p.next = l;
                l = l.next;
            } else {
                p.next = r;
                r = r.next;
            }
            p = p.next;
        }
        return tmp.next;
    }

    /**
     * 1305. 两棵二叉搜索树中的所有元素
     * 给你 root1 和 root2 这两棵二叉搜索树。
     *
     * 请你返回一个列表，其中包含 两棵树 中的所有整数并按 升序 排序。.
     * @param root1
     * @param root2
     * @return
     */
    public List<Integer> getAllElements(TreeNode root1, TreeNode root2) {
        List<Integer> list1 = new ArrayList<>();
        getAllElements(root1,list1);
        List<Integer> list2 = new ArrayList<>();
        getAllElements(root2,list2);
        List ans = new ArrayList();
        int size1 = list1.size();
        int size2 = list2.size();
        int i = 0,j = 0;
        //对两个集合进行归并排序
        while (i < size1 || j < size2) {
            if (j >= size2 || (i < size1 && list1.get(i) < list2.get(j))) {
                ans.add(list1.get(i++));
            } else {
                ans.add(list2.get(j++));
            }
        }
        return ans;
    }

    /**
     * 将root树转化为左中右的list集合
     * @param root
     * @param list
     */
    public void getAllElements(TreeNode root, List list) {
        if (root == null) {
            return;
        }
        getAllElements(root.left,list);
        list.add(root.val);
        getAllElements(root.right,list);
    }


    /**
     * 327. 区间和的个数
     * 给你一个整数数组 nums 以及两个整数 lower 和 upper 。求数组中，值位于范围 [lower, upper]
     * （包含 lower 和 upper）之内的 区间和的个数 。
     *
     * 区间和 S(i, j) 表示在 nums 中，位置从 i 到 j 的元素之和，包含 i 和 j (i ≤ j)。
     * 示例 1：
     * 输入：nums = [-2,5,-1], lower = -2, upper = 2
     * 输出：3
     * 解释：存在三个区间：[0,0]、[2,2] 和 [0,2] ，对应的区间和分别是：-2 、-1 、2 。
     * @param nums
     * @param lower
     * @param upper
     * @return
     */
    long[] tp;
    public int countRangeSum(int[] nums, int lower, int upper) {
        long[] sum = new long[nums.length + 1];
        tp = new long[sum.length];
        for (int i = 0;i < nums.length; i ++) {
            sum[i + 1] = sum[i] + nums[i];
        }
        return countRangeSum(sum, lower, upper, 0, sum.length - 1);
    }

    public int countRangeSum(long[] nums, int lower, int upper, int l, int r) {
        if (l >= r) {
            return 0;
        }
        int mid = (l + r) / 2;
        int ans = 0;
        ans += countRangeSum(nums,lower,upper, l, mid);
        ans += countRangeSum(nums,lower,upper, mid + 1, r);

        int k = l, i = mid + 1,j = mid + 1;
        while (k <= mid) {
            while (i <= r && nums[i] - nums[k] < lower) {
                i ++;
            }
            while (j <= r && nums[j] - nums[k] <= upper) {
                j ++ ;
            }
            ans += j - i;
            k ++;
        }
        k = l;
        i = l;
        j = mid + 1;
        while (i <= mid || j <= r) {
            if (j > r || (i <= mid && nums[i] < nums[j])) {
                tp[k ++] = nums[i ++];
            } else {
                tp[k ++] = nums[j ++];
            }
        }

        for (int m = l; m <= r; m ++) {
            nums[m] = tp[m];
        }
        return ans;
    }


    public static void main(String[] args) {
        Solution solution = new Solution();
//        int[] nums = new int[]{2,6,4,8,6,5,1,9};
//        int[] ans = new int[nums.length];
//        solution.mergeSort_1(nums);
//        System.out.println(nums);

        int[] nums = new int[]{-2,5,-1};
        solution.countRangeSum(nums, -2, 2);

    }
}

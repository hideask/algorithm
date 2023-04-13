package heap;

import java.lang.reflect.WildcardType;
import java.util.*;

/**
 * description:Solution
 * create user: songj
 * date : 2021/4/10 15:42
 */
public class Solution {
    /**
     * 剑指 Offer 40. 最小的k个数
     * 输入整数数组 arr ，找出其中最小的 k 个数。例如，输入4、5、1、6、2、7、3、8这8个数字，
     * 则最小的4个数字是1、2、3、4。
     * @param arr
     * @param k
     * @return
     */
    public int[] getLeastNumbers(int[] arr, int k) {
        if (arr.length == 0 || k == 0) {
            return new int[0];
        }
        //优先队列，默认是小顶堆
//        PriorityQueue queue = new PriorityQueue(new Comparator<Integer>() {
//            @Override
//            public int compare(Integer o1, Integer o2) {
//                //大顶堆
//                return o2 - o1;
//            }
//        });


        PriorityQueue<Integer> queue = new PriorityQueue<>((o1, o2) -> o2 - o1);
        for (int i = 0 ;i < k ;i++) {
            queue.offer(arr[i]);
        }
        for (int i = k; i < arr.length; i++) {
            if (arr[i] < queue.peek()) {
                queue.offer(arr[i]);
                queue.poll();
            }
        }
        return queue.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * 1046. 最后一块石头的重量
     * 有一堆石头，每块石头的重量都是正整数。
     *
     * 每一回合，从中选出两块 最重的 石头，然后将它们一起粉碎。假设石头的重量分别为 x 和 y，且 x <= y。
     * 那么粉碎的可能结果如下：
     *
     * 如果 x == y，那么两块石头都会被完全粉碎；
     * 如果 x != y，那么重量为 x 的石头将会完全粉碎，而重量为 y 的石头新重量为 y-x。
     * 最后，最多只会剩下一块石头。返回此石头的重量。如果没有石头剩下，就返回 0。
     * @param stones
     * @return
     */
    public int lastStoneWeight(int[] stones) {
        PriorityQueue<Integer> queue = new PriorityQueue<>((o1, o2) -> o2 - o1);
        for (int i = 0 ; i < stones.length; i ++) {
            queue.offer(stones[i]);
        }
        int x,y;
        while (queue.size() > 1) {
            x = queue.poll();
            y = queue.poll();
            if (x < y) {
                queue.offer(x - y);
            }
        }
        return queue.isEmpty() ? 0 :queue.poll();
    }


    /**
     *
     *703. 数据流中的第 K 大元素
     * 设计一个找到数据流中第 k 大元素的类（class）。注意是排序后的第 k 大元素，不是第 k 个不同的元素。
     *
     * 请实现 KthLargest 类：
     *
     * KthLargest(int k, int[] nums) 使用整数 k 和整数流 nums 初始化对象。
     * int add(int val) 将 val 插入数据流 nums 后，返回当前数据流中第 k 大的元素。
     * @param k
     * @param nums
     */
    PriorityQueue<Integer> queue ;
    int K;
    public void KthLargest(int k, int[] nums) {
        queue = new PriorityQueue<Integer>();
        K = k;
        for (int i: nums) {
            add(i);
        }
    }

    public int add(int val) {
        queue.offer(val);
        if (queue.size() > K) {
            queue.poll();
        }
        return queue.peek();
    }

    /**
     * 373. 查找和最小的K对数字
     * 给定两个以升序排列的整形数组 nums1 和 nums2, 以及一个整数 k。
     *
     * 定义一对值 (u,v)，其中第一个元素来自 nums1，第二个元素来自 nums2。
     *
     * 找到和最小的 k 对数字 (u1,v1), (u2,v2) ... (uk,vk)。
     */
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        PriorityQueue<Integer[]> queue = new PriorityQueue<>(new Comparator<Integer[]>() {
            @Override
            public int compare(Integer[] o1, Integer[] o2) {
                return (o2[0] + o2[1]) - (o1[0] + o1[1]);
            }
        });
        for (int i = 0;i < nums1.length;i ++) {
            for (int j = 0;j <nums2.length;j++) {
                queue.offer(new Integer[]{nums1[i],nums2[j]});
                if (queue.size() > k) {
                    queue.poll();
                }
            }
        }
        List<List<Integer>> res = new ArrayList<>();
        while (!queue.isEmpty()) {
            res.add(Arrays.asList(queue.poll()));
        }
        return res;
    }

    /**
     * 优化版本
     * @param nums1
     * @param nums2
     * @param k
     * @return
     */
    public List<List<Integer>> kSmallestPairs1(int[] nums1, int[] nums2, int k) {
        PriorityQueue<Integer[]> queue = new PriorityQueue<>(new Comparator<Integer[]>() {
            @Override
            public int compare(Integer[] o1, Integer[] o2) {
                return o2[2] - o1[2];
            }
        });
        for (int i = 0;i < nums1.length;i ++) {
            for (int j = 0;j <nums2.length;j++) {
                //使用两个和先筛选一遍
                if (queue.size() < k || (nums1[i]+ nums2[j] < queue.peek()[2])) {
                    queue.offer(new Integer[]{nums1[i],nums2[j], nums1[i]+ nums2[j]} );
                    if (queue.size() > k) {
                        queue.poll();
                    }
                } else {
                    //因为是从小到大有序的，不满足就
                    break;
                }
            }
        }
        List<List<Integer>> res = new ArrayList<>();
        while (!queue.isEmpty()) {
            Integer[] ints = queue.poll();
            res.add(new ArrayList<Integer>(){{
                this.add(ints[0]);
                this.add(ints[1]);
            }});
        }
        return res;
    }

    /**
     * 215. 数组中的第K个最大元素
     * 在未排序的数组中找到第 k 个最大的元素。请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> queue = new PriorityQueue();
        for (int i : nums) {
//            if (queue.isEmpty() || i > queue.peek()) {
//                queue.offer(i);
//            } else  {
//                if (queue.size() + 1 == k && i <= queue.peek()) {
//                    return i;
//                }
//            }
            queue.offer(i);
            if (queue.size() > k) {
                queue.poll();
            }
        }
        return queue.peek();
    }

    /**
     * 692. 前K个高频单词
     * 给一非空的单词列表，返回前 k 个出现次数最多的单词。
     *
     * 返回的答案应该按单词出现频率由高到低排序。如果不同的单词有相同出现频率，按字母顺序排序。
     */
    public List<String> topKFrequent(String[] words, int k) {
        HashMap<String,Integer> map = new HashMap<>();
        for (String word : words) {
            //getOrDefault获取value值，没有则返回0
            map.put(word,map.getOrDefault(word,0)+1);
        }
        PriorityQueue<Map.Entry<String,Integer>> queue = new PriorityQueue(new Comparator<Map.Entry<String,Integer>>() {

            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                //如果不同的单词有相同出现频率，按字母顺序排序，比较asc码
                return o1.getValue() == o2.getValue() ? o2.getKey().compareTo(o1.getKey()) : o1.getValue() - o2.getValue();
            }
        });

        for (Map.Entry<String,Integer> entry:map.entrySet()) {
            queue.offer(entry);
            if (queue.size() > k) {
                queue.poll();
            }
        }
        List<String> list = new ArrayList<>();
        while (!queue.isEmpty()){
            //头插法
            list.add(0,queue.poll().getKey());
        }
        return list;
    }

    /**
     * 4. 寻找两个正序数组的中位数
     * 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。
     * 请你找出并返回这两个正序数组的 中位数 。
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        PriorityQueue<Integer> squeue = new PriorityQueue();;
        PriorityQueue<Integer> lqueue = new PriorityQueue<>((o1, o2) -> o2 - o1);
        int l1 = nums1.length;
        int l2 = nums2.length;
        int[] nums3 = new int[l1 + l2];
        for (int i = 0 ;i < l1;i++) {
            nums3[i] = nums1[i];
        }
        for (int i= 0;i < l2;i ++) {
            nums3[i + l1] = nums2[i];
        }
        for (int i : nums3) {
            squeue.offer(i);
            lqueue.offer(squeue.poll());
            if (lqueue.size() > squeue.size()) {
                squeue.offer(lqueue.poll());
            }
        }
        if (squeue.size() == lqueue.size()) {
            return (squeue.peek() + lqueue.peek())/2.0d;
        }
        return squeue.peek();

    }

    /**
     * 264. 丑数 II
     * 给你一个整数 n ，请你找出并返回第 n 个 丑数 。
     *
     * 丑数 就是只包含质因数 2、3 和/或 5 的正整数。
     * 示例 1：
     *
     * 输入：n = 10
     * 输出：12
     * 解释：[1, 2, 3, 4, 5, 6, 8, 9, 10, 12] 是由前 10 个丑数组成的序列。
     * 示例 2：
     *
     * 输入：n = 1
     * 输出：1
     * 解释：1 通常被视为丑数。
     * @param n
     * @return
     */
    public int nthUglyNumber(int n) {
        int[] ints = new int[]{2,3,5};
        HashSet<Long> hashSet = new HashSet<>();
        PriorityQueue<Long> queue = new PriorityQueue<>();
        queue.offer(1l);
        hashSet.add(1l);
        int result = 0;
        for (int i = 0; i < n;i++) {
            long cur = queue.poll();
            result = (int) cur;
            for (int num:ints) {
                long next = cur * num;
                if (hashSet.add(next)) {
                    queue.offer(next);
                }
            }
        }
        return result;
    }

    /**
     * 313. 超级丑数
     * 编写一段程序来查找第 n 个超级丑数。
     *
     * 超级丑数是指其所有质因数都是长度为 k 的质数列表 primes 中的正整数。
     *
     * 示例:
     *
     * 输入: n = 12, primes = [2,7,13,19]
     * 输出: 32
     * 解释: 给定长度为 4 的质数列表 primes = [2,7,13,19]，前 12 个超级丑数序列为：[1,2,4,7,8,13,14,16,19,26,28,32] 。
     * @param n
     * @param primes
     * @return
     */
    public int nthSuperUglyNumber(int n, int[] primes) {
        HashSet<Long> hashSet = new HashSet<>();
        PriorityQueue<Long> queue = new PriorityQueue<>();
        queue.offer(1l);
        hashSet.add(1l);
        int result = 0;
        for (int i = 0; i < n;i++) {
            long cur = queue.poll();
            result = (int) cur;
            for (int num:primes) {
                long next = cur * num;
                if (hashSet.add(next)) {
                    queue.offer(next);
                }
            }
        }
        return result;
    }

    /**
     * 1801. 积压订单中的订单总数
     * 给你一个二维整数数组 orders ，其中每个 orders[i] = [pricei, amounti, orderTypei] 表示有 amounti 笔类型为 orderTypei 、价格为 pricei 的订单。
     *
     * 订单类型 orderTypei 可以分为两种：
     *
     * 0 表示这是一批采购订单 buy
     * 1 表示这是一批销售订单 sell
     * 注意，orders[i] 表示一批共计 amounti 笔的独立订单，这些订单的价格和类型相同。对于所有有效的 i ，由 orders[i] 表示的所有订单提交时间均早于 orders[i+1] 表示的所有订单。
     *
     * 存在由未执行订单组成的 积压订单 。积压订单最初是空的。提交订单时，会发生以下情况：
     *
     *   如果该订单是一笔采购订单 buy ，则可以查看积压订单中价格 最低 的销售订单 sell 。
     * 如果该销售订单 sell 的价格 低于或等于 当前采购订单 buy 的价格，
     * 则匹配并执行这两笔订单，并将销售订单 sell 从积压订单中删除。
     * 否则，采购订单 buy 将会添加到积压订单中。
     *
     * 反之亦然，如果该订单是一笔销售订单 sell ，则可以查看积压订单中价格 最高 的采购订单 buy 。
     * 如果该采购订单 buy 的价格 高于或等于 当前销售订单 sell 的价格，则匹配并执行这两笔订单，
     * 并将采购订单 buy 从积压订单中删除。否则，销售订单 sell 将会添加到积压订单中。
     * 输入所有订单后，返回积压订单中的 订单总数 。由于数字可能很大，所以需要返回对 109 + 7 取余的结果。
     */
    public int getNumberOfBacklogOrders(int[][] orders) {
        PriorityQueue<int[]> buyQueue = new PriorityQueue<>((o1, o2) -> o2[0] - o1[0]);
        PriorityQueue<int[]> sellQueue = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]);
        for (int[] order: orders) {
            int price = order[0],amount = order[1], orderType = order[2];
            if (orderType == 0) {//采购订单
                while (amount > 0 && !sellQueue.isEmpty() && sellQueue.peek()[0] <= price) {
                    if (amount >= sellQueue.peek()[1]) {
                        amount = amount - sellQueue.poll()[1];
                    } else {
                        int[] poll = sellQueue.poll();
                        sellQueue.offer(new int[]{poll[0],poll[1] - amount});
                        amount = 0;
                    }
                }
                if (amount > 0 ) {
                    buyQueue.offer(new int[]{price,amount});
                }
            } else {
                while (amount > 0 && !buyQueue.isEmpty() && buyQueue.peek()[0] >= price) {
                    if (amount >= buyQueue.peek()[1]) {
                        amount -= buyQueue.poll()[1];
                    } else {
                        int[] poll = buyQueue.poll();
                        buyQueue.offer(new int[]{poll[0],poll[1] - amount});
                        amount = 0;
                    }
                }
                if (amount > 0) {
                    sellQueue.offer(new int[]{price,amount});
                }
            }

        }

        int result = 0;
        while (!buyQueue.isEmpty()) {
            result = (result + buyQueue.poll()[1])%1000000007;
        }
        while (!sellQueue.isEmpty()) {
            result = (result + sellQueue.poll()[1])%1000000007;
        }
        return result;
    }

    /**
     * 1753. 移除石子的最大得分
     * 你正在玩一个单人游戏，面前放置着大小分别为 a、b 和 c 的 三堆 石子。
     *
     * 每回合你都要从两个 不同的非空堆 中取出一颗石子，并在得分上加 1 分。当存在 两个或更多 的空堆时，游戏停止。
     *
     * 给你三个整数 a 、b 和 c ，返回可以得到的 最大分数
     * @param a
     * @param b
     * @param c
     * @return
     */
    public int maximumScore(int a, int b, int c) {
        int scope = 0;
        PriorityQueue<Integer> queue = new PriorityQueue<>((o1, o2) -> o2 - o1);
        queue.offer(a);
        queue.offer(b);
        queue.offer(c);
        while (!queue.isEmpty()) {
            int r1 = queue.poll();
            int r2 = queue.poll();
            if (r2 == 0 || r1 == 0) {
                break;
            }
            r1 = r1 - 1;
            r2 = r2 - 1;
            scope ++;
            queue.offer(r1);
            queue.offer(r2);
        }
        return scope;
    }

    /**
     * 973. 最接近原点的 K 个点
     * 我们有一个由平面上的点组成的列表 points。需要从中找出 K 个距离原点 (0, 0) 最近的点。
     *
     * （这里，平面上两点之间的距离是欧几里德距离。）
     *
     * 你可以按任何顺序返回答案。除了点坐标的顺序之外，答案确保是唯一的。
     *
     *
     */
    public int[][] kClosest(int[][] points, int k) {
        PriorityQueue<int[]> queue = new PriorityQueue<int[]>(
                new Comparator<int[]>() {
                    @Override
                    public int compare(int[] o1, int[] o2) {
                        return (o2[0]*o2[0] + o2[1]*o2[1])
                                - (o1[0] * o1[0] + o1[1] * o1[1]);
                    }
                }

        );

        for (int[] point: points) {
            queue.offer(point);
            if (queue.size() > k) {
                queue.poll();
            }
        }

        int[][] res = new int[k][2];
        for (int i = 0; i < k;i++) {
            int[] point = queue.poll();
            res[i] = new int[] {point[0],point[1]};
        }
        return res;
    }


}

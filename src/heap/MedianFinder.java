package heap;

import java.util.PriorityQueue;

/**
 * 面试题 17.20. 连续中值
 * 随机产生数字并传递给一个方法。你能否完成这个方法，在每次产生新值时，寻找当前所有值的中间值（中位数）并保存。
 *
 * 中位数是有序列表中间的数。如果列表长度是偶数，中位数则是中间两个数的平均值。
 *
 * 例如，
 *
 * [2,3,4] 的中位数是 3
 *
 * [2,3] 的中位数是 (2 + 3) / 2 = 2.5
 *
 * 设计一个支持以下两种操作的数据结构：
 *
 * void addNum(int num) - 从数据流中添加一个整数到数据结构中。
 * double findMedian() - 返回目前所有元素的中位数。
 * description:MedianFinder
 * create user: songj
 * date : 2021/4/10 19:37
 */
public class MedianFinder {
    private PriorityQueue<Integer> squeue ;
    private PriorityQueue<Integer> lqueue;
    public MedianFinder() {
        squeue = new PriorityQueue();
        lqueue = new PriorityQueue<>((o1, o2) -> o2 - o1);

    }

    public void addNum(int num) {
        squeue.offer(num);
        lqueue.offer(squeue.poll());
        //squeue最多比lqueue多一个
        if (lqueue.size() > squeue.size() ) {
            squeue.offer(lqueue.poll());
        }
    }

    public double findMedian() {
        if (squeue.size() == lqueue.size()) {
            return (squeue.peek() +lqueue.peek())/2.0d;
        }
        return squeue.peek();
    }

    public static void main(String[] args) {
        //["MedianFinder","addNum","addNum","findMedian","addNum","findMedian"]
        //[[],[1],[2],[],[3],[]]
        MedianFinder medianFinder = new MedianFinder();
        medianFinder.addNum(-1);
        medianFinder.findMedian();
        medianFinder.addNum(-2);
        medianFinder.findMedian();
        medianFinder.addNum(-3);
        medianFinder.findMedian();
        medianFinder.addNum(-4);
        medianFinder.findMedian();
          medianFinder.addNum(-5);
        medianFinder.findMedian();
    }
}

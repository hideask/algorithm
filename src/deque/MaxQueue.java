package deque;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 剑指 Offer 59 - II. 队列的最大值
 * 请定义一个队列并实现函数 max_value 得到队列里的最大值，要求函数max_value、push_back 和 pop_front 的均摊时间复杂度都是O(1)。
 *
 * 若队列为空，pop_front 和 max_value 需要返回 -1
 */
public class MaxQueue {

    Deque<Integer> q,mq ;

    public MaxQueue() {
        q = new LinkedList<>();
        mq = new LinkedList<>();
    }

    public int max_value() {
        if (mq.size() == 0) {
            return -1;
        }
        return mq.peekFirst();
    }

    public void push_back(int value) {
        q.addLast(value);
        while (!mq.isEmpty() && mq.peekLast() < value) {
            mq.pollLast();
        }
        mq.addLast(value);
    }

    public int pop_front() {
        if (!q.isEmpty()) {
            int front = q.pollFirst();
            if (front == mq.peekFirst()) {
                mq.pollFirst();
            }
            return front;
        } else {
            return -1;
        }
    }
}

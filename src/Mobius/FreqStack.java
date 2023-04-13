package Mobius;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 *895. 最大频率栈
 * 设计一个类似堆栈的数据结构，将元素推入堆栈，并从堆栈中弹出出现频率最高的元素。
 *
 * 实现 FreqStack 类:
 *
 * FreqStack() 构造一个空的堆栈。
 * void push(int val) 将一个整数 val 压入栈顶。
 * int pop() 删除并返回堆栈中出现频率最高的元素。
 * 如果出现频率最高的元素不只一个，则移除并返回最接近栈顶的元素。
 */
public class FreqStack {
    Map<Integer,Integer> map;
    PriorityQueue<int[]> queue;
    int idx ;
    public FreqStack() {
        idx = 0;
        map = new HashMap<>();
        //int 数组存放val,cnt,idx
        //idx后进来的放最前面
        queue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[1] == o2[1] ? o2[2] - o1[2] : o2[1] - o1[1];
            }
        });
    }

    public void push(int val) {
        int cnt = map.getOrDefault(val,0) + 1;
        map.put(val, cnt);
        queue.offer(new int[]{val,cnt,idx ++});
    }

    public int pop() {
        int[] nums = queue.poll();
        int val = nums[0];
        //减1
        map.put(val, -- nums[1]);
        return val;
    }
}

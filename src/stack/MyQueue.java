package stack;

import java.util.Stack;

/**
 * description:MyQueue
 * create user: songj
 * date : 2021/3/22 15:00
 * 面试题 03.04. 化栈为队
 * 实现一个MyQueue类，该类用两个栈来实现一个队列。
 */
public class MyQueue {
    Stack<Integer> instack = new Stack<>();
    Stack<Integer> outstack = new Stack<>();

    /** Initialize your data structure here. */
    public MyQueue() {

    }

    /** Push element x to the back of queue. */
    public void push(int x) {
        instack.push(x);
    }

    public void transfer() {
        if (!outstack.isEmpty()) {
            return;
        }
        while (!instack.isEmpty()) {
            outstack.push(instack.pop());
        }
    }

    /** Removes the element from in front of queue and returns that element. */
    public int pop() {
        transfer();
        return outstack.pop();
    }

    /** Get the front element. */
    public int peek() {
        transfer();
        return outstack.peek();
    }

    /** Returns whether the queue is empty. */
    public boolean empty() {
        return instack.empty() && outstack.empty();
    }
}

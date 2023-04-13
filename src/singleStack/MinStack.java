package singleStack;

import java.util.Stack;

/**
 * 155. 最⼩栈
 * 题⽬描述：设计⼀个⽀持 push ， pop ， top 操作，并能在常数时间内检索到最⼩元
 * 素的栈
 */
public class MinStack {
    /** initialize your data structure here. */
    Stack<Integer> stack ;
    Stack<Integer> minStack ;
    public MinStack() {
        stack = new Stack<>();
        minStack = new Stack<>();
    }

    public void push(int val) {
        stack.push(val);
        if (minStack.isEmpty() || minStack.peek() >= val ) {
            minStack.push(val);
        }
    }

    public void pop() {
        int val = stack.pop();
        if (val == minStack.peek()) {
            minStack.pop();
        }
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }
}

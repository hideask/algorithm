package Mobius;

import java.util.Iterator;

/**
 * 284. 顶端迭代器
 * 请你在设计一个迭代器，在集成现有迭代器拥有的 hasNext 和 next 操作的基础上，还额外支持 peek 操作。
 *
 * 实现 PeekingIterator 类：
 *
 * PeekingIterator(Iterator<int> nums) 使用指定整数迭代器 nums 初始化迭代器。
 * int next() 返回数组中的下一个元素，并将指针移动到下个元素处。
 * bool hasNext() 如果数组中存在下一个元素，返回 true ；否则，返回 false 。
 * int peek() 返回数组中的下一个元素，但 不 移动指针。
 * 注意：每种语言可能有不同的构造函数和迭代器 Iterator，但均支持 int next() 和 boolean hasNext() 函数。
 */
public class PeekingIterator implements Iterator<Integer> {
    int cur ;
    boolean end_flag ;
    Iterator<Integer> iterator;
    public PeekingIterator(Iterator<Integer> iterator) {
        // initialize any member here.
        end_flag = false;
        if (iterator.hasNext()) {
            cur = iterator.next();
        } else {
            end_flag = true;
        }
        this.iterator = iterator;
    }

    // Returns the next element in the iteration without advancing the iterator.
    public Integer peek() {
        return cur;
    }

    // hasNext() and next() should behave the same as in the Iterator interface.
    // Override them if needed.
    @Override
    public Integer next() {
        if (end_flag) {
            return -1;
        }
        int res = cur;
        if (iterator.hasNext()) {
            cur = iterator.next();
        } else {
            end_flag = true;
        }
        return res;
    }

    @Override
    public boolean hasNext() {
        return end_flag;
    }
}

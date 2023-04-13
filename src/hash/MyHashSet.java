package hash;

/**
 * description:MyHashSet
 * create user: songj
 * 705. 设计哈希集合
 * 题⽬描述：不使⽤任何内建的哈希表库设计⼀个哈希集合（HashSet）。
 * 实现 MyHashSet 类：
 * void add(key) 向哈希集合中插⼊值 key 。
 * bool contains(key) 返回哈希集合中是否存在这个值 key 。
 * void remove(key) 将给定值 key 从哈希集合中删除。如果哈希集合中没有这个值，什么也不
 * 做。
 * 数组实现：
 * 由于题⽬给出了 0 <= key <= 10^6 数据范围，同时限定了 key 只能是 int。我们可以直接使⽤⼀个
 * boolean 数组记录某个 key 是否存在，key 直接对应 boolean 的下标。
 * date : 2021/6/15 14:21
 */
public class MyHashSet {
    private boolean[] set = new boolean[1000000];

     /** Initialize your data structure here. */
    public MyHashSet() {

    }

    public void add(int key) {
        set[key] = true;
    }

    public void remove(int key) {
        set[key] = false;
    }

    /** Returns true if this set contains the specified element */
    public boolean contains(int key) {
        return set[key];
    }
}

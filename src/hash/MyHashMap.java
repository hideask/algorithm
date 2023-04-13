package hash;

import java.util.Arrays;

/**
 * description:MyHashMap
 * create user: songj
 * 706. 设计哈希映射
 * 题⽬描述：不使⽤任何内建的哈希表库设计⼀个哈希映射（HashMap）。
 * 实现 MyHashMap 类：
 * MyHashMap() ⽤空映射初始化对象
 * void put(int key, int value) 向 HashMap 插⼊⼀个键值对 (key, value) 。如果 key 已经存在于
 * 映射中，则更新其对应的值 value 。
 * int get(int key) 返回特定的 key 所映射的 value ；如果映射中不包含 key 的映射，返回 1 。
 * void remove(key) 如果映射中存在 key 的映射，则移除 key 和它所对应的 value 。
 * 简单数组：
 * date : 2021/6/15 14:29
 */
public class MyHashMap {
    private int[] set = new int[1000001];
     /** Initialize your data structure here. */
    public MyHashMap() {
        Arrays.fill(set,-1);
    }

    /** value will always be non-negative. */
    public void put(int key, int value) {
        set[key] = value;
    }

    /** Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key */
    public int get(int key) {
        return set[key] ;
    }

    /** Removes the mapping of the specified value key if this map contains a mapping for the key */
    public void remove(int key) {
        set[key] = 0;
    }
}

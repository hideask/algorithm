package hash;

import java.util.LinkedHashMap;

/**
 * 面试题 16.25. LRU 缓存
 * 设计和构建一个“最近最少使用”缓存，该缓存会删除最近最少使用的项目。缓存应该从键映射到值(允许你插入和检索特定键对应的值)，
 * 并在初始化时指定最大容量。当缓存被填满时，它应该删除最近最少使用的项目。
 *
 * 它应该支持以下操作： 获取数据 get 和 写入数据 put 。
 *
 * 获取数据 get(key) - 如果密钥 (key) 存在于缓存中，则获取密钥的值（总是正数），否则返回 -1。
 * 写入数据 put(key, value) - 如果密钥不存在，则写入其数据值。当缓存容量达到上限时，
 * 它应该在写入新数据之前删除最近最少使用的数据值，从而为新的数据值留出空间。
 *
 * description:LRUCache
 * create user: songj
 * date : 2021/6/16 20:42
 */
public class LRUCache {
    //使用LinkedHashMap
    LinkedHashMap<Integer,Integer> lm;
    int capacity ;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        lm = new LinkedHashMap();
    }

    public int get(int key) {
        if (!lm.containsKey(key)) {
            return -1;
        }
        int value = lm.get(key);
        lm.remove(key);
        lm.put(key,value);
        return value;
    }

    public void put(int key, int value) {
        if (lm.containsKey(key)) {
            lm.remove(key);
            lm.put(key,value);
            return;
        }
        lm.put(key,value);
        if (lm.size() > capacity) {
            lm.remove(lm.keySet().iterator().next());
        }
    }
}

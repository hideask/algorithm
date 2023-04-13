package RedBlackTree;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 981. 基于时间的键值存储
 * 设计一个基于时间的键值数据结构，该结构可以在不同时间戳存储对应同一个键的多个值，并针对特定时间戳检索键对应的值。
 *
 * 实现 TimeMap 类：
 *
 * TimeMap() 初始化数据结构对象
 * void set(String key, String value, int timestamp) 存储键 key、值 value，以及给定的时间戳 timestamp。
 * String get(String key, int timestamp)
 * 返回先前调用 set(key, value, timestamp_prev) 所存储的值，其中 timestamp_prev <= timestamp 。
 * 如果有多个这样的值，则返回对应最大的  timestamp_prev 的那个值。
 * 如果没有值，则返回空字符串（""）。
 */
public class TimeMap {
    TreeMap<String,TreeMap<Integer,String>> map;
    /** Initialize your data structure here. */
    public TimeMap() {
        map = new TreeMap<>();
    }

    public void set(String key, String value, int timestamp) {
        if (map.containsKey(key)) {
            TreeMap<Integer,String> hm = map.get(key);
            hm.put(timestamp,value);
        } else {
            TreeMap<Integer,String> hm = new TreeMap<>();
            hm.put(timestamp,value);
            map.put(key, hm);
        }

    }

    public String get(String key, int timestamp) {
        if (!map.containsKey(key)) {
            return "";
        } else {
            TreeMap<Integer,String> hm = map.get(key);
            if (hm.containsKey(timestamp)) {
                return hm.get(timestamp);
            } else {
                return hm.floorEntry(timestamp) == null ? "" : hm.floorEntry(timestamp).getValue();
            }
        }
    }
}

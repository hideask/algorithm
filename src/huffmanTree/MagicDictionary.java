package huffmanTree;

import java.util.ArrayList;
import java.util.List;

/**
 * 676. 实现一个魔法字典
 * 设计一个使用单词列表进行初始化的数据结构，单词列表中的单词 互不相同 。 如果给出一个单词，
 * 请判定能否只将这个单词中一个字母换成另一个字母，使得所形成的新单词存在于你构建的字典中。
 *
 * 实现 MagicDictionary 类：
 *
 * MagicDictionary() 初始化对象
 * void buildDict(String[] dictionary) 使用字符串数组 dictionary 设定该数据结构，dictionary 中的字符串互不相同
 * bool search(String searchWord) 给定一个字符串 searchWord ，判定能否只将字符串中 一个 字母换成另一个字母，
 * 使得所形成的新字符串能够与字典中的任一字符串匹配。如果可以，返回 true ；否则，返回 false 。
 */
public class MagicDictionary {

    public MagicDictionary() {
        root = new Node();
    }

    public void buildDict(String[] dictionary) {
        for (String str : dictionary) {
            insert(str);
        }
    }

    public boolean search(String searchWord) {
        return __search(searchWord, 0, 1, root);
    }

    class Node {
        boolean flag = false;
        Node[] next = new Node[26];
    }

    Node root;

    public void insert(String str) {
        Node p = root;
        char[] chars = str.toCharArray();
        for (char c : chars) {
            int idx = c - 'a';
            if (p.next[idx] == null) {
                p.next[idx] = new Node();
            }
            p = p.next[idx];
        }
        p.flag = true;
    }

    /**
     * @param str
     * @param n 当前匹配字符串的下标
     * @param m 模糊多少次
     * @param p 当前节点
     * @return
     */
    public boolean __search(String str, int n , int m, Node p) {
        if (n == str.length()) {
            return p.flag && m == 0;
        }
        int idx = str.charAt(n) - 'a';
        //精准匹配一次
        if (p.next[idx] != null && __search(str, n + 1, m ,p.next[idx])) {
            return true;
        }

        //模糊匹配一次
        if (m > 0) {
            //扫描所有节点
            for (int i = 0 ; i < 26 ; i ++) {
                if (i == idx || p.next[i] == null) {
                    continue;
                }
                if (__search(str, n + 1, m - 1, p.next[i])) {
                    return true;
                }
            }
        }
        return false;
    }

}

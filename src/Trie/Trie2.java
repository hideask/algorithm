package Trie;

import java.util.Scanner;

public class Trie2 {
    int Base = 26;
    class Node {
        boolean flag = false;
        int[] next = new int[Base];
    }

    Node[] tries = new Node[10000];
    int cnt,root ;

    public Trie2 () {
        cnt = 2;
        root = 1;
        tries[root] = new Node();
    }

    public int getNewNode() {
        tries[cnt] = new Node();
        return cnt ++;
    }

    public boolean insert(String word) {
        int p = root;
        char[] chars = word.toCharArray();
        for (char c : chars) {
            int idx = c - 'a';
            if (tries[p].next[idx] == 0) {
                tries[p].next[idx] = getNewNode();
            }
            p = tries[p].next[idx];
        }
        //非第一次插入
        if (tries[p].flag) {
            return false;
        }
        tries[p].flag = true;
        return true;
    }

    public boolean search(String word) {
        int p = root;
        char[] chars = word.toCharArray();
        for (char c : chars) {
            int idx = c - 'a';
            p = tries[p].next[idx];
            if (p == 0) {
                return false;
            }
        }

        return tries[p].flag;
    }

    public void output() {
        __output(this.root, "");

    }

    public void __output(int root, String s) {
        if (root == 0) {
            return;
        }
        if (tries[root].flag) System.out.println(s);
        for (int i = 0; i < Base; i ++) {
            __output(tries[root].next[i], s + (char)(i + 'a'));
        }
    }

    public static void main(String[] args) {
        Trie2 trie = new Trie2();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            int a = scanner.nextInt();
            String s = scanner.next();

            switch (a) {
                case 1 :
                    System.out.println(trie.insert(s));
                    break;
                case 2:
                    System.out.println(trie.search(s));
                    break;
                case 3:
                    trie.output();
                    break;
            }
        }
    }
}

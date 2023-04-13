package Trie;

import java.util.Scanner;

public class Trie {
    class Node {
        int Base = 26;
        boolean flag ;
        Node[] next = new Node[Base];
        public Node() {
            flag = false;
            for (int i = 0; i < Base; i ++) {
                next[i] = null;
            }
        }
    }

    private Node root ;
    public Trie () {
        root = new Node();
    }

    public void clearTrie(Node root) {

        root = null;
    }

    public boolean insert(String word) {
        Node p = this.root;
        char[] chars = word.toCharArray();
        for (char c : chars) {
            int idx = c - 'a';
            if (p.next[idx] == null) {
                p.next[idx] = new Node();
            }
            p = p.next[idx];
        }
        //非第一次插入
        if (p.flag) {
            return false;
        }
        p.flag = true;
        return true;
    }

    public boolean search(String word) {
        Node p = this.root;
        char[] chars = word.toCharArray();
        for (char c : chars) {
            int idx = c - 'a';
            p = p.next[idx];
            if (p == null) {
                return false;
            }
        }

        return p.flag;
    }

    public void output() {
        __output(this.root, "");

    }

    public void __output(Node root, String s) {
        if (root == null) {
            return;
        }
        if (root.flag) System.out.println(s);
        for (int i = 0; i < root.Base; i ++) {
            __output(root.next[i], s + (char)(i + 'a'));
        }
    }

    public static void main(String[] args) {
        Trie trie = new Trie();

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

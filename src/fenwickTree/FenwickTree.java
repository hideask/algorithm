package fenwickTree;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 树状数组实现
 */
public class FenwickTree {
    private int[] c ;//
    int n;//下标最大上限

    public FenwickTree (int n) {
        this.n = n + 1;
        this.c = new int[n + 1];
    }

    /**
     * 在原数组的 第 i 个位置加上 x 的值
     * 公式 修改c[i]的值，需要同时更新 c[i + lowbit(i)]的值
     * 因为 c[i + lowbit(i)] 的范围包含了 c[i] 的范围
     * @param i
     * @param x
     */
    public void add (int i , int x) {
        while (i < n) {
            c[i] += x;
            i += lowbit(i);
        }
    }

    /**
     * 获得二进制的最后一个 1
     * @param i
     * @return
     */
    public int lowbit(int i) {
        return i & (-i);
    }

    /**
     * 求第X位的前缀和
     * 公式： s[i] = s[i - lowbit(i)] + c[i]
     * @param x
     * @return
     */
    public int query(int x) {
        int sum = 0;
        while (x > 0) {
            sum += c[x];
            x -= lowbit(x);
        }
        return sum;
    }

    /**
     * 求当前值
     * @param x
     * @return
     */
    public int at(int x) {
        return query(x) - query(x - 1);
    }

    /**
     * 求区间和
     * @param m
     * @param n
     * @return
     */
    public int sum(int m , int n) {
        return query(m) - query(n - 1);
    }

    public void output() {
        for (int i = 1; i < n ; i ++) {
            System.out.printf("%d   ", c[i]);
        }
        System.out.printf("\n");
        for (int i = 1; i < n ; i ++) {
            System.out.printf("%d   ", query(i) - query(i - 1));
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        FenwickTree fenwickTree = new FenwickTree(n);
        for (int i = 1, a; i <= n ; i ++) {
            a = scanner.nextInt();
            fenwickTree.add(i, a);
        }
        fenwickTree.output();
    }
}

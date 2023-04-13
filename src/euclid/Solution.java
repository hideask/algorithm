package euclid;

import java.util.Arrays;

public class Solution {

    /**
     * 287. 寻找重复数
     * 给定一个包含 n + 1 个整数的数组 nums ，其数字都在 1 到 n 之间（包括 1 和 n），可知至少存在一个重复的整数。
     *
     * 假设 nums 只有 一个重复的整数 ，找出 这个重复的数 。
     *
     * 你设计的解决方案必须不修改数组 nums 且只用常量级 O(1) 的额外空间。
     * @param nums
     * @return
     */
    public int findDuplicate(int[] nums) {
        //如果是必须不修改数组 nums 且只用常量级 O(1) 的额外空间，
        //我们把nums中的值当作一个链表，值是指向另一个nums下标，如果有重复的值，说明有两个值
        //是指向同一个下标，就会产生环；使用快慢指针判断环
        int p = 0, q = 0;
        do {
            p = nums[p];
            q = nums[nums[q]];
        } while (p != q);
        p = 0;
        while (p != q) {
            p = nums[p];
            q = nums[q];
        }
        return p;
    }

    /**
     * 1175. 质数排列
     * 请你帮忙给从 1 到 n 的数设计排列方案，使得所有的「质数」都应该被放在「质数索引」（索引从 1 开始）上；你需要返回可能的方案总数。
     *
     * 让我们一起来回顾一下「质数」：质数一定是大于 1 的，并且不能用两个小于它的正整数的乘积来表示。
     *
     * 由于答案可能会很大，所以请你返回答案 模 mod 10^9 + 7 之后的结果即可。
     * @param n
     * @return
     */
    public int numPrimeArrangements(int n) {

        int m = countPrime(n), mod = (int)(1e9 + 7);

        //求组合总数
        return (int) (frac(m) * frac(n - m) % mod) ;
    }

    private int countPrime(int n) {
        //素数筛算法
        int[] count = new int[n + 1];
        count[0] = count[1] = 1;
        for (int i = 2; i * i <= n; i ++) {
            if (count[i] == 1) {
                continue;
            }
            for (int j = 2 * i; j <= n; j += i) {
                count[j] = 1;
            }
        }

        int ans = 0;
        for (int i = 0 ; i <= n; i ++) {
            if (count[i] == 0) {
                ans ++;
            }
        }
        return ans;
    }

    private long frac(long m) {
        //求阶乘
        long ans = 1,mod = (int)(1e9 + 7);
        for (long i = 2; i <= m; i ++) {
            ans = ans * i % mod;
        }
        return ans;
    }

    /**
     * 1071. 字符串的最大公因子
     * 对于字符串 S 和 T，只有在 S = T + ... + T（T 自身连接 1 次或多次）时，我们才认定 “T 能除尽 S”。
     *
     * 返回最长字符串 X，要求满足 X 能除尽 str1 且 X 能除尽 str2。
     * @param str1
     * @param str2
     * @return
     */
    public String gcdOfStrings(String str1, String str2) {
        //如果要满足有最大公因子str，说明str 在 str1中能整除，str2中也能整除，
        //那么一定str1 + str2 = str2 + str1; 如果要求最大公因子，其实是求
        //公共子串的最大长度
        if ((str1 + str2).equals(str2 + str1)) {
            return "";
        }
        int n = gcd (str1.length(), str2.length());
        return str1.substring(0,n);
    }

    public int gcd (int m, int n) {
        if (n == 0) {
            return m;
        }
        return gcd(n , m % n);
    }
}

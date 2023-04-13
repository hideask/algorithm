package queue;

import jdk.nashorn.internal.objects.annotations.Where;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * description:
 * create user: songj
 * date : 2021/3/16 19:29
 */
public class Solution {
    /**
     * 面试题 17.09. 第 k 个数
     * 有些数的素因子只有 3，5，7，请设计一个算法找出第 k 个数。注意，不是必须有这些素因子，
     * 而是必须不包含其他的素因子。例如，前几个数按顺序应该是 1，3，5，7，9，15，21。
     */
    public int getKthMagicNumber(int k) {
        int[] numbers = new int[k];
        numbers[0] = 1;
        int p3 = 0, p5 = 0, p7 = 0;
        for (int i = 1; i < k; i++) {
            numbers[i] = Math.min(Math.min(3 * numbers[p3], 5 * numbers[p5]), 7 * numbers[p7]);
            if (numbers[i] == 3 * numbers[p3]) {
                p3++;
            }
            if (numbers[i] == 5 * numbers[p5]) {
                p5++;
            }
            if (numbers[i] == 7 * numbers[p7]) {
                p7++;
            }

        }
        return numbers[k - 1];
    }

    /**
     * 859. 亲密字符串
     * 给定两个由小写字母构成的字符串 A 和 B ，只要我们可以通过交换 A 中的两个字母得到与 B 相等的结果，
     * 就返回 true ；否则返回 false 。
     * <p>
     * 交换字母的定义是取两个下标 i 和 j （下标从 0 开始），只要 i!=j 就交换 A[i] 和 A[j] 处的字符。
     * 例如，在 "abcd" 中交换下标 0 和下标 2 的元素可以生成 "cbad" 。
     */
    public boolean buddyStrings(String a, String b) {
        if (a.length() != b.length()) {
            return false;
        }
        if (a.equals(b)) {
            return specilJudge(a);
        }
        int first = -1, second = -1;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                //找到出现不同字母的次数
                if (first == -1) {
                    first = i;
                } else if (second == -1) {
                    second = i;
                } else {
                    return false;
                }
            }
        }
        return second != -1 && a.charAt(first) == b.charAt(second)
                && a.charAt(second) == b.charAt(first);
    }

    public boolean specilJudge(String a) {
        //定义一个26位的数组，统计每个字母出现的频率
        int[] count = new int[26];
        for (int i = 0; i < a.length(); i++) {
            //'a' - 'a'=0;'b' - 'a'= 1; 0节点表示a的次数，以此类推
            count[a.charAt(i) - 'a']++;
            //因为两个都是abcd这种就不是亲密字符串，acad同一字母出现多次后交换相同字母和b字符串相等
            if (count[a.charAt(i) - 'a'] > 1) {
                return true;
            }
        }

        return false;
    }

    /**
     * 860. 柠檬水找零
     * 在柠檬水摊上，每一杯柠檬水的售价为 5 美元。
     * <p>
     * 顾客排队购买你的产品，（按账单 bills 支付的顺序）一次购买一杯。
     * <p>
     * 每位顾客只买一杯柠檬水，然后向你付 5 美元、10 美元或 20 美元。你必须给每个顾客正确找零，也就是说净交易是每位顾客向你支付 5 美元。
     * <p>
     * 注意，一开始你手头没有任何零钱。
     * <p>
     * 如果你能给每位顾客正确找零，返回 true ，否则返回 false 。
     *
     * @param bills
     * @return
     */
    public boolean lemonadeChange(int[] bills) {
        int five = 0, ten = 0;
        for (int bill : bills) {
            if (bill == 5) {
                five++;
            } else if (bill == 10) {
                if (five > 0) {
                    five--;
                    ten++;
                } else {
                    return false;
                }
            } else if (bill == 20) {
                if (ten > 0 && five > 0) {
                    ten--;
                    five--;
                } else if (five > 2) {
                    five -= 3;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 969. 煎饼排序
     * 给你一个整数数组 arr ，请使用 煎饼翻转 完成对数组的排序。
     * <p>
     * 一次煎饼翻转的执行过程如下：
     * <p>
     * 选择一个整数 k ，1 <= k <= arr.length
     * 反转子数组 arr[0...k-1]（下标从 0 开始）
     * 例如，arr = [3,2,1,4] ，选择 k = 3 进行一次煎饼翻转，反转子数组 [3,2,1] ，得到 arr = [1,2,3,4] 。
     * <p>
     * 以数组形式返回能使 arr 有序的煎饼翻转操作所对应的 k 值序列。
     * 任何将数组排序且翻转次数在 10 * arr.length 范围内的有效答案都将被判断为正确。
     * <p>
     * 1 <= arr.length <= 100
     * 1 <= arr[i] <= arr.length
     * arr 中的所有整数互不相同（即，arr 是从 1 到 arr.length 整数的一个排列）
     */
    public List<Integer> pancakeSort(int[] arr) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        for (int i = arr.length; i > 0; i--) {
            int index = 0;//index是数组中最大值的下标
            while (arr[index] != i) {//i是最大值
                index++;
            }
//            if (index == i - 1) {//最大值已经在最后，则不翻转
//                continue;
//            } else
                if (index == 0) {//最大值在第一位
                reverse(arr, i);//翻转到最后一位
                result.add(i);
            } else {
                //翻转到首位，index + 1是翻转位数，index为索引位置，从0开始
                reverse(arr, index + 1);
                result.add(index + 1);
                reverse(arr, i);//翻转到最后
                result.add(i);
            }
        }
        return result;
    }

    public void reverse(int[] arr, int index) {
        int start = 0;
        int end = index - 1;
        while (start < end) {
            int tmp = arr[end];
            arr[end] = arr[start];
            arr[start] = tmp;
            start++;
            end--;
        }
    }


    /**
     * 621. 任务调度器
     * 给你一个用字符数组 tasks 表示的 CPU 需要执行的任务列表。其中每个字母表示一种不同种类的任务。
     * 任务可以以任意顺序执行，并且每个任务都可以在 1 个单位时间内执行完。在任何一个单位时间，
     * CPU 可以完成一个任务，或者处于待命状态。
     * <p>
     * 然而，两个 相同种类 的任务之间必须有长度为整数 n 的冷却时间，因此至少有连续 n 个单位时间内
     * CPU 在执行不同的任务，或者在待命状态。
     * <p>
     * 你需要计算完成所有任务所需要的 最短时间
     */
    public int leastInterval(char[] tasks, int n) {
        int[] count = new int[26];
        for (int i = 0; i < tasks.length; i++) {//统计每个字母出现的频率
            count[tasks[i] - 'A']++;
        }

        Arrays.sort(count);//从大到小排序
        int maxTimes = count[25];//出现最多的次数
        int maxCount = 1;//多少个最多次数
        for (int i = 25; i >= 1; i--) {
            if (count[i] == count[i - 1]) {
                maxCount++;
            } else {
                break;
            }
        }
        /**
         * A B C
         * A B C
         * A B C
         * A B
         * 如果任务个数小于最短执行时间，取最短时间；大于最短执行时间，取任务个数
         */
        int result = (maxTimes - 1) * (n + 1) + maxCount;
        return Math.max(result, tasks.length);

    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] ints = new int[]{9, 8, 4, 1, 3, 2, 6, 5, 7};
        List list = solution.pancakeSort(ints);
        System.out.println(list.toString());
    }
}

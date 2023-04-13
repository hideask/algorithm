package quicksort;

import java.awt.font.NumericShaper;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

/**
 * 快排
 * description:Solution
 * create user: songj
 * date : 2021/5/15 21:16
 */
public class Solution {
    /**
     * 239. 滑动窗口最大值
     * 给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
     * 返回滑动窗口中的最大值。
     * 示例 1
     * 输入：nums = [1,3,-1,-3,5,3,6,7], k = 3
     * 输出：[3,3,5,5,6,7]
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        Deque<Integer> deque = new LinkedList<>();
        //初始化前k个（第一个窗口）中的数据的队列中，如果队尾元素比要进入的元素小，则队尾元素弹出
        for (int i = 0; i < k; i++) {
            while (!deque.isEmpty() && nums[i] > nums[deque.peekLast()]) {
                deque.pollLast();
            }
            deque.offerLast(i);
        }
        int[] ans = new int[n - k + 1];//初始化大小为滑动的次数 + 默认的一次
        //队首元素为最大的，初始化进去
        ans[0] = nums[deque.peekFirst()];
        //如果队尾元素小于等于要进入的元素，则队尾元素弹出
        //初始化为k位置，移动一位
        for (int i = k; i < n; i++) {
            while (!deque.isEmpty() && nums[i] >= nums[deque.peekLast()]) {
                deque.pollLast();
            }
            deque.offerLast(i);
            //判断队首元素是否在划窗内，不在就弹出队首元素
            while (deque.peekFirst() <= i - k) {
                deque.pollFirst();
            }
            //结果放到ans中
            ans[i - k + 1] = nums[deque.peekFirst()];
        }
        return ans;
    }

    /**
     * 470. 用 Rand7() 实现 Rand10()
     * 已有方法 rand7 可生成 1 到 7 范围内的均匀随机整数，试写一个方法 rand10 生成 1 到 10 范围内
     * 的均匀随机整数。
     * <p>
     * 不要使用系统的 Math.random() 方法。
     *
     * @return
     */
    public int rand10() {
        //可推到出公式（Rx - 1）*y + Ry = Rxy
        while (true) {
            int num = (rand7() - 1) * 7 + rand7();//生成R49
            if (num <= 40) {//R49包含R40，可由R40得到R10
                return num % 10 + 1;
            }
            num = (num - 40 - 1) * 7 + rand7();//生成R63
            if (num <= 60) {
                return num % 10 + 1;
            }
            num = (num - 60 - 1) * 7 + rand7();//生成R21
            if (num <= 20) {
                return num % 10 + 1;
            }
            //num = (num - 20 - 1) * 7 + rand7();//生成R1,无法继续，所以循环；
        }
    }

    public int rand7() {
        return 1;
    }

    /**
     * 11. 盛最多水的容器
     * 给你 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。在坐标内画 n 条垂直线，
     * 垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0) 。
     * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
     * <p>
     * 说明：你不能倾斜容器。
     *
     * @param height
     * @return
     */
    public int maxArea(int[] height) {
        //初始化两个指针，分别指向数组0、最大位置
        int l = height.length;
        int left = 0, right = l - 1;
        int s = 0;
        //当左边小于右边，判断指针指向的高度小的，指针向中间移动，并计算面积
        while (left < right) {
            //面积为底乘以最小的高度
            s = Math.max(s, (right - left) * Math.min(height[left], height[right]));
            if (height[left] > height[right]) {
                right--;
            } else {
                left++;
            }
        }
        return s;
    }

    /**
     * 394. 字符串解码
     * 给定一个经过编码的字符串，返回它解码后的字符串。
     * <p>
     * 编码规则为: k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。
     * <p>
     * 你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。
     * <p>
     * 此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k ，例如不会出现像 3a 或 2[4] 的输入。
     * 示例 1：
     * <p>
     * 输入：s = "3[a]2[bc]"
     * 输出："aaabcbc"
     * 示例 2：
     * <p>
     * 输入：s = "3[a2[c]]"
     * 输出："accaccacc"
     *
     * @param s
     * @return
     */
    public String decodeString(String s) {
        Deque<Integer> numStack = new LinkedList<>();
        Deque<StringBuilder> strStack = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        int num = 0;
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {//如果是数字
                num = num * 10 + (c - '0');
            } else if (Character.isAlphabetic(c)) {//如果是字母
                sb.append(c);
            } else if (c == '[') {//遇到[入栈
                numStack.push(num);
                strStack.push(sb);
                num = 0;
                sb = new StringBuilder();
            } else {
                int n = numStack.pop();
                StringBuilder str = strStack.pop();
                for (int i = 0; i < n; i++) {
                    str.append(sb);
                }
                sb = str;
            }
        }
        return sb.toString();
    }

    /**
     * 75. 颜色分类
     * 给定一个包含红色、白色和蓝色，一共 n 个元素的数组，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。
     * <p>
     * 此题中，我们使用整数 0、 1 和 2 分别表示红色、白色和蓝色。
     *
     * @param nums
     */
    public void sortColors(int[] nums) {
        //设立边界，[0,zero]存储0，[zero，i]存储1，[i,two]存储2，当i = 0时，将i的值同zero位置的值交换
        //如果i的值等于2，将i的值同two位置的值交换
        int n = nums.length;
        int zero = 0;
        int two = n;
        int i = 0;
        while (i < two) {
            if (nums[i] == 0) {
                swap(nums, i, zero);
                i++;
                zero++;
            } else if (nums[i] == 1) {
                i++;
            } else {
                two--;
                swap(nums, i, two);

            }
        }

    }

    /**
     * @param nums
     * @param l
     * @param r
     */
    public void swap(int[] nums, int l, int r) {
        int tmp = nums[r];
        nums[r] = nums[l];
        nums[l] = tmp;
    }

    /**
     * 面试题 17.14. 最小K个数
     * 设计一个算法，找出数组中最小的k个数。以任意顺序返回这k个数均可。
     * <p>
     * 示例：
     * <p>
     * 输入： arr = [1,3,5,7,2,4,6,8], k = 4
     * 输出： [1,2,3,4]
     * <p>
     * 思路：我们知道，经过快速排序算法中的⼀次划分后，基点左边的所有数⼩于基点，右边
     * 的所有数⼤于基点，基点位置pivot有三种情况：
     * pivot=k 说明基点是第k+1⼩的元素，那么基点左边的数就是前k⼩的数，我们只需要返回
     * 下标在0,k-1这个区间的元素即可。
     * pivot > k 说明当前的基点偏⼤，分区太⼤，我们就要对其左边的⼦数组进⾏排序划分
     * pivot < k 说明当前的基点偏⼩，分区不够，我们就要对其右边的⼦数组进⾏排序划分
     *
     * @param arr
     * @param k
     * @return
     */
    public int[] smallestK(int[] arr, int k) {
        int[] ans = new int[k];
        if (k == 0) {
            return ans;
        }
        quickSort(arr, 0, arr.length - 1, k);
        while (k > 0) {
            k--;
            ans[k] = arr[k];
        }
        return ans;
    }

    public int getMid(int a, int b, int c) {
        if (a > b) {
            swap(a, b);
        }
        if (a > c) {
            swap(a, c);
        }
        if (b > c) {
            swap(b, c);
        }
        return b;
    }

    public void swap(int a, int b) {
        int tmp = b;
        b = a;
        a = tmp;
    }

    public void quickSort(int[] arr, int left, int right, int k) {
        int l = left, r = right, mid = getMid(arr[left], arr[right], (arr[left] + arr[right]) / 2);
        if (left > right) {
            return;
        }
        do {
            while (arr[l] < mid) {
                l++;
            }
            while (arr[r] > mid) {
                r--;
            }
            if (l <= r) {
                swap(arr, l, r);
                l++;
                r--;
            }
        } while (l <= r);
        if (r - left == k - 1) {
            return;
        } else if (r - left >= k) {
            quickSort(arr, left, r, k);
        } else {
            quickSort(arr, l, right, k - l + left);
        }
    }

    /**
     * 快排基础版本，如果数组趋于有序，会导致快排陷阱，导致时间复杂度增加
     * @param arr
     * @param left
     * @param right
     */
    public void quickSort(int[] arr, int left, int right) {
        if (left > right) {
            return;
        }
        int l = left, r = right, base = arr[l];
        while (l < r) {
            while (l < r && arr[r] >= base) {
                r--;
            }
            if (l < r) {
                arr[l++] = arr[r];
            }
            while (l < r && arr[l] <= base) {
                l++;
            }
            if (l < r) {
                arr[r--] = arr[l];
            }
        }
        arr[l] = base;
        quickSort(arr, left, l - 1);
        quickSort(arr, l + 1, right);
        return;
    }

    /**
     * 单边递归法
     * 左边循环，右边递归，减少递归次数
     * @param arr
     */
    public void quick_sort_v2(int[] arr, int left, int right) {
        while (left < right) {
            int l = left, r = right, base = arr[l];
            while (l < r) {
                while (l < r && arr[r] >= base) {
                    r--;
                }
                if (l < r) {
                    arr[l++] = arr[r];
                }
                while (l < r && arr[l] <= base) {
                    l++;
                }
                if (l < r) {
                    arr[r--] = arr[l];
                }
            }
            arr[l] = base;
            quickSort(arr, l + 1, right);
            right = l - 1;
        }
        return;
    }

    /**
     * 快速排序加入插入排序，在数据趋于有序时，减少递归次数
     * @param arr
     * @param l
     * @param r
     */
    public void quick_sort_v3(int[] arr, int l, int r) {
        __quick_sort(arr, l, r);
        final_insert_sort(arr, l, r);
        return;
    }

    public void __quick_sort(int[] arr, int l, int r) {
        while (r - l > 16) {//分为16的小块，16以内使用插入排序
            int x = l, y = r, base = getMid(arr[l], arr[r], arr[(l + r) / 2]);
            do {
                while (arr[x] < base) x++;
                while (arr[y] > base) y--;
                if (x <= y) {
                    swap(arr, x, y);
                    x++;
                    y--;
                }
            } while (x <= y);
            __quick_sort(arr, x, r);
            r = y;
        }
    }

    private void final_insert_sort(int[] arr, int l, int r) {
        int ind = l;
        //找出最小值的下标
        for (int i = l + 1; i <= r; i++) {
            if (arr[i] < arr[ind]) {
                ind = i;
            }
        }
        //将最小值放到最前面去
        while (ind > l) {
            swap(arr, ind, ind - 1);
            ind--;
        }
        //将其他元素依次往后挪
        for (int i = l + 2; i <= r; i++) {
            int j = i;
            while (arr[j] < arr[j - 1]) {
                swap(arr, j, j - 1);
                j--;
            }
        }
        return;
    }

    /**
     * 912. 排序数组
     * 给你一个整数数组 nums，请你将该数组升序排列。
     * <p>
     * 示例 1：
     * <p>
     * 输入：nums = [5,2,3,1]
     * 输出：[1,2,3,5]
     *
     * @param nums
     * @return
     */
    public int[] sortArray(int[] nums) {
        quick_sort_v3(nums,0,nums.length - 1);
        return nums;
    }

    /**
     * 剑指 Offer 21. 调整数组顺序使奇数位于偶数前面
     * 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有奇数位于数组的前半部分，所有偶数位于数组的后半部分。
     * <p>
     * 示例：
     * <p>
     * 输入：nums = [1,2,3,4]
     * 输出：[1,3,2,4]
     * 注：[3,1,2,4] 也是正确的答案之一。
     *
     * @param nums
     * @return
     */
    public int[] exchange(int[] nums) {
        if (nums.length == 0) {
            return nums;
        }
        int l = 0, r = nums.length - 1;
        do {
            while (l <= r && nums[l] % 2 == 1) {
                l++;
            }
            while (r >= l && nums[r] % 2 == 0) {
                r--;
            }
            if (l <= r) {
                swap(nums, l, r);
                l++;
                r--;
            }
        } while (l <= r);
        return nums;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        String s = "3[a2[c]]";
//        solution.decodeString(s);

        int[] arr = new int[]{1, 3, 5, 7, 2, 4, 6, 8};
//        int[] arr = new int[] {1,3,5};
        //solution.smallestK(arr,4);
        solution.quick_sort_v2(arr, 0, 7);
        System.out.println(arr);
//        solution.exchange(arr);
    }
}

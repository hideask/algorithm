package binarysearch;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * description:Solution
 * create user: songj
 * date : 2021/6/4 11:03
 */
public class Solution {
    /**
     * 35. 搜索插入位置
     * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，
     * 返回它将会被按顺序插入的位置。
     *
     * 你可以假设数组中无重复元素。
     *
     * 示例 1:
     *
     * 输入: [1,3,5,6], 5
     * 输出: 2
     * @param nums
     * @param target
     * @return
     */
    public int searchInsert(int[] nums, int target) {
        int head = 0 , tail = nums.length - 1, mid, midnum;
        while (head - tail > 3) {
            mid = (head + tail) / 2;
            midnum = nums[mid];
            if (target >= midnum) {
                head = mid ;
            } else {
                tail = mid - 1;
            }
        }
        for (int i = head ; i <= tail; i ++) {
            if (nums[i] >= target) {
                return i;
            }
        }
        return nums.length;
    }

    /**
     * 34. 在排序数组中查找元素的第一个和最后一个位置
     * 给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。
     *
     * 如果数组中不存在目标值 target，返回 [-1, -1]。
     *
     * 进阶：
     *
     * 你可以设计并实现时间复杂度为 O(log n) 的算法解决此问题吗？
     *
     * 示例 1：
     *
     * 输入：nums = [5,7,7,8,8,10], target = 8
     * 输出：[3,4]
     * @param nums
     * @param target
     * @return
     */
    public int[] searchRange(int[] nums, int target) {
        int[] res = new int[2];
        res[0] = binarySearch(nums,target);
        //判断在数组中没找到
        if (res[0] == nums.length || nums[res[0]] != target) {
            res[0] = res[1] = -1;
            return res;
        }
        res[1] = binarySearch(nums,target+1);
        return res;
    }

    public int binarySearch(int[] nums, int target) {
        int head = 0, tail = nums.length - 1, mid;
        //大范围的查找
        while (head - tail > 3) {
            mid = (head + tail) / 2;
            if (nums[mid] >= target) {
                tail = mid;
            } else {
                head = mid + 1;
            }
        }
        //小范围的查找
        for (int i = head; i <= tail; i ++) {
            if (nums[i] >= target) {
                return i;
            }
        }
        //如果没找到，则返回最大下标+1
        return nums.length;
    }

    /**
     * 1. 两数之和
     * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，
     * 并返回它们的数组下标。
     *
     * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
     *
     * 你可以按任意顺序返回答案。
     * 示例 1：
     *
     * 输入：nums = [2,7,11,15], target = 9
     * 输出：[0,1]
     * 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        //使用二分查找
        int len = nums.length;
        int[] cp = Arrays.copyOf(nums, len);
        Arrays.sort(cp);
        int i = 0, j = len - 1;

        while (i != j) {
            if (cp[i] + cp[j] == target) {
                break;
            } else if (cp[i] + cp[j] < target) {
                i ++ ;
            } else {
                j -- ;
            }
        }
        if (i != j) {
            if (cp[i] != cp[j]) {
                return new int[]{
                        find(nums,0, len, cp[i]),
                        find(nums,0, len, cp[j])
                };
            } else {
                int x = find(nums,0, len, cp[i]);
                int y = find(nums,x + 1, len, cp[j]);
                return new int[]{x,y};
            }
        }
        return new int[]{-1,-1};
    }

    public int find (int[] nums, int begin, int end, int target) {
        for (int i = begin; i < end; i ++) {
            if (nums[i] == target) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 69. x 的平方根
     * 实现 int sqrt(int x) 函数。
     *
     * 计算并返回 x 的平方根，其中 x 是非负整数。
     *
     * 由于返回类型是整数，结果只保留整数的部分，小数部分将被舍去。
     *
     * 示例 1:
     *
     * 输入: 4
     * 输出: 2
     * 示例 2:
     *
     * 输入: 8
     * 输出: 2
     * 说明: 8 的平方根是 2.82842...,
     *      由于返回类型是整数，小数部分将被舍去。
     * @param x
     * @return
     */
    public int mySqrt(int x) {
//        double head = 0,tail = x ,mid;
//        //相当于2的100次方，
//        for (int i = 0; i < 100 ;i ++) {
//            mid = head + (tail - head) / 2.0;
//            if (mid * mid <= x) {
//                head = mid;
//            } else {
//                tail = mid;
//            }
//        }
//        return (int) Math.floor(head);

        int head = 0, tail = x, ans = -1 , mid;
        while (head <= tail) {
            mid = head + (tail - head)/2;
            if ((long) mid * mid <= x) {
                ans = mid;
                head = mid + 1;
            } else {
                tail = mid - 1;
            }
        }
        return ans;
    }

    /**
     * 1658. 将 x 减到 0 的最小操作数
     * 给你一个整数数组 nums 和一个整数 x 。每一次操作时，你应当移除数组 nums 最左边或最右边的元素，
     * 然后从 x 中减去该元素的值。请注意，需要 修改 数组以供接下来的操作使用。
     *
     * 如果可以将 x 恰好 减到 0 ，返回 最小操作数 ；否则，返回 -1 。
     * 示例 1：
     *
     * 输入：nums = [1,1,4,2,3], x = 5
     * 输出：2
     * 解释：最佳解决方案是移除后两个元素，将 x 减到 0 。
     * @param nums
     * @param x
     * @return
     */
    public int minOperations(int[] nums, int x) {
        int len = nums.length;
        int[] sum = new int[len + 1];
        for (int i = 0; i < len; i ++) {
            sum[i + 1] = sum[i] + nums[i];
        }

        int ans = -1;
        for (int i = len,s = 0,j; i >= 0; i --, s += nums[Math.max(i, 0)]) {
            j = bs(sum, x - s);
            if (j == -1) {
                continue;
            }
            int cnt = len - i + j;
            if (cnt > len) {
                continue;
            }
            if (ans == -1 || cnt < ans) {
                ans = cnt;
            }
        }
        return ans;
    }

    public int bs(int[] nums,int target) {
        int head = 0,tail = nums.length - 1,mid;
        while (head <= tail) {
            mid = (head + tail)/2;
            if (target == nums[mid]) {
                return mid;
            } else if (target > nums[mid]) {
                head = mid + 1;
            } else {
                tail = mid - 1;
            }
        }
        return -1;
    }

    /**
     * 81. 搜索旋转排序数组 II
     * 已知存在一个按非降序排列的整数数组 nums ，数组中的值不必互不相同。
     *
     * 在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了 旋转 ，
     * 使数组变为 [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]
     * （下标 从 0 开始 计数）。例如， [0,1,2,4,4,4,5,6,6,7] 在下标 5 处经旋转后可能
     * 变为 [4,5,6,6,7,0,1,2,4,4] 。
     *
     * 给你 旋转后 的数组 nums 和一个整数 target ，请你编写一个函数来判断给定的目标值是否存在于数组中。
     * 如果 nums 中存在这个目标值 target ，则返回 true ，否则返回 false 。
     *
     * 示例 1：
     *
     * 输入：nums = [2,5,6,0,0,1,2], target = 0
     * 输出：true
     * @param nums
     * @param target
     * @return
     */
    public boolean search(int[] nums, int target) {
        int len = nums.length;
        if (len == 0) {
            return false;
        }
        if (nums[0] == target) {
            return true;
        }

        int l = 0,r = len - 1,head = nums[l], tail = nums[r], mid, midnum;
        while (l < r && nums[r] == nums[0]) {
            r --;
        }
        while (l < r && nums[l] == nums[0]) {
            l ++;
        }
        while (l <= r) {
            mid = (l + r)/2;
            midnum = nums[mid];
            if (target == midnum) {
                return true;
            }
            if (midnum >= head) {
                if (target < midnum && target >= head) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            } else {
                if (target > midnum && target <= tail) {
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
        }
        return false;
    }

    /**
     * 475. 供暖器
     * 冬季已经来临。 你的任务是设计一个有固定加热半径的供暖器向所有房屋供暖。
     *
     * 在加热器的加热半径范围内的每个房屋都可以获得供暖。
     *
     * 现在，给出位于一条水平线上的房屋 houses 和供暖器 heaters 的位置，
     * 请你找出并返回可以覆盖所有房屋的最小加热半径。
     *
     * 说明：所有供暖器都遵循你的半径标准，加热的半径也一样。
     * @param houses
     * @param heaters
     * @return
     */
    public int findRadius(int[] houses, int[] heaters) {
        Arrays.sort(heaters);
        int res = 0;
        for (int house : houses) {
            int l = 0,r = heaters.length - 1,mid;
            //二分法查找距离左边最近位置
            while (l < r) {
                mid = (l + r)/2;
                if (heaters[mid] >= house) {
                    r = mid;
                } else {
                    l = mid + 1;
                }
            }

            int leftemp = l;
            l = 0;
            r = heaters.length - 1;
            //获取距离右边最近距离
            while (l < r) {
                mid = (l + r + 1)/2;
                if (heaters[mid] <= house) {
                    l = mid;
                } else {
                    r = mid - 1;
                }
            }
            int righttemp = r;
            res = Math.max(
                    res,
                    Math.min(
                            Math.abs(house - heaters[leftemp]),
                            Math.abs(house - heaters[righttemp])));
        }
        return res;
    }

    /**
     * 300. 最⻓递增⼦序列
     * 题⽬描述：给你⼀个整数数组 nums ，找到其中最⻓严格递增⼦序列的⻓度。⼦序列
     * 是由数组派⽣⽽来的序列，删除（或不删除）数组中的元素⽽不改变其余元素的顺
     * 序。例如，3,6,2,7 是数组 0,3,1,6,2,2,7 的⼦序列。
     * ⽰例 :输⼊：nums = 10,9,2,5,3,7,101,18
     * 输出：4
     * 解释：最⻓递增⼦序列是 2,3,7,101，因此⻓度为 4 。
     * @param nums
     * @return
     */
    public int lengthOfLIS(int[] nums) {
        int[] len = new int[nums.length + 1];
        len[1] = nums[0];
        int res = 1;
        for (int i = 1; i < nums.length; i ++) {
            int l = binarySearch(len, res, nums[i]);
            len[l] = nums[i];
            res = Math.max(res, l);
        }
        return res;
    }

    public int binarySearch(int[] nums,int n, int t) {
        int l = 1,r = n + 1,mid;
        while (l < r) {
            mid = (l + r)/2;
            if (t <= nums[mid]) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }

    /**
     * 1011. 在 D 天内送达包裹的能力
     * 传送带上的包裹必须在 D 天内从一个港口运送到另一个港口。
     *
     * 传送带上的第 i 个包裹的重量为 weights[i]。每一天，我们都会按给出重量的顺序往传送带上装载包裹。
     * 我们装载的重量不会超过船的最大运载重量。
     *
     * 返回能在 D 天内将传送带上的所有包裹送达的船的最低运载能力。
     * @param weights
     * @param days
     * @return
     */
    public int shipWithinDays(int[] weights, int days) {
        //每次运送最小重量，不能小于包裹的最大值
        int min = Arrays.stream(weights).max().getAsInt();
        //每次运送最大重量，不能超过包裹的总和
        int max = Arrays.stream(weights).sum();
        int mid;
        while (min < max) {
            mid = (min + max)/2;
            if (checkDays(weights,mid) <= days) {
                max = mid;
            } else {
                min = mid + 1;
            }
        }
        return min;
    }

    public int checkDays(int[] weights, int max) {
        int sum = 0,cnt = 1;
        for (int weight : weights) {
            //计算包裹累计和，如果不大于最大承重则继续累加
            //如果超过则天数加一，当前包裹挪到第二天
            if (sum + weight <= max) {
                sum += weight;
            } else {
                cnt ++;
                sum = weight;
            }
        }
        return cnt;
    }

    /**
     * 4. 寻找两个正序数组的中位数
     * 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。
     * 请你找出并返回这两个正序数组的 中位数 。
     * 示例 1：
     *
     * 输入：nums1 = [1,3], nums2 = [2]
     * 输出：2.00000
     * 解释：合并数组 = [1,2,3] ，中位数 2
     * 示例 2：
     *
     * 输入：nums1 = [1,2], nums2 = [3,4]
     * 输出：2.50000
     * 解释：合并数组 = [1,2,3,4] ，中位数 (2 + 3) / 2 = 2.5
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int n = nums1.length + nums2.length;
        //如果是奇数，则返回中位数
        double a = binaryFind(nums1,nums2,0,0,(n + 1) / 2);
        if (n % 2 == 1) {
            return a;
        }
        //如果是偶数，则返回
        double b = binaryFind(nums1,nums2,0,0,(n + 1) / 2 + 1);
        return (a + b) / 2.0d;
    }

    /**
     *
     * @param nums1
     * @param nums2
     * @param i nums1 数组开始查找的位置
     * @param j nums2 数组开始查找的位置
     * @param k 查找第几个位置
     * @return
     */
    public double binaryFind(int[] nums1,int[] nums2,int i,int j,int k) {
        if (i == nums1.length) {//第一个数组空了，则查找nums2
            return nums2[j + k - 1];
        }
        if (j == nums2.length) {//第二个数组空了，则查找nums1
            return nums1[i + k - 1];
        }
        if (k == 1) {//当t== 1时返回两个数组第一个较小元素
            return nums1[i] < nums2[j] ? nums1[i] : nums2[j];
        }
        //二分查找其中
        int a = Math.min(k / 2, nums1.length - i);
        int b = Math.min(k - a, nums2.length - j);
//        a = k - b;
        if (nums1[i + a - 1] <= nums2[j + b - 1]) {
            return binaryFind(nums1,nums2,i + a,j,k - a);
        } else {
            return binaryFind(nums1,nums2, i, j + b,k - b);
        }

    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.twoSum(new int[]{3,2,4}, 6);
    }
}

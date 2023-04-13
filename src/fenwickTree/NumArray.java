package fenwickTree;

/**
 * 307. 区域和检索 - 数组可修改
 * 给你一个数组 nums ，请你完成两类查询，其中一类查询要求更新数组下标对应的值，另一类查询要求返回数组中某个范围内元素的总和。
 *
 * 实现 NumArray 类：
 *
 * NumArray(int[] nums) 用整数数组 nums 初始化对象
 * void update(int index, int val) 将 nums[index] 的值更新为 val
 * int sumRange(int left, int right) 返回子数组 nums[left, right] 的总和（即，nums[left] + nums[left + 1], ..., nums[right]）
 *
 */
public class NumArray {
    FenwickTree tree ;
    public NumArray(int[] nums) {
        tree = new FenwickTree(nums.length);
        for (int i = 0 ; i < nums.length ; i ++) {
            tree.add(i + 1, nums[i]);
        }
    }

    public void update(int index, int val) {
        //更新为val, 相当于原有的值加上一个 val 和原有值之间的差值
        tree.add(index + 1, val - tree.at(index + 1));
    }

    public int sumRange(int left, int right) {
        return tree.query(right + 1) - tree.query(left);
    }
}

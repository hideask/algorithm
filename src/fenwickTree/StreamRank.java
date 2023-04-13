package fenwickTree;

import FactoryPattern.Factory.TestFactory;

/**
 * 面试题 10.10. 数字流的秩
 * 假设你正在读取一串整数。每隔一段时间，你希望能找出数字 x 的秩(小于或等于 x 的值的个数)。请实现数据结构和算法来支持这些操作，也就是说：
 *
 * 实现 track(int x) 方法，每读入一个数字都会调用该方法；
 *
 * 实现 getRankOfNumber(int x) 方法，返回小于或等于 x 的值的个数。
 * x <= 50000
 * track 和 getRankOfNumber 方法的调用次数均不超过 2000 次
 */
public class StreamRank {
    //可以开一个50000大小的数组，对存进来的数据在对应位置计数
    //我们获取小于等于当前值的数据的个数，就是求计数数组对应位置的前缀和

    FenwickTree tree ;
    public StreamRank() {
        //因为存在0 值，数组每位往后偏移一位
        tree = new FenwickTree(50001);
    }

    public void track(int x) {
        tree.add(x + 1, 1);
    }

    public int getRankOfNumber(int x) {
        return tree.query(x + 1);
    }
}

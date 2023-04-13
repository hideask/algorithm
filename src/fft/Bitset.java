package fft;

/**
 * 2166. 设计位集
 * 位集 Bitset 是一种能以紧凑形式存储位的数据结构。
 *
 * 请你实现 Bitset 类。
 *
 * Bitset(int size) 用 size 个位初始化 Bitset ，所有位都是 0 。
 * void fix(int idx) 将下标为 idx 的位上的值更新为 1 。如果值已经是 1 ，则不会发生任何改变。
 * void unfix(int idx) 将下标为 idx 的位上的值更新为 0 。如果值已经是 0 ，则不会发生任何改变。
 * void flip() 翻转 Bitset 中每一位上的值。换句话说，所有值为 0 的位将会变成 1 ，反之亦然。
 * boolean all() 检查 Bitset 中 每一位 的值是否都是 1 。如果满足此条件，返回 true ；否则，返回 false 。
 * boolean one() 检查 Bitset 中 是否 至少一位 的值是 1 。如果满足此条件，返回 true ；否则，返回 false 。
 * int count() 返回 Bitset 中值为 1 的位的 总数 。
 * String toString() 返回 Bitset 的当前组成情况。注意，在结果字符串中，第 i 个下标处的字符应该与 Bitset 中的第 i 位一致。
 */
public class Bitset {
    int base,
            size,
            cnt,
            n;
    int[] data ;

    /**
     * 用 size 个位初始化 Bitset ，所有位都是 0 。
     * @param size
     */
    public Bitset(int size) {
        //使用32位int值去表示对应的二进制位
        this.size = size;
        cnt = 0;
        //使用其中30位二进制位
        base = 30;
        //表示需要多少个int，每一个int表示其中一段
        n = size / base + (size % base == 0 ? 0 : 1);
        data = new int[n];
    }

    /**
     * 将下标为 idx 的位上的值更新为 1 。如果值已经是 1 ，则不会发生任何改变。
     * @param idx
     */
    public void fix(int idx) {
        int x = idx / base;
        int y = idx % base;
        if ((data[x] & (1 << y)) == 0) {
            data[x] |= (1 << y);
            cnt ++;
        }
    }

    public void unfix(int idx) {
        int x = idx / base;
        int y = idx % base;
        if ((data[x] & (1 << y)) != 0) {
            data[x] ^= (1 << y);
            cnt --;
        }
    }

    public void flip() {
        for (int i = 0 ;i < n; i ++) {
            //按位取反
            data[i] = ~data[i];
        }
        cnt = size - cnt;
    }

    public boolean all() {
        return cnt == size;
    }

    public boolean one() {
        return cnt != 0;
    }

    public int count() {
        return cnt;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int x = size / base;
        int y = size % base;
        for (int i = 0 ;i < x ;i ++) {
            for (int j = 0 ; j < base; j ++) {
                if ((data[i] & (1 << j)) == 0) {
                    sb.append("0");
                } else {
                    sb.append("1");
                }
            }
        }
        for (int j = 0 ;j < y ; j ++) {
            if ((data[n - 1] & (1 << j)) == 0) {
                sb.append("0");
            } else {
                sb.append("1");
            }
        }
        return sb.toString();
    }
}

package link;

/**
 * 202
 * 编写一个算法来判断一个数 n 是不是快乐数。
 *
 * 「快乐数」定义为：
 *
 * 对于一个正整数，每一次将该数替换为它每个位置上的数字的平方和。
 * 然后重复这个过程直到这个数变为 1，也可能是 无限循环 但始终变不到 1。
 * 如果 可以变为  1，那么这个数就是快乐数。
 * 如果 n 是快乐数就返回 true ；不是，则返回 false 。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/happy-number
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * description:HappyNum
 * create user: songj
 * date : 2021/3/8 16:39
 */
public class HappyNum {
    //输入：19
    //输出：true
    //解释：
    //12 + 92 = 82
    //82 + 22 = 68
    //62 + 82 = 100
    //12 + 02 + 02 = 1
    //19->82->68->100->1->1
    public boolean isHappy(int n) {
        //双指针防止形成环
        int slow = n,quick = n;
        do {
            slow = getNext(slow);
            quick = getNext(getNext(quick));
        } while (slow != quick && quick != 1);
        return quick == 1;
    }

    public int getNext(int n) {
        int m = 0;
        while (n > 0) {
            m += (n%10) * (n%10);
            n = n/10;
        }
        return m;
    }

    public static void main(String[] args) {
        HappyNum hn = new HappyNum();
        int num = 0;
        for (int n = 0; n <= 100000 ;n++) {
            if (hn.isHappy(n)) {
                num += n;
            }
        }
        System.out.println(num);
    }
}

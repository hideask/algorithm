package binarysearch;

/**
 * description:TwoFind
 * create user: songj
 * date : 2021/6/3 20:46
 */
public class TwoFind {

    public int binarySearch(int[] nums,int x, int r) {
        int head = 0, tail = r - 1, mididx, mid;
        while (head <= tail) {
            mididx = (head + tail) / 2;
            mid = nums[mididx];
            if (x > mid) {
                head = mididx + 1;
            } else if (x == mid) {
                return mididx;
            } else {
                tail = mididx - 1;
            }
        }
        return -1 ;
    }

    public int binarySearch_1(int[] nums,int x, int r) {
        int head = 0, tail = r - 1, mididx, mid;
        while (head < tail) {
            mididx = (head + tail) / 2;
            mid = nums[mididx];
            if (x > mid) {
                head = mididx + 1;
            } else {
                tail = mididx;
            }
        }
        return head ;
    }

    public int binarySearch_2(int[] nums,int x, int r) {
        int head = 0, tail = r - 1, mididx, mid;
        while (tail - head > 3) {
            mididx = (head + tail) / 2;
            mid = nums[mididx];
            if (x > mid) {
                head = mididx + 1;
            } else if (x == mid) {
                return mididx;
            } else {
                tail = mididx - 1;
            }
        }
        for (int i = head; i <= tail; i ++) {
            if (nums[i] == x) {
                return i;
            }
        }
        return -1 ;
    }

    public static void main(String[] args) {
        TwoFind twoFind = new TwoFind();
//        System.out.println(twoFind.binarySearch(new int[]{1,2,3,4,5,6,7,8,9}, 7, 9));
        System.out.println(twoFind.binarySearch_1(new int[]{1,2,3,4,5,6,17,18,19}, 20, 9));
//        System.out.println(twoFind.binarySearch_2(new int[]{1,2,3,4,5,6,7,8,9}, 7, 9));
    }
}

package euclid;

import java.util.Scanner;

/**
 * 欧拉公式和欧几里得算法
 */
public class EulerAndEuclid {
    /**
     * 欧几里得算法，也称辗转相除算法，求两个整数的最大公约数
     */
    public int pcd (int a, int b) {
        if (b == 0) {
            return a;
        }
        return pcd(b, a % b);
    }

//    public int  {
//
//    }

    public static void main(String[] args) {
        EulerAndEuclid ea = new EulerAndEuclid();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int a = scanner.nextInt() , b = scanner.nextInt();
            int c = ea.pcd(a, b);
            System.out.println(c);
        }
    }
}

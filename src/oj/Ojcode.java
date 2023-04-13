package oj;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Ojcode {
    public double fib(int n) {
        if (n == 0) {
            return 0d;
        }
        if (n == 1) {
            return 1d;
        }
        double[] fn = new double[n + 1];
        fn[0] = 0d;
        fn[1] = 1d;
        for (int i = 2; i <= n; i++) {
            fn[i] = fn[i - 1] + (fn[i - 2]) ;
//            fn[i] %= 1000000007;
        }
        return fn[n];
    }

    Set arrss ;

    /**
     * 4、埃氏筛法求找素数
     * 解释：
     * 埃氏筛法运行原理：
     *
     * 要得到自然数n以内的全部素数，必须把不大于 的所有素数的倍数剔除，剩下的就是素数。
     * 给出要筛数值的范围n，找出以内的素数。先用2去筛，即把2留下，把2的倍数剔除掉；再用下一个质数，也就是3筛，把3留下，把3的倍数剔除掉；
     * 接下去用下一个质数5筛，把5留下，把5的倍数剔除掉；不断重复下去…。
     * 如果想知道第N位素数需要从多少个自然数中找，那么还需要根据素数定理，在素数定理中可以给出第n个素数p(n)的渐近估计：在这里插入图片描述 。
     * 它也给出从整数中抽到素数的概率。从不大于n的自然数随机选一个，它是素数的概率大约是在这里插入图片描述。
     *
     * 代码大概的方法是申请一个布尔类型的数组，利用埃氏筛法筛选，当该数组的值是true的时候的们就认为该数不是素数。找出第1百万位素数 运行时间1856毫秒约等于2秒钟。
     */
    public void primeNumber(double n) {
        //已知在整数X内有大约X/log(X)个素数
        double m = 6;
        while (m / Math.log(m) < n) {
            m ++;
        }
        //开辟一个数组，下标是自然数，值是标记
        //通过筛选把非素数标记出来
        arrss = new HashSet();
        double x = 2;
        while (x < n) {
            //标记过了，下一个
            if (arrss.contains(x)) {
                x ++ ;
                continue;
            }

            //对于x,从2倍开始，对x的倍数进行标记
            for (double k = 2; x * k < n; k ++) {
                arrss.add(x * k);
            }
            x ++;
        }
    }

    public boolean check(double n) {

        for (double i = 2d; i < Math.sqrt(n); i++) {

            if (n % i == 0d) {
                return true;
            }
        }
        return false;
    }


    public static void main (String[]args){
//        Scanner scanner = new Scanner(System.in);
//        String s = scanner.nextLine();
//        System.out.println(isValid(s));

//        Scanner sc = new Scanner(System.in);
//        int n = Integer.valueOf(sc.nextLine());
//        String[] cards = new String[n];
//        for (int i = 0; i < n; i ++) {
//            cards[i] = sc.nextLine();
//        }
//
//        Arrays.sort(cards,(a, b) ->{
//            String csnya = a.substring(6,14);
//            String csnyb = b.substring(6,14);
//            if (csnya.equals(csnyb)) {
//                return a.compareTo(b);
//            } else {
//                return csnyb.compareTo(csnya);
//            }
//        });
//        for (String card: cards) {
//            System.out.println(card);
//        }
//        sc.close();

//        Scanner sc = new Scanner(System.in);
//        int n = sc.nextInt();
//        int[] nums = new int[n];
//        Set<Integer> set = new HashSet<>();
//        for (int i = 0 ; i < n; i ++) {
//            int m = sc.nextInt();
//            nums[i] = m;
//        }
//        Arrays.sort(nums);
//        StringBuilder strnum = new StringBuilder();
//        for (int i = 0 ; i < n; i ++) {
//            int j = nums[i];
//            if (set.add(j)) {
//                if (i < n - 1) {
//                    strnum.append(j).append(" ");
//                } else {
//                    strnum.append(j);
//                }
//            }
//        }
//        System.out.println(strnum.toString());
//        sc.close();
//        System.out.println("513821199104226178".compareTo("4138211991042261710"));


//        Scanner sc = new Scanner(System.in);
//        int num = sc.nextInt();
//        if (num == 0) {
//            System.out.println("No durian today.Sad(T T).");
//        } else if (num == 1) {
//            System.out.println("I ate a durian today!");
//        } else if (num > 1 && num <100) {
//            System.out.println("I ate " + num +" durian today!");
//        }

//        Scanner sc = new Scanner(System.in);
//        String str = sc.nextLine();
//        String[] strs = str.split("");
//        StringBuilder sb = new StringBuilder();
//        for (String s : strs) {
//            if (s.compareTo("a") >= 0 && s.compareTo("z") <= 0 ) {
//                s = s.toUpperCase();
//            }
//            if (s.compareTo("A") >= 0 && s.compareTo("Z") <= 0 ) {
//                s = s.toLowerCase();
//            }
//            sb.append(s);
//        }
//        System.out.println(sb.toString());

//        Scanner sc = new Scanner(System.in);
//        Integer cnt = Integer.valueOf(sc.nextLine());
//        String[] strs ;
//        String[][] records = new String[cnt][2];
//        for (int i = 0; i < cnt; i ++) {
//            strs = sc.nextLine().split(" ");
//            Integer count = 0;
//
//            for (int j = 1; j < cnt; j++) {
//                count += Integer.valueOf(strs[j]);
//            }
//            records[i][0] = strs[0];
//            records[i][1] = count.toString();
//        }
//        Arrays.sort(records, (a, b) -> {
//            return Integer.valueOf(b[1]) - Integer.valueOf(a[1]);
//        });
//        for (int i = 0; i < 3 ; i ++) {
//            System.out.println(records[i][1]);
//        }

//        Scanner sc = new Scanner(System.in);
//        Integer cnt = Integer.valueOf(sc.nextLine());
//        String[] strs = sc.nextLine().split(" ");
//        int min = new Integer(strs[0]);
//        for (int j = 1; j < cnt; j++) {
//            min = Math.min(new Integer(strs[j]),min);
//        }
//        System.out.println(Integer.valueOf(min));

            Ojcode ojcode = new Ojcode();
            Scanner sc = new Scanner(System.in);
            Integer cnt = Integer.valueOf(sc.nextLine());
            int i = 1;
            double m = 0d;
//            ojcode.primeNumber(100000000d);
            for (int j = 5; j < 100000; j++) {
                m = ojcode.fib(j);
//                if (ojcode.arrss.contains(m)) {
//                    System.out.println(new BigDecimal(m).toString());
//                    if (i == cnt) {
//                        return;
//                    }
//                    i++;
//                }

                if (ojcode.check(m)) {
                    System.out.println(new BigDecimal(m).toString());
                    if (i == cnt) {
                        return;
                    }
                    i++;
                }
            }
        }
    }

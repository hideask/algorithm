package stringMatch;

import java.util.Scanner;

/**
 * 字符串匹配算法
 */
public class StringMatch {
    public static void main(String[] args) {
        StringMatch sm = new StringMatch();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String s1 = scanner.next();
            String s2 = scanner.next();

            System.out.println(sm.kmp(s1, s2));
        }
    }
    /**
     * 暴力匹配算法
     */
    //public int

    /**
     * kmp算法
     * @param text
     * @param parrern
     * @return
     */
    public int kmp(String text, String parrern) {
        int n = parrern.length();
        int m = text.length();
        int[] next = new int[n];
        getNext(parrern, next) ;
        for (int i = 0 ,j = -1;i < m ; i ++) {
            while (j != -1 && text.charAt(i) != parrern.charAt(j + 1)) {
                j = next[j];
            }
            if (text.charAt(i) == parrern.charAt(j + 1)) {
                j += 1;
            }
            if (j + 1 == n) {
                return i - j;
            }
        }
        return -1;
    }

    public void getNext(String parrern, int[] next) {
        int n = parrern.length();
        //默认0位跳转位置为-1
        next[0] = -1;
        for (int i = 1, j = -1; i < n ; i ++) {
            //当遇到第一个不匹配的字符时，跳到上一个j位置
            while (j != -1 && parrern.charAt(i) != parrern.charAt(j + 1)) {
                j = next[j];
            }

            //匹配处理相同字符串的跳转位置
            if (parrern.charAt(i) == parrern.charAt(j + 1)) {
                j += 1;
            }
            next[i] = j;
        }
    }
}

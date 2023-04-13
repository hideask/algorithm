package stringMatch;

import com.sun.org.apache.bcel.internal.generic.PUTFIELD;

public class Solution {
    /**
     * 28. 实现 strStr()
     * 实现 strStr() 函数。
     *
     * 给你两个字符串 haystack 和 needle ，请你在 haystack 字符串中找出 needle 字符串出现的第一个位置（下标从 0 开始）。
     * 如果不存在，则返回  -1 。
     * @param haystack
     * @param needle
     * @return
     */
    public int strStr(String haystack, String needle) {
        if (needle == null || needle.equals("")) {
            return 0;
        }
        return kmp(haystack,needle);
    }

    public int kmp(String text, String parrern) {
        int m = text.length();
        int n = parrern.length();
        int[] next = new int[n];
        getNext(parrern,next);

        for (int i = 0, j = -1; i < m ; i ++) {
            while (j != -1 && text.charAt(i) != parrern.charAt(j + 1)) {
                j = next[j];
            }
            if (text.charAt(i) == parrern.charAt(j + 1)) {
                j ++;
            }
            if (j + 1 == n) {
                return i - j;
            }
        }
        return -1;
    }

    public void getNext(String str , int[] next) {
        int n = str.length();
        next[0] = -1;

        for (int i = 1 , j = -1; i < n ; i ++) {
            while (j != -1 && str.charAt(i) != str.charAt(j + 1)) {
                j = next[j];
            }
            if (str.charAt(i) == str.charAt(j + 1)) {
                j ++;
            }
            next[i] = j;
        }
    }

    /**
     * 459. 重复的子字符串
     * 给定一个非空的字符串，判断它是否可以由它的一个子串重复多次构成。给定的字符串只含有小写英文字母，并且长度不超过10000。
     * @param s
     * @return
     */
    public boolean repeatedSubstringPattern(String s) {
        int n = s.length();
        int[] next = new int[n];
        getNext(s,next);
        int j = next[n - 1];
        if (j == -1) {
            return false;
        }
        if (n % (n - j -1) == 0) {
            return true;
        }
        return false;
    }

    /**
     * 3. 无重复字符的最长子串
     * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        if (s.isEmpty()) {
            return 0;
        }
        //二分算法，分的是最大长度，定义一个滑窗大小mid, 如果滑窗内字符串
        //不重复，找到最大的滑窗
        int head = 1,tail = s.length(),mid = 0;
        while (head < tail) {
            mid = (tail + head + 1) / 2;
            if (check(mid,s)) {
                head = mid;
            } else {
                tail = mid - 1;
            }
        }
        return head;
    }

    public boolean check(int l, String s) {
        int[] cnt = new int[256];
        int n = s.length(), k = 0;
        for (int i = 0;i < n; i ++) {
            if (cnt[s.charAt(i)] == 0) {
                k ++;
            }
            cnt[s.charAt(i)] ++;

            //窗口滑动
            if (i >= l) {
                //剪掉最左边
                cnt[s.charAt(i - l)] --;
                if (cnt[s.charAt(i - l)] == 0) {
                    k --;
                }
            }
            //说明最长的不重复找到了
            if (k == l) {
                return true;
            }
        }
        return false;
    }

    /**
     * 14. 最长公共前缀
     * 编写一个函数来查找字符串数组中的最长公共前缀。
     *
     * 如果不存在公共前缀，返回空字符串 ""。
     * @param strs
     * @return
     */
    public String longestCommonPrefix(String[] strs) {
        int n = strs.length;
        if (n == 1) {
            return strs[0];
        }
        //默认前缀为第一个字符串
        String sb = strs[0];
        int m = sb.length();
        String str = "";
        for (int i = 1; i < n ; i ++) {
            str = strs[i];
            //公共前缀不会比第一个字符串更长
            if (str.length() < m) {
                sb = sb.substring(0,str.length());
                m = str.length();
            }
            for (int j = 0 ; j < str.length(); j ++) {
                if (j + 1 > m) {
                    break;
                }
                if (str.charAt(j) == sb.charAt(j)) {
                    continue;
                } else {
                    if (j == 0) {
                        sb = "";
                    } else {
                        sb = sb.substring(0,j);
                    }
                    m = j ;
                    break;
                }
            }
            if (sb.equals("")) {
                break;
            }
        }
        return sb;
    }

    /**
     * 面试题 01.05. 一次编辑
     * 字符串有三种编辑操作:插入一个字符、删除一个字符或者替换一个字符。 给定两个字符串，
     * 编写一个函数判定它们是否只需要一次(或者零次)编辑。
     * @param first
     * @param second
     * @return
     */
    public boolean oneEditAway(String first, String second) {
        int m = first.length();
        int n = second.length();
        if (Math.abs(m - n) >= 2) {
            return false;
        }
        //让长的等于first ,短的等于second
        if (m < n) {
            String tmp = first;
            first = second;
            second = tmp;
            m = first.length();
            n = second.length();
        }

        if (m == n) {
            int k = 0;
            for (int i = 0; i < m; i ++) {
                if (first.charAt(i) == second.charAt(i)) {
                    continue;
                }
                k ++;
                if (k > 1) {
                    return false;
                }
            }
            return true;
        }
        int i = 0, j = n - 1;
        while (i <= j && first.charAt(i) == second.charAt(i)) i ++;
        while (i <= j && first.charAt(j + 1) == second.charAt(j)) j --;

        return i > j;

    }

    /**
     * 12. 整数转罗马数字
     * 罗马数字包含以下七种字符： I， V， X， L，C，D 和 M。
     *
     * 字符          数值
     * I             1
     * V             5
     * X             10
     * L             50
     * C             100
     * D             500
     * M             1000
     * 例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII,
     * 即为 XX + V + II 。
     *
     * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，
     * 而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，
     * 数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
     *
     * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
     * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。
     * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
     * 给你一个整数，将其转为罗马数字。
     * @param num
     * @return
     */
    Integer num1 ;
    public String intToRoman(int num) {
        String res = "";
        num1 = num;
        while (num1 != 0) {
            res += output();
        }
        return res;
    }

    public String output() {
        if (num1 >= 1000) {
            int d = num1 / 1000;
            num1 -= d * 1000;
            return romanStr(d, 'M','M','M');
        } else if (num1 >= 100) {
            int d = num1 / 100;
            num1 -= d * 100;
            return romanStr(d,'C', 'D', 'M');
        } else if (num1 >= 10) {
            int d = num1 / 10;
            num1 -= d * 10;
            return romanStr(d,'X','L','C');
        } else {
            int d = num1;
            num1 = 0;
            return romanStr(d,'I','V','X');
        }
    }

    public String romanStr(int d, char a, char b, char c) {
        String res = "";
        if (d == 4 || d == 9) {
            res += a;
            res += d == 4 ? b : c;
            return res;
        }
        if (d >= 5) {
            res += b;
            d -= 5;
        }
        while (d > 0) {
            res += a;
            d --;
        }
        return res;
    }

    /**
     * 1392. 最长快乐前缀
     * 「快乐前缀」是在原字符串中既是 非空 前缀也是后缀（不包括原字符串自身）的字符串。
     *
     * 给你一个字符串 s，请你返回它的 最长快乐前缀。
     *
     * 如果不存在满足题意的前缀，则返回一个空字符串。
     * @param s
     * @return
     */
    public String longestPrefix(String s) {
        //kmp获得next数组的过程
        int n = s.length();
        int[] next = new int[n];
        next[0] = -1;
        for (int i = 1, j = -1; i < n ; i ++) {
            while (j != -1 && s.charAt(i) != s.charAt(j + 1)) {
                j = next[j];
            }
            if (s.charAt(i) == s.charAt(j + 1)) {
                j ++;
            }
            next[i] = j;
        }
        return s.substring(0,next[n - 1] + 1);
    }

    /**
     * 214. 最短回文串
     * 给定一个字符串 s，你可以通过在字符串前面添加字符将其转换为回文串。找到并返回可以用这种方式转换的最短回文串。
     *
     * @param s
     * @return
     */
    public String shortestPalindrome(String s) {
        int n = s.length();
        //将s翻转，例如aabc翻转为cbaa
        String s1 = "";
        for (int i = n - 1; i >= 0 ; i --) {
            s1 += s.charAt(i);
        }
        s = s + "#" + s1;
        //求next数组
        int m = s.length();
        int[] next = new int[m];
        next[0] = -1;
        for (int i = 1, j = -1;i < m ; i ++) {
            while (j != -1 && s.charAt(i) != s.charAt(j + 1) ) {
                j = next[j];
            }
            if (s.charAt(i) == s.charAt(j + 1)) {
                j ++;
            }
            next[i] = j;
        }
        String res = s1 + s.substring(next[m - 1] + 1, n) ;
        return res;
    }

    /**
     * 5. 最长回文子串
     * 给你一个字符串 s，找到 s 中最长的回文子串。
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        int n = s.length();
        String newstr = "#";
        //马拉车算法
        for (int i = 0; i < n ; i ++) {
            newstr += s.charAt(i) + "#";
        }

        //回文串左边界，回文串右边界
        int l = 0 , r = -1;
        n = newstr.length();
        int[] d = new int[n];
        for (int i = 0 ; i < n ; i ++) {
            //默认回文串长度为1
            if (i > r) {
                d[i] = 1;
            } else {
                //如果超过当前最长回文串的最大长度，取i到边界和i的对称点的最长回文串长度中的最小值作为半径
                d[i] = Math.min(r - i , d[l + (r - i)]);
            }

            //朴素匹配算法,以i为中心，向两边扩展
            while (i - d[i] >= 0 && i + d[i] < n
                    && newstr.charAt(i - d[i]) == newstr.charAt(i + d[i])) {
                d[i] ++;
            }

            //边界扩展
            if (i + d[i] > r && i - d[i] > 0) {
                r = i + d[i];
                l = i - d[i];
            }
        }
        String res = "";
        int len = 0;
        for (int i = 0; i < n; i ++) {
            if (d[i] <= len) {
                continue;
            }
            res = "";
            len = d[i];
            for (int j = i - d[i] + 1; j < i + d[i]; j ++) {
                if (newstr.charAt(j) == '#') {
                    continue;
                }
                res += newstr.charAt(j);
            }
        }

        return res;

    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        solution.intToRoman(8200);
        solution.longestPalindrome("babad");
    }
}


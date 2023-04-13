package huffmanTree;

import java.util.*;
import java.util.stream.Collectors;

public class Solution {

    /**
     * 面试题 17.17. 多次搜索
     * 给定一个较长字符串big和一个包含较短字符串的数组smalls，设计一个方法，根据smalls中的每一个较短字符串，对big进行搜索。
     * 输出smalls中的字符串在big里出现的所有位置positions，其中positions[i]为smalls[i]出现的所有位置。
     * @param big
     * @param smalls
     * @return
     */
    public int[][] multiSearch(String big, String[] smalls) {
        int n = smalls.length;
        int[][] res = new int[n][];
        for (int i = 0; i < n ; i ++) {
            res[i] = sunday(big, smalls[i]);
        }
        return res;
    }

    /**
     * sunday 算法
     * @param text
     * @param str
     * @return
     */
    public int[] sunday(String text, String str) {
        int m = text.length(), n = str.length();
        if (n == 0) {
            return new int[0];
        }
        List<Integer> ans = new ArrayList<>();
        //记录每个字符出现在哪个位置
        int[] last_pos = new int[128];
        for (int i = 0 ; i < n ; i ++) {
            last_pos[str.charAt(i)] = i ;
        }
        //处理匹配，黄金对齐点位在 i + m 处，在 str 里面黄金对齐点位出现在
        //倒数第几位，我们就把i 往后推几位
        int i = 0;
        while (i + n <= m) {
            boolean flag = true;
            for (int j = 0; j < n ; j ++) {
                if (text.charAt(i + j) == str.charAt(j)) {
                    continue;
                }
                flag = false;
                break;
            }
            if (flag) {
                ans.add(i);
                i ++;
            } else {
                if (i + n >= m) {
                    break;
                }
                i += (n - last_pos[text.charAt(i + n)]);
            }
        }

        int[] res = new int[ans.size()];
        for (int j = 0 ; j < ans.size(); j ++) {
            res[j] = ans.get(j);
        }

        return res;
    }

    /**
     * 32. 最长有效括号
     * 给你一个只包含 '(' 和 ')' 的字符串，找出最长有效（格式正确且连续）括号子串的长度。
     * @param s
     * @return
     */
    public int longestValidParentheses(String s) {
        //不能用栈，因为是求最长
        int n = s.length();
        if (n == 0) {
            return 0;
        }
//        Deque<Character> deque = new LinkedList<>();
//        int res = 0;
//        deque.addLast(s.charAt(0));
//        for (int i = 1 ; i < n ; i ++) {
//            if (s.charAt(i) == ')') {
//                if (!deque.isEmpty() && deque.peekLast() == '(') {
//                    deque.pollLast();
//                    res += 2;
//                    continue;
//                }
//            }
//            deque.addLast(s.charAt(i));
//        }
//        return res;

        //动态规划
        int[] dp = new int[n];
        int ans = 0;
        for (int i = 1 ; i < n ; i ++) {
            //如果当前是左括号，连续最长就为0
            if (s.charAt(i) == '(') {
                continue;
            }
            //如果当前是右括号，并且前一个是左括号
            if (s.charAt(i) == ')' && s.charAt(i - 1) == '(') {
                if (i - 2 < 0) {
                    dp[i] = 2;
                } else {
                    dp[i] = dp[i - 2] + 2;
                }
            } else {
                //说明最近连续两个都是右括号，我们往前找到连续匹配最前的那个位置
                int j = i - dp[i - 1] - 1;
                //如果为右括号，说明和i位匹配不上
                if (j < 0 || s.charAt(j) == ')') {
                    continue;
                }
                if (j - 1 < 0) {
                    dp[i] = dp[i - 1] + 2;
                } else {
                    dp[i] = dp[i - 1] + 2 + dp[j - 1];
                }
            }
            ans = Math.max(dp[i], ans);
        }
        return ans;
    }

    /**
     * 76. 最小覆盖子串
     * 给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，
     * 则返回空字符串 "" 。
     *
     * 注意：
     *
     * 对于 t 中重复字符，我们寻找的子字符串中该字符数量必须不少于 t 中该字符数量。
     * 如果 s 中存在这样的子串，我们保证它是唯一的答案。
     * @param s
     * @param t
     * @return
     */
    public String minWindow(String s, String t) {
        //滑动窗口法
        int cnt = 0;//记录亏损的字符串
        int[] cnts = new int[128];
        //记录t中每个字符出现的次数，相当于出现亏损状态
        for (char c : t.toCharArray()) {
            cnts[c] --;
            if (cnts[c] == -1) {
                cnt ++;
            }
        }

        int l = 0 , r = 0 , ans_len = s.length() + 1;
        String ans = "";
        while (r <= s.length()) {
            //如果r 大于0 ，说明 t中还有没匹配的字符，r 往后移
            if (cnt > 0) {
                if (r == s.length()) {
                    break;
                }
                //不在t中的字符会是正数
                cnts[s.charAt(r)] ++;
                if (cnts[s.charAt(r)] == 0) {
                    cnt --;
                }
                r ++;
            } else {
                //说明t已经匹配完了，我们需要缩短窗口的大小，以找到最短
                //不在t中的字符会减为0，在t中的又会减为负数，cnt只会统计t中的字符
                cnts[s.charAt(l)] --;
                if (cnts[s.charAt(l)] == -1) {
                    cnt ++;
                }
                l ++;
            }
            if (cnt == 0 && r - l < ans_len) {
                ans_len = r - l;
                ans = s.substring(l, r);
            }
        }
        return ans;
    }

    /**
     * 468. 验证IP地址
     * 编写一个函数来验证输入的字符串是否是有效的 IPv4 或 IPv6 地址。
     *
     * 如果是有效的 IPv4 地址，返回 "IPv4" ；
     * 如果是有效的 IPv6 地址，返回 "IPv6" ；
     * 如果不是上述类型的 IP 地址，返回 "Neither" 。
     * IPv4 地址由十进制数和点来表示，每个地址包含 4 个十进制数，其范围为 0 - 255， 用(".")分割。比如，172.16.254.1；
     *
     * 同时，IPv4 地址内的数不会以 0 开头。比如，地址 172.16.254.01 是不合法的。
     *
     * IPv6 地址由 8 组 16 进制的数字来表示，每组表示 16 比特。这些组数字通过 (":")分割。
     * 比如,  2001:0db8:85a3:0000:0000:8a2e:0370:7334 是一个有效的地址。
     * 而且，我们可以加入一些以 0 开头的数字，字母可以使用大写，也可以是小写。
     * 所以， 2001:db8:85a3:0:0:8A2E:0370:7334 也是一个有效的 IPv6 address地址 (即，忽略 0 开头，忽略大小写)。
     *
     * 然而，我们不能因为某个组的值为 0，而使用一个空的组，以至于出现 (::) 的情况。
     * 比如， 2001:0db8:85a3::8A2E:0370:7334 是无效的 IPv6 地址。
     *
     * 同时，在 IPv6 地址中，多余的 0 也是不被允许的。比如， 02001:0db8:85a3:0000:0000:8a2e:0370:7334 是无效的。
     * @param queryIP
     * @return
     */
    public String validIPAddress(String queryIP) {
        int n = queryIP.length();
        int cnt = 0, pre_idx = 0, flag = 0;
        for (int i = 0 ; i <= n ;i ++) {
            if ((i == n && flag == 1) || (i < n && queryIP.charAt(i) == '.' )) {
                if (flag == 1) {
                    flag = 0;
                }
                if (flag == 0 && validIPv4(queryIP, pre_idx, i)) {
                    flag = 1;
                    cnt ++;
                    pre_idx = i + 1;
                    continue;
                }
                flag = -1;
            }
            if ((i == n && flag == 2) || (i < n &&queryIP.charAt(i) == ':')) {
                if (flag == 2) {
                    flag = 0;
                }
                if (flag == 0 && validIPv6(queryIP, pre_idx, i)) {
                    flag = 2;
                    cnt ++;
                    pre_idx = i + 1;
                    continue;
                }
                flag = -1;
            }
            if (flag == -1) {
                break;
            }
        }
        if (cnt == 4 && flag == 1) {
            return "IPv4";
        } else if (cnt == 8 && flag == 2) {
            return "IPv6";
        } else {
            return "Neither";
        }
    }

    public boolean validIPv4(String str, int l ,int r) {
        if (r - l <= 0 || r - l > 3) {
            return false;
        }
        String sub = str.substring(l, r);
        int sum = 0;
        for (int i = 0 ; i < sub.length(); i ++) {
            char c = sub.charAt(i);
            if (i == 0 && sub.length() > 1 && c == '0') {
                return false;
            }
            if (!(c >= '0' && c <= '9')) {
                return false;
            }

            sum = sum * 10 + (c - '0');
        }
        return sum <= 255;
    }

    public boolean validIPv6(String str, int l ,int r) {
        if (r - l <= 0 || r - l > 4) {
            return false;
        }
        String sub = str.substring(l, r);
        for (int i = 0 ; i < sub.length(); i ++) {
            char c = sub.charAt(i);

            if (c >= '0' && c <= '9') {
                continue;
            }
            if (c >= 'a' && c <= 'f') {
                continue;
            }
            if (c >= 'A' && c <= 'F') {
                continue;
            }
            return false;
        }
        return true;
    }

    /**
     * 89. 格雷编码
     * 格雷编码是一个二进制数字系统，在该系统中，两个连续的数值仅有一个位数的差异。
     *
     * 给定一个代表编码总位数的非负整数 n，打印其格雷编码序列。即使有多个不同答案，你也只需要返回其中一种。
     *
     * 格雷编码序列必须以 0 开头。
     * @param n
     * @return
     */
    public List<Integer> grayCode(int n) {
        //要求n位的格雷编码，我们可以先求n - 1位的编码，n位编码比n - 1位多一倍
        //我们可以将n - 1位编码最后一位补全一个0 作为n位编码的上半部分，
        //n位后半部分为将 n - 1位的格雷编码倒叙，最后一位补全为1
        int[] gray = grayCode1(n);
        List<Integer> res = new ArrayList<>();
        res = Arrays.stream(gray).boxed().collect(Collectors.toList());

        return res;
    }

    public int[] grayCode1(int n) {
        //要求n位的格雷编码，我们可以先求n - 1位的编码，n位编码比n - 1位多一倍
        //我们可以将n - 1位编码最后一位补全一个0 作为n位编码的上半部分，
        //n位后半部分为将 n - 1位的格雷编码倒叙，最后一位补全为1
        int m = 1 << n;
        int[] res = new int[m];
        if (n == 0) {
            res[0] = 0;
            return res;
        }
        int[] n_1 = grayCode1(n - 1);
        int n_1_len = n_1.length;
        for (int i = 0 ; i < n_1_len ; i ++) {
            res[i] = (n_1[i] << 1);
            res[2 * n_1_len - i - 1] = (n_1[i] << 1 | 1);
        }
        return res;
    }


    public static void main(String[] args) {
        Solution solution = new Solution();

        solution.multiSearch("mississippi", new String[]{"is","ppi","hi","sis","i","ssippi"});
        System.out.println('1' - '0');
        solution.validIPAddress("192.0.0.1");
    }
}

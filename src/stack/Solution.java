package stack;

import jdk.nashorn.internal.objects.annotations.Where;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * description:Solution
 * create user: songj
 * date : 2021/3/22 16:03
 */
public class Solution {

    /**
     * 682. 棒球比赛
     * 你现在是一场采用特殊赛制棒球比赛的记录员。这场比赛由若干回合组成，过去几回合的得分可能会影响以后几回合的得分。
     * <p>
     * 比赛开始时，记录是空白的。你会得到一个记录操作的字符串列表 ops，其中 ops[i] 是你需要记录的第 i 项操作，ops 遵循下述规则：
     * <p>
     * 整数 x - 表示本回合新获得分数 x
     * "+" - 表示本回合新获得的得分是前两次得分的总和。题目数据保证记录此操作时前面总是存在两个有效的分数。
     * "D" - 表示本回合新获得的得分是前一次得分的两倍。题目数据保证记录此操作时前面总是存在一个有效的分数。
     * "C" - 表示前一次得分无效，将其从记录中移除。题目数据保证记录此操作时前面总是存在一个有效的分数。
     * 请你返回记录中所有得分的总和
     *
     * @param ops
     * @return
     */
    public int calPoints(String[] ops) {
        int a;
        int b;
        Stack<Integer> stack = new Stack<>();
        for (String score : ops) {
            switch (score) {
                case "+":
                    a = stack.pop();
                    b = a + stack.peek();

                    stack.push(a);
                    stack.push(b);
                    break;
                case "D":
                    stack.push(stack.peek() * 2);
                    break;
                case "C":
                    stack.pop();
                    break;
                default:
                    stack.push(Integer.parseInt(score));
                    break;
            }
        }
        int num = 0;
        while (!stack.isEmpty()) {
            num += stack.pop();
        }
        return num;
    }

    /**
     * 844. 比较含退格的字符串
     * 给定 S 和 T 两个字符串，当它们分别被输入到空白的文本编辑器后，判断二者是否相等，并返回结果。 # 代表退格字符。
     * <p>
     * 注意：如果对空文本输入退格字符，文本继续为空。
     *
     * @param S
     * @param T
     * @return
     */
    public boolean backspaceCompare(String S, String T) {
        return backspace(S).equals(backspace(T));
    }

    public String backspace(String s) {
        StringBuilder sb = new StringBuilder();
        char a;
        for (int i = 0; i < s.length(); i++) {
            a = s.charAt(i);
            if (a == '#') {
                if (sb.length() > 0) {
                    sb.deleteCharAt(sb.length() - 1);
                }
            } else {
                sb.append(a);
            }
        }
        return sb.toString();
    }

    /**
     * 双指针法，获取两个确定的值去做对比，不相同就为false
     *
     * @param S
     * @param T
     * @return
     */
    public boolean backspaceCompare1(String S, String T) {
        int counts = 0, countt = 0;//获取S、T中#的个数
        int a = S.length() - 1, b = T.length() - 1;
        while (a >= 0 || b >= 0) {
            while (a >= 0) {
                if (S.charAt(a) == '#') {
                    counts++;
                    a--;//指针往前移一位
                } else if (counts > 0) {
                    a--;//指针往前移一位，相当于忽略一位
                    counts--;
                } else {
                    break;
                }
            }
            while (b >= 0) {
                if (T.charAt(b) == '#') {
                    countt++;
                    b--;
                } else if (countt > 0) {
                    b--;
                    countt--;
                } else {
                    break;
                }
            }
            if (a >= 0 && b >= 0) {
                if (S.charAt(a) != T.charAt(b)) {
                    return false;
                }
            } else {
                if (a >= 0 || b >= 0) {//字符串长度不相等
                    return false;
                }
            }
            a--;
            b--;
        }
        return true;
    }

    /**
     * 946. 验证栈序列
     * 给定 pushed 和 popped 两个序列，每个序列中的 值都不重复，只有当它们可能是在最初空栈上
     * 进行的推入 push 和弹出 pop 操作序列的结果时，返回 true；否则，返回 false 。
     *
     * @param pushed
     * @param popped
     * @return
     */
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        Stack<Integer> stack = new Stack<>();
        int j = 0;
        for (int ps : pushed) {
            stack.push(ps);
            while (!stack.isEmpty()
                    && j < popped.length
                    && stack.peek() == popped[j]
            ) {
                stack.pop();
                j++;
            }
        }
        return stack.isEmpty();
    }

    /**
     * 20. 有效的括号
     * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
     * <p>
     * 有效字符串需满足：
     * <p>
     * 左括号必须用相同类型的右括号闭合。
     * 左括号必须以正确的顺序闭合。
     *
     * @param s
     * @return
     */
    public boolean isValid(String s) {
        Stack stack = new Stack();
        HashMap map = new HashMap();
        map.put(')', '(');
        map.put(']', '[');
        map.put('}', '{');
        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case '(':
                case '[':
                case '{':
                    stack.push(s.charAt(i));
                    break;
                case ')':
                case ']':
                case '}':
                    if (stack.isEmpty()
                            && stack.peek() != map.get(s.charAt(i))
                    ) {
                        return false;
                    }
                    stack.pop();
                    break;
            }

        }
        return stack.isEmpty();
    }

    /**
     * 1021. 删除最外层的括号
     * 有效括号字符串为空 ("")、"(" + A + ")" 或 A + B，其中 A 和 B 都是有效的括号字符串，+ 代表字符串的连接。
     * 例如，""，"()"，"(())()" 和 "(()(()))" 都是有效的括号字符串。
     * <p>
     * 如果有效字符串 S 非空，且不存在将其拆分为 S = A+B 的方法，我们称其为原语（primitive），
     * 其中 A 和 B 都是非空有效括号字符串。
     * <p>
     * 给出一个非空有效字符串 S，考虑将其进行原语化分解，使得：S = P_1 + P_2 + ... + P_k，
     * 其中 P_i 是有效括号字符串原语。
     * <p>
     * 对 S 进行原语化分解，删除分解中每个原语字符串的最外层括号，返回 S 。
     *
     * @param S
     * @return
     */
    public String removeOuterParentheses(String S) {
        StringBuilder sb = new StringBuilder();
        int pre = 0, count = 0;
        for (int i = 0; i < S.length(); i++) {
            //遇到'('加一，当遇到')'减一，当count等于0时就形成一个闭环()
            if (S.charAt(i) == '(') {
                count++;
            } else {
                count--;
            }
            if (count != 0) {
                continue;
            }
            //pre+1时会去掉外面的(,
            sb.append(S.substring(pre + 1, i));
            pre = i + 1;
        }
        return sb.toString();
    }

    /**
     * 1249. 移除无效的括号
     * 给你一个由 '('、')' 和小写字母组成的字符串 s。
     * <p>
     * 你需要从字符串中删除最少数目的 '(' 或者 ')' （可以删除任意位置的括号)，使得剩下的「括号字符串」有效。
     * <p>
     * 请返回任意一个合法字符串。
     * <p>
     * 有效「括号字符串」应当符合以下 任意一条 要求：
     * <p>
     * 空字符串或只包含小写字母的字符串
     * 可以被写作 AB（A 连接 B）的字符串，其中 A 和 B 都是有效「括号字符串」
     * 可以被写作 (A) 的字符串，其中 A 是一个有效的「括号字符串」
     *
     * @param s
     * @return
     */
    public String minRemoveToMakeValid(String s) {
        char[] t = new char[s.length()];
        char[] ans = new char[s.length()];
        int tlen = 0;
        //先从左到右获取有效的右括号，
        for (int i = 0, cnt = 0; i < s.length(); i++) {
            if (s.charAt(i) != ')') {
                if (s.charAt(i) == '(') {
                    cnt++;
                }
                t[tlen++] = s.charAt(i);
            } else {
                if (cnt == 0) {
                    continue;
                }
                cnt--;
                t[tlen++] = ')';
            }
        }
        //从右到左获取有效的左括号
        int ansHead = tlen;
        for (int i = tlen - 1, cnt = 0; i >= 0; i--) {
            if (t[i] != '(') {
                if (t[i] == ')') {
                    cnt++;
                }
                ans[--ansHead] = t[i];
            } else {
                if (cnt == 0) {
                    continue;
                }
                cnt--;
                ans[--ansHead] = '(';
            }
        }
        return new String(ans).trim();
    }

    //1249解法2，使用栈
    public String minRemoveToMakeValid1(String s) {
        StringBuilder sb = new StringBuilder(s);
        Deque<Integer> dq = new LinkedList();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                dq.push(i);
            } else if (s.charAt(i) == ')') {
                if (!dq.isEmpty() && s.charAt(dq.peek()) == '(') {
                    dq.pop();
                } else {
                    dq.push(i);
                }
            }
        }
        for (Integer i : dq) {
            sb.deleteCharAt(i);
        }
        return sb.toString();
    }

    /**
     * 145. 二叉树的后序遍历
     * 给定一个二叉树，返回它的 后序 遍历。
     * <p>
     * 后序遍历 左右根
     * 中序遍历 左中右
     * 前序遍历 根左右
     *
     * @param root
     * @return
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> results = new ArrayList<>();
        Deque<TreeNode> stack = new LinkedList<>();
        //0、1、2；0表示遍历左节点，1表示遍历右节点，2表示根节点
        //同时记录遍历情况
        Deque<Integer> statueStack = new LinkedList<>();
        if (root == null) {
            return results;
        }
        stack.push(root);
        statueStack.push(0);
        while (!stack.isEmpty()) {
            switch (statueStack.pop()) {
                case 0:
                    statueStack.push(1);
                    if (stack.peek().left != null) {
                        stack.push(stack.peek().left);
                        statueStack.push(0);
                    }
                    break;
                case 1:
                    statueStack.push(2);
                    if (stack.peek().right != null) {
                        stack.push(stack.peek().right);
                        statueStack.push(0);
                    }
                    break;
                case 2:
                    results.add(stack.peek().val);
                    stack.pop();
            }
        }
        return results;
    }

    /**
     * 331. 验证二叉树的前序序列化
     * 序列化二叉树的一种方法是使用前序遍历。当我们遇到一个非空节点时，我们可以记录下这个节点的值。如果它是一个空节点，我们可以使用一个标记值记录，例如 #。
     * <p>
     * _9_
     * /   \
     * 3     2
     * / \   / \
     * 4   1  #  6
     * / \ / \   / \
     * # # # #   # #
     * 例如，上面的二叉树可以被序列化为字符串 "9,3,4,#,#,1,#,#,2,#,6,#,#"，其中 # 代表一个空节点。
     * <p>
     * 给定一串以逗号分隔的序列，验证它是否是正确的二叉树的前序序列化。编写一个在不重构树的条件下的可行算法。
     * <p>
     * 每个以逗号分隔的字符或为一个整数或为一个表示 null 指针的 '#' 。
     * <p>
     * 你可以认为输入格式总是有效的，例如它永远不会包含两个连续的逗号，比如 "1,,3" 。
     *
     * @param preorder
     * @return
     */
    public boolean isValidSerialization(String preorder) {
        /**
         * 消除法，例如4,#,#消除为#，当最后只剩一个#是就为真
         */
        String[] strings = preorder.split(",");
        List list = new ArrayList();
        int lastindex = 0;
        for (int i = 0; i < strings.length; i++) {
            list.add(strings[i]);
            lastindex = list.size() - 1;
            while (list.size() >= 3
                    && list.get(lastindex).equals("#")
                    && list.get(lastindex - 1).equals("#")
                    && !list.get(lastindex - 2).equals("#")) {
                list.set(lastindex - 2, "#");
                list.remove(lastindex);
                list.remove(lastindex - 1);
                lastindex = list.size() - 1;
            }
        }
        return list.size() == 1 && list.get(0).equals("#");
    }

    /**
     * 解法2
     *
     * @param preorder
     * @return
     */
    public boolean isValidSerialization1(String preorder) {
        int i = 0;//指针
        int slot = 1;//默认一个槽位
        int length = preorder.length();
        while (i < length) {
            if (slot == 0) return false;
            if (preorder.charAt(i) == '#') {//当遇到#时，槽位减一
                slot--;
                i++;
            } else if (preorder.charAt(i) == ',') {
                i++;
            } else {//遇到数字时，有可能是两位数或三位数，槽位加一
                while (i < length && preorder.charAt(i) != ',') {
                    i++;
                }
                slot++;
            }
        }
        return slot == 0;
    }

    /**
     * 227. 基本计算器 II
     * 给你一个字符串表达式 s ，请你实现一个基本计算器来计算并返回它的值。
     * <p>
     * 整数除法仅保留整数部分。
     *
     * @param s
     * @return
     */
    public int calculate(String s) {
        Deque<Integer> stack = new LinkedList();
        char presign = '+';
        int num = 0;
        int n = s.length();
        for (int i = 0; i < n; i++) {
            if (Character.isDigit(s.charAt(i))) {
                //将字符串‘153’转换为153
                num = num * 10 + s.charAt(i) - '0';
            }
            if ((!Character.isDigit(s.charAt(i)) && s.charAt(i) != ' ')
                    || i == n - 1) {
                switch (presign) {
                    case '+':
                        stack.push(num);
                        break;
                    case '-':
                        stack.push(-num);
                        break;
                    case '*':
                        stack.push(stack.pop() * num);
                        break;
                    case '/':
                        stack.push(stack.pop() / num);
                        break;
                }
                presign = s.charAt(i);
                num = 0;
            }
        }
        int result = 0;
        while (!stack.isEmpty()) {
            result += stack.pop();
        }
        return result;
    }

    /**
     * 636. 函数的独占时间
     * 给出一个非抢占单线程CPU的 n 个函数运行日志，找到函数的独占时间。
     * <p>
     * 每个函数都有一个唯一的 Id，从 0 到 n-1，函数可能会递归调用或者被其他函数调用。
     * <p>
     * 日志是具有以下格式的字符串：function_id：start_or_end：timestamp。例如："0:start:0" 表示函数 0 从 0 时刻开始运行。"0:end:0" 表示函数 0 在 0 时刻结束。
     * <p>
     * 函数的独占时间定义是在该方法中花费的时间，调用其他函数花费的时间不算该函数的独占时间。你需要根据函数的 Id 有序地返回每个函数的独占时间。
     * <p>
     * 示例 1:
     * <p>
     * 输入:
     * n = 2
     * logs =
     * ["0:start:0",
     * "1:start:2",
     * "1:end:5",
     * "0:end:6"]
     * 输出:[3, 4]
     * 说明：
     * 函数 0 在时刻 0 开始，在执行了  2个时间单位结束于时刻 1。
     * 现在函数 0 调用函数 1，函数 1 在时刻 2 开始，执行 4 个时间单位后结束于时刻 5。
     * 函数 0 再次在时刻 6 开始执行，并在时刻 6 结束运行，从而执行了 1 个时间单位。
     * 所以函数 0 总共的执行了 2 +1 =3 个时间单位，函数 1 总共执行了 4 个时间单位。
     */
    public int[] exclusiveTime(int n, List<String> logs) {
        //思路：封装一个类表示操作的对象
        //当遇到start时入栈，end时出栈，计算start和end的时间差；如果栈内还有数据，
        //则需要将中间执行的时间减去
        Deque<Task> stack = new LinkedList<>();
        int[] result = new int[n];
        for (String s : logs) {
            Task task = new Task(s.split(":"));
            if (task.isstart) {
                stack.push(task);
            } else {
                Task last = stack.pop();

                int time = task.time - last.time + 1;
                result[last.id] += time;
                if (!stack.isEmpty()) {
                    result[stack.peek().id] -= time;
                }
            }
        }
        return result;
    }

    class Task {
        int id;
        int time;
        boolean isstart;

        public Task(String[] s) {
            id = Integer.parseInt(s[0]);
            isstart = s[1].equals("start");
            time = Integer.parseInt(s[2]);
        }
    }

    /**
     * 1124. 表现良好的最长时间段
     * 给你一份工作时间表 hours，上面记录着某一位员工每天的工作小时数。
     * <p>
     * 我们认为当员工一天中的工作小时数大于 8 小时的时候，那么这一天就是「劳累的一天」。
     * <p>
     * 所谓「表现良好的时间段」，意味在这段时间内，「劳累的天数」是严格 大于「不劳累的天数」。
     * <p>
     * 请你返回「表现良好时间段」的最大长度。
     * <p>
     * <p>
     * <p>
     * 示例 1：
     * <p>
     * 输入：hours = [9,9,6,0,6,6,9]
     * 输出：3
     * 解释：最长的表现良好时间段是 [9,9,6]。
     *
     * @param hours
     * @return
     */
    public int longestWPI(int[] hours) {
        int sum = 0;
        int res = 0;
        HashMap<Integer, Integer> sumToIndex = new HashMap<>();
        for (int i = 0; i < hours.length; i++) {
            if (hours[i] > 8) {
                sum++;
            } else {
                sum--;
            }
            if (sum > 0) {
                res = i + 1;
            } else {
                if (!sumToIndex.containsKey(sum)) {
                    sumToIndex.put(sum, i);
                }
                //比较从前往后数还是从后往前数，当num开始减少时代表已经大于8，需要
                //找到最开始出现的节点
                if (sumToIndex.containsKey(sum - 1)) {
                    res = Math.max(res, i - sumToIndex.get(sum - 1));
                }
            }
        }
        return res;
    }

    public void coloregg() {
        BigDecimal count = BigDecimal.ZERO;
        String path = "E:\\微服务资料\\算法\\【周四】船长刷题直播课\\【第三周】递归与栈-0318\\【第三周】递归与栈-彩蛋数据.txt";
        File file = new File(path);
        try{
            InputStreamReader read = new InputStreamReader(
                new FileInputStream(file), "UTF-8");//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            Deque<Integer> stack = new LinkedList();
            int i = 1;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                String[] strings = lineTxt.split(" ");
                System.out.println(strings[0]);
                int num = 0;
                if (strings[0].equals("push")) {
                    System.out.println(strings[1]);
                    stack.push(Integer.parseInt(strings[1]));
                } else if (strings[0].equals("pop")) {
                    num = stack.pop();
                    num = num * i;
                    count = count.add(new BigDecimal(num));
                    i++;
                }

            }
            read.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(count.toString());
    }

    /**
     * 54. 螺旋矩阵
     * 给你一个 m 行 n 列的矩阵 matrix ，请按照 顺时针螺旋顺序 ，返回矩阵中的所有元素。
     *
     *
     *
     * 示例 1：
     *
     *
     * 输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
     * 输出：[1,2,3,6,9,8,7,4,5]
     * @param matrix
     * @return
     */
    public List<Integer> spiralOrder(int[][] matrix) {;
        //思路：按矩阵顺时针遍历，依次遍历四个边
        int m = matrix.length;//有多少行
        int n = matrix[0].length;//有多少列
        List<Integer> list = new ArrayList();
//        int i = 0;//纵坐标
//        int j = -1;//横坐标，默认指向开头前面一位，虚拟位置
        int k = 0;//遍历多少个元素
        int K = m*n;//总共有多少个元素
        int l = 0;//遍历了多少圈
        for (int i = 0,j = -1;k < K;l ++) {
            while (k < K && j + 1 <= n-1-l) {//遍历上边
                k += 1;
                j += 1;
                list.add(matrix[i][j]);
            }
            while (k < K && i + 1 <= m - 1 - l) {//遍历右边
                k += 1;
                i += 1;
                list.add(matrix[i][j]);
            }
            while (k < K && j -1 >= l) {//遍历下边
                k += 1;
                j -= 1;
                list.add(matrix[i][j]);
            }
            while (k < K && i - 1 > l) {//遍历左边
                k += 1;
                i -= 1;
                list.add(matrix[i][j]);
            }

        }
        return list;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
//        solution.calculate("0-2147483647");
//        int[] ints = {9, 9, 6, 0, 6, 6, 9, 9, 10, 11, 12, 6, 7, 8, 5, 4};
//        0,1,2,1,0,-1,-2,-1,0,1,2,3,2,1,0,-1,-2
//        solution.longestWPI(ints);
//        solution.coloregg();
        int[][] matrix = new int[][] {{1,2,3,4},{5,6,7,8},{9,10,11,12}};

        solution.spiralOrder(matrix);
    }

}

package Trie;

import java.util.*;

public class Solution {

    /**
     * 14. 最长公共前缀
     * 编写一个函数来查找字符串数组中的最长公共前缀。
     *
     * 如果不存在公共前缀，返回空字符串 ""。
     * @param strs
     * @return
     */
    public String longestCommonPrefix(String[] strs) {
        Trie trie = new Trie();
        for (String s : strs) {
            if (s.equals("")) {
                return "";
            }
            trie.insert(s);
        }
        String res = "";
        Node p = trie.root;
        while (p != null) {
            int idx = 0;
            boolean flag = false;
            //判断是否每个字符串都是以同一个字符开头
            for (int i = 0 ; i < 26 ;i ++) {
                if (p.next[i] == null) {
                    continue;
                }
                if (flag) {
                    return res;
                }
                idx = i;
                flag = true;
            }
            res += (char)(idx + 'a');
            p = p.next[idx];
            if (p.flag) {//如果独立成词，则前面是最长前缀
                return res;
            }
        }
        return res;
    }

    class Node {
        boolean flag = false;
        Node[] next = new Node[26];
    }

    class Trie {
        Node root = new Node();
        public boolean insert(String str) {
            Node p = root;
            char[] chars = str.toCharArray();
            for (char c : chars) {
                int idx = c - 'a';

                if (p.next[idx] == null) {
                    p.next[idx] = new Node();
                }
                p = p.next[idx];
            }
            if (p.flag) {
                return false;
            }
            p.flag = true;
            return true;
        }
    }

    class Node1 {
        boolean flag = false;
        Node1[] next = new Node1[26];
        //set保存到当前节点前缀的字符串
        TreeSet<String> set = new TreeSet();
    }

    Node1 root1 = new Node1();

    public void insert (String str) {
        Node1 p = root1;
        char[] chars = str.toCharArray();
        for (char c : chars) {
            int idx = c - 'a';
            if (p.next[idx] == null) {
                p.next[idx] = new Node1();
            }

            p = p.next[idx];
            p.set.add(str);
            if (p.set.size() > 3) {
                p.set.pollLast();
            }
        }
        p.flag = true;
    }

    /**
     * 1268. 搜索推荐系统
     * 给你一个产品数组 products 和一个字符串 searchWord ，products  数组中每个产品都是一个字符串。
     *
     * 请你设计一个推荐系统，在依次输入单词 searchWord 的每一个字母后，推荐 products 数组中前缀与 searchWord 相同的最多三个产品。
     * 如果前缀相同的可推荐产品超过三个，请按字典序返回最小的三个。
     *
     * 请你以二维列表的形式，返回在输入 searchWord 每个字母后相应的推荐产品的列表。
     * @param products
     * @param searchWord
     * @return
     */
    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        Node1 p = root1;
        for (String str : products) {
            insert(str);
        }

        List<List<String>> res = new ArrayList<>();
        char[] chars = searchWord.toCharArray();
        for (char c : chars) {
            //当前节点为空，插入空集合
            if (p == null) {
                res.add(new ArrayList<String>());
                continue;
            }
            List<String> list = new ArrayList<>();
            int idx = c - 'a';
            if (p.next[idx] != null) {
                TreeSet<String> set = p.next[idx].set;
                for (String s : set) {
                    list.add(s);
                }
            }
            p = p.next[idx];
            res.add(list);
        }

        return res;
    }

    /**
     * 421. 数组中两个数的最大异或值
     * 给你一个整数数组 nums ，返回 nums[i] XOR nums[j] 的最大运算结果，其中 0 ≤ i ≤ j < n 。
     *
     * 进阶：你可以在 O(n) 的时间解决这个问题吗？
     * @param nums
     * @return
     */
    public int findMaximumXOR(int[] nums) {
        //将nums放入一个每个节点有两条边的字典树，在查找的过程中
        //按照高位为1的原则 去得到异或的值
        for (int n : nums) {
            insert2(n);
        }

        int ans = 0;
        for (int n : nums) {
            ans = Math.max(ans,search2(n));
        }
        return ans;
    }

    class Node2 {
        boolean flag = false;
        Node2[] next = new Node2[2];
    }

    Node2 root2 = new Node2();

    public void insert2 (int n) {
        Node2 p = root2;

        //题意 num值小于 2的31次方
        for (int i = 30 ; i >= 0; i --) {
            //右移i 位 取得高位的值，& 1 确定是否为1
            int idx = n >> i & 1;
            if (p.next[idx] == null) {
                p.next[idx] = new Node2();
            }
            p = p.next[idx];
        }
        p.flag = true;
    }

    public int search2(int x) {
        Node2 p = root2;
        int ans = 0;
        for (int i = 30 ; i >= 0; i --) {
            int idx = x >> i & 1;
            //确保高位为1
            //如果异或1，对应边不为空，保证0边去找1边，保证当前位为1
            if (p.next[1 ^ idx] != null) {
                //将1 左移 i 位，
                ans |= (1 << i);
                p = p.next[1 ^ idx];
            } else {
                //没有则继续往下跳
                p = p.next[idx];
            }
        }
        return ans;
    }


    /**
     * 241. 为运算表达式设计优先级
     * 给定一个含有数字和运算符的字符串，为表达式添加括号，改变其运算优先级以求出不同的结果。
     * 你需要给出所有可能的组合的结果。有效的运算符号包含 +, - 以及 * 。
     * @param expression
     * @return
     */
    public List<Integer> diffWaysToCompute(String expression) {
        //以一个表达式符号作为根节点，形成表达式树
        List<Integer> res = new ArrayList<>();
        for (int i = 0 ; i < expression.length(); i ++) {
            char c = expression.charAt(i);
            if (c != '+' && c != '-' && c != '*') {
                continue;
            }
            //按运算符把表达式拆成左右两边
            String a = expression.substring(0,i);
            String b = expression.substring(i + 1, expression.length());
            //递归左右两边的表达式，求出值
            List<Integer> lista = diffWaysToCompute(a);
            List<Integer> listb = diffWaysToCompute(b);
            //左右两边的值排列组合
            for (int numa : lista) {
                for (int numb : listb) {
                    switch (c) {
                        case '+' :
                            res.add(numa + numb);
                            break;
                        case '-':
                            res.add(numa - numb);
                            break;
                        case '*':
                            res.add(numa * numb);
                            break;
                    }
                }
            }
        }
        //如果为空集合，则说明是纯数字
        if (res.size() == 0) {
            int num = 0;
            for (int i = 0; i < expression.length(); i ++) {
                num = num * 10 + (expression.charAt(i) - '0');
            }
            res.add(num);
        }
        return res;
    }

    /**
     * 133. 克隆图
     * 给你无向 连通 图中一个节点的引用，请你返回该图的 深拷贝（克隆）。
     *
     * 图中的每个节点都包含它的值 val（int） 和其邻居的列表（list[Node]）。
     *
     * class Node {
     *     public int val;
     *     public List<Node> neighbors;
     * }
     * @param node
     * @return
     */

//        Map<Node,Node> nodemap = new HashMap<>();
//        public Node cloneGraph(Node node) {
//            if (node == null) {
//                return null;
//            }
//            if (nodemap.containsKey(node)) {
//                return node;
//            }
//            Node newnode = new Node(node.val);
//            nodemap.put(node, newnode);
//
//            for (Node nd : node.neighbors) {
//                cloneGraph(nd);
//                nodemap.get(node).neighbors.add(nodemap.get(nd));
//            }
//
//            return nodemap.get(node);
//        }

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
    /**
     * 987. 二叉树的垂序遍历
     * 给你二叉树的根结点 root ，请你设计算法计算二叉树的 垂序遍历 序列。
     *
     * 对位于 (row, col) 的每个结点而言，其左右子结点分别位于 (row + 1, col - 1) 和 (row + 1, col + 1) 。树的根结点位于 (0, 0) 。
     *
     * 二叉树的 垂序遍历 从最左边的列开始直到最右边的列结束，按列索引每一列上的所有结点，
     * 形成一个按出现位置从上到下排序的有序列表。如果同行同列上有多个结点，则按结点的值从小到大进行排序。
     *
     * 返回二叉树的 垂序遍历 序列。
     * @param root
     * @return
     */

    //构造一个结构，第一个int存储纵坐标，第二个存储int[横坐标，值]
    TreeMap<Integer, List<int[]>> omap = new TreeMap<>();

    public void getResults(TreeNode root, int i , int j) {
        if (root == null) {
            return;
        }
        if (!omap.containsKey(j)) {
            omap.put(j, new ArrayList<>());
        }
        omap.get(j).add(new int[]{i , root.val});

        getResults(root.left, i + 1, j - 1);
        getResults(root.right, i + 1, j + 1);
    }

    public List<List<Integer>> verticalTraversal(TreeNode root) {
        //左边列坐标是（x + 1, y - 1）,右边坐标是 (x + 1, y + 1), 按纵坐标排序，纵坐标相等，
        // 按横坐标排序,横纵都等，按值大小排序
        getResults(root,0,0);
        List<List<Integer>> res = new ArrayList<>();

        for (Map.Entry<Integer, List<int[]>> entry : omap.entrySet()) {
            List<int[]> arr = entry.getValue();
            Collections.sort(arr, new Comparator<int[]>() {
                @Override
                public int compare(int[] o1, int[] o2) {
                    return o1[0] == o2[0] ? o1[1] - o2[1] : o1[0] - o2[0];
                }
            });

            List<Integer> list = new ArrayList<>();
            for (int[] ints : arr) {
                list.add(ints[1]);
            }
            res.add(list);
        }
        return res;
    }

    /**
     * 440. 字典序的第K小数字
     * 给定整数 n 和 k，找到 1 到 n 中字典序第 k 小的数字。
     *
     * 注意：1 ≤ k ≤ n ≤ 109。
     * @param n
     * @param k
     * @return
     */
    public int findKthNumber(int n, int k) {
        //字典序是从 1到9 排序，分为9个字典树，例如：
        //                1                         2              ........    9
        //           10,11.....19             20,21.....29
        //         100,101......199        200,201......299
        //以此类推，找第K个数就是按堆排除，直到找到第K个数
        if (k == 1) {
            return 1;
        }
        long i = 1;//从第一个节点开始
        while (k > 1) {
            //获取每一堆的数量
            long cnt = getCount(n, i);
            //当每一堆的数量小于K,说明K值在下一堆，k减去上一堆数量，当前堆根节点 + 1
            if (k > cnt) {
                k -= cnt;
                i ++;
            } else {
                //如果堆数量小于K，我们就往当前堆下一层找，下一层开始节点就是当前值 * 10，
                //对于字典树排序后来说就是 k - 1
                i *= 10;
                k --;
            }
        }

        return (int) i;

    }

    public long getCount(int n ,long cur) {
        long next = cur + 1, cnt = 0;
        while (cur <= n) {
            //cur为每一层第一个节点，每一层的数量如果是满的情况下，
            //等于下一个堆对应层第一个节点值减去当前堆的值
            //如果没满，说明已经到n，则为n 减去当前值 + 1
            cnt += Math.min(next - cur, n - cur + 1);
            cur *= 10;
            next *= 10;
        }
        return cnt;
    }

    /**
     * 611. 有效三角形的个数
     * 给定一个包含非负整数的数组，你的任务是统计其中可以组成三角形三条边的三元组个数
     * @param nums
     * @return
     */
    public int triangleNumber(int[] nums) {
        //采用二分法
        int n = nums.length;
        //将数组从小到大排序
        Arrays.sort(nums);
        int ans = 0;
        //我们遍历两条较短边，根据公式 a + b > c ,确定两条短边后，
        //我们需要的值在 j 到 n - 1之间，采用二分法去找到 a + b的点
        //边界确定，i 至少要留两个值，j留一个值
        for (int i = 0; i < n - 2; i ++) {
            for (int j = i + 1; j < n - 1; j ++) {
                int t = nums[i] + nums[j];
                int l = j + 1, r = n - 1, mid = 0;
                while (l < r) {
                    mid = (l + r + 1) / 2;
                    if (nums[mid] < t) {
                        l = mid + 1;
                    } else {
                        r = mid;
                    }
                }

                if (nums[r] < t) {
                    ans += (r - j);
                }
            }
        }
        return ans;
    }

    public int triangleNumber1(int[] nums) {
        int n = nums.length;
        //将数组从小到大排序
        Arrays.sort(nums);
        int ans = 0;
        //固定长边，往前面去固定两条短边
        for (int i = n - 1; i > 1; i --) {
            int l = 0 , r = i - 1;
            while (l < r) {
                //如果满足 a + b > c ，说明 l，r之间的数都符合条件，r往前挪
                if (nums[i] < nums[l] + nums[r]) {
                    ans += r - l;
                    r --;
                } else {
                    //l往后挪
                    l ++;
                }
            }
        }
        return ans;
    }

    /**
     * 696. 计数二进制子串
     * 给定一个字符串 s，计算具有相同数量 0 和 1 的非空（连续）子字符串的数量，并且这些子字符串中的所有 0 和所有 1 都是连续的。
     *
     * 重复出现的子串要计算它们出现的次数。
     * @param s
     * @return
     */
    public int countBinarySubstrings(String s) {
        int n = s.length();
        //扫描一遍，cur代表相同的字符有多少个，当不同时，将cur值重新计数，
        //pre代表需要组合的个数，cur重新计数后就和之前的组成组合
        int pre = 0,cur = 1, res = 0;
        for (int i = 0; i < n - 1 ; i ++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                cur ++;
            } else {
                pre = cur;
                cur = 1;
            }
            if (pre >= cur) {
                res ++;
            }
        }
        return res;
    }

    /**
     * 686. 重复叠加字符串匹配
     * 给定两个字符串 a 和 b，寻找重复叠加字符串 a 的最小次数，使得字符串 b 成为叠加后的字符串 a 的子串，如果不存在则返回 -1。
     *
     * 注意：字符串 "abc" 重复叠加 0 次是 ""，重复叠加 1 次是 "abc"，重复叠加 2 次是 "abcabc"。
     * @param a
     * @param b
     * @return
     */
    public int repeatedStringMatch(String a, String b) {
        int len_a = a.length();
        int len_b = b.length();
        //如果要匹配上，如果在中间匹配上，匹配之前的字符串和匹配之后的字符串能组合成一个a串，
        //要么通过a重复后，直接就能匹配上；如果len_a > len_b, a最多重复2次就能匹配上；
        //如果len_a < len_b ,则最多需要len_b + 2len_a匹配上
        StringBuilder sb = new StringBuilder();
        int cnt = 0;
        while (sb.length() <= len_b + 2 * len_a ) {
            if (sb.indexOf(b) != -1) {
                return cnt;
            }

            sb.append(a);
            cnt ++;
        }
        return -1;
    }

    /**
     * 820. 单词的压缩编码
     * 单词数组 words 的 有效编码 由任意助记字符串 s 和下标数组 indices 组成，且满足：
     *
     * words.length == indices.length
     * 助记字符串 s 以 '#' 字符结尾
     * 对于每个下标 indices[i] ，s 的一个从 indices[i] 开始、到下一个 '#'字符结束（但不包括 '#'）的 子字符串 恰好与 words[i] 相等
     * 给你一个单词数组 words ，返回成功对 words 进行编码的最小助记字符串 s 的长度 。
     * @param words
     * @return
     */
    class Node3 {
        boolean flag = false;
        Node3[] next = new Node3[26];
    }

    Node3 root3 = new Node3();

    public int insert3(String word) {
        Node3 p = root3;
        String str = new StringBuffer(word).reverse().toString();
        boolean flag = false;
        for (char c : str.toCharArray()) {
            int idx = c - 'a';
            if (p.next[idx] == null) {
                p.next[idx] = new Node3();
                flag = true;//说明分叉了，不是前面的后缀
            }
            p = p.next[idx];
        }
        p.flag = true;
        return flag ? word.length() + 1 : 0;
    }

    public int minimumLengthEncoding(String[] words) {
        Arrays.sort(words, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.length() - o1.length();
            }
        });
        //求后一个单词是不是前面的单词的后缀,我们构建一个字典树，把字符串反向插入
        int cnt = 0;
        for (String word : words) {
            cnt += insert3(word);
        }
        return cnt;
    }

    /**
     * 409. 最长回文串
     * 给定一个包含大写字母和小写字母的字符串，找到通过这些字母构造成的最长的回文串。
     *
     * 在构造过程中，请注意区分大小写。比如 "Aa" 不能当做一个回文字符串。
     *
     * 注意:
     * 假设字符串的长度不会超过 1010。
     * @param s
     * @return
     */
    public int longestPalindrome(String s) {
        //如何组合成一个回文串，先统计每个字符出现的次数，
        //组成回文串，我们要么需要字符都是偶数个，要么都是偶数的字符 加上一个字符放中间
        int[] cnt = new int[128];
        for (char c : s.toCharArray()) {
            cnt[c]++;
        }

        int ans = 0;
        for (int cn : cnt) {
            ans += (cn % 2 == 0 ? cn : cn - 1);
        }
        return ans == s.length() ? ans : ans + 1;
    }

    /**
     * 647. 回文子串
     * 给你一个字符串 s ，请你统计并返回这个字符串中 回文子串 的数目。
     *
     * 回文字符串 是正着读和倒过来读一样的字符串。
     *
     * 子字符串 是字符串中的由连续字符组成的一个序列。
     *
     * 具有不同开始位置或结束位置的子串，即使是由相同的字符组成，也会被视作不同的子串。
     * @param args
     */
    public int countSubstrings(String s) {
        //马拉车算法
        String str = getNewStr(s);
        int n = str.length();
        int l = 0, r = -1;
        int cnt = 0;
        //存指定位置半径
        int[] d = new int[n];
        for (int i = 0; i < n ; i ++) {
            //超过最大回文串边界，半径为1
            if (i > r) {
                d[i] = 1;
            } else {
                //没超过说明，使用马拉车算法加速
                d[i] = Math.min(r - i, d[r - i + l]);
            }
            //半径扩展
            while (i - d[i] >= 0 && i + d[i] < n && str.charAt(i - d[i]) == str.charAt(i + d[i])) {
                d[i] ++;
            }
            //重新确定边界
            if (i - d[i] > 0 && i + d[i] > r) {
                l = i - d[i];
                r = i + d[i];
            }
            cnt += d[i] / 2;
        }
        return cnt;
    }

    public String getNewStr(String s) {
        StringBuilder sb = new StringBuilder("#");
        for (char c : s.toCharArray()) {
            sb.append(c).append("#");
        }
        return sb.toString();
    }

    /**
     * 205. 同构字符串
     * 给定两个字符串 s 和 t，判断它们是否是同构的。
     *
     * 如果 s 中的字符可以按某种映射关系替换得到 t ，那么这两个字符串是同构的。
     *
     * 每个出现的字符都应当映射到另一个字符，同时不改变字符的顺序。不同字符不能映射到同一个字符上，
     * 相同字符只能映射到同一个字符上，字符可以映射到自己本身
     * @param s
     * @param t
     * @return
     */
    public boolean isIsomorphic(String s, String t) {
        int n = s.length(), m = t.length();
        if (n != m) {
            return false;
        }
        //"paper"
        //"title"
        Map<Character,Character> s_map = new HashMap<>();
        Map<Character,Character> t_map = new HashMap<>();
        for (int i = 0 ; i < n; i ++) {
            char s1 = s.charAt(i) ,t1 = t.charAt(i);
            if ((s_map.containsKey(s1) && s_map.get(s1) != t1) ||
                    (t_map.containsKey(t1) && t_map.get(t1) != s1)) {
                return false;
            }

            s_map.put(s1, t1);
            t_map.put(t1, s1);
        }
        return true;
    }

    /**
     * 211. 添加与搜索单词 - 数据结构设计
     * 请你设计一个数据结构，支持 添加新单词 和 查找字符串是否与任何先前添加的字符串匹配 。
     *
     * 实现词典类 WordDictionary ：
     *
     * WordDictionary() 初始化词典对象
     * void addWord(word) 将 word 添加到数据结构中，之后可以对它进行匹配
     * bool search(word) 如果数据结构中存在字符串与 word 匹配，则返回 true ；否则，返回  false 。
     * word 中可能包含一些 '.' ，每个 . 都可以表示任何一个字母。
     */
    class WordDictionary {

        class Node {
            boolean flag = false;
            Node[] next = new Node[26];
        }

        Node root ;

        public WordDictionary() {
            root = new Node();
        }

        public void addWord(String word) {
            Node p = root;
            for (char c : word.toCharArray()) {
                int idx = c - 'a';
                if (p.next[idx] == null) {
                    p.next[idx] = new Node();
                }
                p = p.next[idx];
            }
            p.flag = true;
        }

        public boolean search(String word) {
            Node p = root;
            return __search(word, 0, p);
        }

        public boolean __search(String word, int idx, Node node) {
            if (idx == word.length()) {
                return node.flag;
            }
            char c = word.charAt(idx);
            if (c == '.') {
                for (int i = 0 ; i < 26; i ++) {
                    if (node.next[i] != null && __search(word,idx + 1, node.next[i])) {
                        return true;
                    }
                }

            } else {
                int i = c - 'a';
                if (node.next[i] == null) {
                    return false;
                }
                return __search(word,idx + 1, node.next[i]);
            }
            return false;
        }
    }

    /**
     * 745. 前缀和后缀搜索
     * 设计一个包含一些单词的特殊词典，并能够通过前缀和后缀来检索单词。
     *
     * 实现 WordFilter 类：
     *
     * WordFilter(string[] words) 使用词典中的单词 words 初始化对象。
     * f(string prefix, string suffix) 返回词典中具有前缀 prefix 和后缀suffix 的单词的下标。
     * 如果存在不止一个满足要求的下标，返回其中 最大的下标 。如果不存在这样的单词，返回 -1 。
     */
    static class WordFilter {
        class Node {
            Node[] next = new Node[27];
            int val = 0;
        }

        Node root;

        public void insert(String word ,int start,int val) {
            Node p = root;
            //插入后缀 + 前缀的所有情况
            for (int i = start; i < word.length(); i++) {
                int idx = word.charAt(i) - 'a';
                if (p.next[idx] == null) {
                    p.next[idx] = new Node();
                }
                p = p.next[idx];
                p.val = Math.max(p.val,val);
            }

        }

        public int search(String word) {
            Node p = root;
            for (char c : word.toCharArray()) {
                int idx = c - 'a';
                if (p.next[idx] == null) {
                    return -1;
                }
                p = p.next[idx];
            }
            return p.val;
        }

        public WordFilter(String[] words) {
            root = new Node();
            int n = words.length;
            //将字符串拼接成 后缀 + 前缀的方式
            for (int i = 0;i < n; i ++) {
                String s = words[i] + "{" + words[i];
                int len = words[i].length();
                //依次遍历去插入所有的后缀 + 前缀的所有情况
                for (int j = 0; j <= len; j ++) {
                    insert(s,j, i);
                }
            }
        }

        public int f(String prefix, String suffix) {
            return search( suffix + "{" + prefix);
        }
    }

    /**
     *  1161. 最大层内元素和
     * 给你一个二叉树的根节点 root。设根节点位于二叉树的第 1 层，而根节点的子节点位于第 2 层，依此类推。
     *
     * 请你找出层内元素之和 最大 的那几层（可能只有一层）的层号，并返回其中 最小 的那个。
     * @param root
     * @return
     */
    public int maxLevelSum(TreeNode root) {
        //广搜，逐层去扫描
        Queue<TreeNode> queue = new ArrayDeque<>();
        int depth = 0;
        int max = root.val;
        int res = 1;
        queue.offer(root);
        while (!queue.isEmpty()) {
            int tmp = 0;
            int n = queue.size();
            for (int i = 0; i < n;i ++) {
                TreeNode treeNode = queue.poll();
                tmp += treeNode.val;
                if (treeNode.left != null) {
                    queue.offer(treeNode.left);
                }
                if (treeNode.right != null) {
                    queue.offer(treeNode.right);
                }
            }
            depth ++;
            if (tmp > max) {
                max = tmp;
                res = depth;
            }
        }
        return res;
    }

    /**
     * 572. 另一棵树的子树
     * 给你两棵二叉树 root 和 subRoot 。检验 root 中是否包含和 subRoot 具有相同结构和节点值的子树。
     * 如果存在，返回 true ；否则，返回 false 。
     *
     * 二叉树 tree 的一棵子树包括 tree 的某个节点和这个节点的所有后代节点。tree 也可以看做它自身的一棵子树。
     *
     * root 树上的节点数量范围是 [1, 2000]
     * subRoot 树上的节点数量范围是 [1, 1000]
     * -10^4 <= root.val <= 10^4
     * -10^4 <= subRoot.val <= 10^4
     *
     * @param root
     * @param subRoot
     * @return
     */
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        List<Integer> rsb = new ArrayList<>();
        getPreOrder(root,rsb);
        List<Integer> ssb = new ArrayList<>();
        getPreOrder(subRoot, ssb);
        return kmp(rsb , ssb);
    }

    public void getPreOrder(TreeNode root, List<Integer> sb) {
        if (root == null) {
            sb.add(10001);
            return;
        }
        sb.add(root.val);
        getPreOrder(root.left,sb);
        getPreOrder(root.right,sb);
    }

    public boolean kmp (List<Integer> rsb, List<Integer> ssb) {
        int n = rsb.size();
        int m = ssb.size();
        int[] next = new int[m];
        getNext(ssb, next);

        for (int i = 0 , j = -1; i < n ; i ++) {
            while (j != -1 && !rsb.get(i).equals(ssb.get(j + 1)) ) {
                j = next[j];
            }
            if (rsb.get(i).equals(ssb.get(j + 1))) {
                j ++;
            }
            if (j + 1 == m) {
                return true;
            }
        }
        return false;
    }

    public void getNext(List<Integer> ssb, int[] next) {
        int n = ssb.size();
        next[0] = -1;
        for (int i = 0, j = -1; i < n; i ++) {
            while (j != -1 && ssb.get(i) != ssb.get(j + 1)) {
                j = next[j];
            }
            if (ssb.get(i) == ssb.get(j + 1)) {
                j ++;
            }
            next[i] = j;
        }
    }

    public static void main(String[] args) {
        System.out.println('{' - 'a');
        Solution solution = new Solution();
        WordFilter wordFilter = new WordFilter(new String[]{"apple"});
        System.out.println(wordFilter.f("a","e"));

    }
}

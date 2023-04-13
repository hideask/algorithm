package unionfindset;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.*;

/**
 * description:Solution
 * create user: songj
 * date : 2021/5/5 20:49
 */
public class Solution {

    /**
     * 128. 最长连续序列
     * 给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
     * 进阶：你可以设计并实现时间复杂度为 O(n) 的解决方案吗？
     * @param nums
     * @return
     */
    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        //将数字放到set集合中去
        for (int num : nums) {
            set.add(num);
        }

        int ans = 0;
        for (int num : set) {
            //是最小的数字，起始点
            if (!set.contains(num - 1)) {
                int numtemp = num;
                int numlongstr = 1;
                //循环获取下一个数字并计算长度
                while (set.contains(numtemp + 1)) {
                    numtemp = numtemp + 1;
                    numlongstr ++;
                }
                ans = Math.max(ans,numlongstr);
            }
        }
        return ans;
    }

    /**
     * 947. 移除最多的同行或同列石头
     * n 块石头放置在二维平面中的一些整数坐标点上。每个坐标点上最多只能有一块石头。
     *
     * 如果一块石头的 同行或者同列 上有其他石头存在，那么就可以移除这块石头。
     *
     * 给你一个长度为 n 的数组 stones ，其中 stones[i] = [xi, yi] 表示第 i 块石头的位置，
     * 返回 可以移除的石子 的最大数量。
     * 示例 1：
     *
     * 输入：stones = [[0,0],[0,1],[1,0],[1,2],[2,1],[2,2]]
     * 输出：5
     * 解释：一种移除 5 块石头的方法如下所示：
     * 1. 移除石头 [2,2] ，因为它和 [2,1] 同行。
     * 2. 移除石头 [2,1] ，因为它和 [0,1] 同列。
     * 3. 移除石头 [1,2] ，因为它和 [1,0] 同行。
     * 4. 移除石头 [1,0] ，因为它和 [0,0] 同列。
     * 5. 移除石头 [0,1] ，因为它和 [0,0] 同行。
     * 石头 [0,0] 不能移除，因为它没有与另一块石头同行/列。
     * @param stones
     * @return
     */
    public int removeStones(int[][] stones) {
        int len = stones.length;
        UnionFind unionFind = new UnionFind(len);
        for (int i = 0;i < len; i ++) {
            for (int j = i + 1;j < len; j ++) {
                int x1 = stones[i][0];//取第一个石头x坐标
                int y1 = stones[i][1];//取第一个石头y坐标
                int x2 = stones[j][0];//取第二个石头x坐标
                int y2 = stones[j][1];//取第二个石头y坐标
                if (x1 == x2 || y1 == y2) {//如果相等则联通
                    unionFind.union(i,j);
                }
            }
        }
        return len - unionFind.getSetcount();
    }

    /**
     * 721. 账户合并
     * 给定一个列表 accounts，每个元素 accounts[i] 是一个字符串列表，其中第一个元素 accounts[i][0] 是 名称 (name)，
     * 其余元素是 emails 表示该账户的邮箱地址。
     *
     * 现在，我们想合并这些账户。如果两个账户都有一些共同的邮箱地址，则两个账户必定属于同一个人。请注意，
     * 即使两个账户具有相同的名称，它们也可能属于不同的人，因为人们可能具有相同的名称。一个人最初可以拥有任意数量的账户，
     * 但其所有账户都具有相同的名称。
     *
     * 合并账户后，按以下格式返回账户：每个账户的第一个元素是名称，其余元素是按字符 ASCII 顺序排列的邮箱地址。
     * 账户本身可以以任意顺序返回。
     * @param accounts
     * @return
     */
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        int len = accounts.size();

        //建立email和name的对应关系
        Map<String,String> emailToName = new HashMap<>();
        //建立email和编号的对应关系
        Map<String,Integer> emailToIndex = new HashMap<>();
        int emailcount = 0;
        for (List<String> acc: accounts) {
            String name = acc.get(0);
            int size = acc.size();
            for (int i = 1;i < size; i ++) {
                String email = acc.get(i);
                if (!emailToName.containsKey(email)) {
                    emailToName.put(email,name);
                    emailToIndex.put(email,emailcount++);
                }
            }
        }
        //使用并查集将同一个账户的email联通
        UnionFind unionFind = new UnionFind(emailcount);
        for (List<String> acc: accounts) {
            String firstemail = acc.get(1);
            int size = acc.size();
            for (int i = 2; i < size; i++) {
                String nextemail = acc.get(i);
                unionFind.union(emailToIndex.get(firstemail),emailToIndex.get(nextemail));
            }
        }

        //按属于一个集合的email按index存储
        Map<Integer,List<String>> indexToEmail = new HashMap<>();
        for (String email : emailToIndex.keySet()) {
            int index = unionFind.find(emailToIndex.get(email));
            List<String> emails = indexToEmail.getOrDefault(index,new ArrayList<>());
            emails.add(email);
            indexToEmail.put(index,emails);
        }
        List<List<String>> res = new ArrayList<>();
        //拼接返回值
        for (List<String> emails : indexToEmail.values()) {
            Collections.sort(emails);
            List<String> ans = new ArrayList<>();
            ans.add(emailToName.get(emails.get(0)));
            ans.addAll(emails);
            res.add(ans);
        }
        return res;
    }

    /**
     * 547. 省份数量
     * 有 n 个城市，其中一些彼此相连，另一些没有相连。如果城市 a 与城市 b 直接相连，且城市 b 与城市 c 直接相连，
     * 那么城市 a 与城市 c 间接相连。
     *
     * 省份 是一组直接或间接相连的城市，组内不含其他没有相连的城市。
     *
     * 给你一个 n x n 的矩阵 isConnected ，其中 isConnected[i][j] = 1 表示第 i 个城市和第 j 个城市直接相连，
     * 而 isConnected[i][j] = 0 表示二者不直接相连。
     *
     * 返回矩阵中 省份 的数量。
     * @param isConnected
     * @return
     */
    public int findCircleNum(int[][] isConnected) {
        int size = isConnected.length;
        UnionFind uf = new UnionFind(size);
        for (int i = 0; i < size; i ++) {
            for (int j = 0; j < i; j++) {//上下两种都可以
//                for (int j = 0; j < isConnected[i].length; j++) {
                if (isConnected[i][j] == 1) {
                    uf.union(i,j);
                }
            }
        }
//        int ans = 0;
//        for (int i = 0; i < size; i++) {
//            if (uf.find(i) == i) {
//                ans ++;
//            }
//        }
//        return ans;
        return uf.getSetcount();
    }

    /**
     * 200. 岛屿数量
     * 给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
     *
     * 岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
     *
     * 此外，你可以假设该网格的四条边均被水包围。
     * @param grid
     * @return
     */
    public int numIslands(char[][] grid) {
        int n = grid.length,m = grid[0].length,num = 0;
        for (int i = 0;i < n ; i++) {
            for (int j = 0;j < m ; j++) {
                if (grid[i][j] == '1') {
                    num ++;
                }
            }
        }
        UnionFind uf = new UnionFind(n * m, num);
        for (int i = 0 ; i < n ; i ++) {
            for (int k = 0; k < m; k ++) {
                if (grid[i][k] == '1') {
                    if (i - 1>= 0 && grid[i - 1][k] == '1') {
                        uf.union(i * m + k,(i -1) * m +k);
                    }
                    if (i + 1< n && grid[i + 1][k] == '1') {
                        uf.union(i * m + k,(i + 1) * m +k);
                    }
                    if (k - 1 >= 0 && grid[i][k - 1] == '1') {
                        uf.union(i * m + k, i * m + k - 1);
                    }
                    if (k + 1 < m && grid[i][k + 1] == '1') {
                        uf.union(i * m + k, i * m + k + 1);
                    }
                }

            }
        }
        return uf.num;
    }

    /**
     * 990. 等式方程的可满足性
     * 给定一个由表示变量之间关系的字符串方程组成的数组，每个字符串方程 equations[i] 的长度为 4，
     * 并采用两种不同的形式之一："a==b" 或 "a!=b"。在这里，a 和 b 是小写字母（不一定不同），表示单字母变量名。
     *
     * 只有当可以将整数分配给变量名，以便满足所有给定的方程时才返回 true，否则返回 false。
     * @param equations
     * @return
     */
    public boolean equationsPossible(String[] equations) {
        UnionFind uf = new UnionFind(26);
        for (String str : equations) {
            if (str.charAt(1) == '=') {
                int a = str.charAt(0) - 'a';
                int b = str.charAt(3) - 'a';
                uf.union(a,b);
            }
        }

        for (String str : equations) {
            if (str.charAt(1) == '!') {
                int a = str.charAt(0) - 'a';
                int b = str.charAt(3) - 'a';
                if (uf.find(a) == uf.find(b)) {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * 684. 冗余连接
     * 在本问题中, 树指的是一个连通且无环的无向图。
     *
     * 输入一个图，该图由一个有着N个节点 (节点值不重复1, 2, ..., N) 的树及一条附加的边构成。
     * 附加的边的两个顶点包含在1到N中间，这条附加的边不属于树中已存在的边。
     *
     * 结果图是一个以边组成的二维数组。每一个边的元素是一对[u, v] ，满足 u < v，表示连接顶点u 和v的无向图的边。
     *
     * 返回一条可以删去的边，使得结果图是一个有着N个节点的树。如果有多个答案，则返回二维数组中最后出现的边。
     * 答案边 [u, v] 应满足相同的格式 u < v。
     * 示例 1：
     *
     * 输入: [[1,2], [1,3], [2,3]]
     * 输出: [2,3]
     * 解释: 给定的无向图为:
     *   1
     *  / \
     * 2 - 3
     * 示例 2：
     *
     * 输入: [[1,2], [2,3], [3,4], [1,4], [1,5]]
     * 输出: [1,4]
     * 解释: 给定的无向图为:
     * 5 - 1 - 2
     *     |   |
     *     4 - 3
     * @param edges
     * @return
     */
    public int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;
        //需要修改并查集初始化大小为n + 1,
        UnionFind uf = new UnionFind(n);
        for (int[] deg : edges) {
            if (uf.find(deg[0]) == uf.find(deg[1])) {
                return deg;
            }
            uf.union(deg[0],deg[1]);
        }
        return null;
    }

    /**
     * 1319. 连通网络的操作次数
     * 用以太网线缆将 n 台计算机连接成一个网络，计算机的编号从 0 到 n-1。线缆用 connections 表示，
     * 其中 connections[i] = [a, b] 连接了计算机 a 和 b。
     *
     * 网络中的任何一台计算机都可以通过网络直接或者间接访问同一个网络中其他任意一台计算机。
     *
     * 给你这个计算机网络的初始布线 connections，你可以拔开任意两台直连计算机之间的线缆，
     * 并用它连接一对未直连的计算机。请你计算并返回使所有计算机都连通所需的最少操作次数。如果不可能，则返回 -1 。
     * 示例 1：
     * 输入：n = 4, connections = [[0,1],[0,2],[1,2]]
     * 输出：1
     * 解释：拔下计算机 1 和 2 之间的线缆，并将它插到计算机 1 和 3 上。
     * @param n
     * @param connections
     * @return
     */
    public int makeConnected(int n, int[][] connections) {
        //线的数量为电脑数减一，如果电脑数量大于后者，这不可能满足
        if (n - 1 > connections.length) {
            return -1;
        }

        //并查集初始化
        UnionFind unionFind = new UnionFind(n);
        for (int[] line:connections) {
            int a = line[0];
            int b = line[1];
            unionFind.union(a,b);
        }
        //集合数减一，除去已经联通的一个
        return unionFind.getSetcount() - 1;
    }

    /**
     * 1202. 交换字符串中的元素
     * 给你一个字符串 s，以及该字符串中的一些「索引对」数组 pairs，其中 pairs[i] = [a, b]
     * 表示字符串中的两个索引（编号从 0 开始）。
     * 你可以 任意多次交换 在 pairs 中任意一对索引处的字符。
     * 返回在经过若干次交换后，s 可以变成的按字典序最小的字符串。
     * 示例 1:
     *
     * 输入：s = "dcab", pairs = [[0,3],[1,2]]
     * 输出："bacd"
     * 解释：
     * 交换 s[0] 和 s[3], s = "bcad"
     * 交换 s[1] 和 s[2], s = "bacd"
     * 示例 2：
     *
     * 输入：s = "dcab", pairs = [[0,3],[1,2],[0,2]]
     * 输出："abcd"
     * 解释：
     * 交换 s[0] 和 s[3], s = "bcad"
     * 交换 s[0] 和 s[2], s = "acbd"
     * 交换 s[1] 和 s[2], s = "abcd"
     * 示例 3：
     *
     * 输入：s = "cba", pairs = [[0,1],[1,2]]
     * 输出："abc"
     * 解释：
     * 交换 s[0] 和 s[1], s = "bca"
     * 交换 s[1] 和 s[2], s = "bac"
     * 交换 s[0] 和 s[1], s = "abc"
     * @param s
     * @param pairs
     * @return
     */
    public String smallestStringWithSwaps(String s, List<List<Integer>> pairs) {
        int length = s.length();
        UnionFind unionFind = new UnionFind(length);
        for (List<Integer> list: pairs) {
            int a = list.get(0);
            int b = list.get(1);
            unionFind.union(a,b);
        }
        char[] chars = s.toCharArray();
        Map<Integer,PriorityQueue<Character>> map= new HashMap<>(length);
        for (int i = 0; i < length ;i ++) {
            int index = unionFind.find(i);
            //computeIfAbsent() 方法对 hashMap 中指定 key 的值进行重新计算，如果不存在这个 key，则添加到 hasMap 中。
            map.computeIfAbsent(index, key -> new PriorityQueue<>()).offer(chars[i]);
            //computeIfPresent方法对 hashMap 中指定 key 的值进行重新计算，并返回，如果不存在返回null
            //map.computeIfPresent();
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = unionFind.find(i);
            sb.append(map.get(index).poll());
        }
        return sb.toString();
    }

    /**
     * 765. 情侣牵手
     * N 对情侣坐在连续排列的 2N 个座位上，想要牵到对方的手。 计算最少交换座位的次数，以便每对情侣可以并肩坐在一起。
     * 一次交换可选择任意两人，让他们站起来交换座位。
     *
     * 人和座位用 0 到 2N-1 的整数表示，情侣们按顺序编号，第一对是 (0, 1)，第二对是 (2, 3)，以此类推，最后一对是 (2N-2, 2N-1)。
     *
     * 这些情侣的初始座位  row[i] 是由最初始坐在第 i 个座位上的人决定的。
     *
     * 示例 1:
     *
     * 输入: row = [0, 2, 1, 3]
     * 输出: 1
     * 解释: 我们只需要交换row[1]和row[2]的位置即可。
     * @param row
     * @return
     */
    public int minSwapsCouples(int[] row) {
        int len = row.length;
        int n = len / 2;
        UnionFind unionFind = new UnionFind(n);
        for (int i = 0; i < len; i += 2) {
            unionFind.union(row[i]/2,row[i+1]/2);
        }

        return n - unionFind.getSetcount();

    }

    /**
     * oj 214. 朋友圈
     * 题目描述
     * ​ 所谓一个朋友圈子，不一定其中的人都互相直接认识。
     *
     * ​ 例如：小张的朋友是小李，小李的朋友是小王，那么他们三个人属于一个朋友圈。
     *
     * ​ 现在给出一些人的朋友关系，人按照从  到  编号在这中间会进行询问某两个人是否属于一个朋友圈，
     * 请你编写程序，实现这个过程。
     */
    public void friendCycle() throws IOException {
        int m, n ;
        Scanner sc = new Scanner(System.in);
        n = Integer.valueOf(sc.next());
        m = Integer.valueOf(sc.next());
        UnionFind unionFind = new UnionFind(n+1);
        for (int i = 0 ; i < m; i ++) {
            Integer a = sc.nextInt(),
                b = sc.nextInt(),
                c = sc.nextInt();
            if (a == 1) {
                unionFind.union(b,c);
            } else {
                if (unionFind.find(b) == unionFind.find(c)) {
                    System.out.println("Yes");
                } else {
                    System.out.println("No");
                }
            }
        }
        sc.close();
    }

    public static void main(String[] args) {
//        int[][] nums = new int[][]{{0,0},{0,1},{1,0},{1,2},{2,1},{2,2}};
//        System.out.println(nums.length);
//        System.out.println(nums[2][0]);
//        System.out.println(nums[2][1]);
//        Solution solution = new Solution();
//        try {
//            solution.friendCycle();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}

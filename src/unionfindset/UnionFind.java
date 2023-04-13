package unionfindset;

import java.util.Arrays;

/**
 * 并查集
 * description:UnionFind
 * create user: songj
 * date : 2021/5/5 21:55
 */
public class UnionFind {
    int[] parent ;
    int[] rank ;
    int setcount;
    int num;

    public UnionFind (int n) {
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        rank = new int[n];
        Arrays.fill(rank,1);
        setcount = n;
    }

    public UnionFind (int n,int m) {
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        rank = new int[n];
        Arrays.fill(rank,1);
        num = m;
        setcount = n;
    }

    public int find (int index) {
        //如果节点值是他自己，则找到根节点
        //路径压缩将最终查找到的根节点作为自己的父节点
        if (parent[index] != index) {
            parent[index] = find(parent[index]);
        }
        return parent[index];
    }

    public void union(int index1, int index2) {
        int root1 = find(index1);
        int root2 = find(index2);
        if (root1 != root2) {
            if (rank[root1] < rank[root2]) {
                swap(root1, root2);
            }
            //将root2的父节点设为root1，则将两个集合合并
            parent[root2] = root1;
            rank[root1] += rank[root2];
            setcount --;
            num --;
        }
    }

    public void swap(int root1, int root2) {
        int tmp = root1;
        root1 = root2;
        root2 = tmp;
    }

    public int getSetcount () {
        return setcount;
    }

    public boolean isConnected(int index1,int index2) {
        return find(index1) == find(index2);
    }

}

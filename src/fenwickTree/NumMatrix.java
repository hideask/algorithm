package fenwickTree;

/**
 * 304. 二维区域和检索 - 矩阵不可变
 * 给定一个二维矩阵 matrix，以下类型的多个请求：
 *
 * 计算其子矩形范围内元素的总和，该子矩阵的 左上角 为 (row1, col1) ，右下角 为 (row2, col2) 。
 * 实现 NumMatrix 类：
 *
 * NumMatrix(int[][] matrix) 给定整数矩阵 matrix 进行初始化
 * int sumRegion(int row1, int col1, int row2, int col2) 返回 左上角 (row1, col1) 、右下角 (row2, col2) 所描述的子矩阵的元素 总和 。
 */
public class NumMatrix {
    int[][] arr;
    int[][] sum;
    int n, m;
    public NumMatrix(int[][] matrix) {
        arr = matrix;
        n = arr.length;
        m = arr[0].length;
        sum = new int[n + 1][m + 1];
        initSum(sum);
    }

    /**
     * 初始化一个二维前缀和数组
     * @param sum
     */
    public void initSum(int[][] sum) {
        for (int i = 1 ; i < n + 1; i ++) {
            for (int j = 1; j < m + 1; j ++) {
                sum[i][j] = sum[i - 1][j] + sum[i][j - 1] - sum[i - 1][j - 1] + arr[i - 1][j - 1];
            }
        }

    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        //坐标偏移，由于sum数组坐标比arr数组坐标大1
        row1 ++;
        col1 ++;
        row2 ++;
        col2 ++;
        return sum[row2][col2] - sum[row2][col1 - 1] - sum[row1 - 1][col2] + sum[row1 - 1][col1 - 1];
    }
}

package zhangchx.algorithm.arrays;

import java.util.Arrays;

/**
 * 约翰的生意
 * 考察数组分组，取数组中最小值等算法。
 * 在一条数轴上，有n个城市，编号从0 ~ n – 1 , 约翰打算在这n个城市做点生意，他对Armani的一批货物感兴趣，每个城市对于这批货物都有一个价格prices[i]。
 * 对于城市x,约翰可从城市编号为[x - k, x + k]购买货物，然后卖到城市x,问约翰在每个城市最多能赚到多少钱？
 * @author Snow
 *
 */
public class JohnBusiness {
	
	public static void main(String[] args) {
		int[] A = new int[]{1, 3, 2, 1, 5};
		int k = 2;
		new JohnBusiness().business(A, k);
	}
	public int[] business(int[] A, int k) {
        int[] B = new int[A.length];
        for (int i = 0; i < A.length; i++) {
            int[] sub = getSubArray(A, i, k);
            int val = getMinValue(sub);
            B[i] = A[i] - val;
        }
        return B;
    }
    
    public int[] getSubArray(int[] A, int i, int k) {
        int begin = i - k;
        int end = i + k;
        if (end >= A.length) {
            end = A.length - 1;
        }
        if (begin < 0) {
            begin = 0;
        }
        int range = end - begin + 1;
        int[] B = new int[range];
        System.arraycopy(A, begin, B, 0, range);
        return B;
    }
    
    public int getMinValue(int[] A) {
        Arrays.sort(A);
        return A[0];
    }
}

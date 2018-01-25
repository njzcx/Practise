package zhangchx.algorithm.arrays;

import org.junit.Test;

/**
 * 合并两个排序的整数数组A和B变成一个新的数组。
 * 给出 A = [1, 2, 3, empty, empty], B = [4, 5]
 * 合并之后 A 将变成 [1,2,3,4,5]
 * @author Snow
 *
 */
public class MergeSortedArray2 {

	@Test
	public void test() throws Exception {
		mergeSortedArray(new int[]{1,3,4,6,0,0}, 4, new int[]{2,5}, 2);
	}
	/*
     * @param A: sorted integer array A which has m elements, but size of A is m+n
     * @param m: An integer
     * @param B: sorted integer array B which has n elements
     * @param n: An integer
     * @return: nothing
     */
	public void mergeSortedArray(int[] A, int m, int[] B, int n) {
        int len = A.length;
        int i = 0;
        int a = 0;
        int b = 0;
        while (i < len) {
            if (a >= m || b >= n) {
                break;
            }
            if (A[i] < B[b]) {
                a++;
                i++;
            } else {
                //A移位
                System.arraycopy(A, i, A, i + 1, m - a);
                A[i++] = B[b++];
            }
        }
        
        if (a >= m) {
            while (i < len) {
                A[i++] = B[b++];
            }
        }
    }
}

package zhangchx.algorithm.arrays;

import org.junit.Test;

public class MergeSortedArray {
	
	@Test
	public void testname() throws Exception {
		mergeSortedArray(new int[]{1, 5}, new int[]{2, 3});
	}
	public int[] mergeSortedArray(int[] A, int[] B) {
        int len = A.length + B.length;
        int[] C = new int[len];
        int a = 0;
        int b = 0;
        int c = 0;
        while (c < len) {
            if (a > A.length - 1 || b > B.length - 1) {
                break;
            }
            if (A[a] <= B[b]) {
                C[c++] = A[a++];
            } else {
                C[c++] = B[b++];
            }
        }
        
        if (a > A.length - 1) {
            while (c < len) {
                C[c++] = B[b++];
            }
        }
        
        if (b > B.length - 1) {
            while (c < len) {
                C[c++] = A[a++];
            }
        }
        return C;
    }
}

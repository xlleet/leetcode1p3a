package medium;

import java.util.Arrays;

/**
 * 就业培训 week6
 * @https://leetcode.com/problems/maximum-product-subarray/
 *
 */
public class MaximumProductSubarray {
	/**
	 * 九章
	 *@space O(n) 可以优化到O(1) 因为[i]只需要[i-1]，可以化简成只需
	 */
	public static int maxProduct(int[] A) {
		if (A == null || A.length == 0)
			return 0;
		int[] max = new int[A.length], min = new int[A.length];
		max[0] = A[0];
		min[0] = max[0];
		int result = A[0];
		for (int i = 1; i < A.length; i++) {
			int currentMax = max[i - 1] * A[i];
			int currentMin = min[i - 1] * A[i];
			if (A[i] > 0) {
				// max (A[i], ...): 意思是只看A[i]的情况。
				max[i] = Math.max(A[i], currentMax);
				min[i] = Math.min(A[i], currentMin);
			} else {
				// max[i] = max(.., min[i-1]*A[i])这里感觉说,
				max[i] = Math.max(A[i], currentMin);
				min[i] = Math.min(A[i], currentMax);
			}
			result = Math.max(result, max[i]); // 需要
		}
		return result;
	}

	//
	/**
	 * 类似的dp优化后解法
	 * 
	 * @https://leetcode.com/discuss/11923
	 * 
	 */
	public int maxProductDP(int[] A) {
		if (A.length == 0)
			return 0;
		int prevMax = A[0];
		int prevMin = A[0];
		int maxSofar = A[0];
		for (int i = 1; i < A.length; i++) { // 必须跳过 A[0] 不然init很麻烦
			int a = A[i];
			int max = Math.max(Math.max(prevMax * a, prevMin * a), a); // max,max
			int min = Math.min(Math.min(prevMax * a, prevMin * a), a); // min,min
			maxSofar = Math.max(max, maxSofar);
			prevMax = max;
			prevMin = min;
		}
		return maxSofar;
	}
}

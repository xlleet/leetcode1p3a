package medium;

/**
 * https://leetcode.com/problems/h-index/
 * 
 * <pre>
 * "A scientist has index h if h of his/her N papers have at least h citations each, 
 * 	and the other N − h papers have no more than h citations each."
 * assert A[0..n-h-1].any≤h, A[n-h,n-1].any≥h
 *  

For example, given citations = [0,1,3,5,6], which means the researcher has 
5 papers in total and each of them had received 3, 0, 6, 1, 5 citations respectively. 
Since the researcher has 3 papers with at least 3 citations each and the remaining two with 
			no more than 3 citations each, his h-index is 3.
 * </pre>
 * 
 * https://leetcode.com/problems/h-index-ii/?input is sorted array
 */

public class H_Index_2 {
	/**
	 * <pre>
	 * https://discuss.leetcode.com/topic/23399
	 *  为什么可以skip掉mid呢？
	 *  -- 因为是 while(left <= right), 不skip的话会infinite loop。
	 *  为什么要用 while (left <= right) 而不是普通的left + 1 < right 呢？
	 *   -- 这是允许left == right的情况，也许是关于h=1的情况?
	 * </pre>
	 */
	public static int hIndex(int[] A) {
		final int n = A.length;
		int left = 0, right = n - 1;
		while (left <= right) { // 重点1
			final int mid = (left + right) / 2;
			if (A[mid] == n - mid)
				return A[mid];
			else if (A[mid] > n - mid)
				right = mid - 1; // 重点2 A[mid]太大，要往左一点
			else
				left = mid + 1; // 重点3 A[mid]太小，要往右一点
		}
		return n - (right + 1);
	}
}

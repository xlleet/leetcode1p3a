package stack.hard;

import java.util.*;

/**
 * https://leetcode.com/problems/maximal-rectangle/
 * 
 * 2016-8-18 就业培训作业
 * 
 * 2016-8-17看了一眼，完全不懂，完全懵逼，直接看答案把
 * 
 * <pre>
 * related
 * https://leetcode.com/problems/largest-rectangle-in-histogram/
 * https://leetcode.com/problems/maximal-square/
 * </pre>
 */
public class MaximalRectangle {
	// 还有一种更优的DP解法
	// https://discuss.leetcode.com/topic/6650/share-my-dp-solution
	// 解法3 http://www.cnblogs.com/grandyang/p/4322667.html
}
class 用LargestRec_In_Hist的解法 {
	/**
	 * {2016-08-21 09.19 PM} 自己手写写出
	 * 
	 * <pre>
	 * TODO：优化：不重复create height
	 * http://www.cnblogs.com/grandyang/p/4322667.html
	 *   for (int i = 0; i < matrix.size(); ++i) {
	        height.resize(matrix[i].size());
	        for (int j = 0; j < matrix[i].size(); ++j) {
	            height[j] = matrix[i][j] == '0' ? 0 : (1 + height[j]);
	        }
	        res = max(res, largestRectangleArea(height));
	    }
	 * </pre>
	 */
	public int maximalRectangle(char[][] matrix) {
		if (matrix.length == 0 || matrix[0].length == 0)
			return 0;
		int max = 0;
		final int nRow = matrix.length, nCol = matrix[0].length;
		int[] heights = new int[nCol];
		for (int row = 0; row < nRow; row++) {
			updateHeights(matrix, row, heights);
			max = Math.max(max, LargestRectangleInHistogram.largestRectangleArea(heights));
		}
		return max;
	}
	/**
	 * 优化：不需要在这里再往上一遍。只要一直保留着之前的，遇到0就清零，遇到1就++。
	 */
	private void updateHeights(char[][] matrix, int row, int[] heights) {
		for (int col = 0; col < matrix[row].length; col++)
			if (matrix[row][col] == '1')
				heights[col]++;
			else
				heights[col] = 0; // 清零；因为是从上往下走的，撞到了就是0了。
	}
	/**
	 * https://leetcode.com/problems/largest-rectangle-in-histogram/
	 */
	static class LargestRectangleInHistogram {
		public static int largestRectangleArea(int[] heights) {
			final int n = heights.length;
			int maxArea = 0;
			Stack<Integer> stackIndex = new Stack<>();
			for (int iRight = 0; iRight <= n; iRight++) {
				while (stackIndex.size() > 0 && (iRight == n || heights[stackIndex.peek()] >= heights[iRight])) {
					int h = heights[stackIndex.pop()];
					int width = stackIndex.size() > 0 ? (iRight - 1) - (stackIndex.peek() + 1) + 1 : iRight;
					maxArea = Math.max(maxArea, h * width);
				}
				stackIndex.push(iRight);
			}
			return maxArea;
		}
	}
}
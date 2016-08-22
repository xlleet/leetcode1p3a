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
		for (int row = 0; row < nRow; row++) {
			int[] height = new int[nCol]; // 可以用clear()而不是new来提速到32ms，39%;for(inti=0;i<height.length;height[i++]=0);
			for (int col = 0; col < nCol; col++)
				// go up in [row], get height[]
				for (int heightI = row; heightI >= 0; heightI--)
					if (matrix[heightI][col] == '1')
						height[col]++;
					else
						break;
			max = Math.max(max, LargestRectangleInHistogram.largestRectangleArea(height)); // O(n)
		}
		return max;
	}
	/**
	 * https://leetcode.com/problems/largest-rectangle-in-histogram/
	 */
	static class LargestRectangleInHistogram {
		public static int largestRectangleArea(int[] height) {
			final int n = height.length;
			int maxArea = 0;
			Stack<Integer> stackIndex = new Stack<>();
			for (int iRight = 0; iRight <= n; iRight++) {
				while (stackIndex.size() > 0 && (iRight == n || height[stackIndex.peek()] >= height[iRight])) {
					int h = height[stackIndex.pop()];
					int width = stackIndex.size() > 0 ? (iRight - 1) - (stackIndex.peek() + 1) + 1 : iRight;
					maxArea = Math.max(maxArea, h * width);
				}
				stackIndex.push(iRight);
			}
			return maxArea;
		}
	}
}
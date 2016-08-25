package medium;

/**
 * https://leetcode.com/problems/container-with-most-water/
 *
 * 作业9
 * 
 * <pre>
 * Given n non-negative integers a1, a2, ..., an, where each represents a point at coordinate (i, ai). 
 * n vertical lines are drawn such that the two endpoints of line i is at (i, ai) and (i, 0). 
 * Find **two** lines, which together with x-axis forms a container, such that the container contains the most water.
 * 
 * 1. 读题：
 * 类似largest-rectangle-in-histogram
 * 求最大的area：这里area是(i1-i2)底*(1/2)*(ai1+ai2)，也就是最大化(i1-i2)*(ai1+ai2)
 * 
 * 								    (i,ai)  (i+1,ai2) 
 *										 v  v 
 *     									 B	A    
								         *  * *  
								      *  *  * *    
								      *  *  * *   
								*     *  *  * *  *  
						x-axis	(0,0) *  *  *  * *  *
无脑O(n^2)?
-- 不对；读题而言，不是(ai1+ai2),而是"water"，也就是以最低的为准。
		- 灵感来自于 http://www.jiuzhang.com/solutions/container-with-most-water/
 * 
 * 
 * </pre>
 */
public class ContainerWithMostWater {

	/**
	 * <pre>
	 * https://discuss.leetcode.com/topic/16754
	 * 定理：[因为是底*高，所以先找最大的[底]：[(n-1)-0],然后要找更大的[高]才能达到更大的area。
	 * 所以如果[高]一样的话，area是小的。那就一直走
	 * time： O(n)
	 * 
	 * 
	 * https://discuss.leetcode.com/topic/14940/
	 * if height[i] < height[j]:
	            i += 1
	        else:
	            j -= 1
	            
	   这个很显然，没什么要proof的：谁小，那就走(因为你大了，h=min(heights[i],heights[j])才能大。
	 * https://discuss.leetcode.com/topic/3462/ 有个另一个解法的proof：
	 * 0. proof：看不懂
	 * 1. 解法：if (height[low] < height[high]) {  
	   low++;  
	 } else {  
	   high--;  
	 }
	 * </pre>
	 * 
	 */
	public int maxArea(int[] heights) {
		int maxWater = 0;
		for (int start = 0, end = heights.length - 1; start < end;) { // 不算<=是因为这题一定要两个
			int h = Math.min(heights[start], heights[end]);
			// 标准2pointer
			maxWater = Math.max(maxWater, h * (end - start));
			for (; start < end && heights[start] <= h; start++);
			for (; start < end && heights[end] <= h; end--);// 注意是--，脑残了
		}

		return maxWater;

	}

}

class Naive_O_n2 {
	/**
	 * O(n^2) compare all pairs
	 */
	public int maxArea(int[] height) {
		int maxArea = 0;
		for (int i = 0; i < height.length - 1; i++)
			for (int j = i + 1; j < height.length; j++)
				maxArea = Math.max(maxArea, (j - i) * Math.min(height[i], height[j]));
		return maxArea;

	}
}
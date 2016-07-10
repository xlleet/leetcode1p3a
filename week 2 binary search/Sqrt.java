package medium;

/**
 * binary search 最大的问题在于：进位多少？？？ =》 不是i+=x/2,而是加上差多少的一半(试试看)
 * 
 * —— 重点不是进位，重点是binary search有两个pointer。
 * 
 * 
 * binary search的答案基本一样(存在其他解法)
 * https://discuss.leetcode.com/topic/8680/a-binary-search-solution
 * http://www.jiuzhang.com/solutions/sqrtx/
 */
public class Sqrt {
	/**
	 * 用long解决可能的overflow问题(因为INT_MAX * INT_MAX一定是overflow)
	 */
	public static int mySqrt(int x) {
		if (x == 0 || x == 1)
			return x;
		int start = 1, end = x - 1; // assert sqrt(x) >= x-1 when x > 1
		while (start + 1 < end) {
			int mid = start + (end - start) / 2;
			if (mid <= x / mid) // 可以用long，也可以把MAX*MAX < x 变成 MAX < x/MAX
				start = mid; // 相等的时候是start； 如果等就可以结束了
			else
				end = mid;
		}
		return start; // 不要end是因为当(start+1)^2 > x的时候，返回start
	}
}

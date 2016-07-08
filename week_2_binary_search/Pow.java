package medium;

/**
 * https://leetcode.com/problems/powx-n/
 * 
 * @date 2016-7-6
 * @就业培训2第二课
 * @time O(logn) => 所以一定是binary search => 先从recursive开始
 */
public class Pow {
	/**
	 * <pre>
	 * (不知道该用哪个解法比较好；这个看起来不是最优解)
	 * Recursion基于九章Idea写出; 偷看了一眼答案 
	 * http://www.jiuzhang.com/solutions/powx-n/
	 * </pre>
	 * 
	 * @recursion - space O(logn)
	 * 
	 * @time O(logn)
	 * 
	 *       <pre>
	 * 需要注意的case
	 * 0. 任何数**0 = 1
	 * 1. exit case n==1。因为n=1, n/2 = 0(！！)， n-k==n，无法结束。 
	 * 2. negative的情况。如何让他知道？
	 * 3. 注意表示double不能说"1",得说"1."或者"1.0".
	 * 4. TLE on myPow(0.00001, 2147483647); debug发现有很多都是n=2; 用double r2 = k == n - k ? r1 : myPow(x, n - k);
	 * 5. ERROR: ∞ case: (2.00000, -2147483648) 我的得到了∞而不是0: 因为出现了1/0=∞的情况。
	 * 6. 最终pass，速度还是差一点(因为是recursion)
	 *       </pre>
	 */
	public static double myPow(double x, int n) {
		if (n == 0)
			return 1.;
		else if (n == 1)
			return x; // 注意这个case
		if (x == 0.)
			return x;
		boolean isNegative = n < 0;
		n = Math.abs(n);
		int k = n / 2;
		double r1 = myPow(x, k);
		double r2 = k == n - k ? r1 : myPow(x, n - k);
		if (r1 * r2 == 0.) // 注意1/0=∞
			return 0.;
		return isNegative ? 1. / r1 / r2 : r1 * r2;
	}

}

class BitOper {
	/**
	 * https://discuss.leetcode.com/topic/40546/iterative-log-n-solution-with-
	 * clear-explanation
	 */
	public double myPow(double x, int n) {
		double ans = 1;
		long absN = Math.abs((long) n); // handle n = Integer.MIN_VALUE
		while (absN > 0) {
			if ((absN & 1) == 1)
				ans *= x;
			absN >>= 1;
			x *= x;
		}
		return n < 0 ? 1 / ans : ans;
	}
}

class LC合集 {
	/**
	 * https://discuss.leetcode.com/topic/21837/5-different-choices-when-talk-
	 * with-interviewers
	 * 
	 * 注意234都有问题。
	 */
	// 1
	double myPow(double x, int n) {
		if (n < 0)
			return 1 / x * myPow(1 / x, -(n + 1));
		if (n == 0)
			return 1;
		if (n == 2)
			return x * x;
		if (n % 2 == 0)
			return myPow(myPow(x, n / 2), 2);
		else
			return x * myPow(myPow(x, n / 2), 2);
	}
	// 2
	double myPow2(double x, int n) {
		if (n == 0)
			return 1;
		double t = myPow(x, n / 2);
		return (n % 2 == 0) ? t * t : // 正好t*t的情况这个很清晰
				(n < 0 ? 1 / x * t * t : x * t * t);
	}

}
/**
 * https://discuss.leetcode.com/topic/5425
 */
class LC_Recursive_1 {
	/**
	 * @recursive 比较简洁，值得学习
	 * 
	 *            <pre>
	 * 1. n==0
	 * 2. n < 0
	 * 3. 直接用n/2
	 *            </pre>
	 */
	public double myPow(double x, int n) {
		if (n == 0)
			return 1.;
		if (n < 0) {
			n = -n;
			x = 1. / x;
		}
		if (Double.isInfinite(x)) // 无法避免的1./0.=∞的错误
			return 0.;
		return (n % 2 == 0) ? myPow(x * x, n / 2) : x * myPow(x * x, n / 2);
	}
}
/**
 * @time O(n) brute force
 */
class BruceForceON {
	public static double myPow(double x, int n) {
		if (n == 0)
			return x == 0.0 ? 0. : 1.;
		double result = 1.; // need to be 1; 0*n=0
		if (n > 0)
			for (int i = 0; i < n; i++)
				result *= x;
		else {
			n = -n; // 注意这里
			for (int i = 0; i < n; i++)
				result /= x;
		}
		return result;
	}

}
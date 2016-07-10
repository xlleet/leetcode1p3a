package week1basis;

/**
 * https://leetcode.com/problems/integer-to-roman/
 * https://www.wikiwand.com/en/Roman_numerals
 */

/**
 * 2016-07-02 自己随手写出； leetcode运行速度够快，但是代码太冗长。尝试处理IV IX这种情况，但是没有能很好处理。
 * 面试中会用精简的九章解法而不是这个暴力解法。
 */
class 暴力解法 {
	// 计划：
	// 1. hard-coded
	// 1.1 StrinbBuilder
	// 希望能优化，然而并没有成功
	private static final int _5 = 5; // V
	private static final int _10 = 10; // X
	private static final int _50 = 50; // L
	private static final int _100 = 100; // C
	private static final int _500 = 500; // D
	private static final int _1000 = 1000; // M
	public static String intToRoman(int num) {
		StringBuilder sb = new StringBuilder();
		f(num, sb);
		return sb.toString();
	}
	private static void f(int num, StringBuilder sb) {
		if (1 <= num && num <= 3) {
			for (int i = 0; i < num; i++) {
				sb.append("I");
			}
		} else if (num == _10 - 1 || num == _5 - 1) {
			sb.append("I");
			f(num + 1, sb);
		} else if (_5 <= num && num < 9) {
			sb.append("V");
			f(num - 5, sb);
		} else if (_10 <= num && num < _50 - _10) {
			sb.append("X");
			f(num - _10, sb);
		} else if ((_50 - _10 <= num && num < _50)
				|| (_100 - _10 <= num && num < _100)) {
			sb.append("X");
			f(num + _10, sb);
		} else if (_50 <= num && num < _100 - _10) {
			sb.append("L");
			f(num - _50, sb);
		} else if (_100 <= num && num < _500 - _100) {
			sb.append("C");
			f(num - (_100), sb);
		} else if ((_500 - _100 <= num && num < _1000 - _500)
				|| (_1000 - _100 <= num && num < _1000)) {
			sb.append("C");// [400,500), [900,1000)
			f(num + _100, sb);
		} else if (_500 <= num && num < _1000 - _100) {
			sb.append("D");
			f(num - (_500), sb);
		} else if (_1000 <= num) {
			sb.append("M");
			f(num - (_1000), sb);
		}
	}
}


class 九章算法 {
	/**
	 * http://www.jiuzhang.com/solutions/integer-to-roman/
	 */
	public String intToRoman(int currentInt) {
		// 这个会很自然地避免了900*3=2700的问题，因为是从左到右，先扣完所有的1000，再走900.
		final int[] A = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
		final String[] STR = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X",
				"IX", "V", "IV", "I"};
		StringBuilder sb = new StringBuilder();

		// //
		// 也可以用loop(毕竟sb都要loop)而不是currentInt/A[]来写.https://leetcode.com/discuss/49870/
		// for (int digit = 0; digit < A.length; digit++)
		// while (currentInt >= A[digit]) {
		// currentInt -= A[digit];
		// sb.append(STR[digit]);
		// }
		int digit = 0;
		while (currentInt > 0) {
			// 例如3500有3个1000，那就减去3*1000: 3500-3000=500
			int 这种有几个 = currentInt / A[digit];
			currentInt -= A[digit] * 这种有几个;
			for (int i = 0; i < 这种有几个; i++)
				sb.append(STR[digit]);
			digit++;// 下一个位数：从1000=>900
		}
		return sb.toString();
	}
}


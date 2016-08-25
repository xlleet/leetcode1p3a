package medium;

/**
 * Given a string S, find the longest palindromic substring in S. You may assume
 * that the maximum length of S is 1000, and there exists one unique longest
 * palindromic substring.
 * 
 * Given the string = "abcdzdcab", return "cdzdc".
 * 
 * O(n2) time is acceptable. Can you do it in O(n) time.
 * 
 * https://leetcode.com/problems/longest-palindromic-substring/
 * 
 * 
 * <pre>
 * 答案：
 * http://www.jiuzhang.com/solutions/longest-palindromic-substring/
 * 可以看一下C++下的
 * </pre>
 */
public class LongestPalindromicSubstring {
}
/**
 * <pre>
 * https://discuss.leetcode.com/topic/23498/
 * 
 * 各种解法都是从中间一个点开始，然后往两边"extend"
 * </pre>
 */
class LeetCode解法1_Recursive {

	public String longestPalindrome(String s) {
		if (s.length() <= 1)
			return s;
		int[] lo = new int[1], maxLen = new int[1];
		for (int i = 0; i < s.length() - 1; i++) {
			// extend Palindrome as possible
			extendPalindrome(s, i, i, lo, maxLen); // assume odd length:'aba'
			extendPalindrome(s, i, i + 1, lo, maxLen); // assume
														// even-length:'abba'
		}
		return s.substring(lo[0], lo[0] + maxLen[0]);
	}

	private void extendPalindrome(String s, int j, int k, int[] lo, int[] maxLen) {
		while (j >= 0 && k < s.length() && s.charAt(j) == s.charAt(k)) {
			j--;
			k++;
		} // extend到+1
		if (maxLen[0] < k - j - 1) {
			lo[0] = j + 1;
			maxLen[0] = k - j - 1;
		}
	}

}

class LeetCode解法2_Iterative {
	/**
	 * https://discuss.leetcode.com/topic/12187/
	 * 
	 * 这个iterative 的换pointer换得飞起，不是很好懂
	 */
	public String longestPalindrome(String s) {
		if (s.length() <= 1)
			return s;
		int min_start = 0, max_len = 1, oldEnd = 0;
		while (oldEnd < s.length()) {
			if (s.length() - oldEnd <= max_len / 2) // 已经无法超过了
				break;
			int start = oldEnd, end = oldEnd;
			while (end < s.length() - 1 && s.charAt(end + 1) == s.charAt(end))
				end++; // Skip duplicate characters.
			oldEnd = end + 1;
			while (end + 1 < s.length() && start > 0 && // 在range里
					s.charAt(end + 1) == s.charAt(start - 1)) {
				end++;
				start--;
			} // Expand.
			int new_len = end - start + 1;
			if (new_len > max_len) {
				min_start = start;
				max_len = new_len;
			}
		}
		return s.substring(min_start, max_len + 1);
	}

}

/**
 * O(n^3) TLE 毕竟其实重复数了很多。
 * 
 * 把所有的i=[0..n-1],j=[i..n-1]都过一遍https://leetcode.com/problems/valid-
 * palindrome/的O(n)解法
 */
class Trivial_O_N3 {
}
package week1basis;

/**
 * https://leetcode.com/problems/longest-substring-without-repeating-characters/
 */
public class LongSubstringWithoutRepeatChar {
	/**
	 * <pre>
	 * 2016-07-02 自己随手写出, 从HashSet简单优化，简单思路
	 * 18ms, 61%
	 * 
	 * 可以去看看(还没看，今晚leetcode讨论区维护)
	 * https://leetcode.com/discuss/questions/oj/longest-substring-without-repeating-characters
	 * http://www.jiuzhang.com/solutions/longest-substring-without-repeating-characters/
	 * </pre>
	 */
	public int lengthOfLongestSubstring(String s) {
		// 把所有s[i..j] 过一遍：
		// 每一个i，就往后走，到j，看路上有没有repeat，有repeat就离开j.
		if (s.length() == 0)
			return 0;
		int count = 1; // 至少是1
		for (int i = 0; i < s.length(); i++) {
			SetChar set = new SetChar();
			for (int j = i; j < s.length() && !set.contains(s.charAt(j)); j++) {
				count = Math.max(count, j - i + 1);
				set.add(s.charAt(j));
			}
		}
		return count;
	}
}

class SetChar {
	boolean[] yes;
	public SetChar() {
		yes = new boolean[256]; // char is 256
	}
	public void add(char charAt) {
		yes[charAt] = true;
	}
	public boolean contains(char charAt) {
		System.out.println(charAt);
		return yes[charAt];
	}
}
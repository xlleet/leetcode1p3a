package week1basis;

import java.util.*;

/**
 * https://leetcode.com/problems/restore-ip-addresses/
 * 
 * <pre>
 * 思考:
 * 1. 重复的情况
 * 111.11.1.111
 * 111.111.1.11
 * ........
 * 2. All possible pairs => 应当用permutation, DFS
 * 3. 观察：一定是int[4]. List<String> onePair = new ArrayList<>(4); String total=String.join('.', onePair);
 * 4. 最长的input是111111111111,一共12个char，每3个是一组。
 * 
 * 其他人的解法：
 * http://www.jiuzhang.com/solutions/restore-ip-addresses/ 这个解法{i<= start+3}有问题，所以isvalid()里其实会有4位(WTF)
 * 
 * 我的解法：
 * 基本按照九章的思路："all pairs" => DFS.
 * </pre>
 */
public class RestoreIPAddresses {
	public static List<String> restoreIpAddresses(String s) {
		List<String> result = new ArrayList<String>();
		dfs(result, new ArrayList<String>(4), s, 0);
		return result;
	}

	private static void dfs(List<String> result, List<String> arrayList,
			String s, int from) {
		if (arrayList.size() == 4) {
			if (from == s.length())
				result.add(String.join(".", arrayList));
			return;
		}
		for (int step = 0; step < 3 && from + step < s.length(); step++) {
			String sub = s.substring(from, from + step + 1);
			if ((sub.length() > 1 && sub.charAt(0) == '0') // "0" OK, "00" NO
					|| (sub.length() == 3 && Integer.parseInt(sub) > 255))
				return; // remove "256" case; sub.length是用来加速
			arrayList.add(sub);
			dfs(result, arrayList, s, from + step + 1); // move forward
			arrayList.remove(arrayList.size() - 1);
		}
	}

}
class leetcode的暴力解法竟然比我快 {
	/**
	 * 暴力解法，直接跑3圈for loop：因为input一定是n=12，所以无所谓O(n)..
	 * 
	 * @也可以吧 ……
	 * 
	 * @https://leetcode.com/discuss/12790
	 */
	public List<String> restoreIpAddresses(String s) {
		List<String> result = new ArrayList<String>();
		final int n = s.length();
		for (int i = 1; i < 4 && i < n - 2; i++)
			for (int j = i + 1; j < i + 4 && j < n - 1; j++)
				for (int k = j + 1; k < j + 4 && k < n; k++) {
					String s1 = s.substring(0, i), s2 = s.substring(i, j),
							s3 = s.substring(j, k), s4 = s.substring(k, n); // 暴力解
					if (isValid(s1) && isValid(s2) && isValid(s3)
							&& isValid(s4))
						result.add(s1 + "." + s2 + "." + s3 + "." + s4);

				}
		return result;
	}
	public boolean isValid(String s) {
		if (s.length() > 3 || s.length() == 0
				|| (s.charAt(0) == '0' && s.length() > 1)
				|| Integer.parseInt(s) > 255)
			return false;
		return true;
	}

}
class 优化用 {
	public static String join(String sep, List<String> list) {
		// return String.join(sep, list); JAVA 8, 0.5ms slower in leetcode
		StringBuilder sb = new StringBuilder();
		for (String string : list)
			sb.append(string).append(sep);

		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
}

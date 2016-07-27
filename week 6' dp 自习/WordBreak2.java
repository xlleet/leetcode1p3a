package hard;
import java.util.*;
/**
 * https://leetcode.com/problems/word-break-ii/
 */
public class WordBreak2 {
	/**
	 * https://discuss.leetcode.com/topic/27855
	 * 
	 * 2016-7-26 懵逼大概看懂了
	 * 
	 * @time: O(len(wordDict) ** len(s / minLengthInDict)), because there're
	 *        len(wordDict) possibilities for each cut
	 */
	public static List<String> wordBreak(String s, Set<String> wordDict) {
		return dfs(s, wordDict, new HashMap<String, LinkedList<String>>());
	}

	// DFS function returns an array including all substrings derived from s.
	private static List<String> dfs(String s, Set<String> wordDict,
			HashMap<String, LinkedList<String>> memo) {
		// case 0: memoized
		if (memo.containsKey(s))
			return memo.get(s);

		LinkedList<String> res = new LinkedList<String>();
		// case 1: empty string
		if (s.isEmpty()) {
			res.add("");
			return res;
		}

		// case 2: full string
		/**
		 * <pre>
		 * traverse wordDict，看是否是s的prefix
		 * 		如果是，就去找s[prefix.length:]的suffixes。会recursively找完所有的。
		 * 		然后把所有的可能性都加起来：prefix + " " + suffix
		 * 			（如果已经到尽头 -- suffix="" (case 1返回值) -- 那么就只加prefix); (i==s.length()的情况)
		 * </pre>
		 */
		for (String prefixWord : wordDict)
			if (s.startsWith(prefixWord)) {
				List<String> suffixes = dfs(s.substring(prefixWord.length()),
						wordDict, memo);
				for (String suffix : suffixes)
					res.add(prefixWord
							+ (suffix.isEmpty() ? "" : " " + suffix));
			}

		memo.put(s, res);
		return res;
	}
}

/**
 * <pre>

07/10/2016 - mock interview - 第二课 - 二分与倍增查找 
    public ArrayList<String> wordBreak2(String s, Set<String> dict) {
        Map<String, ArrayList<String>> memorized = new HashMap<>();
        return breakIntoWordList(s, dict, memorized);
    }

    private List<String> breakIntoWordList(String s, Set<String> dict,
            Map<String, ArrayList<String>> memorized) {
        ArrayList<String> result = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return result;
        }

        if (memorized.containsKey(s)) {
            return memorized.get(s);
        }

        for (int i = 1; i <= s.length(); i++) {
            String prefix = s.substring(0, i);
            if (dict.contains(prefix)) {
                if (i == s.length()) { // whole string is word
                    result.add(s);
                    memorized.put(s, result);
                    return result;
                }

                String suffix = s.substring(i, s.length());
                List<String> segSuffix = breakIntoWordList(suffix, dict,
                        memorized);
                for (String suffixWord : segSuffix) {
                    result.add(prefix + " " + suffixWord);
                }
            }
        }

        memorized.put(s, result);
        return result;
    }
 * 
 * 
 * </pre>
 */

class 自己写的失败的DP解法 {
	/**
	 * 类似于word break和coin change的解法，但是TLE;
	 * 
	 * 类似的： https://discuss.leetcode.com/topic/3495
	 * 
	 * @time O(s.length() * max(s.length(), maxLengthInwordDict))?
	 */
	public static List<String> wordBreak(String s, final Set<String> wordDict) {
		ArrayList<List<String>> possible = new ArrayList<List<String>>(
				s.length() + 1);
		for (int i = 0; i < s.length() + 1; i++)
			possible.add(new ArrayList<String>());
		possible.get(0).add("");

		// get max and min length
		int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
		for (String word : wordDict) {
			max = Math.max(max, word.length());
			min = Math.min(min, word.length());
		}

		for (int end = min - 1; end < s.length(); end++) { // 如果min是3的话，那就从index=2开始，因为这样[0..2]才可能形成一个word。
			for (int start = Math.max(0, end - max); start <= end; start++) { // "<="为了length-1的string
				if (possible.get(start).isEmpty())
					continue; // if cannot reach [start], skip.
				String currentSubstring = s.substring(start, end + 1); // s[start..end]
				if (wordDict.contains(currentSubstring))
					for (String previous : possible.get(start))
						possible.get(end + 1).add(previous
								+ (start == 0 ? "" : " ") + currentSubstring);
			}
		}
		return possible.get(possible.size() - 1);
	}

	public static void main(String[] args) {
		System.out.println(wordBreak("catsanddog", new HashSet<String>(Arrays
				.asList(new String[]{"cat", "cats", "and", "sand", "dog"}))));
		System.out.println(wordBreak("a",
				new HashSet<String>(Arrays.asList(new String[]{"a"}))));
	}

}
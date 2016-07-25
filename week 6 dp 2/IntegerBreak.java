public class IntegerBreak {
	/**
	 * https://discuss.leetcode.com/topic/42978
	 */
	public int integerBreak(int n) {
		int[] dp = new int[n + 1];
		for (int i = 2; i <= n; i++) {
			for (int j = 1; j < i; j++) {
				int curBreak = (i - j) * Math.max(dp[j], j); // max{dp[j],j}:j是拆分好还是用自己的j值好。
				// dp[j]表示： 拆分到j的时候的值
				// j表示：不拆分j，但是拆分i = (i-j) + j； 看哪个比较大。
				// 例如j=6，这个就是对比「6」本身大，还是integerBreak(6)=2*2*2=「8」大。
				dp[i] = Math.max(dp[i], curBreak); // 反复更新dp[i]的值:(i-1)次,看目前用currentBreak大不大。
			}
		}
		return dp[n];
	}
}

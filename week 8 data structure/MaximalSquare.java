package stack.hard;

/**
 * https://leetcode.com/problems/maximal-square/ , 数1组成的正方形最大的。
 * 
 * 作为week8.stack.作业题的related来做（https://leetcode.com/problems/maximal-rectangle/）
 * 
 * @tag: DP
 */
public class MaximalSquare {
	/**
	 * https://discuss.leetcode.com/topic/15328? 有解释
	 * 
	 * <pre>
	 * input:
	 *  1 0 1 0 0
		1 0 1 1 1
		1 1 1 1 1
		1 0 0 1 0
	dp:
	   [[1, 0, 1, 0, 0], 
		[1, 0, 1, 1, 1], 
		[1, 1, 1, 2!,2], <- 注意这里要达成【2】的话，自己的【上面】+【左边】+【右边】必须都是【1】，才能达成【2】（这样才凑出来一个square） 
		[1, 0, 0, 1, 0]] <- 所以才是min(左，上，右)
	 * 
	 * 一句话解答：
	 * 0. 选择使用DP，是因为比较直白（为什么不是像【maximal-rectangle】用stack？说不清楚。）
	 * 1. dp[i][j]表示以(i,j)为【右下角】的正方形的面积
	 * 		- 注意这里dp[i][j]并不是maximalSquare(matrix[0..i][0..j])的解(max area而不是单个)，像以往的dp一样。
	 * 			- 是因为：如果是那样的话，答案整理起来很困难。
	 * 			- 启发：DP还可以这样玩：不一定是存【global optimal】，只是存【small portion optimal】
	 * 		- 例如上面的例子。
	 * 
	 * 2. init DP: 【上】和【左】两条边都无法形成以此为【右下角】的>1的正方形；
	 * 		- 所以只能是[1]或者[0]，也就是直接赋值
	 * 		- 当然也可以像下面解答一样
	 * 
	 * 2.1. 在init dp的时候，如果有一个1，那就把maxLen = 1。。说不定就只有这个1了
	 * 
	 * 3. DP loop, 走[1..n-1]，跳过0是因为需要往前看（所以才会有[2.1]的init)
	 * 		3.1. 判断是否目前为'1',如果是1的话：
	 * 			3.1.1. 那就看自己的【←左】+【↖左上】+【↑上】是不是都有；
	 * 				- 如果都有的话就可以和他们合成一个更大的正方形：（边长+1）
	 * 				- 如果有一个没有（就是0）-那自己就是孤单的一个1了。
	 * 		
	 * 		3.2. 在[2]和[3]的整个过程中，一直记录maxLenth,表示最大正方形的边长。
	 * 4. return maxLength^2
	 * 
	
	FAQ: 3.1.1. 万一边长比2更大呢？
	
	例如DP长这样：
	[[0, 0, 0, 0, 0, 0], 
	 [0, 1, 0, 1, 0, 0], 
	 [0, 1, 0, 1, 1, 1], 
	 [0, 1, 1, 1, 2, 2], 
	 [0, 1, 0, 1, 2, 3!]] 
		-- 也就是3会被2包围着。为什么呢？因为一个边长为[3]的正方形的【里面】，
			一定要有4个（具体几个不重要）边长为[3-1]的正方形。例如下图：
	1.  + + _ 2.	_ + + 3._ _ _ 4._ _ _
		+ + _	_ + +	+ + _	_ + +
		_ _ _	_ _ _	+ + _	_ + +
		也就是说，在访问到右下角这个点的时候，一定会看到有{图2}、{图3}这两个边长[2]的正方形。
		
		
	FAQ: 你能优化space O(n^2)吗？
	  A: 
	  		1. 可以优化到 O(n),因为每次只用n-1那一边，所以只用两行:{cur[],pre[]}
	  		2. 或者用上一行的[i-1][j-1], [i-1][j] & 单独放一个variable保留
	  		- 这里都有https://discuss.leetcode.com/topic/15328
	 * 
	 * </pre>
	 */
	public int maximalSquare(char[][] matrix) {
		if (matrix.length == 0 || matrix[0].length == 0)
			return 0;
		int[][] dp = new int[matrix.length][matrix[0].length];
		int maxLength = 0;// 一边跑一边max
		// 1. init; can skip i=0
		for (int i = 0; i < matrix.length; i++)
			maxLength = Math.max(maxLength, dp[i][0] = matrix[i][0] - '0');
		for (int j = 0; j < matrix[0].length; j++)
			maxLength = Math.max(maxLength, dp[0][j] = matrix[0][j] - '0');
		// 3. loop
		for (int i = 1; i < matrix.length; i++)
			for (int j = 1; j < matrix[0].length; j++)
				if (matrix[i][j] == '1')
					maxLength = Math.max(maxLength,
							dp[i][j] = min(dp[i - 1][j - 1], dp[i][j - 1],
									dp[i - 1][j]) + 1);
		return maxLength * maxLength;
	}

	protected int min(int i, int j, int k) {
		return Math.min(i, Math.min(j, k));
	}
}
class 二合一 extends MaximalSquare {
	/**
	 * <pre>
	 * 微变形1：直接二合一：直接用int[n+1][m+1]来左
	 * https://discuss.leetcode.com/topic/20801
	 * 这里的dp[i][j]的意义变成了以[i-1][j-1]这个点为【最右下角】的【最大】正方形的【边长】（边长^2=面积）
	 * 
	 * space: O(n^2)
	 * </pre>
	 */
	public int maximalSquare二合一(char[][] matrix) {
		if (matrix.length == 0 || matrix[0].length == 0)
			return 0;
		int[][] dp = new int[matrix.length + 1][matrix[0].length + 1];
		int maxSide = 0;
		// 1. loop
		for (int i = 1; i <= matrix.length; i++)
			for (int j = 1; j <= matrix[0].length; j++)
				if (matrix[i - 1][j - 1] == '1')
					maxSide = Math.max(maxSide, dp[i][j] = min(dp[i - 1][j - 1],
							dp[i][j - 1], dp[i - 1][j]) + 1);
		return maxSide * maxSide;
	}
}